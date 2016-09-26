package org.ps.connection;

import javafx.application.Platform;
import org.ps.gui.controllers.MainWindowController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by psend on 23.05.2016.
 */
public class ConnectionManager
{
	Socket connect;
	DatagramSocket send;
	DatagramSocket receive;
	PrintWriter socketWriter;
	BufferedReader socketReader;
	String ip;
	int port;
	String password;
	String nick;
	MainWindowController gui;
	String last_catched;


	public ConnectionManager(String ip, int port, String password, String nick, MainWindowController gui)
	{
		this.ip = ip;
		this.port = port;
		this.password = password;
		this.nick = nick;
		this.gui = gui;
	}

	public Runnable changeChannel(String name)
	{
		return new ChannelPicker(name);
	}

	public Runnable connect()
	{
		return new ConnectionEstabilisher();
	}

	public class ChannelPicker implements Runnable
	{
		String name;

		public ChannelPicker(String nme)
		{
			name = nme;
		}

		@Override
		public void run()
		{
			pickChannel(name);
		}

		public void pickChannel(String name)
		{
			socketWriter.write(String.format(SIP.CHANGE_CHANNEL, name) + "\r");
			socketWriter.flush();
			System.out.println(String.format(SIP.CHANGE_CHANNEL, name));
			socketWriter.write(SIP.READY + "\r");
			socketWriter.flush();
		}
	}


	public void getChannelList()
	{
		String response = "";
		String channelList = "";
		List<String> channels = new ArrayList<>();
		List<String> users = new ArrayList<>();

		try
		{
			socketWriter.write("READY!\r");
			socketWriter.flush();

			while (!((response = socketReader.readLine()).contains("@")))
			{
				System.out.println(response);
				channelList += response + "\n";
				if (true)
				{
				}
				String[] tmp = response.split("[:]");
				channels.add(tmp[0].split("[=]")[0]);
				{
					if (tmp.length > 1)
						users.add(tmp[1]);
					else
						users.add("NULL");
				}
			}
			socketWriter.write("LIST_RECEIVED!\r");
			socketWriter.flush();
			System.out.println(channelList);

			Platform.runLater(() -> gui.PopulateTreeView(ip, channels, users));
		} catch (Exception e)
		{
			System.err.println(e.getMessage());

		}
	}

	public void getChannelList(String resp)
	{
		List<String> channels = new ArrayList<>();
		List<String> users = new ArrayList<>();

		String[] list = resp.split("[:]")[1].split("[|]");
		for (String x : list)
		{
			channels.add(x.split("[;]")[0].split("=")[0]);
			String y = x.split("[;]")[1];
			if(!y.contains("NULL"))
			users.add(y);

		}

		Platform.runLater(() -> gui.PopulateTreeView(ip, channels, users));

	}


	public class ConnectionEstabilisher implements Runnable
	{

		@Override
		public void run()
		{
			Connect();
		}

		public void Connect()
		{

			try
			{
				connect = new Socket(ip, port);
				socketReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
				socketWriter = new PrintWriter(connect.getOutputStream(), true);
				EstablishServerConnection();
			} catch (IOException e)
			{
				System.err.println("Impossible to establish connection");
			}

		}

		public String EstablishServerConnection()
		{

			String response = "";
			String channelList = "";
			List<String> channels = new ArrayList<>();
			List<String> users = new ArrayList<>();


			try
			{
				socketWriter.write(String.format(SIP.HELLO_CLIENT, nick, password) + "\r");
				socketWriter.flush();
				System.out.println(String.format(SIP.HELLO_CLIENT, nick, password));
				response = socketReader.readLine();
				if (response.equals(SIP.HELLO_SERVER))
				{
					System.out.println(response);
					socketWriter.write("READY!\r");
					socketWriter.flush();
					while (!((response = socketReader.readLine()).contains("@")))
					{
						channelList += response + "\n";
						String[] tmp = response.split("[:]");
						channels.add(tmp[0].split("[=]")[0]);
						{
							if (tmp.length > 1)
								users.add(tmp[1]);
							else
								users.add("NULL");
						}
					}
					socketWriter.write("LIST_RECEIVED!\r");
					socketWriter.flush();
					System.out.println(channelList);

					String default_channel = socketReader.readLine();
					int channelNumber = channels.indexOf(default_channel.split("[:]")[1].split("[=]")[0]);
					String tmp = users.get(channels.indexOf(default_channel.split("[:]")[1].split("[=]")[0]));
					if (tmp == "NULL")
					{
						users.remove(channelNumber);
						users.add(channelNumber, nick);
					} else
					{
						tmp += "," + nick;
						users.remove(channelNumber);
						users.add(channelNumber, tmp);
					}
					Platform.runLater(() -> gui.PopulateTreeView(ip, channels, users));
				} else
				{
					Platform.runLater(() -> gui.showErrorConnectionWindow());
				}

			} catch (IOException e)
			{
				System.err.println(e.getMessage());
			}
			new Thread(new Receiver()).start();


			return channelList;
		}
	}




	public class Receiver implements Runnable
	{
		@Override
		public void run()
		{
			String response;
			String header;
			while (true)
			{
				try
				{
					System.out.println("pre-read in thread");
					response = socketReader.readLine();
					System.out.println(response);
					header = response.split("[:]")[0];
					switch (header)
					{
						case "ACTUALIZE!":
							getChannelList();
							break;
						case "JOIN":
							int port = Integer.parseInt((response.split("[:]")[1]).split("[=]")[1]);
							//getChannelList();
							send = new DatagramSocket(port);
							break;
						case "HELLO!":
							break;
						case "BYE!":
							break;
						case "DENIED!":
							break;
						case "LIST":
							getChannelList(response);
							break;


					}
				} catch (Exception e)
				{
					System.err.println(e.getMessage());
					break;
				}
			}
		}
	}

}
