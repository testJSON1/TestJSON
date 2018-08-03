package ro.hpe;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import ro.hpe.MessageRestService;

/**
 * @author popescra
 * Base MessageAplication
 *
 */
public class MessageApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	/**
	 * Add Message Services
	 */
	public MessageApplication() {
		singletons.add(new MessageRestService());
	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.core.Application#getSingletons()
	 */
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
