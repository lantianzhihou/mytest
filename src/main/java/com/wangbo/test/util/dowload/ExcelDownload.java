package com.wangbo.test.util.dowload;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.springframework.util.CollectionUtils;

public class ExcelDownload {

    @SuppressWarnings("unchecked")
    public void exportRedressInfos(List<Integer> baseIds, HttpServletResponse response)
            throws Exception {
        List<List<Object>> datas = new ArrayList<List<Object>>();
        List<Object> row0 = new ArrayList<Object>();
        row0.add("姓名");
        row0.add("性别");
        row0.add("罪名");
        row0.add("入矫日期");
        row0.add("解矫日期");
        row0.add("矫正类型");
        datas.add(row0);

        // List<RedressBase> baseInfos = baseMapper.getRecordsByIds(baseIds);
        List<String> baseInfos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(baseInfos)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Map<String, Object> dictMap = getALlDict();
            Map<String, String> sexList = (Map<String, String>) dictMap.get("性别");
            Map<String, String> rectificatoryTypeList = (Map<String, String>) dictMap.get("矫正类型");

            for (String baseInfo : baseInfos) {
                List<Object> row = new ArrayList<Object>();
                String name = "name";
                row.add(name == null ? "" : name);
                String sex = sexList.get("sex");
                row.add(sex == null ? "" : sex);
                String offenceName = "offenceName";
                row.add(offenceName == null ? "" : offenceName);
                Date startTime = new Date();
                row.add(startTime == null ? "" : sdf.format(startTime));
                Date endTime = Date
                        .from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
                row.add(endTime == null ? "" : sdf.format(endTime));
                String rectificatoryType = rectificatoryTypeList.get("rectificatoryType");
                row.add(rectificatoryType == null ? "" : rectificatoryType);
                datas.add(row);
            }
        }
        exportExcel(response, datas, "社区矫正人员信息列表");
    }

    private void exportExcel(HttpServletResponse response, List<List<Object>> datas,
            String fileName) throws Exception {
        OutputStream out = null;
        response.setContentType("octets/stream");
        try {
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xls");
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("导出失败，请稍后重试!");
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
        cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
        cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
        for (int i = 0; i < datas.size(); ++i) {
            HSSFRow row = sheet.createRow(i);
            List<Object> rows = datas.get(i);
            for (int j = 0; j < rows.size(); ++j) {
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(rows.get(j).toString());
            }
        }
        try {
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, Object> getALlDict() {
        return new HashMap<>();
    }

}
