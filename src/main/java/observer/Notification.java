package observer;

import observer.enums.NotificationCode;
import resource.implementation.InformationResource;
import resource.tree.DBNode;

public class Notification {
    private NotificationCode code;
    private Object data;

    public Notification(NotificationCode resourceLoaded, DBNode node) {
        this.code = resourceLoaded;
        this.data = node;
    }

    public NotificationCode getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public void setCode(NotificationCode code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
