package com.elnassera.sdk.screen.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;

import java.io.Serializable;

/**
 * <p>
 * Title: Request.java
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
 * @date 02/03/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request implements Serializable {

	private JSONObject data;

}
