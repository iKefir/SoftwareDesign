import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.wall.WallPostFull;

import java.util.List;

public class VKDiagramProducer {
    public List<Integer> getDiagram(String hashtag, Integer hours) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        VKAPI vkapi = new VKAPI(vk);
        DiagramProducer diagramProducer = new DiagramProducer();

        Integer curTime = (int) (System.currentTimeMillis() / 1000L);
        List<WallPostFull> response = vkapi.search(hashtag, hours, curTime);
        return diagramProducer.countPostsPerHour(response, hours, curTime);
    }
}
