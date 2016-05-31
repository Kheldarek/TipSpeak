package org.ps.gui.controllers;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.ps.connection.ConnectionManager;
import org.ps.data.ServerData;
import org.ps.data.ServerListHandler;
import org.ps.gui.listcells.ServerCellFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by psend on 22.05.2016.
 */
public class FavoritesController
{
	Scene scene;
	TextField name, ip, port, password, nick;
	Button connect, cancel, add;
	ListView<ServerData> serverList;
	MainWindowController gui;
	ConnectionManager connectionManager;

	public FavoritesController(MainWindowController gui) throws IOException
	{
		GridPane root = FXMLLoader.load(getClass().getResource("../fxmls/Favorites.fxml"));
		scene = new Scene(root);

		ObservableList<Node> objects = root.getChildren();

		SetControlls(objects);
		SetEvents();
		serverList.getItems().addAll(ServerListHandler.getServerList());
		serverList.setCellFactory(new ServerCellFactory());
		this.gui = gui;



	}

	public Scene getScene()
	{
		return scene;
	}

	private void SetEvents()
	{
		serverList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ServerData>() {

			@Override
			public void changed(ObservableValue<? extends ServerData> observable, ServerData oldValue, ServerData newValue) {
				// Your action here
				if(newValue!=null)
				{
					name.setText(newValue.getName());
					port.setText(newValue.getPort() + "");
					ip.setText(newValue.getIp());
					password.setText(newValue.getPassword());
					nick.setText(newValue.getNick());
				}
			}
		});

		add.setOnAction(event ->
		{
			if (!(name.getText().equals("")||port.getText().equals("")||ip.getText().equals("")||
					password.getText().equals("")||nick.getText().equals("")))
			{
				ServerData tmp = new ServerData(name.getText(), Integer.parseInt(port.getText()),ip.getText(),password.getText(),nick.getText());
				List<ServerData> tmplist = ServerListHandler.getServerList();
				tmplist.add(tmp);
				ServerListHandler.saveServerList(tmplist);
				serverList.getItems().clear();
				serverList.getItems().addAll(tmplist);
			}
			else
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("WRONG DATA");
				alert.setContentText("Fields cannot be empty!");
				alert.showAndWait();
			}

		});
		connect.setOnAction(event ->
		{
			if (!(name.getText().equals("")||port.getText().equals("")||ip.getText().equals("")||
					password.getText().equals("")||nick.getText().equals("")))
			{
				 Platform.runLater(new ConnectionManager(ip.getText(), Integer.parseInt(port.getText()), password.getText(), nick.getText(),gui));

			}
		});

		cancel.setOnAction(event -> {
			((Stage) scene.getWindow()).close();
		});

	}

	private void SetControlls(ObservableList<Node> objects)
	{
		for (Node x : objects)
		{

			if (x.getId() == null) continue;
			switch (x.getId())
			{
				case "name":
					name = (TextField) x;
					break;
				case "ip":
					ip = (TextField) x;
					break;
				case "port":
					port = (TextField) x;
					break;
				case "password":
					password = (TextField) x;
					break;
				case "nick":
					nick = (TextField) x;
					break;
				case "connect":
					connect = (Button) x;
					break;
				case "add":
					add = (Button) x;
					break;
				case "cancel":
					cancel = (Button) x;
					break;
				case "serverList":
					serverList = (ListView<ServerData>) x;
					break;
			}
		}
	}
}
