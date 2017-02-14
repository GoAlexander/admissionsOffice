package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.toedter.calendar.JDateChooser;

import backend.MessageProcessing;
import backend.ModelDBConnection;
import tab_education.EducationPanel;
import tab_entrance_tests.EntranceTestsPanel;
import tab_passport.PassportPanel;
import tab_address_contacts.AddressContactsPanel;
import tab_catalogs.EditMenuListener;
import tab_competitive_groups.AddNewCompetitiveGroup;
import tab_competitive_groups.CompetitiveGroupPanelListener;
import tab_competitive_groups.CompetitiveGroupsPanel;
import tab_competitive_groups.SimpleCompetitiveGroupPanel;
import general_classes.GUITableModel;
import tab_achievements.IndividualAchievementsPanel;

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
	private JMenuItem docMenuApplication, docMenuOpRasp, docMenuListEntranceExam, directoryMenuEntranceTest,
			directoryMenuBlockTest, directoryMenuBaseMark, directoryMenuTypePasport, directoryMenuRegion,
			directoryMenuTypeSettlements, directoryMenuSex, directoryMenuNationality, directoryMenuReturnReason,
			directoryMenuIndividual, directoryMenuStudyFields, directoryMenuEducForm, directoryMenuSpeciality,
			directoryMenuDepartments, directoryMenuCompetitiveGroup, directoryMenuOrganisations,
			directoryMenuEducStandard, directoryMenuPlan, directoryMenuUsers, reportMenuListCandidates,
			reportMenuListGroups, reportMenuResults, reportMenuStatistics, compMenuCalculation, compMenuPlayCompet,
			compMenuInternalRating, compMenuRankedList, compMenuCompetList, compMenuResetCompetResults,
			expMenuOrganisations, expMenuCompetGroup, expMenuPlan, expMenuApplications, expMenuResults;

	private JButton addButton, editButton, deleteButton;

	public GeneralInfoInput() {
		// ----------------------
		// Временно
		ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Ordinator", "user", "password");
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
		directoryMenuPlan.addActionListener(new EditMenuListener("AdmissionPlan", "План приема"));
		directoryMenu.add(directoryMenuPlan);
		directoryMenu.addSeparator();
		directoryMenuUsers = new JMenuItem("Пользователи");
		directoryMenuUsers.addActionListener(new EditMenuListener("Users", "Пользователи"));
		directoryMenu.add(directoryMenuUsers);

		docMenu = new JMenu("Документы");
		menuBar.add(docMenu);
		docMenuApplication = new JMenuItem("Заявление");
		docMenu.add(docMenuApplication);
		docMenuOpRasp = new JMenuItem("Опись/расписка");
		docMenu.add(docMenuOpRasp);
		docMenuListEntranceExam = new JMenuItem("Лист вступительных испытаний");
		docMenu.add(docMenuListEntranceExam);

		reportMenu = new JMenu("Отчетность");
		menuBar.add(reportMenu);
		reportMenuListCandidates = new JMenuItem("Список подавших документы");
		reportMenu.add(reportMenuListCandidates);
		reportMenuListGroups = new JMenuItem("Списки групп на вступительные");
		reportMenu.add(reportMenuListGroups);
		reportMenuResults = new JMenuItem("Результаты вступительных");
		reportMenu.add(reportMenuResults);
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
		expMenuCompetGroup = new JMenuItem("Конкурсные группы");
		expMenu.add(expMenuCompetGroup);
		expMenuPlan = new JMenuItem("План приема");
		expMenu.add(expMenuPlan);
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
		comboSexList.setEnabled(false);
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
		calendar = new JDateChooser();
		calendar.setFont(new Font("Dialog", Font.PLAIN, 11));
		calendar.setEnabled(false);
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
		comboNationality.setEnabled(false);
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
		comboReturnReason.setEnabled(false);
		comboReturnReason.setSelectedIndex(-1);
		panelReturnReason.add(comboReturnReason);

		panelInfoBackDoc.add(panelReturnReason, gbc2);

		panelDateReturn = new JPanel();
		panelDateReturn.setLayout(new FlowLayout());

		panelDateReturn.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelDateReturn.setMaximumSize(dimPanel);
		JLabel dateReturnLabel = new JLabel("Дата возврата документов:     ");
		panelDateReturn.add(dateReturnLabel);
		textDateReturn = new JTextField();
		textDateReturn.setPreferredSize(dimText);
		textDateReturn.setEnabled(false);
		panelDateReturn.add(textDateReturn);
		checkBackDoc = new JCheckBox("Забрал документы");
		checkBackDoc.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (checkBackDoc.isSelected()) {
					textDateReturn.setEnabled(true);
					comboReturnReason.setEnabled(true);
					if (textDateReturn.getText().equals("")) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
						textDateReturn.setText(sdf.format(new Date()));
					}
				} else {
					textDateReturn.setEnabled(false);
					comboReturnReason.setEnabled(false);
				}
			}
		});
		checkBackDoc.setEnabled(false);
		gbc2.gridy = 1;
		panelInfoBackDoc.add(panelDateReturn, gbc2);
		gbc2.gridx = 1;
		panelInfoBackDoc.add(checkBackDoc, gbc2);

		userInfoTPane = new JTabbedPane();
		compGroupPanel = new CompetitiveGroupsPanel("aaa");
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
					try {
						String[] selectedAbitGeneralInfo, selectedAbitPassport, selectedAbitAddressAndContacts, selectedAbitHigherEducation, selectedAbitPostGraduationEducation, selectedAbitIndividualAchievement;

						if (dataTable.getSelectedRow() >= 0) {
							selectedAbitGeneralInfo = ModelDBConnection.getAbiturientGeneralInfoByID(String.valueOf(
									Integer.valueOf((String) currentTM.getValueAt(dataTable.getSelectedRow(), 0))));
							selectedAbitPassport = ModelDBConnection.getAbiturientPassportByID(String.valueOf(
									Integer.valueOf((String) currentTM.getValueAt(dataTable.getSelectedRow(), 0))));
							selectedAbitAddressAndContacts = ModelDBConnection.getAbiturientAddressAndContactsByID(String.valueOf(
									Integer.valueOf((String) currentTM.getValueAt(dataTable.getSelectedRow(), 0))));
							selectedAbitHigherEducation = ModelDBConnection.getAbiturientEducationByID(String.valueOf(
									Integer.valueOf((String) currentTM.getValueAt(dataTable.getSelectedRow(), 0))), "AbiturientHigherEducation");
							selectedAbitPostGraduationEducation = ModelDBConnection.getAbiturientEducationByID(String.valueOf(
									Integer.valueOf((String) currentTM.getValueAt(dataTable.getSelectedRow(), 0))), "AbiturientPostgraduateEducation");
							selectedAbitIndividualAchievement = ModelDBConnection.getAbiturientIndividualAchievementByID(String.valueOf(
									Integer.valueOf((String) currentTM.getValueAt(dataTable.getSelectedRow(), 0))));
							
						} else {
							selectedAbitGeneralInfo = new String[10];
							for (int i = 0; i < selectedAbitGeneralInfo.length; i++)
								selectedAbitGeneralInfo[i] = "";
							selectedAbitPassport = new String[7];
							for (int i = 0; i < selectedAbitPassport.length; i++)
								selectedAbitPassport[i] = "";
							selectedAbitAddressAndContacts = new String[7];
							for (int i = 0; i < selectedAbitAddressAndContacts.length; i++)
								selectedAbitAddressAndContacts[i] = "";
							selectedAbitHigherEducation = new String[6];
							for (int i = 0; i < selectedAbitHigherEducation.length; i++)
								selectedAbitHigherEducation[i] = "";
							selectedAbitPostGraduationEducation = new String[6];
							for (int i = 0; i < selectedAbitPostGraduationEducation.length; i++)
								selectedAbitPostGraduationEducation[i] = "";
							selectedAbitIndividualAchievement = new String[8];
							for (int i = 0; i < selectedAbitIndividualAchievement.length; i++)
								selectedAbitIndividualAchievement[i] = "";
						}

						setValues(selectedAbitGeneralInfo);
						((PassportPanel)passportPanel).setValues(selectedAbitPassport);
						((AddressContactsPanel)contPanel).setValues(selectedAbitAddressAndContacts);
						((EducationPanel)educPanel).setValues(selectedAbitHigherEducation, selectedAbitPostGraduationEducation);
						((IndividualAchievementsPanel)indAchivPanel).setValues(selectedAbitIndividualAchievement[0]);
					} catch (SQLException e1) {
						MessageProcessing.displayErrorMessage(tablePanel, e1);
					}
				}
			}
		});
	}

	private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			AddGeneralInfo addGeneralInfoFrame = new AddGeneralInfo(currentTM);

			addGeneralInfoFrame.setVisible(true);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if (((JTextField) panelSurname.getComponent(1)).isEnabled()) {
				// Изменения в БД
				String[] abitBaseInfo = new String[10];
				abitBaseInfo[0] = ((JTextField) panelID.getComponent(1)).getText();
				abitBaseInfo[1] = ((JTextField) panelSurname.getComponent(1)).getText();
				abitBaseInfo[2] = ((JTextField) panelName.getComponent(1)).getText();
				abitBaseInfo[3] = ((JTextField) panelPatronymic.getComponent(1)).getText();
				abitBaseInfo[4] = new SimpleDateFormat("dd.MM.yyyy").format(calendar.getDate()).toString();
				abitBaseInfo[5] = String.valueOf(comboSexList.getSelectedIndex() + 1);
				abitBaseInfo[6] = String.valueOf(comboNationality.getSelectedIndex() + 1);
				abitBaseInfo[7] = ((JLabel) panelDateDoc.getComponent(1)).getText();
				if (checkBackDoc.isSelected()) {
					abitBaseInfo[8] = textDateReturn.getText();
					abitBaseInfo[9] = String.valueOf(comboReturnReason.getSelectedIndex() + 1);
				} else {
					abitBaseInfo[8] = null;
					abitBaseInfo[9] = null;
				}

				ModelDBConnection.editAbiturient(abitBaseInfo);

				int selectedRow = dataTable.getSelectedRow();
				// Изменение таблицы
				currentTM.setDataVector(ModelDBConnection.getAllAbiturients(), columnNames);
				dataTable.addRowSelectionInterval(selectedRow, selectedRow);
				dataTable.getColumnModel().getColumn(0).setMaxWidth(40);
				dataTable.updateUI();

				MessageProcessing.displaySuccessMessage(this, 2);

				// Обновление интерфейса
				((JTextField) panelSurname.getComponent(1)).setEnabled(false);
				((JTextField) panelName.getComponent(1)).setEnabled(false);
				((JTextField) panelPatronymic.getComponent(1)).setEnabled(false);
				calendar.setEnabled(false);
				comboSexList.setEnabled(false);
				comboNationality.setEnabled(false);
				textDateReturn.setEnabled(false);
				comboReturnReason.setEnabled(false);
				checkBackDoc.setEnabled(false);

				// !!! Добавить !!!
				// Фрагмент setEnable(false) для всех компонентов JTabbedPane

				addButton.setEnabled(true);
				deleteButton.setEnabled(true);
				editButton.setText("Редактировать");
			} else {
				((JTextField) panelSurname.getComponent(1)).setEnabled(true);
				((JTextField) panelName.getComponent(1)).setEnabled(true);
				((JTextField) panelPatronymic.getComponent(1)).setEnabled(true);
				calendar.setEnabled(true);
				comboSexList.setEnabled(true);
				comboNationality.setEnabled(true);
				checkBackDoc.setEnabled(true);
				if (checkBackDoc.isSelected()) {
					textDateReturn.setEnabled(true);
					comboReturnReason.setEnabled(true);
				}
				// !!! Добавить !!!
				// Фрагмент setEnable(false) для всех компонентов JTabbedPane

				addButton.setEnabled(false);
				deleteButton.setEnabled(false);
				editButton.setText("    Сохранить     ");
			}
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if (MessageProcessing.displayDialogMessage(this, 1) == 0) {
				ModelDBConnection.deleteAbiturient(((JTextField) panelID.getComponent(1)).getText());
				currentTM.removeRow(dataTable.getSelectedRow());
				dataTable.clearSelection();
				dataTable.updateUI();
				MessageProcessing.displaySuccessMessage(this, 3);
			}
		} catch (SQLException e) {
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

	public void setValues(String[] values) {
		try {
			((JTextField) panelID.getComponent(1)).setText(values[0]);
			((JTextField) panelSurname.getComponent(1)).setText(values[1]);
			((JTextField) panelName.getComponent(1)).setText(values[2]);
			((JTextField) panelPatronymic.getComponent(1)).setText(values[3]);
			SimpleDateFormat format = new SimpleDateFormat();
			format.applyPattern("yyyy-MM-dd");
			calendar.setDate(values[4].equals("") ? null
					: format.parse(values[4]));
			comboSexList.setSelectedIndex(values[5].equals("") ? -1
					: Integer.valueOf(values[5]) - 1);
			comboNationality.setSelectedIndex(values[6].equals("") ? -1
					: Integer.valueOf(values[6]) - 1);
			((JLabel) panelDateDoc.getComponent(1)).setText(values[7]);
			comboReturnReason.setSelectedIndex(values[8].equals("") ? -1
					: Integer.valueOf(values[8]) - 1);
			textDateReturn.setText(values[9]);
			if (values[8].equals(""))
				checkBackDoc.setSelected(false);
			else
				checkBackDoc.setSelected(true);
			textDateReturn.setEnabled(false);
			comboReturnReason.setEnabled(false);
		} catch (ParseException e) {
			MessageProcessing.displayErrorMessage(tablePanel, e);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeneralInfoInput window = new GeneralInfoInput();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
