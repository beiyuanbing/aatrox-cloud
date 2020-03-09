package ${ServiceImpl.beanPackage};

import ${Model.beanPackage}.${Model.fileName};
<#if openForm>
import ${InsertForm.beanPackage}.${InsertForm.fileName};
import ${EditForm.beanPackage}.${EditForm.fileName};
</#if>
import ${Dao.beanPackage}.${Dao.fileName};
import ${Service.beanPackage}.${Service.fileName};
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
<#if openMybaitPlus>
import ${superServiceImplClassPackage};
</#if>

/**
* <p>
    * ${table.comment!} 服务实现类
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Service
<#if openMybaitPlus>
public class ${ServiceImpl.fileName} extends ${superServiceImplClass}<${Dao.fileName}, ${Model.fileName}> implements ${Service.fileName} {
<#else>
public class ${ServiceImpl.fileName} implements ${Service.fileName} {
</#if>
<#if !openMybaitPlus>
    @Resource
    private ${Dao.fileName} ${Dao.fileName?uncap_first};

    @Override
    public ${Model.fileName} selectById(${table.fields[0].propertyType} id){
        return ${Dao.fileName?uncap_first}.selectById(id);
    }

    @Override
    public List<${Model.fileName}> selectAll(){
        return ${Dao.fileName?uncap_first}.selectAll();
    }

    @Override
    <#if openForm>
    public ${InsertForm.fileName} insert${entity}(${InsertForm.fileName} record){
    <#else>
    public ${Model.fileName} insert${entity}(${Model.fileName} record){
    </#if>
         ${Dao.fileName?uncap_first}.insert${entity}(record);
         return record;
    }

    @Override
    <#if openForm>
    public ${EditForm.fileName} update${entity}(${EditForm.fileName} record){
    <#else>
    public ${Model.fileName} update${entity}(${Model.fileName} record){
    </#if>
        ${Dao.fileName?uncap_first}.update${entity}(record);
        return record;
    }

    @Override
    public int deleteById(${table.fields[0].propertyType} id){
        return ${Dao.fileName?uncap_first}.deleteById(id);
    }
</#if>
}
