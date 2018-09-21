package br.com.wgcisotto.chapter.two;

import br.com.wgcisotto.model.User;

import java.util.function.Consumer;

/**
 * Classe criada implementar interface funcional default do java
 *
 */

public class UserConsumer implements Consumer<User>{

    @Override
    public void accept(User user) {
        System.out.println(user.getName());
    }

}
