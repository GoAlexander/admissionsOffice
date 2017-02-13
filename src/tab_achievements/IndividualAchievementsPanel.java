package tab_achievements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import backend.MessageProcessing;
import backend.ModelDBConnection;
import general_classes.CheckBoxCellRenderer;
import general_classes.GUITableModel;

public class IndividualAchievementsPanel extends JPanel{
	private String currentAbit;
	private JTable indAchivTable;
	private JButton addNewAchievmentButton, editAchievmentButton, saveAchievmentButton;
	private GUITableModel individAchivTM = new GUITableModel();
	private String[] individAchivColumnNames = { "Наименование", "Балл", "Подтверждающий документ" };

	public IndividualAchievementsPanel() {
		this.setLayout(new BorderLayout());

		indAchivTable = new JTable(individAchivTM);
		individAchivTM.setDataVector(null, individAchivColumnNames);

		JScrollPane scrPane = new JScrollPane(indAchivTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		indAchivTable.setMaximumSize(new Dimension(100, 100));
		this.add(scrPane, BorderLayout.CENTER);
		indAchivTable.setRowHeight(37);
		// ***test data

		individAchivTM.setDataVector(null, individAchivColumnNames);

		String[] nameIndAchivTest = ModelDBConnection.getNamesFromTableOrderedById("IndividualAchievement");
		createCheckboxTable(indAchivTable, 0, nameIndAchivTest);

		EditWatchRenderer renderer = new EditWatchRenderer(indAchivTable);
		indAchivTable.getColumnModel().getColumn(2).setCellRenderer(renderer);
		indAchivTable.getColumnModel().getColumn(2).setCellEditor(new AcceptRejectEditor(indAchivTable));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		JPanel longButtonPanel = new JPanel();
		longButtonPanel.setLayout(new GridLayout(0, 1));
		addNewAchievmentButton = new JButton("Добавить новое достижение");
		addNewAchievmentButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addNewAchievmentButtonActionPerformed(evt);
			}
		});

		longButtonPanel.add(addNewAchievmentButton);

		Dimension minSize = new Dimension(5, 10);
		Dimension prefSize = new Dimension(5, 10);
		Dimension maxSize = new Dimension(Short.MAX_VALUE, 100);

		buttonPanel.add(new Box.Filler(minSize, prefSize, maxSize));
		buttonPanel.add(longButtonPanel);

		buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel buttonEditSavePanel = new JPanel();
		buttonEditSavePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		editAchievmentButton = new JButton("Редактировать");
		editAchievmentButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editAchievmentButtonActionPerformed(evt);
			}
		});

		buttonEditSavePanel.add(editAchievmentButton);

		saveAchievmentButton = new JButton("Сохранить");
		saveAchievmentButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveAchievmentButtonActionPerformed(evt);
			}
		});

		buttonEditSavePanel.add(saveAchievmentButton);
		buttonPanel.add(buttonEditSavePanel);

		this.add(buttonPanel, BorderLayout.PAGE_END);
		
		this.setEditable(false);
	}

	private void addNewAchievmentButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			individAchivTM.addRow(new String[individAchivColumnNames.length]);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void editAchievmentButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			this.setEditable(true);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void saveAchievmentButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			indAchivTable.clearSelection();
			this.setEditable(false);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public void setValues(String aid) {
		currentAbit = aid;
		individAchivTM.setDataVector(ModelDBConnection.getAllAchievmentsByAbiturientId(aid), individAchivColumnNames);
	}

	public void setEditable(boolean state) {
		indAchivTable.setEnabled(state);
		saveAchievmentButton.setEnabled(state);
		addNewAchievmentButton.setEnabled(state);

		editAchievmentButton.setEnabled(!state);
	}

	private void createCheckboxTable(JTable table, int numColumn, String[] dataCheck) {
		TableColumn tmpColumn = table.getColumnModel().getColumn(numColumn);
		JComboBox<String> comboBox = new JComboBox<String>(dataCheck);

		DefaultCellEditor defaultCellEditor = new DefaultCellEditor(comboBox);
		tmpColumn.setCellEditor(defaultCellEditor);
		tmpColumn.setCellRenderer(new CheckBoxCellRenderer(comboBox));
	}
}
