package outputDoc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import backend.ModelDBConnection;

public class AdmissionPlanOutExcel {

	public static void planOutExcel() throws Exception {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Admission plan");

		int rowNum = 0;
		Row row = sheet.createRow(rowNum);
		row.createCell(0).setCellValue("Код специальности");
		row.createCell(1).setCellValue("Форма обучения");
		row.createCell(2).setCellValue("Конкурсная группа");
		row.createCell(3).setCellValue("Целевая организация");
		row.createCell(4).setCellValue("Стандарт образования");
		row.createCell(5).setCellValue("Количество мест");

		String[][] dataPlan = ModelDBConnection.getAllFromTable("AdmissionPlan");

		for (int i = 0; i < dataPlan.length; i++) {
			writeToRow(sheet, ++rowNum, dataPlan[i]);
		}

		String path = "./files/admissionPlanFile.xls";
		File admissioinPlanFile = new File(path);
		if (admissioinPlanFile.exists())
			admissioinPlanFile.delete();
		admissioinPlanFile.createNewFile();

		workbook.write(new FileOutputStream(admissioinPlanFile));
	}
	
	private static void writeToRow(HSSFSheet sheet, int rowNum, String[] dataRow){
		Row row = sheet.createRow(rowNum);

		row.createCell(0).setCellValue(dataRow[0]);
		row.createCell(1).setCellValue(dataRow[1]);
		row.createCell(2).setCellValue(dataRow[2]);
		row.createCell(3).setCellValue(dataRow[3]);
		row.createCell(4).setCellValue(dataRow[4]);
		row.createCell(5).setCellValue(dataRow[5]);
	}
}
