package minidb;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
//import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.lang.model.type.PrimitiveType;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class BearbeitenEintrag extends JDialog{
	
	private JTextField name, nachname, strasse, plz, ort, telefon;
	private JLabel numer, datenSatz;
	
	private MeineActionen loeschenAct, vorAct, zurueckAct, startAct, endeAct, aktualisierenAct;
	
	private Connection verbindung;
	private ResultSet ergebnisMenge;
	private String sqlAbfrage;
	

	
//	eine innere Klasse fur die Fenstererreignisse
	class Fensterlistener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			
			try {
				ergebnisMenge.close();
				verbindung.close();
				MiniDBTools.schlissenDB("jdbc:derby:C:/probe/adressenDB");
				
			}
			catch(Exception exp) {
				System.out.println("Problem : /n" + exp.toString());
			}
		}
	}
	
//	eine innere Klasse fur die Actionen
	class MeineActionen extends AbstractAction{
		
//		Kinstruktor
		public MeineActionen(String text, ImageIcon icon, String beschreibung,
							 KeyStroke shortcut,String actionText) {
			super(text, icon);
			putValue(SHORT_DESCRIPTION, beschreibung);
			putValue(ACCELERATOR_KEY, shortcut);
			putValue(ACTION_COMMAND_KEY, actionText);
			
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("vor"))
				ganzVor();
			if(e.getActionCommand().equals("zurueck"))
				ganzZurueck();
			if(e.getActionCommand().equals("einenvor"))
				einenVor();
			if(e.getActionCommand().equals("einenzurueck"))
				einenZurueck();
			if(e.getActionCommand().equals("loeschen"))
				loeschen();
			if(e.getActionCommand().equals("aktualisieren"))
				aktualisieren();	
		}		
	}
	
//	der Konstructor class BearbeitenEintrag
	public BearbeitenEintrag(JFrame parent, boolean modal) {
		super(parent, modal);
		setTitle("Eintrage beerbeiten");
		setLayout(new BorderLayout());
		loeschenAct = new MeineActionen(
				"Datensatz loeschen", 
				new ImageIcon("icons/Delete24.gif"),
				"Loescht den aktuelen Datensatz",
				null,
				"loeschen");
		vorAct = new MeineActionen(
				"Einen Datensatz weiter",
				new ImageIcon("icons/Forward24.gif"),
				"Blaettert einen Datensatz weiter",
				null,
				"einenvor");
		zurueckAct = new MeineActionen(
				"einen Datensatz zurueck",
				new ImageIcon("icons/Back24.gif"),
				"blaettern einen Datensatz zurueck",
				null,
				"einenzurueck");
		startAct = new MeineActionen(
				"zum ersten Datensatz",
				new ImageIcon("icons/Front24.gif"),
				"Geht zum ersten Datensatz",
				null,
				"vor");
		endeAct = new MeineActionen(
				"zum letzten Datensatz",
				new ImageIcon("icons/End24.gif"),
				"Geht zum letzten Datensatz",
				null,
				"zurueck");
		aktualisierenAct = new MeineActionen(
				"Anderungen speichern",
				new ImageIcon("icons/Save24.gif"),
				"Speichern Anderungen am aktuelen Datensatz",
				null,
				"aktualisieren");	
		
		add(symbolleiste(), BorderLayout.NORTH);
		add(initGui(), BorderLayout.CENTER);
		sqlAbfrage = "SELECT * FROM adressen";
//		DB Verbindung herstellen
		initDB();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		
//		add window listener only befor pack() and setVisible()
		addWindowListener(new Fensterlistener());

		pack();
		setVisible(true);
		System.out.println("start");
		
		
		
	}
	
//	fugt die Felder in ein Panel ein und liefert das Panel zurueck
	private JPanel initGui() {
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new GridLayout(0,2));
		tempPanel.add(new JLabel("ID-Nummer : "));
		numer = new JLabel();
		tempPanel.add(numer);
		
		tempPanel.add(new JLabel("Vorname : "));
		name = new JTextField();
		tempPanel.add(name);
		
		tempPanel.add(new JLabel("Nachname : "));
		nachname = new JTextField();
		tempPanel.add(nachname);
		
		tempPanel.add(new JLabel("Strasse : "));
		strasse= new JTextField();
		tempPanel.add(strasse);
		
		tempPanel.add(new JLabel("PLZ : "));
		plz= new JTextField();
		tempPanel.add(plz);
		
		tempPanel.add(new JLabel("Ort : "));
		ort= new JTextField();
		tempPanel.add(ort);
		
		tempPanel.add(new JLabel("Telefon : "));
		telefon= new JTextField();
		tempPanel.add(telefon);
		
		
//		Label fur Datensatznummer
		datenSatz = new JLabel();
		tempPanel.add(datenSatz);
		
		
		return tempPanel;
		
	}
	
//	die Synbolleiste erzeugen und zuruckgeben
	private JToolBar symbolleiste() {
		JToolBar leiste = new JToolBar();
		leiste.add(loeschenAct);
		leiste.add(aktualisierenAct);
		leiste.addSeparator();
		leiste.add(startAct);
		leiste.add(zurueckAct);
		leiste.add(vorAct);
		leiste.add(endeAct);
		
		return leiste;
		
	}
	
//	die verbindung Datenbank herstellen
	private void initDB() {
		try {
			verbindung = MiniDBTools.oeffnenDB("jdbc:derby:C:/probe/adressenDB");
			ergebnisMenge = MiniDBTools.liefereErgebnis(verbindung, sqlAbfrage);
			if(ergebnisMenge.next())
				datenLesen();
		}
		catch(Exception e) {
			System.out.println("Problem : /n" + e.toString());
		}
	}
	
//	die Methode liest die Daten und schreibt sie in die Felder
	private void datenLesen() {
		try {
			
//			int datenID = ergebnisMenge.getInt(1);			
//			int maxID = MiniDBTools.getMaximalID(ergebnisMenge);
			int laufDaten = ergebnisMenge.getRow();
			int maxDat = MiniDBTools.getElementMenge(ergebnisMenge);
			numer.setText(Integer.toString(ergebnisMenge.getInt(1)));
			name.setText(ergebnisMenge.getString(2));
			nachname.setText(ergebnisMenge.getString(3));
			strasse.setText(ergebnisMenge.getString(4));
			plz.setText(ergebnisMenge.getString(5));
			ort.setText(ergebnisMenge.getString(6));
			telefon.setText(ergebnisMenge.getString(7));
			
			datenSatz.setText("Datensatz " + laufDaten + "  von " + maxDat );	
			
			
		}
		catch(Exception e) {
			System.out.println("Problem : /n" + e.toString());
		}
		
	}
	
//	die Methode geht zum ersten Datensatz
	private void ganzVor() {
		try {
			ergebnisMenge.first();
			datenLesen();
		}
		catch(Exception e) {
			System.out.println("Problem in ganzVor : /n" + e.toString());
		}
		
	}
	
//	die Methode geht zum letzten Datensatz
	private void ganzZurueck() {
		try {
			ergebnisMenge.last();
			datenLesen();
		}
		catch(Exception e) {
			System.out.println("Problem in ganzZurueck : /n" + e.toString());
		}		
	};
	
//	die Methode geht einen Datensatz weiter
	private void einenVor() {
		try {
			if(!ergebnisMenge.isLast()) {
				ergebnisMenge.next();
				datenLesen();
			}
		}
		catch (Exception e) {
			System.out.println("Problem in einenVor : /n" + e.toString());
		}
		
	};
	
//	die Methode geht einen Datensatz zurueck
	private void einenZurueck() {
		try {
			if(!ergebnisMenge.isFirst()) {
				ergebnisMenge.previous();
				datenLesen();
			}
		}
		catch (Exception e) {
			System.out.println("Problem in einenZurueck : /n" + e.toString());
		}
	};
	
//	die Methode loescht einen Datensatz
	private void loeschen() {
		
		try {
			int position;
			position = ergebnisMenge.getRow();
			System.out.println(position);
			ergebnisMenge.deleteRow();
			ergebnisMenge.close();
			ergebnisMenge = MiniDBTools.liefereErgebnis(verbindung, sqlAbfrage);
			ergebnisMenge.absolute(position);
			if(ergebnisMenge.isAfterLast())
				ergebnisMenge.last();
			datenLesen();
		}
		catch(Exception e) {
			System.out.println("Problem in loeschen : /n" + e.toString());
		}
		
	};
	
//	die Metgode aktualisiert einen Eintrag
	private void aktualisieren() {
		
		try {
			int position;
			position = ergebnisMenge.getRow();
			
			ergebnisMenge.updateString(2, name.getText());
			ergebnisMenge.updateString(3, nachname.getText());
			ergebnisMenge.updateString(4, strasse.getText());
			ergebnisMenge.updateString(5, plz.getText());
			ergebnisMenge.updateString(6, ort.getText());
			ergebnisMenge.updateString(7, telefon.getText());
			
			ergebnisMenge.updateRow();
			ergebnisMenge.close();
			ergebnisMenge = MiniDBTools.liefereErgebnis(verbindung, sqlAbfrage);
			ergebnisMenge.absolute(position);
			datenLesen();				
		}
		catch(Exception e) {
			System.out.println("Problem in aktualisieren : /n" + e.toString());
		}
	}
	

}
