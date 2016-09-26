package org.ps.gui.serverTable;

/**
 * Created by psend on 26.09.2016.
 */

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ServerTableItem
{

	private  StringProperty name;
	private StringProperty ip;
	private  StringProperty nick;
	private  StringProperty port;
	private  StringProperty password;

	public ServerTableItem() {
		this(null, null,null,null,null);
	}

	public ServerTableItem(String name, String ip, String password,String nick, String port) {
		this.name = new SimpleStringProperty(name);
		this.ip = new SimpleStringProperty(ip);
		this.password = new SimpleStringProperty(password);
		this.nick = new SimpleStringProperty(nick);
		this.port = new SimpleStringProperty(port);

	}

	public String getName()
	{
		return name.get();
	}

	public StringProperty nameProperty()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name.set(name);
	}

	public String getIp()
	{
		return ip.get();
	}

	public StringProperty ipProperty()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip.set(ip);
	}

	public String getNick()
	{
		return nick.get();
	}

	public StringProperty nickProperty()
	{
		return nick;
	}

	public void setNick(String nick)
	{
		this.nick.set(nick);
	}

	public String getPort()
	{
		return port.get();
	}

	public StringProperty portProperty()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port.set(port);
	}

	public String getPassword()
	{
		return password.get();
	}

	public StringProperty passwordProperty()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password.set(password);
	}

}
