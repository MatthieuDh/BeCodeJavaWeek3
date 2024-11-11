package org.example;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class TradeDataFilter {
    private TradeDataProcessor processor;
    private DecimalFormat df;

    public TradeDataFilter(){
        this.processor = new TradeDataProcessor();
        this.df = new DecimalFormat("#.##"); // Format to two decimal places
    }

    public List<TradeData> filterTheList(List<TradeData> dataList, String input) {
        return dataList.stream()
                .filter(d -> input == null || input.isEmpty() ||
                        input.equalsIgnoreCase(d.getCountry()) ||
                        input.equalsIgnoreCase(d.getMeasure()) ||
                        input.equalsIgnoreCase(d.getTransportMode()) ||
                        input.equalsIgnoreCase(d.getCommodity()))
                .collect(Collectors.toList());
    }

    private Map<String, Map<String,Long>> getTotal(List<TradeData> tradeDataList, int year){
        return tradeDataList.stream()
                .filter(d->d.getYear()==year)
                .collect(Collectors.groupingBy(
                        TradeData::getMonth,
                        Collectors.groupingBy(
                                TradeData::getDirection,
                                Collectors.summingLong(TradeData::getValue)
                        )
                ));
    }

    public void monthlyTotal(List<TradeData>tradeDataList, int year, String month){
        Map<String, Map<String, Long>> monthlyTotal = getTotal(tradeDataList,year);

        if (monthlyTotal.containsKey(month)) {
            Map<String, Long> directionTotals = monthlyTotal.get(month);
            long totalImports = directionTotals.get("Imports");
            long totalExports = directionTotals.get("Exports");

            System.out.println("Month: " + month);
            System.out.println("Import: " + totalImports);
            System.out.println("Export: " + totalExports);

        }
    }

    public void monthlyAverage(List<TradeData>tradeDataList, int year, String month){
        Map<String,Map<String,Long>> monthlyAverage = getTotal(tradeDataList,year);

        if (monthlyAverage.containsKey(month)){
            Map<String,Long> directionMap = monthlyAverage.get(month);
            double averageOfImports = directionMap.entrySet().stream()
                    .filter(entry->entry.getKey().equalsIgnoreCase("Imports"))
                    .mapToDouble(Map.Entry::getValue)
                    .average()
                    .orElse(0.0);

            double averageOfExports = directionMap.entrySet().stream()
                    .filter(entry->entry.getKey().equalsIgnoreCase("Exports"))
                    .mapToDouble(Map.Entry::getValue)
                    .average()
                    .orElse(0.0);



            System.out.println(month + " Imports: " + df.format(averageOfImports));
            System.out.println(month + " Exports: " + df.format(averageOfExports));
        }
    }

    public void yearlyTotal (List<TradeData>tradeDataList,int year){
        Map<String,Map<String,Long>> yearlyTotal = getTotal(tradeDataList,year);

        long yearlyImportTotal = 0L;
        long yearlyExportTotal = 0L;

        for (Map.Entry<String, Map<String, Long>> entry : yearlyTotal.entrySet()) {
            String month = entry.getKey();
            Map<String, Long> directionTotals = entry.getValue();
            long totalImports = directionTotals.get("Imports");
            long totalExports = directionTotals.get("Exports");

            System.out.println(month);
            System.out.println("Import: " + totalImports);
            System.out.println("Export: " + totalExports);

            yearlyImportTotal += totalImports;
            yearlyExportTotal += totalExports;
        }
        System.out.println("Yearly Import Total: " + yearlyImportTotal);
        System.out.println("Yearly Export Total: " + yearlyExportTotal);
    }

    public void yearlyAverage(List<TradeData>tradeDataList, int year){
        Map<String,Map<String,Long>> yearlyAverage = getTotal(tradeDataList,year);

        double totalImportValue = 0.0;
        double totalExportValue = 0.0;

        for (Map.Entry<String, Map<String, Long>> entry : yearlyAverage.entrySet()) {
            String month = entry.getKey();
            Map<String,Long>directionMap = entry.getValue();

            OptionalDouble averageOfImports = directionMap.entrySet().stream()
                    .filter(direction->direction.getKey().equalsIgnoreCase("Imports"))
                    .mapToDouble(Map.Entry::getValue)
                    .average();

            OptionalDouble averageOfExports = directionMap.entrySet().stream()
                    .filter(direction->direction.getKey().equalsIgnoreCase("Exports"))
                    .mapToDouble(Map.Entry::getValue)
                    .average();


            System.out.println(month);
            System.out.println("Import" + ": " + df.format(averageOfImports.getAsDouble()));
            System.out.println("Export" + ": " + df.format(averageOfExports.getAsDouble()));

            totalImportValue +=averageOfImports.orElse(0.0);
            totalExportValue +=averageOfExports.orElse(0.0);
        }
        double yearlyImportAverage = totalImportValue/12.0;
        double yearlyExportAverage = totalExportValue/12.0;

        System.out.println("Overall Yearly Average Imports: " + df.format(yearlyImportAverage));
        System.out.println("Overall Yearly Average Exports: " + df.format(yearlyExportAverage));
    }
}
