package tab_passport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import backend.MessageProcessing;
import backend.ModelDBConnection;

public class PassportPanel extends JPanel{
	private String currentAbit;

	private Dimension	dimStartRigidArea = new Dimension(50, 0), 
						dimTextDigitInfo = new Dimension(139, 25),
						dimRigidArea = new Dimension(10, 0);

	private JComboBox comboDocType;
	private JTextField textSeria, textNum, textDate, textplaceBirth;
	private JTextArea textIssuedBy;
	private JButton editPassportButton, savePassportButton;

	public PassportPanel() {
		String[] arrDocType = ModelDBConnection.getNamesFromTableOrderedById("PassportType");

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel docTypePanel = new JPanel();
		docTypePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		docTypePanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel docTypeLabel = new JLabel("Тип документа");
		comboDocType = new JComboBox(arrDocType);
		docTypePanel.add(docTypeLabel);
		docTypePanel.add(comboDocType);
		this.add(docTypePanel);

		JPanel passportDataPanel = new JPanel();
		passportDataPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		passportDataPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel seriaLabel = new JLabel("Серия ");
		passportDataPanel.add(seriaLabel);
		textSeria = new JTextField();
		textSeria.setPreferredSize(dimTextDigitInfo);
		textSeria.setBackground(Color.WHITE);
		passportDataPanel.add(textSeria);

		passportDataPanel.add(Box.createRigidArea(dimRigidArea));

		JLabel numLabel = new JLabel("Номер ");
		passportDataPanel.add(numLabel);
		textNum = new JTextField();
		textNum.setPreferredSize(dimTextDigitInfo);
		textNum.setBackground(Color.WHITE);
		passportDataPanel.add(textNum);

		passportDataPanel.add(Box.createRigidArea(dimRigidArea));

		JLabel dateLabel = new JLabel("Дата выдачи ");
		passportDataPanel.add(dateLabel);
		textDate = new JTextField();
		textDate.setPreferredSize(dimTextDigitInfo);
		textDate.setBackground(Color.WHITE);
		passportDataPanel.add(textDate);
		this.add(passportDataPanel);

		JPanel issuedByPanel = new JPanel();
		issuedByPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		issuedByPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel issuedByLabel = new JLabel("Кем выдан");
		issuedByPanel.add(issuedByLabel);
		textIssuedBy = new JTextArea(2, 51);
		textIssuedBy.setLineWrap(true);
		JScrollPane paneIssuedBy = new JScrollPane(textIssuedBy);
		textIssuedBy.setBackground(Color.WHITE);
		issuedByPanel.add(paneIssuedBy);
		this.add(issuedByPanel);

		JPanel placeBirthPanel = new JPanel();
		placeBirthPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		placeBirthPanel.add(Box.createRigidArea(dimStartRigidArea));
		JLabel placeBirthLabel = new JLabel("Место рождения");
		placeBirthPanel.add(placeBirthLabel);
		textplaceBirth = new JTextField();
		textplaceBirth.setPreferredSize(new Dimension(532, 25));
		textplaceBirth.setBackground(Color.WHITE);
		placeBirthPanel.add(textplaceBirth);
		this.add(placeBirthPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		editPassportButton = new JButton("Редактировать");
		editPassportButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editPassportButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(editPassportButton);

		savePassportButton = new JButton("Сохранить");
		savePassportButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				savePassportButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(savePassportButton);

		this.add(buttonPanel);

		this.setEditable(false);
	}

	private void editPassportButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			this.setEditable(true);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void savePassportButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			ModelDBConnection.updateAbiturientPassportByID(currentAbit, getValues());
			this.setEditable(false);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public void setValues(String[] values) {
		currentAbit = values[0];
		comboDocType.setSelectedIndex(values[1].equals("") ? -1 : Integer.valueOf(values[1]) - 1);
		textSeria.setText(values[2]);
		textNum.setText(values[3]);
		textIssuedBy.setText(values[4]);
		textDate.setText(values[5]);
		textplaceBirth.setText(values[6]);
	}

	public String[] getValues() {
		String[] values = new String[6];
		values[0] = String.valueOf(comboDocType.getSelectedIndex() + 1);
		values[1] = textSeria.getText();
		values[2] = textNum.getText();
		values[3] = textIssuedBy.getText();
		values[4] = textDate.getText();
		values[5] = textplaceBirth.getText();

		return values;
	}

	public void setEditable(boolean state) {
		comboDocType.setEnabled(state);
		textSeria.setEditable(state);
		textNum.setEditable(state);
		textDate.setEditable(state);
		textIssuedBy.setEditable(state);
		textplaceBirth.setEditable(state);
		savePassportButton.setEnabled(state);
		
		editPassportButton.setEnabled(!state);
	}
}
