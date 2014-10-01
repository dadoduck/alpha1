import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.SystemColor;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;


public class MainFrame extends JFrame {

	private JFrame frame;
	private int wScreen, hScreen;
	private Container container;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					frame.setExtendedState(Frame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		super("Gestione alpha");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		Dimensioni risluzione getContentPane
		Toolkit tk = Toolkit.getDefaultToolkit();
		wScreen = ((int)tk.getScreenSize().getWidth());
		hScreen = ((int)tk.getScreenSize().getHeight());
		System.out.println(wScreen + " " + hScreen);
		
		container = this.getContentPane();
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFattura = new JMenu("Fattura");
		menuBar.add(mnFattura);
		
		JMenuItem mntmVisualizza = new JMenuItem("Gestisci");
		mntmVisualizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VisualizzaFatturaPanelBis visualizzaFatturaPanel = new VisualizzaFatturaPanelBis();
				container.removeAll();
				container.repaint();
				container.revalidate();
				container.add(visualizzaFatturaPanel);
				container.repaint();
				container.revalidate();
			}
		});
		mnFattura.add(mntmVisualizza);
		
		JMenuItem mntmEsci = new JMenuItem("Esci");
		mnFattura.add(mntmEsci);
		
		JMenu mnPersonale = new JMenu("Personale");
		menuBar.add(mnPersonale);
		
		JMenuItem mntmNuovo = new JMenuItem("Gestisci");
		mnPersonale.add(mntmNuovo);
		
		JMenu mnTipologia = new JMenu("Tipologia");
		menuBar.add(mnTipologia);
		
		JMenuItem mntmGestisci = new JMenuItem("Gestisci");
		mnTipologia.add(mntmGestisci);
		
		JMenu mnAiuto = new JMenu("Aiuto");
		menuBar.add(mnAiuto);
		
		JMenuItem mntmProve = new JMenuItem("prove");
		mntmProve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proveTable proveTablePanel = new proveTable();
				container.removeAll();
				container.repaint();
				container.revalidate();
				container.add(proveTablePanel);
				container.repaint();
				container.revalidate();
			}
		});
		mnAiuto.add(mntmProve);
		
		
		
		
	}
}
