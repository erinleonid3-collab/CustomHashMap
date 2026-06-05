package org.example.springexample;

public interface CustomMap<K, V> {

    V get(K key);

    V remove(K key);

    V put(K key, V value);
}
