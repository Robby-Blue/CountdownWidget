package me.robbyblue.countdownwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import me.robbyblue.countdownwidget.selection.SelectCountdownActivity;

public class CountdownWidget extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();

        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, CountdownWidget.class));
            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        DataManager data = DataManager.getInstance(context);

        if (data.getCountdowns().size() == 0)
            return;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.countdown_widget);

        Intent intent = new Intent(context, SelectCountdownActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("widgetId", appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.countdown_name, pendingIntent);

        Countdown countdown = data.getCountdownForWidget(appWidgetId);
        // Update the widget
        if (countdown == null) {
            views.setTextViewText(R.id.countdown_name, "Click to add");
            appWidgetManager.updateAppWidget(appWidgetId, views);
            return;
        }

        views.setTextViewText(R.id.countdown_name, countdown.getName());
        views.setTextViewText(R.id.countdown_date, countdown.formatEndTimeDelta());

        views.setProgressBar(R.id.countdown_progress, 100, (int) countdown.getPercentPassed(), false);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}