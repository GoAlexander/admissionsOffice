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
	
	private static String[] columnNamesAdmPlan = {"Код специальности", "Форма обучения", "Конкурсная группа", "Целевая организация", "Стандарт образования", "Количество мест"};
	
	public static void outExcel(String tableName) throws Exception {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(tableName);

		int rowNum = 0;
		Row row = sheet.createRow(rowNum);
		for(int i = 0; i < columnNamesAdmPlan.length; i++)
			row.createCell(i).setCellValue(columnNamesAdmPlan[i]);

		String[][] dataPlan = ModelDBConnection.getAllFromTable(tableName);

		for (int i = 0; i < dataPlan.length; i++) 
			writeToRow(sheet, ++rowNum, dataPlan[i]);

		String path = "./files/" + tableName + "File.xls";
		File file = new File(path);
		if (file.exists())
			file.delete();
		file.createNewFile();

		workbook.write(new FileOutputStream(file));
	}
	
	private static void writeToRow(HSSFSheet sheet, int rowNum, String[] dataRow){
		Row row = sheet.createRow(rowNum);

		for(int i = 0; i < dataRow.length; i++)
			row.createCell(i).setCellValue(dataRow[i]);
	}
}
