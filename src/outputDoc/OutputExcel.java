package outputDoc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import backend.ModelDBConnection;

public class OutputExcel {
	private static String currentPath = new File("").getAbsolutePath();

	private static String[] colNamesAdmissionPlan = {"Код специальности", "Форма обучения", "Конкурсная группа", "Целевая организация", "Стандарт образования", "Количество мест"};
	private static String[] colNamesAbiturient = {"Идентификатор", "Фамилия", "Имя", "Отчество"};
	private static String[] columnNames;

	public static void outExcel(String tableName) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(tableName);
		
		int rowNum = 0;
		Row row = sheet.createRow(rowNum);
		
		switch(tableName){
		case "AdmissionPlan":
			columnNames = colNamesAdmissionPlan;
			break;
		case "Abiturient":
			columnNames = colNamesAbiturient;
			break;
		}
		
		for(int i = 0; i < columnNames.length; i++)
			row.createCell(i).setCellValue(columnNames[i]);

		String[][] data = ModelDBConnection.getAllFromTable(tableName);

		for (int i = 0; i < data.length; i++) 
			writeToRow(sheet, ++rowNum, data[i]);

		String path = currentPath + "/files/" + tableName + "File.xls";
		File file = new File(path);
		if (file.exists())
			file.delete();
		file.createNewFile();

		workbook.write(new FileOutputStream(file));
	}
	
	private static void writeToRow(HSSFSheet sheet, int rowNum, String[] dataRow){
		Row row = sheet.createRow(rowNum);

		for(int i = 0; i < columnNames.length; i++)
			row.createCell(i).setCellValue(dataRow[i]);
	}
}
