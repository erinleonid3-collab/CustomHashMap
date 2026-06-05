package org.example.springexample;


import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> b = new HashMap<>();
        CustomHashMap<String, Integer> a = new CustomHashMap<>();

        a.put("a", 12);
        a.put("b", 11);
        b.put("a", 12);
        b.put("b", 11);
        a.get("b");
        System.out.println(a.get("b"));
        a.remove("b");
        System.out.println(a.get("b"));


//        final HashMap.Node<K,V> getNode(Object key) {
//            HashMap.Node<K,V>[] tab = table;//массив с элементами
//            HashMap.Node<K,V> first= tab[(n - 1) & (hash )]; //первый нод
//            HashMap.Node<K,V> e= first.next;//следующий нод
//            int n= tab.length;//размер массива
//            int hash=hash(key);//хэшкод переданного ключа
//            K k= first.key;
//
//            if ((tab ) != null && (n ) > 0 && (first ) != null) {   //если массив не пустой и если 1 элемент не равен null иначе вернет null
//                if (first.hash == hash && ((k ) == key || (key != null && key.equals(k))))  //если переданный ключ равен первому
//                    return first;//возвращаем первое значение
//                if ((e ) != null) {//если следующий нод не равен null
//                    if (first instanceof HashMap.TreeNode)//если первый элемент это дерево????
//                        return ((HashMap.TreeNode<K,V>)first).getTreeNode(hash, key);//????
//                    do {
//                        if (e.hash == hash && // если хэш второго noda равен хэшу переданного
//                                ((k = e.key) == key || (key != null && key.equals(k))))//и
//                            return e;
//                    } while ((e = e.next) != null);
//                }
//            }
//            return null;
//        }


    }
}