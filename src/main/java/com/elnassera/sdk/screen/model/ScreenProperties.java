package com.elnassera.sdk.screen.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * Title: ScreenProperties.java
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
 * @date 24/03/2022
 */
@Data
@AllArgsConstructor
public class ScreenProperties {

	private String ip;
	private String username;
	private String password;
}
