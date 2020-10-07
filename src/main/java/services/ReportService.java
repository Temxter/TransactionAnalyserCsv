package services;

import enums.TransactionType;
import model.Report;
import model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportService {

    private ReportService() {}

    static public Report getReport(List<Transaction> transactionList, final Date fromDate, final Date toDate, final String merchant) {
        List<Transaction> filteredTransactionList = transactionList.stream()
                .filter(transaction ->
                        transaction.getDate().compareTo(fromDate) >= 0
                        && transaction.getDate().compareTo(toDate) <= 0
                        && transaction.getMerchant().equals(merchant))
                .collect(Collectors.toList());

        //remove from filtered set REVERSAL transactions
        Set<String> filteredTransactionIdSet = filteredTransactionList.stream()
                .map(transaction -> transaction.getId())
                .collect(Collectors.toSet());

        Set<String> filteredReversalTransactionSet = transactionList.stream()
                .filter(transaction ->
                        transaction.getTransactionType().equals(TransactionType.REVERSAL)
                        && filteredTransactionIdSet.contains(transaction.getIdRelatedTransaction()))
                .map(transaction -> transaction.getIdRelatedTransaction())
                .collect(Collectors.toSet());

        filteredTransactionList = filteredTransactionList.stream()
                .filter(transaction -> !filteredReversalTransactionSet.contains(transaction.getId()))
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

        Report report = new Report(filteredTransactionList.size(), amountAverage);
        return report;
    }
}
