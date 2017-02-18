package tab_achievements;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import backend.MessageProcessing;
import backend.ModelDBConnection;

public class EditWatchAchievmentPanel extends JPanel{
	private WatchConfirmingDoc window;

	private JButton editButton, watchButton;
	private String state;
	private String[] values;

	public EditWatchAchievmentPanel() {
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
			if(values != null) {
				window = new WatchConfirmingDoc("edit");
				window.setEditable(true);
				window.setVisible(true);
				window.setValues(values);
			}
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void watchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if(values != null) {
				window = new WatchConfirmingDoc("watch");
				window.setValues(values);
				window.setVisible(true);
			}
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public String getState() {
		return state;
	}
	
	public void setValues(String[] values) {
		this.values = values;
	}
}
