package parsers;

import enums.TransactionType;
import model.Transaction;
import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.Unique;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class TransactionCsvParser {
    static private final String dateFormat = "DD/MM/YYYY hh:mm:ss";

    private TransactionCsvParser() {}

    static public Map<TransactionType, Map<String, Transaction>> parse(String filename) throws IOException, ParseException {
        Map<TransactionType, Map<String, Transaction>> transactionMap = new HashMap<>();
        transactionMap.put(TransactionType.REVERSAL, new HashMap<>());
        transactionMap.put(TransactionType.PAYMENT, new HashMap<>());

        //Csv reader settings
        ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(filename),
                new CsvPreference.Builder('"', ',', "\n")
                        .surroundingSpacesNeedQuotes(true).build());
        beanReader.getHeader(true);
        final CellProcessor[] processors = getProcessors();
        final String[] nameMapping = getTransactionFieldMapping();

        Transaction transaction = beanReader.read(Transaction.class, nameMapping, processors);
        while (transaction != null) {
            transactionMap.get(transaction.getTransactionType()).put(transaction.getId(), transaction);
            //add link to REVERSAL transaction
            if (transaction.getTransactionType() == TransactionType.REVERSAL) {
                Transaction oldTransaction = transactionMap.get(TransactionType.PAYMENT).get(transaction.getIdRelatedTransaction());
                oldTransaction.setRelatedTransaction(transaction);
                // add oldTransaction link to transaction etc.
            }
            transaction = beanReader.read(Transaction.class, nameMapping, processors);
        }
        return transactionMap;
    }

    private static String[] getTransactionFieldMapping() {
        return new String[]{"id", "date", "amount", "merchant", "transactionType", "idRelatedTransaction"};
    }

    private static CellProcessor[] getProcessors() {
        return new CellProcessor[] {
                new Unique(),
                new Token(" ", null, new ParseDate(dateFormat,true)),
                new ParseBigDecimal(),
                new NotNull(),
                new ParseEnum(TransactionType.class),
                new Optional(),
        };
    }
}
