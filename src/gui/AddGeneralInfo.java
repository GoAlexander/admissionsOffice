package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import backend.MessageProcessing;
import backend.ModelDBConnection;
import general_classes.GUITableModel;

public class AddGeneralInfo extends JFrame {
	private GUITableModel currentTM;

	private JPanel mainPanel, GIPanel, panelID, dateRecDocPanel, panelSurname, panelName, panelPatronymic, panelSex, panelDB,
			panelNationality;
	private JButton applyButton;
	private JTextField textID, textDateRecDoc;
	private JComboBox comboSexList, comboNationality;
	private GridBagConstraints gbc;

	private Dimension dimText = new Dimension(107, 25);
	private Dimension dimPanel = new Dimension(300, 40);

	private String[] arrSex, arrNationality;

	private JDateChooser calendar;

	public AddGeneralInfo(GUITableModel currentTM) {
		arrSex = ModelDBConnection.getNamesFromTableOrderedById("Gender");
		arrNationality = ModelDBConnection.getNamesFromTableOrderedById("Nationality");

		this.currentTM = currentTM;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		setLocationRelativeTo(null);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel);

		JPanel GIPanelMain = new JPanel();
		GIPanelMain.setLayout(new BoxLayout(GIPanelMain, BoxLayout.Y_AXIS));
		GIPanelMain.setBorder(
				new TitledBorder(null, "Основная информация", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		JLabel idLabel = new JLabel("№:  ");
		panelID.add(idLabel);
		textID = new JTextField();
		textID.setPreferredSize(new Dimension(60, 25));
		panelID.add(textID);
		GIPanel.add(panelID, gbc);

		dateRecDocPanel = new JPanel();
		dateRecDocPanel.setLayout(new FlowLayout());
		dateRecDocPanel.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		dateRecDocPanel.setMaximumSize(dimPanel);
		JLabel dateRecDocLabel = new JLabel("Дата приема документов ");
		dateRecDocPanel.add(dateRecDocLabel);
		textDateRecDoc = new JTextField();
		textDateRecDoc.setPreferredSize(dimText);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		textDateRecDoc.setText(sdf.format(new Date()));
		dateRecDocPanel.add(textDateRecDoc);
		gbc.gridx = 1;
		GIPanel.add(dateRecDocPanel, gbc);

		panelSurname = new JPanel();
		panelSurname = createFIOPanel("Фамилия:     ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		GIPanel.add(panelSurname, gbc);

		panelName = new JPanel();
		panelName = createFIOPanel("Имя:                ");
		gbc.gridy = 2;
		GIPanel.add(panelName, gbc);

		panelPatronymic = new JPanel();
		panelPatronymic = createFIOPanel("Отчество:      ");
		gbc.gridy = 3;
		GIPanel.add(panelPatronymic, gbc);

		panelSex = new JPanel();
		panelSex.setLayout(new FlowLayout());
		panelSex.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelSex.setMaximumSize(dimPanel);
		JLabel sexLabel = new JLabel("Пол:                        ");
		panelSex.add(sexLabel);
		comboSexList = new JComboBox(arrSex);
		comboSexList.setSelectedIndex(-1);
		panelSex.add(comboSexList);
		gbc.gridx = 1;
		gbc.gridy = 1;
		GIPanel.add(panelSex, gbc);

		panelDB = new JPanel();
		panelDB.setLayout(new FlowLayout());
		panelDB.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelDB.setMaximumSize(dimPanel);
		JLabel dbLabel = new JLabel("Дата Рождения:  ");
		panelDB.add(dbLabel);
		calendar = new JDateChooser("dd.MM.yyyy", "##.##.####", '_');
		calendar.setFont(new Font("Dialog", Font.PLAIN, 11));
		panelDB.add(calendar);
		gbc.gridy = 2;
		GIPanel.add(panelDB, gbc);

		panelNationality = new JPanel();
		panelNationality.setLayout(new FlowLayout());
		panelNationality.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		panelNationality.setMaximumSize(dimPanel);
		JLabel nationalityLabel = new JLabel("Гражданство:      ");
		panelNationality.add(nationalityLabel);
		comboNationality = new JComboBox(arrNationality);
		comboNationality.setSelectedIndex(-1);
		comboNationality.setPreferredSize(dimText);
		panelNationality.add(comboNationality);
		gbc.gridy = 3;
		GIPanel.add(panelNationality, gbc);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		applyButton = new JButton("Подтвердить");
		applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	applyButtonActionPerformed(evt);
            }
        });

		btnPanel.add(applyButton);
		GIPanelMain.add(btnPanel);

		setPreferredSize(new Dimension(600, 300));
		pack();

	}

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
    	try {
    		//Запись в БД
    		String[] abitBaseInfo = new String[8];
    		abitBaseInfo[0] = textID.getText();
    		abitBaseInfo[1] = ((JTextField)panelSurname.getComponent(1)).getText();
    		abitBaseInfo[2] = ((JTextField)panelName.getComponent(1)).getText();
    		abitBaseInfo[3] = ((JTextField)panelPatronymic.getComponent(1)).getText();
    		abitBaseInfo[4] = new SimpleDateFormat("dd.MM.yyyy").format(calendar.getDate()).toString();
    		abitBaseInfo[5] = String.valueOf(comboSexList.getSelectedIndex()+1);
    		abitBaseInfo[6] = String.valueOf(comboNationality.getSelectedIndex()+1);
    		abitBaseInfo[7] = textDateRecDoc.getText();

    		ModelDBConnection.insertAbiturient(abitBaseInfo);

    		//Обновление таблицы главного фрейма
    		String[] abit_ID_FIO = {abitBaseInfo[0], abitBaseInfo[1], abitBaseInfo[2], abitBaseInfo[3]};
    		this.currentTM.addRow(abit_ID_FIO);

    		this.setVisible(false);
    		MessageProcessing.displaySuccessMessage(this, 1);
    	} catch (Exception e) {
    		MessageProcessing.displayErrorMessage(this, e);
		}
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
					AddGeneralInfo window = new AddGeneralInfo(new GUITableModel());
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
