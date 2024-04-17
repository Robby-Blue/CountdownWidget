package me.robbyblue.countdownwidget;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Countdown {

    private UUID uuid;
    private String name;
    private long startTime;
    private long endTime;

    public Countdown(JSONObject countdownData) {
        try {
            this.uuid = UUID.fromString(countdownData.getString("uuid"));
            this.name = countdownData.getString("name");
            this.startTime = countdownData.getLong("startTime");
            this.endTime = countdownData.getLong("endTime");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Countdown(String name, long endTime) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.startTime = System.currentTimeMillis();
        this.endTime = endTime;
    }

    public String getFormattedDate() {
        Date date = new Date(endTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }

    public double getPercentPassed() {
        if (System.currentTimeMillis() < startTime) return 0;
        if (System.currentTimeMillis() > endTime) return 100;

        long timeDelta = endTime - startTime;
        long currentTimeDelta = System.currentTimeMillis() - startTime;

        return (float) currentTimeDelta / timeDelta * 100;
    }

    public String formatEndTimeDelta() {
        long seconds = (this.endTime - System.currentTimeMillis()) / 1000;

        int minutes = (int) (seconds / 60.0);
        int hours = (int) Math.ceil(minutes / 60.0);
        int days = (int) Math.ceil(hours / 24.0);
        int weeks = (int) Math.ceil(days / 7.0);
        int months = (int) Math.ceil(days / 30.0);
        int years = (int) Math.ceil(days / 365.0);

        if (seconds < 0) return "now";
        if (minutes < 60) return "soon";
        if (hours < 24) return hours + "h";
        if (days < 7) return days + "d";
        if (days < 30) return weeks + "w";
        if (months < 12) return months + "mo";

        return years + "y";
    }

    public JSONObject toJSON() {
        try {
            JSONObject countdownData = new JSONObject();
            countdownData.put("uuid", this.uuid.toString());
            countdownData.put("name", this.name);
            countdownData.put("startTime", this.startTime);
            countdownData.put("endTime", this.endTime);
            return countdownData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

}
