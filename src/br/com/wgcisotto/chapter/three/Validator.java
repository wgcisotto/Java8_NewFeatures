package br.com.wgcisotto.chapter.three;

/**
 * Minha interface funcional com metodo abstrato valida.
 *
 * Interface functional
 *
 */

@FunctionalInterface
public interface Validator<T> {

    boolean valida(T t);

}
