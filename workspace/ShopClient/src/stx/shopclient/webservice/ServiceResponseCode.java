package stx.shopclient.webservice;

import java.util.HashMap;
import java.util.Map;

public class ServiceResponseCode
{
	public static final int NOT_FOUND_USER = 1; // ������������ �� ������
	public static final int OK = 2; // �������� ���������
	public static final int EXISTS_USER = 3; // ������������ � ����� ������ ���
												// ���� � �������
	public static final int NO_VALIDE_ARGS = 5; // ��������� �� ������ ���������
	public static final int ITEM_NOT_FOUND = 6; // ������� � �����
												// ��������������� �� ������
	public static final int ORDER_CANT_DELETE = 7; // ��� �������� �����������
													// ����, �������� ����������

	public static final int SQL_SERVER_ERROR = 1000; // ���������� ������ SQL
														// �������
	public static final int RETURN_EMPTY_STRING = 1001; // ��������� ����
														// ��������� �������
														// ������ ������. ��
														// ������ ������� ������
														// �� �������.
	public static final int WEB_SERVER_ERROR = 1002; // ������ http code 500,
														// ���������� ������
														// �������
	public static final int ACCESS_DENIED = 1003; // ������ �� ������� �����
													// ����������

	static Map<Integer, String> messages = new HashMap<Integer, String>();

	static
	{
		messages.put(NOT_FOUND_USER, "������������ �� ������");
		messages.put(OK, "�������� ���������");
		messages.put(EXISTS_USER,
				"������������ � ����� ������ ��� ���� � �������");
		messages.put(NO_VALIDE_ARGS, "��������� �� ������ ���������");
		messages.put(SQL_SERVER_ERROR, "���������� ������ SQL �������");
		messages.put(WEB_SERVER_ERROR, "������ http code 500");
		messages.put(ACCESS_DENIED, "������ �� ������� ����� ����������");
		messages.put(RETURN_EMPTY_STRING, "��� ������");
		messages.put(ORDER_CANT_DELETE,
				"��� �������� ����������� ����, �������� ����������");
	}

	public static String getMessage(int code)
	{
		if (messages.containsKey(code))
			return messages.get(code);
		else
			return "����������� ������";
	}
}
