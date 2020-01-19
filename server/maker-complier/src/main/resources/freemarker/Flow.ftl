package ${packageName}.flow;

import co.paralleluniverse.fibers.Suspendable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.corda.core.contracts.Command;
import net.corda.core.crypto.SecureHash;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;
import net.corda.core.utilities.ProgressTracker;
import net.corda.core.utilities.ProgressTracker.Step;

import ${packageName}.asset.${entityName}Asset;
import ${packageName}.contract.${entityName}Contract;
import ${packageName}.state.${entityName}State;

public class ${entityName}Flow {

    @InitiatingFlow
    @StartableByRPC
    public static class Initiator extends FlowLogic<SignedTransaction> {

        private final ${entityName}Asset asset;

        private final Step GENERATING_TRANSACTION = new Step("Generating transaction based on new IOU.");
        private final Step VERIFYING_TRANSACTION = new Step("Verifying contract constraints.");
        private final Step SIGNING_TRANSACTION = new Step("Signing transaction with our private key.");
        private final Step GATHERING_SIGS = new Step("Gathering the counterparty's signature.") {
            @Override
            public ProgressTracker childProgressTracker() {
                return CollectSignaturesFlow.Companion.tracker();
            }
        };
        private final Step FINALISING_TRANSACTION = new Step("Obtaining notary signature and recording transaction.") {
            @Override
            public ProgressTracker childProgressTracker() {
                return FinalityFlow.Companion.tracker();
            }
        };

        private final ProgressTracker progressTracker = new ProgressTracker(
                GENERATING_TRANSACTION,
                VERIFYING_TRANSACTION,
                SIGNING_TRANSACTION,
                GATHERING_SIGS,
                FINALISING_TRANSACTION
        );

        public Initiator(${entityName}Asset asset) {
            this.asset = asset;
        }

        @Override
        public ProgressTracker getProgressTracker() {
            return progressTracker;
        }

        @Suspendable
        @Override
        public SignedTransaction call() throws FlowException {
            // Obtain a reference to the notary we want to use.
            final Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

            // Stage 1.
            progressTracker.setCurrentStep(GENERATING_TRANSACTION);
            // Generate an unsigned transaction.
            Party me = getOurIdentity();

            //sign
            String signStr = <#list fields as field><#if field.sign == true>asset.get${field.name?cap_first}()+</#if></#list>"";
            String sign = SecureHash.sha256(signStr).toString();

            ${entityName}State state = new ${entityName}State(me, <#list fields as field>asset.get${field.name?cap_first}(),</#list> sign);

            final Command<${entityName}Contract.Commands.${command?cap_first}> txCommand = new Command<>(
                    new ${entityName}Contract.Commands.${command?cap_first}(),
                    ImmutableList.of(state.getOwner().getOwningKey()<#if participants?size gt 0>, <#list participants as field>state.get${field?cap_first}().getOwningKey()<#if field_has_next>,</#if></#list></#if>));
            final TransactionBuilder txBuilder = new TransactionBuilder(notary)
                    .addOutputState(state, ${entityName}Contract.ID)
                    .addCommand(txCommand);

            // Stage 2.
            progressTracker.setCurrentStep(VERIFYING_TRANSACTION);
            // Verify that the transaction is valid.
            txBuilder.verify(getServiceHub());

            // Stage 3.
            progressTracker.setCurrentStep(SIGNING_TRANSACTION);
            // Sign the transaction.
            final SignedTransaction partSignedTx = getServiceHub().signInitialTransaction(txBuilder);

            // Stage 4.
            progressTracker.setCurrentStep(GATHERING_SIGS);
            // Send the state to the counterparty, and receive it back with their signature.
            <#list participants as field>
            FlowSession ${field}Session = initiateFlow(state.get${field?cap_first}());
            </#list>
            <#if participants?size gt 0>
            final SignedTransaction fullySignedTx = subFlow(
                    new CollectSignaturesFlow(partSignedTx, ImmutableSet.of(<#list participants as field>${field}Session<#if field_has_next>,</#if></#list>), CollectSignaturesFlow.Companion.tracker()));
            <#else>
            final SignedTransaction fullySignedTx = partSignedTx;
            </#if>

            // Stage 5.
            progressTracker.setCurrentStep(FINALISING_TRANSACTION);
            // Notarise and record the transaction in both parties' vaults.
            <#if participants?size gt 0>
            return subFlow(new FinalityFlow(fullySignedTx, ImmutableSet.of(<#list participants as field>${field}Session<#if field_has_next>,</#if></#list>)));
            <#else>
            return subFlow(new FinalityFlow(fullySignedTx));
            </#if>
        }
    }

    @InitiatedBy(Initiator.class)
    public static class Acceptor extends FlowLogic<Void> {

        private final FlowSession otherSide;

        public Acceptor(FlowSession otherSide) {
            this.otherSide = otherSide;
        }

        @Override
        @Suspendable
        public Void call() throws FlowException {
            SignedTransaction signedTransaction = subFlow(new SignTransactionFlow(otherSide) {
                @Suspendable
                @Override
                protected void checkTransaction(SignedTransaction stx) throws FlowException {
                    // Implement responder flow transaction checks here
                }
            });
            subFlow(new ReceiveFinalityFlow(otherSide, signedTransaction.getId()));
            return null;
        }
    }
}

