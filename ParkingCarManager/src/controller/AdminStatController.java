package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import classd.Order;
import classd.User;
import data.DBConnect;
import data.OrderM;
import data.PlM;
import data.UserM;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class AdminStatController {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	User u;
	UserM um = new UserM();
	PlM plm = new PlM();
	OrderM om = new OrderM();
	@FXML
	private Label lb_date;
	@FXML
	private Label lb_carin;
	@FXML
	private Label lb_profit;
	@FXML
	private Label lb_plate;
	@FXML
	private Label lb_timein;
	@FXML
	private Label lb_timeout;
	@FXML
	private DatePicker dp_date;
	@FXML
	private TableView<Order> tv_order;

	public void initobj() throws SQLException {
		u = um.selectbyid(UserSession.getInstance().getId());
	}

	public void init(final AdminMenuController AdminMenuController) throws SQLException {
		initobj();
		view();
		dp_date.setOnAction(evnt_seldate);
	}

	public void view() {
		lb_date.setText("-");
		lb_carin.setText("-");
		lb_profit.setText("-");
		lb_plate.setText("-");
		lb_timein.setText("-");
		lb_timeout.setText("-");

		tv_order.getColumns().clear();
		tv_order.getItems().clear();

	}

	EventHandler<ActionEvent> evnt_seldate = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			LocalDate date = dp_date.getValue();

			tv_order.getColumns().clear();
			tv_order.getItems().clear();

			TableColumn<Order, Integer> IDColumn = new TableColumn<Order, Integer>("ID");
			IDColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id_order"));
			TableColumn<Order, String> PlateColumn = new TableColumn<Order, String>("Biển số xe");
			PlateColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("plate_number"));
			TableColumn<Order, Timestamp> TimeinColumn = new TableColumn<Order, Timestamp>("Giờ vào");
			TimeinColumn.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("time_in"));
			TableColumn<Order, Timestamp> TimeoutColumn = new TableColumn<Order, Timestamp>("Giờ ra");
			TimeoutColumn.setCellValueFactory(new PropertyValueFactory<Order, Timestamp>("time_out"));
			tv_order.getColumns().addAll(IDColumn, PlateColumn, TimeinColumn, TimeoutColumn);
			ObservableList<Order> list = om.selectforadmin(date);
			tv_order.setItems(list);
		
			int fee = 0;
			lb_date.setText(date.toString());
			lb_carin.setText(String.valueOf(tv_order.getItems().size()));
			for (int i = 0; i < tv_order.getItems().size(); i++) {
				tv_order.getSelectionModel().select(i);
				Order sel = tv_order.getSelectionModel().getSelectedItem();
				fee += sel.getFee();
				tv_order.getSelectionModel().clearSelection();
			}
			lb_profit.setText(String.valueOf(fee));
		}
	};

	// Event Listener on TableView.onMouseClicked
	@FXML
	public void evnt_selorder(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
			Order sel = tv_order.getSelectionModel().getSelectedItem();
			lb_plate.setText(sel.getPlate_number());
			lb_timein.setText(sel.getTime_in().toLocalTime().toString());
			lb_timeout.setText(sel.getTime_out().toLocalTime().toString());
		}
	}
}
