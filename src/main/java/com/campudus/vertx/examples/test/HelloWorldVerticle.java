package com.campudus.vertx.examples.test;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.deploy.Verticle;

public class HelloWorldVerticle extends Verticle implements Handler<Message<JsonObject>> {

	@Override
	public void start() throws Exception {
		vertx.eventBus().registerHandler("hello", this);
	}

	@Override
	public void handle(Message<JsonObject> event) {
		if (event.body.getString("say") == null) {
			event.reply(jsonError("action missing"));
			return;
		}
		switch (event.body.getString("say")) {
		case "hello":
			event.reply(jsonResult("hi there!"));
			break;
		case "bye":
			event.reply(jsonResult("have a nice day!"));
			break;
		default:
			event.reply(jsonError("don't know what to say"));
			break;
		}
	}

	public static JsonObject jsonResult(String text) {
		return new JsonObject().putString("result", text);
	}

	public static JsonObject jsonError(String text) {
		return new JsonObject().putString("error", text);
	}
}
