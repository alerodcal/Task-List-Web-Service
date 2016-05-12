package tasklistclient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TaskListClient extends JFrame {
	private static final int DONE_COLUMN = 3;

	WindowListener exitListener = null;
	
	private JPanel contentPane;
	
	private Client client = null;
	private JTable table_lists;
	private JTable table_tasks;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaskListClient frame = new TaskListClient();
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
	public TaskListClient() {
		client = Client.getInstance();
		
		setResizable(false);
		setTitle("Task List Client");
		setBackground(Color.GRAY);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 685, 324);
		exitListener = new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	client.closeSession();
		        System.exit(0);
		    }
		};
		this.addWindowListener(exitListener);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 80, 177, 207);
		contentPane.add(scrollPane);
		
		table_lists = new JTable();
		table_lists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_lists.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (table_lists.getSelectedRow() != -1)
					reloadTasks(table_lists.getValueAt(table_lists.getSelectedRow(), 0).toString());
			}
	    });
		reloadLists();
		scrollPane.setViewportView(table_lists);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(237, 80, 434, 207);
		contentPane.add(scrollPane_1);
		
		table_tasks = new JTable();
		table_tasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		reloadTasks(null);
		scrollPane_1.setViewportView(table_tasks);
		
		JButton btnDeleteTask = new JButton("Delete");
		btnDeleteTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table_tasks.getSelectedRow() != -1) {
					String list = table_lists.getValueAt(table_lists.getSelectedRow(), 0).toString();
					int task = Integer.parseInt(table_tasks.getValueAt(table_tasks.getSelectedRow(), 0).toString());
					client.deleteTask(task, list);
					reloadTasks(list);
				}
			}
		});
		btnDeleteTask.setBounds(330, 39, 81, 25);
		contentPane.add(btnDeleteTask);
		
		JButton btnAddTask = new JButton("Add");
		btnAddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table_lists.getSelectedRow() != -1) 
					client.showNewTaskPanel(table_lists.getValueAt(table_lists.getSelectedRow(), 0).toString());
			}
		});
		btnAddTask.setBounds(237, 39, 81, 25);
		contentPane.add(btnAddTask);
		
		JButton btnDeleteList = new JButton("Delete");
		btnDeleteList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table_lists.getSelectedRow() != -1) {
					String list = table_lists.getValueAt(table_lists.getSelectedRow(), 0).toString();
					client.deleteList(list);
					reloadLists();
					reloadTasks(null);
				}
			}
		});
		btnDeleteList.setBounds(105, 39, 81, 25);
		contentPane.add(btnDeleteList);
		
		JButton btnAddList = new JButton("Add");
		btnAddList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.showNewListPanel();
			}
		});
		btnAddList.setBounds(12, 39, 81, 25);
		contentPane.add(btnAddList);
		
		JLabel lblTaskLists = new JLabel("Task Lists");
		lblTaskLists.setFont(new Font("Droid Sans", Font.BOLD, 14));
		lblTaskLists.setBounds(12, 12, 81, 15);
		contentPane.add(lblTaskLists);
		
		JLabel lblTasks = new JLabel("Tasks");
		lblTasks.setFont(new Font("Droid Sans", Font.BOLD, 14));
		lblTasks.setBounds(241, 12, 41, 15);
		contentPane.add(lblTasks);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table_tasks.getSelectedRow() != -1){
					int id = Integer.parseInt(table_tasks.getValueAt(table_tasks.getSelectedRow(), 0).toString());
					String task = table_tasks.getValueAt(table_tasks.getSelectedRow(), 1).toString();
					String dueDate = table_tasks.getValueAt(table_tasks.getSelectedRow(), 2).toString();
					Boolean done = (Boolean) table_tasks.getValueAt(table_tasks.getSelectedRow(), 3);
					String list = table_lists.getValueAt(table_lists.getSelectedRow(), 0).toString();
					client.showEditTaskPanel(id, task, dueDate, done, list);
				}
			}
		});
		btnEdit.setBounds(423, 39, 81, 25);
		contentPane.add(btnEdit);
		
		JButton btnCloseSession = new JButton("Close session");
		btnCloseSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.closeSession();
			}
		});
		btnCloseSession.setBounds(539, 7, 132, 25);
		contentPane.add(btnCloseSession);
		
		JButton btnDeleteUser = new JButton("Delete user");
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.deleteUser();
			}
		});
		btnDeleteUser.setBounds(539, 39, 132, 25);
		contentPane.add(btnDeleteUser);
	}
	
	public void reloadLists(){
		Object [][] data = null; 
		data = client.getLists();
		if (data == null){
			data = new Object [][] {};
		}
		table_lists.setModel(new DefaultTableModel(data,
				new String[] {
					"Tasks Lists"
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table_lists.getColumnModel().getColumn(0).setResizable(false);
			table_lists.getColumnModel().getColumn(0).setPreferredWidth(163);
	}
	
	public void reloadTasks(String taskList) {
		Object [][] data = null;
		if (taskList != null)
			data = client.getTasks(taskList);
		if (data == null)
			data = new Object [][] {};
		table_tasks.setModel(new DefaultTableModel(data,
				new String[] {
					"id", "Description", "Due Date", "Done"
				}
			) {
				Class[] columnTypes = new Class[] {
					Integer.class, String.class, String.class, Boolean.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false, true
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table_tasks.getColumnModel().getColumn(0).setPreferredWidth(28);
			table_tasks.getColumnModel().getColumn(1).setPreferredWidth(266);
			table_tasks.getColumnModel().getColumn(2).setPreferredWidth(86);
			table_tasks.getColumnModel().getColumn(3).setPreferredWidth(50);
			
			table_tasks.getModel().addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					// TODO Auto-generated method stub
					int row = e.getFirstRow();
					int column = e.getColumn();
				    if (column == DONE_COLUMN) {
				        TableModel model = (TableModel) e.getSource();
				        String columnName = model.getColumnName(column);
				        Boolean checked = (Boolean) model.getValueAt(row, column);
				        client.editTask((int) model.getValueAt(row, 0), 
				        		(String) model.getValueAt(row, 1), 
				        		(String) model.getValueAt(row, 2), checked, 
				        		table_lists.getValueAt(table_lists.getSelectedRow(), 0).toString());
				    }
				}
			});
	}
}
