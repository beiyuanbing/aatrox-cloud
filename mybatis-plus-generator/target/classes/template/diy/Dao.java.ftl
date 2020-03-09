package ${Dao.beanPackage};

import ${Model.beanPackage}.${Model.fileName};
<#if openForm>
import ${InsertForm.beanPackage}.${InsertForm.fileName};
import ${EditForm.beanPackage}.${EditForm.fileName};
</#if>
import java.util.List;
<#if openMybaitPlus>
import ${superMapperClassPackage};
</#if>
/**
* <p>
    * ${table.comment!} Mapper 接口
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if openMybaitPlus>
public interface ${Dao.fileName} extends ${superMapperClass}<${Model.fileName}>{
<#else >
public interface ${Dao.fileName}{
</#if>
    <#if !openMybaitPlus>
    ${Model.fileName} selectById(${table.fields[0].propertyType} id);

    List<${Model.fileName}> selectAll();

    <#if openForm>
    int insert${entity}(${InsertForm.fileName} record);
    <#else>
    int insert${entity}(${Model.fileName} record);
    </#if>

    <#if openForm>
    int update${entity}(${EditForm.fileName} record);
    <#else>
    int update${entity}(${Model.fileName} record);
    </#if>

    int deleteById(${table.fields[0].propertyType} id);
    </#if>
}

