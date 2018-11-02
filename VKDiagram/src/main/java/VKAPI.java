import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.newsfeed.responses.SearchResponse;
import com.vk.api.sdk.objects.wall.WallPostFull;

import java.util.ArrayList;
import java.util.List;

public class VKAPI {
    private VkApiClient vk;
    private UserActor actor;

    public VKAPI(VkApiClient vkClient) {
        vk = vkClient;
        actor = new UserActor(1, "token");
    }

    public List<WallPostFull> search(String hashtag, Integer hours, Integer curTime) {
        List<WallPostFull> posts = new ArrayList<>();
        try {
            Integer startTime = curTime - hours * 60 * 60;
            Integer endTime = curTime;
            String searchString = "#".concat(hashtag);
            SearchResponse response;

            while(true) {
                response = vk.newsfeed().search(actor)
                        .q(searchString)
                        .count(200)
                        .startTime(startTime)
                        .endTime(endTime)
                        .execute();
                posts.addAll(response.getItems());
                if (response.getItems().size() < 200) {
                    break;
                }
                endTime = response.getItems().get(response.getItems().size() - 1).getDate() - 1;
                Thread.sleep(500);
            }
        } catch (ApiException|ClientException|InterruptedException e) {
            System.out.println(e);
        }
        return posts;
    }
}
