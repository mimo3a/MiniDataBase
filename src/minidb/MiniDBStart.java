package minidb;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MiniDBStart extends JFrame {
	
	class MeinListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("liste"))
				anzeigeListe();
			if(e.getActionCommand().equals("einzel"))
				anzeigeEinzel();
			if(e.getActionCommand().equals("neu"))
				neuerEintrag();
			if(e.getActionCommand().equals("ende"))
				beenden();
				
			
		}
		
	}
	

	public MiniDBStart(String titel) {
		super(titel);

		setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton liste = new JButton("Listenanzeige");
		liste.setActionCommand("liste");
		JButton einzel = new JButton("Einzelanzeige");
		einzel.setActionCommand("einzel");
		JButton neu = new JButton("Neuer Eintrag");
		neu.setActionCommand("neu");
		JButton beenden = new JButton("Beenden");
		beenden.setActionCommand("ende");
		
		MeinListener listener = new MeinListener();
		liste.addActionListener(listener);
		einzel.addActionListener(listener);
		neu.addActionListener(listener);
		beenden.addActionListener(listener);
		
		add(liste);
		add(einzel);
		add(neu);
		add(beenden);
		
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		

	}
	
	private void anzeigeListe() {
		
		new ListenAnzeige(this, true);
	};
	private void anzeigeEinzel() {
		
		new BearbeitenEintrag(this, true);
		
	};
	private void neuerEintrag() {
		
		new NeuerEintrag(this, true);
		
	};
	private void beenden() {
		
	};
	public static void main(String[] args) {
		new MiniDBStart("Mini Adresse");
	}

}
