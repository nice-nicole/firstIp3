package com.example.firstip3.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstip3.Constants;
import com.example.firstip3.R;
import com.example.firstip3.models.Business;
import com.example.firstip3.models.Category;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewDetailFragment extends Fragment implements View.OnClickListener {
//    @BindView(R.id.newImageView)
//    ImageView mImageLabel;
    @BindView(R.id.newNameTextView)
    TextView mNameLabel;
    @BindView(R.id.moreTextView)
    TextView mCategoriesLabel;
    @BindView(R.id.ratingTextView)
    TextView mRatingLabel;
    @BindView(R.id.websiteTextView)
    TextView mWebsiteLabel;
    @BindView(R.id.phoneTextView)
    TextView mPhoneLabel;
    @BindView(R.id.addressTextView)
    TextView mAddressLabel;
    @BindView(R.id.saveNewsButton)
    Button mSaveNewsButton;

    private Business mNew;

    public NewDetailFragment() {
        // Required empty public constructor
    }

    public static NewDetailFragment newInstance(Business newss) {
        NewDetailFragment newDetailFragment = new NewDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("newss", Parcels.wrap(newss));
        newDetailFragment.setArguments(args);
        return newDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNew = Parcels.unwrap(getArguments().getParcelable("newss"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_detail, container, false);
        ButterKnife.bind(this, view);

//        Picasso.get().load(mNew.getImageUrl()).into(mImageLabel);

        List<String> categories = new ArrayList<>();

        for (Category category : mNew.getCategories()) {
            categories.add(category.getTitle());
        }

        mNameLabel.setText(mNew.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", categories));
        mRatingLabel.setText(Double.toString(mNew.getRating()) + "/5");
        mPhoneLabel.setText(mNew.getPhone());
        mAddressLabel.setText(mNew.getLocation().toString());

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);
        mSaveNewsButton.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mNew.getUrl()));
            startActivity(webIntent);
        }
        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mNew.getPhone()));
            startActivity(phoneIntent);
        }
        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mNew.getCoordinates().getLatitude()
                            + "," + mNew.getCoordinates().getLongitude()
                            + "?q=(" + mNew.getName() + ")"));
            startActivity(mapIntent);
        }
        if (v == mSaveNewsButton) {
            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_NEWS);
            restaurantRef.push().setValue(mNew);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }

    }
}
