package friend.util;

public class JsonResponse {

    private boolean success;
    private Object data;

    public JsonResponse(boolean isOK, Object data) {
        this.success = isOK;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
