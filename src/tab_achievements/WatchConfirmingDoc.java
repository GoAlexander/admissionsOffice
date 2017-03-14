package tab_achievements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import backend.MessageProcessing;
import backend.ModelDBConnection;

public class WatchConfirmingDoc extends JFrame {
	String currentAbit, currentAchievmentId, score;

	private JPanel mainPanel;
	private JButton editConfirmingDocButton;
	private JTextField nameDocText, seriaText, numText, dateText;
	private JTextArea textIssuedBy;

	private Dimension dimTextDigitInfo = new Dimension(400, 25), dimRigidArea = new Dimension(10, 0),
			dimText = new Dimension(107, 25);

	private String nameButton;

	public WatchConfirmingDoc(String nameButton) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);

		this.nameButton = nameButton;

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel);

		JPanel nameDocPanel = new JPanel();
		nameDocPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		nameDocPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel nameDocLabel = new JLabel("Название документа");
		nameDocPanel.add(nameDocLabel);
		nameDocText = new JTextField();
		nameDocText.setPreferredSize(dimTextDigitInfo);
		nameDocPanel.add(nameDocText);
		mainPanel.add(nameDocPanel);

		JPanel docDataPanel = new JPanel();
		docDataPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		docDataPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel seriaLabel = new JLabel("Серия");
		docDataPanel.add(seriaLabel);
		seriaText = new JTextField();
		seriaText.setPreferredSize(dimText);
		docDataPanel.add(seriaText);
		docDataPanel.add(Box.createRigidArea(dimRigidArea));

		JLabel numLabel = new JLabel("Номер");
		docDataPanel.add(numLabel);
		numText = new JTextField();
		numText.setPreferredSize(dimText);
		docDataPanel.add(numText);
		docDataPanel.add(Box.createRigidArea(dimRigidArea));

		JLabel dateLabel = new JLabel("Дата выдачи");
		docDataPanel.add(dateLabel);
		try {
			MaskFormatter mf;
			mf = new MaskFormatter("##.##.####");
			mf.setPlaceholderCharacter('_');
			dateText = new JFormattedTextField(mf);
		} catch (ParseException e) {
			dateText = new JTextField();
		}
		dateText.setPreferredSize(dimText);
		docDataPanel.add(dateText);
		docDataPanel.add(Box.createRigidArea(dimRigidArea));

		mainPanel.add(docDataPanel);

		JPanel issuedByPanel = new JPanel();
		issuedByPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		issuedByPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel issuedByLabel = new JLabel("Кем выдан");
		issuedByPanel.add(issuedByLabel);
		textIssuedBy = new JTextArea(2, 41);
		JScrollPane paneIssuedBy = new JScrollPane(textIssuedBy);
		textIssuedBy.setLineWrap(true);
		issuedByPanel.add(paneIssuedBy);
		mainPanel.add(issuedByPanel);

		editConfirmingDocButton = new JButton("Сохранить");
		editConfirmingDocButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editConfirmingDocButtonActionPerformed(evt);
			}
		});

		if (nameButton.equals("watch")) {
			editConfirmingDocButton.setVisible(false);
		}

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		buttonPanel.add(editConfirmingDocButton);

		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

		setPreferredSize(new Dimension(580, 300));
		pack();

		setEditable(false);
	}

	private void editConfirmingDocButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			ModelDBConnection.updateAbiturientIndividualAchivementByID(getValues());
			this.setVisible(false);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public void setValues(String[] values) throws ParseException {
		currentAbit = values[0];
		currentAchievmentId = values[1];
		score = values[2];
		String[] data = ModelDBConnection.getElementFromTableByIDs("AbiturientIndividualAchievement", values);

		nameDocText.setText(data[3]);
		seriaText.setText(data[4]);
		numText.setText(data[5]);
		textIssuedBy.setText(data[6]);
		if(!data[7].equals("")) {
			SimpleDateFormat format = new SimpleDateFormat();
			format.applyPattern("yyyy-MM-dd");
			Date docDate= format.parse(data[7]);
			format.applyPattern("dd.MM.yyyy");
			dateText.setText(format.format(docDate));
		} else
			dateText.setText(null);
	}

	public String[] getValues() {
		String[] data = new String[8];

		data[0] = currentAbit;
		data[1] = currentAchievmentId;
		data[2] = score;
		data[3] = nameDocText.getText();
		data[4] = seriaText.getText();
		data[5] = numText.getText();
		data[6] = textIssuedBy.getText();
		data[7] = dateText.getText().equals("__.__.____") ? null : dateText.getText();

		return data;
	}

	public void setEditable(boolean state) {
		nameDocText.setEditable(state);
		seriaText.setEditable(state);
		numText.setEditable(state);
		dateText.setEditable(state);
		textIssuedBy.setEditable(state);

	}
}
