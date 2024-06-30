package com.incubation_lab.edoctors.Repository.Remote;

public interface RemoteRequestInterface {
    void onSuccess(int code,String msg);
    void onFailure(String msg);
}
