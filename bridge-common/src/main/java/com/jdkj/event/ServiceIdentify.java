package com.jdkj.event;

public class ServiceIdentify {
    private String serviceName;
    private ServiceType serviceType;

    public ServiceIdentify(String serviceName, ServiceType serviceType) {
        this.serviceName = serviceName;
        this.serviceType = serviceType;
    }

    public ServiceIdentify() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

}
