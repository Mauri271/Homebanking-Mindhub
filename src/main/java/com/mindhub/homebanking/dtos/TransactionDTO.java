package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.enums.TransactionType;
import java.time.LocalDateTime;

public class TransactionDTO {

        private Long id;
        private TransactionType type;
        private double amount;
        private String description;
        private LocalDateTime date;
        private Account account;

        private Boolean hidden;


        public TransactionDTO(Transaction transaction) {

            this.id = transaction.getId();

            this.type = transaction.getType();

            this.amount = transaction.getAmount();

            this.description = transaction.getDescription();

            this.date = transaction.getDate();

            this.hidden = transaction.getHidden();

        }

        public Long getId() {
            return id;
        }

        public TransactionType getType() {
            return type;
        }

        public double getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public Boolean getHidden() { return hidden; }
}