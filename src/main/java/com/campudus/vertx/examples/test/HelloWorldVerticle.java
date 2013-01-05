package com.campudus.vertx.examples.test;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.deploy.Verticle;

public class HelloWorldVerticle extends Verticle implements
		Handler<Message<JsonObject>> {

	@Override
	public void start() throws Exception {
		vertx.eventBus().registerHandler("hello", this);
	}

	@Override
	public void handle(Message<JsonObject> event) {
		if (event.body.getString("say") == null) {
			event.reply(new JsonObject().putString("error", "action missing"));
			return;
		}
		switch (event.body.getString("say")) {
		case "hello":
			event.reply(new JsonObject().putString("result", "hi there!"));
			break;
		case "bye":
			event.reply(new JsonObject()
					.putString("result", "have a nice day!"));
			break;
		default:
			event.reply(new JsonObject().putString("error",
					"don't know what to say"));
			break;
		}
	}
}
