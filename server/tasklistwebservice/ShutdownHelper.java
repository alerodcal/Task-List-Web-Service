package tasklistwebservice;

public class ShutdownHelper extends Thread {
	tasklistService service = null;

   public ShutdownHelper(tasklistService service) {
	   super();
	   this.service = service;
   }

   public void run() {
   	    System.out.println("Disconnecting from database...");
   	    service.close();
   }
}