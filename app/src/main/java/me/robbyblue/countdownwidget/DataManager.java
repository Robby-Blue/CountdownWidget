package me.robbyblue.countdownwidget;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;

public class DataManager {

    private static DataManager instance;
    File dataFile;

    ArrayList<Countdown> countdowns = new ArrayList<>();
    ArrayList<CountdownWidgetData> widgets = new ArrayList<>();

    private DataManager(Context context) {
        dataFile = new File(context.getFilesDir(), "data.json");
        loadData();
    }

    public Countdown getCountdownForWidget(int appWidgetId) {
        CountdownWidgetData widgetData = getWidgetById(appWidgetId);
        if (widgetData.getCountdownUUID() == null) return null;
        return getCountdownById(widgetData.getCountdownUUID());
    }

    public CountdownWidgetData getWidgetById(int id) {
        for (CountdownWidgetData widget : this.widgets) {
            if (widget.getAppWidgetId() == id) return widget;
        }
        CountdownWidgetData widget = new CountdownWidgetData(id);
        widgets.add(widget);
        return widget;
    }

    public Countdown getCountdownById(UUID uuid) {
        for (Countdown countdown : this.countdowns) {
            if (countdown.getUuid() == uuid) return countdown;
        }
        return null;
    }

    public void addCountdown(Countdown countdown) {
        this.countdowns.add(countdown);
        saveData();
    }

    private void loadData() {
        try {
            if (!dataFile.exists()) return;

            String fileContents = readFile(dataFile);
            if (fileContents == null) return;

            JSONObject data = new JSONObject(fileContents);
            JSONArray countdownsData = data.getJSONArray("countdowns");
            for (int i = 0; i < countdownsData.length(); i++) {
                JSONObject countdownData = countdownsData.getJSONObject(i);
                countdowns.add(new Countdown(countdownData));
            }

            JSONArray widgetsData = data.getJSONArray("widgets");
            for (int i = 0; i < widgetsData.length(); i++) {
                JSONObject widgetJSON = widgetsData.getJSONObject(i);
                widgets.add(new CountdownWidgetData(widgetJSON));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        try {
            JSONArray countdownsData = new JSONArray();
            for (Countdown countdown : countdowns) {
                countdownsData.put(countdown.toJSON());
            }

            JSONArray widgetsData = new JSONArray();
            for (CountdownWidgetData widget : widgets) {
                widgetsData.put(widget.toJSON());
            }

            JSONObject data = new JSONObject();
            data.put("countdowns", countdownsData);
            data.put("widgets", widgetsData);

            writeFile(dataFile, data.toString(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            StringBuilder res = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }

            return res.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeFile(File file, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Countdown> getCountdowns() {
        return countdowns;
    }

    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

}
