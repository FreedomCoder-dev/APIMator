package com.freedomcoder.apigen.parser;

import com.freedomcoder.apigen.iface.ContextWrapper;
import com.freedomcoder.apigen.iface.LangContext;

public abstract class ParseProcessor<T> extends ContextWrapper {

    public ParseProcessor(LangContext base) {
        super(base);
    }

    public abstract T parse();
}
