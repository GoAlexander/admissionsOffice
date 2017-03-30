package outputDoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import backend.ModelDBConnection;

public class OutputWord {
	private static String currentPath = new File("").getAbsolutePath();

	public static void writeStatement(String[][] allCompetitiveGroups, String[] generalInfo, String[] passportData, String[] addressContacts, String[] highEducation, String[] postGraduateEducation, String needSpecialConditions, String[][] indAchievments) throws Exception {
		XWPFDocument doc_out = new XWPFDocument();

		String moduleType = ModelDBConnection.getDBName().equals("Aspirant") ? "аспирантура" : "ординатура";
		String hasPublications = "Нет";
		if (ModelDBConnection.getDBName().equals("Aspirant"))
			for (int i = 0; i < (indAchievments != null ? indAchievments.length : 0); i++)
				if (indAchievments[i][1].equals("Научные публикации") || indAchievments[i][1].equals("Научные труды")) {
					hasPublications = "Да";
					break;
				}

		for (int i = 0; i < allCompetitiveGroups.length; i++)
		{
			XWPFDocument doc = new XWPFDocument(new FileInputStream(currentPath + "/Dots/Заявление_" + moduleType + ".dotx"));
			boolean start_replace = false;
			for (XWPFParagraph p : doc.getParagraphs()) {
				for (XWPFRun r : p.getRuns()) {
					if (r.getText(0) != null) {
						if(start_replace) {
							String text;
							switch (r.getText(0)) {
							case "Направление_подготовки":
								text = r.getText(0).replace("Направление_подготовки", allCompetitiveGroups[i][0] != null ? allCompetitiveGroups[i][0] : "");
								r.setText(text, 0);
								break;
							case "Специальность":
								text = r.getText(0).replace("Специальность", allCompetitiveGroups[i][1] != null ? allCompetitiveGroups[i][1] : "");
								r.setText(text, 0);
								break;
							case "Кафедра":
								text = r.getText(0).replace("Кафедра", allCompetitiveGroups[i][2] != null ? allCompetitiveGroups[i][2] : "");
								r.setText(text, 0);
								break;
							case "Конкурсная_группа":
								text = r.getText(0).replace("Конкурсная_группа", allCompetitiveGroups[i][3] != null ? allCompetitiveGroups[i][3] : "");
								r.setText(text, 0);
								break;
							case "id":
								text = r.getText(0).replace("id", generalInfo[0] != null ? generalInfo[0] : "");
								r.setText(text, 0);
								break;
							case "Фамилия":
								text = r.getText(0).replace("Фамилия", generalInfo[1] != null ? generalInfo[1] : "");
								r.setText(text, 0);
								break;
							case "Имя":
								text = r.getText(0).replace("Имя", generalInfo[2] != null ? generalInfo[2] : "");
								r.setText(text, 0);
								break;
							case "Отчество":
								text = r.getText(0).replace("Отчество", generalInfo[3] != null ? generalInfo[3] : "");
								r.setText(text, 0);
								break;
							case "Дата_рождения":
								text = r.getText(0).replace("Дата_рождения", generalInfo[4] != null ? generalInfo[4] + " г." : "");
								r.setText(text, 0);
								break;
							case "Пол":
								text = r.getText(0).replace("Пол", generalInfo[5] != null ? generalInfo[5] : "");
								r.setText(text, 0);
								break;
							case "Гражданство":
								text = r.getText(0).replace("Гражданство", generalInfo[6] != null ? generalInfo[6] : "");
								r.setText(text, 0);
								break;
							case "Дата_зап":
								text = r.getText(0).replace("Дата_зап", generalInfo[7] != null ? generalInfo[7] + " г." : "");
								r.setText(text, 0);
								break;
							case "Общежитие":
								text = r.getText(0).replace("Общежитие", generalInfo[8]);
								r.setText(text, 0);
								break;
							case "паспорт":
								text = r.getText(0).replace("паспорт", passportData[0] != null ? passportData[0] : "");
								r.setText(text, 0);
								break;
							case "Серия":
								text = r.getText(0).replace("Серия", passportData[1] != null ? passportData[1] : "");
								r.setText(text, 0);
								break;
							case "Номер":
								text = r.getText(0).replace("Номер", passportData[2] != null ? passportData[2] : "");
								r.setText(text, 0);
								break;
							case "Когда_и_кем_выдан":
								text = r.getText(0).replace("Когда_и_кем_выдан", (passportData[3] != null ? passportData[3] + ", " : "") + (passportData[4] != null ? passportData[4] + " г." : ""));
								r.setText(text, 0);
								break;
							case "Место_рождения":
								text = r.getText(0).replace("Место_рождения", passportData[5] != null ? passportData[5] : "");
								r.setText(text, 0);
								break;
							case "Почтовый_адрес":
								text = r.getText(0).replace("Почтовый_адрес", (addressContacts[2] != null ? addressContacts[2] + ", " : "") + (addressContacts[0] != null ? addressContacts[0] + ", " : "") + (addressContacts[3] != null ? addressContacts[3] : ""));
								r.setText(text, 0);
								break;
							case "Электронный_адрес":
								text = r.getText(0).replace("Электронный_адрес", addressContacts[4] != null ? addressContacts[4] : "");
								r.setText(text, 0);
								break;
							case "Телефоны":
								text = r.getText(0).replace("Телефоны", addressContacts[5] != null ? addressContacts[5] : "");
								r.setText(text, 0);
								break;
							case "Диплом_серия":
								text = r.getText(0).replace("Диплом_серия", highEducation[1] != null ? highEducation[1] : "");
								r.setText(text, 0);
								break;
							case "Диплом_номер":
								text = r.getText(0).replace("Диплом_номер", highEducation[2] != null ? highEducation[2] : "");
								r.setText(text, 0);
								break;
							case "Диплом_специальность":
								text = r.getText(0).replace("Диплом_специальность", highEducation[3] != null ? highEducation[3] : "");
								r.setText(text, 0);
								break;
							case "Название_уч_заведения":
								text = r.getText(0).replace("Название_уч_заведения", highEducation[4] != null ? highEducation[4] : "");
								r.setText(text, 0);
								break;
							case "Год_оконч_ВУЗа":
								text = r.getText(0).replace("Год_оконч_ВУЗа", highEducation[5] != null ? highEducation[5] : "");
								r.setText(text, 0);
								break;
							case "Дипл_серия_интерн":
								text = r.getText(0).replace("Дипл_серия_интерн", postGraduateEducation[1] != null ? postGraduateEducation[1] : "");
								r.setText(text, 0);
								break;
							case "Дипл_номер_интерн":
								text = r.getText(0).replace("Дипл_номер_интерн", postGraduateEducation[2] != null ? postGraduateEducation[2] : "");
								r.setText(text, 0);
								break;
							case "Спец_интерн_дипл":
								text = r.getText(0).replace("Спец_интерн_дипл", postGraduateEducation[3] != null ? postGraduateEducation[3] : "");
								r.setText(text, 0);
								break;
							case "Название_интернатура":
								text = r.getText(0).replace("Название_интернатура", postGraduateEducation[4] != null ? postGraduateEducation[4] : "");
								r.setText(text, 0);
								break;
							case "Год_оконч_интерн":
								text = r.getText(0).replace("Год_оконч_интерн", postGraduateEducation[5] != null ? postGraduateEducation[5] : "");
								r.setText(text, 0);
								break;
							case "Спецусловия_испытаний":
								text = r.getText(0).replace("Спецусловия_испытаний", needSpecialConditions);
								r.setText(text, 0);
								break;
							case "Труды_и_др":
								text = r.getText(0).replace("Труды_и_др", hasPublications);
								r.setText(text, 0);
								break;
							case "Инд_достижения":
								String indAchivments = "";
								for(int j = 0; j < (indAchievments != null ? indAchievments.length : 0); j++)
									indAchivments += (indAchievments[j][1] + (Integer.valueOf(indAchievments[j][2]) > 0 ? " - " + indAchievments[j][2] + " балл;" : "; "));
								text = r.getText(0).replace("Инд_достижения", indAchivments);
								r.setText(text, 0);
								break;
							case "Док_подтв_инд_достиж":
								String indAchivmentsDocs = "";
								for(int j = 0; j < (indAchievments != null ? indAchievments.length : 0); j++)
									indAchivmentsDocs += ((indAchievments[j][3] != null ? indAchievments[j][3] + ", "
											: "")
											+ (indAchievments[j][4] != null ? "серия: " + indAchievments[j][4] + ", "
													: "")
											+ (indAchievments[j][5] != null ? "номер: " + indAchievments[j][5] + ", "
													: "")
											+ (indAchievments[j][6] != null || indAchievments[j][7] != null ? "выдан: "
													: "")
											+ (indAchievments[j][6] != null ? indAchievments[j][6] + ", " : "")
											+ (indAchievments[j][7] != null ? indAchievments[j][7] + " г.;" : ";"));
								text = r.getText(0).replace("Док_подтв_инд_достиж", (!indAchivmentsDocs.equals(";") ? indAchivmentsDocs : ""));
								r.setText(text, 0);
								break;
							case "}":
								start_replace = false;
								r.setText("", 0);
								break;
							}
						} else if(r.getText(0).equals("{")) {
								start_replace = true;
								r.setText("", 0);
						}
					}
				}
			}
	
			for (XWPFTable tbl : doc.getTables()) {
				for (XWPFTableRow row : tbl.getRows()) {
					for (XWPFTableCell cell : row.getTableCells()) {
						for (XWPFParagraph p : cell.getParagraphs()) {
							for (XWPFRun r : p.getRuns()) {
								if (r.getText(0) != null) {
									if(start_replace) {
										String text;
										switch (r.getText(0)) {
										case "Направление_подготовки":
											text = r.getText(0).replace("Направление_подготовки", allCompetitiveGroups[i][0] != null ? allCompetitiveGroups[i][0] : "");
											r.setText(text, 0);
											break;
										case "Специальность":
											text = r.getText(0).replace("Специальность", allCompetitiveGroups[i][1] != null ? allCompetitiveGroups[i][1] : "");
											r.setText(text, 0);
											break;
										case "Кафедра":
											text = r.getText(0).replace("Кафедра", allCompetitiveGroups[i][2] != null ? allCompetitiveGroups[i][2] : "");
											r.setText(text, 0);
											break;
										case "Конкурсная_группа":
											text = r.getText(0).replace("Конкурсная_группа", allCompetitiveGroups[i][3] != null ? allCompetitiveGroups[i][3] : "");
											r.setText(text, 0);
											break;
										case "id":
											text = r.getText(0).replace("id", generalInfo[0] != null ? generalInfo[0] : "");
											r.setText(text, 0);
											break;
										case "Фамилия":
											text = r.getText(0).replace("Фамилия", generalInfo[1] != null ? generalInfo[1] : "");
											r.setText(text, 0);
											break;
										case "Имя":
											text = r.getText(0).replace("Имя", generalInfo[2] != null ? generalInfo[2] : "");
											r.setText(text, 0);
											break;
										case "Отчество":
											text = r.getText(0).replace("Отчество", generalInfo[3] != null ? generalInfo[3] : "");
											r.setText(text, 0);
											break;
										case "Дата_рождения":
											text = r.getText(0).replace("Дата_рождения", generalInfo[4] != null ? generalInfo[4] + " г." : "");
											r.setText(text, 0);
											break;
										case "Пол":
											text = r.getText(0).replace("Пол", generalInfo[5] != null ? generalInfo[5] : "");
											r.setText(text, 0);
											break;
										case "Гражданство":
											text = r.getText(0).replace("Гражданство", generalInfo[6] != null ? generalInfo[6] : "");
											r.setText(text, 0);
											break;
										case "Дата_зап":
											text = r.getText(0).replace("Дата_зап", generalInfo[7] != null ? generalInfo[7] + " г." : "");
											r.setText(text, 0);
											break;
										case "Общежитие":
											text = r.getText(0).replace("Общежитие", generalInfo[8]);
											r.setText(text, 0);
											break;
										case "паспорт":
											text = r.getText(0).replace("паспорт", passportData[0] != null ? passportData[0] : "");
											r.setText(text, 0);
											break;
										case "Серия":
											text = r.getText(0).replace("Серия", passportData[1] != null ? passportData[1] : "");
											r.setText(text, 0);
											break;
										case "Номер":
											text = r.getText(0).replace("Номер", passportData[2] != null ? passportData[2] : "");
											r.setText(text, 0);
											break;
										case "Когда_и_кем_выдан":
											text = r.getText(0).replace("Когда_и_кем_выдан", (passportData[3] != null ? passportData[3] + ", " : "") + (passportData[4] != null ? passportData[4] + " г." : ""));
											r.setText(text, 0);
											break;
										case "Место_рождения":
											text = r.getText(0).replace("Место_рождения", passportData[5] != null ? passportData[5] : "");
											r.setText(text, 0);
											break;
										case "Почтовый_адрес":
											text = r.getText(0).replace("Почтовый_адрес", (addressContacts[2] != null ? addressContacts[2] + ", " : "") + (addressContacts[0] != null ? addressContacts[0] + ", " : "") + (addressContacts[3] != null ? addressContacts[3] : ""));
											r.setText(text, 0);
											break;
										case "Электронный_адрес":
											text = r.getText(0).replace("Электронный_адрес", addressContacts[4] != null ? addressContacts[4] : "");
											r.setText(text, 0);
											break;
										case "Телефоны":
											text = r.getText(0).replace("Телефоны", addressContacts[5] != null ? addressContacts[5] : "");
											r.setText(text, 0);
											break;
										case "Диплом_серия":
											text = r.getText(0).replace("Диплом_серия", highEducation[1] != null ? highEducation[1] : "");
											r.setText(text, 0);
											break;
										case "Диплом_номер":
											text = r.getText(0).replace("Диплом_номер", highEducation[2] != null ? highEducation[2] : "");
											r.setText(text, 0);
											break;
										case "Диплом_специальность":
											text = r.getText(0).replace("Диплом_специальность", highEducation[3] != null ? highEducation[3] : "");
											r.setText(text, 0);
											break;
										case "Название_уч_заведения":
											text = r.getText(0).replace("Название_уч_заведения", highEducation[4] != null ? highEducation[4] : "");
											r.setText(text, 0);
											break;
										case "Год_оконч_ВУЗа":
											text = r.getText(0).replace("Год_оконч_ВУЗа", highEducation[5] != null ? highEducation[5] : "");
											r.setText(text, 0);
											break;
										case "Дипл_серия_интерн":
											text = r.getText(0).replace("Дипл_серия_интерн", postGraduateEducation[1] != null ? postGraduateEducation[1] : "");
											r.setText(text, 0);
											break;
										case "Дипл_номер_интерн":
											text = r.getText(0).replace("Дипл_номер_интерн", postGraduateEducation[2] != null ? postGraduateEducation[2] : "");
											r.setText(text, 0);
											break;
										case "Спец_интерн_дипл":
											text = r.getText(0).replace("Спец_интерн_дипл", postGraduateEducation[3] != null ? postGraduateEducation[3] : "");
											r.setText(text, 0);
											break;
										case "Название_интернатура":
											text = r.getText(0).replace("Название_интернатура", postGraduateEducation[4] != null ? postGraduateEducation[4] : "");
											r.setText(text, 0);
											break;
										case "Год_оконч_интерн":
											text = r.getText(0).replace("Год_оконч_интерн", postGraduateEducation[5] != null ? postGraduateEducation[5] : "");
											r.setText(text, 0);
											break;
										case "Спецусловия_испытаний":
											text = r.getText(0).replace("Спецусловия_испытаний", needSpecialConditions);
											r.setText(text, 0);
											break;
										case "Труды_и_др":
											text = r.getText(0).replace("Труды_и_др", hasPublications);
											r.setText(text, 0);
											break;
										case "Инд_достижения":
											String indAchivments = "";
											for(int j = 0; j < (indAchievments != null ? indAchievments.length : 0); j++)
												indAchivments += (indAchievments[j][1] + (Integer.valueOf((indAchievments[j][2] != null ? indAchievments[j][2] : "0")) > 0 ? " - " + indAchievments[j][2] + " баллов;" : "; "));
											text = r.getText(0).replace("Инд_достижения", indAchivments);
											r.setText(text, 0);
											break;
										case "Док_подтв_инд_достиж":
											String indAchivmentsDocs = "";
											for(int j = 0; j < (indAchievments != null ? indAchievments.length : 0); j++)
												indAchivmentsDocs += ((indAchievments[j][3] != null
														? indAchievments[j][3] + ", " : "")
														+ (indAchievments[j][4] != null
																? "серия: " + indAchievments[j][4] + ", " : "")
														+ (indAchievments[j][5] != null
																? "номер: " + indAchievments[j][5] + ", " : "")
														+ (indAchievments[j][6] != null || indAchievments[j][7] != null
																? "выдан: " : "")
														+ (indAchievments[j][6] != null ? indAchievments[j][6] + ", "
																: "")
														+ (indAchievments[j][7] != null ? indAchievments[j][7] + " г.;"
																: ";"));
											text = r.getText(0).replace("Док_подтв_инд_достиж", (!indAchivmentsDocs.equals(";") ? indAchivmentsDocs : ""));
											r.setText(text, 0);
											break;
										case "}":
											start_replace = false;
											r.setText("", 0);
											break;
										}
									} else if(r.getText(0).equals("{")) {
											start_replace = true;
											r.setText("", 0);
									}
								}
							}
						}
					}
				}
			}
			copyElements(doc, doc_out);
		}

		File file = new File(currentPath + "/files/" + generalInfo[0] + "_statement.doc");

		if (file != null) {
			OutputStream outputStream = new FileOutputStream(new File(file.getAbsolutePath()));
			doc_out.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
		java.awt.Desktop.getDesktop().open(file);
	}

	public static void writeInventory(String[] generalInfo, String[] highEducation, String[] postGraduateEducation) throws Exception {
		XWPFDocument doc = new XWPFDocument(new FileInputStream(currentPath + "/Dots/Опись-расписка.dotx"));
		boolean start_replace = false;
		for (XWPFParagraph p : doc.getParagraphs()) {
			for (XWPFRun r : p.getRuns()) {
				if (r.getText(0) != null) {
					if(start_replace) {
						String text;
						switch (r.getText(0)) {
						case "id":
							text = r.getText(0).replace("id", generalInfo[0] != null ? generalInfo[0] : "");
							r.setText(text, 0);
							break;
						case "Фамилия":
							text = r.getText(0).replace("Фамилия", generalInfo[1] != null ? generalInfo[1] : "");
							r.setText(text, 0);
							break;
						case "Имя":
							text = r.getText(0).replace("Имя", generalInfo[2] != null ? generalInfo[2] : "");
							r.setText(text, 0);
							break;
						case "Отчество":
							text = r.getText(0).replace("Отчество", generalInfo[3] != null ? generalInfo[3] : "");
							r.setText(text, 0);
							break;
						case "Диплом_серия":
							text = r.getText(0).replace("Диплом_серия", highEducation[1] != null ? highEducation[1] : "");
							r.setText(text, 0);
							break;
						case "Диплом_номер":
							text = r.getText(0).replace("Диплом_номер", highEducation[2] != null ? highEducation[2] : "");
							r.setText(text, 0);
							break;
						case "Название_уч_заведения":
							text = r.getText(0).replace("Название_уч_заведения", highEducation[4] != null ? highEducation[4] : "");
							r.setText(text, 0);
							break;
						case "Год_оконч_ВУЗа":
							text = r.getText(0).replace("Год_оконч_ВУЗа", highEducation[5] != null ? highEducation[5] : "");
							r.setText(text, 0);
							break;
						case "Дипл_серия_интерн":
							text = r.getText(0).replace("Дипл_серия_интерн", postGraduateEducation[1] != null ? postGraduateEducation[1] : "");
							r.setText(text, 0);
							break;
						case "Дипл_номер_интерн":
							text = r.getText(0).replace("Дипл_номер_интерн", postGraduateEducation[2] != null ? postGraduateEducation[2] : "");
							r.setText(text, 0);
							break;
						case "Название_ординатура":
							text = r.getText(0).replace("Название_ординатура", postGraduateEducation[4] != null ? postGraduateEducation[4] : "");
							r.setText(text, 0);
							break;
						case "Год_оконч_интерн":
							text = r.getText(0).replace("Год_оконч_интерн", postGraduateEducation[5] != null ? postGraduateEducation[5] : "");
							r.setText(text, 0);
							break;
						case "}":
							start_replace = false;
							r.setText("", 0);
							break;
						}
					} else if(r.getText(0).equals("{")) {
							start_replace = true;
							r.setText("", 0);
					}
				}
			}
		}

		for (XWPFTable tbl : doc.getTables()) {
			for (XWPFTableRow row : tbl.getRows()) {
				for (XWPFTableCell cell : row.getTableCells()) {
					for (XWPFParagraph p : cell.getParagraphs()) {
						for (XWPFRun r : p.getRuns()) {
							if (r.getText(0) != null) {
								if(start_replace) {
									String text;
									switch (r.getText(0)) {
									case "id":
										text = r.getText(0).replace("id", generalInfo[0] != null ? generalInfo[0] : "");
										r.setText(text, 0);
										break;
									case "Фамилия":
										text = r.getText(0).replace("Фамилия", generalInfo[1] != null ? generalInfo[1] : "");
										r.setText(text, 0);
										break;
									case "Имя":
										text = r.getText(0).replace("Имя", generalInfo[2] != null ? generalInfo[2] : "");
										r.setText(text, 0);
										break;
									case "Отчество":
										text = r.getText(0).replace("Отчество", generalInfo[3] != null ? generalInfo[3] : "");
										r.setText(text, 0);
										break;
									case "Диплом_серия":
										text = r.getText(0).replace("Диплом_серия", highEducation[1] != null ? highEducation[1] : "");
										r.setText(text, 0);
										break;
									case "Диплом_номер":
										text = r.getText(0).replace("Диплом_номер", highEducation[2] != null ? highEducation[2] : "");
										r.setText(text, 0);
										break;
									case "Название_уч_заведения":
										text = r.getText(0).replace("Название_уч_заведения", highEducation[4] != null ? highEducation[4] : "");
										r.setText(text, 0);
										break;
									case "Год_оконч_ВУЗа":
										text = r.getText(0).replace("Год_оконч_ВУЗа", highEducation[5] != null ? highEducation[5] : "");
										r.setText(text, 0);
										break;
									case "Дипл_серия_интерн":
										text = r.getText(0).replace("Дипл_серия_интерн", postGraduateEducation[1] != null ? postGraduateEducation[1] : "");
										r.setText(text, 0);
										break;
									case "Дипл_номер_интерн":
										text = r.getText(0).replace("Дипл_номер_интерн", postGraduateEducation[2] != null ? postGraduateEducation[2] : "");
										r.setText(text, 0);
										break;
									case "Название_ординатура":
										text = r.getText(0).replace("Название_ординатура", postGraduateEducation[4] != null ? postGraduateEducation[4] : "");
										r.setText(text, 0);
										break;
									case "Год_оконч_интерн":
										text = r.getText(0).replace("Год_оконч_интерн", postGraduateEducation[5] != null ? postGraduateEducation[5] : "");
										r.setText(text, 0);
										break;
									case "}":
										start_replace = false;
										r.setText("", 0);
										break;
									}
								} else if(r.getText(0).equals("{")) {
										start_replace = true;
										r.setText("", 0);
								}
							}
						}
					}
				}
			}
		}

		File file = new File(currentPath + "/files/" + generalInfo[0] + "_inventory.doc");

		if (file != null) {
			OutputStream outputStream = new FileOutputStream(new File(file.getAbsolutePath()));
			doc.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
		java.awt.Desktop.getDesktop().open(file);
	}

	public static void writeExams(String[] generalInfo, ArrayList<String> specialities, ArrayList<String> examNames, ArrayList<String> examDates) throws Exception {
		XWPFDocument doc_out = new XWPFDocument();

		String moduleType = ModelDBConnection.getDBName().equals("Aspirant") ? "аспирантура" : "ординатура";
		int i = 0;
		File theDir = new File(currentPath + "/tmp_folder");
		theDir.mkdir();

		for (String speciality : specialities)
			for (String examName : examNames)
			{
				XWPFDocument doc = new XWPFDocument(new FileInputStream(currentPath + "/Dots/Лист_вступительных_" + moduleType +".dotm"));
				boolean start_replace = false;

				for (XWPFParagraph p : doc.getParagraphs()) {
					for (XWPFRun r : p.getRuns()) {
						if (r.getText(0) != null) {
							if(start_replace) {
								String text;
								switch (r.getText(0)) {
								case "id":
									text = r.getText(0).replace("id", generalInfo[0]);
									r.setText(text, 0);
									break;
								case "Фамилия":
									text = r.getText(0).replace("Фамилия", generalInfo[1]);
									r.setText(text, 0);
									break;
								case "Имя":
									text = r.getText(0).replace("Имя", generalInfo[2]);
									r.setText(text, 0);
									break;
								case "Отчество":
									text = r.getText(0).replace("Отчество", generalInfo[3]);
									r.setText(text, 0);
									break;
								case "Специальность":
									text = r.getText(0).replace("Специальность", speciality);
									r.setText(text, 0);
									break;
								case "Дата_проведения_теста":
									text = r.getText(0).replace("Дата_проведения_теста", examDates.get(examNames.indexOf(examName)));
									r.setText(text, 0);
									break;
								case "Дисциплина":
									text = r.getText(0).replace("Дисциплина", (!examName.equals("Специальная дисциплина") ? examName : ""));
									r.setText(text, 0);
									break;
								case "}":
									start_replace = false;
									r.setText("", 0);
									break;
								}
							} else if(r.getText(0).equals("{")) {
									start_replace = true;
									r.setText("", 0);
							}
						}
					}
				}

				for (XWPFTable tbl : doc.getTables()) {
					for (XWPFTableRow row : tbl.getRows()) {
						for (XWPFTableCell cell : row.getTableCells()) {
							for (XWPFParagraph p : cell.getParagraphs()) {
								for (XWPFRun r : p.getRuns()) {
									if (r.getText(0) != null) {
										if(start_replace) {
											String text;
											switch (r.getText(0)) {
											case "id":
												text = r.getText(0).replace("id", generalInfo[0]);
												r.setText(text, 0);
												break;
											case "Фамилия":
												text = r.getText(0).replace("Фамилия", generalInfo[1]);
												r.setText(text, 0);
												break;
											case "Имя":
												text = r.getText(0).replace("Имя", generalInfo[2]);
												r.setText(text, 0);
												break;
											case "Отчество":
												text = r.getText(0).replace("Отчество", generalInfo[3]);
												r.setText(text, 0);
												break;
											case "Специальность":
												text = r.getText(0).replace("Специальность", speciality);
												r.setText(text, 0);
												break;
											case "Дата_проведения_теста":
												text = r.getText(0).replace("Дата_проведения_теста", examDates.get(examNames.indexOf(examName)));
												r.setText(text, 0);
												break;
											case "Дисциплина":
												text = r.getText(0).replace("Дисциплина", (!examName.equals("Специальная дисциплина") ? examName : ""));
												r.setText(text, 0);
												break;
											case "}":
												start_replace = false;
												r.setText("", 0);
												break;
											}
										} else if(r.getText(0).equals("{")) {
												start_replace = true;
												r.setText("", 0);
										}
									}
								}
							}
							for (XWPFTable tbl1 : cell.getTables()) {
								for (XWPFTableRow row1 : tbl1.getRows()) {
									for (XWPFTableCell cell1 : row1.getTableCells()) {
										for (XWPFParagraph p1 : cell1.getParagraphs()) {
											for (XWPFRun r1 : p1.getRuns()) {
												if (r1.getText(0) != null) {
													if(start_replace) {
														String text;
														switch (r1.getText(0)) {
														case "id":
															text = r1.getText(0).replace("id", generalInfo[0]);
															r1.setText(text, 0);
															break;
														case "Фамилия":
															text = r1.getText(0).replace("Фамилия", generalInfo[1]);
															r1.setText(text, 0);
															break;
														case "Имя":
															text = r1.getText(0).replace("Имя", generalInfo[2]);
															r1.setText(text, 0);
															break;
														case "Отчество":
															text = r1.getText(0).replace("Отчество", generalInfo[3]);
															r1.setText(text, 0);
															break;
														case "Специальность":
															text = r1.getText(0).replace("Специальность", speciality);
															r1.setText(text, 0);
															break;
														case "Дата_проведения_теста":
															text = r1.getText(0).replace("Дата_проведения_теста", examDates.get(examNames.indexOf(examName)));
															r1.setText(text, 0);
															break;
														case "Дисциплина":
															text = r1.getText(0).replace("Дисциплина", (!examName.equals("Специальная дисциплина") ? examName : ""));
															r1.setText(text, 0);
															break;
														case "}":
															start_replace = false;
															r1.setText("", 0);
															break;
														}
													} else if(r1.getText(0).equals("{")) {
															start_replace = true;
															r1.setText("", 0);
													}
												}
											}
										}
										
									}
								}
							}
						}
					}
				}
				File file = new File(theDir.getAbsolutePath() + "/" + generalInfo[0] + "_exams" + (i == 0 ? "" : i) + ".doc");
				i++;

				if (file != null) {
					OutputStream outputStream = new FileOutputStream(new File(file.getAbsolutePath()));
					doc.write(outputStream);
					outputStream.flush();
					outputStream.close();
				}
			}

		if (i > 0)
			Runtime.getRuntime().exec("cmd /c start script.vbs " + currentPath + "\\ " + generalInfo[0] + "_exams.doc");
	}

	public static void writeTitul(String[] generalInfo, String[][] allCompetitiveGroups) throws Exception {
		XWPFDocument doc = new XWPFDocument(new FileInputStream(currentPath + "/Dots/Титульный_лист_дела.dotx"));

		String program = ModelDBConnection.getDBName().equals("Aspirant") ? "подготовка научно-педагогических кадров в аспирантуре" : "ординатура";
		boolean start_replace = false;

		for (XWPFParagraph p : doc.getParagraphs()) {
			for (XWPFRun r : p.getRuns()) {
				if (r.getText(0) != null) {
					if(start_replace) {
						String text;
						switch (r.getText(0)) {
						case "id":
							text = r.getText(0).replace("id", generalInfo[0] != null ? generalInfo[0] : "");
							r.setText(text, 0);
							break;
						case "Фамилия":
							text = r.getText(0).replace("Фамилия", generalInfo[1] != null ? generalInfo[1] : "");
							r.setText(text, 0);
							break;
						case "Имя":
							text = r.getText(0).replace("Имя", generalInfo[2] != null ? generalInfo[2] : "");
							r.setText(text, 0);
							break;
						case "Отчество":
							text = r.getText(0).replace("Отчество", generalInfo[3] != null ? generalInfo[3] : "");
							r.setText(text, 0);
							break;
						case "Дата_рождения":
							text = r.getText(0).replace("Дата_рождения", generalInfo[4] != null ? generalInfo[4] : "");
							r.setText(text, 0);
							break;
						case "Дата_зап":
							text = r.getText(0).replace("Дата_зап", generalInfo[5] != null ? generalInfo[5] + " г." : "");
							r.setText(text, 0);
							break;
						case "Программа":
							text = r.getText(0).replace("Программа", program != null ? program : "");
							r.setText(text, 0);
							break;
						case "Направление_подготовки":
							text = r.getText(0).replace("Направление_подготовки", allCompetitiveGroups[0][0] != null ? allCompetitiveGroups[0][0] : "");
							r.setText(text, 0);
							break;
						case "Специальность":
							text = r.getText(0).replace("Специальность", allCompetitiveGroups[0][1] != null ? allCompetitiveGroups[0][1] : "");
							r.setText(text, 0);
							break;
						case "Кафедра":
							text = r.getText(0).replace("Кафедра", allCompetitiveGroups[0][2] != null ? allCompetitiveGroups[0][2] : "");
							r.setText(text, 0);
							break;
						case "}":
							start_replace = false;
							r.setText("", 0);
							break;
						}
					} else if(r.getText(0).equals("{")) {
							start_replace = true;
							r.setText("", 0);
					}
				}
			}
		}

		for (XWPFTable tbl : doc.getTables()) {
			for (XWPFTableRow row : tbl.getRows()) {
				for (XWPFTableCell cell : row.getTableCells()) {
					for (XWPFParagraph p : cell.getParagraphs()) {
						for (XWPFRun r : p.getRuns()) {
							if (r.getText(0) != null) {
								if(start_replace) {
									String text;
									switch (r.getText(0)) {
									case "id":
										text = r.getText(0).replace("id", generalInfo[0] != null ? generalInfo[0] : "");
										r.setText(text, 0);
										break;
									case "Фамилия":
										text = r.getText(0).replace("Фамилия", generalInfo[1] != null ? generalInfo[1] : "");
										r.setText(text, 0);
										break;
									case "Имя":
										text = r.getText(0).replace("Имя", generalInfo[2] != null ? generalInfo[2] : "");
										r.setText(text, 0);
										break;
									case "Отчество":
										text = r.getText(0).replace("Отчество", generalInfo[3] != null ? generalInfo[3] : "");
										r.setText(text, 0);
										break;
									case "Дата_рождения":
										text = r.getText(0).replace("Дата_рождения", generalInfo[4] != null ? generalInfo[4] : "");
										r.setText(text, 0);
										break;
									case "Дата_зап":
										text = r.getText(0).replace("Дата_зап", generalInfo[5] != null ? generalInfo[5] + " г." : "");
										r.setText(text, 0);
										break;
									case "Программа":
										text = r.getText(0).replace("Программа", program != null ? program : "");
										r.setText(text, 0);
										break;
									case "Направление_подготовки":
										text = r.getText(0).replace("Направление_подготовки", allCompetitiveGroups[0][0] != null ? allCompetitiveGroups[0][0] : "");
										r.setText(text, 0);
										break;
									case "Специальность":
										text = r.getText(0).replace("Специальность", allCompetitiveGroups[0][1] != null ? allCompetitiveGroups[0][1] : "");
										r.setText(text, 0);
										break;
									case "Кафедра":
										text = r.getText(0).replace("Кафедра", allCompetitiveGroups[0][2] != null ? allCompetitiveGroups[0][2] : "");
										r.setText(text, 0);
										break;
									case "}":
										start_replace = false;
										r.setText("", 0);
										break;
									}
								} else if(r.getText(0).equals("{")) {
										start_replace = true;
										r.setText("", 0);
								}
							}
						}
					}
				}
			}
		}

		File file = new File(currentPath + "/files/" + generalInfo[0] + "_titul.doc");

		if (file != null) {
			OutputStream outputStream = new FileOutputStream(new File(file.getAbsolutePath()));
			doc.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
		java.awt.Desktop.getDesktop().open(file);
	}

	private static void copyElements(XWPFDocument doc_input, XWPFDocument doc_output) {
		int i = doc_output.getParagraphs().size();
		int j = doc_output.getTables().size();
		for (IBodyElement e : doc_input.getBodyElements()) {
			if (e instanceof XWPFParagraph) {
				XWPFParagraph p = (XWPFParagraph) e;
				if (p.getCTP().getPPr() != null && p.getCTP().getPPr().getSectPr() != null) {
					continue;
				} else {
					doc_output.createParagraph();
					doc_output.setParagraph(p, i);
					i++;
				}
			} else if (e instanceof XWPFTable) {
				XWPFTable t = (XWPFTable) e;
				doc_output.createTable();
				doc_output.setTable(j, t);
				j++;
			}
		}
		doc_output.createParagraph().createRun().addBreak(BreakType.PAGE);
		i++;
	}
}
