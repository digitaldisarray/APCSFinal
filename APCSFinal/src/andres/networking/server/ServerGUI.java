package andres.networking.server;

/*
 * Programmer: Andres Carranza
 * Date: 5/16/2019
 *
 * CLASS DETAILS:
 	- This class holds the graphics and logic for a server
 	- Server communicates with all users
 	- Users communicate with each other through the server
 */
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ServerGUI extends Server {

	/*
	 * GUI Fields and constants
	 */

	private static final int JPANEL_WIDTH = 500;
	private static final int JPANEL_HEIGHT = 500;
	private static final int JFRAME_WIDTH = getJFrameWidth();
	private static final int JFRAME_HEIGHT = getJFrameHeight();
	private JFrame frame;// JFrame for server
	private JLabel onlineTitle;// Label for online header
	private JLabel trafficTitle;// Label for traffic header
	private JTextArea onlineUsersDisplay;// Displays all online usernames/ips
	private JTextArea serverTraffic;// Logs all server traffic
	private JTextArea serverIp;// Displays server ip(connect code)
	private JScrollPane serverTrafficPane;// Scrolls server traffic

	// Constructor
	ServerGUI() {
		super();

		setUpScreen();
	}

	/*
	 * METHODS DEALING WITH GUI
	 */

	// Sets up GUI
	private void setUpScreen() {
		// Creating window
		frame = new JFrame("Server");

		onlineTitle = new JLabel("Online: ");

		onlineUsersDisplay = new JTextArea(27, 15);
		onlineUsersDisplay.setEditable(false);

		JScrollPane onlineIpsPane = new JScrollPane(onlineUsersDisplay, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		trafficTitle = new JLabel("Server Traffic: ");

		serverTraffic = new JTextArea(27, 25);
		serverTraffic.setEditable(false);

		serverTrafficPane = new JScrollPane(serverTraffic, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		serverIp = new JTextArea(1, 10);
		serverIp.setEditable(false);
		String sIp = "unknown";
		try {
			sIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
		}

		serverIp.setText("Server Connection code: " + sIp);
		serverIp.setFont(new Font("Arial", Font.BOLD, 25));

		Box box1 = Box.createVerticalBox();
		box1.add(Box.createVerticalStrut(10));
		box1.add(onlineTitle);
		box1.add(Box.createVerticalStrut(5));
		box1.add(onlineIpsPane);
		box1.add(Box.createVerticalStrut(10));

		Box box2 = Box.createVerticalBox();
		box2.add(Box.createVerticalStrut(10));
		box2.add(trafficTitle);
		box2.add(Box.createVerticalStrut(5));
		box2.add(serverTrafficPane);
		box2.add(Box.createVerticalStrut(10));

		Box box3 = Box.createHorizontalBox();
		box3.add(box1);
		box3.add(Box.createHorizontalStrut(20));
		box3.add(box2);

		Box box4 = Box.createHorizontalBox();
		box4.add(serverIp);

		Box box5 = Box.createVerticalBox();
		box5.add(box3);
		box5.add(Box.createHorizontalStrut(10));
		box5.add(box4);

		Container c = frame.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(box5);

		// setting up window
		frame.setSize(JFRAME_WIDTH, JFRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(0, 0);
		frame.setResizable(false);

		frame.setVisible(true);

	}

	// Updates the onlineUsers
	protected void updateOnlineUsers() {
		super.updateOnlineUsers();
		onlineUsersDisplay.setText(onlineUsers);
	}

	// Appends server traffic
	protected void appendServerTraffic(String str) {
		serverTraffic.append(str);
		serverTraffic.setCaretPosition(serverTraffic.getText().length());
	}

	// returns screen size
	private static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	// Returns jframe height
	private static int getJFrameHeight() {
		Insets insets = getInsets();
		return JPANEL_HEIGHT + insets.bottom + insets.top;
	}

	// returns jframe height
	private static int getJFrameWidth() {
		Insets insets = getInsets();
		return JPANEL_WIDTH + insets.left + insets.right;
	}

	// returns insets
	private static Insets getInsets() {
		JFrame f = new JFrame();
		f.setLocation(getScreenSize().width, getScreenSize().height);
		f.setVisible(true);
		Insets insets = f.getInsets();
		f.setVisible(false);
		return insets;
	}

	// Initializes the server
	public static void main(String[] args) {
		ServerGUI serverobj = new ServerGUI();
		try {
			serverobj.startServer();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
