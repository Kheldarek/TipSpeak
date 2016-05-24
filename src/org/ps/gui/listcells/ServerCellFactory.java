package org.ps.gui.listcells;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.ps.data.ServerData;
import javafx.util.Callback;


/**
 * Created by psend on 24.05.2016.
 */
public class ServerCellFactory implements Callback<ListView<ServerData>, ListCell<ServerData>>
{

		@Override
		public ListCell<ServerData> call(ListView<ServerData> listview)
		{
			return new ServerCell();
		}

}
