package org.ps.gui.controllers;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ps.connection.ConnectionManager;

import java.io.IOException;
import java.util.List;

public class MainWindowController
{

	public TextArea chatView;
	public TextField chatSend;
	public Button sendBtn;
	public MenuItem about;
	public MenuItem close, connect, disconnect, favorites;
	public TreeView channelView;
	public ConnectionManager connectionManager;
	public int oo;

	private Scene scene;

	public MainWindowController() throws IOException
	{
		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/MainWindow.fxml"));
		scene = new Scene(root);
		close = ((Menu) ((MenuBar) root.getTop()).getMenus().get(0)).getItems().get(3);
		disconnect = ((Menu) ((MenuBar) root.getTop()).getMenus().get(0)).getItems().get(2);
		connect = ((Menu) ((MenuBar) root.getTop()).getMenus().get(0)).getItems().get(1);
		favorites = ((Menu) ((MenuBar) root.getTop()).getMenus().get(0)).getItems().get(0);
		about = ((Menu) ((MenuBar) root.getTop()).getMenus().get(2)).getItems().get(0);
		channelView = (TreeView) root.getCenter();
		sendBtn = (Button) (((HBox) ((VBox) root.getBottom()).getChildren().get(1)).getChildren().get(0));
		chatSend = (TextField) (((HBox) ((VBox) root.getBottom()).getChildren().get(1)).getChildren().get(1));
		chatView = (TextArea) (((VBox) root.getBottom()).getChildren().get(0));
		SetEvents();
		//connectionManager = new ConnectionManager();


	}

	public void SetEvents()
	{

		sendBtn.setOnAction(event ->
		{
			connectionManager.sendTextMessage(chatSend.getText());
		});

		disconnect.setOnAction(event -> {
			//connectionManager = new ConnectionManager();
			//connectionManager.run();

		});
		connect.setOnAction(event ->
		{
			try
			{
				Stage stage = new Stage();
				stage.setScene(new ConnectController(this).getScene());
				stage.setTitle("Connect");
				stage.show();
			} catch (IOException ex)
			{

			}
		});

		favorites.setOnAction(event ->
		{
			try
			{
				Stage stage = new Stage();
				stage.setScene(new FavoritesController(this).getScene());
				stage.setTitle("Favorites");
				stage.show();
			} catch (IOException ex)
			{

			}
		});
		close.setOnAction(event ->
		{
			Platform.exit();
		});

		about.setOnAction(event ->
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("About");
			alert.setHeaderText("TipSpeak");
			alert.setContentText("Voice and text group communication program, developed by Piotr Sendrowski and Filip Sochal");
			alert.showAndWait();
		});

		channelView.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent mouseEvent)
			{
				if(mouseEvent.getClickCount() == 2)
				{
					TreeItem<String> item = (TreeItem<String>)channelView.getSelectionModel().getSelectedItem();
					connectionManager.pickChannel(item.getValue());

				}
			}
		});
	}

	public Scene getScene()
	{
		return scene;
	}

	public void PopulateTreeView(String server,List<String> channels, List<String> users)
	{
		channelView.setRoot(new TreeItem(server));
		TreeItem<String> tmpChannel;
		for (String channel : channels)
		{
			tmpChannel = new TreeItem<>(channel);

			for (String userList : users)
			{
				if(!userList.equals("NULL"))
				for(String user:userList.split("[,]"))
					tmpChannel.getChildren().add(new TreeItem<>(user));
			}
			channelView.getRoot().getChildren().add(tmpChannel);

		}
		channelView.getRoot().setExpanded(true);
	}

}


