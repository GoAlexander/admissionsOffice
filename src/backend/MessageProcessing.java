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
		case 2:
			titleMessage = "Результат редактирования абитуриента";
			message = "Данные успешно изменены!";
			break;
		case 3:
			titleMessage = "Результат удаления абитуриента";
			message = "Абитуриент успешно удален из базы!";
			break;
		case 4:
			titleMessage = "Результат редактирования справочника";
			message = "Данные успешно изменены!";
			break;
		case 5:
			titleMessage = "Результат редактирования справочника";
			message = "Данные успешно удалены!";
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
		case 2:
			titleMessage = "Результат редактирования справочника";
			message = "Справочник не может быть отредактирован!";
			break;
		case 3:
			titleMessage = "Результат редактирования справочника";
			message = "Элемент не может быть удален из справочника!";
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

	public static int displayDialogMessage(Component parent, int messageType) {
		String message = null, titleMessage = null;

		switch(messageType) {
		case 1:
			titleMessage = "Удаление абитуриента";
			message = "Вы действительно хотите удалить текущего абитуриента?";
			break;
		}

        return JOptionPane.showConfirmDialog(parent,
        		message,
        		titleMessage, 
        		JOptionPane.YES_NO_OPTION);
	}
}
