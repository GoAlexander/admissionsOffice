package tab_entrance_tests;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import backend.MessageProcessing;
import backend.ModelDBConnection;
import general_classes.GUITableModel;
import general_classes.CheckBoxCellRenderer;

public class EntranceTestsPanel extends JPanel {
	private String currentAbit;
	private JTable entranceTestTable;
	private GUITableModel entranceTestTM = new GUITableModel();
	private JButton editTestResultButton, saveTestResultButton;
	private JCheckBox specialCond;
	private String[] entranceTestColumnNames = { "Наименование", "Группа", "Блок испытаний", "Дата испытания", "Балл" };

	public EntranceTestsPanel() {
		this.setLayout(new BorderLayout());

		entranceTestTable = new JTable(entranceTestTM);
		//entranceTestTM.setDataVector(null, entranceTestColumnNames);
		entranceTestTM.setDataVector(ModelDBConnection.getAllEntranceTestsResultsByAbiturientId(""), entranceTestColumnNames);
		JScrollPane scrPane = new JScrollPane(entranceTestTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		entranceTestTable.setMaximumSize(new Dimension(100, 100));
		entranceTestTable.setRowHeight(25);
		this.add(scrPane, BorderLayout.CENTER);

		String[] nameEntranceTest = ModelDBConnection.getNamesFromTableOrderedById("EntranceTest");
		createCheckboxTable(entranceTestTable, 0, nameEntranceTest);

		String[] groupEntranceTest = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
		createCheckboxTable(entranceTestTable, 1, groupEntranceTest);

		String[] blockEntranceTest = ModelDBConnection.getNamesFromTableOrderedById("TestBox");
		createCheckboxTable(entranceTestTable, 2, blockEntranceTest);

		JPanel butPanel = new JPanel();
		butPanel.setLayout(new BoxLayout(butPanel, BoxLayout.PAGE_AXIS));

		specialCond = new JCheckBox("Нуждается в специальных условиях");
		specialCond.setAlignmentX(Component.RIGHT_ALIGNMENT);
		butPanel.add(specialCond);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		editTestResultButton = new JButton("Редактировать");
		editTestResultButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editTestResultButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(editTestResultButton);

		saveTestResultButton = new JButton("Сохранить");
		saveTestResultButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveTestResultButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(saveTestResultButton);
		buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		butPanel.add(buttonPanel);
		this.add(butPanel, BorderLayout.PAGE_END);

		this.setEditable(false);
	}

	private void editTestResultButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			this.setEditable(true);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void saveTestResultButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			entranceTestTable.clearSelection();
			this.setEditable(false);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public void setValues(String aid) {
		currentAbit = aid;
		entranceTestTM.setDataVector(ModelDBConnection.getAllEntranceTestsResultsByAbiturientId(aid), entranceTestColumnNames);
	}

	public void setEditable(boolean state) {
		entranceTestTable.setEnabled(state);
		saveTestResultButton.setEnabled(state);
		specialCond.setEnabled(state);

		editTestResultButton.setEnabled(!state);
	}

	private void createCheckboxTable(JTable table, int numColumn, String[] dataCheck) {
		TableColumn tmpColumn = table.getColumnModel().getColumn(numColumn);
		JComboBox<String> comboBox = new JComboBox<String>(dataCheck);

		DefaultCellEditor defaultCellEditor = new DefaultCellEditor(comboBox);
		tmpColumn.setCellEditor(defaultCellEditor);
		tmpColumn.setCellRenderer(new CheckBoxCellRenderer(comboBox));
	}
}
