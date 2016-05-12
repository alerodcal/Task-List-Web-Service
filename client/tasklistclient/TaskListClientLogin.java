package tasklistclient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;

public class TaskListClientLogin extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;
	
	private Client client = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskListClientLogin frame = new TaskListClientLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TaskListClientLogin() {
		client = Client.getInstance();
		
		setTitle("Login");
		setBackground(Color.GRAY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 314, 152);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setForeground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User");
		lblNewLabel.setBounds(23, 23, 70, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(23, 52, 70, 15);
		contentPane.add(lblNewLabel_1);
		
		username = new JTextField();
		username.setBounds(109, 21, 179, 19);
		contentPane.add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(109, 50, 179, 19);
		contentPane.add(password);
		password.setColumns(10);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (username.getText() != null && password.getText() != null )
					client.register(username.getText(), password.getText());
			}
		});
		btnRegister.setBounds(23, 79, 117, 25);
		contentPane.add(btnRegister);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (username.getText() != null && password.getText() != null )
					client.login(username.getText(), password.getText());
			}
		});
		btnLogin.setBounds(171, 79, 117, 25);
		contentPane.add(btnLogin);
	}
}
