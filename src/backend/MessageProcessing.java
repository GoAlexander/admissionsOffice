package backend;

import java.awt.Component;

import javax.swing.JOptionPane;

public abstract class MessageProcessing {
	public static void displaySuccessMessage(Component parent, int messageType) {
		String message = null, titleMessage = null;

		switch (messageType) {
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
		case 6:
			titleMessage = "Результат редактирования паспортных данных";
			message = "Данные успешно сохранены!";
			break;
		case 7:
			titleMessage = "Результат редактирования контактной информации";
			message = "Данные успешно сохранены!";
			break;
		case 8:
			titleMessage = "Результат редактирования информации по образованию";
			message = "Данные успешно сохранены!";
			break;
		case 9:
			titleMessage = "Результат редактирования вступительных испытаний";
			message = "Данные успешно сохранены!";
			break;
		}

		JOptionPane.showMessageDialog(parent, message, titleMessage, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void displayErrorMessage(Component parent, Exception e) {
		JOptionPane.showMessageDialog(parent, e.toString(), "Ошибка", JOptionPane.ERROR_MESSAGE);
	}

	public static void displayErrorMessage(Component parent, int messageType) {
		String message = null, titleMessage = null;

		switch (messageType) {
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
		case 4:
			titleMessage = "Результат авторизации";
			message = "Ошибка входа! Неверный логин или пароль!";
			break;
		case 5:
			titleMessage = "Результат вывода листа вступительных испытаний";
			message = "Список специальностей пуст. Вывод невозможен!";
			break;
		case 6:
			titleMessage = "Результат вывода листа вступительных испытаний";
			message = "Список вступительных испытаний пуст. Вывод невозможен!";
			break;
		case 7:
			titleMessage = "Результат проверки данных";
			message = "Не указан регион проживания абитуриента!";
			break;
		case 8:
			titleMessage = "Результат проверки данных";
			message = "Не указан тип населенного пункта!";
			break;
		case 9:
			titleMessage = "Результат проверки данных";
			message = "Некорректный формат данных! Исправьте поля, выделенные красным";
			break;
		case 10:
			titleMessage = "Результат проверки данных";
			message = "Не указан блок испытаний!";
			break;
		case 11:
			titleMessage = "Результат проверки данных";
			message = "Не указано количество набранных баллов!";
			break;
		case 12:
			titleMessage = "Результат проверки данных";
			message = "Группа не указана либо указана не верно!";
			break;
		case 13:
			titleMessage = "Результат проверки данных";
			message = "Неверно введена дата! Правильный формат: dd.mm.yyyy";
			break;
		case 14:
			titleMessage = "Результат проверки данных";
			message = "Невозможно сохранить несколько результатов одного вступительного испытания!";
			break;
		default:
			titleMessage = "Неизвестная ошибка";
			message = "Произошла неизвестная ошибка. Обратитесь к администратору!";
		}

		JOptionPane.showMessageDialog(parent, message, titleMessage, JOptionPane.ERROR_MESSAGE);
	}

	public static int displayDialogMessage(Component parent, int messageType) {
		String message = null, titleMessage = null;

		switch (messageType) {
		case 1:
			titleMessage = "Удаление абитуриента";
			message = "Вы действительно хотите удалить текущего абитуриента?";
			break;
		}

		return JOptionPane.showConfirmDialog(parent, message, titleMessage, JOptionPane.YES_NO_OPTION);
	}
}
