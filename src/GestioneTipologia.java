import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import utility.AlphaPanel;


public class GestioneTipologia extends JPanel {

	Connection connection = null;
	
	private JTable table;
	private String[] strNameColumns;
	
	private JPanel headerPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	
	private AlphaPanel pnModificaTipologia;
	
	private JScrollPane scrollPane;
	private JButton btnModificaTipologia;
	private JButton btnInserisciTipologia;
	private JButton btnEliminaTipologia;
	
	
	public GestioneTipologia() {

//		connessione al database
		connection = sqliteConnection.dbConnector();
		
		setLayout(new BorderLayout(0, 0));
		
//		###	HEADER			#############################################################################################################
		
		headerPanel = new JPanel();
		JLabel lblVisualizzazioneEGestione = new JLabel("Visualizzazione e gestione delle Tipologie");
		lblVisualizzazioneEGestione.setVerticalAlignment(SwingConstants.BOTTOM);
		lblVisualizzazioneEGestione.setFont(new Font("Purisa", Font.BOLD, 16));
		headerPanel.add(lblVisualizzazioneEGestione);
		headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 70));
		add(headerPanel, BorderLayout.PAGE_START);
		
//		###	LEFT			#############################################################################################################
		
		leftPanel = new JPanel();
		leftPanel.setLayout(null);
		
		leftPanel.setPreferredSize(new Dimension(350, 400));
		add(leftPanel, BorderLayout.LINE_START);
		
		
		
//		###	CENTER			#############################################################################################################
		
//		TABLE
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 1276, 432);
		add(scrollPane);
		
		//printTable();
	
		scrollPane.setViewportView(table);
		
		
		
//		###	RIGHT			#############################################################################################################
		
		rightPanel = new JPanel();
		rightPanel.setLayout(null);
		rightPanel.setPreferredSize(new Dimension(20, 400));
		add(rightPanel, BorderLayout.LINE_END);
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
