package minidb;

import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ListenAnzeige extends JDialog{
	
	private JButton ok;
	
	class ListenListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("ok"))
				dispose();
			
		}
		
	}
	
//	Konstruktor
	public ListenAnzeige(JFrame parent, boolean modal) {
		super(parent, modal);
		setTitle("Listen anzeige");
		setLayout(new FlowLayout(FlowLayout.LEFT));
		lesen();
		
		ok = new JButton("OK");
		ok.setActionCommand("ok");
		ok.addActionListener(new ListenListener());
		add(ok);
		setSize(220,300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		
	}
	
	private void lesen() {
		Connection verbindung;
		ResultSet ergebnisMenge;
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		JScrollPane pane = new JScrollPane(panel);
		pane.setPreferredSize(new Dimension(200,220));
		
		try {
//			verbindung = MiniDBTools.oeffnenDB("jdbc:derby:C:/probe/adressenDB");
			verbindung = DriverManager.getConnection("jdbc:derby:C:/probe/adressenDB");
			
			ergebnisMenge = MiniDBTools.liefereErgebnis(verbindung, "SELECT * FROM adressen");
			while(ergebnisMenge.next()) {
				panel.add(new JLabel("ID-Numer : "));
				panel.add(new JLabel(Integer.toString(ergebnisMenge.getInt(1))));
				panel.add(new JLabel("Vorname : "));
				panel.add(new JLabel(ergebnisMenge.getString(2)));
				panel.add(new JLabel("Nachname : "));
				panel.add(new JLabel(ergebnisMenge.getString(3)));
				panel.add(new JLabel("Srtasse : "));
				panel.add(new JLabel(ergebnisMenge.getString(4)));
				panel.add(new JLabel("PZL : "));
				panel.add(new JLabel(ergebnisMenge.getString(5)));
				panel.add(new JLabel("Ort : "));
				panel.add(new JLabel(ergebnisMenge.getString(6)));
				panel.add(new JLabel("Telafon : "));
				panel.add(new JLabel(ergebnisMenge.getString(7)));
				panel.add(new JLabel("----------"));
				panel.add(new JLabel("----------"));			
			}
			add(pane);
			ergebnisMenge.close();
			verbindung.close();
			MiniDBTools.schlissenDB("jdbc:derby:C:/probe/adressenDB");
		}
		catch(Exception e) {
			System.out.println("Problem : /n" + e.toString());
		}
	}

}
