package com.example.skillsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getName());
        holder.userDomain.setText(user.getDomain());
        holder.userDescription.setText(user.getDescription());
        holder.userRating.setRating(user.getRating());
        // You can set the user image here if available
        // holder.userImage.setImageResource(user.getImageResId());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userName;
        TextView userDomain;
        TextView userDescription;
        RatingBar userRating;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.imageUser);
            userName = itemView.findViewById(R.id.textUserName);
            userDomain = itemView.findViewById(R.id.textDomain);
            userDescription = itemView.findViewById(R.id.textDescription);
            userRating = itemView.findViewById(R.id.ratingBar);
        }
    }
}
