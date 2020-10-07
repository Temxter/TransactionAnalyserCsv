package model;

import enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Transaction {
    private String id;
    private Date date;
    private BigDecimal amount;
    private String merchant;
    private TransactionType transactionType;
    private String idRelatedTransaction;

    public Transaction(String id, Date date, BigDecimal amount, String merchant, TransactionType transactionType,
                       String idRelatedTransaction) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.merchant = merchant;
        this.transactionType = transactionType;
        this.idRelatedTransaction = idRelatedTransaction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getIdRelatedTransaction() {
        return idRelatedTransaction;
    }

    public void setIdRelatedTransaction(String idRelatedTransaction) {
        this.idRelatedTransaction = idRelatedTransaction;
    }
}
