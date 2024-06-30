package com.incubation_lab.edoctors.MainActivity.ui.doctors;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.incubation_lab.edoctors.MainActivity.ui.doctors.recycler.ReviewsAdapter;
import com.incubation_lab.edoctors.Models.AppointmentDataModel;
import com.incubation_lab.edoctors.Models.DoctorDataModel;

import com.incubation_lab.edoctors.Login.ui.LoginViewModel;
import com.incubation_lab.edoctors.Models.ReviewDataModel;
import com.incubation_lab.edoctors.R;
import com.incubation_lab.edoctors.Repository.Remote.OnReviewReceivedInterface;
import com.incubation_lab.edoctors.Repository.Remote.RetroInstance;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import static com.incubation_lab.edoctors.Repository.Remote.RetroInstance.BASE_URL;
import static com.incubation_lab.edoctors.StaticData.DOCTOR_BUNDLE_KEY;
import static com.incubation_lab.edoctors.StaticData.PICTURE_UPDATE_SUCCESS;

import java.util.ArrayList;

public class ViewDoctorProfileFragment extends Fragment {
    private TextView tvName,tvFee,tvDesignation,tvDegree,tvAbout,tvRating;
    private ImageView ivDoctorImage;
    private RatingBar rbDoctorRating;
    private Button getAppointmentBtn;
    private DoctorsViewModel doctorsViewModel;
    private LoginViewModel loginViewModel;
    private RecyclerView rvReviews;
    private ReviewsAdapter reviewsAdapter;
    private DoctorDataModel doctorData;

    public ViewDoctorProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_view_doctor_profile, container, false);

        doctorData = (DoctorDataModel) getArguments().getSerializable(DOCTOR_BUNDLE_KEY);
        Toast.makeText(getContext(), doctorData.getName(), Toast.LENGTH_SHORT).show();
        tvName = root.findViewById(R.id.doctor_profile_name);
        tvFee = root.findViewById(R.id.doctor_profile_fee);
        tvDesignation = root.findViewById(R.id.doctor_profile_designation);
        tvDegree = root.findViewById(R.id.doctor_profile_degree);
        tvAbout = root.findViewById(R.id.doctor_profile_about);
        ivDoctorImage = root.findViewById(R.id.doctor_profile_image);
        rbDoctorRating = root.findViewById(R.id.doctor_profile_ratingBar);
        getAppointmentBtn = root.findViewById(R.id.doctor_profile_get_appointment_btn);
        rvReviews = root.findViewById(R.id.doctor_profile_reviews);
        tvRating = root.findViewById(R.id.tv_doc_profile_rating);


        tvName.setText(doctorData.getName());
        tvFee.setText(doctorData.getFee()+ " Tk.");
        tvDesignation.setText(doctorData.getCurrentDesignation());
        tvDegree.setText(doctorData.getQualifications());
        tvAbout.setText(doctorData.getBio());
        Picasso.get().load(doctorData.getImageUrl()).into(ivDoctorImage);
        rbDoctorRating.setRating((float) (Float.parseFloat(doctorData.getRating())/5.0));
        tvRating.setText(doctorData.getRating()+"");


        doctorsViewModel = new ViewModelProvider(this).get(DoctorsViewModel.class);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        reviewsAdapter = new ReviewsAdapter();
        rvReviews.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        rvReviews.setAdapter(reviewsAdapter);

        doctorsViewModel.getReviews(doctorData.getBmdc(), new OnReviewReceivedInterface() {
            @Override
            public void onReceived(ArrayList<ReviewDataModel> reviewData) {
                reviewsAdapter.setAllReviews(reviewData);
                Toast.makeText(getContext(), "received data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(getContext(), "review fetching failed", Toast.LENGTH_SHORT).show();

            }
        });

        getAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAppointmentDialog();
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
//                datePickerDialog.getDatePicker();
//                datePickerDialog.show();
//                datePickerDialog.setTitle("Select Date");
//                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        month = month+1;
//                        String date = year +"-"+ (month<10?"0":"") +month+ "-"+ dayOfMonth;
//                        datePickerDialog.dismiss();
//                        doctorsViewModel.getAppointment(new AppointmentDataModel(loginViewModel.getLoggedInUser().getValue().getPhone(),doctorData.getBmdc(),date));
//                    }
//                });


            }
        });




        return root;
    }

    private void showAppointmentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View appointmentDialog = inflater.inflate(R.layout.get_appointment_dialog, null);
        Button btnSubmit = appointmentDialog.findViewById(R.id.btn_appointment_submit);
        EditText etMsg = appointmentDialog.findViewById(R.id.et_appointment_msg);
        DatePicker datePicker = appointmentDialog.findViewById(R.id.dp_appointment_date);
        builder.setCancelable(true);
        builder.setView(appointmentDialog);
        RadioGroup radioGroup = appointmentDialog.findViewById(R.id.rg_appointment_type);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rb_online){
                    datePicker.setVisibility(View.GONE);
                    etMsg.setVisibility(View.VISIBLE);
                }else {
                    datePicker.setVisibility(View.VISIBLE);
                    etMsg.setVisibility(View.GONE);
                }
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String symMsg =""+ etMsg.getText().toString();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                int year = datePicker.getYear();
                String date = year+"-"+ (month<10?"0":"")+month +"-" +day;
                String type = radioGroup.getCheckedRadioButtonId()==R.id.rb_online?"online":"offline";
                doctorsViewModel.getAppointment(new AppointmentDataModel(loginViewModel.getLoggedInUser().getValue().getPhone(),doctorData.getBmdc(),date,symMsg,type));

                alertDialog.dismiss();


            }
        });
    }

}