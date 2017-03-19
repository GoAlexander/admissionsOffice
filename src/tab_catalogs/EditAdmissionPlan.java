package tab_catalogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import backend.MessageProcessing;
import backend.ModelDBConnection;
import general_classes.GUITableModel;

public class EditAdmissionPlan extends JFrame {
	private JPanel mainPanel;

	private JTable dataTable;
	private GUITableModel currentTM = new GUITableModel();

	private String[] arrCouse, arrSpeciality, arrFormEduc, arrCompetGroup, arrOrg, arrStandard, columnNames = { "Направление", "Специальность",
			"Форма обучения", "Конкурсная группа", "Целевая организация", "Стандарт", "План" };

	private JButton addBtn, editBtn, saveBtn, deleteBtn;

	public EditAdmissionPlan() {
		setTitle("Редактирование плана приема");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel);

		new JPanel();
		dataTable = new JTable(currentTM);
		currentTM.setDataVector(null, columnNames);
		JScrollPane scrPane = new JScrollPane(dataTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		// tablePanel.add(scrPane);
		dataTable.setMaximumSize(new Dimension(100, 100));
		dataTable.setRowHeight(25);
		dataTable.getColumnModel().getColumn(columnNames.length - 1).setMaxWidth(70);
		mainPanel.add(scrPane, BorderLayout.CENTER);

		currentTM.setDataVector(ModelDBConnection.getAdmissionPlan(), columnNames);

		arrCouse = ModelDBConnection.getNamesFromTableOrderedById("Course");
		createCheckboxTable(dataTable, 0, arrCouse);
		
		arrSpeciality = ModelDBConnection.getNamesFromTableOrderedById("Speciality");
		createCheckboxTable(dataTable, 1, arrSpeciality);

		arrFormEduc = ModelDBConnection.getNamesFromTableOrderedById("EducationForm");
		createCheckboxTable(dataTable, 2, arrFormEduc);

		arrCompetGroup = ModelDBConnection.getNamesFromTableOrderedById("CompetitiveGroup");
		createCheckboxTable(dataTable, 3, arrCompetGroup);

		arrOrg = ModelDBConnection.getNamesFromTableOrderedById("TargetOrganisation");
		createCheckboxTable(dataTable, 4, arrOrg);

		arrStandard = ModelDBConnection.getNamesFromTableOrderedById("EducationStandard");
		createCheckboxTable(dataTable, 5, arrStandard);

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
				saveButtonActionPerformed(e);
			}
		});
		buttonPanel.add(saveBtn);

		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteButtonActionPerformed(e);
				currentTM.removeRow(dataTable.getSelectedRow());
				currentTM.fireTableDataChanged();
			}
		});
		buttonPanel.add(deleteBtn);

		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

		setPreferredSize(new Dimension(1000, 500));
		pack();
	}

	private void saveButtonActionPerformed(ActionEvent e) {
		try {
			ModelDBConnection.deleteElementInTableByExpression("AdmissionPlan", null, 0);

			Vector<Vector<Object>> data = currentTM.getDataVector();
			Object[] tmpdata;
			for (int i = 0; i < data.size(); i++) {
				String[] rowData = new String[columnNames.length];
				tmpdata = data.elementAt(i).toArray();
				if(tmpdata[0] != null) {
					rowData[0] = "1";
					for(int j = 0; !tmpdata[0].toString().equals(arrCouse[j]); j++, rowData[0] = String.valueOf(j+1));
				}
				if(tmpdata[1] != null) {
					rowData[1] = "1";
					for(int j = 0; !tmpdata[1].toString().equals(arrSpeciality[j]); j++, rowData[1] = String.valueOf(j+1));
				}
				if(tmpdata[2] != null) {
					rowData[2] = "1";
					for(int j = 0; !tmpdata[2].toString().equals(arrFormEduc[j]); j++, rowData[2] = String.valueOf(j+1));
				}
				if(tmpdata[3] != null) {
					rowData[3] = "1";
					for(int j = 0; !tmpdata[3].toString().equals(arrCompetGroup[j]); j++, rowData[3] = String.valueOf(j+1));
				}
				if(tmpdata[4] != null) {
					rowData[4] = "1";
					for(int j = 0; !tmpdata[4].toString().equals(arrOrg[j]); j++, rowData[4] = String.valueOf(j+1));
				}
				if(tmpdata[5] != null) {
					rowData[5] = "1";
					for(int j = 0; !tmpdata[5].toString().equals(arrStandard[j]); j++, rowData[5] = String.valueOf(j+1));
				}
				if(tmpdata[6] != null) rowData[6] = tmpdata[6].toString();

				ModelDBConnection.updateElementInTableByExpression("AdmissionPlan", rowData, 6);
			}
			MessageProcessing.displaySuccessMessage(this, 4);
		} catch (SQLException e1) {
			MessageProcessing.displayErrorMessage(this, 2);
		}
	}

	private void deleteButtonActionPerformed(ActionEvent e) {
		try {
			Vector<Vector<Object>> data = currentTM.getDataVector();
			Object[] tmpdata;
			String[] rowData = new String[columnNames.length];
			tmpdata = data.elementAt(dataTable.getSelectedRow()).toArray();
			if(tmpdata[0] != null) {
				rowData[0] = "1";
				for(int j = 0; !tmpdata[0].toString().equals(arrSpeciality[j]); j++, rowData[0] = String.valueOf(j+1));
			}
			if(tmpdata[1] != null) {
				rowData[1] = "1";
				for(int j = 0; !tmpdata[1].toString().equals(arrFormEduc[j]); j++, rowData[1] = String.valueOf(j+1));
			}
			if(tmpdata[2] != null) {
				rowData[2] = "1";
				for(int j = 0; !tmpdata[2].toString().equals(arrCompetGroup[j]); j++, rowData[2] = String.valueOf(j+1));
			}
			if(tmpdata[3] != null) {
				rowData[3] = "1";
				for(int j = 0; !tmpdata[3].toString().equals(arrOrg[j]); j++, rowData[3] = String.valueOf(j+1));
			}
			if(tmpdata[4] != null) {
				rowData[4] = "1";
				for(int j = 0; !tmpdata[4].toString().equals(arrStandard[j]); j++, rowData[4] = String.valueOf(j+1));
			}
			if(tmpdata[5] != null) rowData[5] = tmpdata[5].toString();

			ModelDBConnection.deleteElementInTableByExpression("AdmissionPlan", rowData, 5);

			MessageProcessing.displaySuccessMessage(this, 5);
		} catch (SQLException e1) {
			e1.printStackTrace();
			MessageProcessing.displayErrorMessage(this, 3);
		}
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
					ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Ordinator", "user", "password");
					ModelDBConnection.initConnection();
					EditAdmissionPlan window = new EditAdmissionPlan();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
