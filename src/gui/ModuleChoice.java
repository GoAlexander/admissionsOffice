package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import backend.ModelDBConnection;

public class ModuleChoice extends JFrame {
	private String login, password;
	private Dimension dimBtn = new Dimension(200, 100);
	private Font fontBtn = new Font("Tahoma", Font.BOLD, 18);
	private GridBagConstraints gbc;

	public ModuleChoice(String login, String password) {
		this.login = login;
		this.password = password;

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
		ordinBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Ordinator", login, password);
				ModelDBConnection.initConnection();
				GeneralInfoInput window;
				window = new GeneralInfoInput();
				window.setVisible(true);
				setVisible(false);
			}
		});

		ordinBtn.setFont(fontBtn);
		ordinBtn.setPreferredSize(dimBtn);
		add(ordinBtn, gbc);

		JButton aspirBtn = new JButton("Аспирантура");
		aspirBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Aspirant", login, password);
				ModelDBConnection.initConnection();
				GeneralInfoInput window = new GeneralInfoInput();
				window.setVisible(true);
				setVisible(false);
			}
		});

		aspirBtn.setFont(fontBtn);
		aspirBtn.setPreferredSize(dimBtn);
		gbc.gridx = 1;
		add(aspirBtn, gbc);

		setPreferredSize(new Dimension(550, 250));
		pack();

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent event) {}
			public void windowClosed(WindowEvent event) {}
			public void windowDeactivated(WindowEvent event) {}
			public void windowDeiconified(WindowEvent event) {}
			public void windowIconified(WindowEvent event) {}
			public void windowOpened(WindowEvent event) {}

			public void windowClosing(WindowEvent event) {
				Object[] options = { "Да", "Нет!" };
				int n = JOptionPane.showOptionDialog(event.getWindow(), "Выйти из приложения?", "Выход из приложения",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (n == 0) {
					event.getWindow().setVisible(false);
					System.exit(0);
				}
			}
		});
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModuleChoice window = new ModuleChoice("admin1", "admin1");
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
