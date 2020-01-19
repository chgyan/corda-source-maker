package ${packageName}.contract;

import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.Contract;
import net.corda.core.identity.AbstractParty;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

import static net.corda.core.contracts.ContractsDSL.requireThat;

import ${packageName}.state.${entityName}State;


/**
 * ${entityName} contract
 */
public class ${entityName}Contract implements Contract {
    public static String ID = "${packageName}.contract.${entityName}Contract";

    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {
        if (tx.getCommands().size() != 1) {
            throw new IllegalArgumentException("invalid commands size");
        }

        CommandData command = tx.getCommand(0).getValue();
        if (command instanceof Commands.${command?cap_first}) {
            ${command}(tx);
        } else {
            throw new IllegalArgumentException("invalid command");
        }
    }

    private void ${command}(LedgerTransaction tx) throws IllegalArgumentException {
        requireThat(require -> {
            require.using("No inputs should be consumed when issuing product information",
                    tx.getInputs().isEmpty());
            require.using("only on output state should be created",
                    tx.getOutputs().size() == 1);

            final ${entityName}State output = tx.outputsOfType(${entityName}State.class).get(0);

            require.using("All of the participants must be signers.",
                    tx.getCommand(0).getSigners().containsAll(
                            output.getParticipants().stream().map(AbstractParty::getOwningKey).collect(Collectors.toList())));

            //require.using("invalid ${entityName}State", output.valid());

            return null;
        });
    }

    public interface Commands extends CommandData {
        class ${command?cap_first} implements Commands {
        }
    }
}