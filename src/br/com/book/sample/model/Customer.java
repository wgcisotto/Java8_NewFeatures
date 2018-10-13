package br.com.book.sample.model;

public class Customer {

    private String name;

    public Customer(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return this.name;
    }

}
