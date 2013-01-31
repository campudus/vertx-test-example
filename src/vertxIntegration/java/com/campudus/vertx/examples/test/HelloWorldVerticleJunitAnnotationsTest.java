package com.campudus.vertx.examples.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.test.TestVerticle;
import org.vertx.java.test.VertxConfiguration;
import org.vertx.java.test.VertxTestBase;
import org.vertx.java.test.junit.VertxJUnit4ClassRunner;
import org.vertx.java.test.utils.QueueReplyHandler;

@RunWith(VertxJUnit4ClassRunner.class)
@VertxConfiguration
@TestVerticle(main = "com.campudus.vertx.examples.test.HelloWorldVerticle")
public class HelloWorldVerticleJunitAnnotationsTest extends VertxTestBase {

	private long timeout = 10L;

	private void checkAsync(String address, JsonObject question, Handler<JsonObject> handler) {
		final LinkedBlockingQueue<JsonObject> queue = new LinkedBlockingQueue<>();

		getVertx().eventBus().send(address, question,
				new QueueReplyHandler<JsonObject>(queue, timeout));

		try {
			JsonObject answer = queue.poll(timeout, TimeUnit.SECONDS);

			if (answer == null) {
				fail("Did not receive answer in time for: " + question.encode());
			}

			handler.handle(answer);

		} catch (InterruptedException e) {
			//
		}
	}

	@Test
	public void testHello() {
		checkAsync("hello", new JsonObject().putString("say", "hello"), new Handler<JsonObject>() {
			@Override
			public void handle(JsonObject event) {
				assertEquals("hi there!", event.getString("result"));
				assertNull(event.getString("error"));
			}
		});
	}

	@Test
	public void testBye() {
		checkAsync("hello", new JsonObject().putString("say", "bye"), new Handler<JsonObject>() {
			@Override
			public void handle(JsonObject event) {
				assertEquals("have a nice day!", event.getString("result"));
				assertNull(event.getString("error"));
			}
		});
	}

	@Test
	public void testMissing() {
		checkAsync("hello", new JsonObject().putString("foobar", "hello"),
				new Handler<JsonObject>() {
					@Override
					public void handle(JsonObject event) {
						assertNull(event.getString("result"));
						assertEquals("action missing", event.getString("error"));
					}
				});
	}

	@Test
	public void testUnknown() {
		checkAsync("hello", new JsonObject().putString("say", "foobar"), new Handler<JsonObject>() {
			@Override
			public void handle(JsonObject event) {
				assertNull(event.getString("result"));
				assertEquals("don't know what to say", event.getString("error"));
			}
		});
	}
}
