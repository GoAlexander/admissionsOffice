package tab_catalogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import backend.MessageProcessing;
import backend.ModelDBConnection;
import general_classes.GUITableModel;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class EditCatalogElem extends JFrame {

	private JPanel mainPanel;
	private EditableJTable dataTable;
	private GUITableModel currentTM = new GUITableModel();
	private String[] columnNames = { "id", "Наименование", "Код ФИС" };

	private JButton addBtn, editBtn, saveBtn, deleteBtn;

	public EditCatalogElem(String table, String title) throws SQLException {

		switch (table) {
		case "EntranceTest":
			columnNames = new String[] { "id", "Наименование", "Минимальный балл", "Код ФИС" };
			break;
		case "IndividualAchievement":
			columnNames = new String[] { "id", "Наименование", "Балл", "Код ФИС" };
			break;
		case "AdmissionPlan":
			columnNames = new String[] { "Код специальности", "Форма обучения", "Конкурсная группа",
					"Целевая организация", "Стандарт образования", "Количество мест" };
			break;
		case "Users":
			columnNames = new String[] { "Логин", "Пароль", "Подпись" };
			break;
		}

		setTitle("Редактирование элементов справочника: " + title);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		getContentPane().add(mainPanel);

		dataTable = new EditableJTable(currentTM);
		dataTable.setEnabled(false);

		String[][] data = ModelDBConnection.getAllFromTableOrderedById(table);

		currentTM.setDataVector(data, columnNames);
		JScrollPane scrPane = new JScrollPane(dataTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		// tablePanel.add(scrPane);
		dataTable.setMaximumSize(new Dimension(100, 100));
		dataTable.setRowHeight(25);
		dataTable.setColumnEditable(0, false);
		// dataTable.getColumnModel().getColumn(0).setMaxWidth(50);
		mainPanel.add(scrPane, BorderLayout.CENTER);

		addBtn = new JButton("Добавить");
		editBtn = new JButton("Редактировать");
		saveBtn = new JButton("Сохранить");
		deleteBtn = new JButton("Удалить");
		saveBtn.setEnabled(false);
		deleteBtn.setEnabled(false);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentTM.addRow(new String[columnNames.length]);
				dataTable.setEnabled(true);
				addBtn.setEnabled(false);
				editBtn.setEnabled(false);
				saveBtn.setEnabled(true);
			}
		});
		buttonPanel.add(addBtn);

		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dataTable.setEnabled(true);
				addBtn.setEnabled(false);
				editBtn.setEnabled(false);
				saveBtn.setEnabled(true);
				deleteBtn.setEnabled(true);
			}
		});
		buttonPanel.add(editBtn);

		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dataTable.isEditing())
					dataTable.getCellEditor().stopCellEditing();
				currentTM.fireTableDataChanged();
				saveButtonActionPerformed(e, table);
			}
		});
		buttonPanel.add(saveBtn);

		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteButtonActionPerformed(e, table);
			}
		});
		buttonPanel.add(deleteBtn);

		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

		setPreferredSize(new Dimension(850, 600));
		pack();

	}

	private void saveButtonActionPerformed(ActionEvent e, String table) {
		try {
			Vector<Vector<Object>> data = currentTM.getDataVector();
			Object[] tmpdata;
			ArrayList<Integer> mistakesIndices;
			String[][] rowData = new String[data.size()][columnNames.length];
			boolean hasNullID = false, hasNullNames = false;
			
			for (int i = 0; i < data.size(); i++) {
				tmpdata = data.elementAt(i).toArray();
				for (int j = 0; j < tmpdata.length; j++) {
					if (tmpdata[j] != null) {
						rowData[i][j] = tmpdata[j].toString();
						if (rowData[i][j].isEmpty())
							rowData[i][j] = null;
					}
				}
				if (rowData[i][0] == null)
					hasNullID = true;
				else if (rowData[i][1] == null)
					hasNullNames = true;
			}

			mistakesIndices = checkData(table, rowData);
			if (hasNullID) {
				MessageProcessing.displayErrorMessage(null, 35);
			} else if (hasNullNames) {
				MessageProcessing.displayErrorMessage(null, 36);
			} else if (mistakesIndices.contains(0))
				MessageProcessing.displayErrorMessage(null, 31);
			else if (mistakesIndices.contains(2)) {
				if (table.equals("EntranceTest") || table.equals("IndividualAchievement"))
					MessageProcessing.displayErrorMessage(null, 32);
				else
					MessageProcessing.displayErrorMessage(null, 33);
			} else if (mistakesIndices.contains(3)) {
				MessageProcessing.displayErrorMessage(null, 33);
			} else if (mistakesIndices.contains(-1)) {
				MessageProcessing.displayErrorMessage(null, 34);
			} else if (mistakesIndices.isEmpty()) {
				for (int i = 0; i < data.size(); i++) {
					ModelDBConnection.updateElementInTableById(table, rowData[i]);
				}
				MessageProcessing.displaySuccessMessage(this, 4);
				dataTable.setEnabled(false);
				addBtn.setEnabled(true);
				editBtn.setEnabled(true);
				saveBtn.setEnabled(false);
				deleteBtn.setEnabled(false);
				dataTable.clearSelection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			MessageProcessing.displayErrorMessage(this, e1);
		}
	}

	private ArrayList<Integer> checkData(String table, String[][] rowData) {
		ArrayList<Integer> mistakesIndices = new ArrayList<Integer>();

		ArrayList<String> allUniqueIDs = new ArrayList<String>();
		ArrayList<String> allUniqueNames = new ArrayList<String>();

		for (int i = 0; i < rowData.length; i++) {
			if (!allUniqueIDs.contains(rowData[i][0]))
				allUniqueIDs.add(rowData[i][0]);
			if (!allUniqueNames.contains(rowData[i][1]))
				allUniqueNames.add(rowData[i][1]);
		}

		if (rowData.length != allUniqueIDs.size() || rowData.length != allUniqueNames.size())
			mistakesIndices.add(-1);

		if (table.equals("EntranceTest") || table.equals("IndividualAchievement")) {
			for (int i = 0; i < rowData.length; i++) {
				if (rowData[i][3] != null && !rowData[i][3].isEmpty() && !rowData[i][3].matches("^[0-9]+$"))
					mistakesIndices.add(3);
			}
		}

		if (!table.equals("Users")) {
			for (int i = 0; i < rowData.length; i++) {
				if (rowData[i][0] != null && !rowData[i][0].matches("^[0-9]+$"))
					mistakesIndices.add(0);
				if (rowData[i][2] != null && !rowData[i][2].matches("^[0-9]+$"))
					mistakesIndices.add(2);
			}
		}

		return mistakesIndices;
	}

	private void deleteButtonActionPerformed(ActionEvent e, String table) {
		try {
			ModelDBConnection.deleteElementInTableById(table,
					(String) currentTM.getValueAt(dataTable.getSelectedRow(), 0));
			MessageProcessing.displaySuccessMessage(this, 5);
			currentTM.removeRow(dataTable.getSelectedRow());
			currentTM.fireTableDataChanged();
		} catch (SQLException e1) {
			e1.printStackTrace();
			MessageProcessing.displayErrorMessage(this, 3);
		}
	}

}
