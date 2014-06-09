package stx.shopclient.webservice;

import java.util.HashMap;
import java.util.Map;

public class ServiceResponseCode
{
	static Map<Integer, String> errorMessages = new HashMap<Integer, String>();
	
	public static final int NOT_FOUND_USER = 1; // ������������ �� ������
	public static final int OK = 2; // �������� ���������
	public static final int EXISTS_USER = 3; // ������������ � ����� ������ ���
												// ���� � �������
	public static final int NO_VALIDE_ARGS = 5; // ��������� �� ������ ���������

	public static final int SQL_SERVER_ERROR = 1000; // ���������� ������ SQL
														// �������
	public static final int WEB_SERVER_ERROR = 1002; // ������ http code 500,
														// ���������� ������
														// �������
	public static final int ACCESS_DENIED = 1003; // ������ �� ������� �����
													// ����������

	static
	{
		errorMessages.put(NOT_FOUND_USER, "������������ �� ������");
		errorMessages.put(OK, "�������� ���������");
		errorMessages.put(EXISTS_USER, "������������ � ����� ������ ��� ���� � �������");
		errorMessages.put(NO_VALIDE_ARGS, "��������� �� ������ ���������");
		errorMessages.put(SQL_SERVER_ERROR, "���������� ������ SQL �������");
		errorMessages.put(WEB_SERVER_ERROR, "������ http code 500");
		errorMessages.put(ACCESS_DENIED, "������ �� ������� ����� ����������");
	}
	
	public static String getMessage(int code)
	{
		if (errorMessages.containsKey(code))
			return errorMessages.get(code);
		else
			return "����������� ������";
	}
}
