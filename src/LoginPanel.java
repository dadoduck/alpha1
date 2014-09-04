import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;


public class LoginPanel extends JPanel {
	private JTextField textFieldUsername;
	private JPasswordField passwordField;

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		setLayout(null);
		
		JLabel labelUsername = new JLabel("Usename");
		labelUsername.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 12));
		labelUsername.setBounds(99, 118, 84, 15);
		add(labelUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 12));
		lblPassword.setBounds(99, 163, 70, 15);
		add(lblPassword);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(195, 116, 148, 19);
		add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(195, 161, 148, 19);
		add(passwordField);
		
		JLabel lblNewLabel = new JLabel("Effettua il login");
		lblNewLabel.setFont(new Font("Sawasdee", Font.BOLD, 25));
		lblNewLabel.setBounds(99, 44, 232, 47);
		add(lblNewLabel);
		
		JButton btnNewButton = new JButton("login");
		btnNewButton.setFont(new Font("Courier 10 Pitch", Font.BOLD, 20));
		Image imgLoginOk = new ImageIcon(this.getClass().getResource("/loginIconOk.png")).getImage();
		btnNewButton.setIcon(new ImageIcon(imgLoginOk));
		btnNewButton.setBounds(99, 211, 244, 34);
		add(btnNewButton);
		
	}
}
