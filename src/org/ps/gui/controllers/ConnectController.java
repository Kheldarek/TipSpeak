package org.ps.gui.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.ps.connection.ConnectionManager;

import java.io.IOException;

/**
 * Created by psend on 22.05.2016.
 */
public class ConnectController
{
	Scene scene;
	TextField ip, port, password, nick;
	Button connect, cancel;
	ConnectionManager connectionManager;

	public ConnectController(ConnectionManager connectionManager) throws IOException
	{
		this.connectionManager = connectionManager;
		GridPane root = FXMLLoader.load(getClass().getResource("../fxmls/Connect.fxml"));
		scene = new Scene(root);
		SetControlls(root.getChildren());
		SetEvents();
	}

	public Scene getScene()
	{
		return scene;
	}

	private void SetEvents()
	{
		connect.setOnAction(event ->
		{
			connectionManager.Connect(ip.getText(),Integer.parseInt(port.getText()),password.getText(),nick.getText());
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
				case "cancel":
					cancel = (Button) x;
					break;
			}
		}
	}
}
