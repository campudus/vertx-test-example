package com.campudus.vertx.examples.test;

import org.junit.Test;
import org.vertx.java.testframework.TestBase;

public class HelloWorldVerticleOfficialTestFrameworkTest extends TestBase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		startApp(HelloWorldVerticleOfficialTestFrameworkTestClient.class.getName());
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
