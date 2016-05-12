/**
 * TasklistService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package HP.axis.services.tasklistService;

public interface TasklistService extends java.rmi.Remote {
    public es.uc3m.www.WS.tasklistService.Task[] getTasks(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public void closeSession(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String registerUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public java.lang.String loginUser(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public void newTaskList(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public void deleteUser(java.lang.String in0) throws java.rmi.RemoteException;
    public void deleteTaskList(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException;
    public java.lang.String[] getLists(java.lang.String in0) throws java.rmi.RemoteException;
    public void newTask(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3) throws java.rmi.RemoteException;
    public void deleteTask(int in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public void editTask(int in0, java.lang.String in1, java.lang.String in2, java.lang.Boolean in3, java.lang.String in4, java.lang.String in5) throws java.rmi.RemoteException;
    public void main(java.lang.String[] in0) throws java.rmi.RemoteException;
    public void close() throws java.rmi.RemoteException;
}
