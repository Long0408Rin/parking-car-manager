package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import classd.Car;
import classd.User;
import data.CarM;
import data.DBConnect;
import data.UserM;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.scene.control.TableView;
import javafx.scene.control.DatePicker;

public class UserInfoController {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	User u;
	UserM um = new UserM();
	CarM cm = new CarM();
	@FXML
	private Label lb_nametag;
	@FXML
	private Label lb_roletag;
	@FXML
	private Button btn_viewinfo;
	@FXML
	private Button btn_editinfo;
	@FXML
	private Button btn_editcar;
	@FXML
	private TextField fld_plate3;
	@FXML
	private TextField fld_brand3;
	@FXML
	private TextField fld_carname3;
	@FXML
	private TextField fld_color3;
	@FXML
	private ComboBox<String> cb_cartype3;
	@FXML
	private TableView<Car> tv_car3;
	@FXML
	private Button btn_add3;
	@FXML
	private Button btn_edit3;
	@FXML
	private Button btn_del3;
	@FXML
	private Button btn_reset3;
	@FXML
	private TextField fld_name2;
	@FXML
	private DatePicker dp_birth2;
	@FXML
	private ComboBox<String> cb_gender2;
	@FXML
	private TextField fld_email2;
	@FXML
	private TextField fld_phone2;
	@FXML
	private TextField fld_add2;
	@FXML
	private PasswordField fld_pass2;
	@FXML
	private PasswordField fld_newpass2;
	@FXML
	private PasswordField fld_repass2;
	@FXML
	private Button btn_update2;
	@FXML
	private Button btn_reset2;
	@FXML
	private Label lb_email1;
	@FXML
	private Label lb_birth1;
	@FXML
	private Label lb_gender1;
	@FXML
	private Label lb_phone1;
	@FXML
	private Label lb_add1;
	@FXML
	private TableView<Car> tv_car1;
	@FXML
	private StackPane stk_menupane;

	public void initobj() throws SQLException {
		u = um.selectbyid(UserSession.getInstance().getId());
	}

	public void init(final UserMenuController UserMenuController) throws SQLException {
		initobj();
		lb_nametag.setText("Xin ch??o, " + u.getName());
		lb_roletag.setText("T??i kho???n: User");
		viewinfo();
		fld_phone2.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					fld_phone2.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	public void switch_pane(int n) {
		ObservableList<Node> childs = stk_menupane.getChildren();
		if (childs.size() > 1) {
			Node infoNode = childs.get(childs.size() - 1);
			Node editNode = childs.get(childs.size() - 2);
			Node editcarNode = childs.get(childs.size() - 3);
			if (n == 1) {
				infoNode.setVisible(true);
				editNode.setVisible(false);
				editcarNode.setVisible(false);
			} else if (n == 2) {
				infoNode.setVisible(false);
				editNode.setVisible(true);
				editcarNode.setVisible(false);
			} else if (n == 3) {
				infoNode.setVisible(false);
				editNode.setVisible(false);
				editcarNode.setVisible(true);
			}
		}
	}

	private void alert(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.setContentText(text);
		alert.showAndWait();
	}

	public void viewinfo() throws SQLException {
		switch_pane(1);
		lb_email1.setText(u.getEmail());
		if (u.getBirthday() != null)
			lb_birth1.setText(u.getBirthday().toString());
		else
			lb_birth1.setText("");
		lb_gender1.setText(u.getGender());
		lb_phone1.setText(u.getPhone_number());
		lb_add1.setText(u.getAddress());

		tv_car1.getColumns().clear();
		tv_car1.getItems().clear();

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
		tv_car1.getColumns().addAll(PlateColumn, BrandColumn, NameColumn, ColorColumn, TypeColumn);

		ObservableList<Car> list = cm.selectbyiduser(UserSession.getInstance().getId());
		tv_car1.setItems(list);
	}

	public void vieweditinfo() {
		switch_pane(2);

		cb_gender2.getItems().clear();
		cb_gender2.getItems().add("Nam");
		cb_gender2.getItems().add("N???");
		cb_gender2.getItems().add("Kh??c");

		fld_name2.setText(u.getName());
		if (u.getBirthday() != null)
			dp_birth2.setValue(u.getBirthday());
		cb_gender2.setValue(u.getGender());
		fld_email2.setText(u.getEmail());
		fld_phone2.setText(u.getPhone_number());
		fld_add2.setText(u.getAddress());
		fld_pass2.setText("");
		fld_newpass2.setText("");
		fld_repass2.setText("");
	}

	public void vieweditcar() throws SQLException {
		switch_pane(3);
		fld_plate3.setText("");
		fld_brand3.setText("");
		fld_carname3.setText("");
		fld_color3.setText("");
		cb_cartype3.setValue("");

		cb_cartype3.getItems().clear();
		cb_cartype3.getItems().add("4 ch???");
		cb_cartype3.getItems().add("7 ch???");

		tv_car3.getColumns().clear();
		tv_car3.getItems().clear();

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
		tv_car3.getColumns().addAll(PlateColumn, BrandColumn, NameColumn, ColorColumn, TypeColumn);

		ObservableList<Car> list = cm.selectbyiduser(UserSession.getInstance().getId());
		tv_car3.setItems(list);
	}

	private boolean validate_email(String email) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Boolean b = email.matches(EMAIL_REGEX);
		return b;
	}

	private boolean validate_phone(String phone) {
		String PHONE_REGEX = "^(09|03|07|08|05)+([0-9]{8})$";
		Boolean b = phone.matches(PHONE_REGEX);
		return b;
	}

	@FXML
	public void evnt_getitem(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
			Car sel = tv_car3.getSelectionModel().getSelectedItem();
			fld_plate3.setText(sel.getPlate_number());
			fld_brand3.setText(sel.getCar_brand());
			fld_carname3.setText(sel.getCar_name());
			fld_color3.setText(sel.getCar_color());
			cb_cartype3.setValue(sel.getCar_type());
		}
	}

	@FXML
	public void evnt_viewinfo(MouseEvent event) throws SQLException {
		viewinfo();
	}

	@FXML
	public void evnt_editinfo(MouseEvent event) {
		vieweditinfo();
	}

	@FXML
	public void evnt_editcar(MouseEvent event) throws SQLException {
		vieweditcar();
	}

	@FXML
	public void evnt_add3(MouseEvent event) throws SQLException {
		con = DBConnect.conDB();
		if (fld_plate3.getText().isEmpty() || fld_brand3.getText().isEmpty() || fld_carname3.getText().isEmpty()
				|| fld_color3.getText().isEmpty() || cb_cartype3.getValue().isEmpty()) {
			alert("Vui l??ng nh???p ????? th??ng tin");
		} else {
			Car c = new Car(fld_plate3.getText(), UserSession.getInstance().getId(), fld_brand3.getText(),
					fld_carname3.getText(), fld_color3.getText(), cb_cartype3.getValue());
			if (cm.checkduplicate(c.getPlate_number())) {
				alert("Xe ???? t???n t???i");
			} else {
				cm.insertcar(c);
				alert("Th??m th??nh c??ng");
			}
		}
		vieweditcar();
	}

	@FXML
	public void evnt_edit3(MouseEvent event) throws SQLException {
		con = DBConnect.conDB();
		if (fld_plate3.getText().isEmpty() || fld_brand3.getText().isEmpty() || fld_carname3.getText().isEmpty()
				|| fld_color3.getText().isEmpty() || cb_cartype3.getValue().isEmpty()) {
			alert("Vui l??ng nh???p ????? th??ng tin");
		} else {
			Car sel = tv_car3.getSelectionModel().getSelectedItem();
			if (sel == null) {
				alert("Vui l??ng ch???n xe ????? ch???nh s???a th??ng tin");
			} else if (fld_plate3.getText().equals(sel.getPlate_number())) {
				sel.setCar_brand(fld_brand3.getText());
				sel.setCar_name(fld_carname3.getText());
				sel.setCar_color(fld_color3.getText());
				sel.setCar_type(cb_cartype3.getValue());
				cm.updatecar(sel);
				alert("Thay ?????i th??ng tin xe th??nh c??ng");
				vieweditcar();
			} else
				alert("Kh??ng ???????c ph??p thay ?????i bi???n s??? xe");
		}
	}

	@FXML
	public void evnt_del3(MouseEvent event) throws SQLException {
		Car sel = tv_car3.getSelectionModel().getSelectedItem();
		cm.delcar(sel);
		alert("???? x??a");
		vieweditcar();
	}

	@FXML
	public void evnt_reset3(MouseEvent event) {
		fld_plate3.setText("");
		fld_brand3.setText("");
		fld_carname3.setText("");
		fld_color3.setText("");
		cb_cartype3.setValue("");
	}

	@FXML
	public void evnt_update2(MouseEvent event) throws SQLException {
		con = DBConnect.conDB();
		if (u.getPassword().equals(fld_pass2.getText())) {
			if (!fld_newpass2.getText().isEmpty() && fld_newpass2.getText().equals(fld_repass2.getText()))
				u.setPassword(fld_newpass2.getText());
			else if (!fld_newpass2.getText().isEmpty() && !fld_newpass2.getText().equals(fld_repass2.getText())) {
				alert("Vui l??ng x??c nh???n l???i m???t kh???u m???i");
				return;
			}
			u.setName(fld_name2.getText());
			u.setBirthday(dp_birth2.getValue());
			u.setGender(cb_gender2.getValue());
			if (validate_email(fld_email2.getText()) || fld_email2.getText().equals(""))
				u.setEmail(fld_email2.getText());
			else {
				alert("?????a ch??? email kh??ng h???p l???");
				return;
			}
			if (validate_phone(fld_phone2.getText()) || fld_phone2.getText().equals(""))
				u.setPhone_number(fld_phone2.getText());
			else {
				alert("S??? ??i???n tho???i kh??ng h???p l???");
				return;
			}
			u.setAddress(fld_add2.getText());
			um.updateuserbyid(u);
			lb_nametag.setText("Xin ch??o, " + u.getName());
			alert("C???p nh???t th??nh c??ng");
		} else {
			alert("Sai m???t kh???u");
			return;
		}
	}

	@FXML
	public void evnt_reset2(MouseEvent event) {
		fld_name2.setText(u.getName());
		if (u.getBirthday() != null)
			dp_birth2.setValue(u.getBirthday());
		cb_gender2.setValue(u.getGender());
		fld_email2.setText(u.getEmail());
		fld_phone2.setText(u.getPhone_number());
		fld_add2.setText(u.getAddress());
	}
}
