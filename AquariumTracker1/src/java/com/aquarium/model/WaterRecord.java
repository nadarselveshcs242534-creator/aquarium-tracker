package com.aquarium.model;

public class WaterRecord {
    
    private String tankName;
    private String dateLogged;
    private double ph, ammonia, nitrite, nitrate, temperature;

    public WaterRecord(String tankName, String dateLogged, double ph, double ammonia, double nitrite, double nitrate, double temperature) {
        this.tankName = tankName;
        this.dateLogged = dateLogged;
        this.ph = ph;
        this.ammonia = ammonia;
        this.nitrite = nitrite;
        this.nitrate = nitrate;
        this.temperature = temperature;
    }

    public String getTankName() { return tankName; }
    public String getDateLogged() { return dateLogged; }
    public double getPh() { return ph; }
    public double getAmmonia() { return ammonia; }
    public double getNitrite() { return nitrite; }
    public double getNitrate() { return nitrate; }
    public double getTemperature() { return temperature; }

    public String getAlertStatus() {
        StringBuilder alert = new StringBuilder();
        if (ammonia > 0.0) alert.append("Lethal Ammonia! ");
        if (nitrite > 0.2) alert.append("Dangerous Nitrite! ");
        if (nitrate > 40.0) alert.append("High Nitrate! ");
        if (ph < 6.5 || ph > 8.0) alert.append("pH Imbalance! ");
        if (temperature < 22.0 || temperature > 28.0) alert.append("Temp Warning! ");
        return alert.length() == 0 ? "Safe" : alert.toString();
    }
}