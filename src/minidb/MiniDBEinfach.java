package minidb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MiniDBEinfach {
	public static void main(String[] args) {
		try {
//			Class.forName("org.apache.derby.jdbc.ClientDriver");
			Connection verbindung = DriverManager.getConnection("jdbc:derby:C:/probe/adressenDB");
			System.out.println("one");
			Statement state = verbindung.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet ergebnisMenge = state.executeQuery("SELECT * FROM adressen");
			System.out.println(ergebnisMenge.toString());
			
			while(ergebnisMenge.next()) {
				System.out.println("nummer" + ergebnisMenge.getInt("iNummer"));
				System.out.println(ergebnisMenge.getString("vorname"));
				System.out.println(ergebnisMenge.getString("nachname"));
				System.out.println(ergebnisMenge.getString("plz"));
				System.out.println(ergebnisMenge.getString("ort"));
				System.out.println(ergebnisMenge.getString("telefon"));	
			}
			state.close();
			ergebnisMenge.close();
			verbindung.close();
		}
		catch(Exception e) {
			System.out.println("Problem : \n" + e.toString());
		}
	}

}
