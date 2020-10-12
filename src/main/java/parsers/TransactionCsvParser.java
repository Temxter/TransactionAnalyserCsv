package parsers;

import enums.TransactionType;
import model.Transaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionCsvParser {
    static private final DateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY hh:mm:ss");

    private TransactionCsvParser() {}



    static public Map<TransactionType, Map<String, Transaction>> parse(String filename) throws IOException, ParseException {
        File file = new File(filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        Map<TransactionType, Map<String, Transaction>> transactionMap = new HashMap<>();
        transactionMap.put(TransactionType.REVERSAL, new HashMap<>());
        transactionMap.put(TransactionType.PAYMENT, new HashMap<>());

        bufferedReader.readLine(); //header of csv file
        String lineFile = bufferedReader.readLine();
        while (lineFile != null) {
            String[] splitLineArray = lineFile.split(", ");
            String id = splitLineArray[0];
            Date date = dateFormat.parse(splitLineArray[1]);
            BigDecimal amount = new BigDecimal(splitLineArray[2]);
            String merchant = splitLineArray[3];
            String transactionTypeString = splitLineArray[4].replaceFirst(",", ""); // for "PAYMENT,"
            TransactionType transactionType = TransactionType.valueOf(transactionTypeString);
            String idRelationTransaction = null;
            if (transactionType.equals(TransactionType.REVERSAL)) {
                idRelationTransaction = splitLineArray[5];
            }
            Transaction transaction = new Transaction(id, date, amount, merchant, transactionType,
                    idRelationTransaction);
            transactionMap.get(transactionType).put(id, transaction);
            if (transactionType == TransactionType.REVERSAL) {
                Transaction oldTransaction = transactionMap.get(TransactionType.PAYMENT).get(idRelationTransaction);
                oldTransaction.setRelatedTransaction(transaction);
                // add oldTransaction link to transaction etc.
            }
            lineFile = bufferedReader.readLine();
        }
        return transactionMap;
    }
}
