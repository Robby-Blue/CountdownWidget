package me.robbyblue.countdownwidget;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    RecyclerView countdownRecycler;
    CountdownAdapter countdownAdapter;
    DataManager data;

    ActivityResultLauncher<Intent> createCountdownLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() != RESULT_OK) return;
        Intent intent = result.getData();
        if (intent == null) return;

        String name = intent.getStringExtra("name");
        Date date = (Date) intent.getSerializableExtra("date");
        long epoch = date.getTime();

        data.addCountdown(new Countdown(name, epoch));

        int position = data.getCountdowns().size();

        countdownAdapter.notifyItemInserted(position);
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = DataManager.getInstance(this);

        Intent updateIntent = new Intent(this, CountdownWidget.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(updateIntent);

        countdownRecycler = findViewById(R.id.countdown_recycler);
        countdownRecycler.setLayoutManager(new LinearLayoutManager(this));
        countdownAdapter = new CountdownAdapter(data.getCountdowns());
        countdownRecycler.setAdapter(countdownAdapter);

        findViewById(R.id.countdown_create_button).setOnClickListener((v) -> {
            Intent intent = new Intent(this, CreateCountdownActivity.class);
            createCountdownLauncher.launch(intent);
        });
    }
}