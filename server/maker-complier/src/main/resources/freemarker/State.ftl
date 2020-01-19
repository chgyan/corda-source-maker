<#include "macro.ftl">
package ${packageName}.state;

import java.util.List;
import com.google.common.collect.ImmutableList;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;
import net.corda.core.schemas.QueryableState;

import ${packageName}.contract.${entityName}Contract;
import ${packageName}.schema.${entityName}SchemaV1;

/**
 * ${entityName} state
 */
@BelongsToContract(${entityName}Contract.class)
public class ${entityName}State implements QueryableState {

    private Party owner; //参与者自己
	<#list fields as field>
    private ${field.type} ${field.name};
	</#list>
    private String sign; //需要加密内容的签名项.flow中自动加密
    
    public ${entityName}State(Party owner, <#list fields as field>${field.type} ${field.name}<#if field_has_next>,</#if></#list>,String sign) {
        this.owner = owner;
        <#list fields as field>
        this.${field.name} = ${field.name};
		</#list>
        this.sign = sign;
    }

    <#-- get方法 -->
    public Party getOwner() {
        return this.owner;
    }

    <@getAllField fields=fields/>

    public String getSign() {
        return this.sign;
    }

    @Override
    public List<AbstractParty> getParticipants() {
        return ImmutableList.of(owner<#if participants?size gt 0>, <#list participants as field>${field}<#if field_has_next>,</#if></#list></#if>);
    }

    @Override
    public PersistentState generateMappedObject(MappedSchema schema) {
        if (schema instanceof ${entityName}SchemaV1) {
            return new ${entityName}SchemaV1.${entityName}Persistent(
                    this.owner.getName().toString(),
                <#list fields as field>
                    <#if field.type == "Party">
                    this.${field.name}.getName().toString()<#if field_has_next>,</#if>
                    <#else>
                    this.${field.name},
                    </#if>
                </#list>
                    this.sign
            );
        } else {
            throw new IllegalArgumentException("Unrecognised schema $schema");
        }
    }


    @Override
    public Iterable<MappedSchema> supportedSchemas() {
        return ImmutableList.of(new ${entityName}SchemaV1());
    }
}