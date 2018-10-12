import java.util.*;

public class LRUCache {

    private class Node {

        Node next, prev;
        Integer key;
        Object value;

        Node(Integer key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node first, last;

    private void removeFromDeq(Node cur) {
        if (cur.prev != null) {
            cur.prev.next = cur.next;
        }
        if (cur.next != null) {
            cur.next.prev = cur.prev;
        }

        if (first == cur) {
            first = cur.next;
            if (first != null) {
                first.prev = null;
            }
        }
        if (last == cur) {
            last = cur.prev;
            if (last != null) {
                last.next = null;
            }
        }

        assert (first == null || first.prev == null);
        assert (last == null || last.next == null);
    }

    private void insertToDeq(Node cur) {
        cur.next = first;
        cur.prev = null;
        if (first != null) {
            first.prev = cur;
        }
        first = cur;
        if (last == null) {
            last = cur;
        }
    }

    private int size;
    private Map<Integer, Node> map;

    public LRUCache(Integer cacheSize) {
        assert (cacheSize >= 1);

        size = cacheSize;
        map = new HashMap<>();
    }

    public LRUCache() {
        size = 100;
        map = new HashMap<>();
    }

    public void insert(Integer key, Object obj) {
        assert (map != null);
        assert (map.size() <= size);

        if (!map.containsKey(key)) {
            if (map.size() == size) {
                map.remove(last.key);
                removeFromDeq(last);
            }
            Node node = new Node(key, obj);
            map.put(key, node);
            insertToDeq(node);
        } else {
            Node oldNode = map.get(key);
            removeFromDeq(oldNode);
            Node newNode = new Node(key, obj);
            map.put(key, newNode);
            insertToDeq(newNode);
        }

        assert (first.key.equals(key));
        assert (map.size() <= size);
    }

    public Object get(Integer key) {
        Node node = map.get(key);
        if (node == null) {
            return null;
        }
        removeFromDeq(node);
        insertToDeq(node);

        assert (first.key.equals(key));
        return node.value;
    }
}
