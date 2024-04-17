package me.robbyblue.countdownwidget.selection;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.robbyblue.countdownwidget.Countdown;
import me.robbyblue.countdownwidget.CountdownWidget;
import me.robbyblue.countdownwidget.CountdownWidgetData;
import me.robbyblue.countdownwidget.DataManager;
import me.robbyblue.countdownwidget.R;

public class SelectCountdownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_countdown);

        int widgetId = getIntent().getIntExtra("widgetId", 0);

        DataManager data = DataManager.getInstance(this);
        ArrayList<Countdown> countdowns = data.getCountdowns();

        RecyclerView countdownRecycler = findViewById(R.id.compact_countdown_recycler);
        countdownRecycler.setLayoutManager(new LinearLayoutManager(this));
        CompactCountdownAdapter countdownAdapter = new CompactCountdownAdapter(countdowns, (index) -> {
            Countdown countdown = countdowns.get(index);

            CountdownWidgetData widget = data.getWidgetById(widgetId);
            widget.setCountdownUUID(countdown.getUuid());
            data.saveData();

            Intent updateIntent = new Intent(this, CountdownWidget.class);
            updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            sendBroadcast(updateIntent);
            finish();
        });
        countdownRecycler.setAdapter(countdownAdapter);
    }
}