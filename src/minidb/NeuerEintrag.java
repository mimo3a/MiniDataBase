package minidb;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NeuerEintrag extends JDialog {

	private JTextField name, nachname, strasse, pzl, ort, telefon;
	private JButton ok, abbrechen;

	public NeuerEintrag(JFrame parent, boolean modal) {
		super(parent, modal);
		setTitle("Neuer Eintrag");
		initGui();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	class NeuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("ok"))
				uebernehmen();
			if (e.getActionCommand().equals("abbrechen"))
				dispose();
		}
	}

	private void initGui() {
		setLayout(new GridLayout(0, 2));
		add(new JLabel("Vorname : "));
		name = new JTextField();
		add(name);
		add(new JLabel("Nachname : "));
		nachname = new JTextField();
		add(nachname);
		add(new JLabel("Strasse : "));
		strasse = new JTextField();
		add(strasse);
		add(new JLabel("PZL : "));
		pzl = new JTextField();
		add(pzl);
		add(new JLabel("Ort : "));
		ort = new JTextField();
		add(ort);
		add(new JLabel("Telefon : "));
		telefon = new JTextField();
		add(telefon);

		ok = new JButton("OK");
		ok.setActionCommand("ok");
		abbrechen = new JButton("Abbrechen");
		abbrechen.setActionCommand("abbrechen");

		NeuListener listener = new NeuListener();
		ok.addActionListener(listener);
		abbrechen.addActionListener(listener);

		add(ok);
		add(abbrechen);

		pack();
		setVisible(true);
	}
	
	private void uebernehmen() {
		Connection verbindung;
		ResultSet ergebnismenge;
		try {
//			verbindung = MiniDBTools.oeffnenDB("jdbc:derby:C:/probe/adressenDB");
			verbindung = DriverManager.getConnection("jdbc:derby:C:/probe/adressenDB");
			ergebnismenge = MiniDBTools.liefereErgebnis(verbindung, "SELECT * FROM adressen");
			ergebnismenge.moveToInsertRow();
			
			ergebnismenge.updateString(2, name.getText());
			ergebnismenge.updateString(3, nachname.getText());
			ergebnismenge.updateString(4, strasse.getText());
			ergebnismenge.updateString(5, pzl.getText());
			ergebnismenge.updateString(6, ort.getText());
			ergebnismenge.updateString(7, telefon.getText());
			
			ergebnismenge.insertRow();
			ergebnismenge.close();
			verbindung.close();
			MiniDBTools.schlissenDB("jdbc:derby:C:/probe/adressenDB");	
			dispose();
		}
		catch(Exception e) {
			System.out.println("Problem : /n" + e.toString());
		}
	}
}
