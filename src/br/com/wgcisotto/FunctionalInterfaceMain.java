package br.com.wgcisotto;


/**
 * Classe criada para testar minha propria interface funcional
 * onde no teste foi feito uma validacao de CEP com Regex.
 *
 */
public class FunctionalInterfaceMain {

    public static void main (String ... args){

        Validator<String> validadorCEP =
                valor -> valor.matches("[0-9]{5}-[0-9]{3}");


        System.out.println(validadorCEP.valida("04101-300"));

    }

}
