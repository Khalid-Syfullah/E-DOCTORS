package com.incubationlab.edoctors_doctors.Main.ui.HealthBlogs;

import static android.app.Activity.RESULT_OK;

import static com.incubationlab.edoctors_doctors.StaticData.LoggedInDoctorData;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.incubationlab.edoctors_doctors.Models.BlogDataModel;
import com.incubationlab.edoctors_doctors.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class AddNewBlogFragment extends Fragment {
    public static final int PICTURE_CODE = 12;
    private int mode = 0;

    private ImageView blogImg;
    private EditText etHeadline,etDescription;
    private Button btnSubmit;
    private BlogViewModel blogViewModel;
    private  BlogDataModel oldBlogData=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View root = inflater.inflate(R.layout.fragment_add_new_blog, container, false);
         etDescription = root.findViewById(R.id.etBlogDescription);
         etHeadline = root.findViewById(R.id.etBlogHeadline);
         blogImg = root.findViewById(R.id.iv_blog);
         btnSubmit = root.findViewById(R.id.btn_blog_submit);
         if(getArguments()!=null && getArguments().getSerializable("blog_data")!= null){
             setData();
         }

         blogViewModel = new ViewModelProvider(this).get(BlogViewModel.class);

         blogImg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent();
                 intent.setType("image/*");
                 intent.setAction(Intent.ACTION_GET_CONTENT);
                 startActivityForResult(intent, PICTURE_CODE);
             }
         });
         btnSubmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 uploadBlog();
             }
         });

         return root;
    }

    private void setData() {
        oldBlogData = (BlogDataModel) getArguments().getSerializable("blog_data");

        etHeadline.setText(oldBlogData.getTitle());
        etDescription.setText(oldBlogData.getDescription());
        Picasso.get().load(oldBlogData.getImageUrl()).into(blogImg);
        btnSubmit.setText("Update");
        mode = 1;
    }

    private void uploadBlog() {
        Bitmap bitmap = ((BitmapDrawable) blogImg.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
        String title = etHeadline.getText().toString();
        String description = etDescription.getText().toString();
        if(mode == 0) {
            Toast.makeText(getContext(), "uploading", Toast.LENGTH_SHORT).show();
            blogViewModel.uploadBlog(new BlogDataModel(LoggedInDoctorData.getValue().getDoctorId(), title, encodedImage, description));
        }
        else {
            Toast.makeText(getContext(), "updating", Toast.LENGTH_SHORT).show();

            blogViewModel.updateBlog(new BlogDataModel(oldBlogData,LoggedInDoctorData.getValue().getDoctorId(), title, encodedImage, description));
        }




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_CODE && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                blogImg.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

