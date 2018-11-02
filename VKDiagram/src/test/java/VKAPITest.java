import com.vk.api.sdk.actions.Newsfeed;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.newsfeed.responses.SearchResponse;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.queries.newsfeed.NewsfeedSearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

public class VKAPITest {

    @Test
    void searchTest() throws ApiException, ClientException {
        Random rand = new Random();
        Integer curTime = (int) (System.currentTimeMillis() / 1000L);
        Integer postsAmount = 199;

        VkApiClient vk = mock(VkApiClient.class);
        Newsfeed newsfeed = mock(Newsfeed.class);
        NewsfeedSearchQuery sQuery = mock(NewsfeedSearchQuery.class);
        SearchResponse sResponse = mock(SearchResponse.class);

        List<WallPostFull> mockResult = new ArrayList<>();
        for (int i = 0; i < postsAmount; ++i) {
            WallPostFull wPost = mock(WallPostFull.class);
            when(wPost.getDate()).thenReturn(curTime - 1 - i);
            mockResult.add(wPost);
        }

        when(vk.newsfeed()).thenReturn(newsfeed);
        when(newsfeed.search(any(UserActor.class))).thenReturn(sQuery);
        when(sQuery.q(any(String.class))).thenReturn(sQuery);
        when(sQuery.count(any(Integer.class))).thenReturn(sQuery);
        when(sQuery.startTime(any(Integer.class))).thenReturn(sQuery);
        when(sQuery.endTime(any(Integer.class))).thenReturn(sQuery);
        when(sQuery.execute()).thenReturn(sResponse);
        when(sResponse.getItems()).thenReturn(mockResult);

        VKAPI vkapi = new VKAPI(vk);

        List<WallPostFull> res = vkapi.search("a", 24, curTime);
        Assertions.assertEquals(mockResult, res);
    }
}
