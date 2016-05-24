package org.ps.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

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



	public void run()
	{
		Connect("localhost",45932,"","");
		getChannelList();
		pickChannel(45933,"test");
	}


	public void Connect(String ip, int port, String password,String nick)
	{

		try
		{
			connect = new Socket(ip,port);
			socketReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			socketWriter = new PrintWriter(connect.getOutputStream(),true);
		}
		catch (IOException e)
		{
			System.err.println("Impossible to establish connection");
		}

	}

	public String getChannelList()
	{
		String response="";
		char[] somethin = new char[10];
		try
		{
				socketReader.read(somethin);


				System.out.println((String.copyValueOf(somethin)));
				socketWriter.write("READY");



		}
		catch(IOException e)
		{
			System.err.println(e.getMessage());
		}

		return response;
	}

	public void pickChannel(int port, String name)
	{

	}

	public void sendTextMessage(String message)
	{

	}


}
