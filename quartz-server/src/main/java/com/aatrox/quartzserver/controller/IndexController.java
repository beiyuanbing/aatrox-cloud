package com.aatrox.quartzserver.controller;

import com.aatrox.quartzserver.model.AppQuartz;
import com.aatrox.quartzserver.service.AppQuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * @author yasuo
 */
@Controller
public class IndexController {

    @Autowired
    private AppQuartzService appQuartzService;

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(Model model) {
        List<AppQuartz> jobList = appQuartzService.selectAppQuartzs(null);
        model.addAttribute("jobs", jobList);
        return "index";
    }

}
