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

	private String[] columnNames = { "�", "���", "�������", "��������" };
	private GUITableModel currentTM = new GUITableModel();
	private JTable dataTable;
	private JCheckBox checkBackDoc;
	private JTabbedPane userInfoTPane;

	
	private JComboBox comboSexList, comboNationality, comboReturnReason, comboDocType;
	private JDateChooser calendar;
	
	private String[] arrSex = { "�������", "�������" };
	private String[] arrNationality = { "��", "�������", "��������", "���������" };
	private String[] arrReturnReason = { "1                               ", "2      ", "3      " };
	private String[] arrDocType = {"������� ��", "������� �������"};
	
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
		directoryMenu = new JMenu("�����������");
		menuBar.add(directoryMenu);
		directoryMenuEntranceTest = new JMenuItem("������������� ���������");
		directoryMenu.add(directoryMenuEntranceTest);
		directoryMenuBlockTest = new JMenuItem("����� ���������");
		directoryMenu.add(directoryMenuBlockTest);
		directoryMenuBaseMark = new JMenuItem("��������� ��� ������");
		directoryMenu.add(directoryMenuBaseMark);
		directoryMenu.addSeparator();
		directoryMenuTypePasport = new JMenuItem("���� ���������");
		directoryMenu.add(directoryMenuTypePasport);
		directoryMenuRegion = new JMenuItem("�������");
		directoryMenu.add(directoryMenuRegion);
		directoryMenuTypeSettlements = new JMenuItem("���� ���������� �������");
		directoryMenu.add(directoryMenuTypeSettlements);
		directoryMenu.addSeparator();
		directoryMenuSex = new JMenuItem("���");
		directoryMenu.add(directoryMenuSex);
		directoryMenuNationality = new JMenuItem("�����������");
		directoryMenu.add(directoryMenuNationality);
		directoryMenu.addSeparator();
		directoryMenuReturnReason = new JMenuItem("������� ��������");
		directoryMenu.add(directoryMenuReturnReason);
		directoryMenu.addSeparator();
		directoryMenuIndividual = new JMenuItem("��������������");
		directoryMenu.add(directoryMenuIndividual);
		directoryMenu.addSeparator();
		directoryMenuStudyFields = new JMenuItem("����������� ��������");
		directoryMenu.add(directoryMenuStudyFields);
		directoryMenuEducForm = new JMenuItem("����� ��������");
		directoryMenu.add(directoryMenuEducForm);
		directoryMenuSpeciality = new JMenuItem("�������������");
		directoryMenu.add(directoryMenuSpeciality);
		directoryMenuDepartments = new JMenuItem("�������");
		directoryMenu.add(directoryMenuDepartments);
		directoryMenuCompetitiveGroup = new JMenuItem("���������� ������");
		directoryMenu.add(directoryMenuCompetitiveGroup);
		directoryMenuOrganisations = new JMenuItem("������� �����������");
		directoryMenu.add(directoryMenuOrganisations);
		directoryMenuEducStandard = new JMenuItem("��������� �����������");
		directoryMenu.add(directoryMenuEducStandard);
		directoryMenu.addSeparator();
		directoryMenuPlan = new JMenuItem("���� ������");
		directoryMenu.add(directoryMenuPlan);
		directoryMenu.addSeparator();
		directoryMenuUsers = new JMenuItem("������������");
		directoryMenu.add(directoryMenuUsers);
		
		docMenu = new JMenu("���������");
		menuBar.add(docMenu);
		docMenuApplication = new JMenuItem("���������");
		docMenu.add(docMenuApplication);
		docMenuOpRasp = new JMenuItem("�����/��������");
		docMenu.add(docMenuOpRasp);
		docMenuListEntranceExam = new JMenuItem("���� ������������� ���������");
		docMenu.add(docMenuListEntranceExam);
		
		reportMenu = new JMenu("����������");
		menuBar.add(reportMenu);
		reportMenuListCandidates = new JMenuItem("������ �������� ���������");
		reportMenu.add(reportMenuListCandidates);
		reportMenuListGroups = new JMenuItem("������ ����� �� �������������");
		reportMenu.add(reportMenuListGroups);
		reportMenuResults = new JMenuItem("���������� �������������");
		reportMenu.add(reportMenuResults);
		reportMenu.addSeparator();
		reportMenuStatistics = new JMenuItem("����������");
		reportMenu.add(reportMenuStatistics);
		
		compMenu = new JMenu("�������");
		menuBar.add(compMenu);
		compMenuCalculation = new JMenuItem("��������������� �������");
		compMenu.add(compMenuCalculation);
		compMenu.addSeparator();
		compMenuPlayCompet = new JMenuItem("������� �������");
		compMenu.add(compMenuPlayCompet);
		compMenu.addSeparator();
		compMenuInternalRating = new JMenuItem("���������� �������");
		compMenu.add(compMenuInternalRating);
		compMenuRankedList = new JMenuItem("������������� ������");
		compMenu.add(compMenuRankedList);
		compMenu.addSeparator();
		compMenuCompetList = new JMenuItem("���������� ������");
		compMenu.add(compMenuCompetList);
		compMenu.addSeparator();
		compMenuResetCompetResults = new JMenuItem("����� ����������� ��������");
		compMenu.add(compMenuResetCompetResults);
		
		expMenu = new JMenu("�������");
		menuBar.add(expMenu);
		expMenuOrganisations = new JMenuItem("������� �����������");
		expMenu.add(expMenuOrganisations);
		expMenuCompetGroup = new JMenuItem("���������� ������");
		expMenu.add(expMenuCompetGroup);
		expMenuPlan = new JMenuItem("���� ������");
		expMenu.add(expMenuPlan);
		expMenuApplications = new JMenuItem("��������� ������������");
		expMenu.add(expMenuApplications);
		expMenuResults = new JMenuItem("���������� ��������");
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
		addButton = new JButton("��������");
		editButton = new JButton("�������������");
		deleteButton = new JButton("�������");
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
				new TitledBorder(null, "�������� ����������", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		JLabel idLabel = new JLabel("�:  ");
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
		JLabel surnameLabel = new JLabel("�������:     ");
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
		JLabel nameLabel = new JLabel("���:                ");
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
		JLabel patronymicLabel = new JLabel("��������:      ");
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
		JLabel sexLabel = new JLabel("���:                        ");
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
		JLabel dbLabel = new JLabel("���� ��������:  ");
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
		JLabel nationalityLabel = new JLabel("�����������:      ");
		panelNationality.add(nationalityLabel);
		comboNationality = new JComboBox(arrNationality);
		comboNationality.setSelectedIndex(0);
		comboNationality.setPreferredSize(dimText);
		panelNationality.add(comboNationality);
		gbc.gridy = 3;
		GIPanel.add(panelNationality, gbc);

		panelInfoBackDoc = new JPanel();
		panelInfoBackDoc.setBorder(new TitledBorder(null, "�������� � �������� ����������", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		// panelInfoBackDoc.setBounds(10, 10, 10, 10);

		GIPanelMain.add(panelInfoBackDoc);
		panelInfoBackDoc.setLayout(new GridBagLayout());

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.anchor = GridBagConstraints.WEST;
		gbc2.insets = new Insets(0, 0, 5, 65);

		panelReturnReason = new JPanel();
		panelReturnReason.setLayout(new FlowLayout());

		JLabel returnReasonlbl = new JLabel("������� ��������   ");
		panelReturnReason.add(returnReasonlbl);

		comboReturnReason = new JComboBox(arrReturnReason);
		comboNationality.setSelectedIndex(0);
		panelReturnReason.add(comboReturnReason);

		panelInfoBackDoc.add(panelReturnReason, gbc2);

		panelDateReturn = new JPanel();
		panelDateReturn.setLayout(new FlowLayout());

		panelDateReturn.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelDateReturn.setMaximumSize(dimPanel);
		JLabel dateReturnLabel = new JLabel("���� �������� ����������:     ");
		panelDateReturn.add(dateReturnLabel);
		textDateReturn = new JTextField();
		textDateReturn.setPreferredSize(dimText);
		panelDateReturn.add(textDateReturn);
		checkBackDoc = new JCheckBox("������ ���������");
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
		
		userInfoTPane.add("����-��� ������", contGroupPanel);
		userInfoTPane.add("�����-��� ���������", entranceTestpPanel);
		userInfoTPane.add("���-��� ����������", indAchivPanel);
		userInfoTPane.add("�����������", educPanel);
		userInfoTPane.add("����� � ��������", contPanel);
		userInfoTPane.add("�������", createPassportPanel());
		//userInfoTPane.setPreferredSize(new Dimension(300, 250));
		
		centralPanel.add(userInfoTPane);

		setPreferredSize(new Dimension(1100, 500));
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
		JLabel docTypeLabel = new JLabel("��� ���������");
		comboDocType = new JComboBox(arrDocType);
		docTypePanel.add(docTypeLabel);
		docTypePanel.add(comboDocType);
		passportPanel.add(docTypePanel, gbc3);
		
		JPanel seriaPanel = new JPanel();
		seriaPanel.setLayout(new FlowLayout());
		JLabel seriaLabel = new JLabel("����� ");
		seriaPanel.add(seriaLabel);
		textSeria = new JTextField();
		textSeria.setPreferredSize(dimTextPassport);
		seriaPanel.add(textSeria);
		gbc3.gridy = 1;
		passportPanel.add(seriaPanel, gbc3);
		
		JPanel numPanel = new JPanel();
		numPanel.setLayout(new FlowLayout());
		JLabel numLabel = new JLabel("����� ");
		numPanel.add(numLabel);
		textNum = new JTextField();
		textNum.setPreferredSize(dimTextPassport);
		numPanel.add(textNum);
		gbc3.gridx = 1;
		passportPanel.add(numPanel, gbc3);
		
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new FlowLayout());
		JLabel dateLabel = new JLabel("���� ������ ");
		datePanel.add(dateLabel);
		textDate = new JTextField();
		textDate.setPreferredSize(dimTextPassport);
		datePanel.add(textDate);
		gbc3.gridx = 2;
		passportPanel.add(datePanel, gbc3);
		
		JPanel issuedByPanel = new JPanel();
		issuedByPanel.setLayout(new FlowLayout());
		JLabel issuedByLabel = new JLabel("��� �����");
		issuedByPanel.add(issuedByLabel);
		textIssuedBy = new JTextField();
		textIssuedBy.setPreferredSize(new Dimension(350, 70));
		issuedByPanel.add(textIssuedBy);
		gbc3.gridx = 0;
		gbc3.gridy = 2;
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
