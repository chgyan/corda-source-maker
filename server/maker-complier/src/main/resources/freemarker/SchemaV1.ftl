<#include "macro.ftl">
package ${packageName}.schema;

import com.google.common.collect.ImmutableList;
import net.corda.core.schemas.MappedSchema;
import net.corda.core.schemas.PersistentState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ${entityName}State schema.
 */
public class ${entityName}SchemaV1 extends MappedSchema {
    public ${entityName}SchemaV1() {
        super(${entityName}Schema.class, 1, ImmutableList.of(${entityName}Persistent.class));
    }

    @Entity
    @Table(name = "${tableName}")
    public static class ${entityName}Persistent extends PersistentState {
        @Column(name = "owner")
        private String owner;
    <#list fields as field>
        @Column(name = "${field.name}" <#if field.length gt 0>, length=${field.length}</#if>)
        private <#if field.type == "Party">String<#else> ${field.type}</#if> ${field.name};
    </#list>
        @Column(name = "sign")
        private String sign;

        public ${entityName}Persistent(String owner, <#list fields as field><#if field.type == "Party">String<#else> ${field.type}</#if> ${field.name}<#if field_has_next>,</#if></#list>, String sign) {
        this.owner = owner;
        <#list fields as field>
        this.${field.name} = ${field.name};
        </#list>
        this.sign = sign;
        }

        public ${entityName}Persistent() {
            this.owner = null;
            <#list fields as field>
            this.${field.name} = null;
            </#list>
            this.sign = null;
        }

        <#-- get方法 -->
        public String getOwner() {
            return this.owner;
        }

        <#list fields as field>
        <#-- **************除了布尔类型之外的属性调用函数*********** -->
        <#if field.type!="boolean">
        public <#if field.type == "Party">String<#else> ${field.type}</#if> get${field.name?cap_first}() {
            return this.${field.name};
        }

        <#else>
        <#-- *************布尔类型进行特殊处理 独有的属性调用函数*********** -->
        public ${field.type} is${field.name?cap_first}() {
             return this.${field.name};
        }

            </#if>
        </#list>
        public String getSign() {
            return this.sign;
        }
    }
}