package br.com.wgcisotto;

import java.util.function.Consumer;

/**
 * Classe criada implementar interface funcional default do java
 *
 */

class UserConsumer implements Consumer<User>{

    @Override
    public void accept(User user) {
        System.out.println(user.getName());
    }

}
