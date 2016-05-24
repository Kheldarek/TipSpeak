package org.ps.data;

import java.io.Serializable;

/**
 * Created by psend on 24.05.2016.
 */

public class ServerData implements Serializable
{
	String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getNick()
	{
		return nick;
	}

	public void setNick(String nick)
	{
		this.nick = nick;
	}

	int port;
	String ip;
	String password;
	String nick;

	public ServerData(String name, int port, String ip, String password, String nick)
	{
		this.name = name;
		this.port = port;
		this.ip = ip;
		this.password = password;
		this.nick = nick;
	}

	@Override
	public String toString()
	{
		return super.toString();
	}
}
