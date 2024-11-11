package org.example;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TradeDataOverview {
    private TradeDataProcessor dataProcessor;

    public TradeDataOverview(){
        this.dataProcessor = new TradeDataProcessor();
    }

    public <T> List<T> getUniqueValues(Function<TradeData, T> mapper){
        dataProcessor.readTradeDataFromFile("effects-of-covid-19-on-trade-at-21-July-2021-provisional.csv");
        return dataProcessor.getTradeDataList().stream()
                .map(mapper)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Integer> getUniqueYears(){
        return getUniqueValues(TradeData::getYear);
    }
    public List<String> getUniqueCountries(){
        return getUniqueValues(TradeData::getCountry);
    }
    public List<String> getUniqueCommodities(){
        return getUniqueValues(TradeData::getCommodity);
    }
    public List<String> getUniqueTransportationMode(){
        return getUniqueValues(TradeData::getTransportMode);
    }
    public List<String> getUniqueMeasure(){
        return  getUniqueValues(TradeData::getMeasure);
    }

}
