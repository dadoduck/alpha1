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
		getContentPane().setBackground(SystemColor.desktop);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		Dimensioni risluzione getContentPane
		Toolkit tk = Toolkit.getDefaultToolkit();
		wScreen = ((int)tk.getScreenSize().getWidth());
		hScreen = ((int)tk.getScreenSize().getHeight());
		System.out.println(wScreen + " " + hScreen);
		setBounds(0, 0, 1366, 768);
		getContentPane().setLayout(null);
		
//		CONTAINER PANEL
		final JPanel containerPanel = new JPanel();
		containerPanel.setBounds(12, 12, 1336, 680);
		getContentPane().add(containerPanel);
		containerPanel.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFattura = new JMenu("Fattura");
		menuBar.add(mnFattura);
		
		JMenuItem mntmNuova = new JMenuItem("Nuova");
		mntmNuova.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NuovaFatturaPanel nuovaFatturaPanel = new NuovaFatturaPanel();
				containerPanel.removeAll();
				containerPanel.repaint();
				containerPanel.revalidate();
				containerPanel.add(nuovaFatturaPanel);
				containerPanel.repaint();
				containerPanel.revalidate();
			}
		});
		mnFattura.add(mntmNuova);
		
		JSeparator separator = new JSeparator();
		mnFattura.add(separator);
		
		JMenuItem mntmVisualizza = new JMenuItem("Visualizza");
		mntmVisualizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VisualizzaFatturaPanel visualizzaFatturaPanel = new VisualizzaFatturaPanel();
				containerPanel.removeAll();
				containerPanel.repaint();
				containerPanel.revalidate();
				containerPanel.add(visualizzaFatturaPanel);
				containerPanel.repaint();
				containerPanel.revalidate();
			}
		});
		mnFattura.add(mntmVisualizza);
		
		JMenuItem mntmFiltraVista = new JMenuItem("Filtra vista");
		mnFattura.add(mntmFiltraVista);
		
		JSeparator separator_1 = new JSeparator();
		mnFattura.add(separator_1);
		
		JMenuItem mntmEsci = new JMenuItem("Esci");
		mnFattura.add(mntmEsci);
		
		JMenu mnPersonale = new JMenu("Personale");
		menuBar.add(mnPersonale);
		
		JMenuItem mntmNuovo = new JMenuItem("Nuovo");
		mntmNuovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NuovoPersonalePanel nuovoPersonalePanel = new NuovoPersonalePanel();
				containerPanel.removeAll();
				containerPanel.repaint();
				containerPanel.revalidate();
				containerPanel.add(nuovoPersonalePanel);
				containerPanel.repaint();
				containerPanel.revalidate();
			}
		});
		mnPersonale.add(mntmNuovo);
		
		JMenuItem mntmVisualizza_1 = new JMenuItem("Visualizza");
		mntmVisualizza_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VisualizzaPersonalePanel visualizzaPersonalePanel = new VisualizzaPersonalePanel();
				containerPanel.removeAll();
				containerPanel.repaint();
				containerPanel.revalidate();
				containerPanel.add(visualizzaPersonalePanel);
				containerPanel.repaint();
				containerPanel.revalidate();
			}
		});
		mnPersonale.add(mntmVisualizza_1);
		
		JMenu mnTipologia = new JMenu("Tipologia");
		menuBar.add(mnTipologia);
		
		JMenuItem mntmGestisci = new JMenuItem("Gestisci");
		mntmGestisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GestisciTipologiaPanel gestisciTipologiaPanel = new GestisciTipologiaPanel();
				containerPanel.removeAll();
				containerPanel.repaint();
				containerPanel.revalidate();
				containerPanel.add(gestisciTipologiaPanel);
				containerPanel.repaint();
				containerPanel.revalidate();
			}
		});
		mnTipologia.add(mntmGestisci);
		
		JMenu mnAiuto = new JMenu("Aiuto");
		menuBar.add(mnAiuto);
		
		JMenuItem mntmProve = new JMenuItem("prove");
		mntmProve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				proveTable proveTablePanel = new proveTable();
				containerPanel.removeAll();
				containerPanel.repaint();
				containerPanel.revalidate();
				containerPanel.add(proveTablePanel);
				containerPanel.repaint();
				containerPanel.revalidate();
			}
		});
		mnAiuto.add(mntmProve);
		
		
		
	}
}
