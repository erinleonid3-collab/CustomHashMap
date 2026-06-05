package org.example.springexample;

public class CustomHashMap<K, V> implements CustomMap<K, V> {
    public static final float LOAD_FACTORY = 0.75f;
    private Node<K, V>[] buckets;
    private int trashHold;
    private int countElement;

    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        this.buckets = new Node[16];
        this.trashHold = getTrashHold(buckets.length);
    }

    private int getTrashHold(int size) {
        return (int) (size * LOAD_FACTORY);
    }

    @Override
    public V get(K key) {
        int hash = getHash(key);
        int bucketIndex = getBucketIndex(hash);
        Node<K, V> node = buckets[bucketIndex];
        while (node != null) {
            var nodeKey = node.getKey();
            if ((nodeKey == null && key == null)
                    || (nodeKey != null && nodeKey.equals(key))) {
                return node.getValue();
            } else {
                node = node.getNext();
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int hash = getHash(key);
        int bucketIndex = getBucketIndex(hash);
        Node<K, V> node = buckets[bucketIndex];
        Node<K, V> prev = null;
        Node<K, V> current = node;
        if (node == null) {
            return null;
        }
        while (current != null) {
            boolean keyIsMatch = isKeyIsMatch(key, current);
            if (keyIsMatch) {
                V currentValue = current.getValue();
                if (prev == null) {
                    buckets[bucketIndex] = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                countElement--;
                return currentValue;
            }
            prev = current;
            current = current.getNext();
        }
        return null;
    }

    private int getHash(K key) {
        return key == null ? 0 : key.hashCode();
    }

    @Override
    public V put(K key, V value) {
        int hash = getHash(key);
        int indexBucket = getBucketIndex(hash);
        Node<K, V> node = buckets[indexBucket];
        if (node == null) {
            buckets[indexBucket] = createNode(key, value, hash);
            countElement++;
            checkCountElement();
            return null;
        }
        while (node != null) {
            boolean keyIsMatch = isKeyIsMatch(key, node);
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

    private static <K, V> boolean isKeyIsMatch(K key, Node<K, V> node) {
        return (key == null && node.getKey() == null) ||
                key != null && key.equals(node.getKey());
    }

    private void checkCountElement() {
        if (countElement > trashHold) {
            resize();
        }
    }

    @SuppressWarnings("unchecked")
    private void resize() {
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

    private static <K, V> Node<K, V> createNode(K key, V value, int hash) {
        return new Node<>(hash, key, value, null);
    }

    private int getBucketIndex(int hash) {
        return hash & (buckets.length - 1);
    }

    @Override
    public int size() {
        return countElement;
    }
}
