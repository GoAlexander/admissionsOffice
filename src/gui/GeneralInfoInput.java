﻿package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
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
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JDateChooser;

public class GeneralInfoInput extends JFrame {

	private JPanel mainPanel, centralPanel, GIPanel, panelSurname, panelName, panelPatronymic, panelID, panelSex,
			panelDB, panelNationality, panelInfoBackDoc, panelReturnReason, panelDateReturn, tablePanel, btnTablePanel,
			passportPanel, entranceTestPanel, EntranceTestTablePanel;
	private Dimension dimPanel = new Dimension(300, 40);
	private Dimension dimText = new Dimension(170, 25);
	private Dimension dimTextDigitInfo = new Dimension(139, 25);
	private Dimension dimTextIssuedBy = new Dimension(565, 70);
	private Dimension dimRigidArea = new Dimension(10, 0);
	private Dimension dimStartRigidArea = new Dimension(50, 0);
	private JTextField  textID, textDateReturn, textSeria, textNum, textDate, textIssuedBy, textplaceBirth;
	private GridBagConstraints gbc;

	private String[] columnNames = { "№", "Имя", "Фамилия", "Отчество" };
	private String[] entranceTestColumnNames = { "Наименование", "Группа", "Блок испытаний", "Дата испытания", "Балл" };
	private GUITableModel currentTM = new GUITableModel();
	private GUITableModel entranceTestTM = new GUITableModel();
	private JTable dataTable, entranceTestTable;
	private JCheckBox checkBackDoc;
	private JTabbedPane userInfoTPane;	
	
	private JComboBox comboSexList, comboNationality, comboReturnReason, comboDocType;
	private JDateChooser calendar;
	
	private String[] arrSex = { "Женский", "Мужской" };
	private String[] arrNationality = { "РФ", "Украина", "Белорусь", "Казахстан" };
	private String[] arrReturnReason = { "1                               ", "2      ", "3      " };
	private String[] arrDocType = {"паспорт РФ", "паспорт Украина"};
	
	private JMenuBar menuBar;
	private JMenu directoryMenu, docMenu, reportMenu, compMenu, expMenu;
	private JMenuItem docMenuApplication, docMenuOpRasp, docMenuListEntranceExam, directoryMenuEntranceTest, 
	directoryMenuBlockTest, directoryMenuBaseMark, directoryMenuTypePasport, directoryMenuRegion, 
	directoryMenuTypeSettlements, directoryMenuSex, directoryMenuNationality, directoryMenuReturnReason,
	directoryMenuIndividual, directoryMenuStudyFields, directoryMenuEducForm, directoryMenuSpeciality,
	directoryMenuDepartments, directoryMenuCompetitiveGroup, directoryMenuOrganisations, directoryMenuEducStandard,
	directoryMenuPlan, directoryMenuUsers, reportMenuListCandidates, reportMenuListGroups, reportMenuResults,
	reportMenuStatistics, compMenuCalculation, compMenuPlayCompet, compMenuInternalRating, compMenuRankedList, 
	compMenuCompetList, compMenuResetCompetResults, expMenuOrganisations,	expMenuCompetGroup, expMenuPlan, 
	expMenuApplications, expMenuResults;

	private JButton addButton, editButton, deleteButton, editPassportButton, savePassportButton;

	public GeneralInfoInput() {

		setTitle("General Info");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		setLocation(0,0);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		getContentPane().add(mainPanel);
		
		menuBar = new JMenuBar();
		directoryMenu = new JMenu("Справочники");
		menuBar.add(directoryMenu);
		directoryMenuEntranceTest = new JMenuItem("Вступительные испытания");
		directoryMenu.add(directoryMenuEntranceTest);
		directoryMenuBlockTest = new JMenuItem("Блоки испытаний");
		directoryMenu.add(directoryMenuBlockTest);
		directoryMenuBaseMark = new JMenuItem("Основания для оценки");
		directoryMenu.add(directoryMenuBaseMark);
		directoryMenu.addSeparator();
		directoryMenuTypePasport = new JMenuItem("Типы паспортов");
		directoryMenu.add(directoryMenuTypePasport);
		directoryMenuRegion = new JMenuItem("Регионы");
		directoryMenu.add(directoryMenuRegion);
		directoryMenuTypeSettlements = new JMenuItem("Типы населенных пунктов");
		directoryMenu.add(directoryMenuTypeSettlements);
		directoryMenu.addSeparator();
		directoryMenuSex = new JMenuItem("Пол");
		directoryMenu.add(directoryMenuSex);
		directoryMenuNationality = new JMenuItem("Гражданство");
		directoryMenu.add(directoryMenuNationality);
		directoryMenu.addSeparator();
		directoryMenuReturnReason = new JMenuItem("Причины возврата");
		directoryMenu.add(directoryMenuReturnReason);
		directoryMenu.addSeparator();
		directoryMenuIndividual = new JMenuItem("Индивидуальные");
		directoryMenu.add(directoryMenuIndividual);
		directoryMenu.addSeparator();
		directoryMenuStudyFields = new JMenuItem("Направления обучения");
		directoryMenu.add(directoryMenuStudyFields);
		directoryMenuEducForm = new JMenuItem("Формы обучения");
		directoryMenu.add(directoryMenuEducForm);
		directoryMenuSpeciality = new JMenuItem("Специальности");
		directoryMenu.add(directoryMenuSpeciality);
		directoryMenuDepartments = new JMenuItem("Кафедры");
		directoryMenu.add(directoryMenuDepartments);
		directoryMenuCompetitiveGroup = new JMenuItem("Конкурсные группы");
		directoryMenu.add(directoryMenuCompetitiveGroup);
		directoryMenuOrganisations = new JMenuItem("Целевые организации");
		directoryMenu.add(directoryMenuOrganisations);
		directoryMenuEducStandard = new JMenuItem("Стандарты образования");
		directoryMenu.add(directoryMenuEducStandard);
		directoryMenu.addSeparator();
		directoryMenuPlan = new JMenuItem("План приема");
		directoryMenu.add(directoryMenuPlan);
		directoryMenu.addSeparator();
		directoryMenuUsers = new JMenuItem("Пользователи");
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
		currentTM.setDataVector(null, columnNames);
		JScrollPane scrPane = new JScrollPane(dataTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		tablePanel.add(scrPane, BorderLayout.CENTER);
		
		btnTablePanel = new JPanel();
		btnTablePanel.setLayout(new FlowLayout());
		addButton = new JButton("Добавить");
		editButton = new JButton("Редактировать");
		deleteButton = new JButton("Удалить");
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
		panelID.add(textID);
		GIPanel.add(panelID, gbc);

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
		comboSexList.setSelectedIndex(0);
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
		comboNationality.setSelectedIndex(0);
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
		comboNationality.setSelectedIndex(0);
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
		panelDateReturn.add(textDateReturn);
		checkBackDoc = new JCheckBox("Забрал документы");
		gbc2.gridy = 1;
		panelInfoBackDoc.add(panelDateReturn, gbc2);
		gbc2.gridx = 1;
		panelInfoBackDoc.add(checkBackDoc, gbc2);
				
		userInfoTPane = new JTabbedPane();
		JPanel contGroupPanel = new JPanel();
		JPanel entranceTestpPanel = new JPanel();
		entranceTestpPanel = createEntranceTestPanel();
		JPanel indAchivPanel = new JPanel();
		JPanel educPanel = new JPanel();
		educPanel = createEducPanel();
		JPanel contPanel = new JPanel();
		JPanel passportPanel = new JPanel();
		passportPanel = createPassportPanel();
		
		userInfoTPane.add("Конк-ные группы", contGroupPanel);
		userInfoTPane.add("Вступ-ные испытания", entranceTestpPanel);
		userInfoTPane.add("Инд-ные достижения", indAchivPanel);
		userInfoTPane.add("Образование", educPanel);
		userInfoTPane.add("Адрес и контакты", contPanel);
		userInfoTPane.add("Паспорт", passportPanel);
		//userInfoTPane.setPreferredSize(new Dimension(300, 250));
		
		centralPanel.add(userInfoTPane);

		setPreferredSize(new Dimension(1100, 770));
		pack();

	}
	
	private JPanel createFIOPanel(String nameLabel) {
		JPanel panelFIO = new JPanel();
		panelFIO.setLayout(new FlowLayout());
		panelFIO.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelFIO.setMaximumSize(dimPanel);
		JLabel FIOLabel = new JLabel(nameLabel);
		panelFIO.add(FIOLabel);
		JTextField textFIO = new JTextField();
		textFIO.setPreferredSize(dimText);
		panelFIO.add(textFIO);

		return panelFIO;
	}
	
	private JPanel createEducPanel(){
		JPanel educPanel = new JPanel();
		educPanel.setLayout(new BoxLayout(educPanel, BoxLayout.Y_AXIS));
		
		JPanel highEducPanel = new JPanel();
		highEducPanel = createAddEducPanel("Высшее образование");
		educPanel.add(highEducPanel);
		
		JPanel afterDiplEducPanel = new JPanel();
		afterDiplEducPanel = createAddEducPanel("Последипломное образование");
		educPanel.add(afterDiplEducPanel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton editBtn = new JButton("Редактировать");
		buttonPanel.add(editBtn);
		JButton saveBtn = new JButton("Сохранить");
		buttonPanel.add(saveBtn);		
		educPanel.add(buttonPanel);
		
		return educPanel;
	}
	
	private JPanel createAddEducPanel(String name){
		JPanel addEducPanel = new JPanel();
		addEducPanel.setBorder(new TitledBorder(null, name, TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		addEducPanel.setLayout(new BoxLayout(addEducPanel, BoxLayout.Y_AXIS));
		
		JPanel digitInfoEducPanel = new JPanel();
		digitInfoEducPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		digitInfoEducPanel.add(Box.createRigidArea(dimStartRigidArea));
		
		JLabel seriaLabel = new JLabel("Серия");
		digitInfoEducPanel.add(seriaLabel);
		JTextField textSeria = new JTextField();
		textSeria.setPreferredSize(dimTextDigitInfo);
		digitInfoEducPanel.add(textSeria);
		digitInfoEducPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel numLabel = new JLabel("Номер");
		digitInfoEducPanel.add(numLabel);
		JTextField textNum = new JTextField();
		textNum.setPreferredSize(dimTextDigitInfo);
		digitInfoEducPanel.add(textNum);
		digitInfoEducPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel yearLabel = new JLabel("Год окончания");
		digitInfoEducPanel.add(yearLabel);
		JTextField textYear = new JTextField();
		textYear.setPreferredSize(dimTextDigitInfo);
		digitInfoEducPanel.add(textYear);
		
		addEducPanel.add(digitInfoEducPanel);
		
		JPanel specialityEducPanel = new JPanel();
		specialityEducPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		specialityEducPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel specialityLabel = new JLabel("Специальность");
		specialityEducPanel.add(specialityLabel);
		JTextField specialitySeria = new JTextField();
		specialitySeria.setPreferredSize(new Dimension(538, 25));
		specialityEducPanel.add(specialitySeria);
		addEducPanel.add(specialityEducPanel);
		
		JPanel issuedByEducPanel = new JPanel();
		issuedByEducPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		issuedByEducPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel issuedByLabel = new JLabel("Кем выдан");
		issuedByEducPanel.add(issuedByLabel);
		JTextField issuedBySeria = new JTextField();
		issuedBySeria.setPreferredSize(dimTextIssuedBy);
		issuedByEducPanel.add(issuedBySeria);
		addEducPanel.add(issuedByEducPanel);
		
		return addEducPanel;
	}
	
	private JPanel createEntranceTestPanel(){
		entranceTestPanel = new JPanel();
		entranceTestPanel.setLayout(new BorderLayout());
		
		EntranceTestTablePanel = new JPanel();
		entranceTestTable = new JTable(entranceTestTM);
		entranceTestTM.setDataVector(null, entranceTestColumnNames);
		JScrollPane scrPane = new JScrollPane(entranceTestTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		entranceTestTable.setMaximumSize(new Dimension(100,100));
		entranceTestPanel.add(scrPane, BorderLayout.CENTER);
//***test data		
		entranceTestTM.setDataVector(new Object[][]
				{
			{ "1", "123","123","123","123"},
			{ "2", "123","123","123","123"},
			{ "3", "123","123","123","123"},
				},
				entranceTestColumnNames);
		
		String[] nameEntranceTest = {"n1", "n2", "n3"};
		createCheckboxTable(entranceTestTable, 0, nameEntranceTest);
		
		String[] groupEntranceTest = {"gr1", "gr2", "gr3"};
		createCheckboxTable(entranceTestTable, 1, groupEntranceTest);
		
		String[] blockEntranceTest = {"bl1", "bl2", "bl3"};
		createCheckboxTable(entranceTestTable, 2, blockEntranceTest);
		
		JPanel butPanel = new JPanel();
		butPanel.setLayout(new BoxLayout(butPanel,  BoxLayout.PAGE_AXIS));
		
		JCheckBox specialCond = new JCheckBox("Нуждается в специальных условиях");
		specialCond.setAlignmentX(Component.RIGHT_ALIGNMENT);
		butPanel.add(specialCond);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton editBtn = new JButton("Редактировать");
		buttonPanel.add(editBtn);
		JButton saveBtn = new JButton("Сохранить");
		buttonPanel.add(saveBtn);		
		buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		butPanel.add(buttonPanel);
		entranceTestPanel.add(butPanel, BorderLayout.PAGE_END);
		
		return entranceTestPanel;
	}

	private JPanel createPassportPanel() {
		passportPanel = new JPanel();
		passportPanel.setLayout(new BoxLayout(passportPanel, BoxLayout.Y_AXIS));

		JPanel docTypePanel = new JPanel();
		docTypePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		docTypePanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel docTypeLabel = new JLabel("Тип документа");
		comboDocType = new JComboBox(arrDocType);
		docTypePanel.add(docTypeLabel);
		docTypePanel.add(comboDocType);
		passportPanel.add(docTypePanel);

		JPanel passportDataPanel = new JPanel();
		passportDataPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		passportDataPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel seriaLabel = new JLabel("Серия ");
		passportDataPanel.add(seriaLabel);
		textSeria = new JTextField();
		textSeria.setPreferredSize(dimTextDigitInfo);
		passportDataPanel.add(textSeria);

		passportDataPanel.add(Box.createRigidArea(dimRigidArea));

		JLabel numLabel = new JLabel("Номер ");
		passportDataPanel.add(numLabel);
		textNum = new JTextField();
		textNum.setPreferredSize(dimTextDigitInfo);
		passportDataPanel.add(textNum);

		passportDataPanel.add(Box.createRigidArea(dimRigidArea));

		JLabel dateLabel = new JLabel("Дата выдачи ");
		passportDataPanel.add(dateLabel);
		textDate = new JTextField();
		textDate.setPreferredSize(dimTextDigitInfo);
		passportDataPanel.add(textDate);
		passportPanel.add(passportDataPanel);

		JPanel issuedByPanel = new JPanel();
		issuedByPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		issuedByPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel issuedByLabel = new JLabel("Кем выдан");
		issuedByPanel.add(issuedByLabel);
		textIssuedBy = new JTextField();
		textIssuedBy.setPreferredSize(new Dimension(565, 70));
		issuedByPanel.add(textIssuedBy);
		passportPanel.add(issuedByPanel);

		JPanel placeBirthPanel = new JPanel();
		placeBirthPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		placeBirthPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel placeBirthLabel = new JLabel("Место рождения");
		placeBirthPanel.add(placeBirthLabel);
		textplaceBirth = new JTextField();
		textplaceBirth.setPreferredSize(new Dimension(532, 25));
		placeBirthPanel.add(textplaceBirth);
		passportPanel.add(placeBirthPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		editPassportButton = new JButton("Редактировать");
		buttonPanel.add(editPassportButton);
		savePassportButton = new JButton("Сохранить");
		buttonPanel.add(savePassportButton);
		passportPanel.add(buttonPanel);

		return passportPanel;
	}
	
	private void createCheckboxTable(JTable table, int numColumn, String[] dataCheck) {
		TableColumn tmpColumn = table.getColumnModel().getColumn(numColumn);
		JComboBox<String> comboBox = new JComboBox<String>(dataCheck);

		DefaultCellEditor defaultCellEditor = new DefaultCellEditor(comboBox);
		tmpColumn.setCellEditor(defaultCellEditor);
		tmpColumn.setCellRenderer(new CheckBoxCellRenderer(comboBox));
	}

	class CheckBoxCellRenderer implements TableCellRenderer {
		JComboBox combo;

		public CheckBoxCellRenderer(JComboBox comboBox) {
			this.combo = new JComboBox();
			for (int i = 0; i < comboBox.getItemCount(); i++) {
				combo.addItem(comboBox.getItemAt(i));
			}
		}

		public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			combo.setSelectedItem(value);
			return combo;
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
