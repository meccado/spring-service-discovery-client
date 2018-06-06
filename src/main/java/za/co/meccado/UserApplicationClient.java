package za.co.meccado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Hello world!
 *
 */
@RestController
@SpringBootApplication
@RibbonClient(name="say-hello", configuration= HelloApplicationServiceConfiguration.class)
public class UserApplicationClient 
{
    public static void main( String[] args )
    {
        SpringApplication.run(UserApplicationClient.class, args);
    }
    
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
    	return new RestTemplate();
    }
    
    @Autowired
    RestTemplate restTemplate;
    
    @RequestMapping(value ="/hi")
    public String hi(@RequestParam(value="name", defaultValue="Meccado") String name) {
    	String greeting = this.restTemplate.getForObject("http://say-hello/greetings", String.class);
    	return String.format("%s, %s!", greeting, name);
    }
}
