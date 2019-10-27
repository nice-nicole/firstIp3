package com.example.firstip3.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firstip3.R;
import com.example.firstip3.models.Business;
import com.example.firstip3.ui.NewDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewViewHolder> {
    private List<Business> mNews;
    private Context mContext;

    public NewAdapter(Context context, List<Business> news) {
        mContext = context;
        mNews = news;
    }

    @Override
    public NewAdapter.NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_list_item, parent, false);
        NewViewHolder viewHolder = new NewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewAdapter.NewViewHolder holder, int position) {
        holder.bindNew(mNews.get(position));
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class NewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.newImageView)
        ImageView mNewImageView;
        @BindView(R.id.newNameTextView)
        TextView mNameTextView;
        @BindView(R.id.categoryTextView)
        TextView mCategoryTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;
        private Context mContext;

        public NewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindNew(Business newss) {
            mNameTextView.setText(newss.getName());
            mCategoryTextView.setText(newss.getCategories().get(0).getTitle());
            mRatingTextView.setText("Rating: " + newss.getRating() + "/5");
            Picasso.get().load(newss.getImageUrl()).into(mNewImageView);
        }
        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, NewDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("restaurants", Parcels.wrap(mNews));
            mContext.startActivity(intent);
        }
    }
}


