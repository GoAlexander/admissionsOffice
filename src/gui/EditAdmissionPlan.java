package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import gui.GeneralInfoInput.CheckBoxCellRenderer;

public class EditAdmissionPlan extends JFrame {

	private JPanel mainPanel, tablePanel;
	private JTable dataTable;
	private GUITableModel currentTM = new GUITableModel();
	private String[] columnNames = { "Специальность", "Форма обучения", "Конкурсная группа", "Целевая организация",
			"Стандарт", "План" };

	public EditAdmissionPlan() {

		setTitle("Редактирование плана приема");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel);

		tablePanel = new JPanel();
		dataTable = new JTable(currentTM);
		currentTM.setDataVector(null, columnNames);
		JScrollPane scrPane = new JScrollPane(dataTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		// tablePanel.add(scrPane);
		dataTable.setMaximumSize(new Dimension(100, 100));
		dataTable.setRowHeight(25);
		dataTable.getColumnModel().getColumn(columnNames.length - 1).setMaxWidth(70);
		mainPanel.add(scrPane, BorderLayout.CENTER);
		
		// ***test data
		currentTM.setDataVector(new Object[][] { 
			{ "1", "123", "123", "123", "123", "123" },
			{ "2", "123", "123", "123", "123", "123" }, 
			{ "3", "123", "123", "123", "123", "123" }, 
			}, 
				columnNames);

		String[] arrSpeciality = { "s1", "s2", "s3" };
		createCheckboxTable(dataTable, 0, arrSpeciality);

		String[] arrFormEduc = { "f1", "f2", "f3" };
		createCheckboxTable(dataTable, 1, arrFormEduc);

		String[] arrCompetGroup = { "c1", "c2", "c3" };
		createCheckboxTable(dataTable, 2, arrCompetGroup);
		
		String[] arrOrg = { "o1", "o2", "o3" };
		createCheckboxTable(dataTable, 3, arrOrg);

		String[] arrStandard = { "s1", "s2", "s3" };
		createCheckboxTable(dataTable, 4, arrStandard);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton editBtn = new JButton("Редактировать");
		buttonPanel.add(editBtn);
		JButton saveBtn = new JButton("Сохранить");
		buttonPanel.add(saveBtn);

		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

		setPreferredSize(new Dimension(1000, 500));
		pack();
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
					EditAdmissionPlan window = new EditAdmissionPlan();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
