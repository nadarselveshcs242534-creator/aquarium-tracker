package com.aquarium.dao;

import com.aquarium.model.WaterRecord;
import java.util.ArrayList;
import java.util.List;

public class WaterRecordDAO {
    private static final List<String> tanks = new ArrayList<>();
    private static final List<WaterRecord> records = new ArrayList<>();

    static {
        tanks.add("Tank 1 - Community Planted");
        tanks.add("Tank 2 - Marine Reef");
        tanks.add("Tank 3 - Cichlid Setup");
    }

    public static List<String> getTanks() { 
        return tanks; 
    }
    
    public static void addTank(String name) { 
        if (name != null && !tanks.contains(name.trim())) {
            tanks.add(name.trim());
        }
    }
    
    public static void removeTank(String name) { 
        tanks.remove(name); 
        records.removeIf(r -> r.getTankName().equals(name)); 
    }

    public void addRecord(WaterRecord record) { 
        records.add(record); 
    }
    
    public List<WaterRecord> getRecordsByTank(String tankName) {
        List<WaterRecord> filtered = new ArrayList<>();
        for (WaterRecord r : records) {
            if (r.getTankName().equals(tankName)) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    public static String exportToCSV(String tankName) {
        StringBuilder sb = new StringBuilder();
        sb.append("Tank Name,Date Logged,pH,Ammonia,Nitrite,Nitrate,Temperature (°C)\n");
        for (WaterRecord r : records) {
            if (tankName == null || tankName.isEmpty() || r.getTankName().equals(tankName)) {
                sb.append(r.getTankName()).append(",");
                sb.append(r.getDateLogged()).append(",");
                sb.append(r.getPh()).append(",");
                sb.append(r.getAmmonia()).append(",");
                sb.append(r.getNitrite()).append(",");
                sb.append(r.getNitrate()).append(",");
                sb.append(r.getTemperature()).append("\n");
            }
        }
        return sb.toString();
    }
}