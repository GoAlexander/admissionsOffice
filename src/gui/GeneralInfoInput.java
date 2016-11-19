package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
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
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

public class GeneralInfoInput extends JFrame {

	private JPanel mainPanel, centralPanel, GIPanel, panelSurname, panelName, panelPatronymic, panelID, panelSex,
			panelDB, panelNationality, panelInfoBackDoc, panelReturnReason, panelDateReturn, tablePanel, btnTablePanel, passportPanel;
	private Dimension dimPanel, dimText;
	private JTextField textSurname, textName, textPatronymic, textID, textDateReturn, textSeria, textNum, textDate, textIssuedBy;
	private GridBagConstraints gbc;

	private String[] columnNames = { "№", "Имя", "Фамилия", "Отчество" };
	private GUITableModel currentTM = new GUITableModel();
	private JTable dataTable;
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

	private JButton addButton, editButton, deleteButton;

	public GeneralInfoInput() {

		setTitle("General Info");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		
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
		

		dimPanel = new Dimension(300, 40);
		dimText = new Dimension(170, 25);

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
		gbc.gridx = 0;
		gbc.gridy = 0;
		GIPanel.add(panelID, gbc);

		panelSurname = new JPanel();
		panelSurname.setLayout(new FlowLayout());
		panelSurname.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelSurname.setMaximumSize(dimPanel);
		JLabel surnameLabel = new JLabel("Фамилия:     ");
		panelSurname.add(surnameLabel);
		textSurname = new JTextField();
		textSurname.setPreferredSize(dimText);
		panelSurname.add(textSurname);
		gbc.gridy = 1;
		GIPanel.add(panelSurname, gbc);

		panelName = new JPanel();
		panelName.setLayout(new FlowLayout());
		panelName.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelName.setMaximumSize(dimPanel);
		JLabel nameLabel = new JLabel("Имя:                ");
		panelName.add(nameLabel);
		textName = new JTextField();
		textName.setPreferredSize(dimText);
		panelName.add(textName);
		gbc.gridy = 2;
		GIPanel.add(panelName, gbc);

		panelPatronymic = new JPanel();
		panelPatronymic.setLayout(new FlowLayout());
		panelPatronymic.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelPatronymic.setMaximumSize(dimPanel);
		JLabel patronymicLabel = new JLabel("Отчество:      ");
		panelPatronymic.add(patronymicLabel);
		textPatronymic = new JTextField();
		textPatronymic.setPreferredSize(dimText);
		panelPatronymic.add(textPatronymic);
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
		// panelInfoBackDoc.setBounds(10, 10, 10, 10);

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
		JPanel indAchivPanel = new JPanel();
		JPanel educPanel = new JPanel();
		JPanel contPanel = new JPanel();
		JPanel passportPanel = new JPanel();
		
		userInfoTPane.add("Конк-ные группы", contGroupPanel);
		userInfoTPane.add("Вступ-ные испытания", entranceTestpPanel);
		userInfoTPane.add("Инд-ные достижения", indAchivPanel);
		userInfoTPane.add("Образование", educPanel);
		userInfoTPane.add("Адрес и контакты", contPanel);
		userInfoTPane.add("Паспорт", createPassportPanel());
		//userInfoTPane.setPreferredSize(new Dimension(300, 250));
		
		centralPanel.add(userInfoTPane);

		setPreferredSize(new Dimension(1100, 600));
		pack();

	}
	
	private JPanel createPassportPanel(){
		passportPanel = new JPanel();
		passportPanel.setLayout(new GridBagLayout());
		
		Dimension dimTextPassport = new Dimension(120, 25);
		GridBagConstraints gbc3 = new GridBagConstraints();
		gbc3.anchor = GridBagConstraints.WEST;
		gbc3.insets = new Insets(2, 0, 5, 40);

		JPanel docTypePanel = new JPanel(); 
		docTypePanel.setLayout(new FlowLayout());
		JLabel docTypeLabel = new JLabel("Тип документа");
		comboDocType = new JComboBox(arrDocType);
		docTypePanel.add(docTypeLabel);
		docTypePanel.add(comboDocType);
		gbc3.gridwidth = 3;
		passportPanel.add(docTypePanel, gbc3);
		
		JPanel seriaPanel = new JPanel();
		seriaPanel.setLayout(new FlowLayout());
		JLabel seriaLabel = new JLabel("Серия ");
		seriaPanel.add(seriaLabel);
		textSeria = new JTextField();
		textSeria.setPreferredSize(dimTextPassport);
		seriaPanel.add(textSeria);
		gbc3.gridy = 1;
		gbc3.gridwidth = 1;
		passportPanel.add(seriaPanel, gbc3);
		
		JPanel numPanel = new JPanel();
		numPanel.setLayout(new FlowLayout());
		JLabel numLabel = new JLabel("Номер ");
		numPanel.add(numLabel);
		textNum = new JTextField();
		textNum.setPreferredSize(dimTextPassport);
		numPanel.add(textNum);
		gbc3.gridx = 1;
		passportPanel.add(numPanel, gbc3);
		
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new FlowLayout());
		JLabel dateLabel = new JLabel("Дата выдачи ");
		datePanel.add(dateLabel);
		textDate = new JTextField();
		textDate.setPreferredSize(dimTextPassport);
		datePanel.add(textDate);
		gbc3.gridx = 2;
		passportPanel.add(datePanel, gbc3);
		
		JPanel issuedByPanel = new JPanel();
		issuedByPanel.setLayout(new FlowLayout());
		JLabel issuedByLabel = new JLabel("Кем выдан");
		issuedByPanel.add(issuedByLabel);
		textIssuedBy = new JTextField();
		textIssuedBy.setPreferredSize(new Dimension(565, 70));
		issuedByPanel.add(textIssuedBy);
		gbc3.gridx = 0;
		gbc3.gridy = 2;
		gbc3.gridwidth = 3;
		passportPanel.add(issuedByPanel, gbc3);
		
		return passportPanel;
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
