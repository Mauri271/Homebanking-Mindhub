package com.mindhub.homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="native")
    @GenericGenerator(name= "native", strategy = "native")
    private Long id;
    private String name;
    private Double maxAmount;
    @ElementCollection
    private Set<Integer> payments = new HashSet<>();

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();


    public Loan () {}

    public Loan(String name, Double maxAmount, Set<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Long getId (){return id;}

    public String getName(){return name;}

    public void setName(String name){ this.name = name; }

    public Double getMaxAmount(){ return maxAmount; }

    public void setMaxAmount(Double maxAmount){this.maxAmount = maxAmount; }

    public Set<Integer> getPayments(){ return payments;}

    public void setPayments(Set<Integer> payments){ this.payments= payments; }

    @JsonIgnore
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }


    public void addClientLoans( ClientLoan clientLoan ){
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }



}
