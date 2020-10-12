package services;

import enums.TransactionType;
import model.Report;
import model.Transaction;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService {

    private ReportService() {}

    static public Report getReport(Map<TransactionType, Map<String, Transaction>> transactionMap, Date fromDate, Date toDate, String merchant) {
        List<Transaction> filteredTransactionList = transactionMap.get(TransactionType.PAYMENT)
                .values()
                .stream()
                .filter(transaction ->
                        transaction.getRelatedTransaction() != null
                                && transaction.getDate().compareTo(fromDate) >= 0
                                && transaction.getDate().compareTo(toDate) <= 0
                                && transaction.getMerchant().equals(merchant))
                .collect(Collectors.toList());

        int numberOfTransactions = filteredTransactionList.size();
        BigDecimal amountAverage;
        if (numberOfTransactions > 0) {
            BigDecimal amountSum = filteredTransactionList.stream()
                    .reduce(new BigDecimal(0.0), (accumulator, transaction) -> accumulator.add(transaction.getAmount()),
                            BigDecimal::add);
            amountAverage = amountSum.divide(new BigDecimal(numberOfTransactions), 2, BigDecimal.ROUND_HALF_UP);
        } else {
            amountAverage = new BigDecimal("0.00");
        }

        Report report = new Report(numberOfTransactions, amountAverage);
        return report;
    }
}
