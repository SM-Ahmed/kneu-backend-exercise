package com.kneu.medications.transformer;


public interface Transformer<T,R> {

    R transform(T message);
}
