package ${Fegin.beanPackage};

import ${Model.beanPackage}.${Model.fileName};
<#if openForm>
import ${InsertForm.beanPackage}.${InsertForm.fileName};
import ${EditForm.beanPackage}.${EditForm.fileName};
</#if>
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
/**
* <p>
    * ${table.comment!} 服务类
    * </p>
*
* @author ${author}
* @since ${date}
*/
@FeignClient(value = "${package.ModuleName}", contextId = "${Fegin.fileName}")
public interface ${Fegin.fileName}  {

    @PostMapping("/${table.entityPath}/selectById")
    ${Model.fileName} selectById(${table.fields[0].propertyType} id);

    @PostMapping("/${table.entityPath}/selectAll")
    List<${Model.fileName}> selectAll();

    @PostMapping("/${table.entityPath}/insert${entity}")
    <#if openForm>
    ${InsertForm.fileName} insert${entity}(${InsertForm.fileName} record);
    <#else>
    ${Model.fileName} insert${entity}(${Model.fileName} record);
    </#if>


    @PostMapping("/${table.entityPath}/update${entity}")
    <#if openForm>
    ${EditForm.fileName} update${entity}(${EditForm.fileName} record);
    <#else>
    ${Model.fileName} update${entity}(${Model.fileName} record);
    </#if>

    @PostMapping("/${table.entityPath}/deleteById")
    int deleteById(${table.fields[0].propertyType} id);

}

