package andres.networking.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Set;

public class Server {
	/*
	 * Server constants
	 */
	public static final int SERVER_PORT_NUMBER = 5000;
	public static final String SERVER_ID = "server"; 
	public static final String GET_USERS_ONLINE = "get users";//Used to request online ips
	public static final String INVALID_USERNAME = "invalid username";//Used to communicate that setting the username was unsuccessful
	public static final String VALID_USERNAME = "valid username";//Used to communicate that setting the username was unsuccessful
	public static final String RECIPIENT_NOT_FOUND = "recipient not found";//Used to communicate to client that a username was not found
	public static final String SET_NAME = "set name";//Used to set username
	public static final String USERNAME_VALID = "username valid";//Used to communicate that setting the username was successful
	public static final String CHECK_CONNECTION = "check";
	public static final String SEND_TO_ALL = "all";

	/*
	 * Server fields
	 */

	private ServerSocket server;//Socket for server
	private Socket client;//Socket for client
	private LinkedHashMap< String,ServerThread> serverThreads; //List of all server threads
	private LinkedHashMap< String, String> onlineNames; //List of all username
	private boolean logPing;

	protected String onlineUsers;//Contains all online usernames/ips

	// Constructor
	public Server(){
		serverThreads = new LinkedHashMap<String, ServerThread>(); //Ip is key, server thread is value
		onlineNames = new LinkedHashMap<String, String>(); //name is key, ip is value
		logPing = false;
	}

	// Starts the server
	protected void startServer() throws IOException {

		server = new ServerSocket(SERVER_PORT_NUMBER);

		appendServerTraffic("Server started succesfully\n");

		while(true) {
			client = server.accept();//Waits for a client to connect
			String clientIp =  client.getInetAddress().getHostAddress();

			appendServerTraffic(clientIp + " connected to the server\n");

			// Creates a new server thread with the connection to the client
			ServerThread serverThread = new ServerThread(client,this);
			new Thread(serverThread).start();

			// Adds the thread to hash map. clientIp is the key, serverThread is the value
			serverThreads.put(clientIp, serverThread);
			setClientName(clientIp, clientIp);

			updateOnlineUsers();
		}

	}

	// Sends a message 
	private void sendMessage(String sender, String recipient, String ID, String message) {
		String recipientIp = onlineNames.get(recipient);
		serverThreads.get(recipientIp).sendMessage(sender, ID, message);
	}

	// Sends a message to all online
	private void sendMessageToAll(String sender, String ID, String message) {
		for(String recipient: onlineNames.keySet()) {
			if(!recipient.equals(sender))
				sendMessage(sender,recipient, ID, message);
		}
	}

	// Notifies all connected clients of the online ips/usernames
	private void notifyClientsOfOnlineUsers() {
		for(ServerThread s : serverThreads.values()) {
			s.sendMessage(SERVER_ID, GET_USERS_ONLINE, getUsersOnline().toString());
		}
	}

	// Returns the online usernames
	private Set<String> getUsersOnline(){
		return onlineNames.keySet();
	}

	// Deletes an ip
	private synchronized void deleteIp(String name) {
		serverThreads.remove(onlineNames.get(name));
		onlineNames.remove(name);
		updateOnlineUsers();
	}

	// Sets a username for a client
	private void  setClientName(String clientName, String clientIp) {
		if(getUsersOnline().contains(clientIp))
			onlineNames.remove(clientIp);
		onlineNames.put(clientName, clientIp); 
		updateOnlineUsers();
	}


	// Updates the onlineUsers
	protected void updateOnlineUsers() {
		if(onlineNames.size() == 0) {
			onlineUsers = "No one is currently online";
		} else {
			onlineUsers = "";
			for(String name : getUsersOnline())
				onlineUsers+=name+"\n";

		}
	}

	// Appends server traffic
	protected void appendServerTraffic(String str) {
		System.out.print(str);
	}

	// Class handles a connection with a client
	private  class ServerThread implements Runnable {
		private Server server; // Used to communicate with the server object
		private Socket client; // Used to communicate with the client
		private BufferedReader clientIn; // Used to receive messages form the client
		private PrintStream clientOut; // Used to send messages to the client
		private String clientIp; // Stores the client's IP address
		private String clientName; // Stores the client's username (set as default to its IP)
		private boolean connected;

		//Constructor
		ServerThread(Socket client, Server server ) throws IOException {

			this.client = client;
			this.server = server;

			clientIp = client.getInetAddress().getHostAddress();
			clientName = clientIp;

			clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
			clientOut = new PrintStream(client.getOutputStream());

			connected = true;

			checkConnection();
		}

		// Communicating with the client
		@Override
		public void run() {
			while( !client.isClosed()) {
				try{
					// Required format -> recipient:id:message
					String message = clientIn.readLine();

					int i1 = message.indexOf(":");
					int i2 = message.indexOf(":", i1 + 1);

					String recipient = message.substring(0, i1);
					String ID  = message.substring(i1 + 1, i2);
					message = message.substring(i2 + 1);

					if(recipient.equals(SERVER_ID)) {

						if(logPing || !ID.equals(CHECK_CONNECTION)) {
							appendServerTraffic(clientName + " >>> Server: " + ID +"-"+ message + "\n");
						}
						if(ID.equals(GET_USERS_ONLINE)) {
							String onlineIps = getUsersOnline().toString();
							sendMessage(recipient, GET_USERS_ONLINE, onlineIps);
						}
						else if(ID.equals(CHECK_CONNECTION)) {
							connected = true;
						}
						else if(ID.equals(SET_NAME)) {
							appendServerTraffic(clientIp + " >>> Server: " +ID +"-"+message + "\n");

							/*
							 * Requirements for a valid username:
							 * 	-> Must not be taken
							 * 	-> Must be comprised of alphanumerals or underscores
							 */
							if(getUsersOnline().contains(message) || !message.matches("^[a-zA-Z0-9_]*$") ) {
								sendMessage(recipient, INVALID_USERNAME, message);
							}
							else {
								clientName = message;
								setClientName(clientName, clientIp);
								sendMessage(recipient, VALID_USERNAME, message);
								notifyClientsOfOnlineUsers();
							}
						}
					}
					else {

						appendServerTraffic(clientName + " >>> " + recipient +": " +ID +"-"+ message + "\n");
						if(recipient.equals(Server.SEND_TO_ALL)) {
							server.sendMessageToAll(clientName, ID, message);
						}
						else if(getUsersOnline().contains(recipient)) {
							server.sendMessage(clientName, recipient, ID, message);
						}
						else {
							appendServerTraffic("Server >>> " + clientName +": " + RECIPIENT_NOT_FOUND + "\n");
							sendMessage(SERVER_ID,  ID, RECIPIENT_NOT_FOUND);
						}
					}
				}
				catch(IOException ex){}
			}
		}

		// Sends a message from a client to a client
		public synchronized void sendMessage(String sender, String ID, String message) {
			clientOut.println(sender + ":"+ ID + ":" + message);
		}


		public void checkConnection() {
			Runnable checkConnection = new Runnable() {
				@Override
				public void run() {
					while(!client.isClosed()) {
						try {
							connected = false;
							sendMessage(SERVER_ID, CHECK_CONNECTION, " ");	
							Thread.currentThread().sleep(1000);

							if(!connected)
								try {
									closeConnection();
								} catch (IOException e) {
									e.printStackTrace();
								}

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};

			new Thread(checkConnection).start();
		}

		// Closes the connection
		public void closeConnection() throws IOException{
			appendServerTraffic(clientName + " disconnected from the server\n");
			connected = false;
			client.close();
			clientIn.close();
			clientOut.close();
			deleteIp(clientName);

			notifyClientsOfOnlineUsers();
		}
	}

	// Initializes the server
	public static void main(String[] args) { 
		Server serverobj = new Server();
		try {
			serverobj.startServer();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
