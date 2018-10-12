public class Main {

    public static void main(String[] args) {
	    LRUCache cache = new LRUCache(9);
//	    cache.insert(1, 1);
//	    cache.insert(5, 5);
//	    cache.insert(8, 8);
//	    cache.insert(9, 9);
//	    cache.insert(4, 4);
//	    cache.get(5);
//	    cache.insert(2, 2);
//	    cache.insert(2, 2);
//	    cache.get(1);
//	    cache.insert(8, 8);

        cache.insert(0, 0);
        cache.insert(1, 1);
        cache.insert(8, 8);
        cache.insert(3, 3);
        cache.insert(5, 5);
        cache.insert(5, 5);
        cache.insert(9, 9);
        cache.get(8);

    }
}
