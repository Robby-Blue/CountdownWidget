package me.robbyblue.countdownwidget;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CountdownViewHolder extends RecyclerView.ViewHolder {

    View view;
    TextView nameLabel;
    TextView dateLabel;
    ProgressBar progressBar;
    ImageView deleteButton;

    public CountdownViewHolder(View itemView){
        super(itemView);
        view = itemView;
        nameLabel = itemView.findViewById(R.id.countdown_name);
        dateLabel = itemView.findViewById(R.id.countdown_date);
        progressBar = itemView.findViewById(R.id.countdown_progress);
        deleteButton = itemView.findViewById(R.id.countdown_delete);
    }
}
