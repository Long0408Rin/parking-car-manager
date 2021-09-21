package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import classd.Order;
import classd.Parking_Lot;
import classd.User;
import data.CarM;
import data.DBConnect;
import data.OrderM;
import data.PlM;
import data.UserM;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserHistoryController {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	User u;
	UserM um = new UserM();
	CarM cm = new CarM();
	PlM plm = new PlM();
	OrderM om = new OrderM();
	@FXML
	private TableView<Order> tv_history;
	@FXML
	private Button btn_refresh;
	@FXML
	private Label lb_name;
	@FXML
	private Label lb_plate;
	@FXML
	private Label lb_fee;

	public void initobj() throws SQLException {
		u = um.selectbyid(UserSession.getInstance().getId());
	}

	public void init(final UserMenuController UserMenuController) throws SQLException {
		initobj();
		viewhistory();
	}

	public void viewhistory() throws SQLException {
		lb_plate.setText("-");
		lb_name.setText("-");
		lb_fee.setText("-");

		tv_history.getColumns().clear();
		tv_history.getItems().clear();

		TableColumn<Order, Integer> IDColumn = new TableColumn<Order, Integer>("ID");
		IDColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id_order"));
		TableColumn<Order, Timestamp> TimeinColumn = new TableColumn<Order, Timestamp>("Giờ vào");
		TimeinColumn.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("time_in"));
		TableColumn<Order, Timestamp> TimeoutColumn = new TableColumn<Order, Timestamp>("Giờ ra");
		TimeoutColumn.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("time_out"));
		tv_history.getColumns().addAll(IDColumn, TimeinColumn, TimeoutColumn);
		ObservableList<Order> list = om.selectforuser(UserSession.getInstance().getId());
		tv_history.setItems(list);
	}

	@FXML
	public void evnt_getitem(MouseEvent event) throws SQLException {
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
			Order sel = tv_history.getSelectionModel().getSelectedItem();
			lb_plate.setText(sel.getPlate_number());
			lb_fee.setText(String.valueOf(sel.getFee()));
			Parking_Lot obj = plm.selectbyidpl(sel.getId_pl());
			lb_name.setText(obj.getPl_name());
		}
	}

	@FXML
	public void evnt_refresh(MouseEvent event) throws SQLException {
		viewhistory();
	}
}
