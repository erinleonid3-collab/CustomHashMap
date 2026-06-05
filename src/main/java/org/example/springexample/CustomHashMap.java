package org.example.springexample;

import java.util.HashMap;

import static java.util.Objects.hash;

public class CustomHashMap<K, V> implements CustomMap<K, V> {
    public static final float LOAD_FACTORY = 0.75f;
    private Node<K, V>[] buckets;
    private int trashHold;
    private int countElement;

    public CustomHashMap() {
        this.buckets = new Node[16];
        this.trashHold = getTrashHold(buckets.length);
    }

    private int getTrashHold(int size) {
        return (int) (size * LOAD_FACTORY);
    }

    @Override
    public V get(K key) {
        int n = buckets.length;
        int hash = key.hashCode();
        Node<K, V>[] buck = buckets;
        Node<K, V> first = buckets[(n - 1) & hash];
        Node<K, V> nextNode;


        if (first != null) {
            if (first.getHash() == key.hashCode() && first.getKey().equals(key)) {
                return first.getValue();
            }
            if ((nextNode = first.getNext()) != null) {
                while (nextNode != null) {
                    if (nextNode.getHash() == key.hashCode() && nextNode.getKey().equals(key) ||
                            key == null && (nextNode.getKey()==null)) {
                        return nextNode.getValue();
                    }
                    nextNode = nextNode.getNext();
                }
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int n = buckets.length;
        int hash = key.hashCode(); //зачем в оригинале создан метод hash(key)???
        int indexBucket = (n - 1) & hash;
        Node<K, V>[] buck = buckets;
        Node<K, V> first = buckets[indexBucket];
        Node<K, V> nextNode, iterNode;


        if (first != null) {
            if (first.getHash() == hash(key) && first.getKey().equals(key)) {
                buckets[indexBucket] = first.getNext();
                countElement--;
                return first.getValue();

            }
            if ((iterNode = first.getNext()) != null) {
                while (iterNode != null) {
                    if (iterNode.getHash() == hash(key) && iterNode.getKey().equals(key) ||
                            key == null && iterNode.getKey()==null) {
                        first.setNext(iterNode.getNext());
                        countElement--;
                        return iterNode.getValue();
                    }
                    first = iterNode;
                    iterNode = iterNode.getNext();

                }
                }
            }
            return null;
        }
        private int hash(K key){
            return key == null ? 0 : key.hashCode();
        }
        @Override
        public V put (K key, V value){
            int hash = hash(key);
            int indexBucket = getBucketIndex(hash);
            Node<K, V> node = buckets[indexBucket];
            if (node == null) {
                buckets[indexBucket] = createNode(key, value, hash);
                countElement++;
                checkCountElement();
                return null;
            }
            while (node != null) {
                boolean keyIsMatch = (key == null && node.getKey() == null) ||
                        key != null && key.equals(node.getKey());
                if (keyIsMatch) {
                    V oldValue = node.getValue();
                    node.setValue(value);
                    return oldValue;
                }
                if (node.getNext() == null) {
                    break;
                }
                node = node.getNext();

            }
            node.setNext(createNode(key, value, hash));
            countElement++;
            checkCountElement();
            return null;
        }

        private void checkCountElement () {
            if (countElement > trashHold) {
                resize();
            }
        }

        private void resize () {
            int oldSize = buckets.length;
            Node<K, V>[] newBuckets = new Node[oldSize * 2];
            trashHold = getTrashHold(newBuckets.length);
            for (int i = 0; i < oldSize; i++) {
                var oldNode = buckets[i];
                if (oldNode == null) {
                    continue;
                }
                Node<K, V> lowHead = null;
                Node<K, V> lowTale = null;
                Node<K, V> highHead = null;
                Node<K, V> highTale = null;
                while (oldNode != null) {
                    var nextNode = oldNode.getNext();
                    if ((oldNode.getHash() & oldSize) == 0) {
                        if (lowHead == null) {
                            lowHead = oldNode;
                            lowTale = oldNode;
                        } else {
                            lowTale.setNext(oldNode);
                            lowTale = lowTale.getNext();
                        }
                    } else {
                        if (highHead == null) {
                            highHead = oldNode;
                            highTale = oldNode;
                        } else {
                            highTale.setNext(oldNode);
                            highTale = highTale.getNext();
                        }
                    }
                    oldNode.setNext(null);
                    oldNode = nextNode;
                }
                if (lowTale != null) {
                    lowTale.setNext(null);
                    newBuckets[i] = lowHead;
                }
                if (highTale != null) {
                    highTale.setNext(null);
                    newBuckets[i + oldSize] = highHead;
                }
            }
            buckets = newBuckets;
        }

        private static <K, V > Node < K, V > createNode(K key, V value, int hash){
            return new Node<>(hash, key, value, null);
        }

        private int getBucketIndex ( int hash){
            return hash & (buckets.length - 1);
        }


    }
