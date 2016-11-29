package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

public class ModuleChoice extends JFrame {

	private Dimension dimBtn = new Dimension(200, 100);
	private Font fontBtn = new Font("Tahoma", Font.BOLD, 18);
	private GridBagConstraints gbc;
	
	public ModuleChoice(){
		
		setTitle("Окно выбора модуля");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		
		setLayout(new GridBagLayout());
		
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 15, 5, 15);	
		
		JButton ordinBtn = new JButton("Ординатура");
		ordinBtn.setFont(fontBtn);
		ordinBtn.setPreferredSize(dimBtn);
		add(ordinBtn, gbc);
		JButton aspirBtn = new JButton("Аспирантура");
		aspirBtn.setFont(fontBtn);
		aspirBtn.setPreferredSize(dimBtn);
		gbc.gridx = 1;
		add(aspirBtn, gbc);
		
		setPreferredSize(new Dimension(550, 250));
		pack();
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModuleChoice window = new ModuleChoice();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
