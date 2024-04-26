package me.robbyblue.countdownwidget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {

    public static String formatTimeDifference(long startTime, long endTime) {
        int maxSegmentCount = 2;
        int segmentCount = 0;

        ArrayList<TimeSegment> segments = getTimeDifference(startTime, endTime);
        if (segments.size() == 0) return "now";

        StringBuilder time = new StringBuilder();
        for (TimeSegment segment : segments) {
            if (segment.getCount() == 0)
                continue;
            if (segmentCount >= maxSegmentCount)
                continue;

            time.append(segment.getCount());
            time.append(segment.getUnit());
            time.append(" ");
            segmentCount += 1;
        }
        return time.toString().trim();
    }

    public static ArrayList<TimeSegment> getTimeDifference(long startTime, long endTime) {
        ArrayList<TimeSegment> segments = new ArrayList<>();

        if (endTime < startTime) {
            return segments;
        }

        // Convert UNIX timestamps to Date objects
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(endTime);

        // Convert Date objects to Calendar objects
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        // Calculate the difference in years, months, weeks, and days
        int yearDiff = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);
        int monthDiff = endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);
        int dayDiff = endCal.get(Calendar.DAY_OF_MONTH) - startCal.get(Calendar.DAY_OF_MONTH);
        int hourDiff = endCal.get(Calendar.HOUR_OF_DAY) - startCal.get(Calendar.HOUR_OF_DAY);

        if (hourDiff < 0) {
            dayDiff--;
            hourDiff += 24;
        }

        if (dayDiff < 0) {
            monthDiff--;
            dayDiff += startCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        if (monthDiff < 0) {
            monthDiff += 12;
            yearDiff--;
        }

        int weekDiff = dayDiff / 7;
        dayDiff = dayDiff % 7;

        segments.add(new TimeSegment(yearDiff, "y"));
        segments.add(new TimeSegment(monthDiff, "mo"));
        segments.add(new TimeSegment(weekDiff, "w"));
        segments.add(new TimeSegment(dayDiff, "d"));
        segments.add(new TimeSegment(hourDiff, "h"));

        return segments;
    }

    private static class TimeSegment {

        private final int count;
        private final String unit;

        public TimeSegment(int count, String unit) {
            this.count = count;
            this.unit = unit;
        }

        public int getCount() {
            return count;
        }

        public String getUnit() {
            return unit;
        }
    }

}
