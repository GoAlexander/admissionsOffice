package tab_education;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import backend.MessageProcessing;
import backend.ModelDBConnection;

public class EducationPanel extends JPanel{
	private SimpleEducationPanel highEducPanel, afterDiplEducPanel;
	private JButton editEducationButton, saveEducationButton;

	public EducationPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		highEducPanel = new SimpleEducationPanel("Высшее образование");
		this.add(highEducPanel);

		afterDiplEducPanel = new SimpleEducationPanel("Последипломное образование");
		this.add(afterDiplEducPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		editEducationButton = new JButton("Редактировать");
		editEducationButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editEducationButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(editEducationButton);

		saveEducationButton = new JButton("Сохранить");
		saveEducationButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveEducationButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(saveEducationButton);

		this.add(buttonPanel);

		this.setEditable(false);
	}

	private void editEducationButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			this.setEditable(true);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void saveEducationButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			this.setEditable(false);
			ModelDBConnection.updateAbiturientEducationByID("AbiturientHigherEducation", highEducPanel.getValues());
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public void setEditable(boolean state) {
		highEducPanel.setEditable(state);
		afterDiplEducPanel.setEditable(state);
		saveEducationButton.setEnabled(state);
		
		editEducationButton.setEnabled(!state);
	}
	
	public SimpleEducationPanel getHigherPanel(){
		return highEducPanel;
	}
}
