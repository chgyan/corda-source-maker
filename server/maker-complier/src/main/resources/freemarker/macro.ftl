<#-- 这是getAll方法的宏定义 -->
<#macro getAllField fields>
<#list fields as field>
	<#-- **************除了布尔类型之外的属性调用函数*********** -->
	<#if field.type!="boolean">
	public ${field.type} get${field.name?cap_first}() {
		return this.${field.name};
	}

	<#else>
	<#-- *************布尔类型进行特殊处理 独有的属性调用函数*********** -->
	public ${field.type} is${field.name?cap_first}() {
		return this.${field.name};
	}

	</#if>
</#list>
</#macro>
<#-- 这是setAll方法的宏定义 -->
<#macro setAllField fields>
<#list fields as field>
	public void set${field.name?cap_first}(final ${field.type} ${field.name}) {
		this.${field.name} = ${field.name};
	}
</#list>
</#macro>