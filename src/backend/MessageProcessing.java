package backend;

import java.awt.Component;

import javax.swing.JOptionPane;

public abstract class MessageProcessing {
	public static void displaySuccessMessage(Component parent, int messageType) {
		String message = null, titleMessage = null;

		switch(messageType) {
		case 1:
			titleMessage = "Результат добавления абитуриента";
			message = "Абитуриент успешно добавлен!";
			break;
		}

        JOptionPane.showMessageDialog(parent,
        		message,
        		titleMessage, 
                JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void displayErrorMessage(Component parent, Exception e) {
        JOptionPane.showMessageDialog(parent,
        		e.toString(),
        		"Ошибка", 
                JOptionPane.ERROR_MESSAGE);
	}
	
	public static void displayErrorMessage(Component parent, int messageType) {
		String message = null, titleMessage = null;

		switch(messageType) {
		case 1:
			titleMessage = "Результат добавления абитуриента";
			message = "Абитуриент не может быть добавлен!";
			break;
		default:
			titleMessage = "Неизвестная ошибка";
			message = "Произошла неизвестная ошибка. Обратитесь к администратору!";
		}

        JOptionPane.showMessageDialog(parent,
        		message,
        		titleMessage, 
                JOptionPane.ERROR_MESSAGE);
	}
}
