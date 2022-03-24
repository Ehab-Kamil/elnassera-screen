package com.elnassera.sdk.screen.service;

import com.elnassera.sdk.screen.model.ScreenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Title: Details.java
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright(c) Bishoy George, 2022
 * </p>
 *
 * @version 1.0
 * @date 24/03/2022
 */
@Component
public class ProptertiesLoader {

	@Autowired
	private Environment env;

	public ScreenProperties loadPropsForSN(String sn) {
		String[] details = env.getProperty(sn).split(",");
		return new ScreenProperties(details[0], details[1], details[2]);
	}

}
