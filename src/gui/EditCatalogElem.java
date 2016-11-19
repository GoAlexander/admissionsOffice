package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class EditCatalogElem extends JFrame{

	private JPanel mainPanel, tablePanel;
	private JTable dataTable;
	private GUITableModel currentTM = new GUITableModel();
	private String[] columnNames = { "id", "Наименование", "Код в ФИС" };
	
	private JButton editBtn, saveBtn;
	
	public  EditCatalogElem() {
		
		setTitle("Редактирование элементов справочника");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel);
		
		tablePanel = new JPanel();
		dataTable = new JTable(currentTM);
		currentTM.setDataVector(null, columnNames);
		JScrollPane scrPane = new JScrollPane(dataTable);
		scrPane.setPreferredSize(new Dimension(300, 0));
		//tablePanel.add(scrPane);
		dataTable.setMaximumSize(new Dimension(100,100));
		dataTable.getColumnModel().getColumn(0).setMaxWidth(50);
		mainPanel.add(scrPane, BorderLayout.CENTER);
		
		editBtn = new JButton("Редактировать");
		saveBtn = new JButton("Сохранить");
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,  BoxLayout.LINE_AXIS));
		
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(editBtn);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(saveBtn);
		
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
			
		setPreferredSize(new Dimension(600, 500));
		pack();
		
	}
	
	
	
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditCatalogElem window = new EditCatalogElem();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
