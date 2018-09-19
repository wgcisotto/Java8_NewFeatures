package br.com.wgcisotto;

@FunctionalInterface
public interface Validator<T> {

    boolean valida(T t);

}
