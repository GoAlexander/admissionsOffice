package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.util.Properties;

import backend.MessageProcessing;
import backend.ModelDBConnection;

public class LogIn extends JFrame {
	private JPanel mainPanel;
	private JTextField textLogin, textPassword;
	private JButton applyButton;

	public LogIn() {
		setTitle("Авторизация");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel);

		JPanel centralPanel = new JPanel();
		centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		loginPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		loginPanel.add(Box.createRigidArea(new Dimension(47, 0)));
		JLabel loginLabel = new JLabel("Логин");
		loginPanel.add(loginLabel);
		loginPanel.add(Box.createRigidArea(new Dimension(18, 0)));
		textLogin = new JTextField();
		textLogin.setPreferredSize(new Dimension(150, 25));
		loginPanel.add(textLogin);
		centralPanel.add(loginPanel);
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	    passwordPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		JLabel passwordLabel = new JLabel("Пароль");
		passwordPanel.add(passwordLabel);
		passwordPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		textPassword = new JPasswordField();
		textPassword.setPreferredSize(new Dimension(150, 25));
		passwordPanel.add(textPassword);
		centralPanel.add(passwordPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		applyButton = new JButton("Подтвердить");
		applyButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					Properties property = new Properties();
					property.load(new FileInputStream(new File("").getAbsolutePath() + "/.conf"));
		            String serverType = property.getProperty("db.serverType");
		            String serverAddress = property.getProperty("db.serverAddress");
		            String login = property.getProperty("db.login");
		            String password = property.getProperty("db.password");

					ModelDBConnection.setConnectionParameters(serverType, serverAddress, "Ordinator", login, password);
					ModelDBConnection.initConnection();
					if(ModelDBConnection.checkUser(textLogin.getText(), textPassword.getText())) {
						setVisible(false);
						ModuleChoice window = new ModuleChoice(serverType, serverAddress, textLogin.getText(), textPassword.getText());
						window.setVisible(true);
					} else {
						MessageProcessing.displayErrorMessage(mainPanel, 4);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		buttonPanel.add(applyButton);

		mainPanel.add(centralPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

		setPreferredSize(new Dimension(380, 190));
		pack();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn window = new LogIn();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
