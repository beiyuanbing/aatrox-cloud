package com.aatrox.web.controller;

import com.aatrox.web.base.controller.ExcelController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author aatrox
 * @desc
 * @date 2019/9/2
 */
@Controller
@RequestMapping("/export")
public class ExportController extends ExcelController {

    @RequestMapping("/order")
    public void exportOrder(HttpServletResponse response) {
        try {
            super.excelExport("orderExport",true,null, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
