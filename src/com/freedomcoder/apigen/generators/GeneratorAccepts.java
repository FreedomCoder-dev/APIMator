package com.freedomcoder.apigen.generators;

public interface GeneratorAccepts<T> {
    boolean generate(T from);
}
