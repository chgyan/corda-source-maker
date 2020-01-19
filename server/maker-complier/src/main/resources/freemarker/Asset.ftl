<#include "macro.ftl">
package ${packageName}.asset;

import net.corda.core.serialization.CordaSerializable;
import net.corda.core.identity.Party;

@CordaSerializable
public class ${entityName}Asset {
    <#list fields as field>
    private ${field.type} ${field.name};
    </#list>

    public ${entityName}Asset(){
    }

    public ${entityName}Asset(<#list fields as field>${field.type} ${field.name}<#if field_has_next>,</#if></#list>) {
    <#list fields as field>
        this.${field.name} = ${field.name};
    </#list>
    }

    <#-- get方法 -->
    <@getAllField fields=fields/>
    <@setAllField fields=fields/>

}
