package me.axorom.bmanticheat;

public class Pair<T, V> {
    private T t;
    private V v;

    public Pair(T t, V v) {
        this.t = t;
        this.v = v;
    }

    public T getFirst() {
        return t;
    }

    public void setFirst(T t) {
        this.t = t;
    }

    public V getSecond() {
        return v;
    }

    public void setSecond(V v) {
        this.v = v;
    }
}
