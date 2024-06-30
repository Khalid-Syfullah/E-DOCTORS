package com.incubationlab.edoctors_doctors.Repository;

public interface RemoteRequestInterface {
    void onSuccess(int code,String msg);
    void onFailure(String msg);
}
