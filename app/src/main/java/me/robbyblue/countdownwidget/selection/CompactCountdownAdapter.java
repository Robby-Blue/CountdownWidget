package me.robbyblue.countdownwidget.selection;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.robbyblue.countdownwidget.Countdown;
import me.robbyblue.countdownwidget.R;

public class CompactCountdownAdapter extends RecyclerView.Adapter<CompactCountdownViewHolder> {

    ArrayList<Countdown> countdowns;
    ClickCallback listener;

    public CompactCountdownAdapter(ArrayList<Countdown> countdowns, ClickCallback listener) {
        this.countdowns = countdowns;
        this.listener = listener;
    }

    @NonNull
    public CompactCountdownViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CompactCountdownViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compact_countdown, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompactCountdownViewHolder holder, int position) {
        Countdown countdown = countdowns.get(position);

        holder.nameLabel.setText(countdown.getName());
        holder.dateLabel.setText(countdown.getFormattedDate());

        holder.view.setOnClickListener((l) -> this.listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return countdowns.size();
    }


    interface ClickCallback {
        void onClick(int index);
    }

}
