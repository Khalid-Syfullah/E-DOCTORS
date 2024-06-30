package com.incubation_lab.edoctors.MainActivity.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.incubation_lab.edoctors.Login.LoginActivity;
import com.incubation_lab.edoctors.Login.ui.LoginViewModel;
import com.incubation_lab.edoctors.Models.UserDataModel;
import com.incubation_lab.edoctors.Models.UserImageModel;
import com.incubation_lab.edoctors.R;
import com.incubation_lab.edoctors.Repository.Remote.RetroInstance;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.incubation_lab.edoctors.Repository.UserRepository.LOGIN_SHARED_PREFS;
import static com.incubation_lab.edoctors.StaticData.PICTURE_UPDATE_SUCCESS;

public class ViewProfileFragment extends Fragment {

    public static final int PICTURE_CODE = 12;
    private LoginViewModel loginViewModel;
    private TextView username, phone, date_of_birth, bloodGroup, update_picture_btn;
    private ImageView profileImage;
    private ImageView alertImage;
    private Button logoutBtn, updateProfileBtn;

    private UserDataModel userData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_profile, container, false);
        username = root.findViewById(R.id.profile_username);
        phone = root.findViewById(R.id.profile_phone);
        date_of_birth = root.findViewById(R.id.profile_date_of_birth);
        bloodGroup = root.findViewById(R.id.profile_Blood_group);
        update_picture_btn = root.findViewById(R.id.update_profile_picture_btn);
        profileImage = root.findViewById(R.id.profile_image);
        logoutBtn = root.findViewById(R.id.profile_log_out);
        updateProfileBtn = root.findViewById(R.id.profile_update);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        userData = loginViewModel.getLoggedInUser().getValue();

        username.setText(userData.getFirstName() + " " + userData.getLastName());
        phone.setText(userData.getPhone());
        date_of_birth.setText(userData.getAge());
        bloodGroup.setText(userData.getEmail());
        Picasso.get().load(RetroInstance.BASE_URL + "/" + userData.getImageUrl()).placeholder(R.drawable.account).into(profileImage);


        update_picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdatePictureAlertDialog();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog("Are you sure ? ", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(LOGIN_SHARED_PREFS, MODE_PRIVATE);
                        sharedPreferences.edit().clear().apply();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });

            }
        });


        return root;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_CODE && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                alertImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void uploadImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        loginViewModel.updatePicture(new UserImageModel(loginViewModel.getLoggedInUser().getValue().getPhone(), encodedImage));

    }


    private void showUpdatePictureAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View pictureAlert = inflater.inflate(R.layout.select_picture_alert, null);
        Button selectPictureBtn = pictureAlert.findViewById(R.id.select_picture_btn);
        Button alertUpdateImgBtn = pictureAlert.findViewById(R.id.alert_update_btn);
        alertImage = pictureAlert.findViewById(R.id.alert_image);
        builder.setCancelable(true);
        builder.setView(pictureAlert);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        selectPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICTURE_CODE);
            }
        });

        alertUpdateImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertImage.getDrawable() instanceof android.graphics.drawable.VectorDrawable) {
                    Toast.makeText(getContext(), "Not Supported", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bitmap bitmap = ((BitmapDrawable) alertImage.getDrawable()).getBitmap();
                uploadImage(bitmap);
                loginViewModel.getLoginStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if (s.equals(PICTURE_UPDATE_SUCCESS)) {
                            Picasso.get().load(RetroInstance.BASE_URL + "/" + userData.getImageUrl()).memoryPolicy(MemoryPolicy.NO_CACHE).into(profileImage);

                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void showAlertDialog(String title, View view, View.OnClickListener positiveClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title).setView(view).setNegativeButton("Cancel", null)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                positiveClickListener.onClick(null);
                            }
                        }

                ).show();
    }
}
