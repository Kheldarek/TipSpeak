package org.ps.gui.listcells;

import javafx.scene.control.ListCell;
import org.ps.data.ServerData;

/**
 * Created by psend on 24.05.2016.
 */
public class ServerCell extends ListCell<ServerData>
{
	@Override
	public void updateItem(ServerData item, boolean empty)
	{
		super.updateItem(item, empty);

		int index = this.getIndex();
		String name = null;

		// Format name
		if (item == null || empty)
		{
		} else
		{
			name = (index + 1) + "\t" +
					item.getName() + "\t" +
					item.getIp() + "\t" +
					item.getPort() + "\t" +
					item.getNick();
		}

		this.setText(name);
		setGraphic(null);
	}
}
