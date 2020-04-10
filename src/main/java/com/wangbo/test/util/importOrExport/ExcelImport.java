package com.wangbo.test.util.importOrExport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelImport {

	final String[] UNIT_EXCEL_HEADS = { "*单位名称", "*信用代码", "单位地址", "法人代表", "联系方式", "经营范围" };
	
	public List<UnitManageInfo> analysisExcelToUnitList(MultipartFile unitInfoFile) throws Exception {
		List<UnitManageInfo> list = new ArrayList<>();
		Workbook wb = null;
		try {
			if (unitInfoFile.getOriginalFilename().endsWith(".xls")) {
				wb = new HSSFWorkbook(unitInfoFile.getInputStream());
			} else {
				wb = new XSSFWorkbook(unitInfoFile.getInputStream());
			}
			Sheet sheet = wb.getSheetAt(0);
			
			if(sheet.getLastRowNum()!=-1) {
				if (sheet.getRow(0).getLastCellNum() > UNIT_EXCEL_HEADS.length) {
					throw new Exception("请导入正确的模板文件");
				}
				for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
					if (!sheet.getRow(0).getCell(i).getStringCellValue().equals(UNIT_EXCEL_HEADS[i])) {
						throw new Exception("请导入正确的模板文件");
					}
				}
			}
			//int rows = sheet.getPhysicalNumberOfRows();
			int rows = sheet.getLastRowNum()+1;
			// 第一行为标题，从第二行开始导入
			for (int i = 1; i < rows; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					boolean badUnitInfo = false;
					UnitManageInfo unitInfo = new UnitManageInfo();
					for (int j = 0; j < UNIT_EXCEL_HEADS.length; j++) {
						if (badUnitInfo) continue;
						
						Cell cell = row.getCell(j);
						switch (j) {
						case 0:
							// 强转字符串，读取
							if (cell != null) {
								cell.setCellType(CellType.STRING);
								String unitName = cell.getStringCellValue();
								if (unitName != null && unitName.trim().length() > 0
								        && unitName.trim().length() <= 30) {
									unitInfo.setUnitName(unitName.trim());
								} else {
									badUnitInfo = true;
								}
							} 
							break;
						case 1:
							// 强转字符串，读取
							if (cell != null) {
								cell.setCellType(CellType.STRING);
								String creditCode = cell.getStringCellValue();
								if (creditCode != null && creditCode.trim().length() > 0
								        && creditCode.trim().length() <= 18) {
									unitInfo.setCreditCode(creditCode.trim());
								} else {
									badUnitInfo = true;
								}
							}
							break;
						default:
							break;
						}
					}
					if (!badUnitInfo)
						list.add(unitInfo);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("单位数据导入模板 excel格式不正确，导入失败");
		}finally {
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			    throw new Exception(e.getMessage());
			}
		}
		if(list.isEmpty()) {
			throw new Exception("单位数据导入模板 excel中除表头行，无数据");
		}
		return list;
	}
	
	public class UnitManageInfo {
		private Integer unitId;

		private String unitName;

		private String creditCode;

		private String unitAddress;

		private String legalPerson;

		private String telephone;

		private String businessScope;

		private Date updateTime;

		public Integer getUnitId() {
			return unitId;
		}

		public void setUnitId(Integer unitId) {
			this.unitId = unitId;
		}

		public String getUnitName() {
			return unitName;
		}

		public void setUnitName(String unitName) {
			this.unitName = unitName == null ? null : unitName.trim();
		}

		public String getCreditCode() {
			return creditCode;
		}

		public void setCreditCode(String creditCode) {
			this.creditCode = creditCode == null ? null : creditCode.trim();
		}

		public String getUnitAddress() {
			return unitAddress;
		}

		public void setUnitAddress(String unitAddress) {
			this.unitAddress = unitAddress == null ? null : unitAddress.trim();
		}

		public String getLegalPerson() {
			return legalPerson;
		}

		public void setLegalPerson(String legalPerson) {
			this.legalPerson = legalPerson == null ? null : legalPerson.trim();
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone == null ? null : telephone.trim();
		}

		public String getBusinessScope() {
			return businessScope;
		}

		public void setBusinessScope(String businessScope) {
			this.businessScope = businessScope == null ? null : businessScope.trim();
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

	}
	
}
