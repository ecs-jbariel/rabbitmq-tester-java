package com.ecsteam.cloud.rabbitmq;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RMQConnectInfo {

	private String username;
	private String password;
	private String vHost;
	private String hostname;
	private int port;

	public RMQConnectInfo() {
		super();
	}

	public RMQConnectInfo(String username, String password, String vHost, String hostname, int port) {
		this();
		setUsername(username);
		setPassword(password);
		setvHost(vHost);
		setHostname(hostname);
		setPort(port);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getvHost() {
		return vHost;
	}

	public void setvHost(String vHost) {
		this.vHost = vHost;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String toUriString() {
		return String.format("amqp:/%s:%s@%s:%d/%s", getUsername(), getPassword(), getHostname(), getPort(),
				getvHost());
	}

}
