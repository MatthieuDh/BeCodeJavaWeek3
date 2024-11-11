package org.example;

import java.util.List;
import java.util.Scanner;

public class CommandPromptSecondEdition {
    private TradeDataFilter filter;
    private Scanner scanner;
    private TradeDataProcessor processor;
    private TradeDataOverview viewer;


    public CommandPromptSecondEdition() {
        this.filter = new TradeDataFilter();
        this.scanner = new Scanner(System.in);
        this.processor = new TradeDataProcessor();
        this.viewer = new TradeDataOverview();
    }

    public void handleInput(String csvFilePath){
        processor.readTradeDataFromFile(csvFilePath);
        List<TradeData> tradeDataList = processor.getTradeDataList();
        while (true){
            String input = scanner.nextLine();
            String[] inputArray = input.split(" ");
            String command = inputArray[0];

            List<TradeData> filteredList = null;
            try{
                if (inputArray.length>3){
                    filteredList = filter.filterTheList(tradeDataList,getTheParameter(inputArray));
                }else{
                    filteredList = tradeDataList;
                }
            }catch (Exception e){
                continue;
            }
            if (command.isEmpty()) {
                break;
            }else if (command.equalsIgnoreCase("help")){
                System.out.println("help <command>\nmonthly_total\nmonthly_average\nyearly_total\nyearly_average\noverview");
            }else if (command.equalsIgnoreCase("help<" + getTheCommand(inputArray) + ">")){
                String explanation = explainTheCommand(getTheCommand(inputArray));
                System.out.println(explanation);
            }else if (command.equalsIgnoreCase("monthly_total")){

                try {
                    int year = Integer.parseInt(inputArray[1]);
                    String month = inputArray[2];
                    filter.monthlyTotal(filteredList, year, month);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid year format.");
                }
            }else if (command.equalsIgnoreCase("yearly_total")){
                try {
                    int year = Integer.parseInt(inputArray[1]);
                    filter.yearlyTotal(filteredList, year);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid year format.");
                }
            }else if (command.equalsIgnoreCase("monthly_average")){
                try {
                    int year = Integer.parseInt(inputArray[1]);
                    String month = inputArray[2];
                    filter.monthlyAverage(filteredList, year, month);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid year format.");
                }
            }else if (command.equalsIgnoreCase("yearly_average")) {
                try {
                    int year = Integer.parseInt(inputArray[1]);
                    filter.yearlyAverage(filteredList, year);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid year format.");
                }
            }else if (command.equalsIgnoreCase("overview")){
                System.out.println("\nYear:");
                viewer.getUniqueYears().forEach(System.out::println);
                System.out.println("\nCountry:");
                viewer.getUniqueCountries().forEach(System.out::println);
                System.out.println(":\nCommodity:");
                viewer.getUniqueCommodities().forEach(System.out::println);
                System.out.println("\nTransportation mode:");
                viewer.getUniqueTransportationMode().forEach(System.out::println);
                System.out.println("\nMeasure:");
                viewer.getUniqueMeasure().forEach(System.out::println);
            }
        }
    }

    private String getTheParameter(String[]inputArray){
        String parameter = "";

        if (inputArray.length > 3) {
            for (int i = 3; i < inputArray.length; i++) {
                parameter += inputArray[i] + " "; // Append each array element with a space
            }
        }
        return parameter.trim();
    }
    private String getTheCommand(String[]inputArray){
        String helpCommand = "";
        if (inputArray[0].contains("help<")){
            String[]splitArray = inputArray[0].split("<|>");
            helpCommand = splitArray[1];
        }
        return helpCommand;
    }

    private String explainTheCommand(String validCommand){
        switch (validCommand){
            case "monthly_total":
                return "Returns the sum of both the export and import for a specified month of a specified year.";
            case "monthly_average":
                return "Returns the average of both the export and import of a specified month of a specified year.";
            case "yearly_total":
                return "Provides an overview of all the monthly totals for a particular year. This command returns the total of each month for both import and export and then gives the yearly total for both import and export.";
            case "yearly_average":
                return "Provides an overview of all the monthly averages for a particular year, for both import and export. Then it gives the yearly average for both import and export.";
            case "overview":
                return "Returns all the unique values that span the data set: years, countries, commodities, transportation modes, and measures.";
            default:
                return "Unknown command";
        }
    }
}

