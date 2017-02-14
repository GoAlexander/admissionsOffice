package tab_achievements;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import backend.MessageProcessing;
import backend.ModelDBConnection;

public class EditWatchAchievmentPanel extends JPanel{
	JTable table;

	private JButton editButton, watchButton;
	private String state;
	
	private String[] data;
	
	public EditWatchAchievmentPanel(JTable table) {
		this.table = table;
		
		this.data = data;
		
		this.setLayout(new FlowLayout());

		editButton = new JButton("Редактировать");
		editButton.setActionCommand("Редактировать");
		editButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editButtonActionPerformed(evt);
				table.getSelectedRow();
			}
		});

		watchButton = new JButton("Просмотреть");
		watchButton.setActionCommand("Просмотреть");
		watchButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				watchButtonActionPerformed(evt);
			}
		});

		this.add(editButton);
		this.add(watchButton);
	}

	private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			WatchConfirmingDoc window = new WatchConfirmingDoc("edit");
			window.setEditable(true);
			window.setVisible(true);
		//	window.setValues(data);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void watchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			WatchConfirmingDoc window = new WatchConfirmingDoc("watch");
			window.setVisible(true);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public String getState() {
		return state;
	}
}
