package com.freedomcoder.astutils;

import java.io.IOException;
import java.io.OutputStream;

public class LineCountingOutStream extends OutputStream {

    OutputStream underlying;
    int count;

    public LineCountingOutStream(OutputStream underlying) {
        this.underlying = underlying;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void write(int p1) throws IOException {
        if ('\n' == p1) count++;
        underlying.write(p1);
    }

}
