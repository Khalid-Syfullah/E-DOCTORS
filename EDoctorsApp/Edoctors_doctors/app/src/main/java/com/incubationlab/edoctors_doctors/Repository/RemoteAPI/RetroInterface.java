package com.incubationlab.edoctors_doctors.Repository.RemoteAPI;



import com.incubationlab.edoctors_doctors.Models.AppointmentDataModel;
import com.incubationlab.edoctors_doctors.Models.BlogDataModel;
import com.incubationlab.edoctors_doctors.Models.DoctorDataModel;
import com.incubationlab.edoctors_doctors.Models.PrescriptionDataModel;
import com.incubationlab.edoctors_doctors.Models.ResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroInterface {

    @POST("/api/doctor/register")
    Call<ResponseModel> register(@Body DoctorDataModel doctorDataModel);

    @POST("/api/doctor/login")
    Call<DoctorDataModel> login(@Body DoctorDataModel doctorDataModel);

    @POST("/api/doctor/findDoctorByPhone/{doctor_phone}")
    Call<ResponseModel> findDoctorByPhone(@Path("doctor_phone") String doctor_phone);

    @POST("/api/doctor/login/{token}")
    Call<DoctorDataModel> loginWithToken(@Path("token") String token);

    @POST("/api/appointment/getDoctorAppointmentList/{doctor_id}")
    Call<ArrayList<AppointmentDataModel>> getAppointments(@Path("doctor_id") String doctorId);

    @POST("/api/appointment/updateCurrentSerial")
    Call<AppointmentDataModel> updateCurrentSerial(@Body AppointmentDataModel appointmentDataModel);

    @POST("/api/prescription/add")
    Call<ResponseModel> addPrescription(@Body PrescriptionDataModel prescriptionDataModel);

    @POST("api/blog/addBlog")
    Call<ResponseModel> uploadBlog(@Body BlogDataModel blogDataModel);

    @GET("api/blog/getAllBlogs")
    Call<ArrayList<BlogDataModel>> getBlogs();

    @POST("/api/blog/updateBlog")
    Call<ResponseModel> updateBlog(@Body BlogDataModel blogDataModel);
    @POST("/api/settings/setStatus")
    Call<ResponseModel> setStatus(@Body DoctorDataModel doctorDataModel);
    @POST("/api/settings/unSetStatus")
    Call<ResponseModel> unSetStatus(@Body DoctorDataModel doctorDataModel);
    
}
