package minidb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyTest {
	public static void main(String[] args) {
	    try {
	    	System.out.println("one");

	      System.out.println("two");
	      Connection conn = DriverManager.getConnection("jdbc:derby:C:/probe/adressenDB");
	      System.out.println("three");
	      // Do something with the connection...
	      conn.close();
	    } catch (SQLException e) {
	      System.out.println("Derby test " + e.toString());
	    }
	  }

}
