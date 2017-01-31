package tab_achievements;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import backend.MessageProcessing;

public class EditWatchAchievmentPanel extends JPanel{
	JTable table;

	private JButton editButton, watchButton;
	private String state;

	public EditWatchAchievmentPanel(JTable table) {
		this.table = table;

		this.setLayout(new FlowLayout());

		editButton = new JButton("Редактировать");
		editButton.setActionCommand("Редактировать");
		editButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editButtonActionPerformed(evt);
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
			WatchConfirmingDoc window = new WatchConfirmingDoc();
			window.setEditable(true);
			window.setVisible(true);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void watchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			WatchConfirmingDoc window = new WatchConfirmingDoc();
			window.setVisible(true);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public String getState() {
		return state;
	}
}
