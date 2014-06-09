package stx.shopclient.webservice;

public class ServiceResponseCode
{
	  public static final int NOT_FOUND_USER = 1; //������������ �� ������
	  public static final int OK = 2; //�������� ���������
	  public static final int EXISTS_USER = 3; //������������ � ����� ������ ��� ���� � �������
	  public static final int NO_VALIDE_ARGS = 5; //��������� �� ������ ���������
	  
	  
	  public static final int SQL_SERVER_ERROR = 1000; //���������� ������ SQL �������
	  public static final int WEB_SERVER_ERROR = 1002; //������ http code 500, ���������� ������ �������
	  public static final int ACCESS_DENIED = 1003; //������ �� ������� ����� ����������
}
