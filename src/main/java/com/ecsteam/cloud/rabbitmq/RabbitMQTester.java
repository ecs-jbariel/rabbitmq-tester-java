package com.ecsteam.cloud.rabbitmq;

import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.ecsteam.cloud.rabbitmq.Out.LogLevel;

public class RabbitMQTester {

	public static ScheduledThreadPoolExecutor globalThreadPool = new ScheduledThreadPoolExecutor(10);

	private static boolean doProduce = true;
	private static boolean doConsume = true;

	public static void main(String[] args) {
		Out.setLogLevel(LogLevel.TRACE);
		Out.i("Starting...");

		RMQConnectInfo info = new RMQConnectInfo("", "", "test", "localhost", 5672);
		final RMQProducer rmqProducer = new RMQProducer(info);
		final RMQConsumer rmqConsumer = new RMQConsumer(info);
		Thread producer = null;
		Thread consumer = null;

		if (doProduce) {
			Out.d("Creating producer...");
			producer = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						rmqProducer.start();
					} catch (IOException e) {
						Out.e("Error starting RMQProducer");
						e.printStackTrace();
					}
				}
			});
			Out.d("Starting producer...");
			producer.start();
		}

		if (doConsume) {
			Out.d("Creating consumer...");
			consumer = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						rmqConsumer.start();
					} catch (IOException e) {
						Out.e("Error starting RQMConsumer");
						e.printStackTrace();
					}
				}
			});
			Out.d("Starting consumer...");
			consumer.start();
		}

		Out.i("Running...");

		Out.i("Press \"ENTER\" to continue...");
		try {
			System.in.read();
		} catch (

		IOException e) {
			e.printStackTrace();
		}

		Out.i("Closing down...");

		if (doProduce) {
			try {
				rmqProducer.stop();
			} catch (IOException e) {
				Out.w("IOException stopping producer!");
				e.printStackTrace();
			}

			if (null != producer) {
				producer.interrupt();
			}
		}

		if (doConsume) {
			try {
				rmqConsumer.stop();
			} catch (IOException e) {
				Out.w("IOException stopping consumer!");
				e.printStackTrace();
			}

			if (null != consumer) {
				consumer.interrupt();
			}
		}

		Out.i("Exiting...");
		System.exit(0);
	}

}
