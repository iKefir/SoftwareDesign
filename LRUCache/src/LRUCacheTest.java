import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

class LRUCacheTest {
    LRUCache cache;

    @BeforeEach
    void setUp() {
        cache = new LRUCache(10);
    }

    @Test
    void insertGet() {
        for (Integer i = 0; i < 20; ++i) {
            cache.insert(i, i);
            Assertions.assertEquals(i, cache.get(i));
        }
    }

    @Test
    void insertUpdate() {
        for (Integer i = 0; i < 20; ++i) {
            cache.insert(0, i);
            Assertions.assertEquals(i, cache.get(0));
        }
    }

    @Test
    void getExistingKey() {
        cache.insert(1, "firstKey");

        Assertions.assertEquals("firstKey", cache.get(1));
    }

    @Test
    void getNotExistingKey() {
        Assertions.assertNull(cache.get(1));
    }

    @Test
    void getPushedOutKey() {
        for (Integer i = 0; i < 20; ++i) {
            cache.insert(i, i);
        }

        Assertions.assertNull(cache.get(1));
    }

    @Test
    void getNotPushedOutKey() {
        for (Integer i = 0; i < 20; ++i) {
            cache.insert(i, i);
            cache.get(0);
        }

        Assertions.assertEquals(0, cache.get(0));
    }

    @Test
    void randomTest() {
        Map<Integer, Object> lruCache = new LinkedHashMap<>(10, 0.75f, true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Object> eldest) {
                return size() > 10;
            }
        };
        Random rand = new Random();

        for (Integer i = 0; i < 5000000; ++i) {
            Integer toTry = rand.nextInt(20);
            if (rand.nextBoolean()) {
                cache.insert(toTry, toTry);
                lruCache.put(toTry, toTry);
            } else {
                Assertions.assertEquals(lruCache.get(toTry), cache.get(toTry));
            }
        }
    }
}