package br.com.wgcisotto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
class User {

    public User(String name, int points){
        this.name = name;
        this.points = points;
        this.admin = false;
    }

    private String name;

    private int points;

    private boolean admin;

}
