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

	public static void writeAdmissionPlan() throws Exception {
		String moduleType = ModelDBConnection.getDBName().equals("Aspirant") ? "аспирантура" : "ординатура";
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(currentPath + "\\Dots\\План_приема" + ".xltx"));
		XSSFSheet sheet = workbook.getSheetAt(0);

		XSSFFont fontForNames = workbook.createFont();
		fontForNames.setFontHeight(11);
		fontForNames.setFontName("Arial Cyr");
		XSSFCellStyle styleForNames = workbook.createCellStyle();
		styleForNames.setFont(fontForNames);
		styleForNames.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleForNames.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleForNames.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		String[][] plan = ModelDBConnection.getAdmissionPlan();

		int rowNum = 2;
		XSSFRow row;

		for (int i = 0; i < plan.length; i++) {
			row = sheet.createRow(++rowNum);
			for (int j = 0; j < plan[i].length; j++) {
				row.createCell(j + 1).setCellValue(plan[i][j]);
				row.getCell(j + 1).setCellStyle(styleForNames);
			}
		}

		String path = currentPath + "\\files\\План_приема" + "_" + moduleType + "_"
				+ (new SimpleDateFormat("dd.MM.yyyy").format(new Date())) + ".xls";
		File file = new File(path);
		if (file.exists())
			file.delete();
		file.createNewFile();

		workbook.write(new FileOutputStream(file));
	}

	public static void writeListGroupsOnEntranceTests() throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook(
				new FileInputStream(currentPath + "\\Dots\\Списки_групп" + ".xltx"));

		XSSFFont fontForEntranceTestName = workbook.createFont();
		fontForEntranceTestName.setBold(true);
		fontForEntranceTestName.setFontHeight(12);
		fontForEntranceTestName.setFontName("Arial Cyr");

		XSSFCellStyle styleForCellsWithCenterAlg = workbook.createCellStyle();
		styleForCellsWithCenterAlg.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleForCellsWithCenterAlg.setFont(fontForEntranceTestName);

		XSSFCellStyle styleForCellsWithLeftAlg = workbook.createCellStyle();
		styleForCellsWithLeftAlg.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleForCellsWithLeftAlg.setFont(fontForEntranceTestName);

		XSSFFont fontForNames = workbook.createFont();
		fontForNames.setFontHeight(11);
		fontForNames.setFontName("Arial Cyr");
		XSSFCellStyle styleForNames = workbook.createCellStyle();
		styleForNames.setFont(fontForNames);
		styleForNames.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleForNames.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleForNames.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		String[][] entranceTests = ModelDBConnection.getAllFromTableOrderedById("EntranceTest");
		String[] entranceTestGroups = ModelDBConnection.getAllGroupsNames();

		int numSheet = 0;

		for (int et_i = 0; et_i < entranceTests.length; et_i++) {
			int rowNum = 0;
			XSSFRow row;
			for (int etg_i = 0; etg_i < entranceTestGroups.length; etg_i++) {
				rowNum = 0;

				String[][] studentsList = ModelDBConnection.getListAbiturientsByEntranceTestAndGroupIDs(entranceTests[et_i][0],
						entranceTestGroups[etg_i]);
				if (studentsList != null) {
					XSSFSheet sheet = workbook.cloneSheet(0);
					workbook.setSheetName(++numSheet, entranceTests[et_i][1] + "_" + entranceTestGroups[etg_i]);

					row = sheet.createRow(rowNum++);
					row.createCell(0).setCellValue("Список группы №" + entranceTestGroups[etg_i] +
							" вступительного испытания " + entranceTests[et_i][1]);
					row.getCell(0).setCellStyle(styleForCellsWithCenterAlg);

					rowNum++;

					for (int i = 0; i < studentsList.length; i++) {
						row = sheet.getRow(++rowNum);
						row.getCell(2).setCellValue(studentsList[i][0] + " " + studentsList[i][1] + " " + studentsList[i][2]);
						row.getCell(2).setCellStyle(styleForNames);
						row.getCell(3).setCellValue(entranceTestGroups[etg_i] + "ord" + studentsList[i][7]);
						row.getCell(3).setCellStyle(styleForNames);
						row.getCell(4).setCellValue(studentsList[i][5] + studentsList[i][6]);
						row.getCell(4).setCellStyle(styleForNames);
					}
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
		fontForEntranceTestName.setFontHeight(12);
		fontForEntranceTestName.setFontName("Arial Cyr");

		XSSFCellStyle styleForCellsWithCenterAlg = workbook.createCellStyle();
		styleForCellsWithCenterAlg.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleForCellsWithCenterAlg.setFont(fontForEntranceTestName);

		XSSFCellStyle styleForCellsWithLeftAlg = workbook.createCellStyle();
		styleForCellsWithLeftAlg.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		styleForCellsWithLeftAlg.setFont(fontForEntranceTestName);

		XSSFFont fontForNames = workbook.createFont();
		fontForNames.setFontHeight(11);
		fontForNames.setFontName("Arial Cyr");
		XSSFCellStyle styleForNames = workbook.createCellStyle();
		styleForNames.setFont(fontForNames);
		styleForNames.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderRight(XSSFCellStyle.BORDER_THIN);
		styleForNames.setBorderTop(XSSFCellStyle.BORDER_THIN);
		styleForNames.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		styleForNames.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);

		String[][] entranceTests = ModelDBConnection.getAllFromTableOrderedById("EntranceTest");
		String[] entranceTestGroups = ModelDBConnection.getAllGroupsNames();

		int numSheet = 0;

		for (int et_i = 0; et_i < entranceTests.length; et_i++) {
			int rowNum = 0;
			XSSFRow row;
			for (int etg_i = 0; etg_i < entranceTestGroups.length; etg_i++) {
				rowNum = 0;

				String[][] studentsTest = ModelDBConnection.getListAbiturientsByEntranceTestAndGroupIDs(entranceTests[et_i][0],
						entranceTestGroups[etg_i]);
				if (studentsTest != null) {
					XSSFSheet sheet = workbook.cloneSheet(0);
					workbook.setSheetName(++numSheet, entranceTests[et_i][1] + "_" + entranceTestGroups[etg_i]);

					row = sheet.createRow(++rowNum);
					row.createCell(0).setCellValue(
							"вступительного испытания " + entranceTests[et_i][1] + " по программам ординатуры");
					row.getCell(0).setCellStyle(styleForCellsWithCenterAlg);

					rowNum++;
					rowNum++;
					row = sheet.getRow(++rowNum);
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
					row.createCell(0).setCellValue("Дата проведения: " + (studentsTest[0][4] != null ? studentsTest[0][4] : ""));
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
						row.getCell(3).setCellValue(studentsTest[i][3] != null ? studentsTest[i][3] : "неявка");
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

	public static void writeStatistics() throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFRow row;

		// Лист 1
		XSSFSheet sheetGZGU_sp = workbook.createSheet("ГЗГУ_Специальность");

		String[][] statisticsGZGU_speciality = ModelDBConnection.getStatisticsGZGU(false);

		sheetGZGU_sp.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
		sheetGZGU_sp.addMergedRegion(new CellRangeAddress(0, 0, 5, 7));

		row = sheetGZGU_sp.createRow(0);
		row.createCell(0).setCellValue("Специальноcть");
		row.createCell(1).setCellValue("Форма обучения");
		row.createCell(2).setCellValue("Количество поданных заявлений");
		row.createCell(5).setCellValue("Количество зачисленных");

		row = sheetGZGU_sp.createRow(1);
		row.createCell(2).setCellValue("всего");
		row.createCell(3).setCellValue("в т.ч. целевая квота");
		row.createCell(4).setCellValue("на коммерческие места");
		row.createCell(5).setCellValue("всего");
		row.createCell(6).setCellValue("в т.ч. целевая квота");
		row.createCell(7).setCellValue("на коммерческие места");

		for (int i = 2; i < statisticsGZGU_speciality.length + 2; i++) {
			row = sheetGZGU_sp.createRow(i);
			row.createCell(0).setCellValue(statisticsGZGU_speciality[i - 2][0]);
			row.createCell(1).setCellValue(statisticsGZGU_speciality[i - 2][1]);
			row.createCell(2).setCellValue(Integer.valueOf(statisticsGZGU_speciality[i - 2][2])
					+ Integer.valueOf(statisticsGZGU_speciality[i - 2][3]));
			row.createCell(3).setCellValue(Integer.valueOf(statisticsGZGU_speciality[i - 2][3]));
			row.createCell(4).setCellValue(Integer.valueOf(statisticsGZGU_speciality[i - 2][4]));
			row.createCell(5).setCellValue(Integer.valueOf(statisticsGZGU_speciality[i - 2][5])
					+ Integer.valueOf(statisticsGZGU_speciality[i - 2][6]));
			row.createCell(6).setCellValue(Integer.valueOf(statisticsGZGU_speciality[i - 2][6]));
			row.createCell(7).setCellValue(Integer.valueOf(statisticsGZGU_speciality[i - 2][7]));
		}

		for (int i = 0; i < statisticsGZGU_speciality[0].length; i++)
			sheetGZGU_sp.autoSizeColumn(i);

		// Лист 2
		XSSFSheet sheetGZGU_crs = workbook.createSheet("ГЗГУ_Направление");

		String[][] statisticsGZGU_course = ModelDBConnection.getStatisticsGZGU(true);

		sheetGZGU_crs.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
		sheetGZGU_crs.addMergedRegion(new CellRangeAddress(0, 0, 5, 7));

		row = sheetGZGU_crs.createRow(0);
		row.createCell(0).setCellValue("Направление");
		row.createCell(1).setCellValue("Форма обучения");
		row.createCell(2).setCellValue("Количество поданных заявлений");
		row.createCell(5).setCellValue("Количество зачисленных");
		
		row = sheetGZGU_crs.createRow(1);
		row.createCell(2).setCellValue("всего");
		row.createCell(3).setCellValue("в т.ч. целевая квота");
		row.createCell(4).setCellValue("на коммерческие места");
		row.createCell(5).setCellValue("всего");
		row.createCell(6).setCellValue("в т.ч. целевая квота");
		row.createCell(7).setCellValue("на коммерческие места");

		for (int i = 2; i < statisticsGZGU_course.length + 2; i++) {
			row = sheetGZGU_crs.createRow(i);
			row.createCell(0).setCellValue(statisticsGZGU_course[i - 2][0]);
			row.createCell(1).setCellValue(statisticsGZGU_course[i - 2][1]);
			row.createCell(2).setCellValue(Integer.valueOf(statisticsGZGU_course[i - 2][2])
					+ Integer.valueOf(statisticsGZGU_course[i - 2][3]));
			row.createCell(3).setCellValue(Integer.valueOf(statisticsGZGU_course[i - 2][3]));
			row.createCell(4).setCellValue(Integer.valueOf(statisticsGZGU_course[i - 2][4]));
			row.createCell(5).setCellValue(Integer.valueOf(statisticsGZGU_course[i - 2][5])
					+ Integer.valueOf(statisticsGZGU_course[i - 2][6]));
			row.createCell(6).setCellValue(Integer.valueOf(statisticsGZGU_course[i - 2][6]));
			row.createCell(7).setCellValue(Integer.valueOf(statisticsGZGU_course[i - 2][7]));
		}

		for (int i = 0; i < statisticsGZGU_speciality[0].length; i++)
			sheetGZGU_crs.autoSizeColumn(i);

		// Лист 3
		XSSFSheet sheetMinZdrav_sp = workbook.createSheet("МинЗдрав_Специальность");
		String[][] statisticsMinZdrav_speciality = ModelDBConnection.getStatisticsMinZdrav(false);

		row = sheetMinZdrav_sp.createRow(0);
		row.createCell(0).setCellValue("Специальноcть");
		row.createCell(1).setCellValue("Форма обучения");
		row.createCell(2).setCellValue("Наименование целевой организации");
		row.createCell(3).setCellValue("План приема");
		row.createCell(4).setCellValue("Количество поданных заявлений");
		row.createCell(5).setCellValue("Количество зачисленных");

		for (int i = 1; i < statisticsMinZdrav_speciality.length + 1; i++) {
			row = sheetMinZdrav_sp.createRow(i);
			row.createCell(0).setCellValue(statisticsMinZdrav_speciality[i - 1][0]);
			row.createCell(1).setCellValue(statisticsMinZdrav_speciality[i - 1][1]);
			row.createCell(2).setCellValue(statisticsMinZdrav_speciality[i - 1][2]);
			row.createCell(3).setCellValue("terra incognita");
			row.createCell(4).setCellValue(Integer.valueOf(statisticsMinZdrav_speciality[i - 1][3]));
			row.createCell(5).setCellValue(Integer.valueOf(statisticsMinZdrav_speciality[i - 1][4]));
		}

		for (int i = 0; i <= statisticsMinZdrav_speciality[0].length; i++)
			sheetMinZdrav_sp.autoSizeColumn(i);

		// Лист 4
		XSSFSheet sheetMinZdrav_crs = workbook.createSheet("МинЗдрав_Направление");
		String[][] statisticsMinZdrav_cousre = ModelDBConnection.getStatisticsMinZdrav(true);

		row = sheetMinZdrav_crs.createRow(0);
		row.createCell(0).setCellValue("Специальноcть");
		row.createCell(1).setCellValue("Форма обучения");
		row.createCell(2).setCellValue("Наименование целевой организации");
		row.createCell(3).setCellValue("План приема");
		row.createCell(4).setCellValue("Количество поданных заявлений");
		row.createCell(5).setCellValue("Количество зачисленных");

		for (int i = 1; i < statisticsMinZdrav_cousre.length + 1; i++) {
			row = sheetMinZdrav_crs.createRow(i);
			row.createCell(0).setCellValue(statisticsMinZdrav_cousre[i - 1][0]);
			row.createCell(1).setCellValue(statisticsMinZdrav_cousre[i - 1][1]);
			row.createCell(2).setCellValue(statisticsMinZdrav_cousre[i - 1][2]);
			row.createCell(3).setCellValue("terra incognita");
			row.createCell(4).setCellValue(Integer.valueOf(statisticsMinZdrav_cousre[i - 1][3]));
			row.createCell(5).setCellValue(Integer.valueOf(statisticsMinZdrav_cousre[i - 1][4]));
		}

		for (int i = 0; i <= statisticsMinZdrav_cousre[0].length; i++)
			sheetMinZdrav_crs.autoSizeColumn(i);

		// Лист 5
		
		// ВСПОМОГАТЕЛЬНЫЕ ПЕРЕМЕННЫЕ ТОЛЬКО ДЛЯ ЛИСТОВ 5-8
		XSSFRow row0;
		XSSFRow row1;
		// отступ слева
		int pitch;
		// промежутки между специальностями/направлениями
		int gap;
		int tmp;
		
		XSSFSheet sheetRegionFull_SubmittedDocuments_sp = workbook.createSheet("РC по регионам_Подано_Специальность");
		String[][] statisticsRegionFull_SubmittedDocuments_speciality = ModelDBConnection
				.getStatisticsRegionFull_SubmittedDocuments(false);

		row0 = sheetRegionFull_SubmittedDocuments_sp.createRow(0);
		row0.createCell(0).setCellValue("Специальноcть");
		row0.createCell(1).setCellValue("Форма обучения");
		row0.createCell(2).setCellValue("Источник финансирования");

		row1 = sheetRegionFull_SubmittedDocuments_sp.createRow(1);


		// кол-во регионов с 1 тк с собой не сравниваем, считаем один раз для всех листов с 5-8
		int regionNumber = 1;
		int jRow = 1;
		while (!(statisticsRegionFull_SubmittedDocuments_speciality[0][3]
				.equals(statisticsRegionFull_SubmittedDocuments_speciality[jRow][3]))) {
			regionNumber++;
			jRow++;
		}

		tmp = 0;
		pitch = 3;
		while (tmp != regionNumber) {
			sheetRegionFull_SubmittedDocuments_sp.addMergedRegion(new CellRangeAddress(0, 0, pitch, pitch + 1));
			row0.createCell(pitch).setCellValue(statisticsRegionFull_SubmittedDocuments_speciality[tmp][3]);
			row1.createCell(pitch).setCellValue("Подано заявлений");
			row1.createCell(pitch + 1).setCellValue("в т.ч. на целевые места");
			tmp++;
			pitch += 2;
		}

		gap = 0;
		for (int i = 2; i < statisticsRegionFull_SubmittedDocuments_speciality.length + 2; i += regionNumber) {
			if (i == 2)
				row = sheetRegionFull_SubmittedDocuments_sp.createRow(i);
			else
				row = sheetRegionFull_SubmittedDocuments_sp.createRow(2 + gap);

			row.createCell(0).setCellValue(statisticsRegionFull_SubmittedDocuments_speciality[i - 2][0]);
			row.createCell(1).setCellValue(statisticsRegionFull_SubmittedDocuments_speciality[i - 2][1]);
			row.createCell(2).setCellValue(statisticsRegionFull_SubmittedDocuments_speciality[i - 2][2]);

			int bla = 0;
			for (int k = 3; k < regionNumber * 2 + 3; k += 2) {
				row.createCell(k).setCellValue(
						Integer.valueOf(statisticsRegionFull_SubmittedDocuments_speciality[i - 2 + bla][4]));
				row.createCell(k + 1).setCellValue(
						Integer.valueOf(statisticsRegionFull_SubmittedDocuments_speciality[i - 2 + bla][5]));
				bla++;

			}
			gap++;
		}

		for (int i = 0; i <= statisticsRegionFull_SubmittedDocuments_speciality[0].length + 1; i++)
			sheetRegionFull_SubmittedDocuments_sp.autoSizeColumn(i);

		// Лист 6
		XSSFSheet sheetRegionFull_SubmittedDocuments_crs = workbook.createSheet("РC по регионам_Подано_Направление");
		String[][] statisticsRegionFull_SubmittedDocuments_course = ModelDBConnection
				.getStatisticsRegionFull_SubmittedDocuments(true);

		row0 = sheetRegionFull_SubmittedDocuments_crs.createRow(0);
		row0.createCell(0).setCellValue("Специальноcть");
		row0.createCell(1).setCellValue("Форма обучения");
		row0.createCell(2).setCellValue("Источник финансирования");

		row1 = sheetRegionFull_SubmittedDocuments_crs.createRow(1);


		tmp = 0;
		pitch = 3;
		while (tmp != regionNumber) {
			sheetRegionFull_SubmittedDocuments_crs.addMergedRegion(new CellRangeAddress(0, 0, pitch, pitch + 1));
			row0.createCell(pitch).setCellValue(statisticsRegionFull_SubmittedDocuments_course[tmp][3]);
			row1.createCell(pitch).setCellValue("Подано заявлений");
			row1.createCell(pitch + 1).setCellValue("в т.ч. на целевые места");
			tmp++;
			pitch += 2;
		}

		gap = 0;
		for (int i = 2; i < statisticsRegionFull_SubmittedDocuments_course.length + 2; i += regionNumber) {
			if (i == 2)
				row = sheetRegionFull_SubmittedDocuments_crs.createRow(i);
			else
				row = sheetRegionFull_SubmittedDocuments_crs.createRow(2 + gap);

			row.createCell(0).setCellValue(statisticsRegionFull_SubmittedDocuments_course[i - 2][0]);
			row.createCell(1).setCellValue(statisticsRegionFull_SubmittedDocuments_course[i - 2][1]);
			row.createCell(2).setCellValue(statisticsRegionFull_SubmittedDocuments_course[i - 2][2]);

			tmp = 0;
			for (int k = 3; k < regionNumber * 2 + 3; k += 2) {
				row.createCell(k).setCellValue(
						Integer.valueOf(statisticsRegionFull_SubmittedDocuments_course[i - 2 + tmp][4]));
				row.createCell(k + 1).setCellValue(
						Integer.valueOf(statisticsRegionFull_SubmittedDocuments_course[i - 2 + tmp][5]));
				tmp++;

			}
			gap++;
		}

		for (int i = 0; i <= statisticsRegionFull_SubmittedDocuments_course[0].length + 1; i++)
			sheetRegionFull_SubmittedDocuments_crs.autoSizeColumn(i);
		
		// Лист 7
		XSSFSheet sheetRegionFull_Enrolled_sp = workbook.createSheet("РC по регионам_Зачислено_Специальность");
		String[][] statisticsRegionFull_Enrolled_speciality = ModelDBConnection
				.getStatisticsRegionFull_Enrolled(false);

		row0 = sheetRegionFull_Enrolled_sp.createRow(0);
		row0.createCell(0).setCellValue("Специальноcть");
		row0.createCell(1).setCellValue("Форма обучения");
		row0.createCell(2).setCellValue("Источник финансирования");

		row1 = sheetRegionFull_Enrolled_sp.createRow(1);

		tmp = 0;
		pitch = 3;
		while (tmp != regionNumber) {
			sheetRegionFull_Enrolled_sp.addMergedRegion(new CellRangeAddress(0, 0, pitch, pitch + 1));
			row0.createCell(pitch).setCellValue(statisticsRegionFull_Enrolled_speciality[tmp][3]);
			row1.createCell(pitch).setCellValue("Подано заявлений");
			row1.createCell(pitch + 1).setCellValue("в т.ч. на целевые места");
			tmp++;
			pitch += 2;
		}

		gap = 0;
		for (int i = 2; i < statisticsRegionFull_Enrolled_speciality.length + 2; i += regionNumber) {
			if (i == 2)
				row = sheetRegionFull_Enrolled_sp.createRow(i);
			else
				row = sheetRegionFull_Enrolled_sp.createRow(2 + gap);

			row.createCell(0).setCellValue(statisticsRegionFull_Enrolled_speciality[i - 2][0]);
			row.createCell(1).setCellValue(statisticsRegionFull_Enrolled_speciality[i - 2][1]);
			row.createCell(2).setCellValue(statisticsRegionFull_Enrolled_speciality[i - 2][2]);

			int bla = 0;
			for (int k = 3; k < regionNumber * 2 + 3; k += 2) {
				row.createCell(k).setCellValue(
						Integer.valueOf(statisticsRegionFull_Enrolled_speciality[i - 2 + bla][4]));
				row.createCell(k + 1).setCellValue(
						Integer.valueOf(statisticsRegionFull_Enrolled_speciality[i - 2 + bla][5]));
				bla++;

			}
			gap++;
		}

		for (int i = 0; i <= statisticsRegionFull_Enrolled_speciality[0].length + 1; i++)
			sheetRegionFull_Enrolled_sp.autoSizeColumn(i);

		// Лист 8
		XSSFSheet sheetRegionFull_Enrolled_crs = workbook.createSheet("РC по регионам_Зачислено_Направление");
		String[][] statisticsRegionFull_Enrolled_course = ModelDBConnection
				.getStatisticsRegionFull_Enrolled(true);

		row0 = sheetRegionFull_Enrolled_crs.createRow(0);
		row0.createCell(0).setCellValue("Специальноcть");
		row0.createCell(1).setCellValue("Форма обучения");
		row0.createCell(2).setCellValue("Источник финансирования");

		row1 = sheetRegionFull_Enrolled_crs.createRow(1);


		tmp = 0;
		pitch = 3;
		while (tmp != regionNumber) {
			sheetRegionFull_Enrolled_crs.addMergedRegion(new CellRangeAddress(0, 0, pitch, pitch + 1));
			row0.createCell(pitch).setCellValue(statisticsRegionFull_Enrolled_course[tmp][3]);
			row1.createCell(pitch).setCellValue("Подано заявлений");
			row1.createCell(pitch + 1).setCellValue("в т.ч. на целевые места");
			tmp++;
			pitch += 2;
		}

		gap = 0;
		for (int i = 2; i < statisticsRegionFull_Enrolled_course.length + 2; i += regionNumber) {
			if (i == 2)
				row = sheetRegionFull_Enrolled_crs.createRow(i);
			else
				row = sheetRegionFull_Enrolled_crs.createRow(2 + gap);

			row.createCell(0).setCellValue(statisticsRegionFull_Enrolled_course[i - 2][0]);
			row.createCell(1).setCellValue(statisticsRegionFull_Enrolled_course[i - 2][1]);
			row.createCell(2).setCellValue(statisticsRegionFull_Enrolled_course[i - 2][2]);

			tmp = 0;
			for (int k = 3; k < regionNumber * 2 + 3; k += 2) {
				row.createCell(k).setCellValue(
						Integer.valueOf(statisticsRegionFull_Enrolled_course[i - 2 + tmp][4]));
				row.createCell(k + 1).setCellValue(
						Integer.valueOf(statisticsRegionFull_Enrolled_course[i - 2 + tmp][5]));
				tmp++;

			}
			gap++;
		}

		for (int i = 0; i <= statisticsRegionFull_Enrolled_course[0].length + 1; i++)
			sheetRegionFull_Enrolled_crs.autoSizeColumn(i);
		

		// Лист 9
		XSSFSheet sheetRegionShort_sp = workbook.createSheet("Сокращенная статистика по регионам_Специальность");
		String[][] statisticsRegionShort_speciality = ModelDBConnection.getStatisticsRegionShort(false);

		sheetRegionShort_sp.addMergedRegion(new CellRangeAddress(0, 0, 4, 6));
		sheetRegionShort_sp.addMergedRegion(new CellRangeAddress(0, 0, 7, 9));

		row = sheetRegionShort_sp.createRow(0);
		row.createCell(0).setCellValue("Специальноcть");
		row.createCell(1).setCellValue("Форма обучения");
		row.createCell(2).setCellValue("Источник финансирования");
		row.createCell(3).setCellValue("Целевая организация");
		row.createCell(4).setCellValue("Подано заявлений");
		row.createCell(7).setCellValue("Зачислено");

		row = sheetRegionShort_sp.createRow(1);
		row.createCell(4).setCellValue("Из Н.Новгорода");
		row.createCell(5).setCellValue("Из Нижегородской обл.");
		row.createCell(6).setCellValue("За пределами Нижегородской обл.");
		row.createCell(7).setCellValue("Из Н.Новгорода");
		row.createCell(8).setCellValue("Из Нижегородской обл.");
		row.createCell(9).setCellValue("За пределами Нижегородской обл.");

		for (int i = 2; i < statisticsRegionShort_speciality.length + 2; i++) {
			row = sheetRegionShort_sp.createRow(i);
			row.createCell(0).setCellValue(statisticsRegionShort_speciality[i - 2][1]);
			row.createCell(1).setCellValue(statisticsRegionShort_speciality[i - 2][3]);
			row.createCell(2).setCellValue(statisticsRegionShort_speciality[i - 2][5]);
			row.createCell(3).setCellValue(statisticsRegionShort_speciality[i - 2][7]);
			row.createCell(4).setCellValue(Integer.valueOf(statisticsRegionShort_speciality[i - 2][8]));
			row.createCell(5).setCellValue(Integer.valueOf(statisticsRegionShort_speciality[i - 2][9]));
			row.createCell(6).setCellValue(Integer.valueOf(statisticsRegionShort_speciality[i - 2][10]));
			row.createCell(7).setCellValue(Integer.valueOf(statisticsRegionShort_speciality[i - 2][11]));
			row.createCell(8).setCellValue(Integer.valueOf(statisticsRegionShort_speciality[i - 2][12]));
			row.createCell(9).setCellValue(Integer.valueOf(statisticsRegionShort_speciality[i - 2][13]));
		}

		for (int i = 0; i <= statisticsRegionShort_speciality[0].length; i++)
			sheetRegionShort_sp.autoSizeColumn(i);

		// Лист 10
		XSSFSheet sheetRegionShort_crs = workbook.createSheet("Сокращ стат по регионам_Напр");
		String[][] statisticsRegionShort_course = ModelDBConnection.getStatisticsRegionShort(true);

		sheetRegionShort_crs.addMergedRegion(new CellRangeAddress(0, 0, 4, 6));
		sheetRegionShort_crs.addMergedRegion(new CellRangeAddress(0, 0, 7, 9));

		row = sheetRegionShort_crs.createRow(0);
		row.createCell(0).setCellValue("Специальноcть");
		row.createCell(1).setCellValue("Форма обучения");
		row.createCell(2).setCellValue("Источник финансирования");
		row.createCell(3).setCellValue("Целевая организация");
		row.createCell(4).setCellValue("Подано заявлений");
		row.createCell(7).setCellValue("Зачислено");

		row = sheetRegionShort_crs.createRow(1);
		row.createCell(4).setCellValue("Из Н.Новгорода");
		row.createCell(5).setCellValue("Из Нижегородской обл.");
		row.createCell(6).setCellValue("За пределами Нижегородской обл.");
		row.createCell(7).setCellValue("Из Н.Новгорода");
		row.createCell(8).setCellValue("Из Нижегородской обл.");
		row.createCell(9).setCellValue("За пределами Нижегородской обл.");

		for (int i = 2; i < statisticsRegionShort_course.length + 2; i++) {
			row = sheetRegionShort_crs.createRow(i);
			row.createCell(0).setCellValue(statisticsRegionShort_course[i - 2][1]);
			row.createCell(1).setCellValue(statisticsRegionShort_course[i - 2][3]);
			row.createCell(2).setCellValue(statisticsRegionShort_course[i - 2][5]);
			row.createCell(3).setCellValue(statisticsRegionShort_course[i - 2][7]);
			row.createCell(4).setCellValue(Integer.valueOf(statisticsRegionShort_course[i - 2][8]));
			row.createCell(5).setCellValue(Integer.valueOf(statisticsRegionShort_course[i - 2][9]));
			row.createCell(6).setCellValue(Integer.valueOf(statisticsRegionShort_course[i - 2][10]));
			row.createCell(7).setCellValue(Integer.valueOf(statisticsRegionShort_course[i - 2][11]));
			row.createCell(8).setCellValue(Integer.valueOf(statisticsRegionShort_course[i - 2][12]));
			row.createCell(9).setCellValue(Integer.valueOf(statisticsRegionShort_course[i - 2][13]));
		}

		for (int i = 0; i <= statisticsRegionShort_course[0].length; i++)
			sheetRegionShort_crs.autoSizeColumn(i);

		String path = currentPath + "\\files\\Статистика" + "_"
				+ (new SimpleDateFormat("dd.MM.yyyy").format(new Date())) + ".xls";
		File file = new File(path);
		if (file.exists())
			file.delete();
		file.createNewFile();

		workbook.write(new FileOutputStream(file));
	}


	public static void main(String[] args) {
		try {
			ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Ordinator", "user", "password");
			ModelDBConnection.initConnection();
			writeListOfSubmittedDocuments();
			writeResultsEntranceTest();
			writeListGroupsOnEntranceTests();
			writeAdmissionPlan();
			writeStatistics();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
