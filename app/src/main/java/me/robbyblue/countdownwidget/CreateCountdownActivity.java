package me.robbyblue.countdownwidget;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class CreateCountdownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_countdown);
        Calendar c = Calendar.getInstance();

        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH);
        int currentDay = c.get(Calendar.DAY_OF_MONTH);

        Button createButton = findViewById(R.id.create_countdown_button);
        EditText nameEditText = findViewById(R.id.countdown_name_edittext);
        Button selectDateButton = findViewById(R.id.select_date_button);
        TextView dateText = findViewById(R.id.countdown_date_text);

        AtomicReference<Date> selectedDate = new AtomicReference<>();

        selectDateButton.setOnClickListener((v) -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (v2, year, month, day) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);

                selectedDate.set(calendar.getTime());

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
                String formattedDate = dateFormat.format(selectedDate.get());
                dateText.setText(formattedDate);
            }, currentYear, currentMonth, currentDay);
            datePickerDialog.show();
        });

        createButton.setOnClickListener((v) -> {
            String name = nameEditText.getText().toString().trim();
            if (name.length() == 0) return;
            Date date = selectedDate.get();
            if (date == null) return;

            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", name);
            resultIntent.putExtra("date", date);

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }
}