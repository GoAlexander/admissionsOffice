package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import backend.MessageProcessing;
import backend.ModelDBConnection;

import java.awt.event.ActionListener;
import java.sql.SQLException;
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
				dataTable.setEnabled(false);
				addBtn.setEnabled(true);
				editBtn.setEnabled(true);
				saveBtn.setEnabled(false);
				deleteBtn.setEnabled(false);
				if (dataTable.isEditing())
					dataTable.getCellEditor().stopCellEditing();
				dataTable.clearSelection();
				currentTM.fireTableDataChanged();
				saveButtonActionPerformed(e, table);
			}
		});
		buttonPanel.add(saveBtn);

		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteButtonActionPerformed(e, table);
				currentTM.removeRow(dataTable.getSelectedRow());
				currentTM.fireTableDataChanged();
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
			for (int i = 0; i < data.size(); i++) {
				String[] rowData = new String[columnNames.length];
				tmpdata = data.elementAt(i).toArray();
				for (int j = 0; j < tmpdata.length; j++) {
					if (tmpdata[j] != null)
						rowData[j] = tmpdata[j].toString();
				}
				ModelDBConnection.updateElementInTableById(table, rowData);
			}
			MessageProcessing.displaySuccessMessage(this, 4);
		} catch (SQLException e1) {
			MessageProcessing.displayErrorMessage(this, 2);
		}
	}

	private void deleteButtonActionPerformed(ActionEvent e, String table) {
		try {
			ModelDBConnection.deleteElementInTableById(table, (String) currentTM.getValueAt(dataTable.getSelectedRow(), 0));
			MessageProcessing.displaySuccessMessage(this, 5);
		} catch (SQLException e1) {
			MessageProcessing.displayErrorMessage(this, 3);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Ordinator", "user", "password");
					EditCatalogElem window = new EditCatalogElem("Gender", "Пол");
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
