
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import utility.AlphaTableRender;

import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class proveTable extends JPanel {
	
	Connection connection = null;
	private JTable table;
	private JScrollPane scrollPane;
	private JCheckBox chckbxDataFattura;
	private JCheckBox chckbxIndirizzo;
	private JCheckBox chckbxTipologia;
	private JCheckBox chckbxImporto;
	private JCheckBox chckbxAcquisitore;
	private JCheckBox chckbxProvvigioniA;
	private JCheckBox chckbxVenditore;
	private JCheckBox chckbxProvvigioniV;
//	String[] columnNames = {"Numero", "Data", "Indirizzo", "Tipologia", "Importo", "Acquisitore", "Provv A", "Venditore", "Provv V"};
	private boolean boolData = true, boolIndirizzo = true, boolTipologia = true, boolImporto = true, boolAcquisitore = true, boolProvvA = true, boolVenditore = true, boolProvvV = true;
	private String[] strNameColumns;
	
	/**
	 * Create the panel.
	 */  
	public proveTable() {

//		connessione al database
		connection = sqliteConnection.dbConnector();
		
//		costruzione jpanel
		setBounds(20, 20, 1300, 600);
		setLayout(null);
		
//		TABLE
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 1276, 432);
		add(scrollPane);
		
		LoadTable();
		
		table.setRowHeight(25);
		
		scrollPane.setViewportView(table);
		
		stampaStatoChkbx(boolData, boolIndirizzo, boolTipologia, boolImporto, boolAcquisitore, boolProvvA, boolVenditore, boolProvvV);
		
		chckbxDataFattura = new JCheckBox("Data Fattura", true);
		chckbxDataFattura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolData = chckbxDataFattura.isSelected();
				setColumnsName();
				test();
			}
		});
		chckbxDataFattura.setBounds(12, 464, 129, 23);
		add(chckbxDataFattura);
		
		chckbxIndirizzo = new JCheckBox("Indirizzo", true);
		chckbxIndirizzo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolIndirizzo = chckbxIndirizzo.isSelected();
				setColumnsName();
				test();
			}
		});
		chckbxIndirizzo.setBounds(145, 464, 91, 23);
		add(chckbxIndirizzo);
		
		chckbxTipologia = new JCheckBox("Tipologia", true);
		chckbxTipologia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolTipologia = chckbxTipologia.isSelected();
				setColumnsName();
				test();
			}
		});
		chckbxTipologia.setBounds(250, 464, 99, 23);
		add(chckbxTipologia);
		
		chckbxImporto = new JCheckBox("Importo", true);
		chckbxImporto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolImporto = chckbxImporto.isSelected();
				setColumnsName();
				test();
			}
		});
		chckbxImporto.setBounds(360, 464, 91, 23);
		add(chckbxImporto);
		
		chckbxAcquisitore = new JCheckBox("Acquisitore", true);
		chckbxAcquisitore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolAcquisitore = chckbxAcquisitore.isSelected();
				setColumnsName();
				test();
			}
		});
		chckbxAcquisitore.setBounds(455, 464, 114, 23);
		add(chckbxAcquisitore);
		
		chckbxProvvigioniA = new JCheckBox("Provvigioni A", true);
		chckbxProvvigioniA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolProvvA = chckbxProvvigioniA.isSelected();
				setColumnsName();
				test();
			}
		});
		chckbxProvvigioniA.setBounds(573, 464, 129, 23);
		add(chckbxProvvigioniA);
		
		chckbxVenditore = new JCheckBox("Venditore", true);
		chckbxVenditore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolVenditore = chckbxVenditore.isSelected();
				setColumnsName();
				test();
			}
		});
		chckbxVenditore.setBounds(706, 464, 99, 23);
		add(chckbxVenditore);
		
		chckbxProvvigioniV = new JCheckBox("Provvigioni V", true);
		chckbxProvvigioniV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolProvvV = chckbxProvvigioniV.isSelected();
				setColumnsName();
				test();
			}
		});
		chckbxProvvigioniV.setBounds(812, 464, 129, 23);
		add(chckbxProvvigioniV);
		
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
		scrollPane.getViewport().add(table);
        
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean isPresent(String colonna) {
		for(int i=0; i<strNameColumns.length; i++)
			if(strNameColumns[i].equals(colonna))
				return true;
		return false;
	}
	
	
	
	
	
//			METODI DI TEST
	
	
	
	/*
	 * metodo do test comune a tutte le chkbox
	 */
	private void test() {
		stampaStatoChkbx(boolData, boolIndirizzo, boolTipologia, boolImporto, boolAcquisitore, boolProvvA, boolVenditore, boolProvvV);
		System.out.println(getNumColumnSelected());
		stampaColonneSelezionate();
		printTable();
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
	
}

