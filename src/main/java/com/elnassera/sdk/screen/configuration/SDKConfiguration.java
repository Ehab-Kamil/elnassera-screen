package com.elnassera.sdk.screen.configuration;

import com.sun.jna.Native;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Title: SDKConfiguration.java
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright(c) Ehab Kamil, 2022
 * </p>
 *
 * @author <a href="mailto:ehabkamil2@gmail.com">Ehab Attia</a>
 * @version 1.0
 * @date 25/02/2022
 */
@Configuration
public class SDKConfiguration {

	@Value("${sdkURL}")
	private String sdkURL;

	@Bean
	public ViplexCore getViplexCore() {
		return (ViplexCore) Native.loadLibrary(sdkURL, ViplexCore.class);
	}

}
