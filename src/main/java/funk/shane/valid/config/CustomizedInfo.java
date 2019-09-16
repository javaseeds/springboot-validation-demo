package funk.shane.valid.config;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.stereotype.Component;
import org.apache.catalina.util.ServerInfo;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.info.InfoContributor;

/**
 * To add a bit more "oomph" to the /info endpoint, I'm adding this extra info based off 
 * https://memorynotfound.com/spring-boot-customize-actuator-info-endpoint-example-configuration/
 */
@Component
public class CustomizedInfo implements InfoContributor {
    @Override
    public void contribute(Builder builder) {
		builder.withDetail("spring.boot.version", SpringBootVersion.getVersion())
			.withDetail("tomcat.version", ServerInfo.getServerInfo());
    }
}