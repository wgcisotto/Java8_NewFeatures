package br.com.wgcisotto.chapter.three;

/**
 * Classe criada para testar alguns recursos
 *  referente a Interface Funcional.
 *
 */
public class GeneralTests {

    public static void main (String ... args){


        /**
        *error: incompatible types: Object is not a functional interface .
        *Object o = () -> {
        *    System.out.println("O que sou eu ? Que Lambda");
        *};
        */

        Runnable o = () ->{
            System.out.println("O que sou eu ? Que Lambda ?");
        };

        System.out.println(o);
        System.out.println(o.getClass());

        /**
         *
         *Variable used in Lambda  expression should be final or effectively final
         *
         */

        final int numero = 5; // final


        /**
         *
         *int numero = 5 //  not final but can be effectively final
         *
         */

        new Thread(()->{
           System.out.println(numero);
        }).start();

        /**
         *
         * linha: System.out.println(numero);
         * não compila, numero deve ser final ou efetivamente final.
         * numero = 6; // ERROR DOING THIS // por causa dessa linha!
         *
         */


    }

}
