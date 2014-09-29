package utility;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;



public class AlphaPanel extends JPanel {
	
	private Image img;
	private JButton btnMinus;
	private JButton btnPlus;


	public AlphaPanel(String strImg, String titolo, final int x, final int y, final int width, final int height) {
		
		this.img = new ImageIcon(strImg).getImage();
		
		// Dimension size = new Dimension(width, height);
		setBounds(x, y, width, height);
		
	    setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
	    setLayout(null);
	    
	    // 	titolo pannello
		JLabel lblTitoloPannello = new JLabel(titolo);
		lblTitoloPannello.setForeground(new Color(255, 255, 255));
		lblTitoloPannello.setFont(new Font("Ubuntu", Font.BOLD, 15));
		lblTitoloPannello.setBounds(12, 12, 219, 20);
		add(lblTitoloPannello);
		
		// btn plus collapse
		btnPlus = new JButton();
		btnPlus.setBounds(236, 12, 20, 20);
		Image imgPlusCollapseDati = new ImageIcon(this.getClass().getResource("/plusCollapse.png")).getImage();
		btnPlus.setIcon(new ImageIcon(imgPlusCollapseDati));
		add(btnPlus);
	    
		// btn minus collapse
		btnMinus = new JButton();
		btnMinus.setBounds(268, 12, 20, 20);
		Image imgMinusCollapseDati = new ImageIcon(this.getClass().getResource("/minusCollapse.png")).getImage();
		btnMinus.setIcon(new ImageIcon(imgMinusCollapseDati));
		add(btnMinus);
		
		// separatore testata - dati
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255));
		separator.setBounds(12, 47, 276, 2);
		add(separator);
	    
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
	
	public void setBtnCollapseMinus(ActionListener l) {
		btnMinus.addActionListener(l);
	}
	
	public void setBtnCollapsePlus(ActionListener l) {
		btnPlus.addActionListener(l);
	}
	

}
