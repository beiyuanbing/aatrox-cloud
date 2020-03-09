<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${Dao.beanPackage}.${Dao.fileName}">

    <#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

    </#if>
    <#if baseResultMap>
        <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${Model.beanPackage}.${Model.fileName}">
        <#list table.fields as field>
            <#if field.keyFlag><#--生成主键排在第一位-->
                <id column="${field.name}" property="${field.propertyName}"/>
            </#if>
        </#list>
        <#list table.commonFields as field><#--生成公共字段 -->
            <result column="${field.name}" property="${field.propertyName}"/>
        </#list>
        <#list table.fields as field>
            <#if !field.keyFlag><#--生成普通字段 -->
                <result column="${field.name}" property="${field.propertyName}"/>
            </#if>
        </#list>
    </resultMap>

    </#if>
    <#if baseColumnList>
        <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        <#list table.commonFields as field>
            ${field.name},
        </#list>
        ${table.fieldNames}
    </sql>

    </#if>
    <#if !openMybaitPlus>

    <select id="selectById" type="${table.fields[0].propertyType?lower_case}"
            <#if baseResultMap>resultMap="BaseResultMap"<#else > resultType="${Model.beanPackage}.${Model.fileName}"</#if >>
        select
        <#if baseResultMap>*<#else><#list table.fields as field><#if field_has_next>${field.name} as ${field.propertyName},
        <#else>${field.name} as ${field.propertyName}
        </#if></#list></#if>
        from ${table.name}
        where ${table.fields[0].name} =${r"#{"}id${r"}"}
    </select>

    <select id="selectAll"
            <#if baseResultMap>resultMap="BaseResultMap"<#else > resultType="${Model.beanPackage}.${Model.fileName}"</#if >>
        select
        <#if baseResultMap>*<#else><#list table.fields as field><#if field_has_next>${field.name} as ${field.propertyName},
        <#else>${field.name} as ${field.propertyName}
        </#if></#list></#if>
        from ${table.name}
    </select>

    <#if openForm>
    <insert id="insert${entity}" parameterType="${InsertForm.beanPackage}.${InsertForm.fileName}">
    <#else>
    <insert id="insert${entity}" parameterType="${Model.beanPackage}.${Model.fileName}">
    </#if>
        insert into ${table.name}
        (<#list table.fields as field><#if field_has_next>${field.name},<#else>${field.name}</#if></#list>)
        values(<#list table.fields as field><#if field_has_next>${r"#{"}${field.propertyName}${r"}"},<#else>${r"#{"}${field.propertyName}${r"}"}</#if></#list>
        )
    </insert>

    <#if openForm>
    <update id="update${entity}" parameterType="${EditForm.beanPackage}.${EditForm.fileName}">
    <#else>
    <update id="update${entity}" parameterType="${Model.beanPackage}.${Model.fileName}">
    </#if>
        update ${table.name}
        <set>
            <#list table.fields as field>
                <if test="${field.propertyName}!=null">
                    ${field.name}=${r"#{"}${field.propertyName}${r"}"},
                </if>
            </#list>
        </set>
        where ${table.fields[0].name} =${r"#{"}${table.fields[0].propertyName}${r"}"}
    </update>

    <delete id="deleteById" type="${table.fields[0].propertyType?lower_case}">
        delete from ${table.name}
        where ${table.fields[0].name} =${r"#{"}${table.fields[0].propertyName}${r"}"}
    </delete>
    </#if>
</mapper>
