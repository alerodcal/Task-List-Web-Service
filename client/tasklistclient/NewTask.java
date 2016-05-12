package tasklistclient;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewTask extends JDialog {
	private Client client = null;

	private final JPanel contentPanel = new JPanel();
	private JTextField tf_task;
	private JTextField tf_dueDate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewTask dialog = new NewTask(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewTask(String list) {
		client = Client.getInstance();
		
		setTitle("New Task");
		setResizable(false);
		setBackground(Color.GRAY);
		setBounds(100, 100, 341, 137);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Description");
		lblNewLabel.setBounds(12, 20, 89, 15);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Due date");
		lblNewLabel_1.setBounds(12, 49, 70, 15);
		contentPanel.add(lblNewLabel_1);
		
		tf_task = new JTextField();
		tf_task.setBounds(119, 18, 204, 19);
		contentPanel.add(tf_task);
		tf_task.setColumns(10);
		
		tf_dueDate = new JTextField();
		tf_dueDate.setBounds(119, 47, 204, 19);
		contentPanel.add(tf_dueDate);
		tf_dueDate.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.LIGHT_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						client.addTask(tf_task.getText(), tf_dueDate.getText(), list);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						client.hideNewTaskPanel();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
