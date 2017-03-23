package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import backend.MessageProcessing;
import backend.ModelDBConnection;
import general_classes.GUITableModel;

public class AddGeneralInfo extends JFrame {
	private JTable dataTable;

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

	public AddGeneralInfo(JTable dataTable) {
		arrSex = ModelDBConnection.getNamesFromTableOrderedById("Gender");
		arrNationality = ModelDBConnection.getNamesFromTableOrderedById("Nationality");

		this.dataTable = dataTable;

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
		textID.setText(String.valueOf(ModelDBConnection.getCount("Abiturient") + 1));
		panelID.add(textID);
		GIPanel.add(panelID, gbc);

		dateRecDocPanel = new JPanel();
		dateRecDocPanel.setLayout(new FlowLayout());
		dateRecDocPanel.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		dateRecDocPanel.setMaximumSize(dimPanel);
		JLabel dateRecDocLabel = new JLabel("Дата приема документов ");
		dateRecDocPanel.add(dateRecDocLabel);
		try {
			MaskFormatter mf;
			mf = new MaskFormatter("##.##.####");
			mf.setPlaceholderCharacter('_');
			textDateRecDoc = new JFormattedTextField(mf);
		} catch (ParseException e1) {
			textDateRecDoc = new JTextField();
		}
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
    		abitBaseInfo[0] = (!textID.getText().equals("") ? textID.getText() : null);
    		abitBaseInfo[1] = (!((JTextField)panelSurname.getComponent(1)).getText().equals("") ? ((JTextField)panelSurname.getComponent(1)).getText() : null);
    		abitBaseInfo[2] = (!((JTextField)panelName.getComponent(1)).getText().equals("") ? ((JTextField)panelName.getComponent(1)).getText() : null);
    		abitBaseInfo[3] = (!((JTextField)panelPatronymic.getComponent(1)).getText().equals("") ? ((JTextField)panelPatronymic.getComponent(1)).getText() : null);
    		abitBaseInfo[4] = (calendar.getDate() != null ? new SimpleDateFormat("dd.MM.yyyy").format(calendar.getDate()).toString() : null);
    		abitBaseInfo[5] = String.valueOf(comboSexList.getSelectedIndex()+1);
    		abitBaseInfo[6] = String.valueOf(comboNationality.getSelectedIndex()+1);
    		abitBaseInfo[7] = (!textDateRecDoc.getText().equals("") ? textDateRecDoc.getText() : null);

    		ArrayList<Integer> mistakesIndices = checkData(abitBaseInfo);
			if (abitBaseInfo[0] == null) {
				MessageProcessing.displayErrorMessage(null, 18);
			} else if (abitBaseInfo[1] == null) {
				MessageProcessing.displayErrorMessage(null, 19);
			} else if (abitBaseInfo[2] == null) {
				MessageProcessing.displayErrorMessage(null, 20);
			} else if (abitBaseInfo[4] == null) {
				MessageProcessing.displayErrorMessage(null, 21);
			} else if (abitBaseInfo[5].equals("0")) {
				abitBaseInfo[5] = null;
				MessageProcessing.displayErrorMessage(null, 16);
			} else if (abitBaseInfo[6].equals("0")) {
				abitBaseInfo[6] = null;
				MessageProcessing.displayErrorMessage(null, 17);
			} else {
				if (mistakesIndices.contains(0))
					textID.setForeground(Color.RED);
				else
					textID.setForeground(Color.BLACK);
				if (mistakesIndices.contains(1))
					((JTextField)panelSurname.getComponent(1)).setForeground(Color.RED);
				else
					((JTextField)panelSurname.getComponent(1)).setForeground(Color.BLACK);
				if (mistakesIndices.contains(2))
					((JTextField)panelName.getComponent(1)).setForeground(Color.RED);
				else
					((JTextField)panelName.getComponent(1)).setForeground(Color.BLACK);
				if (mistakesIndices.contains(3))
					((JTextField)panelPatronymic.getComponent(1)).setForeground(Color.RED);
				else
					((JTextField)panelPatronymic.getComponent(1)).setForeground(Color.BLACK);
				
				if (mistakesIndices.isEmpty()) {
					ModelDBConnection.insertAbiturient(abitBaseInfo);

					//Обновление таблицы главного фрейма
					String[] abit_ID_FIO = {abitBaseInfo[0], abitBaseInfo[1], abitBaseInfo[2], abitBaseInfo[3]};
					((GUITableModel)(dataTable.getModel())).addRow(abit_ID_FIO);

					this.setVisible(false);
					MessageProcessing.displaySuccessMessage(this, 1);
					dataTable.setRowSelectionInterval(dataTable.getRowCount() - 1, dataTable.getRowCount() - 1);
				} else {
					MessageProcessing.displayErrorMessage(null, 9);
				}
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    		if (e.getMessage().toString().indexOf("PRIMARY KEY") > 0)
    			MessageProcessing.displayErrorMessage(this, 22);
    		else
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

	private ArrayList<Integer> checkData(String[] values) {
		ArrayList<Integer> mistakesIndices = new ArrayList<Integer>();
		if (values[0] != null && !values[0].matches("^[0-9]+$"))
			mistakesIndices.add(0);
		if (values[1] != null && !values[1].matches("^[а-яА-Я]+[ -]?[а-яА-Я]*$"))
			mistakesIndices.add(1);
		if (values[2] != null && !values[2].matches("^[а-яА-Я]+[ -]?[а-яА-Я]*$"))
			mistakesIndices.add(2);
		if (values[3] != null && !values[3].matches("^[а-яА-Я]+[ -]?[а-яА-Я]*$"))
			mistakesIndices.add(3);

		return mistakesIndices;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Ordinator", "user", "password");
					ModelDBConnection.initConnection();
					AddGeneralInfo window = new AddGeneralInfo(new JTable());
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
