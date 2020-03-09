package ${Service.beanPackage};

import ${Model.beanPackage}.${Model.fileName};
<#if openForm>
import ${InsertForm.beanPackage}.${InsertForm.fileName};
import ${EditForm.beanPackage}.${EditForm.fileName};
</#if>
import java.util.List;
<#if openMybaitPlus>
import ${superServiceClassPackage};
</#if>
/**
* <p>
    * ${table.comment!} 服务类
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if openMybaitPlus>
public interface ${Service.fileName} extends ${superServiceClass}<${Model.fileName}> {
<#else>
public interface ${Service.fileName}  {
</#if>

<#if !openMybaitPlus>
    ${Model.fileName} selectById(${table.fields[0].propertyType} id);

    List<${Model.fileName}> selectAll();

    <#if openForm>
    ${InsertForm.fileName} insert${entity}(${InsertForm.fileName} record);
    <#else>
    ${Model.fileName} insert${entity}(${Model.fileName} record);
    </#if>

    <#if openForm>
    ${EditForm.fileName} update${entity}(${EditForm.fileName} record);
    <#else>
    ${Model.fileName} update${entity}(${Model.fileName} record);
    </#if>

    int deleteById(${table.fields[0].propertyType} id);
</#if>
}

