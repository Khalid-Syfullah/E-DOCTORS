package com.incubationlab.edoctors_doctors.Main.ui.HealthBlogs;

import static com.incubationlab.edoctors_doctors.StaticData.LoggedInDoctorData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.incubationlab.edoctors_doctors.Models.BlogDataModel;
import com.incubationlab.edoctors_doctors.R;
import com.squareup.picasso.Picasso;

public class BlogDetailsFragment extends Fragment {

    private TextView title, description,date;
    private ImageView blogImg;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_blog_details, container, false);
        title = root.findViewById(R.id.tv_details_blog_headline);
        description = root.findViewById(R.id.blog_description);
        blogImg = root.findViewById(R.id.blog_image);
        date = root.findViewById(R.id.tv_date);
        fab = root.findViewById(R.id.detailsBlogFab);
        BlogDataModel blogData = (BlogDataModel) getArguments().getSerializable("blog_data");
        title.setText(blogData.getTitle());
        description.setText(blogData.getDescription());
        date.setText(blogData.getDate());

        Picasso.get().load(blogData.getImageUrl()).into(blogImg);

        fab.setVisibility(blogData.getDoctorId().equals(LoggedInDoctorData.getValue().getDoctorId())?View.VISIBLE:View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("blog_data",blogData);
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_main).navigate(R.id.action_navigation_blog_details_to_navigation_add_blog,bundle);

            }
        });



        return root;
    }
}
