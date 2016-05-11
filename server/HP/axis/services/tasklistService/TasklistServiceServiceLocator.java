/**
 * TasklistServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package HP.axis.services.tasklistService;

public class TasklistServiceServiceLocator extends org.apache.axis.client.Service implements HP.axis.services.tasklistService.TasklistServiceService {

    public TasklistServiceServiceLocator() {
    }


    public TasklistServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TasklistServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for tasklistService
    private java.lang.String tasklistService_address = "http://HP:8888/axis/services/tasklistService";

    public java.lang.String gettasklistServiceAddress() {
        return tasklistService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String tasklistServiceWSDDServiceName = "tasklistService";

    public java.lang.String gettasklistServiceWSDDServiceName() {
        return tasklistServiceWSDDServiceName;
    }

    public void settasklistServiceWSDDServiceName(java.lang.String name) {
        tasklistServiceWSDDServiceName = name;
    }

    public HP.axis.services.tasklistService.TasklistService gettasklistService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(tasklistService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return gettasklistService(endpoint);
    }

    public HP.axis.services.tasklistService.TasklistService gettasklistService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            HP.axis.services.tasklistService.TasklistServiceSoapBindingStub _stub = new HP.axis.services.tasklistService.TasklistServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(gettasklistServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void settasklistServiceEndpointAddress(java.lang.String address) {
        tasklistService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (HP.axis.services.tasklistService.TasklistService.class.isAssignableFrom(serviceEndpointInterface)) {
                HP.axis.services.tasklistService.TasklistServiceSoapBindingStub _stub = new HP.axis.services.tasklistService.TasklistServiceSoapBindingStub(new java.net.URL(tasklistService_address), this);
                _stub.setPortName(gettasklistServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("tasklistService".equals(inputPortName)) {
            return gettasklistService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://HP:8888/axis/services/tasklistService", "tasklistServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://HP:8888/axis/services/tasklistService", "tasklistService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("tasklistService".equals(portName)) {
            settasklistServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
