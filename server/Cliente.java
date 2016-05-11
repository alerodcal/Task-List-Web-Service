import es.uc3m.www.WS.tasklistService.*;
    import HP.axis.services.tasklistService.*;

    public class Cliente {

      public static void main(String [] args) throws Exception {

        // Crear un servicio agenda
        TasklistServiceService port = new TasklistServiceServiceLocator();

        // Obtener un stub que utilizaremos para invocar los métodos remotos
        TasklistService service = port.gettasklistService();

                try {
            // Create new user
            service.registerUser("user3", "pass2");
            // Login user
            String token = service.loginUser("user3", "pass2");
            // Create new task list for that user
            service.newTaskList("newtasklist", token);
            // Print all lists for that user
            service.getLists(token);
            // Create a new task in the task list
            service.newTask("Come hoy", "10/06/78", "newtasklist", token);
            // Print all tasks in the task list
            service.getTasks("newtasklist", token);
            // Delete task from that task list
            service.deleteTask(1, "newtasklist", token);
            // Delete task list for that user
            service.deleteTaskList("newtasklist", token);
            // Delete user
            service.deleteUser(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
      }
    }