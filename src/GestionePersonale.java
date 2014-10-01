import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import utility.AlphaPanel;


public class GestionePersonale extends JPanel {

	Connection connection = null;
	
	private JPanel headerPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private AlphaPanel pnModificaPersonale;
	
	/**
	 * Create the panel.
	 */
	public GestionePersonale() {
		
//		connessione al database
		connection = sqliteConnection.dbConnector();
		
		setLayout(new BorderLayout(0, 0));
		
//	###	HEADER			#############################################################################################################
		
		headerPanel = new JPanel();
		JLabel lblVisualizzazioneEGestione = new JLabel("Visualizzazione e gestione del Personale");
		lblVisualizzazioneEGestione.setVerticalAlignment(SwingConstants.BOTTOM);
		lblVisualizzazioneEGestione.setFont(new Font("Purisa", Font.BOLD, 16));
		headerPanel.add(lblVisualizzazioneEGestione);
		headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 70));
		add(headerPanel, BorderLayout.PAGE_START);
		
		
//		###	LEFT			#############################################################################################################
		
		leftPanel = new JPanel();
		leftPanel.setLayout(null);
		
		
		// ********** pannello Modifica personale
		
		pnModificaPersonale = new AlphaPanel("images/sfondo.jpg", "Modifica Fattura Selezionata", 30, 0, 300, 400);
		
		// button collapse Minus pnDatiFattura 
		ActionListener pnDatiFatturaLisMinus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnModificaPersonale.setSize(300, 40);
				//pnModificaPersonale.setBounds(30, pnDatiFattura.getHeight() + 20, 300, pnInsertFattura.getHeight());
				pnModificaPersonale.revalidate();
				pnModificaPersonale.repaint();
			}
		};
		pnModificaPersonale.setBtnCollapseMinus(pnDatiFatturaLisMinus);
		
		// button collapse Minus pnDatiFattura
		ActionListener pnDatiFatturaLisPlus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnModificaPersonale.setSize(300, 430);
				//pnModificaPersonale.setBounds(30, pnDatiFattura.getHeight() + 20, 300, 40);
				pnModificaPersonale.revalidate();
				pnModificaPersonale.repaint();
			}
		};
		pnModificaPersonale.setBtnCollapsePlus(pnDatiFatturaLisPlus);
		
		
		
		
		
		
		
		leftPanel.add(pnModificaPersonale);
		
		
		
		leftPanel.setPreferredSize(new Dimension(350, 400));
		add(leftPanel, BorderLayout.LINE_START);
		

	}

}
