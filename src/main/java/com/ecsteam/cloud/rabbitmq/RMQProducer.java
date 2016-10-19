package com.ecsteam.cloud.rabbitmq;

import java.io.IOException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RMQProducer extends RMQConnection {

	ScheduledFuture<?> publishFuture;

	public RMQProducer() {
		super();
	}

	public RMQProducer(RMQConnectInfo info) {
		super(info);
	}

	@Override
	public void start() throws IOException {

		connect();

		publishFuture = RabbitMQTester.globalThreadPool.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				try {
					currentChannel.basicPublish(exchangeName, routingKey, null,
							String.format("[%s] Test Message", Out.dateTimeAsString()).getBytes());
				} catch (IOException e) {
					Out.e("Error publishing message");
					e.printStackTrace();
				}

			}
		}, 0L, 1L, TimeUnit.SECONDS);

	}

	@Override
	public void stop() throws IOException {
		publishFuture.cancel(true);
		genericStop();
	}

}
