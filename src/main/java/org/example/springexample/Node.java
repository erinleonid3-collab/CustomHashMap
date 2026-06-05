package org.example.springexample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Node<K,V> {
    private int hash;
    private K key;
    private V value;

    private Node<K,V> next;
}
