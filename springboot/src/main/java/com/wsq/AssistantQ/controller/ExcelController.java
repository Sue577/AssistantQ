package com.wsq.AssistantQ.controller;

import com.wsq.AssistantQ.model.AlumniInformationModel;
import com.wsq.AssistantQ.model.PracticeInforModel;
import com.wsq.AssistantQ.service.AlumniInformationService;
import com.wsq.AssistantQ.service.PracticeInforService;
import com.wsq.AssistantQ.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author CYann
 * @date 2018-04-15 17:49
 */
@RestController
@RequestMapping(value = "/excel")
public class ExcelController {
    @Autowired
    AlumniInformationService alumniInformationService;
    @Autowired
    PracticeInforService practiceInforService;

    //创建表头
    private void createTitle(HSSFWorkbook workbook,HSSFSheet sheet){
        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(1,12*256);
        sheet.setColumnWidth(3,17*256);

        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);

        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("学号");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("公司名称");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("公司地址");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("行业");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("职位");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("薪资");
        cell.setCellStyle(style);

/*        cell = row.createCell(6);
        cell.setCellValue("创建时间");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("更新时间");
        cell.setCellStyle(style);

        cell = row.createCell(8);
        cell.setCellValue("删除时间");
        cell.setCellStyle(style);*/
    }

    //生成校友信息表excel
    @PostMapping(value = "/alumniexcel")
    public Result alumniExcel(HttpServletResponse response) throws Exception{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("校友信息统计表");
        createTitle(workbook,sheet);
        List<AlumniInformationModel> rows = alumniInformationService.findAll();

        //设置日期格式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

        // System.out.println(style);

        //新增数据行，并且设置单元格数据
        int rowNum=1;
        for(AlumniInformationModel alumniInformationModel:rows){
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(alumniInformationModel.getStuNumber());
            row.createCell(1).setCellValue(alumniInformationModel.getCompany());
            row.createCell(2).setCellValue(alumniInformationModel.getCompanyAddress());
            row.createCell(3).setCellValue(alumniInformationModel.getIndustry());
            row.createCell(4).setCellValue(alumniInformationModel.getOccupation());
            row.createCell(5).setCellValue(alumniInformationModel.getSalary());
 /*           row.createCell(6).setCellValue(sdf.parse(String.valueOf(alumniInformationModel.getCreatTime())));
            row.createCell(7).setCellValue((alumniInformationModel.getUpdateTime()).toString());
            row.createCell(8).setCellValue((alumniInformationModel.getDelTime()).toString());*/
            HSSFCell cell = row.createCell(6);
            rowNum++;
        }
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

        int year = c.get(Calendar.YEAR);

        int month = c.get(Calendar.MONTH) + 1;

        int date = c.get(Calendar.DATE);

        int hour = c.get(Calendar.HOUR_OF_DAY);

        int minute = c.get(Calendar.MINUTE);


        String fileName = "src/main/resources/static/ExportInformation"+year+month+date+hour+minute+".xls";

        //生成excel文件
        buildExcelFile(fileName, workbook);

        //浏览器下载excel
        buildExcelDocument(fileName,workbook,response);

        return Result.success();
    }


    //生成实习生信息表excel
    @PostMapping(value = "/practiceexcel")
    public String practiceExcel(HttpServletResponse response) throws Exception{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("实习生信息统计表");
        createTitle(workbook,sheet);
        List<PracticeInforModel> rows = practiceInforService.findALLPracticeInfor();

        //设置日期格式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

        //新增数据行，并且设置单元格数据
        int rowNum=1;
        for(PracticeInforModel practiceInforModel:rows){
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(practiceInforModel.getStuNumber());
            row.createCell(1).setCellValue(practiceInforModel.getCompany());
            row.createCell(2).setCellValue(practiceInforModel.getCompanyAddress());
            row.createCell(3).setCellValue(practiceInforModel.getIndustry());
            row.createCell(4).setCellValue(practiceInforModel.getOccupation());
            row.createCell(5).setCellValue(practiceInforModel.getSalary());
            /*row.createCell(6).setCellValue(practiceInforModel.getCreatTime());
            row.createCell(7).setCellValue(practiceInforModel.getUpdateTime());
            row.createCell(8).setCellValue(practiceInforModel.getDelTime());*/
            HSSFCell cell = row.createCell(6);
            rowNum++;
        }

        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

        int year = c.get(Calendar.YEAR);

        int month = c.get(Calendar.MONTH);

        int date = c.get(Calendar.DATE);

        int hour = c.get(Calendar.HOUR_OF_DAY);

        int minute = c.get(Calendar.MINUTE);


        String fileName = "src/main/resources/static/ExportPractice"+year+month+date+hour+minute+".xls";

        //生成excel文件
        buildExcelFile(fileName, workbook);

        //浏览器下载excel
        buildExcelDocument(fileName,workbook,response);

        return "download excel";
    }

    //生成excel文件
    protected void buildExcelFile(String filename,HSSFWorkbook workbook) throws Exception{
        // File savefile = new File("D:/excel/");
        FileOutputStream fos = new FileOutputStream(filename,true);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    //浏览器下载excel
    protected void buildExcelDocument(String filename,HSSFWorkbook workbook,HttpServletResponse response) throws Exception{
        response.setContentType("application/x-download;charset=UTF-8");
        //response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(filename, "utf-8"));
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

}
