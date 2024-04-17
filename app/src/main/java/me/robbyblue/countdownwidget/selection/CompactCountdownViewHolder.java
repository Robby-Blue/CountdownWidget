package me.robbyblue.countdownwidget.selection;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import me.robbyblue.countdownwidget.R;

public class CompactCountdownViewHolder extends RecyclerView.ViewHolder {

    View view;
    TextView nameLabel;
    TextView dateLabel;

    public CompactCountdownViewHolder(View itemView){
        super(itemView);
        view = itemView;
        nameLabel = itemView.findViewById(R.id.countdown_name);
        dateLabel = itemView.findViewById(R.id.countdown_date);
    }
}
