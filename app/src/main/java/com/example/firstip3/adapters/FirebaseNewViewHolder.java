package com.example.firstip3.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firstip3.Constants;
import com.example.firstip3.R;
import com.example.firstip3.models.News;
import com.example.firstip3.ui.NewDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseNewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        View mView;
        Context mContext;

        public FirebaseNewViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindNew(News restaurant) {
            ImageView restaurantImageView = (ImageView) mView.findViewById(R.id.newImageView);
            TextView nameTextView = (TextView) mView.findViewById(R.id.newNameTextView);
            TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
            TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);

            Picasso.get().load(restaurant.getImageUrl()).into(restaurantImageView);

            nameTextView.setText(restaurant.getName());
            categoryTextView.setText(restaurant.getCategories().get(0));
            ratingTextView.setText("Rating: " + restaurant.getRating() + "/5");
        }

        @Override
        public void onClick(View view) {
            final ArrayList<News> restaurants = new ArrayList<>();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_NEWS);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        restaurants.add(snapshot.getValue(News.class));
                    }

                    int itemPosition = getLayoutPosition();

                    Intent intent = new Intent(mContext, NewDetailActivity.class);
                    intent.putExtra("position", itemPosition + "");
                    intent.putExtra("restaurants", Parcels.wrap(restaurants));

                    mContext.startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }


