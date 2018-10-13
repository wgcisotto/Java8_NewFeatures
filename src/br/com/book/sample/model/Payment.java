package br.com.book.sample.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class Payment {

    private List<Product> products;


    /**
     * utilizado novo recurso do java 8 LocalDateTime,
     * para armazenar a data e formata-lá.
     */
    private LocalDateTime date;

    private Customer customer;

    public Payment(List<Product> products,
                        LocalDateTime date,
                        Customer customer){

        this.products = Collections.unmodifiableList(products);

        this.date = date;

        this.customer = customer;

    }

    public List<Product> getProducts() {
        return products;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String toString(){
        return "[Payment: " +
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " " + customer + " " + products + "]";
    }
}
