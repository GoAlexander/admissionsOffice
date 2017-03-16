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

public class OutputWord {
	//Прототип функции создания заявлений
	//Need: скорректировать список входных параметров
	//		изменить блоки switch
	public static void writeStatement(String[] generalInfo, ArrayList<String> specialities, ArrayList<String> examDates) throws Exception {
		XWPFDocument doc_out = new XWPFDocument();
		int i = 0;
		for (String speciality : specialities)
			for (String examDate : examDates)
			{
				XWPFDocument doc = new XWPFDocument(new FileInputStream("./Dots/Заявление_ординатура.dotx"));
				boolean start_replace = false;

				for (XWPFParagraph p : doc.getParagraphs()) {
					for (XWPFRun r : p.getRuns()) {
						if (r.getText(0) != null) {
							if(start_replace) {
								String text;
								switch (r.getText(0)) {
								case "ID":
									text = r.getText(0).replace("ID", generalInfo[0]);
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
									text = r.getText(0).replace("Дата_проведения_теста", examDate);
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
									System.out.println(r.getText(0));
									if (r.getText(0) != null) {
										if(start_replace) {
											String text;
											switch (r.getText(0)) {
											case "ID":
												text = r.getText(0).replace("ID", generalInfo[0]);
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
												text = r.getText(0).replace("Дата_проведения_теста", examDate);
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

		File file = new File(generalInfo[0] + "_statement.doc");

		if (file != null) {
			OutputStream outputStream = new FileOutputStream(new File(file.getAbsolutePath()));
			doc_out.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
		java.awt.Desktop.getDesktop().open(file);
	}

	public static void writeExams(String[] generalInfo, ArrayList<String> specialities, ArrayList<String> examDates) throws Exception {
		XWPFDocument doc_out = new XWPFDocument();
		int i = 0;
		File theDir = new File("tmp_folder");
		theDir.mkdir();

		for (String speciality : specialities)
			for (String examDate : examDates)
			{
				XWPFDocument doc = new XWPFDocument(new FileInputStream("./Dots/Лист_вступительных_испытаний.dotm"));
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
									text = r.getText(0).replace("Дата_проведения_теста", examDate);
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
									System.out.println(r.getText(0));
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
												text = r.getText(0).replace("Дата_проведения_теста", examDate);
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
												System.out.println(r1.getText(0));
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
															text = r1.getText(0).replace("Дата_проведения_теста", examDate);
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
				File file = new File(theDir.getAbsolutePath() + "\\" + generalInfo[0] + "_exams" + (i == 0 ? "" : i) + ".doc");
				i++;

				if (file != null) {
					OutputStream outputStream = new FileOutputStream(new File(file.getAbsolutePath()));
					doc.write(outputStream);
					outputStream.flush();
					outputStream.close();
				}
			}

		if (i > 0)
			Runtime.getRuntime().exec("cmd /c start script.vbs " + theDir.getAbsolutePath().substring(0, theDir.getAbsolutePath().lastIndexOf("tmp_folder")) + " " + generalInfo[0] + "_exams.doc");
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
