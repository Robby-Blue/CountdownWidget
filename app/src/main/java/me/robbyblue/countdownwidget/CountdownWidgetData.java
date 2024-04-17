package me.robbyblue.countdownwidget;

import org.json.JSONObject;

import java.util.UUID;

public class CountdownWidgetData {

    private int appWidgetId;
    private UUID countdownUUID;

    public CountdownWidgetData(JSONObject countdownData) {
        try {
            this.appWidgetId = countdownData.getInt("appWidgetId");
            this.countdownUUID = UUID.fromString(countdownData.getString("countdownUUID"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CountdownWidgetData(int appWidgetId, UUID countdownUUID) {
        this.appWidgetId = appWidgetId;
        this.countdownUUID = countdownUUID;
    }

    public CountdownWidgetData(int appWidgetId) {
        this.appWidgetId = appWidgetId;
        this.countdownUUID = null;
    }

    public JSONObject toJSON() {
        try {
            JSONObject widgetJSON = new JSONObject();
            widgetJSON.put("appWidgetId", appWidgetId);
            widgetJSON.put("countdownUUID", countdownUUID.toString());
            return widgetJSON;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getAppWidgetId() {
        return appWidgetId;
    }

    public UUID getCountdownUUID() {
        return countdownUUID;
    }

    public void setCountdownUUID(UUID countdownUUID) {
        this.countdownUUID = countdownUUID;
    }
}
