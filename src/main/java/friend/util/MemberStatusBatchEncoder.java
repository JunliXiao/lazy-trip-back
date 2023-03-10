package friend.util;

import com.google.gson.Gson;
import friend.model.MemberStatusBatch;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MemberStatusBatchEncoder implements Encoder.Text<MemberStatusBatch> {

    private static Gson gson = new Gson();

    @Override
    public String encode(MemberStatusBatch memberStatusBatch) throws EncodeException {
        System.out.println("Encoder for MemberStatusBatch called");
        return gson.toJson(memberStatusBatch);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
