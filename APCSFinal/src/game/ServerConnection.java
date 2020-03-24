package game;

import java.io.IOException;

import andres.networking.client.*;

public class ServerConnection implements NetworkListener {
	private Client client;

	public void connectToServer(String IP) {
		try {
			client = new Client(IP, this);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

	public void setName(String name) {
		client.setName(name);
	}

	public void sendMessage() {
		client.sendMessageToAll("hi", "hello");
	}

	@Override
	public void messageReceived(String sender, String ID, String message) {
		System.out.println(sender + "," + ID + "," + message);
	}

}
