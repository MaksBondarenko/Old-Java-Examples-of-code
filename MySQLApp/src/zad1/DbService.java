package zad1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbService {
	private List<List<String>> data = new ArrayList<>();
	
	public List<List<String>> getData() {
		return data;
	}
	public void connect(String conStr,String name,String pass) {
		Connection conn;
        String polecenie = "Select NAZWA,AWTOR,RODZAJ,year(DATA_NAPISANIA),year(DATA_WYDANIA),WYDAWNICTWO FROM book";
        try {
        	DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
            conn = DriverManager.getConnection(conStr,name,pass);
            Statement stmt= conn.createStatement();
            ResultSet rs= stmt.executeQuery(polecenie);
            while( rs.next()) {
            	List<String> dane= new ArrayList<>();
            	dane.add(rs.getString(1));
            	dane.add(rs.getString(2));
            	dane.add(rs.getString(3));
            	dane.add(rs.getString(4));
            	dane.add(rs.getString(5));
            	dane.add(rs.getString(6));
            	data.add(dane);
            }
            rs.close();
            conn.close();
        }catch(SQLException ex) {
            System.out.println("Error 1: " + ex.toString());
        }
	}

}
