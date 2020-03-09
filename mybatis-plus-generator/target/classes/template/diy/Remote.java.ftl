package ${Remote.beanPackage};

import ${Model.beanPackage}.${Model.fileName};
<#if openForm>
import ${InsertForm.beanPackage}.${InsertForm.fileName};
import ${EditForm.beanPackage}.${EditForm.fileName};
</#if>
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* <p>
    * ${table.comment!} 服务实现类
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Service
public class ${Remote.fileName}{

    @Resource
    private ${Fegin.fileName} ${Fegin.fileName?uncap_first};

    public ${Model.fileName} selectById(${table.fields[0].propertyType} id){
        return ${Fegin.fileName?uncap_first}.selectById(id);
    }

    public List<${Model.fileName}> selectAll(){
        return ${Fegin.fileName?uncap_first}.selectAll();

    }
    <#if openForm>
    public ${InsertForm.fileName} insert${entity}(${InsertForm.fileName} record);
    <#else>
    public ${Model.fileName} insert${entity}(${Model.fileName} record){
    </#if>
        return ${Fegin.fileName?uncap_first}.insert${entity}(record);
    }

    <#if openForm>
    public ${EditForm.fileName} update${entity}(${EditForm.fileName} record);
    <#else>
    public ${Model.fileName} update${entity}(${Model.fileName} record){
    </#if>
        return ${Fegin.fileName?uncap_first}.update${entity}(record);
    }

    public int deleteById(${table.fields[0].propertyType} id){
        return ${Fegin.fileName?uncap_first}.deleteById(id);
    }

}
