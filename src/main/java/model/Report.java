package model;

import java.math.BigDecimal;

public class Report {
    private int numberOfTransactions;
    private BigDecimal averageTransactionValue;

    public Report(int numberOfTransactions, BigDecimal averageTransactionValue) {
        this.numberOfTransactions = numberOfTransactions;
        this.averageTransactionValue = averageTransactionValue;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(int numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public BigDecimal getAverageTransactionValue() {
        return averageTransactionValue;
    }

    public void setAverageTransactionValue(BigDecimal averageTransactionValue) {
        this.averageTransactionValue = averageTransactionValue;
    }

    @Override
    public String toString() {
        return "Number of transactions = " + numberOfTransactions + "\n" +
                "Average transaction value = " + averageTransactionValue;
    }
}
