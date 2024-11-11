package org.example;

public class TradeData {
    private String direction;
    private int year;
    private String date;
    private String weekday;
    private String country;
    private String commodity;
    private String transportMode;
    private String measure;
    private long value;
    private long cumulative;

    public TradeData(String direction, int year, String date, String weekday, String country, String commodity, String transportMode, String measure, long value, long cumulative) {
        this.direction = direction;
        this.year = year;
        this.date = date;
        this.weekday = weekday;
        this.country = country;
        this.commodity = commodity;
        this.transportMode = transportMode;
        this.measure = measure;
        this.value = value;
        this.cumulative = cumulative;
    }

    public String getDirection() {
        return direction;
    }

    public int getYear() {
        return year;
    }

    public String getDate() {
        return date;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getCountry() {
        return country;
    }

    public String getCommodity() {
        return commodity;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public String getMeasure() {
        return measure;
    }

    public long getValue() {
        return value;
    }

    public long getCumulative() {
        return cumulative;
    }


    @Override
    public String toString() {
        return direction + ", " + year + ", " + date + ", " + weekday + ", " + country + ", " + commodity + ", " + transportMode + ", " + measure + ", " + value + ", " + cumulative;
    }

    public String getMonth() {
        String month = getDate().split("/")[1].trim();
        switch (month) {
            case "01":
                return "January";
            case "02":
                return "February";
            case "03":
                return "March";
            case "04":
                return "April";
            case "05":
                return "May";
            case "06":
                return "June";
            case "07":
                return "July";
            case "08":
                return "August";
            case "09":
                return "September";
            case "10":
                return "October";
            case "11":
                return "November";
            case "12":
                return "December";
            default: return "Unknown Month";
        }
    }
}