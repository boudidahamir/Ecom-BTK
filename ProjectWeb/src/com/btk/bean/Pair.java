package com.btk.bean;

public class Pair<K, V> {
    private K cart;
    private V product;

    public Pair(K cart, V product) {
        this.cart = cart;
        this.product = product;
    }

    public K getFirst() {
        return cart;
    }

    public V getSecond() {
        return product;
    }
}
