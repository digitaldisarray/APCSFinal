package andres.networking.client;

public interface NetworkListener {
	public abstract void messageReceived(String sender, String ID, String message);
}
