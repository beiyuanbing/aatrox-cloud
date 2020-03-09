package com.aatrox.web.base.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.spring.web.DocumentationCache;

import javax.annotation.Resource;

/**
 * @author aatrox
 * @desc swagger相关的扩展
 * @date 2020/3/2
 */
@Lazy
@Controller
@RequestMapping(
        value = {"/v2/api-docs"},
        produces = {"application/json;charset=UTF-8"}
)
@ApiIgnore
public class SwaggerExpandController extends BaseController{
    @Resource
    private DocumentationCache documentationCache;

    public SwaggerExpandController() {
    }

    @GetMapping({"api-group"})
    public String apiGroup() {
        return this.returnSuccessInfo(this.documentationCache.all().keySet());
    }
}
