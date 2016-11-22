package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WatchConfirmingDoc extends JFrame {
	
	private JPanel mainPanel;
	private JButton editBtn, saveBtn;
	private JTextField nameDocText, seriaText, numText, dateText;
	JTextArea textIssuedBy;
	
	private Dimension dimTextDigitInfo = new Dimension(400, 25);
	private Dimension dimRigidArea = new Dimension(10, 0);
	private Dimension dimText = new Dimension(107, 25);

	
	public  WatchConfirmingDoc() {
		
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

		saveBtn = new JButton("Сохранить");
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	
		buttonPanel.add(saveBtn);
		
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
			
		setPreferredSize(new Dimension(580, 300));
		pack();
		
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

	
	


