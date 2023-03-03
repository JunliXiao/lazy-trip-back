package friend.util;

import com.google.gson.Gson;
import friend.model.ChatMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.Collection;

public class ChatMessageEncoder implements Encoder.Text<Object> {

    private static Gson gson = new Gson();

    @Override
    public String encode(Object data) throws EncodeException {
        if (data instanceof ChatMessage || data instanceof Collection<?>) {
            return gson.toJson(new JsonResponse(true, data));
        } else if (data instanceof Exception) {
            return gson.toJson(new JsonResponse(false, data));
        } else {
            throw new EncodeException(data, "Something wrong the data for encoding");
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
