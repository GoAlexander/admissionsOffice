package tab_education;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

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
			ArrayList<Integer> mistakesIndicesHighEdu = highEducPanel.checkData(highEducPanel.getValues());
			ArrayList<Integer> mistakesIndicesPostGradEdu = afterDiplEducPanel.checkData(afterDiplEducPanel.getValues());

			if (mistakesIndicesHighEdu.contains(5) || mistakesIndicesPostGradEdu.contains(5) || mistakesIndicesHighEdu.contains(6) || mistakesIndicesPostGradEdu.contains(6))
				MessageProcessing.displayErrorMessage(null, 9);
			else {
				if (mistakesIndicesHighEdu.isEmpty())
					ModelDBConnection.updateAbiturientEducationByID("AbiturientHigherEducation", highEducPanel.getValues());
				else if (mistakesIndicesHighEdu.contains(-1))
					ModelDBConnection.deleteElementInTableById("AbiturientHigherEducation", highEducPanel.getValues()[0]);

				if (mistakesIndicesPostGradEdu.isEmpty())
					ModelDBConnection.updateAbiturientEducationByID("AbiturientPostgraduateEducation", afterDiplEducPanel.getValues());
				else if (mistakesIndicesPostGradEdu.contains(-1))
					ModelDBConnection.deleteElementInTableById("AbiturientPostgraduateEducation", afterDiplEducPanel.getValues()[0]);

				this.setEditable(false);
				MessageProcessing.displaySuccessMessage(this, 8);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public void setEditable(boolean state) {
		highEducPanel.setEditable(state);
		afterDiplEducPanel.setEditable(state);
		saveEducationButton.setEnabled(state);
		
		editEducationButton.setEnabled(!state);
		if (highEducPanel.getCurrentAbit() == null || highEducPanel.getCurrentAbit().equals("0"))
			editEducationButton.setEnabled(state);
	}

	public void setValues(String[] abiturientHigherEducationData, String[] abiturientPostgraduateEducationData){
		highEducPanel.setValues(abiturientHigherEducationData);
		afterDiplEducPanel.setValues(abiturientPostgraduateEducationData);
		this.setEditable(false);
	}

	public String[] getValues(int educationType) {
		switch (educationType) {
		case 0:
			return highEducPanel.getValues();
		case 1:
			return afterDiplEducPanel.getValues();
		}
		return null;
	}
}
