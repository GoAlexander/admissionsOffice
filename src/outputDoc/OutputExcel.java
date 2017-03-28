package outputDoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import backend.ModelDBConnection;

public class OutputExcel {
	private static String currentPath = new File("").getAbsolutePath();

	private static String[] colNamesAdmissionPlan = { "Код специальности", "Форма обучения", "Конкурсная группа",
			"Целевая организация", "Стандарт образования", "Количество мест" };
	private static String[] colNamesAbiturient = { "Идентификатор", "Фамилия", "Имя", "Отчество" };
	private static String[] columnNames;

	public static void outExcel(String tableName) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(tableName);

		int rowNum = 0;
		XSSFRow row = sheet.createRow(rowNum);

		switch (tableName) {
		case "AdmissionPlan":
			columnNames = colNamesAdmissionPlan;
			break;
		case "Abiturient":
			columnNames = colNamesAbiturient;
			break;
		}

		for (int i = 0; i < columnNames.length; i++)
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

	public static void writeListGroupEntranceTest() throws Exception {

		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(currentPath + "\\Dots\\Списки_групп" + ".xltx"));

		XSSFFont fontForEntranceTestName = workbook.createFont();
		fontForEntranceTestName.setBold(true);
		fontForEntranceTestName.setFontHeight(15);

		XSSFCellStyle styleForCellsWithCenterAlg = workbook.createCellStyle();
		styleForCellsWithCenterAlg.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleForCellsWithCenterAlg.setFont(fontForEntranceTestName);

		XSSFCellStyle styleForCellsWithLeftAlg = workbook.createCellStyle();
		styleForCellsWithLeftAlg.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleForCellsWithLeftAlg.setFont(fontForEntranceTestName);

		XSSFFont fontForNames = workbook.createFont();
		fontForNames.setFontHeight(13);
		XSSFCellStyle styleForNames = workbook.createCellStyle();
		styleForNames.setFont(fontForNames);
		styleForNames.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleForNames.setAlignment(XSSFCellStyle.ALIGN_CENTER);

		String[] entranceTestGroups = ModelDBConnection.getAllGroupEntranceTest();
		int numSheet = 0;

		for (int etg_i = 0; etg_i < entranceTestGroups.length; etg_i++) {
			int rowNum = 0;
			XSSFRow row;

			String[][] abitData = ModelDBConnection.getEntranceTestGroupsAbit(entranceTestGroups[etg_i]);
			if (abitData != null) {

				XSSFSheet sheet = workbook.cloneSheet(0);
				workbook.setSheetName(++numSheet, entranceTestGroups[etg_i]);

				row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue("Список группы № " + entranceTestGroups[etg_i]);
				row.getCell(0).setCellStyle(styleForCellsWithCenterAlg);

				rowNum++;

				for (int i = 0; i < abitData.length; i++) {
					row = sheet.getRow(++rowNum);
					row.getCell(2).setCellValue(abitData[i][0] + " " + abitData[i][1] + " " + abitData[i][2]);
					row.getCell(2).setCellStyle(styleForNames);
					row.getCell(3).setCellValue(row.getCell(1).getRawValue() + "ord" + entranceTestGroups[etg_i]);
					row.getCell(3).setCellStyle(styleForNames);
					row.getCell(4).setCellValue(abitData[i][3] + " " + abitData[i][4]);
					row.getCell(4).setCellStyle(styleForNames);

				}
			}

		}

		workbook.removeSheetAt(0);

		String path = currentPath + "\\files\\Списки_групп" + "_"
				+ (new SimpleDateFormat("dd.MM.yyyy").format(new Date())) + ".xls";
		File file = new File(path);
		if (file.exists())
			file.delete();
		file.createNewFile();

		workbook.write(new FileOutputStream(file));

	}

	public static void writeResultsEntranceTest() throws Exception {

		XSSFWorkbook workbook = new XSSFWorkbook(
				new FileInputStream(currentPath + "\\Dots\\Результаты_Вступительных" + ".xltx"));

		XSSFFont fontForEntranceTestName = workbook.createFont();
		fontForEntranceTestName.setBold(true);
		fontForEntranceTestName.setFontHeight(15);

		XSSFCellStyle styleForCellsWithCenterAlg = workbook.createCellStyle();
		styleForCellsWithCenterAlg.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleForCellsWithCenterAlg.setFont(fontForEntranceTestName);

		XSSFCellStyle styleForCellsWithLeftAlg = workbook.createCellStyle();
		styleForCellsWithLeftAlg.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleForCellsWithLeftAlg.setFont(fontForEntranceTestName);

		XSSFFont fontForNames = workbook.createFont();
		fontForNames.setFontHeight(13);
		XSSFCellStyle styleForNames = workbook.createCellStyle();
		styleForNames.setFont(fontForNames);
		styleForNames.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleForNames.setAlignment(XSSFCellStyle.ALIGN_CENTER);

		String[] entranceTest = ModelDBConnection.getAllEntranceTest();
		String[] entranceTestGroups = ModelDBConnection.getAllGroupEntranceTest();

		int numSheet = 0;

		for (int et_i = 0; et_i < entranceTest.length; et_i++) {
			int rowNum = 0;
			XSSFRow row;
			for (int etg_i = 0; etg_i < entranceTestGroups.length; etg_i++) {

				String[][] studentsTest = ModelDBConnection.getListSpecialityWithAbit(entranceTest[et_i],
						entranceTestGroups[etg_i]);
				if (studentsTest != null) {
					XSSFSheet sheet = workbook.cloneSheet(0);
					workbook.setSheetName(++numSheet, entranceTest[et_i] + "_" + entranceTestGroups[etg_i]);

					row = sheet.createRow(++rowNum);
					row.createCell(0).setCellValue(
							"вступительного испытания " + entranceTest[et_i] + " по программам ординатуры");
					row.getCell(0).setCellStyle(styleForCellsWithCenterAlg);

					rowNum++;
					rowNum++;
					row = sheet.getRow(++rowNum);
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
					row.createCell(0).setCellValue("Дата проведения: " + studentsTest[0][4]);
					row.getCell(0).setCellStyle(styleForCellsWithLeftAlg);
					row.createCell(4).setCellValue(entranceTestGroups[etg_i]);
					row.getCell(4).setCellStyle(styleForCellsWithCenterAlg);

					rowNum++;
					rowNum++;
					for (int i = 0; i < studentsTest.length; i++) {
						row = sheet.getRow(++rowNum);
						row.getCell(2)
								.setCellValue(studentsTest[i][0] + " " + studentsTest[i][1] + " " + studentsTest[i][2]);
						row.getCell(2).setCellStyle(styleForNames);
						row.getCell(3).setCellValue(studentsTest[i][3]);
						row.getCell(3).setCellStyle(styleForNames);

					}
				}
			}
		}

		workbook.removeSheetAt(0);

		String path = currentPath + "\\files\\Результаты_Вступительных" + "_"
				+ (new SimpleDateFormat("dd.MM.yyyy").format(new Date())) + ".xls";
		File file = new File(path);
		if (file.exists())
			file.delete();
		file.createNewFile();

		workbook.write(new FileOutputStream(file));
	}

	public static void writeListOfSubmittedDocuments() throws Exception {
		String query, moduleType = ModelDBConnection.getDBName().equals("Aspirant") ? "аспирантура" : "ординатура";

		Connection con = ModelDBConnection.getConnection();
		ResultSet rset = null;
		CallableStatement cstmt = null;

		XSSFWorkbook workbook = new XSSFWorkbook(
				new FileInputStream(currentPath + "\\Dots\\Список_подавших_" + moduleType + ".xltx"));
		XSSFSheet sheet = workbook.getSheetAt(0);

		XSSFFont fontForCategories = workbook.createFont();
		fontForCategories.setBold(true);
		XSSFCellStyle styleForCategories = workbook.createCellStyle();
		styleForCategories.setFont(fontForCategories);
		styleForCategories.setAlignment(XSSFCellStyle.ALIGN_CENTER);

		XSSFFont fontForTargetOrgs = workbook.createFont();
		fontForTargetOrgs.setBold(true);
		XSSFCellStyle styleForTargetOrgs = workbook.createCellStyle();
		styleForTargetOrgs.setFont(fontForTargetOrgs);

		XSSFFont fontForSpecialities = workbook.createFont();
		fontForSpecialities.setBold(true);
		fontForSpecialities.setItalic(true);
		XSSFCellStyle styleForSpecialities = workbook.createCellStyle();
		styleForSpecialities.setFont(fontForSpecialities);
		styleForSpecialities.setAlignment(XSSFCellStyle.ALIGN_CENTER);

		XSSFFont fontForNames = workbook.createFont();
		fontForNames.setBold(true);
		XSSFCellStyle styleForNames = workbook.createCellStyle();
		styleForNames.setFont(fontForNames);
		styleForNames.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleForNames.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleForNames.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		styleForNames.setWrapText(true);

		XSSFCellStyle styleForCells = workbook.createCellStyle();
		styleForCells.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleForCells.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleForCells.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleForCells.setBorderTop(XSSFCellStyle.BORDER_THIN);

		XSSFCellStyle styleForCellsWithCenterAlg = workbook.createCellStyle();
		styleForCellsWithCenterAlg.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleForCellsWithCenterAlg.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleForCellsWithCenterAlg.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleForCellsWithCenterAlg.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleForCellsWithCenterAlg.setAlignment(XSSFCellStyle.ALIGN_CENTER);

		int rowNum = 1, numPP = 1;
		XSSFRow row;

		String[][] specialities = moduleType.equals("аспирантура") ? ModelDBConnection.getAllFromTable("Course")
				: ModelDBConnection.getAllFromTable("Speciality");
		String[][] targetOrganisations = ModelDBConnection.getAllFromTable("TargetOrganisation");
		String[][] educationStandarts = moduleType.equals("аспирантура")
				? ModelDBConnection.getAllFromTable("EducationForm")
				: ModelDBConnection.getAllFromTable("EducationStandard");
		String[][] competitiveGroups = ModelDBConnection.getAllFromTableOrderedById("CompetitiveGroup");

		for (int s_i = 0; s_i < specialities.length; s_i++) {
			query = moduleType.equals("аспирантура")
					? "select * from AbiturientCompetitiveGroup where course = '" + specialities[s_i][0] + "'"
					: "select * from AbiturientCompetitiveGroup where speciality = '" + specialities[s_i][0] + "'";
			cstmt = con.prepareCall(query, 1004, 1007);
			rset = cstmt.executeQuery();
			int countAllAbitsOnCurSpec = rset.last() ? rset.getRow() : 0;
			rset.close();

			if (countAllAbitsOnCurSpec > 0) {
				row = sheet.createRow(rowNum++);
				row = sheet.createRow(rowNum++);
				row.createCell(1).setCellValue((moduleType.equals("аспирантура") ? "Направление: " : "Специальность: ")
						+ specialities[s_i][1]);
				sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 1, 2));
				row.getCell(1).setCellStyle(styleForSpecialities);

				for (int cg_i = 0; cg_i < competitiveGroups.length; cg_i++) {
					// Целевики
					if (competitiveGroups[cg_i][0].equals("1")) {
						query = moduleType.equals("аспирантура")
								? "select * from AbiturientCompetitiveGroup where course = '" + specialities[s_i][0]
										+ "' and competitiveGroup in (1,2)"
								: "select * from AbiturientCompetitiveGroup where speciality = '" + specialities[s_i][0]
										+ "' and competitiveGroup in (1,2)";
						cstmt = con.prepareCall(query, 1004, 1007);
						rset = cstmt.executeQuery();
						int countAbitsOnCurSpecOnCurCompGr = rset.last() ? rset.getRow() : 0;
						rset.close();

						if (countAbitsOnCurSpecOnCurCompGr > 0) {
							row = sheet.createRow(rowNum++);
							row.createCell(1).setCellValue("МЕСТА В РАМКАХ КЦП");
							sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 1, 2));
							row.getCell(1).setCellStyle(styleForCategories);
						}

						query = moduleType.equals("аспирантура")
								? "select * from AbiturientCompetitiveGroup where course = '" + specialities[s_i][0]
										+ "' and targetOrganisation is not null"
								: "select * from AbiturientCompetitiveGroup where speciality = '" + specialities[s_i][0]
										+ "' and targetOrganisation is not null";
						cstmt = con.prepareCall(query, 1004, 1007);
						rset = cstmt.executeQuery();
						int countTargAbitsOnCurSpec = rset.last() ? rset.getRow() : 0;
						rset.close();

						if (countTargAbitsOnCurSpec > 0) {
							row = sheet.createRow(rowNum++);
							row.createCell(1).setCellValue("места по целевому приему");
							row.getCell(1).setCellStyle(styleForTargetOrgs);

							for (int j = 0; j < targetOrganisations.length; j++) {
								numPP = 1;
								query = moduleType.equals("аспирантура")
										? "select SName, Fname, MName, Speciality.name, ReturnReasons.name from (Speciality join AbiturientCompetitiveGroup on (AbiturientCompetitiveGroup.speciality = Speciality.id) join Abiturient on (AbiturientCompetitiveGroup.aid_abiturient = Abiturient.aid)) left outer join ReturnReasons on (ReturnReasons.id = Abiturient.id_returnReason) where course = '"
												+ specialities[s_i][0] + "' and targetOrganisation = '"
												+ targetOrganisations[j][0] + "'"
										: "select SName, Fname, MName, ReturnReasons.name from (AbiturientCompetitiveGroup join Abiturient on (AbiturientCompetitiveGroup.aid_abiturient = Abiturient.aid)) left outer join ReturnReasons on (ReturnReasons.id = Abiturient.id_returnReason) where speciality = '"
												+ specialities[s_i][0] + "' and targetOrganisation = '"
												+ targetOrganisations[j][0] + "'";
								cstmt = con.prepareCall(query, 1004, 1007);
								rset = cstmt.executeQuery();
								int countAbitsOnCurSpecOnCurTargOrg = rset.last() ? rset.getRow() : 0;
								rset.beforeFirst();

								if (countAbitsOnCurSpecOnCurTargOrg > 0) {
									row = sheet.createRow(rowNum++);
									row = sheet.createRow(rowNum++);
									row.createCell(0).setCellValue("Целевая организация: " + targetOrganisations[j][1]);
									sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 3));
									row.getCell(0).setCellStyle(styleForTargetOrgs);

									row = sheet.createRow(rowNum++);
									if (moduleType.equals("аспирантура")) {
										row.createCell(0).setCellValue("№п/п");
										row.createCell(1).setCellValue("ФИО");
										row.createCell(2).setCellValue("Специальность");
										row.createCell(3).setCellValue("Статус документов");
										row.createCell(4).setCellValue("Примечание");
										row.getCell(0).setCellStyle(styleForNames);
										row.getCell(1).setCellStyle(styleForNames);
										row.getCell(2).setCellStyle(styleForNames);
										row.getCell(3).setCellStyle(styleForNames);
										row.getCell(4).setCellStyle(styleForNames);
									} else {
										row.createCell(0).setCellValue("№п/п");
										row.createCell(1).setCellValue("ФИО");
										row.createCell(2).setCellValue("Статус документов");
										row.createCell(3).setCellValue("Примечание");
										row.getCell(0).setCellStyle(styleForNames);
										row.getCell(1).setCellStyle(styleForNames);
										row.getCell(2).setCellStyle(styleForNames);
										row.getCell(3).setCellStyle(styleForNames);
									}

									while (rset.next()) {
										row = sheet.createRow(rowNum++);
										if (moduleType.equals("аспирантура")) {
											row.createCell(0).setCellValue(numPP++);
											row.createCell(1).setCellValue(rset.getString(1) + " " + rset.getString(2)
													+ " " + rset.getString(3));
											row.createCell(2).setCellValue(rset.getString(4));
											row.createCell(3)
													.setCellValue(rset.getString(5) != null ? "Отозваны" : "Приняты");
											row.createCell(4).setCellValue(rset.getString(5));
											row.getCell(0).setCellStyle(styleForCells);
											row.getCell(1).setCellStyle(styleForCells);
											row.getCell(2).setCellStyle(styleForCellsWithCenterAlg);
											row.getCell(3).setCellStyle(styleForCellsWithCenterAlg);
											row.getCell(4).setCellStyle(styleForCellsWithCenterAlg);
										} else {
											row.createCell(0).setCellValue(numPP++);
											row.createCell(1).setCellValue(rset.getString(1) + " " + rset.getString(2)
													+ " " + rset.getString(3));
											row.createCell(2)
													.setCellValue(rset.getString(4) != null ? "Отозваны" : "Приняты");
											row.createCell(3).setCellValue(rset.getString(4));
											row.getCell(0).setCellStyle(styleForCells);
											row.getCell(1).setCellStyle(styleForCells);
											row.getCell(2).setCellStyle(styleForCellsWithCenterAlg);
											row.getCell(3).setCellStyle(styleForCellsWithCenterAlg);
										}
									}
								}
								rset.close();
							}
						}
					} else {
						query = moduleType.equals("аспирантура")
								? "select SName, Fname, MName, Speciality.name, ReturnReasons.name, EducationForm.name from (Speciality join AbiturientCompetitiveGroup on (AbiturientCompetitiveGroup.speciality = Speciality.id) join Abiturient on (AbiturientCompetitiveGroup.aid_abiturient = Abiturient.aid) join EducationForm on (AbiturientCompetitiveGroup.educationForm = EducationForm.id)) left outer join ReturnReasons on (ReturnReasons.id = Abiturient.id_returnReason) where course = '"
										+ specialities[s_i][0] + "' and competitiveGroup = '"
										+ competitiveGroups[cg_i][0] + "' and targetOrganisation is null"
								: "select SName, Fname, MName, ReturnReasons.name, EducationStandard.name from (AbiturientCompetitiveGroup join Abiturient on (AbiturientCompetitiveGroup.aid_abiturient = Abiturient.aid) join EducationStandard on (AbiturientCompetitiveGroup.educationStandard = EducationStandard.id)) left outer join ReturnReasons on (ReturnReasons.id = Abiturient.id_returnReason) where speciality = '"
										+ specialities[s_i][0] + "' and competitiveGroup = '"
										+ competitiveGroups[cg_i][0] + "' and targetOrganisation is null";
						cstmt = con.prepareCall(query, 1004, 1007);
						rset = cstmt.executeQuery();
						int countAbitsOnCurSpecOnCurCompGr = rset.last() ? rset.getRow() : 0;
						rset.close();

						if (countAbitsOnCurSpecOnCurCompGr > 0) {
							if (competitiveGroups[cg_i][0].equals("3")) {
								row = sheet.createRow(rowNum++);
								row = sheet.createRow(rowNum++);
								row.createCell(1).setCellValue("МЕСТА ПО ДОГОВОРАМ ОБ ОКАЗАНИИ ПЛАТНЫХ УСЛУГ");
								sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 1, 2));
								row.getCell(1).setCellStyle(styleForCategories);
							}
						}

						for (int es_i = 0; es_i < educationStandarts.length; es_i++) {
							query = moduleType.equals("аспирантура")
									? "select SName, Fname, MName, Speciality.name, ReturnReasons.name, EducationForm.name from (Speciality join AbiturientCompetitiveGroup on (AbiturientCompetitiveGroup.speciality = Speciality.id) join Abiturient on (AbiturientCompetitiveGroup.aid_abiturient = Abiturient.aid) join EducationForm on (AbiturientCompetitiveGroup.educationForm = EducationForm.id)) left outer join ReturnReasons on (ReturnReasons.id = Abiturient.id_returnReason) where course = '"
											+ specialities[s_i][0] + "' and competitiveGroup = '"
											+ competitiveGroups[cg_i][0] + "' and educationForm = '"
											+ educationStandarts[es_i][0] + "' and targetOrganisation is null"
									: "select SName, Fname, MName, ReturnReasons.name, EducationStandard.name from (AbiturientCompetitiveGroup join Abiturient on (AbiturientCompetitiveGroup.aid_abiturient = Abiturient.aid) join EducationStandard on (AbiturientCompetitiveGroup.educationStandard = EducationStandard.id)) left outer join ReturnReasons on (ReturnReasons.id = Abiturient.id_returnReason) where speciality = '"
											+ specialities[s_i][0] + "' and competitiveGroup = '"
											+ competitiveGroups[cg_i][0] + "' and educationStandard = '"
											+ educationStandarts[es_i][0] + "' and targetOrganisation is null";
							cstmt = con.prepareCall(query, 1004, 1007);
							rset = cstmt.executeQuery();
							int countAbitsOnCurSpecOnCurCompGrAndSt = rset.last() ? rset.getRow() : 0;
							rset.beforeFirst();

							if (countAbitsOnCurSpecOnCurCompGrAndSt > 0) {
								numPP = 1;

								row = sheet.createRow(rowNum++);
								row = sheet.createRow(rowNum++);
								if (moduleType.equals("аспирантура")) {
									row.createCell(1).setCellValue("Форма обучения: " + educationStandarts[es_i][1]);
									row.getCell(1).setCellStyle(styleForTargetOrgs);
									row = sheet.createRow(rowNum++);
									row.createCell(1).setCellValue("по общему конкурсу");
								} else
									row.createCell(1)
											.setCellValue("по общему конкурсу" + (competitiveGroups[cg_i][0].equals("3")
													? " (для завершивших обучение по " + educationStandarts[es_i][1]
															+ ")"
													: ""));
								row.getCell(1).setCellStyle(styleForTargetOrgs);
								rowNum++;

								row = sheet.createRow(rowNum++);
								if (moduleType.equals("аспирантура")) {
									row.createCell(0).setCellValue("№п/п");
									row.createCell(1).setCellValue("ФИО");
									row.createCell(2).setCellValue("Специальность");
									row.createCell(3).setCellValue("Статус документов");
									row.createCell(4).setCellValue("Примечание");
									row.getCell(0).setCellStyle(styleForNames);
									row.getCell(1).setCellStyle(styleForNames);
									row.getCell(2).setCellStyle(styleForNames);
									row.getCell(3).setCellStyle(styleForNames);
									row.getCell(4).setCellStyle(styleForNames);
								} else {
									row.createCell(0).setCellValue("№п/п");
									row.createCell(1).setCellValue("ФИО");
									row.createCell(2).setCellValue("Статус документов");
									row.createCell(3).setCellValue("Примечание");
									row.getCell(0).setCellStyle(styleForNames);
									row.getCell(1).setCellStyle(styleForNames);
									row.getCell(2).setCellStyle(styleForNames);
									row.getCell(3).setCellStyle(styleForNames);
								}

								while (rset.next()) {
									row = sheet.createRow(rowNum++);
									if (moduleType.equals("аспирантура")) {
										row.createCell(0).setCellValue(numPP++);
										row.createCell(1).setCellValue(
												rset.getString(1) + " " + rset.getString(2) + " " + rset.getString(3));
										row.createCell(2).setCellValue(rset.getString(4));
										row.createCell(3)
												.setCellValue(rset.getString(5) != null ? "Отозваны" : "Приняты");
										row.createCell(4).setCellValue(rset.getString(5));
										row.getCell(0).setCellStyle(styleForCells);
										row.getCell(1).setCellStyle(styleForCells);
										row.getCell(2).setCellStyle(styleForCellsWithCenterAlg);
										row.getCell(3).setCellStyle(styleForCellsWithCenterAlg);
										row.getCell(4).setCellStyle(styleForCellsWithCenterAlg);
									} else {
										row.createCell(0).setCellValue(numPP++);
										row.createCell(1).setCellValue(
												rset.getString(1) + " " + rset.getString(2) + " " + rset.getString(3));
										row.createCell(2)
												.setCellValue(rset.getString(4) != null ? "Отозваны" : "Приняты");
										row.createCell(3).setCellValue(rset.getString(4));
										row.getCell(0).setCellStyle(styleForCells);
										row.getCell(1).setCellStyle(styleForCells);
										row.getCell(2).setCellStyle(styleForCellsWithCenterAlg);
										row.getCell(3).setCellStyle(styleForCellsWithCenterAlg);
									}
								}
							}
							rset.close();
						}
					}
				}
			}
		}

		String path = currentPath + "\\files\\" + "Список_подавших_" + moduleType + "_"
				+ (new SimpleDateFormat("dd.MM.yyyy").format(new Date())) + ".xls";
		File file = new File(path);
		if (file.exists())
			file.delete();
		file.createNewFile();

		workbook.write(new FileOutputStream(file));
	}

	private static void writeToRow(XSSFSheet sheet, int rowNum, String[] dataRow) {
		XSSFRow row = sheet.createRow(rowNum);

		for (int i = 0; i < columnNames.length; i++)
			row.createCell(i).setCellValue(dataRow[i]);
	}

	public static void main(String[] args) {
		try {
			ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Ordinator", "user", "password");
			ModelDBConnection.initConnection();
			writeListOfSubmittedDocuments();
			writeResultsEntranceTest();
			writeListGroupEntranceTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
