package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

public class AddGeneralInfo extends JFrame {

	private JPanel mainPanel, GIPanel, panelID, dateRecDocPanel, panelSurname, panelName, panelPatronymic, panelSex, panelDB,
			panelNationality, panelInfoBackDoc, panelReturnReason, panelDateReturn;
	private JButton applyBtn;
	private JTextField textID, textDateReturn, textDateRecDoc;
	private JComboBox comboSexList, comboNationality, comboReturnReason;
	private JCheckBox checkBackDoc;

	private GridBagConstraints gbc;

	private Dimension dimTextDigitInfo = new Dimension(400, 25);
	private Dimension dimRigidArea = new Dimension(10, 0);
	private Dimension dimText = new Dimension(107, 25);
	private Dimension dimPanel = new Dimension(300, 40);

	private String[] arrSex = { "�������", "�������" };
	private String[] arrNationality = { "��", "�������", "��������", "���������" };
	private String[] arrReturnReason = { "1                               ", "2      ", "3      " };
	private String[] arrDocType = { "������� ��", "������� �������" };

	private JDateChooser calendar;

	public AddGeneralInfo() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		setLocationRelativeTo(null);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel);

		JPanel GIPanelMain = new JPanel();
		GIPanelMain.setLayout(new BoxLayout(GIPanelMain, BoxLayout.Y_AXIS));
		GIPanelMain.setBorder(
				new TitledBorder(null, "�������� ����������", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.add(GIPanelMain);

		GIPanel = new JPanel();
		GIPanel.setLayout(new GridBagLayout());
		GIPanelMain.add(GIPanel);

		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 0, 5, 40);

		panelID = new JPanel();
		panelID.setLayout(new FlowLayout());
		panelID.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelID.setMaximumSize(dimPanel);
		JLabel idLabel = new JLabel("�:  ");
		panelID.add(idLabel);
		textID = new JTextField();
		textID.setPreferredSize(new Dimension(60, 25));
		panelID.add(textID);
		GIPanel.add(panelID, gbc);
		
		dateRecDocPanel = new JPanel();
		dateRecDocPanel.setLayout(new FlowLayout());
		dateRecDocPanel.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		dateRecDocPanel.setMaximumSize(dimPanel);
		JLabel dateRecDocLabel = new JLabel("���� ������ ���������� ");
		dateRecDocPanel.add(dateRecDocLabel);
		textDateRecDoc = new JTextField();
		textDateRecDoc.setPreferredSize(dimText);
		dateRecDocPanel.add(textDateRecDoc);
		gbc.gridx = 1;
		GIPanel.add(dateRecDocPanel, gbc);

		panelSurname = new JPanel();
		panelSurname = createFIOPanel("�������:     ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		GIPanel.add(panelSurname, gbc);

		panelName = new JPanel();
		panelName = createFIOPanel("���:                ");
		gbc.gridy = 2;
		GIPanel.add(panelName, gbc);

		panelPatronymic = new JPanel();
		panelPatronymic = createFIOPanel("��������:      ");
		gbc.gridy = 3;
		GIPanel.add(panelPatronymic, gbc);

		panelSex = new JPanel();
		panelSex.setLayout(new FlowLayout());
		panelSex.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelSex.setMaximumSize(dimPanel);
		JLabel sexLabel = new JLabel("���:                        ");
		panelSex.add(sexLabel);
		comboSexList = new JComboBox(arrSex);
		comboSexList.setSelectedIndex(0);
		panelSex.add(comboSexList);
		gbc.gridx = 1;
		gbc.gridy = 1;
		GIPanel.add(panelSex, gbc);

		panelDB = new JPanel();
		panelDB.setLayout(new FlowLayout());
		panelDB.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelDB.setMaximumSize(dimPanel);
		JLabel dbLabel = new JLabel("���� ��������:  ");
		panelDB.add(dbLabel);
		calendar = new JDateChooser();
		calendar.setFont(new Font("Dialog", Font.PLAIN, 11));
		panelDB.add(calendar);
		gbc.gridy = 2;
		GIPanel.add(panelDB, gbc);

		panelNationality = new JPanel();
		panelNationality.setLayout(new FlowLayout());
		panelNationality.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelNationality.setMaximumSize(dimPanel);
		JLabel nationalityLabel = new JLabel("�����������:      ");
		panelNationality.add(nationalityLabel);
		comboNationality = new JComboBox(arrNationality);
		comboNationality.setSelectedIndex(0);
		comboNationality.setPreferredSize(dimText);
		panelNationality.add(comboNationality);
		gbc.gridy = 3;
		GIPanel.add(panelNationality, gbc);

		panelInfoBackDoc = new JPanel();
		panelInfoBackDoc.setBorder(new TitledBorder(null, "�������� � �������� ����������", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		GIPanelMain.add(panelInfoBackDoc);
		panelInfoBackDoc.setLayout(new GridBagLayout());

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.anchor = GridBagConstraints.WEST;
		gbc2.insets = new Insets(0, 0, 5, 65);

		panelReturnReason = new JPanel();
		panelReturnReason.setLayout(new FlowLayout());

		JLabel returnReasonlbl = new JLabel("������� ��������   ");
		panelReturnReason.add(returnReasonlbl);

		comboReturnReason = new JComboBox(arrReturnReason);
		comboNationality.setSelectedIndex(0);
		panelReturnReason.add(comboReturnReason);

		panelInfoBackDoc.add(panelReturnReason, gbc2);

		panelDateReturn = new JPanel();
		panelDateReturn.setLayout(new FlowLayout());

		panelDateReturn.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelDateReturn.setMaximumSize(dimPanel);
		JLabel dateReturnLabel = new JLabel("���� �������� ����������:     ");
		panelDateReturn.add(dateReturnLabel);
		textDateReturn = new JTextField();
		textDateReturn.setPreferredSize(dimText);
		panelDateReturn.add(textDateReturn);
		checkBackDoc = new JCheckBox("������ ���������");
		gbc2.gridy = 1;
		panelInfoBackDoc.add(panelDateReturn, gbc2);
		gbc2.gridx = 1;
		panelInfoBackDoc.add(checkBackDoc, gbc2);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		applyBtn = new JButton("�����������");
		btnPanel.add(applyBtn);
		GIPanelMain.add(btnPanel);

		setPreferredSize(new Dimension(600, 500));
		pack();

	}

	private JPanel createFIOPanel(String nameLabel) {
		JPanel panelFIO = new JPanel();
		panelFIO.setLayout(new FlowLayout());
		panelFIO.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelFIO.setMaximumSize(dimPanel);
		JLabel FIOLabel = new JLabel(nameLabel);
		panelFIO.add(FIOLabel);
		JTextField textFIO = new JTextField();
		textFIO.setPreferredSize(dimText);
		panelFIO.add(textFIO);

		return panelFIO;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddGeneralInfo window = new AddGeneralInfo();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
