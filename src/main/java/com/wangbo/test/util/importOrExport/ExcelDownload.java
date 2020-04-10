package com.wangbo.test.util.importOrExport;

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
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

public class ExcelDownload {
	// excel自定义模板下载 start
	final String[] UNIT_EXCEL_HEADS = { "*单位名称", "*信用代码", "单位地址", "法人代表", "联系方式", "经营范围" };
	
	public void downloadExcelTemp(HttpServletResponse response) throws Exception {
		String excelName = "单位数据导入模板";
		Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
		String[] example = null;
		Workbook wb = excelEmployerModel(excelName, UNIT_EXCEL_HEADS, map, example, null);
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		try {
			String fileName = new String(excelName.getBytes("gb2312"), "ISO8859-1") + ".xlsx";
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("下载模板失败，请稍后再试！");
		}
	}
	
	/**
	 * 导出excel导入模板文档，格式为xlsx
	 * 
	 * @param excelName 导出excel模板名称，不带后缀
	 * @param headers 导出excel的列名
	 * @param example 可选，导出excel的第一行示例
     * @param checkMap 可选，导出下拉框
	 * @param list 可选，导出excel的数据
	 */
	public Workbook excelEmployerModel(String excelName, String[] headers,
	        Map<Integer, List<String>> checkMap, String example[], List<Map<Integer, String>> list) {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(excelName);
		XSSFRow hrow = sheet.createRow(0);
		XSSFCellStyle hcs = wb.createCellStyle();
		hcs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		hcs.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		for (int i = 0; i < headers.length; i++) {
			XSSFCellStyle textStyle = wb.createCellStyle();
			textStyle.setDataFormat(wb.createDataFormat().getFormat("@"));
			sheet.setDefaultColumnStyle(i, textStyle);
			XSSFCell cell = hrow.createCell(i);
			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
			cell.setCellValue(text);
			cell.setCellStyle(hcs);
			// 下拉选择框
			List<String> checkBox = checkMap.get(i);
			if (checkBox != null) {
				DataValidationHelper helper = sheet.getDataValidationHelper();
				CellRangeAddressList regions = new CellRangeAddressList(0, 9999, i, i);
				DataValidationConstraint constraint = helper.createExplicitListConstraint((String[]) checkBox.toArray((new String[checkBox.size()])));
				DataValidation dataValidation = helper.createValidation(constraint, regions);
				sheet.addValidationData(dataValidation);
			}
		}
		if (null != example) {
			XSSFRow erow = sheet.createRow(1);
			XSSFCellStyle ecs = wb.createCellStyle();
			ecs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			ecs.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			ecs.setDataFormat(wb.createDataFormat().getFormat("@"));
			for (int i = 0; i < example.length; i++) {
				XSSFCell cell = erow.createCell(i);
				XSSFRichTextString text = new XSSFRichTextString(example[i]);
				cell.setCellValue(text);
				cell.setCellStyle(ecs);
			}
		}
		if (null != list && list.size() > 0) {
			int start = null == example ? 1 : 2;
			for (Map<Integer, String> map : list) {
				XSSFRow row = sheet.createRow(start);
				for (int i = 0; i < headers.length; i++) {
					XSSFCell cell = row.createCell(i);
					XSSFRichTextString text = new XSSFRichTextString(null == map.get(i) ? "" : map.get(i));
					cell.setCellValue(text);
				}
				start++;
			}
		}
		 //设置自动列宽
		sheet.setColumnWidth(0,sheet.getColumnWidth(0)*2);
		sheet.setColumnWidth(1,sheet.getColumnWidth(1)*2);
		sheet.setColumnWidth(2,sheet.getColumnWidth(2)*4);
		sheet.setColumnWidth(3,sheet.getColumnWidth(3)*2);
		sheet.setColumnWidth(4,sheet.getColumnWidth(4)*4);
		sheet.setColumnWidth(5,sheet.getColumnWidth(5)*6);
		return wb;
	}
	// excel自定义模板下载 end
	
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
