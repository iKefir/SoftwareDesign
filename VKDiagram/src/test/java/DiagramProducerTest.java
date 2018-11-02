import com.vk.api.sdk.objects.wall.WallPostFull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DiagramProducerTest {
    @Test
    void countPostsPerHourTest() {
        Random rand = new Random();
        Integer curTime = (int) (System.currentTimeMillis() / 1000L);

        Integer hours = rand.nextInt(24) + 1;
        Integer postsAmount = rand.nextInt(2000);

        List<WallPostFull> mockList = new ArrayList<>();

        for (int i = 0; i < postsAmount; ++i) {
            WallPostFull mockPost = mock(WallPostFull.class);
            Integer postTime = curTime - (rand.nextInt(hours) + 1) * 3600;
            when(mockPost.getDate()).thenReturn(postTime);
        }

        DiagramProducer producer = new DiagramProducer();
        List<Integer> result = producer.countPostsPerHour(mockList, hours, curTime);

        for (WallPostFull el : mockList) {
            int index = (curTime - el.getDate()) / 3600;
            result.set(index, result.get(index) - 1);
        }

        List<Integer> expected = new ArrayList<>(Collections.nCopies(hours, 0));

        Assertions.assertEquals(expected, result);
    }
}
