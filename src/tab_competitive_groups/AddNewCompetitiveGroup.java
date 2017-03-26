﻿package tab_competitive_groups;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import backend.MessageProcessing;
import backend.ModelDBConnection;

public class AddNewCompetitiveGroup extends JFrame{
	private JPanel parentPanel;
	private String currentAbit;

	private JPanel mainPanel, infoPanel, directPanel, specialityPanel, departmentPanel, orgPanel, educFormPanel, competGroupPanel, provisionPanel, panelDateProvide;
	private JButton saveCompetitiveGroupButton, editCompetitiveGroupButton, deleteCompetitiveGroupButton;
	private JComboBox comboCompetGroupType, comboStandardType;
	JCheckBox originalBox;
	private JDateChooser calendar;

	private Dimension dimRigidArea = new Dimension(20, 0);

	private String[] directType, specialityType, departmentType, orgType, educFormType, competGroupType, standardType;

	public AddNewCompetitiveGroup(JPanel parentPanel){
		this.parentPanel = parentPanel;
		directType = ModelDBConnection.getNamesFromTableOrderedById("Course");
		specialityType = ModelDBConnection.getNamesFromTableOrderedById("Speciality");
		departmentType = ModelDBConnection.getNamesFromTableOrderedById("Chair");
		orgType = ModelDBConnection.getNamesFromTableOrderedById("TargetOrganisation");
		educFormType = ModelDBConnection.getNamesFromTableOrderedById("EducationForm");
		competGroupType = ModelDBConnection.getNamesFromTableOrderedById("CompetitiveGroup");
		standardType = ModelDBConnection.getNamesFromTableOrderedById("EducationStandard");

		setTitle("Добавление новой конкурсной группы");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		add(mainPanel);

		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(infoPanel);

		directPanel = new JPanel();
		directPanel = createCheckBoxPanel("Направление", directType);
		infoPanel.add(directPanel);

		specialityPanel = new JPanel();
		specialityPanel = createCheckBoxPanel("Специальность", specialityType);
		infoPanel.add(specialityPanel);
	
		departmentPanel = new JPanel();
		departmentPanel = createCheckBoxPanel("Кафедра", departmentType);
		infoPanel.add(departmentPanel);

		competGroupPanel = new JPanel();
		competGroupPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel competGroupLabel = new JLabel("Конкурсная группа");
		competGroupPanel.add(competGroupLabel);
		comboCompetGroupType = new JComboBox(competGroupType);
		comboCompetGroupType.setSelectedIndex(-1);
		competGroupPanel.add(comboCompetGroupType);
		comboCompetGroupType.setPreferredSize(new Dimension(280,30));
		competGroupPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel standardLabel = new JLabel("Стандарт");
		competGroupPanel.add(standardLabel);
		comboStandardType = new JComboBox(standardType);
		competGroupPanel.add(comboStandardType);
		comboStandardType.setSelectedIndex(-1);
		infoPanel.add(competGroupPanel);

		orgPanel = new JPanel();
		orgPanel = createCheckBoxPanel("Организация", orgType);
		infoPanel.add(orgPanel);

		educFormPanel = new JPanel();
		educFormPanel = createCheckBoxPanel("Форма обучения", educFormType);
		((JComboBox)educFormPanel.getComponent(1)).setSelectedItem("очная");
		//educFormPanel.setVisible(false); // Временно
		infoPanel.add(educFormPanel);

		provisionPanel = new JPanel();
		provisionPanel.setBorder(new TitledBorder(null, "Сведения о предоставлении оригиналов документов", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		provisionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelDateProvide = new JPanel();
		panelDateProvide.setLayout(new FlowLayout());

		panelDateProvide.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		JLabel dateProvideLabel = new JLabel("Дата предоставления:");
		panelDateProvide.add(dateProvideLabel);

		calendar = new JDateChooser("dd.MM.yyyy", "##.##.####", '_');
		calendar.setEnabled(false);
		calendar.setFont(new Font("Dialog", Font.PLAIN, 11));
		panelDateProvide.add(calendar);
		panelDateProvide.add(Box.createRigidArea(dimRigidArea));
		panelDateProvide.setPreferredSize(new Dimension(350, 30));
		provisionPanel.add(panelDateProvide);
		originalBox = new JCheckBox("Предоставил оригинал");
		originalBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (originalBox.isSelected()) {
					calendar.setEnabled(true);
					calendar.setDate(new Date());
				} else {
					calendar.setDate(null);
					calendar.setEnabled(false);
				}
			}
		});
		provisionPanel.add(originalBox);
		infoPanel.add(provisionPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));	
		saveCompetitiveGroupButton = new JButton("Сохранить");
		saveCompetitiveGroupButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveCompetitiveGroupButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(saveCompetitiveGroupButton);
		mainPanel.add(buttonPanel);

		setPreferredSize(new Dimension(580, 380));
		pack();
	}

	private JPanel createCheckBoxPanel(String name, String[] arrCheckBox){
		JPanel checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel checkboxLabel = new JLabel(name);
		checkboxPanel.add(checkboxLabel);

		Dimension comboBoxPrefferedSize = null;
		switch (name) {
		case "Направление":
			comboBoxPrefferedSize = new Dimension(462, 30);
			break;
		case "Специальность":
			comboBoxPrefferedSize = new Dimension(450, 30);
			break;
		case "Кафедра":
			comboBoxPrefferedSize = new Dimension(488, 30);
			break;
		case "Конкурсная группа":
			comboBoxPrefferedSize = new Dimension(150, 30);
			break;
		case "Стандарт":
			comboBoxPrefferedSize = new Dimension(100, 30);
			break;
		case "Организация":
			comboBoxPrefferedSize = new Dimension(466, 30);
			break;
		}

		JComboBox comboType = new JComboBox(arrCheckBox);
		comboType.setPreferredSize(comboBoxPrefferedSize);
		checkboxPanel.add(comboType);
		comboType.setSelectedIndex(-1);

		return checkboxPanel;
	}

	public JPanel getAddNewCompetGroup () {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));	

		editCompetitiveGroupButton = new JButton("Редактировать");
		editCompetitiveGroupButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editCompetitiveGroupButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(editCompetitiveGroupButton);

		deleteCompetitiveGroupButton = new JButton("Удалить");
		deleteCompetitiveGroupButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteCompetitiveGroupButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(deleteCompetitiveGroupButton);
		infoPanel.add(buttonPanel);
		return infoPanel;
	}

	private void saveCompetitiveGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			String[] newCompetitiveGroup = getValues(false);

			if (newCompetitiveGroup[1].equals("0"))
				MessageProcessing.displayErrorMessage(null, 24);
			else if (newCompetitiveGroup[2].equals("0"))
				MessageProcessing.displayErrorMessage(null, 25);
			else if (newCompetitiveGroup[3].equals("0"))
				MessageProcessing.displayErrorMessage(null, 26);
			else if (newCompetitiveGroup[4].equals("0"))
				MessageProcessing.displayErrorMessage(null, 27);
			else if (newCompetitiveGroup[5].equals("0"))
				MessageProcessing.displayErrorMessage(null, 28);
			else if (newCompetitiveGroup[7].equals("0"))
				MessageProcessing.displayErrorMessage(null, 29);
			else {
				boolean isDuplicated = false;

				String[] newCompetitiveGroupWithDetails = getValues(true);
				String[][] allCompetitiveGroups = ((CompetitiveGroupsPanel)parentPanel).getAllCompetitiveGroups();

				for (int i = 0; i < allCompetitiveGroups.length; i++)
					if (allCompetitiveGroups[i][0].equals(newCompetitiveGroupWithDetails[1]) &&
							allCompetitiveGroups[i][1].equals(newCompetitiveGroupWithDetails[2]) &&
							allCompetitiveGroups[i][3].equals(newCompetitiveGroupWithDetails[5])) {
						isDuplicated = true;
						break;
					}

				if (isDuplicated)
					MessageProcessing.displayErrorMessage(null, 30);
				else {
					ModelDBConnection.updateAbiturientCompetitiveGroupByID(newCompetitiveGroup);
					this.setVisible(false);
					ModelDBConnection.updateCompetitiveBallsByID(currentAbit);
					((CompetitiveGroupsPanel)parentPanel).setValues(currentAbit);
					MessageProcessing.displaySuccessMessage(null, 11);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void editCompetitiveGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			if (editCompetitiveGroupButton.getText().equals("Редактировать")) {
				calendar.setEnabled(originalBox.isSelected() ? true : false);
				originalBox.setEnabled(true);
				deleteCompetitiveGroupButton.setEnabled(false);
				editCompetitiveGroupButton.setText("    Сохранить     ");
			} else {
				if (((CompetitiveGroupsPanel)parentPanel).getCountOfSelectedOriginalBox() > 1) {
					MessageProcessing.displayErrorMessage(this, 42);
				} else {
					ModelDBConnection.updateAbiturientCompetitiveGroupByID(getValues(false));
					ModelDBConnection.updateCompetitiveBallsByID(currentAbit);
					calendar.setEnabled(false);
					originalBox.setEnabled(false);
					deleteCompetitiveGroupButton.setEnabled(true);
					editCompetitiveGroupButton.setText("Редактировать");
					MessageProcessing.displaySuccessMessage(null, 13);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void deleteCompetitiveGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			ModelDBConnection.deleteAbiturientCompetitiveGroupByID(currentAbit, getValues(false));
			((CompetitiveGroupsPanel)parentPanel).setValues(currentAbit);
			MessageProcessing.displaySuccessMessage(null, 12);
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public void setValues(String[] values) {
		try {
			currentAbit = values[0];
			if(values.length > 1) {
				((JComboBox)directPanel.getComponent(1)).setSelectedIndex(Integer.valueOf(values[1]) - 1);
				((JComboBox)specialityPanel.getComponent(1)).setSelectedIndex(Integer.valueOf(values[2]) - 1);
				((JComboBox)educFormPanel.getComponent(1)).setSelectedIndex(Integer.valueOf(values[3]) - 1);
				((JComboBox)departmentPanel.getComponent(1)).setSelectedIndex(Integer.valueOf(values[4]) - 1);
				comboCompetGroupType.setSelectedIndex(Integer.valueOf(values[5]) - 1);
				((JComboBox)orgPanel.getComponent(1)).setSelectedIndex(values[6] != null ? (Integer.valueOf(values[6]) - 1) : -1);
				comboStandardType.setSelectedIndex(Integer.valueOf(values[7]) - 1);

				if (values[10] != null) {
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date date = format.parse(values[10]);
					calendar.setDate(date);
				}

				originalBox.setSelected(values[10] != null ? true : false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public String[] getValues(boolean forDocs) {
		String[] values = new String[12];
		values[0] = currentAbit;
		values[1] = forDocs ? ((JComboBox)directPanel.getComponent(1)).getSelectedItem().toString() : String.valueOf(((JComboBox)directPanel.getComponent(1)).getSelectedIndex() + 1);
		values[2] = forDocs ? ((JComboBox)specialityPanel.getComponent(1)).getSelectedItem().toString() : String.valueOf(((JComboBox)specialityPanel.getComponent(1)).getSelectedIndex() + 1);
		values[3] = forDocs ? ((JComboBox)educFormPanel.getComponent(1)).getSelectedItem().toString() : String.valueOf(((JComboBox)educFormPanel.getComponent(1)).getSelectedIndex() + 1);
		values[4] = forDocs ? ((JComboBox)departmentPanel.getComponent(1)).getSelectedItem().toString() : String.valueOf(((JComboBox)departmentPanel.getComponent(1)).getSelectedIndex() + 1);
		values[5] = forDocs ? comboCompetGroupType.getSelectedItem().toString() : String.valueOf(comboCompetGroupType.getSelectedIndex() + 1);
		values[6] = forDocs ? (((JComboBox)orgPanel.getComponent(1)).getSelectedItem() != null ? ((JComboBox)orgPanel.getComponent(1)).getSelectedItem().toString() : null) : (((JComboBox)orgPanel.getComponent(1)).getSelectedIndex() > -1 ? String.valueOf(((JComboBox)orgPanel.getComponent(1)).getSelectedIndex() + 1) : null);
		values[7] = forDocs ? comboStandardType.getSelectedItem().toString() : String.valueOf(comboStandardType.getSelectedIndex() + 1);
		values[8] = null;
		values[9] = null;
		values[10] = originalBox.isSelected() ? new SimpleDateFormat("dd.MM.yyyy").format(calendar.getDate()).toString() : null;
		values[11] = null;

		return values;
	}

	public void setEditable(boolean state) {
		((JComboBox)directPanel.getComponent(1)).setEnabled(state);
		((JComboBox)directPanel.getComponent(1)).setEditable(!state);
		((JComboBox)specialityPanel.getComponent(1)).setEnabled(state);
		((JComboBox)specialityPanel.getComponent(1)).setEditable(!state);
		((JComboBox)educFormPanel.getComponent(1)).setEnabled(state);
		((JComboBox)educFormPanel.getComponent(1)).setEditable(!state);
		((JComboBox)departmentPanel.getComponent(1)).setEnabled(state);
		((JComboBox)departmentPanel.getComponent(1)).setEditable(!state);
		comboCompetGroupType.setEnabled(state);
		comboCompetGroupType.setEditable(!state);
		((JComboBox)orgPanel.getComponent(1)).setEnabled(state);
		((JComboBox)orgPanel.getComponent(1)).setEditable(!state);
		comboStandardType.setEnabled(state);
		comboStandardType.setEditable(!state);
		calendar.setEnabled(state);
		originalBox.setEnabled(state);
	}
}
