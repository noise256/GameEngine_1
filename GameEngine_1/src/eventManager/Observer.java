package eventManager;

import java.util.EventObject;

public interface Observer<T extends EventObject> {
	public void update(T event);
}
