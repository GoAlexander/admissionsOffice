package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import backend.MessageProcessing;
import backend.ModelDBConnection;

import tab_education.EducationPanel;
import tab_entrance_tests.EntranceTestsPanel;
import tab_passport.PassportPanel;
import tab_address_contacts.AddressContactsPanel;
import tab_catalogs.EditAdmissionPlan;
import tab_catalogs.EditMenuListener;
import tab_competitive_groups.CompetitiveGroupsPanel;
import tab_achievements.IndividualAchievementsPanel;

import general_classes.GUITableModel;
import outputDoc.OutputExcel;
import outputDoc.OutputExportFiles;
import outputDoc.OutputWord;

public class GeneralInfoInput extends JFrame {

	private JPanel mainPanel, centralPanel, GIPanel, panelSurname, panelName, panelPatronymic, panelID, panelSex,
			panelDB, panelNationality, panelInfoBackDoc, panelReturnReason, panelDateReturn, tablePanel, btnTablePanel,
			compGroupPanel, entranceTestpPanel, indAchivPanel, educPanel, contPanel, passportPanel, panelDateDoc;
	private Dimension dimPanel = new Dimension(300, 40);
	private Dimension dimText = new Dimension(170, 25);
	private Dimension dimRigidArea = new Dimension(10, 0);
	private JTextField textID, textDateReturn;
	private GridBagConstraints gbc;

	private String[] columnNames = { "№", "Фамилия", "Имя", "Отчество" };
	private GUITableModel currentTM = new GUITableModel();
	private JTable dataTable;
	private JCheckBox checkBackDoc;
	private JTabbedPane userInfoTPane;

	private JComboBox comboSexList, comboNationality, comboReturnReason;
	private JDateChooser calendar;

	private String[] arrSex, arrNationality, arrReturnReason;

	private JMenuBar menuBar;
	private JMenu directoryMenu, docMenu, reportMenu, compMenu, expMenu;
	private JMenuItem docMenuApplication, docMenuTitul, docMenuOpRasp, docMenuListEntranceExam, directoryMenuEntranceTest,
			directoryMenuBlockTest, directoryMenuBaseMark, directoryMenuTypePasport, directoryMenuRegion,
			directoryMenuTypeSettlements, directoryMenuSex, directoryMenuNationality, directoryMenuReturnReason,
			directoryMenuIndividual, directoryMenuStudyFields, directoryMenuEducForm, directoryMenuSpeciality,
			directoryMenuDepartments, directoryMenuCompetitiveGroup, directoryMenuOrganisations,
			directoryMenuEducStandard, directoryMenuPlan, directoryMenuUsers, reportMenuListCandidates,
			reportMenuListGroups, reportMenuResults, reportMenuAdmissionPlan, reportMenuStatistics, compMenuCalculation, compMenuPlayCompet,
			compMenuInternalRating, compMenuRankedList, compMenuCompetList, compMenuResetCompetResults,
			expMenuOrganisations, expMenuCompetGroup, expMenuPlan, expMenuApplications, expMenuResults;

	private JButton addButton, editButton, deleteButton;

	public GeneralInfoInput() {
		// ----------------------
		// Временно
		ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Ordinator", "user", "password");
		ModelDBConnection.initConnection();
		// ----------------------

		arrSex = ModelDBConnection.getNamesFromTableOrderedById("Gender");
		arrNationality = ModelDBConnection.getNamesFromTableOrderedById("Nationality");
		arrReturnReason = ModelDBConnection.getNamesFromTableOrderedById("ReturnReasons");
		ModelDBConnection.getNamesFromTableOrderedById("PassportType");

		setTitle("General Info");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		setLocation(0, 0);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		getContentPane().add(mainPanel);

		menuBar = new JMenuBar();
		directoryMenu = new JMenu("Справочники");
		menuBar.add(directoryMenu);
		directoryMenuEntranceTest = new JMenuItem("Вступительные испытания");
		directoryMenuEntranceTest.addActionListener(new EditMenuListener("EntranceTest", "Вступительные испытания"));
		directoryMenu.add(directoryMenuEntranceTest);
		directoryMenuBlockTest = new JMenuItem("Блоки испытаний");
		directoryMenuBlockTest.addActionListener(new EditMenuListener("TestBox", "Блоки испытаний"));
		directoryMenu.add(directoryMenuBlockTest);
		directoryMenuBaseMark = new JMenuItem("Основания для оценки");
		directoryMenuBaseMark.addActionListener(new EditMenuListener("AssessmentBase", "Основания для оценки"));
		directoryMenu.add(directoryMenuBaseMark);
		directoryMenu.addSeparator();
		directoryMenuTypePasport = new JMenuItem("Типы паспортов");
		directoryMenuTypePasport.addActionListener(new EditMenuListener("PassportType", "Типы паспортов"));
		directoryMenu.add(directoryMenuTypePasport);
		directoryMenuRegion = new JMenuItem("Регионы");
		directoryMenuRegion.addActionListener(new EditMenuListener("Region", "Регионы"));
		directoryMenu.add(directoryMenuRegion);
		directoryMenuTypeSettlements = new JMenuItem("Типы населенных пунктов");
		directoryMenuTypeSettlements.addActionListener(new EditMenuListener("LocalityType", "Типы населенных пунктов"));
		directoryMenu.add(directoryMenuTypeSettlements);
		directoryMenu.addSeparator();
		directoryMenuSex = new JMenuItem("Пол");
		directoryMenuSex.addActionListener(new EditMenuListener("Gender", "Пол"));
		directoryMenu.add(directoryMenuSex);
		directoryMenuNationality = new JMenuItem("Гражданство");
		directoryMenuNationality.addActionListener(new EditMenuListener("Nationality", "Гражданство"));
		directoryMenu.add(directoryMenuNationality);
		directoryMenu.addSeparator();
		directoryMenuReturnReason = new JMenuItem("Причины возврата");
		directoryMenuReturnReason.addActionListener(new EditMenuListener("ReturnReasons", "Причины возврата"));
		directoryMenu.add(directoryMenuReturnReason);
		directoryMenu.addSeparator();
		directoryMenuIndividual = new JMenuItem("Индивидуальные достижения");
		directoryMenuIndividual.addActionListener(new EditMenuListener("IndividualAchievement", "Индивидуальные достижения"));
		directoryMenu.add(directoryMenuIndividual);
		directoryMenu.addSeparator();
		directoryMenuStudyFields = new JMenuItem("Направления обучения");
		directoryMenuStudyFields.addActionListener(new EditMenuListener("Course", "Направления обучения"));
		directoryMenu.add(directoryMenuStudyFields);
		directoryMenuEducForm = new JMenuItem("Формы обучения");
		directoryMenuEducForm.addActionListener(new EditMenuListener("EducationForm", "Формы обучения"));
		directoryMenu.add(directoryMenuEducForm);
		directoryMenuSpeciality = new JMenuItem("Специальности");
		directoryMenuSpeciality.addActionListener(new EditMenuListener("Speciality", "Специальности"));
		directoryMenu.add(directoryMenuSpeciality);
		directoryMenuDepartments = new JMenuItem("Кафедры");
		directoryMenuDepartments.addActionListener(new EditMenuListener("Chair", "Кафедры"));
		directoryMenu.add(directoryMenuDepartments);
		directoryMenuCompetitiveGroup = new JMenuItem("Конкурсные группы");
		directoryMenuCompetitiveGroup.addActionListener(new EditMenuListener("CompetitiveGroup", "Конкурсные группы"));
		directoryMenu.add(directoryMenuCompetitiveGroup);
		directoryMenuOrganisations = new JMenuItem("Целевые организации");
		directoryMenuOrganisations.addActionListener(new EditMenuListener("TargetOrganisation", "Целевые организации"));
		directoryMenu.add(directoryMenuOrganisations);
		directoryMenuEducStandard = new JMenuItem("Стандарты образования");
		directoryMenuEducStandard.addActionListener(new EditMenuListener("EducationStandard", "Стандарты образования"));
		directoryMenu.add(directoryMenuEducStandard);
		directoryMenu.addSeparator();
		directoryMenuPlan = new JMenuItem("План приема");
		directoryMenuPlan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				EditAdmissionPlan window = new EditAdmissionPlan();
				window.setVisible(true);
			}
		});
		directoryMenu.add(directoryMenuPlan);
		directoryMenu.addSeparator();
		directoryMenuUsers = new JMenuItem("Пользователи");
		directoryMenuUsers.addActionListener(new EditMenuListener("Users", "Пользователи"));
		directoryMenu.add(directoryMenuUsers);

		docMenu = new JMenu("Документы");
		menuBar.add(docMenu);
		docMenuApplication = new JMenuItem("Заявление");
		docMenuApplication.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					String[] generalInfo = new String[8];
					generalInfo[0] = ((JTextField) panelID.getComponent(1)).getText();
					generalInfo[1] = ((JTextField) panelSurname.getComponent(1)).getText();
					generalInfo[2] = ((JTextField) panelName.getComponent(1)).getText();
					generalInfo[3] = ((JTextField) panelPatronymic.getComponent(1)).getText();
					generalInfo[4] = new SimpleDateFormat("dd.MM.yyyy").format(calendar.getDate()).toString();
					generalInfo[5] = String.valueOf(comboSexList.getSelectedItem().toString());
					generalInfo[6] = String.valueOf(comboNationality.getSelectedItem().toString());
					generalInfo[7] = ((JLabel) panelDateDoc.getComponent(1)).getText();

					String[] passportData = ((PassportPanel)passportPanel).getValues(true);
					String[] addressContacts = ((AddressContactsPanel)contPanel).getValues(true);
					String[] highEducation = ((EducationPanel)educPanel).getValues(0);
					String[] postGraduateEducation = ModelDBConnection.getDBName().equals("Ordinator") ? null : ((EducationPanel)educPanel).getValues(1);
					String needSpecialConditions = ((EntranceTestsPanel)entranceTestpPanel).getNeedSpecialConditions();
					String[][] indAchievments = ((IndividualAchievementsPanel)indAchivPanel).getValues(true);
					String[][] allCompetitiveGroups = ((CompetitiveGroupsPanel)compGroupPanel).getAllCompetitiveGroups();

					if (allCompetitiveGroups.length == 0)
						MessageProcessing.displayErrorMessage(null, 5);
					else
						OutputWord.writeStatement(allCompetitiveGroups, generalInfo, passportData, addressContacts, highEducation, postGraduateEducation, needSpecialConditions, indAchievments);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		docMenu.add(docMenuApplication);
		docMenuOpRasp = new JMenuItem("Опись/расписка");
		docMenuOpRasp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					String[] generalInfo = new String[4];
					generalInfo[0] = ((JTextField) panelID.getComponent(1)).getText();
					generalInfo[1] = ((JTextField) panelSurname.getComponent(1)).getText();
					generalInfo[2] = ((JTextField) panelName.getComponent(1)).getText();
					generalInfo[3] = ((JTextField) panelPatronymic.getComponent(1)).getText();

					String[] highEducation = ((EducationPanel)educPanel).getValues(0);
					String[] postGraduateEducation = ((EducationPanel)educPanel).getValues(1);

					OutputWord.writeInventory(generalInfo, highEducation, postGraduateEducation);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		docMenu.add(docMenuOpRasp);
		docMenuListEntranceExam = new JMenuItem("Лист вступительных испытаний");
		docMenuListEntranceExam.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					String[] generalInfo = new String[4];
					generalInfo[0] = ((JTextField) panelID.getComponent(1)).getText();
					generalInfo[1] = ((JTextField) panelSurname.getComponent(1)).getText();
					generalInfo[2] = ((JTextField) panelName.getComponent(1)).getText();
					generalInfo[3] = ((JTextField) panelPatronymic.getComponent(1)).getText();

					if (((CompetitiveGroupsPanel)compGroupPanel).getSpecialities().size() == 0)
						MessageProcessing.displayErrorMessage(null, 5);
					else if (((EntranceTestsPanel)entranceTestpPanel).getExamsDates().size() == 0)
						MessageProcessing.displayErrorMessage(null, 6);
					else
						OutputWord.writeExams(generalInfo, ((CompetitiveGroupsPanel)compGroupPanel).getSpecialities(), ((EntranceTestsPanel)entranceTestpPanel).getExamsNames(), ((EntranceTestsPanel)entranceTestpPanel).getExamsDates());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		docMenu.add(docMenuListEntranceExam);
		docMenuTitul = new JMenuItem("Титульный лист");
		docMenuTitul.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					String[] generalInfo = new String[6];
					generalInfo[0] = ((JTextField) panelID.getComponent(1)).getText();
					generalInfo[1] = ((JTextField) panelSurname.getComponent(1)).getText();
					generalInfo[2] = ((JTextField) panelName.getComponent(1)).getText();
					generalInfo[3] = ((JTextField) panelPatronymic.getComponent(1)).getText();
					generalInfo[4] = new SimpleDateFormat("dd.MM.yyyy").format(calendar.getDate()).toString();
					generalInfo[5] = ((JLabel) panelDateDoc.getComponent(1)).getText();

					String[][] allCompetitiveGroups = ((CompetitiveGroupsPanel)compGroupPanel).getAllCompetitiveGroups();

					if (allCompetitiveGroups.length == 0)
						MessageProcessing.displayErrorMessage(null, 5);
					else
						OutputWord.writeTitul(generalInfo, allCompetitiveGroups);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		docMenu.add(docMenuTitul);

		reportMenu = new JMenu("Отчетность");
		menuBar.add(reportMenu);
		reportMenuListCandidates = new JMenuItem("Список подавших документы");
		reportMenu.add(reportMenuListCandidates);
		reportMenuListCandidates.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					OutputExcel.writeListOfSubmittedDocuments();
					JOptionPane.showMessageDialog(null, "Список подавших документы успешно сформирован!", "Результат вывода списка подавших документы", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		});
		reportMenuListGroups = new JMenuItem("Списки групп на вступительные");
		reportMenu.add(reportMenuListGroups);
		reportMenuListGroups.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					OutputExcel.writeListGroupsOnEntranceTests();
					JOptionPane.showMessageDialog(null, "Списки групп вступительных испытаний успешно сформирован!", "Результат вывода списков групп вступительных испытаний", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		});
		reportMenuResults = new JMenuItem("Результаты вступительных");
		reportMenu.add(reportMenuResults);
		reportMenuResults.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					OutputExcel.writeResultsEntranceTest();
					JOptionPane.showMessageDialog(null, "Список результатов вступительных испытаний успешно сформирован!", "Результат вывода итогов вступительных испытаний", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		});
		reportMenu.addSeparator();
		reportMenuAdmissionPlan = new JMenuItem("План приема");
		reportMenu.add(reportMenuAdmissionPlan);
		reportMenuAdmissionPlan.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					OutputExcel.writeAdmissionPlan();
					JOptionPane.showMessageDialog(null, "План приема успешно сформирован!", "Результат вывода плана приема", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		});
		reportMenu.addSeparator();
		reportMenuStatistics = new JMenuItem("Статистика");
		reportMenu.add(reportMenuStatistics);

		compMenu = new JMenu("Конкурс");
		menuBar.add(compMenu);
		compMenuCalculation = new JMenuItem("Предварительные расчеты");
		compMenu.add(compMenuCalculation);
		compMenu.addSeparator();
		compMenuPlayCompet = new JMenuItem("Сыграть конкурс");
		compMenu.add(compMenuPlayCompet);
		compMenu.addSeparator();
		compMenuInternalRating = new JMenuItem("Внутренний рейтинг");
		compMenu.add(compMenuInternalRating);
		compMenuRankedList = new JMenuItem("Ранжированный список");
		compMenu.add(compMenuRankedList);
		compMenu.addSeparator();
		compMenuCompetList = new JMenuItem("Конкурсный список");
		compMenu.add(compMenuCompetList);
		compMenu.addSeparator();
		compMenuResetCompetResults = new JMenuItem("Сброс результатов конкурса");
		compMenu.add(compMenuResetCompetResults);

		expMenu = new JMenu("Экспорт");
		menuBar.add(expMenu);
		expMenuOrganisations = new JMenuItem("Целевые организации");
		expMenu.add(expMenuOrganisations);
		expMenuOrganisations.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					OutputExportFiles.printTargetOrganisations();
					JOptionPane.showMessageDialog(null, "Файл для выгрузки целевых организаций успешно сформирован!", "Результат экспорта целевых организаций", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		});
		expMenuCompetGroup = new JMenuItem("Конкурсные группы");
		expMenu.add(expMenuCompetGroup);
		expMenuCompetGroup.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					OutputExportFiles.printCompetitiveGroups();
					JOptionPane.showMessageDialog(null, "Файл для выгрузки конкурсных групп успешно сформирован!", "Результат экспорта конкурсных групп", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		});
		expMenuPlan = new JMenuItem("План приема");
		expMenu.add(expMenuPlan);
		expMenuPlan.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					OutputExportFiles.printAssesmentPlanGeneral();
					JOptionPane.showMessageDialog(null, "Файл для выгрузки плана приема успешно сформирован!", "Результат экспорта плана приема", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		});
		expMenuApplications = new JMenuItem("Заявления абитуриентов");
		expMenu.add(expMenuApplications);
		expMenuResults = new JMenuItem("Результаты конкурса");
		expMenu.add(expMenuResults);

		mainPanel.add(menuBar, BorderLayout.PAGE_START);

		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		dataTable = new JTable(currentTM);
		dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		currentTM.setDataVector(ModelDBConnection.getAllAbiturients(), columnNames);

        TableRowSorter sorter=new TableRowSorter(currentTM);
        sorter.setSortable(0, true);
        sorter.setSortable(1, true);
        sorter.setSortable(2, false);
        sorter.setSortable(3, false);
        dataTable.setRowSorter(sorter); 
		JScrollPane scrPane = new JScrollPane(dataTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		tablePanel.add(scrPane, BorderLayout.CENTER);

		btnTablePanel = new JPanel();
		btnTablePanel.setLayout(new FlowLayout());

		addButton = new JButton("Добавить");
		addButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addButtonActionPerformed(evt);
			}
		});

		editButton = new JButton("Редактировать");
		editButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editButtonActionPerformed(evt);
			}
		});

		deleteButton = new JButton("Удалить");
		deleteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteButtonActionPerformed(evt);
			}
		});

		btnTablePanel.add(addButton);
		btnTablePanel.add(editButton);
		btnTablePanel.add(deleteButton);

		tablePanel.add(btnTablePanel, BorderLayout.PAGE_END);
		mainPanel.add(tablePanel, BorderLayout.LINE_START);

		dataTable.getColumnModel().getColumn(0).setMaxWidth(40);

		centralPanel = new JPanel();
		centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
		mainPanel.add(centralPanel, BorderLayout.CENTER);

		JPanel GIPanelMain = new JPanel();
		GIPanelMain.setLayout(new BoxLayout(GIPanelMain, BoxLayout.Y_AXIS));
		GIPanelMain.setBorder(
				new TitledBorder(null, "Основная информация", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		centralPanel.add(GIPanelMain);

		GIPanel = new JPanel();
		GIPanel.setLayout(new GridBagLayout());
		GIPanelMain.add(GIPanel);

		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 0, 5, 40);

		panelID = new JPanel();
		panelID.setLayout(new FlowLayout());
		panelID.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelID.setMaximumSize(dimPanel);
		JLabel idLabel = new JLabel("№:  ");
		panelID.add(idLabel);
		textID = new JTextField();
		textID.setPreferredSize(new Dimension(60, 25));
		textID.setEnabled(false);
		panelID.add(textID);
		GIPanel.add(panelID, gbc);

		panelDateDoc = new JPanel();
		panelDateDoc.setLayout(new FlowLayout());
		panelDateDoc.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelDateDoc.setMaximumSize(dimPanel);
		JLabel dateDocLabel = new JLabel("Дата приёма документов:  ");
		panelDateDoc.add(dateDocLabel);
		JLabel dateCurrDocLabel = new JLabel();
		panelDateDoc.add(dateCurrDocLabel);
		GIPanel.add(panelDateDoc, gbc);

		panelSurname = new JPanel();
		panelSurname = createFIOPanel("Фамилия:     ");
		gbc.gridy = 1;
		GIPanel.add(panelSurname, gbc);

		panelName = new JPanel();
		panelName = createFIOPanel("Имя:                ");
		gbc.gridy = 2;
		GIPanel.add(panelName, gbc);

		panelPatronymic = new JPanel();
		panelPatronymic = createFIOPanel("Отчество:      ");
		gbc.gridy = 3;
		GIPanel.add(panelPatronymic, gbc);

		panelSex = new JPanel();
		panelSex.setLayout(new FlowLayout());
		panelSex.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelSex.setMaximumSize(dimPanel);
		JLabel sexLabel = new JLabel("Пол:                        ");
		panelSex.add(sexLabel);
		comboSexList = new JComboBox(arrSex);
		comboSexList.setSelectedIndex(-1);
		panelSex.add(comboSexList);
		gbc.gridx = 1;
		gbc.gridy = 1;
		GIPanel.add(panelSex, gbc);

		panelDB = new JPanel();
		panelDB.setLayout(new FlowLayout());
		panelDB.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelDB.setMaximumSize(dimPanel);
		JLabel dbLabel = new JLabel("Дата Рождения:  ");
		panelDB.add(dbLabel);
		calendar = new JDateChooser("dd.MM.yyyy", "##.##.####", '_');
		calendar.setFont(new Font("Dialog", Font.PLAIN, 11));
		panelDB.add(calendar);
		gbc.gridy = 2;
		GIPanel.add(panelDB, gbc);

		panelNationality = new JPanel();
		panelNationality.setLayout(new FlowLayout());
		panelNationality.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelNationality.setMaximumSize(dimPanel);
		JLabel nationalityLabel = new JLabel("Гражданство:      ");
		panelNationality.add(nationalityLabel);
		comboNationality = new JComboBox(arrNationality);
		comboNationality.setSelectedIndex(-1);
		comboNationality.setPreferredSize(dimText);
		panelNationality.add(comboNationality);
		gbc.gridy = 3;
		GIPanel.add(panelNationality, gbc);

		panelInfoBackDoc = new JPanel();
		panelInfoBackDoc.setBorder(new TitledBorder(null, "Сведения о возврате документов", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		GIPanelMain.add(panelInfoBackDoc);
		panelInfoBackDoc.setLayout(new GridBagLayout());

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.anchor = GridBagConstraints.WEST;
		gbc2.insets = new Insets(0, 0, 5, 65);

		panelReturnReason = new JPanel();
		panelReturnReason.setLayout(new FlowLayout());

		JLabel returnReasonlbl = new JLabel("Причина возврата   ");
		panelReturnReason.add(returnReasonlbl);

		comboReturnReason = new JComboBox(arrReturnReason);
		comboReturnReason.setSelectedIndex(-1);
		panelReturnReason.add(comboReturnReason);

		panelInfoBackDoc.add(panelReturnReason, gbc2);

		panelDateReturn = new JPanel();
		panelDateReturn.setLayout(new FlowLayout());

		panelDateReturn.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelDateReturn.setMaximumSize(dimPanel);
		JLabel dateReturnLabel = new JLabel("Дата возврата документов:     ");
		panelDateReturn.add(dateReturnLabel);
		try {
			MaskFormatter mf;
			mf = new MaskFormatter("##.##.####");
			mf.setPlaceholderCharacter('_');
			textDateReturn = new JFormattedTextField(mf);
		} catch (ParseException e1) {
			textDateReturn = new JTextField();
		}
		textDateReturn.setPreferredSize(dimText);
		panelDateReturn.add(textDateReturn);
		checkBackDoc = new JCheckBox("Забрал документы");
		checkBackDoc.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (checkBackDoc.isSelected()) {
					textDateReturn.setEnabled(true);
					comboReturnReason.setEnabled(true);
					comboReturnReason.setEditable(false);
					if (textDateReturn.getText().equals("__.__.____")) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
						textDateReturn.setText(sdf.format(new Date()));
					}
				} else {
					textDateReturn.setText(null);
					comboReturnReason.setSelectedIndex(-1);
					textDateReturn.setEnabled(false);
					comboReturnReason.setEnabled(false);
					comboReturnReason.setEditable(true);
				}
			}
		});
		gbc2.gridy = 1;
		panelInfoBackDoc.add(panelDateReturn, gbc2);
		gbc2.gridx = 1;
		panelInfoBackDoc.add(checkBackDoc, gbc2);

		userInfoTPane = new JTabbedPane();
		compGroupPanel = new CompetitiveGroupsPanel("0");
		entranceTestpPanel = new EntranceTestsPanel();
		indAchivPanel = new IndividualAchievementsPanel();
		educPanel = new EducationPanel();
		contPanel = new AddressContactsPanel();
		passportPanel = new PassportPanel();

		userInfoTPane.add("Конк-ные группы", compGroupPanel);
		userInfoTPane.add("Вступ-ные испытания", entranceTestpPanel);
		userInfoTPane.add("Инд-ные достижения", indAchivPanel);
		userInfoTPane.add("Образование", educPanel);
		userInfoTPane.add("Адрес и контакты", contPanel);
		userInfoTPane.add("Паспорт", passportPanel);
		userInfoTPane.setPreferredSize(new Dimension(300, 375));

		centralPanel.add(userInfoTPane);

		setPreferredSize(new Dimension(1100, 740));
		pack();

		dataTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					updateTab("GeneralInfo");
					updateTab("Passport");
					updateTab("AddressContacts");
					updateTab("Education");
					updateTab("EntranceTests");
					updateTab("IndividualAchievements");
					updateTab("CompetitiveGroups");
					setEditable(false);
				}
			}
		});

		//this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent event) {}
			public void windowClosed(WindowEvent event) {}
			public void windowDeactivated(WindowEvent event) {}
			public void windowDeiconified(WindowEvent event) {}
			public void windowIconified(WindowEvent event) {}
			public void windowOpened(WindowEvent event) {}

			public void windowClosing(WindowEvent event) {
				ModuleChoice window = new ModuleChoice();
				window.setVisible(true);
				setVisible(false);
			}
		});

		this.setEditable(false);
	}

	private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			AddGeneralInfo addGeneralInfoFrame = new AddGeneralInfo(dataTable);

			addGeneralInfoFrame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if (((JTextField) panelSurname.getComponent(1)).isEnabled()) {
				// Изменения в БД
				String[] abitBaseInfo = new String[10];
				abitBaseInfo[0] = (!((JTextField)panelID.getComponent(1)).getText().equals("") ? ((JTextField)panelID.getComponent(1)).getText() : null);
				abitBaseInfo[1] = (!((JTextField)panelSurname.getComponent(1)).getText().equals("") ? ((JTextField)panelSurname.getComponent(1)).getText() : null);
				abitBaseInfo[2] = (!((JTextField)panelName.getComponent(1)).getText().equals("") ? ((JTextField)panelName.getComponent(1)).getText() : null);
				abitBaseInfo[3] = (!((JTextField)panelPatronymic.getComponent(1)).getText().equals("") ? ((JTextField)panelPatronymic.getComponent(1)).getText() : null);
				abitBaseInfo[4] = (calendar.getDate() != null ? new SimpleDateFormat("dd.MM.yyyy").format(calendar.getDate()).toString() : null);
				abitBaseInfo[5] = String.valueOf(comboSexList.getSelectedIndex() + 1);
				abitBaseInfo[6] = String.valueOf(comboNationality.getSelectedIndex() + 1);
				abitBaseInfo[7] = ((JLabel) panelDateDoc.getComponent(1)).getText();
				if (checkBackDoc.isSelected()) {
					abitBaseInfo[8] = textDateReturn.getText().equals("__.__.____") ? null : textDateReturn.getText();
					abitBaseInfo[9] = String.valueOf(comboReturnReason.getSelectedIndex() + 1);
				} else {
					abitBaseInfo[8] = null;
					abitBaseInfo[9] = null;
				}

	    		ArrayList<Integer> mistakesIndices = checkData(abitBaseInfo);
				if (abitBaseInfo[0] == null) {
					MessageProcessing.displayErrorMessage(null, 18);
				} else if (abitBaseInfo[1] == null) {
					MessageProcessing.displayErrorMessage(null, 19);
				} else if (abitBaseInfo[2] == null) {
					MessageProcessing.displayErrorMessage(null, 20);
				} else if (abitBaseInfo[4] == null) {
					MessageProcessing.displayErrorMessage(null, 21);
				} else if (abitBaseInfo[5].equals("0")) {
					abitBaseInfo[5] = null;
					MessageProcessing.displayErrorMessage(null, 16);
				} else if (abitBaseInfo[6].equals("0")) {
					abitBaseInfo[6] = null;
					MessageProcessing.displayErrorMessage(null, 17);
				} else if (abitBaseInfo[9] != null && abitBaseInfo[9].equals("0")) {
					abitBaseInfo[9] = null;
					MessageProcessing.displayErrorMessage(null, 23);
				} else {
					if (mistakesIndices.contains(0))
						textID.setForeground(Color.RED);
					else
						textID.setForeground(Color.BLACK);
					if (mistakesIndices.contains(1))
						((JTextField)panelSurname.getComponent(1)).setForeground(Color.RED);
					else
						((JTextField)panelSurname.getComponent(1)).setForeground(Color.BLACK);
					if (mistakesIndices.contains(2))
						((JTextField)panelName.getComponent(1)).setForeground(Color.RED);
					else
						((JTextField)panelName.getComponent(1)).setForeground(Color.BLACK);
					if (mistakesIndices.contains(3))
						((JTextField)panelPatronymic.getComponent(1)).setForeground(Color.RED);
					else
						((JTextField)panelPatronymic.getComponent(1)).setForeground(Color.BLACK);
					
					if (mistakesIndices.isEmpty()) {
						ModelDBConnection.editAbiturient(abitBaseInfo);

						int selectedRow = dataTable.convertRowIndexToModel(dataTable.getSelectedRow());
						// Изменение таблицы
						currentTM.setDataVector(ModelDBConnection.getAllAbiturients(), columnNames);
						dataTable.addRowSelectionInterval(selectedRow, selectedRow);
						dataTable.getColumnModel().getColumn(0).setMaxWidth(40);
						dataTable.updateUI();

						MessageProcessing.displaySuccessMessage(this, 2);

						setEditable(false);
					} else {
						MessageProcessing.displayErrorMessage(null, 9);
					}
				}
			} else {
				setEditable(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if (MessageProcessing.displayDialogMessage(this, 1) == 0) {
				ModelDBConnection.deleteAbiturient(((JTextField) panelID.getComponent(1)).getText());
				currentTM.removeRow(dataTable.convertRowIndexToModel(dataTable.getSelectedRow()));
				dataTable.clearSelection();
				dataTable.updateUI();
				MessageProcessing.displaySuccessMessage(this, 3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private JPanel createFIOPanel(String nameLabel) {
		JPanel panelFIO = new JPanel();
		panelFIO.setLayout(new FlowLayout());
		panelFIO.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelFIO.setMaximumSize(dimPanel);
		JLabel FIOLabel = new JLabel(nameLabel);
		panelFIO.add(FIOLabel);
		JTextField textFIO = new JTextField();
		textFIO.setEnabled(false);
		textFIO.setPreferredSize(dimText);
		panelFIO.add(textFIO);

		return panelFIO;
	}

	public void setValues(String tabName, String aid, String[] values, String[] values2) {
		switch(tabName) {
		case "GeneralInfo":
			this.setValues(values);
			break;
		case "Passport":
			((PassportPanel)passportPanel).setValues(values);
			break;
		case "AddressContacts":
			((AddressContactsPanel)contPanel).setValues(values);
			break;
		case "Education":
			((EducationPanel)educPanel).setValues(values, values2);
			break;
		case "EntranceTests":
			((EntranceTestsPanel)entranceTestpPanel).setValues(aid);
			break;
		case "IndividualAchievements":
			((IndividualAchievementsPanel)indAchivPanel).setValues(aid);
			break;
		case "CompetitiveGroups":
			((CompetitiveGroupsPanel)compGroupPanel).setValues(aid);
			break;
		}
	}

	public void updateTab(String tabName) {
		try {
			String[] data = null, data2 = null;
			String aid = (dataTable.getSelectedRow() >= 0) ? (String) currentTM.getValueAt(dataTable.convertRowIndexToModel(dataTable.getSelectedRow()), 0) : "0";

			switch (tabName) {
			case "GeneralInfo":
				data = ModelDBConnection.getAbiturientGeneralInfoByID(aid);
				break;
			case "Passport":
				data = ModelDBConnection.getAbiturientPassportByID(aid);
				break;
			case "AddressContacts":
				data = ModelDBConnection.getAbiturientAddressAndContactsByID(aid);
				break;
			case "Education":
				data = ModelDBConnection.getAbiturientEducationByID(aid, "AbiturientHigherEducation");
				data2 = ModelDBConnection.getAbiturientEducationByID(aid, "AbiturientPostgraduateEducation");
				break;
			case "EntranceTests":
			case "IndividualAchievements":
			case "CompetitiveGroups":
				break;
			}

			setValues(tabName, aid, data, data2);
		} catch (Exception e) {
			e.printStackTrace();
			MessageProcessing.displayErrorMessage(tablePanel, e);
		}
	}

	public void setValues(String[] values) {
		try {
			((JTextField) panelID.getComponent(1)).setText(values[0]);
			((JTextField) panelSurname.getComponent(1)).setText(values[1]);
			((JTextField) panelName.getComponent(1)).setText(values[2]);
			((JTextField) panelPatronymic.getComponent(1)).setText(values[3]);
			SimpleDateFormat format = new SimpleDateFormat();
			format.applyPattern("dd.MM.yyyy");
			calendar.setDate(values[4].equals("") ? null
					: format.parse(values[4]));
			comboSexList.setSelectedIndex(values[5].equals("") ? -1
					: Integer.valueOf(values[5]) - 1);
			comboNationality.setSelectedIndex(values[6].equals("") ? -1
					: Integer.valueOf(values[6]) - 1);
			((JLabel) panelDateDoc.getComponent(1)).setText(values[7]);
			comboReturnReason.setSelectedIndex(values[8].equals("") ? -1
					: Integer.valueOf(values[8]) - 1);
			if(!values[9].equals("")) {
				Date docDate= format.parse(values[9]);
				format.applyPattern("dd.MM.yyyy");
				textDateReturn.setText(format.format(docDate));
			} else
				textDateReturn.setText(null);
			if (values[8].equals(""))
				checkBackDoc.setSelected(false);
			else
				checkBackDoc.setSelected(true);
			textDateReturn.setEnabled(false);
			comboReturnReason.setEnabled(false);
		} catch (Exception e) {
			e.printStackTrace();
			MessageProcessing.displayErrorMessage(tablePanel, e);
		}
	}

	public void setEditable(boolean status) {
		//((JTextField) panelID.getComponent(1)).setEnabled(status);
		((JTextField) panelSurname.getComponent(1)).setEnabled(status);
		((JTextField) panelName.getComponent(1)).setEnabled(status);
		((JTextField) panelPatronymic.getComponent(1)).setEnabled(status);
		calendar.setEnabled(status);
		comboSexList.setEnabled(status);
		comboSexList.setEditable(!status);
		comboNationality.setEnabled(status);
		comboNationality.setEditable(!status);
		checkBackDoc.setEnabled(status);
		comboReturnReason.setEnabled(false);
		textDateReturn.setEnabled(false);
		if (checkBackDoc.isSelected()) {
			textDateReturn.setEnabled(status);
			comboReturnReason.setEnabled(status);
			comboReturnReason.setEditable(!status);
		}
		comboReturnReason.setEditable(true);
		addButton.setEnabled(!status);
		if (dataTable.getSelectedRow() < 0) {
			editButton.setEnabled(false);
			deleteButton.setEnabled(status);
		} else {
			editButton.setEnabled(true);
			deleteButton.setEnabled(!status);
		}
		editButton.setText(status ? "    Сохранить     " : "Редактировать");
	}

	private ArrayList<Integer> checkData(String[] values) {
		ArrayList<Integer> mistakesIndices = new ArrayList<Integer>();
		if (values[0] != null && !values[0].matches("^[0-9]+$"))
			mistakesIndices.add(0);
		if (values[1] != null && !values[1].matches("^[а-яА-Я]+[ -]?[а-яА-Я]*$"))
			mistakesIndices.add(1);
		if (values[2] != null && !values[2].matches("^[а-яА-Я]+[ -]?[а-яА-Я]*$"))
			mistakesIndices.add(2);
		if (values[3] != null && !values[3].matches("^[а-яА-Я]+[ -]?[а-яА-Я]*$"))
			mistakesIndices.add(3);

		return mistakesIndices;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeneralInfoInput window = new GeneralInfoInput();
					window.setVisible(true);
				} catch (Exception e) {
				    String message =  "Невозможно запустить программу.\n" + 
				    		"Проблемы с подключением к базе данных.\n" +
				    		"Чтобы узнать подробности ошибки запустите приложение\n" +
				    		"через консоль.";
				    JOptionPane.showMessageDialog(new JFrame(), message, "Ошибка",
				        JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
	}

}
