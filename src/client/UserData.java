package client;

import java.io.Serializable;

/**
 * Author   NieYinjun
 * Date     2018/9/22 21:18
 * TAG
 */
public class UserData implements Serializable {
    private static final long serialVersionUID=1L;
    private String id;
    private String name;
    private String responseMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String requestMessage) {
        this.responseMessage = requestMessage;
    }

    @Override
    public String toString() {
        return "server.UserParam{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", requestMessage='" + responseMessage + '\'' +
                '}';
    }
}
