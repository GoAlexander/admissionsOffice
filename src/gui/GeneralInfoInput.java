package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.util.EventObject;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JDateChooser;

import backend.MessageProcessing;
import backend.ModelDBConnection;

public class GeneralInfoInput extends JFrame {

	private JPanel mainPanel, centralPanel, GIPanel, panelSurname, panelName, panelPatronymic, panelID, panelSex,
			panelDB, panelNationality, panelInfoBackDoc, panelReturnReason, panelDateReturn, tablePanel, btnTablePanel,
			passportPanel, entranceTestPanel, EntranceTestTablePanel;
	private Dimension dimPanel = new Dimension(300, 40);
	private Dimension dimText = new Dimension(170, 25);
	private Dimension dimTextDigitInfo = new Dimension(139, 25);
	private Dimension dimRigidArea = new Dimension(10, 0);
	private Dimension dimStartRigidArea = new Dimension(50, 0);
	private JTextField textID, textDateReturn, textSeria, textNum, textDate, textIssuedBy, textplaceBirth;
	private GridBagConstraints gbc;

	private String[] columnNames = { "№", "Фамилия", "Имя", "Отчество" };
	private String[] entranceTestColumnNames = { "Наименование", "Группа", "Блок испытаний", "Дата испытания", "Балл" };
	private String[] individAchivColumnNames = { "Наименование", "Балл", "Подтверждающий документ" };
	private GUITableModel currentTM = new GUITableModel();
	private GUITableModel entranceTestTM = new GUITableModel();
	private GUITableModel individAchivTM = new GUITableModel();
	private JTable dataTable, entranceTestTable, indAchivTable;
	private JCheckBox checkBackDoc;
	private JTabbedPane userInfoTPane;

	private JComboBox comboSexList, comboNationality, comboReturnReason, comboDocType;
	private JDateChooser calendar;

	private String[] arrSex = { "Женский", "Мужской" };
	private String[] arrNationality = { "РФ", "Украина", "Белорусь", "Казахстан" };
	private String[] arrReturnReason = { "r1                              ", "r2     ", "r3     " };
	private String[] arrDocType = { "паспорт РФ", "паспорт Украина" };

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
		JScrollPane compGroupPanel = createCompGroupPanel();
		JPanel entranceTestpPanel = createEntranceTestPanel();
		JPanel indAchivPanel = createIndAchivPanel();
		JPanel educPanel = createEducPanel();
		JPanel contPanel = createContPanel();
		JPanel passportPanel = createPassportPanel();

		userInfoTPane.add("Конк-ные группы", compGroupPanel);
		userInfoTPane.add("Вступ-ные испытания", entranceTestpPanel);
		userInfoTPane.add("Инд-ные достижения", indAchivPanel);
		userInfoTPane.add("Образование", educPanel);
		userInfoTPane.add("Адрес и контакты", contPanel);
		userInfoTPane.add("Паспорт", passportPanel);
		// userInfoTPane.setPreferredSize(new Dimension(300, 250));

		centralPanel.add(userInfoTPane);

		setPreferredSize(new Dimension(1100, 740));
		pack();

		dataTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					try {
						String[] selectedAbitGeneralInfo;
						if (dataTable.getSelectedRow() >= 0) {
							selectedAbitGeneralInfo = ModelDBConnection
									.getAbiturientGeneralInfoByID(String.valueOf(Integer.valueOf((String) currentTM.getValueAt(dataTable.getSelectedRow(), 0))));
						} else {
							selectedAbitGeneralInfo = new String[10];
							for (int i = 0; i < selectedAbitGeneralInfo.length; i++)
								selectedAbitGeneralInfo[i] = "";
						}
						((JTextField) panelID.getComponent(1)).setText(selectedAbitGeneralInfo[0]);
						((JTextField) panelSurname.getComponent(1)).setText(selectedAbitGeneralInfo[1]);
						((JTextField) panelName.getComponent(1)).setText(selectedAbitGeneralInfo[2]);
						((JTextField) panelPatronymic.getComponent(1)).setText(selectedAbitGeneralInfo[3]);
						SimpleDateFormat format = new SimpleDateFormat();
						format.applyPattern("yyyy-MM-dd");
						calendar.setDate(selectedAbitGeneralInfo[4].equals("") ? null
								: format.parse(selectedAbitGeneralInfo[4]));
						comboSexList.setSelectedIndex(selectedAbitGeneralInfo[5].equals("") ? -1
								: Integer.valueOf(selectedAbitGeneralInfo[5]) - 1);
						comboNationality.setSelectedIndex(selectedAbitGeneralInfo[6].equals("") ? -1
								: Integer.valueOf(selectedAbitGeneralInfo[6]) - 1);
						comboReturnReason.setSelectedIndex(selectedAbitGeneralInfo[8].equals("") ? -1
								: Integer.valueOf(selectedAbitGeneralInfo[8]) - 1);
						textDateReturn.setText(selectedAbitGeneralInfo[9]);
						if (selectedAbitGeneralInfo[8].equals(""))
							checkBackDoc.setSelected(false);
						else
							checkBackDoc.setSelected(true);
						textDateReturn.setEnabled(false);
						comboReturnReason.setEnabled(false);
					} catch (SQLException | ParseException e1) {
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
				// Временно
				// Раскомментировать, когда добавится соответствующий компонент
				// интерфейса
				// abitBaseInfo[7] = textDateRecDoc.getText();
				abitBaseInfo[7] = "12.12.2009";
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
				// Временно
				// Раскомментировать, когда добавится соответствующий компонент
				// интерфейса
				// textDateRecDoc.setEditable(false);
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
				// Временно
				// Раскомментировать, когда добавится соответствующий компонент
				// интерфейса
				// textDateRecDoc.setEditable(true);
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

	// ************************************************ВРЕМЕННО
	private JScrollPane createCompGroupPanel() {
		JPanel compGroupPanel = new JPanel();
		compGroupPanel.setLayout(new BoxLayout(compGroupPanel, BoxLayout.PAGE_AXIS));

		JScrollPane pane = new JScrollPane(compGroupPanel);

		int numGroup = 3;
		for (int i = 0; i < getAllCompGroup(numGroup).size(); i++) {
			compGroupPanel.add(getAllCompGroup(numGroup).get(i));
			compGroupPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(0, 1));
		JButton addNewGroup = new JButton("Добавить новую конкурсную группу");
		addNewGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddNewCompetGroup openCompetGroup = new AddNewCompetGroup();
				openCompetGroup.setVisible(true);
			}
		});
		btnPanel.add(addNewGroup);
		compGroupPanel.add(btnPanel);

		return pane;
	}

	private Vector<JPanel> getAllCompGroup(int numGroup) {
		Vector<JPanel> allGroup = new Vector<JPanel>();
		for (int i = 0; i < numGroup; i++) {
			JPanel tmp = getMainCompGroupPanel();
			allGroup.add(tmp);
		}

		return allGroup;
	}

	private JPanel getMainCompGroupPanel() {
		JPanel mainCompGroupPanel = new JPanel();
		mainCompGroupPanel.setLayout(new BorderLayout());
		mainCompGroupPanel.setBorder(BorderFactory.createEtchedBorder());

		JPanel compGroupPanel = new JPanel();
		compGroupPanel.setLayout(new BoxLayout(compGroupPanel, BoxLayout.PAGE_AXIS));
		mainCompGroupPanel.add(compGroupPanel, BorderLayout.CENTER);

		JPanel dirSpecPanel = new JPanel();
		dirSpecPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel dirLabel = new JLabel("Направление");
		dirSpecPanel.add(dirLabel);
		JTextField textDir = new JTextField();
		textDir.setPreferredSize(dimText);
		dirSpecPanel.add(textDir);
		dirSpecPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel specLabel = new JLabel("Специальность");
		dirSpecPanel.add(specLabel);
		JTextField textSpec = new JTextField();
		textSpec.setPreferredSize(dimText);
		dirSpecPanel.add(textSpec);
		compGroupPanel.add(dirSpecPanel);

		JPanel groupBallPanel = new JPanel();
		groupBallPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel groupLabel = new JLabel("Конкурсная группа");
		groupBallPanel.add(groupLabel);
		JTextField textGroup = new JTextField();
		textGroup.setPreferredSize(dimText);
		groupBallPanel.add(textGroup);
		groupBallPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel ballLabel = new JLabel("Конкурсный балл");
		groupBallPanel.add(ballLabel);
		JTextField textBall = new JTextField();
		textBall.setPreferredSize(dimText);
		groupBallPanel.add(textBall);
		compGroupPanel.add(groupBallPanel);

		JButton showInfo = new JButton("+");
		showInfo.setForeground(Color.BLACK);
		showInfo.setFont(new Font("Tahoma", Font.BOLD, 20));
		mainCompGroupPanel.add(showInfo, BorderLayout.LINE_END);

		JPanel allInfoPanel = new JPanel();
		// allInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		mainCompGroupPanel.add(allInfoPanel, BorderLayout.PAGE_END);
		showInfo.addActionListener(new OpenCompetGroupPanelListener(allInfoPanel));

		return mainCompGroupPanel;
	}

	private JPanel createEducPanel() {
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

	private JPanel createAddEducPanel(String name) {
		JPanel addEducPanel = new JPanel();
		addEducPanel.setBorder(new TitledBorder(null, name, TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		JTextField TextSpeciality = new JTextField();
		TextSpeciality.setPreferredSize(new Dimension(538, 25));
		specialityEducPanel.add(TextSpeciality);
		addEducPanel.add(specialityEducPanel);

		JPanel issuedByEducPanel = new JPanel();
		issuedByEducPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		issuedByEducPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel issuedByLabel = new JLabel("Кем выдан");
		issuedByEducPanel.add(issuedByLabel);
		JTextArea textIssuedBy = new JTextArea(2, 51);
		JScrollPane paneIssuedBy = new JScrollPane(textIssuedBy);
		textIssuedBy.setLineWrap(true);
		issuedByEducPanel.add(paneIssuedBy);
		addEducPanel.add(issuedByEducPanel);

		return addEducPanel;
	}

	private JPanel createEntranceTestPanel() {
		entranceTestPanel = new JPanel();
		entranceTestPanel.setLayout(new BorderLayout());

		EntranceTestTablePanel = new JPanel();
		entranceTestTable = new JTable(entranceTestTM);
		entranceTestTM.setDataVector(null, entranceTestColumnNames);
		JScrollPane scrPane = new JScrollPane(entranceTestTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		entranceTestTable.setMaximumSize(new Dimension(100, 100));
		entranceTestTable.setRowHeight(25);
		entranceTestPanel.add(scrPane, BorderLayout.CENTER);
		// ***test data
		entranceTestTM.setDataVector(new Object[][] { { "1", "123", "123", "123", "123" },
				{ "2", "123", "123", "123", "123" }, { "3", "123", "123", "123", "123" }, }, entranceTestColumnNames);

		String[] nameEntranceTest = { "n1", "n2", "n3" };
		createCheckboxTable(entranceTestTable, 0, nameEntranceTest);

		String[] groupEntranceTest = { "gr1", "gr2", "gr3" };
		createCheckboxTable(entranceTestTable, 1, groupEntranceTest);

		String[] blockEntranceTest = { "bl1", "bl2", "bl3" };
		createCheckboxTable(entranceTestTable, 2, blockEntranceTest);

		JPanel butPanel = new JPanel();
		butPanel.setLayout(new BoxLayout(butPanel, BoxLayout.PAGE_AXIS));

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

	private JPanel createIndAchivPanel() {
		JPanel indAchivPanel = new JPanel();
		indAchivPanel.setLayout(new BorderLayout());

		// JPanel indAchivTablePanel = new JPanel();
		indAchivTable = new JTable(individAchivTM);
		individAchivTM.setDataVector(null, individAchivColumnNames);

		JScrollPane scrPane = new JScrollPane(indAchivTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		indAchivTable.setMaximumSize(new Dimension(100, 100));
		indAchivPanel.add(scrPane, BorderLayout.CENTER);
		indAchivTable.setRowHeight(37);
		// ***test data

		individAchivTM.setDataVector(new Object[][] { { "1", "123", "123", "123", "123" },
				{ "2", "123", "123", "123", "123" }, { "3", "123", "123", "123", "123" }, }, individAchivColumnNames);

		String[] nameIndAchivTest = { "n1", "n2", "n3" };
		createCheckboxTable(indAchivTable, 0, nameIndAchivTest);

		EditWatchRenderer renderer = new EditWatchRenderer();
		indAchivTable.getColumnModel().getColumn(2).setCellRenderer(renderer);
		indAchivTable.getColumnModel().getColumn(2).setCellEditor(new AcceptRejectEditor());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		JPanel longButtonPanel = new JPanel();
		longButtonPanel.setLayout(new GridLayout(0, 1));
		JButton addNewAchivBtn = new JButton("Добавить новое достижение");
		longButtonPanel.add(addNewAchivBtn);

		Dimension minSize = new Dimension(5, 10);
		Dimension prefSize = new Dimension(5, 10);
		Dimension maxSize = new Dimension(Short.MAX_VALUE, 100);

		buttonPanel.add(new Box.Filler(minSize, prefSize, maxSize));
		buttonPanel.add(longButtonPanel);

		buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel buttonEditSavePanel = new JPanel();
		buttonEditSavePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton editIndAchivButton = new JButton("Редактировать");
		buttonEditSavePanel.add(editIndAchivButton);
		JButton saveIndAchivButton = new JButton("Сохранить");
		buttonEditSavePanel.add(saveIndAchivButton);
		buttonPanel.add(buttonEditSavePanel);

		indAchivPanel.add(buttonPanel, BorderLayout.PAGE_END);

		return indAchivPanel;
	}

	private JPanel createContPanel() {
		JPanel contPanel = new JPanel();
		contPanel.setLayout(new BoxLayout(contPanel, BoxLayout.PAGE_AXIS));

		JPanel adressPanel = new JPanel();
		adressPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		adressPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel indexLabel = new JLabel("Индекс");
		adressPanel.add(indexLabel);
		JTextField textIndex = new JTextField();
		textIndex.setPreferredSize(dimTextDigitInfo);
		adressPanel.add(textIndex);
		adressPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel regionLabel = new JLabel("Регион");
		adressPanel.add(regionLabel);

		String[] arrRegionType = { "                    1", "2", "3" };
		JComboBox comboRegionType = new JComboBox(arrRegionType);
		adressPanel.add(comboRegionType);

		contPanel.add(adressPanel);

		JPanel typePunktPanel = new JPanel();
		typePunktPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		typePunktPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel typePunktLabel = new JLabel("Тип населенного пункта");
		typePunktPanel.add(typePunktLabel);
		String[] arrPunktType = { "                    1", "2", "3" };
		JComboBox comboPunktType = new JComboBox(arrPunktType);
		typePunktPanel.add(comboPunktType);

		contPanel.add(typePunktPanel);

		JPanel adressLivingPanel = new JPanel();
		adressLivingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		adressLivingPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel adressLivingLabel = new JLabel("Адрес");
		adressLivingPanel.add(adressLivingLabel);
		JTextArea textAdressLiving = new JTextArea(2, 51);
		JScrollPane paneIssuedBy = new JScrollPane(textAdressLiving);
		textAdressLiving.setLineWrap(true);
		adressLivingPanel.add(paneIssuedBy);

		contPanel.add(adressLivingPanel);

		JPanel contInfoPanel = new JPanel();
		contInfoPanel.setLayout(new BoxLayout(contInfoPanel, BoxLayout.PAGE_AXIS));
		contInfoPanel.setBorder(
				new TitledBorder(null, "Контактная информация", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JPanel phonePanel = new JPanel();
		phonePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		phonePanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel phoneLabel = new JLabel("Телефон");
		phonePanel.add(phoneLabel);
		JTextField textPhone = new JTextField();
		textPhone.setPreferredSize(new Dimension(545, 25));
		phonePanel.add(textPhone);
		contInfoPanel.add(phonePanel);

		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		emailPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel emailLabel = new JLabel("e-mail");
		emailPanel.add(emailLabel);
		JTextField textEmail = new JTextField();
		textEmail.setPreferredSize(new Dimension(565, 25));
		emailPanel.add(textEmail);
		contInfoPanel.add(emailPanel);

		contPanel.add(contInfoPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton editBtn = new JButton("Редактировать");
		buttonPanel.add(editBtn);
		JButton saveBtn = new JButton("Сохранить");
		buttonPanel.add(saveBtn);
		contPanel.add(buttonPanel);

		return contPanel;
	}

	private JPanel createPassportPanel() {
		passportPanel = new JPanel();
		passportPanel.setLayout(new BoxLayout(passportPanel, BoxLayout.PAGE_AXIS));

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
		JTextArea textIssuedBy = new JTextArea(2, 51);
		JScrollPane paneIssuedBy = new JScrollPane(textIssuedBy);
		textIssuedBy.setLineWrap(true);
		issuedByPanel.add(paneIssuedBy);
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
		JButton editPassportButton = new JButton("Редактировать");
		buttonPanel.add(editPassportButton);
		JButton savePassportButton = new JButton("Сохранить");
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

	public class EditWatchButtonPane extends JPanel {

		private JButton editBt;
		private JButton watchBt;
		private String state;

		public EditWatchButtonPane() {
			setLayout(new FlowLayout());
			editBt = new JButton("Редактировать");
			editBt.setActionCommand("Редактировать");
			watchBt = new JButton("Просмотреть");
			watchBt.setActionCommand("Просмотреть");

			add(editBt);
			add(watchBt);

			ActionListener listenerWatch = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					WatchConfirmingDoc window = new WatchConfirmingDoc();
					window.setVisible(true);
				}
			};

			editBt.addActionListener(listenerWatch);
			watchBt.addActionListener(listenerWatch);
		}

		public void addActionListener(ActionListener listener) {
			editBt.addActionListener(listener);
			watchBt.addActionListener(listener);
		}

		public String getState() {
			return state;
		}
	}

	public class EditWatchRenderer extends DefaultTableCellRenderer {

		private EditWatchButtonPane acceptRejectPane;

		public EditWatchRenderer() {
			acceptRejectPane = new EditWatchButtonPane();
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				acceptRejectPane.setBackground(table.getSelectionBackground());
			} else {
				acceptRejectPane.setBackground(table.getBackground());
			}
			return acceptRejectPane;
		}
	}

	public class AcceptRejectEditor extends AbstractCellEditor implements TableCellEditor {

		private EditWatchButtonPane acceptRejectPane;

		public AcceptRejectEditor() {
			acceptRejectPane = new EditWatchButtonPane();
			acceptRejectPane.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							stopCellEditing();
						}
					});
				}
			});
		}

		@Override
		public Object getCellEditorValue() {
			return acceptRejectPane.getState();
		}

		@Override
		public boolean isCellEditable(EventObject e) {
			return true;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				acceptRejectPane.setBackground(table.getSelectionBackground());
			} else {
				acceptRejectPane.setBackground(table.getBackground());
			}
			return acceptRejectPane;
		}
	}

	public class OpenCompetGroupPanelListener implements ActionListener {

		private boolean isPushed = false;
		private JPanel panel = new JPanel();

		public OpenCompetGroupPanelListener(JPanel panel) {
			this.panel = panel;
			AddNewCompetGroup openCompetGroup = new AddNewCompetGroup();
			panel.add(openCompetGroup.getAddNewCompetGroup());
			panel.setVisible(false);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				if (!isPushed) {
					((JButton) arg0.getSource()).setText("-");
					panel.setVisible(true);
					isPushed = true;
				} else {
					((JButton) arg0.getSource()).setText("+");
					panel.setVisible(false);
					isPushed = false;

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
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
