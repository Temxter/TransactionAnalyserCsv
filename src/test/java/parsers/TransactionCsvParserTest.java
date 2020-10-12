package parsers;

import enums.TransactionType;
import model.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionCsvParserTest {

    private String filename = "example.csv";

    @Test
    public void parseTest() throws IOException, ParseException {
        Map<TransactionType, Map<String, Transaction>> transactionMap = TransactionCsvParser.parse(filename);
        Assert.assertTrue(transactionMap.get(TransactionType.PAYMENT).size() > 0);
    }
}
