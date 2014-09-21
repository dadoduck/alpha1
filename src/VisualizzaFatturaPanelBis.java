import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JComboBox;
public class VisualizzaFatturaPanelBis extends JPanel {
	
	private JPanel headerPanel;
	private JPanel leftPanel;
	private ImagePanel pnDatiFattura;
	private JTextField tfDatiFatturaNumero;
	private JTextField tfDatiFatturaData;
	private JComboBox<String> cbDatiFatturaTipologia;
	private JTextField tfDatiFatturaImporto;
	private JComboBox<String> cbDatiFatturaAcquisitore;
	private JTextField tfDatiFatturaProvvA;
	private JComboBox<String> cbDatiFatturaVenditore;
	private JTextField tfDatiFatturaProvvV;

	/**
	 * Create the panel.
	 */
	public VisualizzaFatturaPanelBis() {
		setLayout(new BorderLayout(0, 0));
		
//	###	HEADER			#############################################################################################################
		
		headerPanel = new JPanel();
		JLabel lblVisualizzazioneEGestione = new JLabel("Visualizzazione e gestione Fatture");
		lblVisualizzazioneEGestione.setFont(new Font("Purisa", Font.BOLD, 16));
		headerPanel.add(lblVisualizzazioneEGestione);
		add(headerPanel, BorderLayout.PAGE_START);
		
		
		
//	###	LEFT			#############################################################################################################
		
		leftPanel = new JPanel();
		leftPanel.setLayout(null);
			
		// pannello con i dati della fattura
		
		pnDatiFattura = new ImagePanel(new ImageIcon("images/sfondo.jpg").getImage());
		pnDatiFattura.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		pnDatiFattura.setLayout(null);
		pnDatiFattura.setBounds(20, 20, 300, 400);
		
		// titolo pannello
		JLabel lblDatiFatturaSelezionata = new JLabel("Dati Fattura Selezionata");
		lblDatiFatturaSelezionata.setForeground(new Color(255, 255, 255));
		lblDatiFatturaSelezionata.setFont(new Font("Ubuntu", Font.BOLD, 15));
		lblDatiFatturaSelezionata.setBounds(12, 12, 184, 20);
		pnDatiFattura.add(lblDatiFatturaSelezionata);
		
		// btn plus collapse
		JButton btnPlusCollapseDati = new JButton();
		btnPlusCollapseDati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pnDatiFattura.setSize(300, 400);
				pnDatiFattura.revalidate();
				pnDatiFattura.repaint();
			}
		});
		btnPlusCollapseDati.setBounds(236, 12, 20, 20);
		Image imgPlusCollapseDati = new ImageIcon(this.getClass().getResource("/plusCollapse.png")).getImage();
		btnPlusCollapseDati.setIcon(new ImageIcon(imgPlusCollapseDati));
		pnDatiFattura.add(btnPlusCollapseDati);
		
		// btn minus collapse
		JButton btnMinusCollapseDati = new JButton();
		btnMinusCollapseDati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnDatiFattura.setSize(300, 40);
				pnDatiFattura.revalidate();
				pnDatiFattura.repaint();
			}
		});
		btnMinusCollapseDati.setBounds(268, 12, 20, 20);
		Image imgMinusCollapseDati = new ImageIcon(this.getClass().getResource("/minusCollapse.png")).getImage();
		btnMinusCollapseDati.setIcon(new ImageIcon(imgMinusCollapseDati));
		pnDatiFattura.add(btnMinusCollapseDati);
		
		// separatore testata - dati
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(12, 47, 276, 2);
		pnDatiFattura.add(separator);
		
		
		
		
		
		leftPanel.add(pnDatiFattura);
		
		JLabel lblNumero = new JLabel("Numero");
		lblNumero.setForeground(new Color(255, 255, 255));
		lblNumero.setBounds(12, 70, 70, 15);
		pnDatiFattura.add(lblNumero);
		
		tfDatiFatturaNumero = new JTextField();
		tfDatiFatturaNumero.setBackground(new Color(224, 255, 255));
		tfDatiFatturaNumero.setBounds(81, 68, 43, 20);
		pnDatiFattura.add(tfDatiFatturaNumero);
		tfDatiFatturaNumero.setColumns(10);
		
		JLabel lblData = new JLabel("Data");
		lblData.setForeground(new Color(255, 255, 255));
		lblData.setBounds(142, 70, 43, 15);
		pnDatiFattura.add(lblData);
		
		tfDatiFatturaData = new JTextField();
		tfDatiFatturaData.setBackground(new Color(224, 255, 255));
		tfDatiFatturaData.setBounds(192, 68, 96, 20);
		pnDatiFattura.add(tfDatiFatturaData);
		tfDatiFatturaData.setColumns(10);
		
		JLabel lblIndirizzoImmobile = new JLabel("Indirizzo");
		lblIndirizzoImmobile.setForeground(new Color(255, 255, 255));
		lblIndirizzoImmobile.setBounds(12, 110, 139, 15);
		pnDatiFattura.add(lblIndirizzoImmobile);
		
		JTextField tfDatiFatturaIndirizzo = new JTextField();
		tfDatiFatturaIndirizzo.setBackground(new Color(224, 255, 255));
		tfDatiFatturaIndirizzo.setBounds(142, 110, 146, 20);
		pnDatiFattura.add(tfDatiFatturaIndirizzo);
		tfDatiFatturaIndirizzo.setColumns(10);
		
		JLabel lblTipologia = new JLabel("Tipologia");
		lblTipologia.setForeground(new Color(255, 255, 255));
		lblTipologia.setBounds(12, 150, 70, 15);
		pnDatiFattura.add(lblTipologia);
		
		cbDatiFatturaTipologia = new JComboBox<String>();
		cbDatiFatturaTipologia.setBackground(new Color(224, 255, 255));
		cbDatiFatturaTipologia.setBounds(142, 150, 146, 20);
		pnDatiFattura.add(cbDatiFatturaTipologia);
		
		JLabel lblImporto = new JLabel("Importo");
		lblImporto.setForeground(new Color(255, 255, 255));
		lblImporto.setBounds(12, 190, 70, 15);
		pnDatiFattura.add(lblImporto);
		
		tfDatiFatturaImporto = new JTextField();
		tfDatiFatturaImporto.setBackground(new Color(224, 255, 255));
		tfDatiFatturaImporto.setBounds(142, 190, 146, 19);
		pnDatiFattura.add(tfDatiFatturaImporto);
		tfDatiFatturaImporto.setColumns(10);
		
		JLabel lblAcquisitore = new JLabel("Acquisitore");
		lblAcquisitore.setForeground(new Color(255, 255, 255));
		lblAcquisitore.setBounds(12, 230, 87, 15);
		pnDatiFattura.add(lblAcquisitore);
		
		cbDatiFatturaAcquisitore = new JComboBox<String>();
		cbDatiFatturaAcquisitore.setBackground(new Color(224, 255, 255));
		cbDatiFatturaAcquisitore.setBounds(142, 230, 146, 20);
		pnDatiFattura.add(cbDatiFatturaAcquisitore);
		
		JLabel lblProvvA = new JLabel("Provv A");
		lblProvvA.setForeground(new Color(255, 255, 255));
		lblProvvA.setBounds(12, 270, 70, 15);
		pnDatiFattura.add(lblProvvA);
		
		tfDatiFatturaProvvA = new JTextField();
		tfDatiFatturaProvvA.setBackground(new Color(224, 255, 255));
		tfDatiFatturaProvvA.setBounds(142, 270, 146, 19);
		pnDatiFattura.add(tfDatiFatturaProvvA);
		tfDatiFatturaProvvA.setColumns(10);
		
		JLabel lblVenditore = new JLabel("Venditore");
		lblVenditore.setForeground(new Color(255, 255, 255));
		lblVenditore.setBounds(12, 310, 70, 15);
		pnDatiFattura.add(lblVenditore);
		
		cbDatiFatturaVenditore = new JComboBox<String>();
		cbDatiFatturaVenditore.setBackground(new Color(224, 255, 255));
		cbDatiFatturaVenditore.setBounds(142, 310, 146, 20);
		pnDatiFattura.add(cbDatiFatturaVenditore);
		
		JLabel lblProvvV = new JLabel("Provv V");
		lblProvvV.setForeground(new Color(255, 255, 255));
		lblProvvV.setBounds(12, 350, 70, 15);
		pnDatiFattura.add(lblProvvV);
		
		tfDatiFatturaProvvV = new JTextField();
		tfDatiFatturaProvvV.setBackground(new Color(224, 255, 255));
		tfDatiFatturaProvvV.setBounds(142, 350, 146, 19);
		pnDatiFattura.add(tfDatiFatturaProvvV);
		tfDatiFatturaProvvV.setColumns(10);
		
		leftPanel.setPreferredSize(new Dimension(350, 400));
		add(leftPanel, BorderLayout.LINE_START);
		
		
		
		
		
		

	}
}



class ImagePanel extends JPanel {

	  private Image img;

	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImagePanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }

	}

