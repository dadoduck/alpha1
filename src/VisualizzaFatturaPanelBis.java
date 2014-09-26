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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

import utility.AlphaTableRender;

import javax.swing.JSlider;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class VisualizzaFatturaPanelBis extends JPanel {
	
	Connection connection = null;
	
	private JTable table;
	private JScrollPane scrollPane;
	
	private JPanel headerPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private ImagePanel pnDatiFattura;
	private ImagePanel pnCheckboxFattura;
	private ImagePanel pnSearchForImportFattura;
	
	private JTextField tfDatiFatturaNumero;
	private JTextField tfDatiFatturaData;
	private JComboBox<String> cbDatiFatturaTipologia;
	private JTextField tfDatiFatturaImporto;
	private JComboBox<String> cbDatiFatturaAcquisitore;
	private JTextField tfDatiFatturaProvvA;
	private JComboBox<String> cbDatiFatturaVenditore;
	private JTextField tfDatiFatturaProvvV;
	
	private boolean boolData = true, boolIndirizzo = true, boolTipologia = true, boolImporto = true, boolAcquisitore = true, boolProvvA = true, boolVenditore = true, boolProvvV = true;
	private String[] strNameColumns;
	
	private JCheckBox chckbxData;
	private JCheckBox chckbxIndirizzo;
	private JCheckBox chckbxTipologia;
	private JCheckBox chckbxImporto;
	private JCheckBox chckbxAcquisitore;
	private JCheckBox chckbxProvvA;
	private JCheckBox chckbxVenditore;
	private JCheckBox chckbxProvvV;
	private JSlider sliderMinimo;
	private JSlider sliderMassimo;
	
//	Valori JSlider 
//	intervallo di consistenza		[ minImporto ----- minSlider ----- maxSlider ----- maxImporto ]
	private double minImporto = 0;
	private double maxImporto = 0;
	private int minSlider = 0;
	private int maxSlider = 0;
	private JLabel lblMinimo;
	private JLabel lblMassimo;
	private JTextField tfDatiFatturaIndirizzo;
	
	/**
	 * Create the panel.
	 */
	public VisualizzaFatturaPanelBis() {
		
//		connessione al database
		connection = sqliteConnection.dbConnector();
		
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
		pnDatiFattura.setBounds(20, 20, 300, 430);
		
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
				pnDatiFattura.setSize(300, 430);
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
		
		// dati
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
		
		tfDatiFatturaIndirizzo = new JTextField();
		tfDatiFatturaIndirizzo.setBackground(new Color(224, 255, 255));
		tfDatiFatturaIndirizzo.setBounds(142, 110, 146, 20);
		pnDatiFattura.add(tfDatiFatturaIndirizzo);
		tfDatiFatturaIndirizzo.setColumns(10);
		
		JLabel lblTipologia = new JLabel("Tipologia");
		lblTipologia.setForeground(new Color(255, 255, 255));
		lblTipologia.setBounds(12, 150, 70, 15);
		pnDatiFattura.add(lblTipologia);
		
		cbDatiFatturaTipologia = new JComboBox<String>();
		fillComboBox(cbDatiFatturaTipologia, "Tipo", "Tipologia");
		cbDatiFatturaTipologia.setBackground(new Color(224, 255, 255));
		cbDatiFatturaTipologia.setBounds(142, 150, 146, 20);
		pnDatiFattura.add(cbDatiFatturaTipologia);
		
		JLabel lblImporto = new JLabel("Importo");
		lblImporto.setForeground(new Color(255, 255, 255));
		lblImporto.setBounds(12, 190, 70, 15);
		pnDatiFattura.add(lblImporto);
		
		tfDatiFatturaImporto = new JTextField();
		tfDatiFatturaImporto.setBackground(new Color(224, 255, 255));
		tfDatiFatturaImporto.setBounds(142, 190, 146, 20);
		pnDatiFattura.add(tfDatiFatturaImporto);
		tfDatiFatturaImporto.setColumns(10);
		
		JLabel lblAcquisitore = new JLabel("Acquisitore");
		lblAcquisitore.setForeground(new Color(255, 255, 255));
		lblAcquisitore.setBounds(12, 230, 87, 15);
		pnDatiFattura.add(lblAcquisitore);
		
		cbDatiFatturaAcquisitore = new JComboBox<String>();
		fillComboBox(cbDatiFatturaAcquisitore, "Cognome", "Personale");
		cbDatiFatturaAcquisitore.setBackground(new Color(224, 255, 255));
		cbDatiFatturaAcquisitore.setBounds(142, 230, 146, 20);
		pnDatiFattura.add(cbDatiFatturaAcquisitore);
		
		JLabel lblProvvA = new JLabel("Provv A");
		lblProvvA.setForeground(new Color(255, 255, 255));
		lblProvvA.setBounds(12, 270, 70, 15);
		pnDatiFattura.add(lblProvvA);
		
		tfDatiFatturaProvvA = new JTextField();
		tfDatiFatturaProvvA.setBackground(new Color(224, 255, 255));
		tfDatiFatturaProvvA.setBounds(142, 270, 146, 20);
		pnDatiFattura.add(tfDatiFatturaProvvA);
		tfDatiFatturaProvvA.setColumns(10);
		
		JLabel lblVenditore = new JLabel("Venditore");
		lblVenditore.setForeground(new Color(255, 255, 255));
		lblVenditore.setBounds(12, 310, 70, 15);
		pnDatiFattura.add(lblVenditore);
		
		cbDatiFatturaVenditore = new JComboBox<String>();
		fillComboBox(cbDatiFatturaVenditore, "Cognome", "Personale");
		cbDatiFatturaVenditore.setBackground(new Color(224, 255, 255));
		cbDatiFatturaVenditore.setBounds(142, 310, 146, 20);
		pnDatiFattura.add(cbDatiFatturaVenditore);
		
		JLabel lblProvvV = new JLabel("Provv V");
		lblProvvV.setForeground(new Color(255, 255, 255));
		lblProvvV.setBounds(12, 350, 70, 15);
		pnDatiFattura.add(lblProvvV);
		
		tfDatiFatturaProvvV = new JTextField();
		tfDatiFatturaProvvV.setBackground(new Color(224, 255, 255));
		tfDatiFatturaProvvV.setBounds(142, 350, 146, 20);
		pnDatiFattura.add(tfDatiFatturaProvvV);
		tfDatiFatturaProvvV.setColumns(10);
		
		// btn update e svuota campi
		JButton btnModificaDatiFattura = new JButton("Modifica");
		btnModificaDatiFattura.setBounds(12, 398, 128, 20);
		Image imgModificaDati = new ImageIcon(this.getClass().getResource("/updateDatiFattura.png")).getImage();
		btnModificaDatiFattura.setIcon(new ImageIcon(imgModificaDati));
		pnDatiFattura.add(btnModificaDatiFattura);
		
		JButton btnClearDatiFattura = new JButton("Clear");
		btnClearDatiFattura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tfDatiFatturaNumero.setText("");
				tfDatiFatturaData.setText("");
				tfDatiFatturaIndirizzo.setText("");
				cbDatiFatturaTipologia.setSelectedIndex(0);
				tfDatiFatturaImporto.setText("");
				cbDatiFatturaAcquisitore.setSelectedIndex(0);
				tfDatiFatturaProvvA.setText("");
				cbDatiFatturaVenditore.setSelectedIndex(0);
				tfDatiFatturaProvvV.setText("");
			}
		});
		btnClearDatiFattura.setBounds(160, 398, 128, 20);
		Image imgClearDati = new ImageIcon(this.getClass().getResource("/clearDatiFattura.png")).getImage();
		btnClearDatiFattura.setIcon(new ImageIcon(imgClearDati));
		pnDatiFattura.add(btnClearDatiFattura);
		
		
		leftPanel.add(pnDatiFattura);
		
		leftPanel.setPreferredSize(new Dimension(350, 400));
		add(leftPanel, BorderLayout.LINE_START);
		
		
		
//		###	CENTER			#############################################################################################################
				
		//		TABLE
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 1276, 432);
		add(scrollPane);
		
		LoadTable();
		
		table.setRowHeight(25);
		
//		set field by click row
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int row = table.getSelectedRow();
					String numeroFattura = (table.getModel().getValueAt(row, 0)).toString();
					String query = "select * from Fattura where NumeroFattura='"+numeroFattura+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					while(rs.next()) {
						tfDatiFatturaNumero.setText(rs.getString("NumeroFattura").toString());
						tfDatiFatturaData.setText(rs.getString("DataFattura"));
						tfDatiFatturaIndirizzo.setText(rs.getString("IndirizzoImmobile"));
						
						cbDatiFatturaTipologia.setSelectedItem((String)rs.getString("Tipologia"));
						tfDatiFatturaImporto.setText(rs.getString("Importo"));
						
						cbDatiFatturaAcquisitore.setSelectedItem((String)rs.getString("Acquisitore"));
						tfDatiFatturaProvvA.setText(rs.getString("ProvvigioniAcquisitore"));
						
						cbDatiFatturaVenditore.setSelectedItem((String)rs.getString("Venditore"));
						tfDatiFatturaProvvV.setText(rs.getString("ProvvigioniVenditore"));
					}
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				} 
			}
		});
		
		scrollPane.setViewportView(table);
		
		
		
		
//		###	RIGHT			#############################################################################################################
		
		rightPanel = new JPanel();
		rightPanel.setLayout(null);
		
		// *************** pannello con i dati della fattura
		
		pnCheckboxFattura = new ImagePanel(new ImageIcon("images/sfondo.jpg").getImage());
		pnCheckboxFattura.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		pnCheckboxFattura.setLayout(null);
		pnCheckboxFattura.setBounds(20, 20, 300, 260);
		
		// titolo pannello
		JLabel lblCheckboxFattura = new JLabel("Colonne visualizzate");
		lblCheckboxFattura.setForeground(new Color(255, 255, 255));
		lblCheckboxFattura.setFont(new Font("Ubuntu", Font.BOLD, 15));
		lblCheckboxFattura.setBounds(12, 12, 184, 20);
		pnCheckboxFattura.add(lblCheckboxFattura);
		
		// btn plus collapse
		JButton btnPlusCollapseCheck = new JButton();
		btnPlusCollapseCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pnCheckboxFattura.setSize(300, 260);
				pnSearchForImportFattura.setBounds(20, pnCheckboxFattura.getHeight() + 40, 300, pnSearchForImportFattura.getHeight());
				rightPanel.revalidate();
				rightPanel.repaint();
			}
		});
		btnPlusCollapseCheck.setBounds(236, 12, 20, 20);
		Image imgPlusCollapseCheck = new ImageIcon(this.getClass().getResource("/plusCollapse.png")).getImage();
		btnPlusCollapseCheck.setIcon(new ImageIcon(imgPlusCollapseCheck));
		pnCheckboxFattura.add(btnPlusCollapseCheck);
		
		// btn minus collapse
		JButton btnMinusCollapseCheck = new JButton();
		btnMinusCollapseCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnCheckboxFattura.setSize(300, 40);
				pnSearchForImportFattura.setBounds(20, pnCheckboxFattura.getHeight() + 40, 300, pnSearchForImportFattura.getHeight());
				rightPanel.revalidate();
				rightPanel.repaint();
			}
		});
		btnMinusCollapseCheck.setBounds(268, 12, 20, 20);
		Image imgMinusCollapseCheck = new ImageIcon(this.getClass().getResource("/minusCollapse.png")).getImage();
		btnMinusCollapseCheck.setIcon(new ImageIcon(imgMinusCollapseCheck));
		pnCheckboxFattura.add(btnMinusCollapseCheck);
		
		// separatore testata - dati
		JSeparator separatorCollapseCheck = new JSeparator();
		separatorCollapseCheck.setBackground(new Color(255, 255, 255));
		separatorCollapseCheck.setBounds(12, 47, 276, 2);
		pnCheckboxFattura.add(separatorCollapseCheck);
		

		rightPanel.add(pnCheckboxFattura);
		
		chckbxData = new JCheckBox("Data", true);
		chckbxData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolData = chckbxData.isSelected();
				setColumnsName();
				runCheck();
			}
		});
		chckbxData.setForeground(new Color(255, 255, 255));
		chckbxData.setBounds(12, 70, 129, 20);
		chckbxData.setOpaque(false);
		pnCheckboxFattura.add(chckbxData);
		
		chckbxIndirizzo = new JCheckBox("Indirizzo", true);
		chckbxIndirizzo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolIndirizzo = chckbxIndirizzo.isSelected();
				setColumnsName();
				runCheck();
			}
		});
		chckbxIndirizzo.setForeground(new Color(255, 255, 255));
		chckbxIndirizzo.setBounds(159, 70, 129, 20);
		chckbxIndirizzo.setOpaque(false);
		pnCheckboxFattura.add(chckbxIndirizzo);
		
		chckbxTipologia = new JCheckBox("Tipologia", true);
		chckbxTipologia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolTipologia = chckbxTipologia.isSelected();
				setColumnsName();
				runCheck();
			}
		});
		chckbxTipologia.setForeground(new Color(255, 255, 255));
		chckbxTipologia.setBounds(12, 120, 129, 20);
		chckbxTipologia.setOpaque(false);
		pnCheckboxFattura.add(chckbxTipologia);
		
		chckbxImporto = new JCheckBox("Importo", true);
		chckbxImporto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolImporto = chckbxImporto.isSelected();
				setColumnsName();
				runCheck();
			}
		});
		chckbxImporto.setForeground(new Color(255, 255, 255));
		chckbxImporto.setBounds(159, 120, 129, 20);
		chckbxImporto.setOpaque(false);
		pnCheckboxFattura.add(chckbxImporto);
		
		chckbxAcquisitore = new JCheckBox("Acquisitore", true);
		chckbxAcquisitore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolAcquisitore = chckbxAcquisitore.isSelected();
				setColumnsName();
				runCheck();
			}
		});
		chckbxAcquisitore.setForeground(new Color(255, 255, 255));
		chckbxAcquisitore.setBounds(12, 170, 129, 20);
		chckbxAcquisitore.setOpaque(false);
		pnCheckboxFattura.add(chckbxAcquisitore);
		
		chckbxProvvA = new JCheckBox("Provv A", true);
		chckbxProvvA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolProvvA = chckbxProvvA.isSelected();
				setColumnsName();
				runCheck();
			}
		});
		chckbxProvvA.setForeground(new Color(255, 255, 255));
		chckbxProvvA.setBounds(159, 170, 129, 20);
		chckbxProvvA.setOpaque(false);
		pnCheckboxFattura.add(chckbxProvvA);
		
		chckbxVenditore = new JCheckBox("Venditore", true);
		chckbxVenditore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolVenditore = chckbxVenditore.isSelected();
				setColumnsName();
				runCheck();
			}
		});
		chckbxVenditore.setForeground(new Color(255, 255, 255));
		chckbxVenditore.setBounds(12, 220, 129, 20);
		chckbxVenditore.setOpaque(false);
		pnCheckboxFattura.add(chckbxVenditore);
		
		chckbxProvvV = new JCheckBox("Provv V", true);
		chckbxProvvV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolProvvV = chckbxProvvV.isSelected();
				setColumnsName();
				runCheck();
			}
		});
		chckbxProvvV.setForeground(new Color(255, 255, 255));
		chckbxProvvV.setBounds(159, 220, 129, 20);
		chckbxProvvV.setOpaque(false);
		pnCheckboxFattura.add(chckbxProvvV);
		
		
		
		// *************** pannello selezione importo
		
		pnSearchForImportFattura = new ImagePanel(new ImageIcon("images/sfondo.jpg").getImage());
		pnSearchForImportFattura.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		pnSearchForImportFattura.setLayout(null);
		pnSearchForImportFattura.setBounds(20, 300, 300, 245);  // da controllare
				
		// titolo pannello
		JLabel lblSearchForImportFattura = new JLabel("Filtra per importo");
		lblSearchForImportFattura.setForeground(new Color(255, 255, 255));
		lblSearchForImportFattura.setFont(new Font("Ubuntu", Font.BOLD, 15));
		lblSearchForImportFattura.setBounds(12, 12, 184, 20);
		pnSearchForImportFattura.add(lblSearchForImportFattura);
		
		// btn plus collapse
		JButton btnPlusCollapseSearch = new JButton();
		btnPlusCollapseSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pnSearchForImportFattura.setSize(300, 245);
				pnSearchForImportFattura.revalidate();
				pnSearchForImportFattura.repaint();
			}
		});
		btnPlusCollapseSearch.setBounds(236, 12, 20, 20);
		Image imgPlusCollapseSearch = new ImageIcon(this.getClass().getResource("/plusCollapse.png")).getImage();
		btnPlusCollapseSearch.setIcon(new ImageIcon(imgPlusCollapseSearch));
		pnSearchForImportFattura.add(btnPlusCollapseSearch);
		
		// btn minus collapse
		JButton btnMinusCollapseSearch = new JButton();
		btnMinusCollapseSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnSearchForImportFattura.setSize(300, 40);
				pnSearchForImportFattura.revalidate();
				pnSearchForImportFattura.repaint();
			}
		});
		btnMinusCollapseSearch.setBounds(268, 12, 20, 20);
		Image imgMinusCollapseSearch = new ImageIcon(this.getClass().getResource("/minusCollapse.png")).getImage();
		btnMinusCollapseSearch.setIcon(new ImageIcon(imgMinusCollapseSearch));
		pnSearchForImportFattura.add(btnMinusCollapseSearch);
		
		// separatore testata - dati
		JSeparator separatorCollapseSearch = new JSeparator();
		separatorCollapseSearch.setBackground(new Color(255, 255, 255));
		separatorCollapseSearch.setBounds(12, 47, 276, 2);
		pnSearchForImportFattura.add(separatorCollapseSearch);
		

		rightPanel.add(pnSearchForImportFattura);
		
		// slider selezione minimo e massimo
		lblMinimo = new JLabel("Minimo:   ");
		lblMinimo.setForeground(new Color(255, 255, 255));
		lblMinimo.setBounds(36, 70, 160, 15);
		pnSearchForImportFattura.add(lblMinimo);
		
		sliderMinimo = new JSlider();
		sliderMinimo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				minSlider=sliderMinimo.getValue();
				sliderMassimo.setMinimum(sliderMinimo.getValue());
				sliderMassimo.repaint();
				setColumnsName();
				printTableMinMax();
				System.out.println(minSlider + "   " + maxSlider);
			}
		});
		sliderMinimo.setBounds(36, 99, 200, 16);
		sliderMinimo.setOpaque(false);
		pnSearchForImportFattura.add(sliderMinimo);
		
		lblMassimo = new JLabel("Massimo:   ");
		lblMassimo.setForeground(new Color(255, 255, 255));
		lblMassimo.setBounds(36, 162, 160, 15);
		pnSearchForImportFattura.add(lblMassimo);
		
		sliderMassimo = new JSlider();
		sliderMassimo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				maxSlider=sliderMassimo.getValue();
				sliderMinimo.setMaximum(sliderMassimo.getValue());
				sliderMinimo.repaint();
				setColumnsName();
				printTableMinMax();
				System.out.println(minSlider + "   " + maxSlider);
			}
		});
		sliderMassimo.setBounds(36, 189, 200, 16);
		sliderMassimo.setOpaque(false);
		pnSearchForImportFattura.add(sliderMassimo);
		
		setMinMaxImporto();
		
		sliderMinimo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblMinimo.setText("Minimo:   " + sliderMinimo.getValue());
			}
		});
		
		sliderMassimo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblMassimo.setText("Massimo:   " + sliderMassimo.getValue());
			}
		});
		
		
		rightPanel.setPreferredSize(new Dimension(350, 400));
		add(rightPanel, BorderLayout.LINE_END);
		
		
		
	}
	
	
	
//	********************************************	METODI DI SUPPORTO	 ******************************************
	
	/*
	 * ritorna il numero di colonne attive
	 * da controllare  ->  SuPerFLuo
	 * sostituire con setColumnsName()
	 */
	private String[] getColumnNames() {
		int i=1; int j=1; 
		if(boolData) i++;
		if(boolIndirizzo) i++;
		if(boolTipologia) i++;
		if(boolImporto) i++;
		if(boolAcquisitore) i++;
		if(boolProvvA) i++;
		if(boolVenditore) i++;
		if(boolProvvV) i++;
			
		String[] columnNames = new String[i];
		columnNames[0]="NumeroFattura";
		if(boolData) {
			columnNames[j]="DataFattura";
			j++;
		}
		if(boolIndirizzo) {
			columnNames[j]="IndirizzoImmobile";
			j++;
		}
		if(boolTipologia) {
			columnNames[j]="Tipologia";
			j++;
		}
		if(boolImporto) {
			columnNames[j]="Importo";
			j++;
		}
		if(boolAcquisitore) { 
			columnNames[j]="Acquisitore";
			j++;
		}
		if(boolProvvA) { 
			columnNames[j]="ProvvigioniAcquisitore";
			j++;
		}
		if(boolVenditore) {
			columnNames[j]="Venditore";
			j++;
		}
		if(boolProvvV) {
			columnNames[j]="ProvvigioniVenditore";
			j++;
		}
		return columnNames;
	}
	
	/*
	 * get numero colonne selezionate che
	 * compongono la tabella
	 */
	private int getNumColumnSelected() {
		int count=0;
		if(boolData) count++;
		if(boolIndirizzo) count++;
		if(boolTipologia) count++;
		if(boolImporto) count++;
		if(boolAcquisitore) count++;
		if(boolProvvA) count++;
		if(boolVenditore) count++;
		if(boolProvvV) count++;
		return count;
	}
	
	/*
	 * creazione tabella con le colonne
	 * selezionate
	 */
	private void setColumnsName() {
		strNameColumns = new String[getNumColumnSelected()+1];
		//System.out.println(getNumColumnSelected());
		strNameColumns[0] = "NumeroFattura";
		int index = 1;
		if(boolData) {
			strNameColumns[index]="DataFattura";
			index++;
		}
		if(boolIndirizzo) {
			strNameColumns[index]="IndirizzoImmobile";
			index++;
		}
		if(boolTipologia) {
			strNameColumns[index]="Tipologia";
			index++;
		}
		if(boolImporto) {
			strNameColumns[index]="Importo";
			index++;
		}
		if(boolAcquisitore) { 
			strNameColumns[index]="Acquisitore";
			index++;
		}
		if(boolProvvA) { 
			strNameColumns[index]="ProvvigioniAcquisitore";
			index++;
		}
		if(boolVenditore) {
			strNameColumns[index]="Venditore";
			index++;
		}
		if(boolProvvV) {
			strNameColumns[index]="ProvvigioniVenditore";
			index++;
		}
		
	}
	
	/*
	 * metodo runCheck comune a tutte le chkbox
	 * funzionalità aggiunte in tutte le chkbx
	 */
	private void runCheck() {
		stampaStatoChkbx(boolData, boolIndirizzo, boolTipologia, boolImporto, boolAcquisitore, boolProvvA, boolVenditore, boolProvvV);
		System.out.println(getNumColumnSelected());
		stampaColonneSelezionate();
		printTable();
	}
	
	/*
	 * metodo test per controllare checkbox selezionate
	 */
	private void stampaStatoChkbx(boolean data, boolean indirizzo, boolean tipologia, boolean importo, boolean acquisitore, boolean provvA, boolean venditore, boolean provvV) {
		System.out.println("Data Fattura: " + data);
		System.out.println("Indirizzo: " + indirizzo);
		System.out.println("Tipologia: " + tipologia);
		System.out.println("Importo: " + importo);
		System.out.println("Acquisitore: " + acquisitore);
		System.out.println("Provvigioni Acquisitore: " + provvA);
		System.out.println("Venditore: " + venditore);
		System.out.println("Provvigioni Venditore: " + provvV);
		System.out.println("################################");
	}
	
	/*
	 * stampa colonne selezionate 
	 */
	private void stampaColonneSelezionate() {
		System.out.println("numero sel -> " + getNumColumnSelected());
		System.out.println("selezionate: ");
		for(int i=0; i<strNameColumns.length; i++) {
			System.out.println(strNameColumns[i]);
		}
	}	
	
	/*
	 * creazione query da selezione
	 */
	private String getQuery() {
		String query = "select NumeroFattura ";
		if(boolData) query+=", DataFattura";
		if(boolIndirizzo) query+=", IndirizzoImmobile";
		if(boolTipologia) query+=", Tipologia";
		if(boolImporto) query+=", Importo";
		if(boolAcquisitore) query+=", Acquisitore";
		if(boolProvvA) query+=", ProvvigioniAcquisitore";
		if(boolVenditore) query+=", Venditore";
		if(boolProvvV) query+=", ProvvigioniVenditore";
		query+=" from Fattura";
		return query;
	}
	
	/*
	 * verifica se la colonna è presente in tabella
	 */
	private boolean isPresent(String colonna) {
		for(int i=0; i<strNameColumns.length; i++)
			if(strNameColumns[i].equals(colonna))
				return true;
		return false;
	}
	
	/*
	 * primo caricamento JTable
	 */
	private void LoadTable() {
		try {
			
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(getColumnNames());
			table = new JTable();
	        table.setModel(model);
			
			//String query = "select * from Fattura";
	        String query = getQuery();
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			String strNumero="", strIndirizzo="", strTipologia="", strAcquisitore="", strVenditore="";
			double dblImporto = 0, dblProvvA = 0, dblProvvV = 0;
			String strDate="";
			
			int i=0;
			
			double totaleImporto = 0, totaleProvvAcq = 0, totaleProvvVen = 0;
			NumberFormat f = new DecimalFormat("#,###.00"); 
			
			while(rs.next()) {
				
				strNumero=rs.getString("NumeroFattura");
				if(!rs.getString("DataFattura").equals(""))
					strDate=rs.getString("DataFattura");
				if(rs.getString("IndirizzoImmobile")!="")
					strIndirizzo=rs.getString("IndirizzoImmobile");
				if(rs.getString("Tipologia")!="")
					strTipologia=rs.getString("Tipologia");
				if(rs.getDouble("Importo")!=0)
					dblImporto=rs.getDouble("Importo");
				if(rs.getString("Acquisitore")!="")
					strAcquisitore=rs.getString("Acquisitore");
				if(rs.getDouble("ProvvigioniAcquisitore")!=0)
					dblProvvA=rs.getDouble("ProvvigioniAcquisitore");
				if(rs.getString("Venditore")!="")
					strVenditore=rs.getString("Venditore");
				if(rs.getDouble("ProvvigioniVenditore")!=0)
					dblProvvV=rs.getDouble("ProvvigioniVenditore");
								
				model.addRow(new Object[]{strNumero, strDate, strIndirizzo, strTipologia, f.format(dblImporto), strAcquisitore, f.format(dblProvvA), strVenditore, f.format(dblProvvV)});
				 
				totaleImporto+=dblImporto;
				totaleProvvAcq+=dblProvvA;
				totaleProvvVen+=dblProvvV;
				i++;
			}
			
			model.addRow(new Object[] {"Totale", "", "", "", f.format(totaleImporto), "", f.format(totaleProvvAcq), "", f.format(totaleProvvVen)});
			
			table.setDefaultRenderer(Object.class, new AlphaTableRender());
			
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * creazione tabella
	 */
	private void printTable() {

		scrollPane.getViewport().remove(table);
		try {
			
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(strNameColumns);
			table = new JTable();
	        table.setModel(model);
	        
	        String query = getQuery();
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			double totaleImporto = 0, totaleProvvAcq = 0, totaleProvvVen = 0;
			NumberFormat f = new DecimalFormat("#,###.00");
			
			while(rs.next()) {
				
				List<Object> row = new ArrayList<Object>();
								
				row.add(rs.getString("NumeroFattura"));
				
				if(isPresent("DataFattura"))
					row.add(rs.getString("DataFattura"));
				if(isPresent("IndirizzoImmobile"))
					row.add(rs.getString("IndirizzoImmobile"));
				if(isPresent("Tipologia"))
					row.add(rs.getString("Tipologia"));
				if(isPresent("Importo")) {
					row.add(f.format(rs.getDouble("Importo")));
					totaleImporto+=rs.getDouble("Importo");
				}
				if(isPresent("Acquisitore"))
					row.add(rs.getString("Acquisitore"));
				if(isPresent("ProvvigioniAcquisitore")) {
					row.add(f.format(rs.getDouble("ProvvigioniAcquisitore")));
					totaleProvvAcq+=rs.getDouble("ProvvigioniAcquisitore");
				}
				if(isPresent("Venditore"))
					row.add(rs.getString("Venditore"));
				if(isPresent("ProvvigioniVenditore")) {
					row.add(f.format(rs.getDouble("ProvvigioniVenditore")));
					totaleProvvVen+=rs.getDouble("ProvvigioniVenditore");
				}
				
				model.addRow(row.toArray());
				
				
			}
			
			List<Object> finalRow = new ArrayList<Object>();
			
			finalRow.add("TOTALE");
			if(isPresent("DataFattura"))
				finalRow.add("");
			if(isPresent("IndirizzoImmobile"))
				finalRow.add("");
			if(isPresent("Tipologia"))
				finalRow.add("");
			if(isPresent("Importo"))
				finalRow.add(f.format(totaleImporto));
			if(isPresent("Acquisitore"))
				finalRow.add("");
			if(isPresent("ProvvigioniAcquisitore"))
				finalRow.add(f.format(totaleProvvAcq));
			if(isPresent("Venditore"))
				finalRow.add("");
			if(isPresent("ProvvigioniVenditore"))
				finalRow.add(f.format(totaleProvvVen));
			
			model.addRow(finalRow.toArray());
			
			table.setDefaultRenderer(Object.class, new AlphaTableRender());
			table.setRowHeight(25);
			
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		scrollPane.setViewportView(table);
        
	}
	
	/*
	 * Overloading creazione tabella
	 * parametro query 
	 * con clausola where sulla select
	 */
	private void printTableMinMax(){
		scrollPane.getViewport().remove(table);
		try {
			
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(strNameColumns);
			table = new JTable();
	        table.setModel(model);
	        
	        String query = getQueryMinMax();
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			double totaleImporto = 0, totaleProvvAcq = 0, totaleProvvVen = 0;
			NumberFormat f = new DecimalFormat("#,###.00");
			
			while(rs.next()) {
				
				List<Object> row = new ArrayList<Object>();
								
				row.add(rs.getString("NumeroFattura"));
				
				if(isPresent("DataFattura"))
					row.add(rs.getString("DataFattura"));
				if(isPresent("IndirizzoImmobile"))
					row.add(rs.getString("IndirizzoImmobile"));
				if(isPresent("Tipologia"))
					row.add(rs.getString("Tipologia"));
				if(isPresent("Importo")) {
					row.add(f.format(rs.getDouble("Importo")));
					totaleImporto+=rs.getDouble("Importo");
				}
				if(isPresent("Acquisitore"))
					row.add(rs.getString("Acquisitore"));
				if(isPresent("ProvvigioniAcquisitore")) {
					row.add(f.format(rs.getDouble("ProvvigioniAcquisitore")));
					totaleProvvAcq+=rs.getDouble("ProvvigioniAcquisitore");
				}
				if(isPresent("Venditore"))
					row.add(rs.getString("Venditore"));
				if(isPresent("ProvvigioniVenditore")) {
					row.add(f.format(rs.getDouble("ProvvigioniVenditore")));
					totaleProvvVen+=rs.getDouble("ProvvigioniVenditore");
				}
				
				model.addRow(row.toArray());
				
				
			}
			
			List<Object> finalRow = new ArrayList<Object>();
			
			finalRow.add("TOTALE");
			if(isPresent("DataFattura"))
				finalRow.add("");
			if(isPresent("IndirizzoImmobile"))
				finalRow.add("");
			if(isPresent("Tipologia"))
				finalRow.add("");
			if(isPresent("Importo"))
				finalRow.add(f.format(totaleImporto));
			if(isPresent("Acquisitore"))
				finalRow.add("");
			if(isPresent("ProvvigioniAcquisitore"))
				finalRow.add(f.format(totaleProvvAcq));
			if(isPresent("Venditore"))
				finalRow.add("");
			if(isPresent("ProvvigioniVenditore"))
				finalRow.add(f.format(totaleProvvVen));
			
			model.addRow(finalRow.toArray());
			
			table.setDefaultRenderer(Object.class, new AlphaTableRender());
			table.setRowHeight(25);
			
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		scrollPane.setViewportView(table);
        
	}
	
	/*
	 * ritorna query con clausola 
	 * su importo
	 */
	private String getQueryMinMax() {
		String query = "select NumeroFattura ";
		if(boolData) query+=", DataFattura";
		if(boolIndirizzo) query+=", IndirizzoImmobile";
		if(boolTipologia) query+=", Tipologia";
		if(boolImporto) query+=", Importo";
		if(boolAcquisitore) query+=", Acquisitore";
		if(boolProvvA) query+=", ProvvigioniAcquisitore";
		if(boolVenditore) query+=", Venditore";
		if(boolProvvV) query+=", ProvvigioniVenditore";
		query+=" from Fattura where Importo >= " + minSlider + " and Importo <= " + maxSlider;
		return query;
	}
	
	/*
	 * set importo minimo ed importo massimo 
	 * presente in db
	 */
	private void setMinMaxImporto() {
		try {
			String query = "select Importo from Fattura";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			minImporto = rs.getDouble("Importo");
			maxImporto = rs.getDouble("Importo");
			while(rs.next()) {
				if(minImporto > rs.getDouble("Importo"))
					minImporto = rs.getDouble("Importo");
				if(maxImporto < rs.getDouble("Importo"))
					maxImporto = rs.getDouble("Importo");
			}
			System.out.println("Minimo: " + minImporto);
			System.out.println("Massimo: " + maxImporto);
			
			sliderMinimo.setMinimum((int)minImporto);
			sliderMinimo.setMaximum((int)maxImporto);
			
			sliderMassimo.setMaximum((int)maxImporto);
			sliderMassimo.setMinimum((int)minImporto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * carica valori da database in combobox
	 */
	private void fillComboBox(JComboBox<String> comboBox, String parameterColumn, String nameTable) {
		comboBox.addItem("..:: Scegli ::..");
		try {
			String query = "select * from "+nameTable;
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				comboBox.addItem(rs.getString(parameterColumn));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}




/*
 * pannello con sfondo
 */
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

