package tab_achievements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
	private String[] nameIndAchivTest, individAchivColumnNames = { "Наименование", "Балл", "Подтверждающий документ" };

	public IndividualAchievementsPanel() {
		this.setLayout(new BorderLayout());

		indAchivTable = new JTable(individAchivTM);
		individAchivTM.setDataVector(null, individAchivColumnNames);

		JScrollPane scrPane = new JScrollPane(indAchivTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		indAchivTable.setMaximumSize(new Dimension(100, 100));
		this.add(scrPane, BorderLayout.CENTER);
		indAchivTable.setRowHeight(37);
		individAchivTM.setDataVector(null, individAchivColumnNames);

		nameIndAchivTest = ModelDBConnection.getNamesFromTableOrderedById("IndividualAchievement");
		createCheckboxTable(indAchivTable, 0, nameIndAchivTest);

		EditWatchRenderer renderer = new EditWatchRenderer(indAchivTable);
		indAchivTable.getColumnModel().getColumn(2).setCellRenderer(renderer);
		indAchivTable.getColumnModel().getColumn(2).setCellEditor(new AcceptRejectEditor(indAchivTable));

		indAchivTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(indAchivTable.getSelectedRow() > -1)
					if(individAchivTM.getValueAt(indAchivTable.getSelectedRow(), 0) != null) {
						String[] values = new String[3];
						values[0] = currentAbit;
						values[1] = "1";
						String selectedIndAch = (String) individAchivTM.getValueAt(indAchivTable.getSelectedRow(), 0);
						for(int j = 0; !selectedIndAch.equals(nameIndAchivTest[j]); j++, values[1] = String.valueOf(j+1));
						values[2] = (String) individAchivTM.getValueAt(indAchivTable.getSelectedRow(), 1);

						((AcceptRejectEditor)indAchivTable.getColumnModel().getColumn(2).getCellEditor()).setValues(values);
					}
			}
		});

		indAchivTable.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent arg0) 
			{
				if(indAchivTable.getSelectedRow() > -1)
					if(individAchivTM.getValueAt(indAchivTable.getSelectedRow(), 0) != null) {
						String[] values = new String[3];
						values[0] = currentAbit;
						values[1] = "1";
						String selectedIndAch = (String) individAchivTM.getValueAt(indAchivTable.getSelectedRow(), 0);
						for(int j = 0; !selectedIndAch.equals(nameIndAchivTest[j]); j++, values[1] = String.valueOf(j+1));
						values[2] = (String) individAchivTM.getValueAt(indAchivTable.getSelectedRow(), 1);

						((AcceptRejectEditor)indAchivTable.getColumnModel().getColumn(2).getCellEditor()).setValues(values);
					}
			}
		});

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
			Vector<Vector<Object>> data = individAchivTM.getDataVector();
			Object[] tmpdata;

			String[][]	data_new = new String[data.size()][8], 
						data_old = ModelDBConnection.getAllAchievmentsByAbiturientId(currentAbit, true);

			int	data_old_length = data_old == null ? 0 : data_old.length;

			for(int i = 0; i < data.size(); i++) {
				tmpdata = data.elementAt(i).toArray();
				data_new[i][0] = currentAbit;
				data_new[i][1] = "1";
				for(int j = 0; !tmpdata[0].toString().equals(nameIndAchivTest[j]); j++, data_new[i][1] = String.valueOf(j+1));
				if (tmpdata[1] != null) data_new[i][2] = tmpdata[1].toString();
				for(int j = 3; j < data_new[i].length; j++)
					data_new[i][j] = null;
				data_new[i][7] = null;
			}

			for (int i = 0; i < ((data.size() <= data_old_length) ? data.size() : data_old_length); i++) {
				tmpdata = data.elementAt(i).toArray();
				data_new[i][0] = currentAbit;
				data_new[i][1] = "1";
				for(int j = 0; !tmpdata[0].toString().equals(nameIndAchivTest[j]); j++, data_new[i][1] = String.valueOf(j+1));
				if (tmpdata[1] != null) data_new[i][2] = tmpdata[1].toString();
				for(int j = 3; j < data_new[i].length; j++)
					data_new[i][j] = data_old[i][j];
			}

			for(int i = 0; i < data_old_length; i++) {
				String[] data_delete = {data_old[i][0], data_old[i][1]};
				ModelDBConnection.deleteElementInTableByIds("AbiturientIndividualAchievement", data_delete);
			}

			for(int i = 0; i < data.size(); i++) {
				ModelDBConnection.updateAbiturientIndividualAchivementByID(data_new[i]);
			}

			MessageProcessing.displaySuccessMessage(this, 4);

			indAchivTable.clearSelection();
			this.setValues(currentAbit);
			this.setEditable(false);
		} catch (Exception e) {
			e.printStackTrace();
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public void setValues(String aid) {
		currentAbit = aid;
		individAchivTM.setDataVector(ModelDBConnection.getAllAchievmentsByAbiturientId(aid, false), individAchivColumnNames);

		String[] nameIndAchivTest = ModelDBConnection.getNamesFromTableOrderedById("IndividualAchievement");
		createCheckboxTable(indAchivTable, 0, nameIndAchivTest);

		EditWatchRenderer renderer = new EditWatchRenderer(indAchivTable);
		indAchivTable.getColumnModel().getColumn(2).setCellRenderer(renderer);

		indAchivTable.getColumnModel().getColumn(2).setCellEditor(new AcceptRejectEditor(indAchivTable));

		this.setEditable(false);
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

	public String[][] getValues(boolean forDocs) {
		String[][] indAchievments = ModelDBConnection.getAllAchievmentsByAbiturientId(currentAbit, true);;
		if (forDocs) {
			Vector<Vector<Object>> data = individAchivTM.getDataVector();
			for(int i = 0; i < data.size(); i++) {
				indAchievments[i][1] = data.elementAt(i).toArray()[0].toString();
			}
		}
		return indAchievments;
	}
}
