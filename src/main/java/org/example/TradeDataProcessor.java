package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TradeDataProcessor {
    private List<TradeData> tradeDataList;

    public TradeDataProcessor(){
        this.tradeDataList = new ArrayList<>();
    }

    public List<TradeData> getTradeDataList() {
        return tradeDataList;
    }

    public void readTradeDataFromFile(String csvFilePath){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
            reader.readLine();

            String line;
            while((line = reader.readLine())!=null){
                String[]fields = line.split(",");

                String direction = fields[0];
                int year = Integer.parseInt(fields[1].trim());
                String date = fields[2];
                String weekday = fields[3];
                String country = fields[4];
                String commodity = fields[5];
                String transportMode = fields[6];
                String measure = fields[7];
                long value;
                long cumulative;
                try{
                    value = Long.parseLong(fields[8].trim());
                    cumulative = Long.parseLong(fields[9].trim());
                }catch(NumberFormatException e){
                    continue;
                }
                TradeData newData = new TradeData(direction, year, date, weekday, country, commodity, transportMode, measure, value,cumulative);
                tradeDataList.add(newData);
            }
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
