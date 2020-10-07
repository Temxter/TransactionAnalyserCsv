package services;

import model.Report;
import model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parsers.TransactionCsvParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportServiceTest {

    private List<Transaction> transactionList;

    @Before
    public void init() throws IOException, ParseException {
        transactionList = TransactionCsvParser.parse("example.csv");
    }

    @Test
    public void getReportTest() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY hh:mm:ss");
        Date fromDate = dateFormat.parse("20/08/2018 12:00:00");
        Date toDate = dateFormat.parse("20/08/2018 13:00:00");
        String merchant = "Kwik-E-Mart";
        Report report = ReportService.getReport(transactionList, fromDate, toDate, merchant);
        Assert.assertNotEquals("The number of transactions must be more than 0",
                0, report.getNumberOfTransactions());
        Assert.assertNotEquals("The average of transaction value must be more than 0",
                new BigDecimal("0.00"), report.getAverageTransactionValue());
        System.out.println(report);
    }
}
