package com.campudus.vertx.examples.test

import org.junit.Assert._

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

@RunWith(classOf[VertxJUnit4ClassRunner])
@VertxConfiguration
@TestVerticle(main = "com.campudus.vertx.examples.test.HelloWorldVerticle")
class HelloWorldVerticleJunitAnnotatedScalaTest extends VertxTestBase {

  private val timeout = 10L;

  private def checkAsync(address: String, question: JsonObject, handler: (JsonObject) => Unit) {
    val queue = new LinkedBlockingQueue[JsonObject]();

    getVertx().eventBus().send(address, question,
      new QueueReplyHandler[JsonObject](queue, timeout));

    try {
      val answer = queue.poll(timeout, TimeUnit.SECONDS);

      if (answer == null) {
        fail("Did not receive answer in time for: " + question.encode());
      }

      handler(answer);

    } catch {
      case e: InterruptedException =>
      //
    }
  }

  @Test
  def testHello() {
    checkAsync("hello", new JsonObject().putString("say", "hello"), {
      event =>
        assertEquals(event.getString("result"), "hi there!");
        assertNull(event.getString("error"));
    });
  }

  @Test
  def testBye() {
    checkAsync("hello", new JsonObject().putString("say", "bye"), {
      event =>
        assertEquals(event.getString("result"), "have a nice day!");
        assertNull(event.getString("error"));
    });
  }

  @Test
  def testMissing() {
    checkAsync("hello", new JsonObject().putString("foobar", "hello"), {
      event =>
        assertNull(event.getString("result") == null);
        assertEquals(event.getString("error"), "action missing");
    });
  }

  @Test
  def testUnknown() {
    checkAsync("hello", new JsonObject().putString("say", "foobar"), {
      event =>
        assertNull(event.getString("result"));
        assertEquals(event.getString("error"), "don't know what to say");
    });
  }
}
