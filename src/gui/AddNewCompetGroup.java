package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

public class AddNewCompetGroup extends JFrame{

	private JPanel mainPanel, directPanel, specialityPanel, departmentPanel, orgPanel, educFormPanel, competGroupPanel, provisionPanel;
	private JButton saveBtn;
	private JDateChooser calendar;
	
	private Dimension dimRigidArea = new Dimension(20, 0);
	
	private String[] directType = {"ddddddddddddddd", "2", "3"};
	private String[] specialityType = {"spspspspspspspspp", "2", "3"};
	private String[] departmentType = {"dpdpdpdpdpdpdpdp", "2", "3"};
	private String[] orgType = {"ooooooooooooooo", "2", "3"};
	private String[] educFormType = {"efefefefefef", "2", "3"};
	private String[] competGroupType = {"cgcgcgcgg", "2", "3"};
	private String[] standardType = {"stststststststststtststststtsts", "2", "3"};
	
	public AddNewCompetGroup(){
		
		setTitle("Добавление новой конкурсной группы");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		add(mainPanel);
		
		directPanel = new JPanel();
		directPanel = createCheckBoxPanel("Направление", directType);
		mainPanel.add(directPanel);
	
		specialityPanel = new JPanel();
		specialityPanel = createCheckBoxPanel("Специальность", specialityType);
		mainPanel.add(specialityPanel);
	
		departmentPanel = new JPanel();
		departmentPanel = createCheckBoxPanel("Кафедра", departmentType);
		mainPanel.add(departmentPanel);
		
		competGroupPanel = new JPanel();
		competGroupPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel competGroupLabel = new JLabel("Конкурсная группа");
		competGroupPanel.add(competGroupLabel);
		JComboBox comboCompetGroupType = new JComboBox(competGroupType);
		competGroupPanel.add(comboCompetGroupType);
		competGroupPanel.add(Box.createRigidArea(dimRigidArea));
		JLabel standardLabel = new JLabel("Стандарт");
		competGroupPanel.add(standardLabel);
		JComboBox comboStandardType = new JComboBox(standardType);
		competGroupPanel.add(comboStandardType);
		mainPanel.add(competGroupPanel);
		
		orgPanel = new JPanel();
		orgPanel = createCheckBoxPanel("Организация", orgType);
		mainPanel.add(orgPanel);
		
		educFormPanel = new JPanel();
		educFormPanel = createCheckBoxPanel("Форма обучения", educFormType);
		mainPanel.add(educFormPanel);
		
		provisionPanel = new JPanel();
		provisionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel dateProvLabel = new JLabel("Дата предоставления");
		provisionPanel.add(dateProvLabel);
		calendar = new JDateChooser();
		calendar.setFont(new Font("Dialog", Font.PLAIN, 11));
		provisionPanel.add(calendar);
		provisionPanel.add(Box.createRigidArea(dimRigidArea));
		JCheckBox originalBox = new JCheckBox("Предоствил оригинал");
		provisionPanel.add(originalBox);
		mainPanel.add(provisionPanel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));	
		JButton saveBtn = new JButton("Сохранить");
		buttonPanel.add(saveBtn);
		mainPanel.add(buttonPanel);
		
		setPreferredSize(new Dimension(600, 370));
		pack();
	}
	
	private JPanel createCheckBoxPanel(String name, String[] arrCheckBox){
		JPanel checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel checkboxLabel = new JLabel(name);
		checkboxPanel.add(checkboxLabel);
		JComboBox comboType = new JComboBox(arrCheckBox);
		checkboxPanel.add(comboType);		

		return checkboxPanel;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddNewCompetGroup window = new AddNewCompetGroup();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
