package tasklistclient;

import javax.swing.JOptionPane;

import es.uc3m.www.WS.tasklistService.*;
import HP.axis.services.tasklistService.*;

public class Client {
	private static Client instance = null;
	
	private TasklistServiceService tasklistservice = null;
	private TasklistService service = null;
	
	private TaskListClientLogin loginPanel = null;
	private TaskListClient tasksPanel = null;
	private NewTask newTaskPanel = null;
	private NewList newListPanel = null;
	private EditTask editTaskPanel = null;
	
	private String sessionToken = null;
	
	public static void main(String[] args){
		Client client = Client.getInstance();
		client.showLoginPanel();
	}
	
	private Client() {
		tasklistservice = new TasklistServiceServiceLocator();
		
		try {
	        service = tasklistservice.gettasklistService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Client getInstance() {
		if(instance == null) {
		    instance = new Client();
		}
		return instance;
	}
	
	public void login(String username, String password){
		try {
			sessionToken = service.loginUser(username, password);
			System.out.println(sessionToken);
			JOptionPane.showMessageDialog(loginPanel,
					"You have been successfully logged.");
			hideLoginPanel();
			showTasksPanel();
		} catch (Exception e){
			System.err.println("Incorrect user or password.");
			//custom title, error icon
			JOptionPane.showMessageDialog(loginPanel,
			    "Incorrect user or password.",
			    "Login error",
			    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void closeSession (){
		try {
			service.closeSession(sessionToken);
			JOptionPane.showMessageDialog(loginPanel,
				"Your session has been closed.");
			hideTasksPanel();
			showLoginPanel();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void register(String username, String password){
		try {
			sessionToken = service.registerUser(username, password);
			System.err.println(sessionToken);
			JOptionPane.showMessageDialog(loginPanel,
					"You have been successfully registered and logged.");
			hideLoginPanel();
			showTasksPanel();
		} catch (Exception e){
			System.err.println("Username is already in use.");
			//custom title, error icon
			JOptionPane.showMessageDialog(loginPanel,
				"Username is already in use.",
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void deleteUser (){
		try {
			service.deleteUser(sessionToken);
			JOptionPane.showMessageDialog(loginPanel,
				"Your user has been deleted.");
			hideTasksPanel();
			showLoginPanel();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public Object[][] getLists(){
		Object [][] result = null;
		String [] lists = null;
		
		try {
			lists = service.getLists(sessionToken);
			if (lists != null) {
				result = new Object [lists.length][1];
				for (int i = 0; i < lists.length; i++) 
					result[i][0] = lists[i];
			}
		} catch (Exception e) {
			System.err.println("Invalid session token.");
		}
		return result;
	}
	
	public void deleteList (String list){
		try {
			service.deleteTaskList(list, sessionToken);
		} catch(Exception e) {
			System.err.println(e);
		}
	}
	
	public void addList (String list){
		try {
			service.newTaskList(list, sessionToken);
			hideNewListPanel();
			tasksPanel.reloadLists();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public Object[][] getTasks(String tasklist){
		Object [][] result = null;
		Task [] tasks = null;
		
		try {
			tasks = service.getTasks(tasklist, sessionToken);
			if (tasks != null) {
				result = new Object [tasks.length][4];
				for (int i = 0; i < tasks.length; i++){
					result[i][0] = tasks[i].getId();
					result[i][1] = tasks[i].getTask();
					result[i][2] = tasks[i].getDueDate();
					result[i][3] = tasks[i].getDone();
				}
					
			}
		} catch (Exception e) {
			System.err.println("Invalid session token.");
		}
		return result;
	}
	
	public void deleteTask (int id, String list){
		try {
			service.deleteTask(id, list, sessionToken);
		} catch(Exception e) {
			System.err.println(e);
		}
	}
	
	public void addTask (String task, String dueDate, String list){
		try {
			service.newTask(task, dueDate, list, sessionToken);
			hideNewTaskPanel();
			tasksPanel.reloadTasks(list);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void editTask (int id, String task, String dueDate, Boolean done, String list) {
		try {
			service.editTask(id, task, dueDate, done, list, sessionToken);
			tasksPanel.reloadTasks(list);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void showLoginPanel (){
		if (loginPanel == null)
			loginPanel = new TaskListClientLogin();
		loginPanel.setVisible(true);
	}
	
	public void hideLoginPanel (){
		if (loginPanel != null) {
			loginPanel.dispose();
			loginPanel = null;
		}
	}
	
	public void showTasksPanel (){
		if (tasksPanel == null)
			tasksPanel = new TaskListClient();
		tasksPanel.setVisible(true);
	}
	
	public void hideTasksPanel (){
		if (tasksPanel != null) {
			tasksPanel.dispose();
			tasksPanel = null;
		}
	}
	
	public void showNewListPanel (){
		if (newListPanel == null)
			newListPanel = new NewList();
		newListPanel.setVisible(true);
	}
	
	public void hideNewListPanel (){
		if (newListPanel != null) {
			newListPanel.dispose();
			newListPanel = null;
		}
	}
	
	public void showNewTaskPanel (String list){
		if (newTaskPanel == null)
			newTaskPanel = new NewTask(list);
		newTaskPanel.setVisible(true);
	}
	
	public void hideNewTaskPanel (){
		if (newTaskPanel != null) {
			newTaskPanel.dispose();
			newTaskPanel = null;
		}
	}
	
	public void showEditTaskPanel (int id, String task, String dueDate, Boolean done, String list){
		if (editTaskPanel == null)
			editTaskPanel = new EditTask(id, task, dueDate, done, list);
		editTaskPanel.setVisible(true);
	}
	
	public void hideEditTaskPanel (){
		if (editTaskPanel != null) {
			editTaskPanel.dispose();
			editTaskPanel = null;
		}
	}
}
