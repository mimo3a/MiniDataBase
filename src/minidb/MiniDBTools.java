package minidb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MiniDBTools {

	public static Connection oeffnenDB(String arg) {
		Connection verbindung = null;
		try {
			verbindung = DriverManager.getConnection(arg);
		} catch (Exception e) {
			System.out.println("Problem :/n" + e.toString());
		}
		return verbindung;
	}
	
//	maximale ID
	public static int getMaximalID(ResultSet ergebnisMenge)  {
		int maxIdValue = 0;
		try {
		int temp = ergebnisMenge.getRow();
		ergebnisMenge.last();
		maxIdValue = ergebnisMenge.getInt(1);
		ergebnisMenge.absolute(temp);
			}
			catch(Exception e) {
				System.out.println("Problem :/n" + e.toString());
			}
		return maxIdValue;		
		}
		
		
	
	
	public static int getElementMenge(ResultSet ergebnisMemge) {
		int maxIdValue = 0;
		int temp = 0;
		try {

			temp = ergebnisMemge.getRow();
			ergebnisMemge.last();
			maxIdValue = ergebnisMemge.getRow();
			ergebnisMemge.absolute(temp);
			
			}
			catch(Exception e) {
			System.out.println("Problem :/n" + e.toString());
		}
		return maxIdValue;
		
	}

	public static ResultSet liefereErgebnis(Connection verbindung, String sqlAnweisung) {
		ResultSet ergebnisMenge = null;
		try {
			Statement state = verbindung.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ergebnisMenge = state.executeQuery(sqlAnweisung);
		} catch (Exception e) {
			System.out.println("Problem :/n" + e.toString());
		}
		return ergebnisMenge;
	}

	public static void schlissenDB(String protokol) {
		boolean erfolg = false;
		try {
			DriverManager.getConnection(protokol + "adressenDB; shutdown = true");
		} catch (SQLException e) {
			erfolg = true;
		}
		if (erfolg != true)
			System.out.println("Das DBMS konnte nicht heruntergefahren werden.");
	}

}
