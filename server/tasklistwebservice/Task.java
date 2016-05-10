package tasklistwebservice;

public class Task {
	private int id = 0;
	private String task = null;
	private String duedate = null;
	private Boolean done = false;
	
	public Task (int id, String task, String duedate, Boolean done){
		this.id = id;
		this.task = task;
		this.duedate = duedate;
		this.done = done;
	}
	
	public int getId (){
		return id;
	}
	
	public String getTask (){
		return task;
	}

	public String getDueDate (){
		return duedate;
	}

	public Boolean getDone (){
		return done;
	}

	public void setId (int id){
		this.id = id;
	}
	
	public void setTask (String task){
		this.task = task;
	}
	
	public void setDueDate (String duedate){
		this.duedate = duedate;
	}

	public void setDone (Boolean done){
		this.done = done;
	}
}