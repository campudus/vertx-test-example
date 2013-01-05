package com.campudus.vertx.examples.test;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.framework.TestClientBase;

public class HelloWorldVerticleTestClient extends TestClientBase {

	final String moduleName = System.getProperty("module.name");

	@Override
	public void start() {
		super.start();

		container.deployModule(moduleName, new JsonObject(), 1,
				new Handler<String>() {
					public void handle(String result) {
						tu.appReady();
					}
				});
	}

	private void testString(String string, String string2) {
		if (string != null) {
			tu.azzert(string.equals(string2), "got: '" + string
					+ "' but should get '" + string2 + "'");
		} else if (string2 != null) {
			tu.azzert(false, "got null as first string");
		}
	}

	public void testHello() {
		vertx.eventBus().send("hello",
				new JsonObject().putString("say", "hello"),
				new Handler<Message<JsonObject>>() {
					@Override
					public void handle(Message<JsonObject> event) {
						testString(event.body.getString("result"), "hi there!");
						tu.azzert(event.body.getString("error") == null);
						tu.testComplete();
					}
				});
	}

	public void testBye() {
		vertx.eventBus().send("hello",
				new JsonObject().putString("say", "bye"),
				new Handler<Message<JsonObject>>() {
					@Override
					public void handle(Message<JsonObject> event) {
						testString(event.body.getString("result"),
								"have a nice day!");
						tu.azzert(event.body.getString("error") == null);
						tu.testComplete();
					}
				});
	}

	public void testMissing() {
		vertx.eventBus().send("hello",
				new JsonObject().putString("foobar", "hello"),
				new Handler<Message<JsonObject>>() {
					@Override
					public void handle(Message<JsonObject> event) {
						tu.azzert(event.body.getString("result") == null);
						testString(event.body.getString("error"),
								"action missing");
						tu.testComplete();
					}
				});
	}

	public void testUnknown() {
		vertx.eventBus().send("hello",
				new JsonObject().putString("say", "foobar"),
				new Handler<Message<JsonObject>>() {
					@Override
					public void handle(Message<JsonObject> event) {
						tu.azzert(event.body.getString("result") == null);
						testString(event.body.getString("error"),
								"don't know what to say");
						tu.testComplete();
					}
				});
	}
}
