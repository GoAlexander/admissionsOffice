package tab_entrance_tests;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Vector;

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
	private String[] nameEntranceTest, blockEntranceTest,
			entranceTestColumnNames = { "Наименование", "Группа", "Блок испытаний", "Дата испытания", "Балл" };

	public EntranceTestsPanel() {
		this.setLayout(new BorderLayout());

		entranceTestTable = new JTable(entranceTestTM);
		// entranceTestTM.setDataVector(null, entranceTestColumnNames);
		entranceTestTM.setDataVector(ModelDBConnection.getAllEntranceTestsResultsByAbiturientId("0", false),
				entranceTestColumnNames);
		JScrollPane scrPane = new JScrollPane(entranceTestTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		entranceTestTable.setMaximumSize(new Dimension(100, 100));
		entranceTestTable.setRowHeight(25);
		this.add(scrPane, BorderLayout.CENTER);

		nameEntranceTest = ModelDBConnection.getNamesFromTableOrderedById("EntranceTest");
		createCheckboxTable(entranceTestTable, 0, nameEntranceTest);

		String[] groupEntranceTest = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
		createCheckboxTable(entranceTestTable, 1, groupEntranceTest);

		blockEntranceTest = ModelDBConnection.getNamesFromTableOrderedById("TestBox");
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
			Vector<Vector<Object>> data = entranceTestTM.getDataVector();
			Object[] tmpdata;

			String[][] data_new = new String[data.size()][10];
			// , data_old =
			// ModelDBConnection.getAllEntranceTestsResultsByAbiturientId(currentAbit,
			// true);

			// int data_old_length = data_old == null ? 0 : data_old.length;

			for (int i = 0; i < data.size(); i++) {
				tmpdata = data.elementAt(i).toArray();
				data_new[i][0] = currentAbit;
				data_new[i][1] = "1";
				for (int j = 0; !tmpdata[0].toString().equals(nameEntranceTest[j]); j++, data_new[i][1] = String
						.valueOf(j + 1))
					;
				if (tmpdata[1] != null) {
					data_new[i][2] = tmpdata[1].toString();
					data_new[i][3] = String
							.valueOf(ModelDBConnection.getFreeNumberInGroupByExam(data_new[i][1], data_new[i][2]));
					System.out.println(data_new[i][3]);
				}

				if (tmpdata[2] != null) {
					data_new[i][4] = "1";
					for (int j = 0; !tmpdata[2].toString().equals(blockEntranceTest[j]); j++, data_new[i][4] = String
							.valueOf(j + 1))
						;
				}
				if (tmpdata[3] != null)
					data_new[i][5] = tmpdata[3].toString();
				if (tmpdata[4] != null)
					data_new[i][6] = tmpdata[4].toString();

				data_new[i][7] = "1";
				data_new[i][8] = null;

				data_new[i][9] = (specialCond.isSelected()) ? "1" : "0";
			}

			ArrayList<Integer> mistakesIndices = checkData(data_new);
			if (mistakesIndices.contains(0))
				MessageProcessing.displayErrorMessage(null, 14);
			if (mistakesIndices.contains(2))
				MessageProcessing.displayErrorMessage(null, 12);
			if (mistakesIndices.contains(4))
				MessageProcessing.displayErrorMessage(null, 10);
			if (mistakesIndices.contains(5))
				MessageProcessing.displayErrorMessage(null, 13);
			if (mistakesIndices.contains(6))
				MessageProcessing.displayErrorMessage(null, 11);

			if (mistakesIndices.isEmpty()) {
				ModelDBConnection.deleteElementInTableById("AbiturientEntranceTests", currentAbit);
				for (int i = 0; i < data.size(); i++) {
					ModelDBConnection.updateAbiturientEntranceTestsResultsByID(data_new[i]);
				}
				MessageProcessing.displaySuccessMessage(this, 9);
				entranceTestTable.clearSelection();
				this.setEditable(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private ArrayList<Integer> checkData(String[][] data) {
		ArrayList<Integer> mistakesIndices = new ArrayList<Integer>();
		if (data.length > 1 && data[0][4].equals(data[1][4]))
			mistakesIndices.add(0);
		for (int i = 0; i < data.length; i++) {
			if (data[i][2] == null)
				mistakesIndices.add(2);
			if (data[i][4] == null)
				mistakesIndices.add(4);
			if (data[i][5] != null && (!data[i][5].matches("^[0-9.]+$")
					|| !data[i][5].matches("(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)\\d\\d)")))
				mistakesIndices.add(5);
			if (data[i][6] == null || !data[i][6].matches("^[0-9]+$"))
				mistakesIndices.add(6);
		}
		return mistakesIndices;
	}

	public void setValues(String aid) {
		currentAbit = aid;
		entranceTestTM.setDataVector(ModelDBConnection.getAllEntranceTestsResultsByAbiturientId(aid, false),
				entranceTestColumnNames);

		specialCond.setSelected(ModelDBConnection.needAbiturientSpecialConditionsByID(aid));

		nameEntranceTest = ModelDBConnection.getNamesFromTableOrderedById("EntranceTest");
		createCheckboxTable(entranceTestTable, 0, nameEntranceTest);

		String[] groupEntranceTest = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
		createCheckboxTable(entranceTestTable, 1, groupEntranceTest);

		blockEntranceTest = ModelDBConnection.getNamesFromTableOrderedById("TestBox");
		createCheckboxTable(entranceTestTable, 2, blockEntranceTest);

		this.setEditable(false);
	}

	public void setEditable(boolean state) {
		entranceTestTable.setEnabled(state);
		saveTestResultButton.setEnabled(state);
		specialCond.setEnabled(state);

		editTestResultButton.setEnabled(!state);
	}

	public ArrayList<String> getExamsDates() {
		ArrayList<String> examsDates = new ArrayList<String>();
		Vector<Vector<Object>> data = entranceTestTM.getDataVector();

		for (int i = 0; i < data.size(); i++) {
			if (data.elementAt(i).toArray()[3] != null)
				examsDates.add(data.elementAt(i).toArray()[3].toString());
			else
				examsDates.add("");
		}

		for (String examsDate : examsDates)
			System.out.println(examsDate);
		return examsDates;
	}

	private void createCheckboxTable(JTable table, int numColumn, String[] dataCheck) {
		TableColumn tmpColumn = table.getColumnModel().getColumn(numColumn);
		JComboBox<String> comboBox = new JComboBox<String>(dataCheck);

		DefaultCellEditor defaultCellEditor = new DefaultCellEditor(comboBox);
		tmpColumn.setCellEditor(defaultCellEditor);
		tmpColumn.setCellRenderer(new CheckBoxCellRenderer(comboBox));
	}

	public String getNeedSpecialConditions() {
		return (specialCond.isSelected()) ? "Нуждаюсь" : "Не нуждаюсь";
	}
}
