package stx.shopclient.webservice;

public class ServiceResponseCode
{
	  public static final int NOT_FOUND_USER = 1; //Пользователь не найден
	  public static final int OK = 2; //Операция выполнена
	  public static final int EXISTS_USER = 3; //Пользователь с таким именем уже есть в системе
	  public static final int NO_VALIDE_ARGS = 5; //Параметры не прошли валидацию
	  
	  
	  public static final int SQL_SERVER_ERROR = 1000; //Внутрянняя ошибка SQL сервера
	  public static final int WEB_SERVER_ERROR = 1002; //ошибка http code 500, внутренняя ошибка сервера
	  public static final int ACCESS_DENIED = 1003; //Доступ по данному ключу невозможен
}
