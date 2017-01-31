package tab_achievements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import backend.MessageProcessing;

public class WatchConfirmingDoc extends JFrame {
	String currentAbit, currentAchievmentId;
	
	private JPanel mainPanel;
	private JButton editConfirmingDocButton;
	private JTextField nameDocText, seriaText, numText, dateText;
	private JTextArea textIssuedBy;

	private Dimension	dimTextDigitInfo = new Dimension(400, 25),
						dimRigidArea = new Dimension(10, 0),
						dimText = new Dimension(107, 25);

	public WatchConfirmingDoc() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);

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
		dateText = new JTextField();
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

		editConfirmingDocButton = new JButton("Редактировать");
		editConfirmingDocButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editConfirmingDocButtonActionPerformed(evt);
			}
		});

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
			if(editConfirmingDocButton.getText().equals("Редактировать"))
				setEditable(true);
			else {
				//Some actions
			}
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public void setValues(String[] values) {
		currentAbit = values[0];
		currentAchievmentId = values[1];

		nameDocText.setText(values[2]);
		seriaText.setText(values[3]);
		numText.setText(values[4]);
		dateText.setText(values[5]);
		textIssuedBy.setText(values[6]);
	}

	public void setEditable(boolean state) {
		nameDocText.setEditable(state);
		seriaText.setEditable(state);
		numText.setEditable(state);
		dateText.setEditable(state);
		textIssuedBy.setEditable(state);

		if(state)
			editConfirmingDocButton.setText("Сохранить");
		else
			editConfirmingDocButton.setText("Редактировать");
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WatchConfirmingDoc window = new WatchConfirmingDoc();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
