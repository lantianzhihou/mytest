package com.wangbo.test.util;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.util.CollectionUtils;

/**
 * 用于excel导出(可用于普通导出或者选择性导出或者字段需要格式转化例如日期格式转化或者类里有别的类等导出)，
 * 
 * @author 0380009081
 *
 * @param <T>
 */
public class MyExcelExportOutHelper<T> {
	/**
	 * 表格
	 */
	Workbook excel = new SXSSFWorkbook();
	/**
	 * 表头及其对应的实体类里属性名和表头名，例如b,b.c,b.c.name
	 */
	Map<String, String> header = new LinkedHashMap<String, String>();
	/**
	 * 要导出的数据
	 */
	List<List<String>> datas = new ArrayList<List<String>>();
	/**
	 * 需要进行数据转换的属性名及其转化函数式接口，例如需要日期转化 Function<Object,String> function = s->new
	 * SimpleDateFormat("yyyy-MM-dd").format(s); functions存在时必须要header
	 */
	Map<String, Function<Object, String>> functions = new HashMap<>();
	
	/**
	 * 异常策略，默认为0遇到异常继续下一个，不抛出也不打印信息；为1抛出异常
	 */
	int exceptionStrategy = 0 ;

	public MyExcelExportOutHelper() {
	}

	/**
	 * 构造函数
	 * 
	 * @param datas 数据集
	 */
	@SuppressWarnings("unchecked")
	public MyExcelExportOutHelper(List<T> datas) {
		if (!CollectionUtils.isEmpty(datas) && datas.get(0) instanceof Collection) {
			this.datas = (List<List<String>>) datas;
		} else {
			setTDatas(datas);
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param header 表头
	 * @param datas  数据集
	 */
	@SuppressWarnings("unchecked")
	public MyExcelExportOutHelper(Map<String, String> header, List<T> datas) {
		this.header = header;
		if (!CollectionUtils.isEmpty(datas) && datas.get(0) instanceof Collection) {
			this.datas = (List<List<String>>) datas;
		} else {
			setTDatas(datas);
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param header    表头
	 * @param datas     数据集
	 * @param functions 转化函数功能集
	 */
	@SuppressWarnings("unchecked")
	public MyExcelExportOutHelper(Map<String, String> header, List<T> datas,
			Map<String, Function<Object, String>> functions) {
		this.header = header;
		this.functions = functions;
		if (!CollectionUtils.isEmpty(datas) && datas.get(0) instanceof Collection) {
			this.datas = (List<List<String>>) datas;
		} else {
			setTDatas(datas);
		}
	}
	

	void setTDatas(List<T> datas) {
		if (!CollectionUtils.isEmpty(header) && !CollectionUtils.isEmpty(datas)) {
			for (int i = 0; i < datas.size(); i++) {
				ArrayList<String> data = new ArrayList<String>();
				T dT = datas.get(i);
				for (Map.Entry<String, String> entry : header.entrySet()) {
					String name = entry.getKey();
					Object value = null;
					try {
						value = getDeclaredFieldValue(dT, name);
						value = getDeclaredConvertValue(name, value);
					} catch (NoSuchFieldException | SecurityException | IllegalArgumentException
							| IllegalAccessException e) {
						if(exceptionStrategy!=0) e.printStackTrace();
					}
					data.add(value == null ? "" : String.valueOf(value));
				}
				this.datas.add(data);
			}
		} else if (!CollectionUtils.isEmpty(datas)) {
			for (T da : datas) {
				ArrayList<String> data = new ArrayList<String>();
				Field field[] = da.getClass().getDeclaredFields();
				for (Field f : field) {
					f.setAccessible(true);
					Object value = null;
					try {
						value = getDeclaredConvertValue(f.getName(), f.get(da));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						if(exceptionStrategy!=0) e.printStackTrace();
					}
					data.add(value == null ? "" : String.valueOf(value));
				}
				this.datas.add(data);
			}
		}
	}

	/**
	 * 获得表格，用来修改样式
	 * 
	 * @return
	 */
	public Workbook getExcel() {
		return excel;
	}
	/**
	 * 设置异常策略，默认为0遇到异常继续下一个，不抛出也不打印信息；为1抛出异常适用于调试
	 * @param strategy
	 */
	public void setExceptionStrategy(int strategy) {
		exceptionStrategy=strategy;
	}
	
	private Object getDeclaredFieldValue(Object clazz, String name)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		String[] arr = name.split("\\.");
		for (String string : arr) {
			Field field = clazz.getClass().getDeclaredField(string);
			field.setAccessible(true);
			clazz = field.get(clazz);
			if (clazz == null) {
				break;
			}
		}
		return clazz;
	}

	private String getDeclaredConvertValue(String name, Object value) {
		Function<Object, String> function = null;
		if (!CollectionUtils.isEmpty(functions) && (function = functions.get(name)) != null) {
			value = function.apply(value);
		}
		return value == null ? "" : String.valueOf(value);
	}

	/**
	 * 导出
	 * 
	 * @param fileName      文件名
	 * @param includeHeader 是否包含表头
	 * @param includeOrder  是否包含编号
	 * @param response
	 * @throws Exception
	 */
	public void export(String fileName, boolean includeHeader, boolean includeOrder, HttpServletResponse response)
			throws Exception {
		Sheet sheet = this.excel.createSheet(fileName);
		int rowNum = 0;
		if (includeHeader) {
			Row row = sheet.createRow(rowNum++);
			int hj = 0;
			if (includeOrder) {
				Cell cell = row.createCell(hj++);
				cell.setCellValue("序号");
			}
			for (Map.Entry<String, String> entry : header.entrySet()) {
				Cell cell = row.createCell(hj++);
				cell.setCellValue(entry.getValue());
			}
		}
		if (!CollectionUtils.isEmpty(datas)) {
			for (int i = 0; i < datas.size(); i++) {
				Row row = sheet.createRow(rowNum++);
				List<String> rows = datas.get(i);
				int j = 0;
				if (includeOrder) {
					Cell cell = row.createCell(j);
					cell.setCellValue(i + 1);
				}
				while (j++ < rows.size()) {
					Cell cell = row.createCell(j-1);
					cell.setCellValue(rows.get(j - 1));
				}
			}
		} else {
			throw new Exception("待导出数据为空");
		}
		if (this.excel != null) {
			ServletOutputStream os = null;
			try {
				response.setContentType("application/octet-stream");
				response.setCharacterEncoding("utf-8");
				response.setHeader("Content-disposition",
						"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
				os = response.getOutputStream();
				this.excel.write(os);
				os.flush();
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("导出失败！");
			} finally {
				if (os != null) {
					os.close();
				}
			}
		}
	}
	/**
	 * 将b的相同名称的属性赋值给a
	 * @param a
	 * @param b
	 */
	@SuppressWarnings("unused")
	private void convertModel(Object a,Object b) {
		Field fields[]=a.getClass().getDeclaredFields();
		for(Field f:fields) {
			String name=f.getName();
			try {
			Field field = b.getClass().getDeclaredField(name);
			if(field!=null) {
				field.setAccessible(true);
				f.setAccessible(true);
				field.set(b, f.get(a));
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

/**
 * 使用示例
 */
//public void list(HttpServletResponse res,Integer num){
//	long start = System.currentTimeMillis();
//	List<TableEntity> tabs = new ArrayList<TableEntity>();
//	for(int i = 0 ; i < num ; i++) {
//		TableEntity tables = new TableEntity();
//		tables.setClassname("类名"+i);
//		tables.setClassName("leiming"+i);
//		tables.setComments("评论"+i);
//		ColumnEntity pk = new ColumnEntity();
//		pk.setAttrname("属性名"+i);
//		pk.setAttrName("shuxingming"+i);
//		pk.setDataType("数据类型"+i);
//		pk.setExtra("格外"+i);
//		pk.setComments("comments"+i);
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DAY_OF_YEAR, i);
//		pk.setDate(cal.getTime());
//		tables.setPk(pk );
//		tabs.add(tables);
//	}
//	Map<String, String> header = new LinkedHashMap<String, String>();
//	header.put("classname", "类名");
//	header.put("comments", "评论");
//	header.put("pk.attrname", "属性名");
//	header.put("pk.dataType", "数据类型");
//	header.put("pk.date", "日期");
//	header.put("className", "lm");
//	Map<String, Function<Object, String>> functions = new HashMap<String, Function<Object, String>>();
//	functions.put("pk.date", s->new SimpleDateFormat("yyyy-MM-dd").format(s));
//	MyExcelExportOutHelper<TableEntity> datas = new MyExcelExportOutHelper<TableEntity>(header,tabs,functions);
//	try {
//		datas.export("测试", false, true, res);
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	long end = System.currentTimeMillis();
//	System.out.println((end-start));
//}