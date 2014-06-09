package stx.shopclient.webservice;

import java.util.HashMap;
import java.util.Map;

public class ServiceResponseCode
{
	public static final int NOT_FOUND_USER = 1; // Пользователь не найден
	public static final int OK = 2; // Операция выполнена
	public static final int EXISTS_USER = 3; // Пользователь с таким именем уже
												// есть в системе
	public static final int NO_VALIDE_ARGS = 5; // Параметры не прошли валидацию
	public static final int ITEM_NOT_FOUND = 6; // Элемент с таким
												// идентификатором не найден
	public static final int ORDER_CANT_DELETE = 7; // Для элемента сформирован
													// счет, удаление невозможно

	public static final int SQL_SERVER_ERROR = 1000; // Внутрянняя ошибка SQL
														// сервера
	public static final int RETURN_EMPTY_STRING = 1001; // Возникает если
														// процедура вернула
														// пустую строку. По
														// Вашему запросу ничего
														// не найдено.
	public static final int WEB_SERVER_ERROR = 1002; // ошибка http code 500,
														// внутренняя ошибка
														// сервера
	public static final int ACCESS_DENIED = 1003; // Доступ по данному ключу
													// невозможен

	static Map<Integer, String> messages = new HashMap<Integer, String>();

	static
	{
		messages.put(NOT_FOUND_USER, "Пользователь не найден");
		messages.put(OK, "Операция выполнена");
		messages.put(EXISTS_USER,
				"Пользователь с таким именем уже есть в системе");
		messages.put(NO_VALIDE_ARGS, "Параметры не прошли валидацию");
		messages.put(SQL_SERVER_ERROR, "Внутрянняя ошибка SQL сервера");
		messages.put(WEB_SERVER_ERROR, "ошибка http code 500");
		messages.put(ACCESS_DENIED, "Доступ по данному ключу невозможен");
		messages.put(RETURN_EMPTY_STRING, "Нет данных");
		messages.put(ORDER_CANT_DELETE,
				"Для элемента сформирован счет, удаление невозможно");
	}

	public static String getMessage(int code)
	{
		if (messages.containsKey(code))
			return messages.get(code);
		else
			return "Неизвестная ошибка";
	}
}
