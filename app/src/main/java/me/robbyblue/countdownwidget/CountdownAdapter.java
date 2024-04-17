package me.robbyblue.countdownwidget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CountdownAdapter extends RecyclerView.Adapter<CountdownViewHolder> {

    ArrayList<Countdown> countdowns;

    public CountdownAdapter(ArrayList<Countdown> countdowns) {
        this.countdowns = countdowns;
    }

    @NonNull
    public CountdownViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CountdownViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_countdown, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountdownViewHolder holder, int position) {
        Countdown countdown = countdowns.get(position);

        holder.nameLabel.setText(countdown.getName());
        holder.dateLabel.setText(countdown.getFormattedDate());
        holder.progressBar.setProgress((int) countdown.getPercentPassed());
        holder.deleteButton.setOnClickListener((v) -> {
            int removePos = holder.getAdapterPosition();
            countdowns.remove(removePos);
            notifyItemRemoved(removePos);
        });
    }

    @Override
    public int getItemCount() {
        return countdowns.size();
    }
}
