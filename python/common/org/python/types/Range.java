package org.python.types;


public class Range extends org.python.types.Object implements org.python.Iterable {
    private long index;

    private long start;
    private long stop;
    private long step;

    public Range(org.python.Object stop) {
        this(new org.python.types.Int(0), stop, new org.python.types.Int(1));
    }

    public Range(org.python.Object start, org.python.Object stop) {
        this(start, stop, new org.python.types.Int(1));
    }

    public Range(org.python.Object start, org.python.Object stop, org.python.Object step) {
        super();
        if (!(start instanceof org.python.types.Int)) {
            throw new org.python.exceptions.TypeError("'" + org.Python.pythonTypeName(start) + "' object cannot be interpreted as an integer");
        }
        if (!(stop instanceof org.python.types.Int)) {
            throw new org.python.exceptions.TypeError("'" + org.Python.pythonTypeName(stop) + "' object cannot be interpreted as an integer");
        }
        if (!(step instanceof org.python.types.Int)) {
            throw new org.python.exceptions.TypeError("'" + org.Python.pythonTypeName(step) + "' object cannot be interpreted as an integer");
        }

        attrs.put("start", start);
        this.start = ((org.python.types.Int) start).value;

        attrs.put("stop", stop);
        this.stop = ((org.python.types.Int) stop).value;

        attrs.put("step", step);
        this.step = ((org.python.types.Int) step).value;

        index = this.start;
        // value = attrs;
    }

    @org.python.Method(
        __doc__ = "Implement iter(self)."
    )
    public org.python.Iterable __iter__() {
        return this;
    }

    public org.python.Object __next__() {
        if (this.step > 0 && this.index >= this.stop) {
            throw new org.python.exceptions.StopIteration();
        } else if (this.step < 0 && this.index <= this.stop) {
            throw new org.python.exceptions.StopIteration();
        }

        org.python.Object result = new org.python.types.Int(this.index);
        this.index += this.step;
        return result;
    }

    public org.python.Object __getitem__(org.python.Object index) {
        long idx = ((org.python.types.Int)index).value;
        long value;
        if (idx >= 0) {
            value = this.start + idx * this.step;
        } else {
            value = this.stop + idx * this.step;
        }

        if (this.step > 0 && value >= this.stop) {
            throw new org.python.exceptions.IndexError("range object index out of range");
        } else if (this.step < 0 && value <= this.stop) {
            throw new org.python.exceptions.IndexError("range object index out of range");
        }
        org.python.Object result = new org.python.types.Int(value);
        return result;
    }

}
