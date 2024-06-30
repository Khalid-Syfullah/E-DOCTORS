package com.incubation_lab.edoctors.Repository.Remote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


import com.incubation_lab.edoctors.Models.AppointmentDataModel;
import com.incubation_lab.edoctors.Models.BlogDataModel;
import com.incubation_lab.edoctors.Models.DoctorDataModel;
import com.incubation_lab.edoctors.Models.PrescriptionDataModel;
import com.incubation_lab.edoctors.Models.ResponseModel;
import com.incubation_lab.edoctors.Models.ReviewDataModel;
import com.incubation_lab.edoctors.Models.UserDataModel;
import com.incubation_lab.edoctors.Models.UserImageModel;

import java.util.ArrayList;

public interface RetroInterface {

    @POST("/api/user/register")
    Call<UserDataModel> register(@Body UserDataModel userDataModel);

    @POST("/api/user/login")
    Call<UserDataModel> login(@Body UserDataModel userDataModel);

    @POST("/api/user/validateToken")
    Call<UserDataModel> validateToken(@Body UserDataModel token);

    @POST("/api/user/findByPhone")
    Call<UserDataModel> findByPhone(@Body UserDataModel phone);

    @PATCH("/api/user/updatePassword")
    Call<UserDataModel> updatePassword(@Body UserDataModel userDataModel);

    @PATCH("/api/user/updatePicture")
    Call<UserImageModel> updatePicture(@Body UserImageModel encodedImage);

    @GET("/api/doctor/getAllDoctors")
    Call<ArrayList<DoctorDataModel>> getAllDoctors();


    @POST("/api/appointment/getAppointment")
    Call<AppointmentDataModel> getAppointment(@Body AppointmentDataModel appointmentDataModel);


    @POST("/api/appointment/getPatientAppointmentList/{patient_id}")
    Call<ArrayList<AppointmentDataModel>> getPatientAppointmentList(@Path("patient_id") String patientId);


    //    Doctors
    @POST("/api/doctor/login")
    Call<DoctorDataModel> loginDoctor(@Body DoctorDataModel doctorData);


    @POST("/api/appointment/getDoctorAppointmentList/{doctor_id}")
    Call<ArrayList<AppointmentDataModel>> getDoctorAppointmentList(@Path("doctor_id") String doctorId);


    @POST("/api/appointment/updateCurrentSerial")
    Call<AppointmentDataModel> updateCurrentSerial(@Body AppointmentDataModel appointmentDataModel);

    @GET("/api/appointment/getCurrentSerial/{doctor_id}")
    Call<AppointmentDataModel> getCurrentSerial(@Path("doctor_id") String doctorId);

    @GET("/api/prescription/getPrescription/{appointment_id}")
    Call<PrescriptionDataModel> getPrescription(@Path("appointment_id") String appointment_id);

    @POST("/api/appointment/addReview")
    Call<ResponseModel> addReview(@Body AppointmentDataModel appointmentDataModel);




    @GET("api/blog/getAllBlogs")
    Call<ArrayList<BlogDataModel>> getBlogs();


    @GET("api/doctor/getAllReviews/{doctor_id}")
    Call<ArrayList<ReviewDataModel>> getReviews(@Path("doctor_id") String doctorId);
}
