package com.incubation_lab.edoctors.MainActivity.ui.doctors;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.incubation_lab.edoctors.MainActivity.ui.doctors.recycler.DoctorsAdapter;
import com.incubation_lab.edoctors.R;

public class DoctorsFragment extends Fragment implements View.OnClickListener{

    private DoctorsViewModel mViewModel;
    private RecyclerView doctorRecyclerView;
    private MotionLayout motionLayout;
    private TextView selectedDept;
    private CardView coronaCard,neurologyCard,liverCard,dentistCard,eyeCard,cardioCard,gastroCard,physioCard,othersCard,respiratoryCard;
    private ImageView selected_deptImage;
    private EditText etDocName;
    private DoctorsAdapter doctorsAdapter;

    public static DoctorsFragment newInstance() {
        return new DoctorsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_doctors, container, false);
        doctorRecyclerView = root.findViewById(R.id.doctor_recycler);

        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        doctorsAdapter = new DoctorsAdapter();

        selectedDept=root.findViewById(R.id.choose_department_Textview);
        motionLayout=root.findViewById(R.id.doctors_motion_layout);
        coronaCard=root.findViewById(R.id.category_corona);
        neurologyCard=root.findViewById(R.id.category_neurologist);
        liverCard=root.findViewById(R.id.category_liver);
        dentistCard=root.findViewById(R.id.category_dentist);
        eyeCard=root.findViewById(R.id.category_eye);
        cardioCard=root.findViewById(R.id.category_cardiologist);
        gastroCard=root.findViewById(R.id.category_gastroenterologist);
        physioCard=root.findViewById(R.id.category_physician);
        othersCard=root.findViewById(R.id.category_others);
        respiratoryCard=root.findViewById(R.id.category_respiratory);
        selected_deptImage=root.findViewById(R.id.selected_dept_image);
        etDocName = root.findViewById(R.id.search_doctor_name);


        if(getArguments()!=null){
            String catName = getArguments().getString("category");
            int imgId = getArguments().getInt("imageId");

            selectedDept.setText(catName);
            selected_deptImage.setImageResource(imgId);

        }


        coronaCard.setOnClickListener(this);
        neurologyCard.setOnClickListener(this);
        liverCard.setOnClickListener(this);
        dentistCard.setOnClickListener(this);
        eyeCard.setOnClickListener(this);
        cardioCard.setOnClickListener(this);
        gastroCard.setOnClickListener(this);
        physioCard.setOnClickListener(this);
        othersCard.setOnClickListener(this);
        respiratoryCard.setOnClickListener(this);
        doctorRecyclerView.setAdapter(doctorsAdapter);
        mViewModel= new ViewModelProvider(this).get(DoctorsViewModel.class);
        mViewModel.getAllDoctors().observe(getViewLifecycleOwner(),doctorDataModels -> {
           doctorsAdapter.setAllDoctors(doctorDataModels);
           if(getArguments()!=null)
            doctorsAdapter.filter(getArguments().getString("category"),"");

        });

        etDocName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                doctorsAdapter.filter(selectedDept.getText().toString(),etDocName.getText().toString());

            }
        });




        return root;
    }


    @Override
    public void onClick(View v) {
        if (coronaCard.equals(v)) {
            selectedDept.setText(getString(R.string.corona_specialist));
            motionLayout.transitionToStart();
            selected_deptImage.setImageResource(R.drawable.icons_virus);
        }
        else if (cardioCard.equals(v)) {
            selectedDept.setText(getString(R.string.cardiologist));
            motionLayout.transitionToStart();
            selected_deptImage.setImageResource(R.drawable.icon_heart);
        }
        else if (neurologyCard.equals(v)) {
            selectedDept.setText(getString(R.string.neurologist));
            motionLayout.transitionToStart();
            selected_deptImage.setImageResource(R.drawable.icons_brain);
        }
        else if (liverCard.equals(v)) {
            selectedDept.setText(getString(R.string.liver));
            motionLayout.transitionToStart();
            selected_deptImage.setImageResource(R.drawable.icons_liver);
        }
        else if (dentistCard.equals(v)) {
            selectedDept.setText(getString(R.string.dentist));
            motionLayout.transitionToStart();
            selected_deptImage.setImageResource(R.drawable.icons_tooth);
        }

        else if (physioCard.equals(v)) {
            selectedDept.setText(getString(R.string.physician));
            motionLayout.transitionToStart();
            selected_deptImage.setImageResource(R.drawable.icons_wheelchair);
        }
        else if (othersCard.equals(v)) {
            selectedDept.setText(getString(R.string.others));
            motionLayout.transitionToStart();
            selected_deptImage.setImageResource(R.drawable.icon_doctor);
        }
        else if (gastroCard.equals(v)) {
            selectedDept.setText(getString(R.string.gastroenterologist));
            motionLayout.transitionToStart();
            selected_deptImage.setImageResource(R.drawable.icons_stomach);
        } else if (eyeCard.equals(v)) {
            selectedDept.setText(getString(R.string.eye));
            motionLayout.transitionToStart();
            selected_deptImage.setImageResource(R.drawable.icon_eye);
        }else if (respiratoryCard.equals(v)) {
            selectedDept.setText(getString(R.string.respiratory));
            motionLayout.transitionToStart();
            selected_deptImage.setImageResource(R.drawable.icons_lungs);
        }


        doctorsAdapter.filter(selectedDept.getText().toString(),etDocName.getText().toString());
    }
}