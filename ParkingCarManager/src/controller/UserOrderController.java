package controller;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import classd.Car;
import classd.Order;
import classd.Parking_Lot;
import classd.User;
import data.CarM;
import data.DBConnect;
import data.OrderM;
import data.PlM;
import data.UserM;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import javafx.scene.control.TableView;

public class UserOrderController {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	User u;
	UserM um = new UserM();
	CarM cm = new CarM();
	PlM plm = new PlM();
	OrderM om = new OrderM();
	@FXML
	private TableView<Car> tv_car;
	@FXML
	private TableView<Parking_Lot> tv_lot;
	@FXML
	private TextField fld_time;
	@FXML
	private ComboBox<String> cb_pay;
	@FXML
	private ComboBox<String> cb_district;
	@FXML
	private Button btn_order;
	@FXML
	private Button btn_reset;

	public void initobj() throws SQLException {
		u = um.selectbyid(UserSession.getInstance().getId());
	}

	public void init(final UserMenuController UserMenuController) throws SQLException {
		initobj();
		viewinfo();
		fld_time.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					fld_time.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	public void viewinfo() throws SQLException {
		plm.check_available();
		fld_time.setText("");

		cb_pay.getItems().clear();
		cb_pay.getItems().add("Ti???n m???t");

		cb_district.getItems().clear();
		cb_district.getItems().add("H???i Ch??u");
		cb_district.getItems().add("C???m L???");
		cb_district.getItems().add("Thanh Kh??");
		cb_district.getItems().add("Li??n Chi???u");
		cb_district.getItems().add("Ng?? H??nh S??n");
		cb_district.getItems().add("S??n Tr??");
		cb_district.getItems().add("H??a Vang");
		cb_district.getItems().add("Ho??ng Sa");
		cb_district.getItems().add("T???t c???");

		tv_car.getColumns().clear();
		tv_car.getItems().clear();

		tv_lot.getColumns().clear();
		tv_lot.getItems().clear();

		TableColumn<Car, String> PlateColumn = new TableColumn<Car, String>("Bi???n s??? xe");
		PlateColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("plate_number"));
		TableColumn<Car, String> BrandColumn = new TableColumn<Car, String>("H??ng xe");
		BrandColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("car_brand"));
		TableColumn<Car, String> NameColumn = new TableColumn<Car, String>("T??n xe");
		NameColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("car_name"));
		TableColumn<Car, String> ColorColumn = new TableColumn<Car, String>("M??u xe");
		ColorColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("car_color"));
		TableColumn<Car, Integer> TypeColumn = new TableColumn<Car, Integer>("Lo???i xe");
		TypeColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("car_type"));
		tv_car.getColumns().addAll(PlateColumn, BrandColumn, NameColumn, ColorColumn, TypeColumn);

		TableColumn<Parking_Lot, String> LotNameColumn = new TableColumn<Parking_Lot, String>("T??n b??i ?????");
		LotNameColumn.setCellValueFactory(new PropertyValueFactory<Parking_Lot, String>("pl_name"));
		TableColumn<Parking_Lot, String> LotAddColumn = new TableColumn<Parking_Lot, String>("?????a ch???");
		LotAddColumn.setCellValueFactory(new PropertyValueFactory<Parking_Lot, String>("address"));
		TableColumn<Parking_Lot, String> LotDistrictColumn = new TableColumn<Parking_Lot, String>("Qu???n");
		LotDistrictColumn.setCellValueFactory(new PropertyValueFactory<Parking_Lot, String>("district"));
		TableColumn<Parking_Lot, Integer> LotAmountColumn = new TableColumn<Parking_Lot, Integer>("S??? ch???");
		LotAmountColumn.setCellValueFactory(new PropertyValueFactory<Parking_Lot, Integer>("slot_amount"));
		TableColumn<Parking_Lot, Integer> LotAvailColumn = new TableColumn<Parking_Lot, Integer>("C??n tr???ng");
		LotAvailColumn.setCellValueFactory(new PropertyValueFactory<Parking_Lot, Integer>("slot_available"));
		tv_lot.getColumns().addAll(LotNameColumn, LotAddColumn, LotDistrictColumn, LotAmountColumn, LotAvailColumn);

		ObservableList<Car> list1 = cm.selectbyiduser(UserSession.getInstance().getId());
		tv_car.setItems(list1);
		ObservableList<Parking_Lot> list2 = plm.selectall();
		tv_lot.setItems(list2);
	}

	private void alert(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.setContentText(text);
		alert.showAndWait();
	}

	public int calculatefee(int min, String car_type) {
		int fee = 0;
		switch (car_type) {
		case "4 ch???":
			if (min <= 60)
				fee = 10000;
			else if (min <= 120)
				fee = 22000;
			else
				fee = 22000 + (min / 60 - 2 + 1) * 11000;
			break;
		case "7 ch???":
			if (min <= 60)
				fee = 12000;
			else if (min <= 120)
				fee = 26000;
			else
				fee = 26000 + (min / 60 - 2 + 1) * 13000;
			break;
		}
		return fee;
	}

	// Event Listener on Button.onMouseClicked
	@FXML
	public void evnt_order(MouseEvent event) throws SQLException {
		Car selcar = tv_car.getSelectionModel().getSelectedItem();
		Parking_Lot selpl = tv_lot.getSelectionModel().getSelectedItem();
		if (selcar == null || selpl == null || fld_time.getText().equals("") || cb_pay.getValue() == null) {
			alert("Vui l??ng nh???p ????? th??ng tin");
			return;
		} else if (selpl.getSlot_available() <= 0) {
			alert("B??i ????? xe ???? h???t ch???");
			return;
		} else {
			if (om.checkcar(selcar)) {
				alert("Xe ???? ???????c ?????t, vui l??ng ch???n xe kh??c");
				return;
			}
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("X??c nh???n");
		alert.setHeaderText("X??c nh???n vi???c ?????t ch???");
		int fee = calculatefee(Integer.parseInt(fld_time.getText()), selcar.getCar_type());
		alert.setContentText("Bi???n s??? xe: " + selcar.getPlate_number() + "\n" + "B??i ?????: " + selpl.getPl_name() + "\n"
				+ "Ph?? ????? xe: " + fee);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			int id_payment = 0;
			if (cb_pay.getValue().equals("Ti???n m???t"))
				id_payment = 1;
			Order obj = new Order(selpl.getId_pl(), selcar.getPlate_number(), id_payment, LocalDateTime.now(),
					LocalDateTime.now().plusMinutes(Long.parseLong(fld_time.getText())), fee);
			om.addorder(obj);
			alert("???? ?????t ch???");

		} else {
		}
		viewinfo();
	}

	// Event Listener on Button.onMouseClicked
	@FXML
	public void evnt_reset(MouseEvent event) throws SQLException {
		viewinfo();
	}

	@FXML
	public void evnt_filter(ActionEvent event) throws SQLException {
		tv_lot.getItems().clear();
		ObservableList<Parking_Lot> list = null;
		switch (cb_district.getValue()) {
		case "T???t c???":
			list = plm.selectall();
			tv_lot.setItems(list);
			break;
		default:
			list = plm.selectbydistrict(cb_district.getValue());
			tv_lot.setItems(list);
			break;
		}
	}
}
