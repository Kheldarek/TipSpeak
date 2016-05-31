package org.ps.connection;

import org.ps.gui.controllers.ConnectController;
import org.ps.gui.controllers.MainWindowController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
			getChannelList();
		} catch (IOException e)
		{
			System.err.println("Impossible to establish connection");
		}

	}

	public String getChannelList()
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
			gui.PopulateTreeView(ip,channels,users);

		} catch (IOException e)
		{
			System.err.println(e.getMessage());
		}

		return channelList;
	}

	public void pickChannel(int port, String name)
	{

	}

	public void sendTextMessage(String message)
	{

	}


}
