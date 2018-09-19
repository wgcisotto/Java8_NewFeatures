package br.com.wgcisotto;

import java.util.function.Consumer;

class UserConsumer implements Consumer<User>{

    @Override
    public void accept(User user) {
        System.out.println(user.getName());
    }

}
