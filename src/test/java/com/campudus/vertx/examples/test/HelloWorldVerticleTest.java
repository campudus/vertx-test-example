package com.campudus.vertx.examples.test;

import org.junit.Test;
import org.vertx.java.framework.TestBase;

public class HelloWorldVerticleTest extends TestBase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		startApp(HelloWorldVerticleTestClient.class.getName());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testHello() {
		startTest(getMethodName());
	}

	@Test
	public void testBye() {
		startTest(getMethodName());
	}

	@Test
	public void testMissing() {
		startTest(getMethodName());
	}

	@Test
	public void testUnknown() {
		startTest(getMethodName());
	}

}
