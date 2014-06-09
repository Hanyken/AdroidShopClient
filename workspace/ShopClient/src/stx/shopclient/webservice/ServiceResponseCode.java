package stx.shopclient.webservice;

import java.util.HashMap;
import java.util.Map;

public class ServiceResponseCode
{
	static Map<Integer, String> errorMessages = new HashMap<Integer, String>();
	
	public static final int NOT_FOUND_USER = 1; // Пользователь не найден
	public static final int OK = 2; // Операция выполнена
	public static final int EXISTS_USER = 3; // Пользователь с таким именем уже
												// есть в системе
	public static final int NO_VALIDE_ARGS = 5; // Параметры не прошли валидацию

	public static final int SQL_SERVER_ERROR = 1000; // Внутрянняя ошибка SQL
														// сервера
	public static final int WEB_SERVER_ERROR = 1002; // ошибка http code 500,
														// внутренняя ошибка
														// сервера
	public static final int ACCESS_DENIED = 1003; // Доступ по данному ключу
													// невозможен

	static
	{
		errorMessages.put(NOT_FOUND_USER, "Пользователь не найден");
		errorMessages.put(OK, "Операция выполнена");
		errorMessages.put(EXISTS_USER, "Пользователь с таким именем уже есть в системе");
		errorMessages.put(NO_VALIDE_ARGS, "Параметры не прошли валидацию");
		errorMessages.put(SQL_SERVER_ERROR, "Внутрянняя ошибка SQL сервера");
		errorMessages.put(WEB_SERVER_ERROR, "ошибка http code 500");
		errorMessages.put(ACCESS_DENIED, "Доступ по данному ключу невозможен");
	}
	
	public static String getMessage(int code)
	{
		if (errorMessages.containsKey(code))
			return errorMessages.get(code);
		else
			return "Неизвестная ошибка";
	}
}
