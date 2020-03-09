package com.aatrox.autocode.feignmapper.helper;

import com.aatrox.autocode.base.helper.BaseHelper;
import com.aatrox.autocode.feignmapper.param.FeignDefinition;
import com.aatrox.autocode.feignmapper.scanner.ClassPathScanner;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FeignToDocxHelper extends BaseHelper {
    private String packageRoot;
    public static final String CONF_DIR_PATH;

    public FeignToDocxHelper(String packageRoot) {
        this.packageRoot = packageRoot;
    }

    public void doMapper() throws Exception {
        List<FeignDefinition> feignDefinitions = ClassPathScanner.getInstance().doScan(this.packageRoot);
        XWPFTable apiTemplateTable = this.getApiTemplateTable();
        XWPFTable modelTemplateTable = this.getModelTemplateTable();
        Resource resource = new ClassPathResource("feignToDocx/template.docx");
        XWPFDocument document = new XWPFDocument(resource.getInputStream());
        this.setStyle(document);
        Iterator var6 = feignDefinitions.iterator();

        while (var6.hasNext()) {
            FeignDefinition fegin = (FeignDefinition) var6.next();
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setStyle("1");
            XWPFRun run = paragraph.createRun();
            run.setText(fegin.getClassName());
            Iterator var10 = fegin.getMethodDefinitions().iterator();

            while (var10.hasNext()) {
                FeignDefinition.FeignMethodDefinition methodDef = (FeignDefinition.FeignMethodDefinition) var10.next();
                XWPFParagraph paragraphFeign = document.createParagraph();
                paragraphFeign.setStyle("2");
                XWPFRun runFeign = paragraphFeign.createRun();
                runFeign.setText(methodDef.getMethodName());
                XWPFTable feginMethod = this.copyTable(apiTemplateTable, document.createTable());
                XWPFTableCell var10000 = ((XWPFTableRow) feginMethod.getRows().get(0)).getCell(1);
                String var10001 = fegin.getClassName();
                var10000.setText(var10001 + "." + methodDef.getMethodName());
                ((XWPFTableRow) feginMethod.getRows().get(1)).getCell(1).setText(methodDef.getDesc());
                ((XWPFTableRow) feginMethod.getRows().get(2)).getCell(1).setText(methodDef.getParameterType());
                var10000 = ((XWPFTableRow) feginMethod.getRows().get(3)).getCell(1);
                var10001 = methodDef.getGenericType();
                var10000.setText(var10001 + "<" + methodDef.getReturnType() + ">");
                ((XWPFTableRow) feginMethod.getRows().get(4)).getCell(1).setText(methodDef.getReturnTypeDesc());
                document.createParagraph();
                XWPFTable parameterType = this.copyTable(modelTemplateTable, document.createTable());
                ((XWPFTableRow) parameterType.getRows().get(0)).getCell(1).setText(methodDef.getParameterType());
                ((XWPFTableRow) parameterType.getRows().get(1)).getCell(1).setText(methodDef.getParameterTypeDesc());
                this.addPropertyInfo(parameterType, methodDef.getParameterMap());
                document.createParagraph();
                XWPFTable returnType = this.copyTable(modelTemplateTable, document.createTable());
                ((XWPFTableRow) returnType.getRows().get(0)).getCell(1).setText(methodDef.getReturnType());
                ((XWPFTableRow) returnType.getRows().get(1)).getCell(1).setText(methodDef.getReturnTypeDesc());
                this.addPropertyInfo(returnType, methodDef.getReturnMap());
            }

            document.createParagraph();
        }

        this.makeFile(document);
        System.out.println("文档生成...DONE");
        System.out.println();
        System.out.println("完成操作...");
        System.out.println("请到以下地址复制文件..." + CONF_DIR_PATH);
        super.openRoot(CONF_DIR_PATH);
    }

    private void setStyle(XWPFDocument document) {
        try {
            Resource resource = new ClassPathResource("feignToDocx/style.docx");
            XWPFDocument style = new XWPFDocument(resource.getInputStream());
            CTStyles wordStyles = style.getStyle();
            XWPFStyles newStyles = document.createStyles();
            newStyles.setStyles(wordStyles);
        } catch (Exception var6) {
        }

    }

    private void addPropertyInfo(XWPFTable table, LinkedHashMap<String, String> propertyMap) {
        boolean flag = false;
        XWPFTableRow styleRow = table.getRow(table.getRows().size() - 1);
        Iterator var5 = propertyMap.entrySet().iterator();

        while (var5.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) var5.next();
            XWPFTableRow targetRow;
            if (flag) {
                targetRow = table.createRow();
                targetRow.getCtRow().setTrPr(styleRow.getCtRow().getTrPr());
            } else {
                targetRow = styleRow;
                flag = true;
            }

            targetRow.getCell(0).setText((String) entry.getKey());
            targetRow.getCell(0).getCTTc().setTcPr(styleRow.getCell(0).getCTTc().getTcPr());
            targetRow.getCell(1).setText((String) entry.getValue());
            targetRow.getCell(1).getCTTc().setTcPr(styleRow.getCell(1).getCTTc().getTcPr());
        }

    }

    private XWPFTable copyTable(XWPFTable source, XWPFTable target) {
        target.removeRow(0);
        Iterator var3 = source.getRows().iterator();

        while (var3.hasNext()) {
            XWPFTableRow sourceRow = (XWPFTableRow) var3.next();
            XWPFTableRow targetRow = new XWPFTableRow(target.getCTTbl().addNewTr(), target);
            target.getRows().add(targetRow);
            List<XWPFTableCell> cellList = sourceRow.getTableCells();
            targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());

            for (int i = 0; i < cellList.size(); ++i) {
                XWPFTableCell sourceCell = (XWPFTableCell) cellList.get(i);
                XWPFTableCell targetCell = targetRow.createCell();
                targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
                targetCell.setText(sourceCell.getText());
            }
        }

        return target;
    }

    private XWPFTable getApiTemplateTable() throws IOException {
        Resource resource = new ClassPathResource("feignToDocx/apiTemplate.docx");
        XWPFDocument document = new XWPFDocument(resource.getInputStream());
        return (XWPFTable) document.getTables().get(0);
    }

    private XWPFTable getModelTemplateTable() throws IOException {
        Resource resource = new ClassPathResource("feignToDocx/modelTemplate.docx");
        XWPFDocument document = new XWPFDocument(resource.getInputStream());
        return (XWPFTable) document.getTables().get(0);
    }

    private void makeFile(XWPFDocument document) throws IOException {
        File root = new File(CONF_DIR_PATH);
        if (!root.exists()) {
            root.mkdir();
        }

        File file = new File(CONF_DIR_PATH + File.separator + "文档描述.docx");
        if (file.exists()) {
            file.delete();
        }

        file.createNewFile();
        document.write(new FileOutputStream(file));
    }

    @Override
    public String toString() {
        return "FeignToDocxHelper{packageRoot='" + this.packageRoot + "'}";
    }

    static {
        String var10000 = System.getProperty("user.home");
        CONF_DIR_PATH = var10000 + File.separator + "byb.feignTodocx";
    }
}

