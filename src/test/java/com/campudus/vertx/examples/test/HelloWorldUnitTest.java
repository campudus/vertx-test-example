package com.campudus.vertx.examples.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.vertx.java.core.json.JsonObject;

public class HelloWorldUnitTest {

	@Test
	public void testJsonResult() {
		JsonObject jsonResult = HelloWorldVerticle.jsonResult("some result");
		assertEquals("Should result in two equal JsonObjects.", new JsonObject("{\"result\":\"some result\"}"), jsonResult);
	}

	@Test
	public void testJsonError() {
		JsonObject jsonError = HelloWorldVerticle.jsonError("some error message");
		assertEquals("Should result in two equal JsonObjects.", new JsonObject("{\"error\":\"some error message\"}"), jsonError);
	}

}
