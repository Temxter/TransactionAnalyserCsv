package main;

import enums.TransactionType;
import model.Report;
import model.Transaction;
import parsers.TransactionCsvParser;
import services.ReportService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            for (String a: args
                 ) {
                System.out.println(a);
            }
            System.err.println("Error: incorrect number of arguments.");
            System.out.println(programInfo);
            System.exit(0);
        }
        //else
        String filename = args[0];
        String fromDateString = args[1];
        String toDateString = args[2];
        String merchant = args[3];

        DateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY hh:mm:ss");

        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = dateFormat.parse(fromDateString);
            toDate = dateFormat.parse(toDateString);
        } catch (ParseException e) {
            printErrorDateParse(e);
            System.exit(0);
        }

        Map<TransactionType, Map<String, Transaction>> transactionMap = null;
        try {
            transactionMap = TransactionCsvParser.parse(filename);
        } catch (IOException e) {
            System.err.printf("Error. File %s cannot be opened or file reading error: %s\n", filename, e.getMessage());
            System.exit(0);
        } catch (ParseException e) {
            printErrorDateParse(e);
            System.exit(0);
        }

        System.out.printf("File %s read successfully.\n", filename);

        Report report = ReportService.getReport(transactionMap, fromDate, toDate, merchant);

        System.out.printf("Report:\n%s\n", report);
    }

    private static void printErrorDateParse(ParseException e) {
        System.err.printf("Error of parsing date: %s. Date format pattern = \"DD/MM/YYYY hh:mm:ss\"\n",
                e.getMessage());
    }

    static private String programInfo = "The program takes 4 arguments:\n" +
            "filename fromDate toDate merchant\n" +
            "1) filename is name of .csv file\n" +
            "2) fromDate indicates from what date the report should be generated\n" +
            "3) toDate indicates to what date the report should be generated\n" +
            "4) merchant is name of specific merchant\n" +
            "Date format pattern = \"DD/MM/YYYY hh:mm:ss\"\n" +
            "Arguments example: example.csv “20/08/2018 12:00:00” \"20/08/2018 13:00:00\" Kwik-E-Mart\n";
}
