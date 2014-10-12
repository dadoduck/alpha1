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
import javax.swing.border.EmptyBorder;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

import utility.AlphaPanel;
import utility.AlphaTableRender;

import javax.swing.JSlider;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.Printable;
import java.awt.SystemColor;
import java.io.File;

import javax.swing.SwingConstants;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class GestioneFatture extends JPanel {
	
	Connection connection = null;
	
	private Image imgContainer;
	
	private JTable table;
	private JScrollPane scrollPane;
	
	private JPanel headerPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private boolean closedLeftPanel = false;
	JButton btnCollapseLeftPanel;
	
	private boolean closedRightPanel = false;
	JButton btnCollapseRightPanel;
	
	private AlphaPanel pnDatiFattura;
	private AlphaPanel pnCheckboxFattura;
	private AlphaPanel pnSearchForImportFattura;
	private AlphaPanel pnInsertFattura;
	
	private JTextField tfDatiFatturaNumero;
	private ObservingTextField tfDatiFatturaData;
	private JTextField tfDatiFatturaIndirizzo;
	private JComboBox<String> cbDatiFatturaTipologia;
	private JTextField tfDatiFatturaImporto;
	private JComboBox<String> cbDatiFatturaAcquisitore;
	private JTextField tfDatiFatturaProvvA;
	private JComboBox<String> cbDatiFatturaVenditore;
	private JTextField tfDatiFatturaProvvV;
	
	private JTextField tfDatiFatturaNumeroIns;
	private ObservingTextField tfDatiFatturaDataIns;
	private JTextField tfDatiFatturaIndirizzoIns;
	private JComboBox<String> cbDatiFatturaTipologiaIns;
	private JTextField tfDatiFatturaImportoIns;
	private JComboBox<String> cbDatiFatturaAcquisitoreIns;
	private JTextField tfDatiFatturaProvvAIns;
	private JComboBox<String> cbDatiFatturaVenditoreIns;
	private JTextField tfDatiFatturaProvvVIns;
	
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
	
//	valori ordinamento table by click Header
	private int orderByType=0;
	private String orderByNameColumn="";
	private JButton btnExportResult;
	
	/**
	 * Create the panel.
	 */
	public GestioneFatture() {
		
//		connessione al database
		connection = sqliteConnection.dbConnector();
		
		setLayout(new BorderLayout(0, 0));
		
		this.imgContainer = new ImageIcon("images/sfondoChiaro.jpg").getImage();
		
//	###	HEADER			#############################################################################################################
		
		headerPanel = new JPanel();
		
		headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 70));
		headerPanel.setOpaque(false);
		
		JLabel lblVisualizzazioneEGestione = new JLabel("Gestione Fatture");
		lblVisualizzazioneEGestione.setForeground(Color.DARK_GRAY);
		lblVisualizzazioneEGestione.setVerticalAlignment(SwingConstants.BOTTOM);
		lblVisualizzazioneEGestione.setFont(new Font("Ubuntu", Font.BOLD, 22));
		lblVisualizzazioneEGestione.setBorder(new EmptyBorder(15, 0, 0, 0) );
		headerPanel.add(lblVisualizzazioneEGestione);
		
		add(headerPanel, BorderLayout.PAGE_START);
		
		
		
//	###	LEFT			#############################################################################################################
		
		leftPanel = new JPanel();
		leftPanel.setLayout(null);
		leftPanel.setOpaque(false);
		
		// ********** pannello Modifica fattura
		
		pnDatiFattura = new AlphaPanel("images/sfondo.jpg", "Modifica Fattura Selezionata", 30, 0, 300, 430);
		
		// button collapse Minus pnDatiFattura 
		ActionListener pnDatiFatturaLisMinus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnDatiFattura.setSize(300, 40);
				pnInsertFattura.setBounds(30, pnDatiFattura.getHeight() + 20, 300, pnInsertFattura.getHeight());
				pnDatiFattura.revalidate();
				pnDatiFattura.repaint();
			}
		};
		pnDatiFattura.setBtnCollapseMinus(pnDatiFatturaLisMinus);
		
		// button collapse Minus pnDatiFattura
		ActionListener pnDatiFatturaLisPlus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnDatiFattura.setSize(300, 430);
				pnInsertFattura.setBounds(30, pnDatiFattura.getHeight() + 20, 300, 40);
				pnDatiFattura.revalidate();
				pnDatiFattura.repaint();
			}
		};
		pnDatiFattura.setBtnCollapsePlus(pnDatiFatturaLisPlus);
		
		// dati
		JLabel lblNumero = new JLabel("Numero");
		lblNumero.setForeground(new Color(255, 255, 255));
		lblNumero.setBounds(12, 70, 70, 15);
		pnDatiFattura.add(lblNumero);
		
		tfDatiFatturaNumero = new JTextField();
		tfDatiFatturaNumero.setEditable(false);
		tfDatiFatturaNumero.setBackground(SystemColor.controlHighlight);
		tfDatiFatturaNumero.setBounds(81, 68, 43, 20);
		pnDatiFattura.add(tfDatiFatturaNumero);
		tfDatiFatturaNumero.setColumns(10);
		
		JButton btnDataModifica = new JButton("Data");
		btnDataModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String lang = null;
				final Locale locale = getLocale(lang);
				DatePicker dp = new DatePicker(tfDatiFatturaData, locale);
				Date selectedDate = dp.parseDate(tfDatiFatturaData.getText());
				dp.setSelectedDate(selectedDate);
				dp.start(tfDatiFatturaData);
			}
		});
		btnDataModifica.setForeground(new Color(255, 255, 255));
		btnDataModifica.setBounds(142, 68, 55, 21);
		pnDatiFattura.add(btnDataModifica);
		
		tfDatiFatturaData = new ObservingTextField();
		tfDatiFatturaData.setEditable(false);
		tfDatiFatturaData.setBackground(new Color(224, 255, 255));
		tfDatiFatturaData.setBounds(206, 68, 82, 20);
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
		btnModificaDatiFattura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "Update Fattura set " + 
									"DataFattura='"+tfDatiFatturaData.getText()+"', IndirizzoImmobile='"+tfDatiFatturaIndirizzo.getText() + 
									"', Tipologia='"+(String)cbDatiFatturaTipologia.getSelectedItem() + "', Importo='"+tfDatiFatturaImporto.getText() + 
									"', Acquisitore='"+(String)cbDatiFatturaAcquisitore.getSelectedItem() + "', ProvvigioniAcquisitore='"+tfDatiFatturaProvvA.getText() +
									"', Venditore='"+(String)cbDatiFatturaVenditore.getSelectedItem() + "', ProvvigioniVenditore='"+tfDatiFatturaProvvV.getText() +
									"' where NumeroFattura='"+tfDatiFatturaNumero.getText()+"'";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Fattura aggiornata correttamente");
					pst.close();
					setColumnsName();
					runCheck();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
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
		
		
		
		// ********** pannello Inserimento fattura
		
		pnInsertFattura = new AlphaPanel("images/sfondo.jpg", "Inserimento nuova Fattura", 30, 450, 300, 40);
		
		// button collapse Minus pnInsertFattura 
		ActionListener pnInsertFatturaLisMinus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnInsertFattura.setSize(300, 40);
				pnInsertFattura.revalidate();
				pnInsertFattura.repaint();
			}
		};
		pnInsertFattura.setBtnCollapseMinus(pnInsertFatturaLisMinus);
		
		// button collapse Minus pnInsertFattura 
		ActionListener pnInsertFatturaLisPlus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnDatiFattura.setSize(300, 40);
				pnInsertFattura.setBounds(30, pnDatiFattura.getHeight() + 20, 300, 430);
				pnInsertFattura.revalidate();
				pnInsertFattura.repaint();
			}
		};
		pnInsertFattura.setBtnCollapsePlus(pnInsertFatturaLisPlus);
		
		// dati
		JLabel lblNumeroIns = new JLabel("Numero");
		lblNumeroIns.setForeground(new Color(255, 255, 255));
		lblNumeroIns.setBounds(12, 70, 70, 15);
		pnInsertFattura.add(lblNumeroIns);
		
		tfDatiFatturaNumeroIns = new JTextField();
		tfDatiFatturaNumeroIns.setEditable(false);
		tfDatiFatturaNumeroIns.setText(String.valueOf(getNextNumber()));
		tfDatiFatturaNumeroIns.setBackground(SystemColor.controlHighlight);
		tfDatiFatturaNumeroIns.setBounds(81, 68, 43, 20);
		pnInsertFattura.add(tfDatiFatturaNumeroIns);
		tfDatiFatturaNumeroIns.setColumns(10);
		
		JButton btnDataIns = new JButton("Data");
		btnDataIns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String lang = null;
				final Locale locale = getLocale(lang);
				DatePicker dp = new DatePicker(tfDatiFatturaDataIns, locale);
				Date selectedDate = dp.parseDate(tfDatiFatturaDataIns.getText());
				dp.setSelectedDate(selectedDate);
				dp.start(tfDatiFatturaDataIns);
			}
		});
		btnDataIns.setForeground(new Color(255, 255, 255));
		btnDataIns.setBounds(142, 68, 55, 21);
		pnInsertFattura.add(btnDataIns);
		
		tfDatiFatturaDataIns = new ObservingTextField();
		tfDatiFatturaDataIns.setEditable(false);
		tfDatiFatturaDataIns.setBackground(new Color(224, 255, 255));
		tfDatiFatturaDataIns.setBounds(206, 68, 82, 20);
		pnInsertFattura.add(tfDatiFatturaDataIns);
		tfDatiFatturaDataIns.setColumns(10);
		
		JLabel lblIndirizzoImmobileIns = new JLabel("Indirizzo");
		lblIndirizzoImmobileIns.setForeground(new Color(255, 255, 255));
		lblIndirizzoImmobileIns.setBounds(12, 110, 139, 15);
		pnInsertFattura.add(lblIndirizzoImmobileIns);
		
		tfDatiFatturaIndirizzoIns = new JTextField();
		tfDatiFatturaIndirizzoIns.setBackground(new Color(224, 255, 255));
		tfDatiFatturaIndirizzoIns.setBounds(142, 110, 146, 20);
		pnInsertFattura.add(tfDatiFatturaIndirizzoIns);
		tfDatiFatturaIndirizzoIns.setColumns(10);
		
		JLabel lblTipologiaIns = new JLabel("Tipologia");
		lblTipologiaIns.setForeground(new Color(255, 255, 255));
		lblTipologiaIns.setBounds(12, 150, 70, 15);
		pnInsertFattura.add(lblTipologiaIns);
		
		cbDatiFatturaTipologiaIns = new JComboBox<String>();
		fillComboBox(cbDatiFatturaTipologiaIns, "Tipo", "Tipologia");
		cbDatiFatturaTipologiaIns.setBackground(new Color(224, 255, 255));
		cbDatiFatturaTipologiaIns.setBounds(142, 150, 146, 20);
		pnInsertFattura.add(cbDatiFatturaTipologiaIns);
		
		JLabel lblImportoIns = new JLabel("Importo");
		lblImportoIns.setForeground(new Color(255, 255, 255));
		lblImportoIns.setBounds(12, 190, 70, 15);
		pnInsertFattura.add(lblImportoIns);
		
		tfDatiFatturaImportoIns = new JTextField();
		tfDatiFatturaImportoIns.setBackground(new Color(224, 255, 255));
		tfDatiFatturaImportoIns.setBounds(142, 190, 146, 20);
		pnInsertFattura.add(tfDatiFatturaImportoIns);
		tfDatiFatturaImportoIns.setColumns(10);
		
		JLabel lblAcquisitoreIns = new JLabel("Acquisitore");
		lblAcquisitoreIns.setForeground(new Color(255, 255, 255));
		lblAcquisitoreIns.setBounds(12, 230, 87, 15);
		pnInsertFattura.add(lblAcquisitoreIns);
		
		cbDatiFatturaAcquisitoreIns = new JComboBox<String>();
		fillComboBox(cbDatiFatturaAcquisitoreIns, "Cognome", "Personale");
		cbDatiFatturaAcquisitoreIns.setBackground(new Color(224, 255, 255));
		cbDatiFatturaAcquisitoreIns.setBounds(142, 230, 146, 20);
		pnInsertFattura.add(cbDatiFatturaAcquisitoreIns);
		
		JLabel lblProvvAIns = new JLabel("Provv A");
		lblProvvAIns.setForeground(new Color(255, 255, 255));
		lblProvvAIns.setBounds(12, 270, 70, 15);
		pnInsertFattura.add(lblProvvAIns);
		
		tfDatiFatturaProvvAIns = new JTextField();
		tfDatiFatturaProvvAIns.setBackground(new Color(224, 255, 255));
		tfDatiFatturaProvvAIns.setBounds(142, 270, 146, 20);
		pnInsertFattura.add(tfDatiFatturaProvvAIns);
		tfDatiFatturaProvvAIns.setColumns(10);
		
		JLabel lblVenditoreIns = new JLabel("Venditore");
		lblVenditoreIns.setForeground(new Color(255, 255, 255));
		lblVenditoreIns.setBounds(12, 310, 70, 15);
		pnInsertFattura.add(lblVenditoreIns);
		
		cbDatiFatturaVenditoreIns = new JComboBox<String>();
		fillComboBox(cbDatiFatturaVenditoreIns, "Cognome", "Personale");
		cbDatiFatturaVenditoreIns.setBackground(new Color(224, 255, 255));
		cbDatiFatturaVenditoreIns.setBounds(142, 310, 146, 20);
		pnInsertFattura.add(cbDatiFatturaVenditoreIns);
		
		JLabel lblProvvVIns = new JLabel("Provv V");
		lblProvvVIns.setForeground(new Color(255, 255, 255));
		lblProvvVIns.setBounds(12, 350, 70, 15);
		pnInsertFattura.add(lblProvvVIns);
		
		tfDatiFatturaProvvVIns = new JTextField();
		tfDatiFatturaProvvVIns.setBackground(new Color(224, 255, 255));
		tfDatiFatturaProvvVIns.setBounds(142, 350, 146, 20);
		pnInsertFattura.add(tfDatiFatturaProvvVIns);
		tfDatiFatturaProvvVIns.setColumns(10);
		
		// btn update e svuota campi
		JButton btnInsertDatiFattura = new JButton("Inserisci");
		btnInsertDatiFattura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(controlloDati()) {
					try {
						String query = "insert into Fattura (DataFattura, IndirizzoImmobile, Tipologia, Importo, Acquisitore, ProvvigioniAcquisitore, Venditore, ProvvigioniVenditore) "
										+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, tfDatiFatturaDataIns.getText());
						pst.setString(2, tfDatiFatturaIndirizzoIns.getText());
						pst.setString(3, (String)cbDatiFatturaTipologiaIns.getSelectedItem());
						pst.setString(4, tfDatiFatturaImportoIns.getText());
						pst.setString(5, (String)cbDatiFatturaAcquisitoreIns.getSelectedItem());
						pst.setString(6, tfDatiFatturaProvvAIns.getText());
						pst.setString(7, (String)cbDatiFatturaVenditoreIns.getSelectedItem());
						pst.setString(8, tfDatiFatturaProvvVIns.getText());
						pst.execute();
						JOptionPane.showMessageDialog(null, "Nuova Fattura inserita correttamente");
						
						printTable(orderByType, orderByNameColumn);
						resetFieldsIns();

						pst.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
				}
				else
					JOptionPane.showMessageDialog(null, "i dati sono tutti obbligatori");
			}
		});
		btnInsertDatiFattura.setBounds(12, 398, 128, 20);
		Image imgInsertDati = new ImageIcon(this.getClass().getResource("/insertNewFattura.png")).getImage();
		btnInsertDatiFattura.setIcon(new ImageIcon(imgInsertDati));
		pnInsertFattura.add(btnInsertDatiFattura);
		
		JButton btnClearDatiFatturaIns = new JButton("Clear");
		btnClearDatiFatturaIns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetFieldsIns();
			}
		});
		btnClearDatiFatturaIns.setBounds(160, 398, 128, 20);
		Image imgClearDatiIns = new ImageIcon(this.getClass().getResource("/clearDatiFattura.png")).getImage();
		btnClearDatiFatturaIns.setIcon(new ImageIcon(imgClearDatiIns));
		pnInsertFattura.add(btnClearDatiFatturaIns);
		
		leftPanel.add(pnInsertFattura);
		
		leftPanel.setPreferredSize(new Dimension(350, 400));
		add(leftPanel, BorderLayout.LINE_START);
		
		// Dimensione Left Panel & Collapse
		btnCollapseLeftPanel = new JButton("");
		btnCollapseLeftPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!closedLeftPanel) {
					leftPanel.setPreferredSize(new Dimension(30, 400));
					closedLeftPanel = true;
					Image imgCollapseLeft = new ImageIcon(this.getClass().getResource("/collapseLeftPanelOpen.png")).getImage();
					btnCollapseLeftPanel.setIcon(new ImageIcon(imgCollapseLeft));
				} else {
					leftPanel.setPreferredSize(new Dimension(350, 400));
					Image imgCollapseLeft = new ImageIcon(this.getClass().getResource("/collapseLeftPanelClosed.png")).getImage();
					btnCollapseLeftPanel.setIcon(new ImageIcon(imgCollapseLeft));
					closedLeftPanel = false;
				}
				revalidate();
				repaint();
			}
		});
		btnCollapseLeftPanel.setBounds(0, 0, 18, 120);
		Image imgCollapseLeft = new ImageIcon(this.getClass().getResource("/collapseLeftPanelClosed.png")).getImage();
		btnCollapseLeftPanel.setIcon(new ImageIcon(imgCollapseLeft));
		leftPanel.add(btnCollapseLeftPanel);
		
		
		
//		###	CENTER			#############################################################################################################
				
		//		TABLE
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 1276, 432);
		add(scrollPane);
		
		printTable(orderByType, orderByNameColumn);
	
		scrollPane.setViewportView(table);
		
		
		
		
//		###	RIGHT			#############################################################################################################
		
		rightPanel = new JPanel();
		rightPanel.setLayout(null);
		rightPanel.setOpaque(false);
		
		// ********** pannello Checkbox fattura
		
		pnCheckboxFattura = new AlphaPanel("images/sfondo.jpg", "Colonne visualizzate", 20, 0, 300, 260);
		
		// button collapse Minus pnCheckboxFattura 
		ActionListener pnCheckboxFatturaLisMinus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnCheckboxFattura.setSize(300, 40);
				pnSearchForImportFattura.setBounds(20, pnCheckboxFattura.getHeight() + 20, 300, pnSearchForImportFattura.getHeight());
				btnExportResult.setBounds(20, pnCheckboxFattura.getHeight() + pnSearchForImportFattura.getHeight() + 40, 300, 30);
				rightPanel.revalidate();
				rightPanel.repaint();
			}
		};
		pnCheckboxFattura.setBtnCollapseMinus(pnCheckboxFatturaLisMinus);
		
		// button collapse Minus pnInsertFattura 
		ActionListener pnCheckboxFatturaLisPlus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnCheckboxFattura.setSize(300, 260);
				pnSearchForImportFattura.setBounds(20, pnCheckboxFattura.getHeight() + 20, 300, pnSearchForImportFattura.getHeight());
				btnExportResult.setBounds(20, pnCheckboxFattura.getHeight() + pnSearchForImportFattura.getHeight() + 40, 300, 30);
				rightPanel.revalidate();
				rightPanel.repaint();
			}
		};
		pnCheckboxFattura.setBtnCollapsePlus(pnCheckboxFatturaLisPlus);

		// dati		
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
		
		rightPanel.add(pnCheckboxFattura);
		
		
		// ********** pannello Slider Importo fattura
		
		pnSearchForImportFattura = new AlphaPanel("images/sfondo.jpg", "Filtra per importo", 20, 280, 300, 245);
		
		// button collapse Minus pnCheckboxFattura 
		ActionListener pnSearchForImportFatturaLisMinus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnSearchForImportFattura.setSize(300, 40);
				btnExportResult.setBounds(20, pnCheckboxFattura.getHeight() + pnSearchForImportFattura.getHeight() + 40, 300, 30);
				pnSearchForImportFattura.revalidate();
				pnSearchForImportFattura.repaint();
			}
		};
		pnSearchForImportFattura.setBtnCollapseMinus(pnSearchForImportFatturaLisMinus);
		
		// button collapse Plus pnInsertFattura 
		ActionListener pnSearchForImportFatturaLisPlus = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnSearchForImportFattura.setSize(300, 245);
				btnExportResult.setBounds(20, pnCheckboxFattura.getHeight() + pnSearchForImportFattura.getHeight() + 40, 300, 30);
				pnSearchForImportFattura.revalidate();
				pnSearchForImportFattura.repaint();
			}
		};
		pnSearchForImportFattura.setBtnCollapsePlus(pnSearchForImportFatturaLisPlus);
		
		// dati
		
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
				printTable(orderByType, orderByNameColumn);
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
				printTable(orderByType, orderByNameColumn);
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
		
		// ********** button esporta risultati ricerca

		btnExportResult = new JButton("Esporta Ricerca");
		btnExportResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExportTableXLS();
			}
		});
		btnExportResult.setFont(new Font("Dialog", Font.BOLD, 14));
		btnExportResult.setForeground(SystemColor.controlLtHighlight);
		btnExportResult.setBackground(Color.DARK_GRAY);
		btnExportResult.setBounds(20, 545, 300, 40);
		Image imgExportResult = new ImageIcon(this.getClass().getResource("/btnExportResult.png")).getImage();
		btnExportResult.setIcon(new ImageIcon(imgExportResult));
		rightPanel.add(btnExportResult);
		
		rightPanel.add(pnSearchForImportFattura);
		
		// Dimensione Right Panel & Collapse
		rightPanel.setPreferredSize(new Dimension(350, 400));
		add(rightPanel, BorderLayout.LINE_END);
		
		btnCollapseRightPanel = new JButton("");
		btnCollapseRightPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!closedRightPanel) {
					rightPanel.setPreferredSize(new Dimension(30, 400));
					pnSearchForImportFattura.setVisible(false);
					pnCheckboxFattura.setVisible(false);
					btnExportResult.setVisible(false);
					btnCollapseRightPanel.setBounds(12, 0, 18, 120);
					closedRightPanel = true;
					Image imgCollapseRight = new ImageIcon(this.getClass().getResource("/collapseRightPanelOpen.png")).getImage();
					btnCollapseRightPanel.setIcon(new ImageIcon(imgCollapseRight));
				} else {
					rightPanel.setPreferredSize(new Dimension(350, 400));
					pnSearchForImportFattura.setVisible(true);
					pnCheckboxFattura.setVisible(true);
					btnExportResult.setVisible(true);
					btnCollapseRightPanel.setBounds(332, 0, 18, 120);
					Image imgCollapseRight = new ImageIcon(this.getClass().getResource("/collapseRightPanelClosed.png")).getImage();
					btnCollapseRightPanel.setIcon(new ImageIcon(imgCollapseRight));
					closedRightPanel = false;
				}
				revalidate();
				repaint();
			}
		});
		btnCollapseRightPanel.setBounds(332, 0, 18, 120);
		Image imgCollapseRight = new ImageIcon(this.getClass().getResource("/collapseRightPanelClosed.png")).getImage();
		btnCollapseRightPanel.setIcon(new ImageIcon(imgCollapseRight));
		rightPanel.add(btnCollapseRightPanel);
		
		
		
	}
	
	
	
//	********************************************	METODI DI SUPPORTO	 ******************************************
	
	/*
	 * stampa la tabella
	 * typeOrder - tipo ordinamento tabella - 
	 *     0 - nessun ordinamento
	 *     1 - ordinamento crescente
	 *     2 - ordinamentp decrescente
	 * nameColumn - colonna su cui effettuare ordinamento    
	 */
	private void printTable(int typeOrder, String nameColumn) {
		
		setColumnsName();
		try {
			
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(strNameColumns);
			table = new JTable();
	        table.setModel(model);
	        
	        String query = getQuery(typeOrder, nameColumn);
	        
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
			table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 50));
			table.getTableHeader().setBackground(new Color(240, 235, 135));
			table.setRowHeight(25);
			
			pst.close();
			rs.close();
			
			// set field by click row
			tableClickRow();
			// order table by click column
			addHeaderListenerTable();
	        
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		scrollPane.setViewportView(table);
	}
	
	/*
	 * crea la query in base 
	 * agli elementi selezionati
	 */
	private String getQuery(int typeOrder, String nameColumn) {
		System.out.println("importo -> "+minImporto + "  " + maxImporto);
		System.out.println("slider -> "+minSlider + "  " + maxSlider);
		String query = "";
		if(minImporto==0 && maxImporto==0) {
			query = "select NumeroFattura ";
			if(boolData) query+=", DataFattura";
			if(boolIndirizzo) query+=", IndirizzoImmobile";
			if(boolTipologia) query+=", Tipologia";
			if(boolImporto) query+=", Importo";
			if(boolAcquisitore) query+=", Acquisitore";
			if(boolProvvA) query+=", ProvvigioniAcquisitore";
			if(boolVenditore) query+=", Venditore";
			if(boolProvvV) query+=", ProvvigioniVenditore";
			query+=" from Fattura";			
			if(typeOrder!=0) {
				String ord = (typeOrder==1) ? "ASC" : "DESC";
				query+=" ORDER BY " + nameColumn + " " + ord;
			}
		} else {
			query = "select NumeroFattura ";
			if(boolData) query+=", DataFattura";
			if(boolIndirizzo) query+=", IndirizzoImmobile";
			if(boolTipologia) query+=", Tipologia";
			if(boolImporto) query+=", Importo";
			if(boolAcquisitore) query+=", Acquisitore";
			if(boolProvvA) query+=", ProvvigioniAcquisitore";
			if(boolVenditore) query+=", Venditore";
			if(boolProvvV) query+=", ProvvigioniVenditore";
			query+=" from Fattura where Importo >= " + minSlider + " and Importo <= " + maxSlider;
			if(typeOrder!=0) {
				String ord = (typeOrder==1) ? "ASC" : "DESC";
				query+=" ORDER BY " + nameColumn + " " + ord;
			}
		}
		return query;
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
		printTable(orderByType, orderByNameColumn);
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
	 * verifica se la colonna è presente in tabella
	 */
	private boolean isPresent(String colonna) {
		for(int i=0; i<strNameColumns.length; i++)
			if(strNameColumns[i].equals(colonna))
				return true;
		return false;
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
			
			minSlider = (int)minImporto;
			maxSlider = (int)maxImporto;
			
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

	
	/*
	 * select row in table
	 */
	public void tableClickRow() {
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
	}
	
	/*
	 * listener header table
	 * order by click
	 */
	private void addHeaderListenerTable() {
		// order table by click column
		table.getTableHeader().addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	int col = table.columnAtPoint(e.getPoint());
		    	String nameColumn = table.getColumnName(col);
		    	if(nameColumn.equals("DataFattura")) {
	    			nameColumn = "datetime(DataFattura)"; 
	    		}
		    	if (e.getClickCount() == 1) {
		    		orderByType = 1;
		    		printTable(1, nameColumn);
		    		System.out.println("un click");
		    	} else if(e.getClickCount() == 2) {
		    		orderByType = 2;
		    		printTable(2, nameColumn);
		    		System.out.println("due click");
		    	}
		    	orderByNameColumn=nameColumn;
		    }
		});

	}
	
	
	/*
	 * controllo dati per inserimento
	 * Fattura in DB
	 */
	private boolean controlloDati() {
		if(tfDatiFatturaDataIns.getText().equals("") || tfDatiFatturaImportoIns.getText().equals("") || tfDatiFatturaIndirizzoIns.getText().equals("") || 
				tfDatiFatturaProvvAIns.getText().equals("") || tfDatiFatturaProvvVIns.getText().equals("") )
			return false;
		return true;
	}
	
	
	/*
	 * reset campi inserimento Fattura
	 */
	private void resetFieldsIns() {
		tfDatiFatturaNumeroIns.setText(String.valueOf(getNextNumber()));
		tfDatiFatturaDataIns.setText("");
		tfDatiFatturaIndirizzoIns.setText("");
		cbDatiFatturaTipologiaIns.setSelectedIndex(0);
		tfDatiFatturaImportoIns.setText("");
		cbDatiFatturaAcquisitoreIns.setSelectedIndex(0);
		tfDatiFatturaProvvAIns.setText("");
		cbDatiFatturaVenditoreIns.setSelectedIndex(0);
		tfDatiFatturaProvvVIns.setText("");
	}
	
	
	/*
	 * posizione IT per la data
	 */
	private Locale getLocale(String loc) {
		if(loc!=null && loc.length() > 0)
			return new Locale(loc);
		else
			return Locale.ITALY;
	}
	
	/*
	 * search in DB next number Fattura
	 */
	private int getNextNumber() {
		int numeroFattura=0;
		try {
			String query = "select NumeroFattura from Fattura";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				numeroFattura++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} return numeroFattura+1;
	}
	
	/*
	 * Esportazione risultati tabella
	 */
	private void ExportTableXLS() {
		try {
			String filename = "/home/dado/Scrivania/Export.xls";
			WritableWorkbook workbook = Workbook.createWorkbook(new File(filename));
			WritableSheet sheet = workbook.createSheet("Tabella", 0);
			
			// Colonne
			for(int i=0; i<table.getColumnCount(); i++) {
				
				WritableFont fontHeader = new WritableFont(WritableFont.TIMES, 18, WritableFont.BOLD, true); 
				WritableCellFormat formatHeader = new WritableCellFormat (fontHeader);
				formatHeader.setAlignment(Alignment.CENTRE);
				Label labelHeader = new Label(i, 0, table.getColumnName(i), formatHeader);
				CellView cell=sheet.getColumnView(i);
			    cell.setAutosize(true);
			    sheet.setColumnView(i, cell);
			    
			    if (i==0) {
			    	sheet.setRowView(0, 500);
				}
			    
				sheet.addCell(labelHeader);
				
				// Righe
				for(int j=0; j<table.getRowCount()-1; j++) {
					WritableCellFormat formatRow = new WritableCellFormat ();
					if( (table.getColumnName(i).equals("NumeroFattura")) || 
						(table.getColumnName(i).equals("DataFattura")) || 
						(table.getColumnName(i).equals("Tipologia"))) {
						formatRow.setAlignment(Alignment.CENTRE);
					} else if( (table.getColumnName(i).equals("Importo")) ||  
						(table.getColumnName(i).equals("ProvvigioniAcquisitore")) ||
						(table.getColumnName(i).equals("ProvvigioniVenditore")) ) {
						formatRow.setAlignment(Alignment.RIGHT);
					}
					Label labelRow = new Label(i, j+1, table.getValueAt(j, i).toString(), formatRow);
					sheet.addCell(labelRow);
				}
				
				// Ultima Riga
				WritableFont fontTotal = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false); 
				WritableCellFormat formatTotal = new WritableCellFormat (fontTotal);
				if( (table.getColumnName(i).equals("NumeroFattura")) ) {
					formatTotal.setAlignment(Alignment.CENTRE);
				}
				else {
					formatTotal.setAlignment(Alignment.RIGHT);
				}
				Label labelTotal = new Label(i, table.getRowCount(), table.getValueAt(table.getRowCount()-1, i).toString(), formatTotal);
				sheet.setRowView(table.getRowCount(), 350);
				sheet.addCell(labelTotal);
			}
			
			workbook.write();
			workbook.close();
			
			JOptionPane.showMessageDialog(null, "Esportazione avvenuta con successo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(imgContainer, 0, 0, null);
	}
}

