/*
 *   Copyright (c) 2019 Shane Funk - Javaseeds Consulting
 *   All rights reserved.

 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */

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