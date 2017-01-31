package tab_competitive_groups;

import java.awt.Dimension;
import java.awt.EventQueue;
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
	private String currentAbit;

	private JPanel mainPanel, infoPanel, directPanel, specialityPanel, departmentPanel, orgPanel, educFormPanel, competGroupPanel, provisionPanel, panelDateProvide;
	private JButton saveCompetitiveGroupButton, editCompetitiveGroupButton, deleteCompetitiveGroupButton;
	private JComboBox comboCompetGroupType, comboStandardType;
	JCheckBox originalBox;
	private JDateChooser calendar;

	private Dimension dimRigidArea = new Dimension(20, 0);

	private String[] directType, specialityType, departmentType, orgType, educFormType, competGroupType, standardType;

	public AddNewCompetitiveGroup(){
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
		competGroupPanel.add(comboCompetGroupType);
		comboCompetGroupType.setPreferredSize(new Dimension(280,30));
		competGroupPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel standardLabel = new JLabel("Стандарт");
		competGroupPanel.add(standardLabel);
		comboStandardType = new JComboBox(standardType);
		competGroupPanel.add(comboStandardType);
		infoPanel.add(competGroupPanel);

		orgPanel = new JPanel();
		orgPanel = createCheckBoxPanel("Организация", orgType);
		infoPanel.add(orgPanel);

		educFormPanel = new JPanel();
		educFormPanel = createCheckBoxPanel("Форма обучения", educFormType);
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

		calendar = new JDateChooser();
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
			//Some actions
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void editCompetitiveGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			//Some actions
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	private void deleteCompetitiveGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			//Some actions
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public void setValues(String[] values) {
		// !!!!!!!!!!!!!!!!!!!!!
		// Need test for indexes of components right values
		try {
			currentAbit = values[0];
			((JComboBox)directPanel.getComponent(1)).setSelectedIndex(Integer.valueOf(values[1]));
			((JComboBox)specialityPanel.getComponent(1)).setSelectedIndex(Integer.valueOf(values[2]));
			((JComboBox)departmentPanel.getComponent(1)).setSelectedIndex(Integer.valueOf(values[3]));
			comboCompetGroupType.setSelectedIndex(Integer.valueOf(values[4]));
			comboStandardType.setSelectedIndex(Integer.valueOf(values[5]));
			((JComboBox)orgPanel.getComponent(1)).setSelectedIndex(Integer.valueOf(values[6]));
			((JComboBox)educFormPanel.getComponent(1)).setSelectedIndex(Integer.valueOf(values[7]));

			DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			Date date = format.parse(values[8]);
			calendar.setDate(date);

			originalBox.setSelected(Boolean.valueOf(values[9]));
		} catch (Exception e) {
			MessageProcessing.displayErrorMessage(this, e);
		}
	}

	public static void main(String[] args) {
		ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Ordinator", "user", "password");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddNewCompetitiveGroup window = new AddNewCompetitiveGroup();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
