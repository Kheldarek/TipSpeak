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
public class ConnectionManager implements Runnable
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

	@Override
	public void run()
	{
		Connect();
	}

	public ConnectionManager(String ip, int port, String password, String nick, MainWindowController gui)
	{
		this.ip = ip;
		this.port = port;
		this.password = password;
		this.nick = nick;
		this.gui = gui;
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
			response = socketReader.readLine();
			System.out.println(response);
			socketWriter.write("READY!\r");
			socketWriter.flush();
			while (!((response = socketReader.readLine()).contains("@")))
			{
				channelList += response + "\n";
				String[] tmp = response.split("[:]");
				channels.add(tmp[0].split("[=]")[0]);
				{
					if(tmp.length>1)
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
			}
			else
			{
				tmp += "," + nick;
				users.remove(channelNumber);
				users.add(channelNumber,nick);
			}
			Platform.runLater(() -> gui.PopulateTreeView(ip,channels,users));


		} catch (IOException e)
		{
			System.err.println(e.getMessage());
		}

		return channelList;
	}

	public void pickChannel(String name)
	{
		socketWriter.write(String.format(SIP.CHANGE_CHANNEL,name) + "\r");
		socketWriter.flush();
		try
		{
			String confirm = socketReader.readLine();
			int port = Integer.parseInt(confirm.split("[:]")[1]);
			send = new DatagramSocket(port);
			//DatagramPacket x = new DatagramPacket();

			
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}

	}

	public void sendTextMessage(String message)
	{
		byte dataToSend[] = new byte[message.getBytes().length];
		DatagramPacket packetToSend = new DatagramPacket(dataToSend,dataToSend.length);
		try{
			send.send(packetToSend);
			Platform.runLater(() ->
					gui.chatView.setText(gui.chatView.getText() + "\n" + message));
		}

		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}

	}

	public void receiveTextMessage ()
	{
		byte[] receiveData = new byte[256];

		DatagramPacket received = new DatagramPacket(receiveData,receiveData.length);
		try
		{
			receive.receive(received);
			String message = new String(received.getData());
			Platform.runLater(() ->
					gui.chatView.setText(gui.chatView.getText() + "\n" + message));

		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}

	}



}
