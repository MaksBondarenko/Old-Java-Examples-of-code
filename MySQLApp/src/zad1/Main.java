package zad1;

public class Main {

	public static void main(String[] args) {
		DbService dbservice = new DbService();
		dbservice.connect("jdbc:mysql://localhost:3306/mylibrary","root","root");
		new GUI(dbservice.getData());
	}

}
