package parsers;

import model.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class TransactionCsvParserTest {

    private String filename = "example.csv";

    @Test
    public void parseTest() throws IOException, ParseException {
        List<Transaction> transactionList = TransactionCsvParser.parse(filename);
        Assert.assertTrue(transactionList.size() > 0);
    }
}
