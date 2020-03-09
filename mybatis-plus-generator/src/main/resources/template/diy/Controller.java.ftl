package ${Controller.beanPackage};


import org.springframework.web.bind.annotation.RequestMapping;
<#if openForm>
import ${InsertForm.beanPackage}.${InsertForm.fileName};
import ${EditForm.beanPackage}.${EditForm.fileName};
</#if>
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import ${Model.beanPackage}.${Model.fileName};
<#if springCloud>
import ${Remote.beanPackage}.${Remote.fileName};
<#else>
import ${Service.beanPackage}.${Service.fileName};
</#if>
import javax.annotation.Resource;
import java.util.List;
<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
</#if>
/**
* <p>
    * ${table.comment!} 前端控制器
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if superControllerClass??>
public class ${Controller.fileName} extends ${superControllerClass} {
<#else>
public class ${Controller.fileName} {
</#if>
    <#if springCloud>
    @Resource
    private ${Remote.fileName} ${Remote.fileName?uncap_first};
    <#else>
    @Resource
    private ${Service.fileName} ${Service.fileName?uncap_first};
    </#if>

    <#if !restControllerStyle>
    @ResponseBody
    </#if>
    @GetMapping(value = "/selectById")
    <#if swagger2>
    @ApiOperation(value = "查询${table.comment!}的详情", tags = "${table.comment!}")
    @ApiResponses({
        @ApiResponse(message = "查询${table.comment!}的详情", code = 200, response = ${Model.fileName}.class)
    })
    </#if>
    public Object selectById(${table.fields[0].propertyType} id){
        <#if openMybaitPlus>
        <#if springCloud>
        return ${Remote.fileName?uncap_first}.selectById(id);
        <#else>
        return ${Service.fileName?uncap_first}.getById(id);
        </#if>
        <#else>
        <#if springCloud>
        return ${Remote.fileName?uncap_first}.selectById(id);
        <#else>
        return ${Service.fileName?uncap_first}.selectById(id);
        </#if>
        </#if>
    }

    <#if !restControllerStyle>
    @ResponseBody
    </#if>
    @GetMapping(value = "/selectAll")
    <#if swagger2>
    @ApiOperation(value = "查询${table.comment!}的所有数据", tags = "${table.comment!}")
    @ApiResponses({
        @ApiResponse(message = "查询${table.comment!}的所有数据", code = 200, response = ${Model.fileName}.class)
    })
    </#if>
    public List<${Model.fileName}> selectAll(){
        <#if openMybaitPlus>
        <#if springCloud>
        return ${Remote.fileName?uncap_first}.selectAll();
        <#else>
        return ${Service.fileName?uncap_first}.list();
        </#if>
        <#else>
        <#if springCloud>
        return ${Remote.fileName?uncap_first}.selectAll();
        <#else>
        return ${Service.fileName?uncap_first}.selectAll();
        </#if>
        </#if>
    }

    <#if !restControllerStyle>
    @ResponseBody
    </#if>
    @PostMapping(value = "/insert${entity}")
    <#if swagger2>
    @ApiOperation(value = "新增${table.comment!}的数据", tags = "${table.comment!}")
    @ApiResponses({
        <#if openForm>
        @ApiResponse(message = "新增${table.comment!}的数据", code = 200, response = ${InsertForm.fileName}.class)
        <#else>
        @ApiResponse(message = "新增${table.comment!}的数据", code = 200, response = ${Model.fileName}.class)
        </#if>
    })
    </#if>
    <#if openForm>
    public Object insert${entity}(${InsertForm.fileName} record){
    <#else>
    public Object insert${entity}(${Model.fileName} record){
    </#if>
        <#if openMybaitPlus>
        <#if springCloud>
        return ${Remote.fileName?uncap_first}.insert${entity}(record);
        <#else>
        ${Service.fileName?uncap_first}.save(record);
        return record;
        </#if>
        <#else>
        <#if springCloud>
        return ${Remote.fileName?uncap_first}.insert${entity}(record);
        <#else>
        return ${Service.fileName?uncap_first}.insert${entity}(record);
        </#if>
        </#if>
    }

    <#if !restControllerStyle>
    @ResponseBody
    </#if>
    @PostMapping(value = "/update${entity}")
    <#if swagger2>
    @ApiOperation(value = "更新${table.comment!}的数据", tags = "${table.comment!}")
    @ApiResponses({
        <#if openForm>
        @ApiResponse(message = "更新${table.comment!}的数据", code = 200, response = ${EditForm.fileName}.class)
        <#else>
        @ApiResponse(message = "更新${table.comment!}的数据", code = 200, response = ${Model.fileName}.class)
        </#if>
    })
    </#if>
    <#if openForm>
    public Object insert${entity}(${EditForm.fileName} record){
    <#else>
    public Object update${entity}(${Model.fileName} record){
    </#if>
        <#if openMybaitPlus>
        <#if springCloud>
        return ${Remote.fileName?uncap_first}.update${entity}(record);
        <#else>
        ${Service.fileName?uncap_first}.updateById(record);
        return record;
        </#if>
        <#else>
        <#if springCloud>
        return ${Remote.fileName?uncap_first}.update${entity}(record);
        <#else>
        return ${Service.fileName?uncap_first}.update${entity}(record);
        </#if>
        </#if>
    }

    <#if !restControllerStyle>
    @ResponseBody
    </#if>
    @PostMapping(value = "/deleteById")
    <#if swagger2>
    @ApiOperation(value = "删除一行${table.comment!}的数据", tags = "${table.comment!}")
    @ApiResponses({
        @ApiResponse(message = "删除一行${table.comment!}的数据", code = 200, response = Object.class)
    })
    </#if>
    public Object deleteById(${table.fields[0].propertyType} id){
        <#if openMybaitPlus>
        <#if springCloud>
        return ${Remote.fileName?uncap_first}.deleteById(id);
        <#else>
        return ${Service.fileName?uncap_first}.removeById(id);
        </#if>
        <#else>
        <#if springCloud>
        return ${Remote.fileName?uncap_first}.deleteById(id);
        <#else>
        return ${Service.fileName?uncap_first}.deleteById(id);
        </#if>
        </#if>
    }
}
