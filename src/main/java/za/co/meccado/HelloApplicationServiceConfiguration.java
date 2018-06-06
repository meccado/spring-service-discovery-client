package za.co.meccado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.NoOpPing;
import com.netflix.loadbalancer.PingUrl;

public class HelloApplicationServiceConfiguration {
	
	@Autowired
	IClientConfig ribbonClientConfig;
	
	/** Default Ping is new NoOpPing():
	 * Which doesn’t actually ping server instances, 
	 * instead always reporting that they’re stable
	 * 
	 * WE use a PingUrl 
	 * Which will ping a URL to check the status of each server
	 * 
	 * @param config
	 * @return
	 */
	
	@Bean
	public IPing ribbonPing(IClientConfig config) {
		return new PingUrl();
	}
	
	/** 
	 * 
	 * Default Rule is new ZoneAvoidanceRule()
	 *  Which avoids the Amazon EC2 zone that has the most malfunctioning servers, 
	 *  and might thus be a bit difficult to try out in our local environment
	 * 
	 * We use AvailabilityFilteringRule():
	 * Which filter out any servers in an “open-circuit” state: 
	 * 		if a ping fails to connect to a given server, 
	 * 		or if it gets a read failure for the server, 
	 * 
	 * Ribbon will consider that server “dead” until it begins to respond normally.
	 * 
	 * @param config
	 * @return
	 */
	@Bean
	public IRule ribbonRule(IClientConfig config) {
		return new AvailabilityFilteringRule();
	}

}
