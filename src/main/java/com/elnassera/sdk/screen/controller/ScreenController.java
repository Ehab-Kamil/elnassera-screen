package com.elnassera.sdk.screen.controller;

import com.elnassera.sdk.screen.model.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title: ScreenController.java
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
@RestController
@RequestMapping(value = "/screen")
@CrossOrigin
public class ScreenController {

	@GetMapping(path = "/test")
	public String getTableStructure() throws InterruptedException {

		Test.testApi();

		return "Done";
	}
}
