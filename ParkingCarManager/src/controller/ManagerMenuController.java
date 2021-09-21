package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import classd.Parking_Lot;
import data.DBConnect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;

public class ManagerMenuController {
	@FXML
	private Button btn_manage;
	@FXML
	private Button btn_stat;
	@FXML
	private Button btn_logout;
	@FXML
	private StackPane pane_main;

	public void init(final AppManager AppManager) throws SQLException, IOException {
		loadManagerInfo();
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
					loadManagerStat();
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
					loadManagerInfo();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loadManagerInfo() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../form/manager/ManagerInfo.fxml"));
		pane_main.getChildren().removeAll();
		pane_main.getChildren().setAll((Parent) loader.load());
		ManagerInfoController controller = loader.<ManagerInfoController>getController();
		controller.init(this);
	}

	public void loadManagerStat() throws IOException, SQLException {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Parking_Lot pl = null;
		con = DBConnect.conDB();
		String sql = "select * from parking_lot where id_user = ?";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, UserSession.getInstance().getId());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				pl = new Parking_Lot(resultSet.getInt("id_pl"), resultSet.getInt("id_user"),
						resultSet.getString("pl_name"), resultSet.getString("address"), resultSet.getString("district"),
						resultSet.getInt("slot_amount"), resultSet.getInt("slot_available"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		if (pl == null)
			alert("Chưa được chỉ định bãi đỗ xe");
		else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../form/manager/ManagerStat.fxml"));
			pane_main.getChildren().removeAll();
			pane_main.getChildren().setAll((Parent) loader.load());
			ManagerStatController controller = loader.<ManagerStatController>getController();
			controller.init(this);
		}
	}

	private void alert(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.setContentText(text);
		alert.showAndWait();
	}
}
