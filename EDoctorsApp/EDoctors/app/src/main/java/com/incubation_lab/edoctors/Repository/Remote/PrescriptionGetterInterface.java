package com.incubation_lab.edoctors.Repository.Remote;

import com.incubation_lab.edoctors.Models.PrescriptionDataModel;

public interface PrescriptionGetterInterface {
    void onSuccess(PrescriptionDataModel prescriptionDataModel);
    void onFailure();
}
