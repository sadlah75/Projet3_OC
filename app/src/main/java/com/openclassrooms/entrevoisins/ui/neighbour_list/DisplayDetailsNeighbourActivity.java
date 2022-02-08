package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayDetailsNeighbourActivity extends AppCompatActivity {
    @BindView(R.id.activity_display_avatar)
    ImageView mAvatar;
    @BindView(R.id.activity_display_name_profile)
    TextView mNameProfile;
    @BindView(R.id.activity_display_name_profile_card)
    TextView mNameProfileCard;
    @BindView(R.id.activity_display_address_profile)
    TextView mAddress;
    @BindView(R.id.activity_display_phonenumber_profile)
    TextView mPhone;
    @BindView(R.id.activity_display_url_profile)
    TextView mUrl;
    @BindView(R.id.activity_display_aboutme)
    TextView mAboutMe;
    @BindView(R.id.activity_display_return)
    ImageView mReturn;
    @BindView(R.id.activity_display_add_fab)
    FloatingActionButton mAddFavorite;

    private static final String KEY_URL = "www.facebook.fr/";
    private NeighbourApiService mApiService;
    private Neighbour mNeighbour;
    private String mNeighbourImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details_neighbour);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();

        Intent intent = getIntent();
        if(intent.hasExtra("neighbour")) {
            mNeighbour = (Neighbour) intent.getExtras().getSerializable("neighbour");
        }
        displayDetails();
    }

    private void displayDetails() {
        mNeighbourImage = mNeighbour.getAvatarUrl();
        String name = mNeighbour.getName();

        Glide.with(this).load(mNeighbourImage).centerCrop().into(mAvatar);
        mNameProfile.setText(name);
        mNameProfileCard.setText(name);
        mAddress.setText(mNeighbour.getAddress());
        mPhone.setText(mNeighbour.getPhoneNumber());
        mUrl.setText(KEY_URL + name);
        mAboutMe.setText(mNeighbour.getAboutMe());

        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setFab(mNeighbour.isFavorite());
        mAddFavorite.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               mNeighbour.setFavorite(!mNeighbour.isFavorite());
               setFab(mNeighbour.isFavorite());
               mApiService.modifyNeighbour(mNeighbour);
            }
        });
    }


    private void setFab(boolean b) {
        if (b) {
            mAddFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_yellow_24dp));
        }else {
            mAddFavorite.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_border_yellow_24dp));
        }
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity,Neighbour neighbour) {
        Intent intent = new Intent(activity, DisplayDetailsNeighbourActivity.class);
        intent.putExtra("neighbour",neighbour);
        ActivityCompat.startActivity(activity, intent,null);
    }
}