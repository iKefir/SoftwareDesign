import com.vk.api.sdk.objects.wall.WallPostFull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiagramProducer {
    List<Integer> countPostsPerHour(List<WallPostFull> posts, Integer hours, Integer curTime) {
        List<Integer> result = new ArrayList<>(Collections.nCopies(hours, 0));

        for (WallPostFull obj : posts) {
            int index = (curTime - obj.getDate()) / 3600;
            result.set(index, result.get(index) + 1);
        }

        return result;
    }
}
