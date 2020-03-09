package ${ServiceImpl.beanPackage};

import ${Model.beanPackage}.${Model.fileName};
<#if openForm>
import ${InsertForm.beanPackage}.${InsertForm.fileName};
import ${EditForm.beanPackage}.${EditForm.fileName};
</#if>
import ${Fegin.beanPackage}.${Fegin.fileName};
import ${Service.beanPackage}.${Service.fileName};
import javax.annotation.Resource;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * ${table.comment!} 服务实现类
    * </p>
*
* @author ${author}
* @since ${date}
*/
@RestController
@RequestMapping(value = "/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
public class ${Api.fileName} implements ${Fegin.fileName} {

    @Resource
    private ${Service.fileName} ${Service.fileName?uncap_first};

    @Override
    @PostMapping("/selectById")
    public ${Model.fileName} selectById(@RequestBody ${table.fields[0].propertyType} id){
        <#if openMybaitPlus>
            return ${Service.fileName?uncap_first}.getById(id);
        <#else>
            return ${Service.fileName?uncap_first}.selectById(id);
        </#if>
    }

    @Override
    @PostMapping("/selectAll")
    public List<${Model.fileName}> selectAll(){
        <#if openMybaitPlus>
            return ${Service.fileName?uncap_first}.list();
        <#else>
            return ${Service.fileName?uncap_first}.selectAll();
        </#if>
    }

    @Override
    @PostMapping("/insert${entity}")
    <#if openMybaitPlus>
    public ${Model.fileName} insert${entity}(@RequestBody ${Model.fileName} record){
        ${Service.fileName?uncap_first}.updateById(record);
        return record;
    }
    <#else>
    <#if openForm>
    public ${InsertForm.fileName} insert${entity}(@RequestBody ${InsertForm.fileName} record);
    <#else>
    public ${Model.fileName} insert${entity}(@RequestBody ${Model.fileName} record){
    </#if>
        return ${Service.fileName?uncap_first}.update${entity}(record);
    }
    </#if>

    @Override
    @PostMapping("/update${entity}")
    <#if openMybaitPlus>
    public ${Model.fileName} update${entity}(@RequestBody ${Model.fileName} record){
        ${Service.fileName?uncap_first}.updateById(record);
        return record;
    }
    <#else>
    <#if openForm>
    public ${EditForm.fileName} update${entity}(@RequestBody ${EditForm.fileName} record);
    <#else>
    public ${Model.fileName} update${entity}(@RequestBody ${Model.fileName} record){
    </#if>
        return ${Service.fileName?uncap_first}.update${entity}(record);
    }
    </#if>

    @Override
    @PostMapping("/deleteById")
    public int deleteById(@RequestBody ${table.fields[0].propertyType} id){
        <#if openMybaitPlus>
            return ${Service.fileName?uncap_first}.removeById(record)?1:0;
        <#else>
            return ${Service.fileName?uncap_first}.deleteById(id);
        </#if>
    }

}
