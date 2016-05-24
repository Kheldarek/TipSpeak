package org.ps.data;

import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by psend on 24.05.2016.
 */
public class ServerListHandler
{
	public static final String SERVER_LIST_FILE = "serverList.dat";


	public static void saveServerList(List<ServerData> serverList)
	{
		try
		{
			ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream(SERVER_LIST_FILE));
			wy.writeObject(serverList);
			wy.close();
		}
		catch(Exception e)
		{

		}
	}

	public static List<ServerData> getServerList()
	{
		try
		{
			ObjectInputStream we = new ObjectInputStream(new FileInputStream(SERVER_LIST_FILE));
			List<ServerData> data = (List<ServerData>) we.readObject();
			we.close();
			return data;

		}
		catch(Exception e)
		{

		}
		return  null;
	}
}
