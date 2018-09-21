package br.com.wgcisotto;

import br.com.wgcisotto.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class UsingConsumerMethodAndThen {

    public static void main (String args[]){

        User user1 = new User("William Galindo Cisotto", 100);
        User user2 = new User("Ana Carolina Costa Rodrigues", 150);
        User user3 = new User("Ian Rodrigues Cisotto", 50);

        List<User> users = Arrays.asList(user1,user2,user3);

        Consumer<User> mostraMensagem = o ->
            System.out.println("Mensagem antes de imprimir os nomes");


        Consumer<User> mostrarNome = u ->
            System.out.println(u.getName());


        users.forEach(mostraMensagem.andThen(mostrarNome));

        /**
         * Podemos então ter implementações comuns de Consumer , e utilizá-las
         em diferentes momentos do nosso código, passando-os como argumentos e de-
         pois compondo-os de maneira a reutilizá-los. Por exemplo, se você tem um
         Consumer<Usuario> auditor que guarda no log que aquele usuário realizou
         algo no sistema, você pode reutilizá-lo, com ou sem o andThen . Um bom exemplo
         do pattern decorator.
         *
         */




    }

}
