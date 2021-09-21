package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import classd.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class UserMenuController {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	User u;
	@FXML
	private Button btn_user;
	@FXML
	private Button btn_order;
	@FXML
	private Button btn_history;
	@FXML
	private Button btn_logout;
	@FXML
	private Pane pane_main;

	public void init(final AppManager AppManager) throws SQLException, IOException {
		loadUserInfo();
		btn_logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AppManager.logout();
			}
		});
		btn_user.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					loadUserInfo();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		btn_order.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					loadUserOrder();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		btn_history.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					loadUserHistory();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loadUserInfo() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../form/user/UserInfo.fxml"));
		pane_main.getChildren().removeAll();
		pane_main.getChildren().setAll((Parent) loader.load());
		UserInfoController controller = loader.<UserInfoController>getController();
		controller.init(this);
	}

	public void loadUserHistory() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../form/user/UserHistory.fxml"));
		pane_main.getChildren().removeAll();
		pane_main.getChildren().setAll((Parent) loader.load());

		UserHistoryController controller = loader.<UserHistoryController>getController();
		controller.init(this);

	}

	public void loadUserOrder() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../form/user/UserOrder.fxml"));
		pane_main.getChildren().removeAll();
		pane_main.getChildren().setAll((Parent) loader.load());
		UserOrderController controller = loader.<UserOrderController>getController();
		controller.init(this);

	}
}
