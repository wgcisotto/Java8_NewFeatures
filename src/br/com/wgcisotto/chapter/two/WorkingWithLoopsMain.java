package br.com.wgcisotto.chapter.two;

import br.com.wgcisotto.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


/**
 * Classe criada para testar algumas formas de fazer loop.
 *
 */

public class WorkingWithLoopsMain {

    public static void main (String ... args){


        User user1 = new User("William Cisotto", 150);
        User user2 = new User("Ana Carolina", 120);
        User user3 = new User("Ian Cisotto", 190);

        List<User> users = Arrays.asList(user1,user2,user3);

        // Old way JAVA 7 -
        for(User u: users)
            System.out.println(u.getName());


        // Test Using JAVA 8 Consumer
        UserConsumer userConsumer = new UserConsumer();
        users.forEach(userConsumer);

        //Other option
        Consumer<User> consumer = new Consumer<User>() {
            @Override
            public void accept(User user) {
                System.out.println(user.getName());
            }
        };

        users.forEach(consumer);


        //The same above
        users.forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                System.out.println(user.getName());
            }
        });

        //using lambda
        Consumer<User> consumer1 =
                u -> {System.out.println(u.getName());};

        Consumer<User> consumer2 =
                u -> System.out.println(u.getName());


        users.forEach(u -> System.out.println(u.getName()));



    }

}
