package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.layout.StackPane;

public class AdminMenuController {
	@FXML
	private Button btn_manage;
	@FXML
	private Button btn_stat;
	@FXML
	private Button btn_logout;
	@FXML
	private StackPane pane_main;

	public void init(final AppManager AppManager) throws SQLException, IOException {
		loadAdminManage();
		btn_logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AppManager.logout();
			}
		});
		btn_stat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					loadAdminStat();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		btn_manage.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					loadAdminManage();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loadAdminManage() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../form/admin/AdminManage.fxml"));
		pane_main.getChildren().removeAll();
		pane_main.getChildren().setAll((Parent) loader.load());
		AdminManageController controller = loader.<AdminManageController>getController();
		controller.init(this);
	}

	public void loadAdminStat() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../form/admin/AdminStat.fxml"));
		pane_main.getChildren().removeAll();
		pane_main.getChildren().setAll((Parent) loader.load());
		AdminStatController controller = loader.<AdminStatController>getController();
		controller.init(this);
	}
}
