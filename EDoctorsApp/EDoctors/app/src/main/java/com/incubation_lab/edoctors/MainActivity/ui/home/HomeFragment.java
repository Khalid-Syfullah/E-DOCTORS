package com.incubation_lab.edoctors.MainActivity.ui.home;

import static com.incubation_lab.edoctors.StaticData.DOCTOR_BUNDLE_KEY;
import static com.incubation_lab.edoctors.StaticData.LoggedInUserData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.incubation_lab.edoctors.MainActivity.ui.appointments.AppointmentsViewModel;
import com.incubation_lab.edoctors.MainActivity.ui.doctors.DoctorsViewModel;
import com.incubation_lab.edoctors.MainActivity.ui.health_blogs.BlogViewModel;
import com.incubation_lab.edoctors.MainActivity.ui.health_blogs.recycler.BlogAdapter;
import com.incubation_lab.edoctors.MainActivity.ui.health_blogs.recycler.BlogClickListener;
import com.incubation_lab.edoctors.MainActivity.ui.home.recycler.CategoriesAdapter;
import com.incubation_lab.edoctors.MainActivity.ui.home.recycler.CategoryClickListener;
import com.incubation_lab.edoctors.MainActivity.ui.home.recycler.DoctorsAdapterHome;
import com.incubation_lab.edoctors.MainActivity.ui.home.recycler.OnDoctorClickListenerHome;
import com.incubation_lab.edoctors.Models.AppointmentDataModel;
import com.incubation_lab.edoctors.Models.BlogDataModel;
import com.incubation_lab.edoctors.Models.CategoryDataModel;
import com.incubation_lab.edoctors.Models.DoctorDataModel;
import com.incubation_lab.edoctors.R;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView greetText,tvAppointmentNotice,tvTopDoctorsViewAll,tvTopBlogsViewAll;
    private DoctorsViewModel doctorsViewModel;

    private RecyclerView rvCategories,rvDoctors,rvBlogs;
    private CategoriesAdapter categoriesAdapter ;
    private BlogAdapter blogAdapter ;
    private DoctorsAdapterHome doctorsAdapterHome;
    private BlogViewModel blogViewModel;
    private AppointmentsViewModel appointmentsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        doctorsViewModel = new ViewModelProvider(this).get(DoctorsViewModel.class);
        appointmentsViewModel = new ViewModelProvider(this).get(AppointmentsViewModel.class);
        blogViewModel = new ViewModelProvider(this).get(BlogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        greetText = root.findViewById(R.id.greet_text);
        rvCategories = root.findViewById(R.id.rv_categories);
        rvDoctors = root.findViewById(R.id.rv_top_doctors);
        rvBlogs = root.findViewById(R.id.rv_top_blogs);
        tvAppointmentNotice = root.findViewById(R.id.appointment_notice);
        tvTopDoctorsViewAll = root.findViewById(R.id.top_doctors_view_all);
        tvTopBlogsViewAll = root.findViewById(R.id.top_blogs_view_all);
        categoriesAdapter = new CategoriesAdapter(new CategoryClickListener() {
            @Override
            public void onclick(CategoryDataModel categoryData) {
                Bundle bundle = new Bundle();
                bundle.putString("category",categoryData.getCatName());
                bundle.putInt("imageId",categoryData.getCatImgId());
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_navigation_home_to_navigation_doctors,bundle);

            }
        });
        doctorsAdapterHome = new DoctorsAdapterHome(new OnDoctorClickListenerHome() {
            @Override
            public void onclick(DoctorDataModel doctorData) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(DOCTOR_BUNDLE_KEY,doctorData);
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_navigation_home_to_navigation_doctor_profile,bundle);

            }
        });

        rvCategories.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvCategories.setAdapter(categoriesAdapter);
        setCategoryData();

        rvDoctors.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        rvDoctors.setAdapter(doctorsAdapterHome);

        blogAdapter = new BlogAdapter(new BlogClickListener() {
            @Override
            public void onclick(BlogDataModel blogData) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("blog_data",blogData);
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_navigation_home_to_navigation_blog_detail,bundle);

            }
        },3);

        doctorsViewModel.getAllDoctors().observe(getViewLifecycleOwner(),doctorDataModels -> {
            doctorsAdapterHome.setDoctorsList(doctorDataModels);

        });

        blogViewModel.getAllBlogs().observe(getViewLifecycleOwner(),blogDataModels -> {

            blogAdapter.setAllBlogs(blogDataModels);

        });

        rvBlogs.setAdapter(blogAdapter);
        rvBlogs.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        appointmentsViewModel.getPatientAppointmentList(LoggedInUserData.getValue().getPhone()).observe(getViewLifecycleOwner(), new Observer<ArrayList<AppointmentDataModel>>() {
            @Override
            public void onChanged(ArrayList<AppointmentDataModel> appointmentDataModels) {
                tvAppointmentNotice.setVisibility(appointmentDataModels.size() > 0 ? View.VISIBLE : View.GONE);
            }
        });


        Date date = new Date();
        int h = date.getHours();
        String userName =  LoggedInUserData.getValue().getFirstName();

        if(h<3 || h>21)
            greetText.setText("Good Night\n" + userName);
        else if(h<12)
            greetText.setText("Good Morning\n" + userName);
        else if(h<18)
            greetText.setText("Good Afternoon\n" + userName);
        else
            greetText.setText("Good Evening\n" + userName);

        tvTopDoctorsViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_navigation_home_to_navigation_doctors);

            }
        });
        tvTopBlogsViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_navigation_home_to_navigation_health_blogs);

            }
        });





        return root;
    }

    private void setCategoryData() {
        ArrayList<CategoryDataModel> categoryData=new ArrayList<>();

        categoryData.add(new CategoryDataModel(getString(R.string.corona_specialist),R.drawable.icons_virus));
        categoryData.add(new CategoryDataModel(getString(R.string.cardiologist),R.drawable.icon_heart));
        categoryData.add(new CategoryDataModel(getString(R.string.liver),R.drawable.icons_liver));
        categoryData.add(new CategoryDataModel(getString(R.string.respiratory),R.drawable.icons_lungs));
        categoryData.add(new CategoryDataModel(getString(R.string.neurologist),R.drawable.icons_brain));
        categoryData.add(new CategoryDataModel(getString(R.string.gastroenterologist),R.drawable.icons_stomach));
        categoryData.add(new CategoryDataModel(getString(R.string.physician),R.drawable.icons_wheelchair));
        categoryData.add(new CategoryDataModel(getString(R.string.eye),R.drawable.icon_eye));
        categoryData.add(new CategoryDataModel(getString(R.string.others),R.drawable.icon_heartbit));


        categoriesAdapter.setCategoryData(categoryData);
    }
}