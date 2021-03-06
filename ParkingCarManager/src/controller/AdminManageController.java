package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import classd.Car;
import classd.Parking_Lot;
import classd.User;
import data.DBConnect;
import data.OrderM;
import data.PlM;
import data.UserM;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
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

public class AdminManageController {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	User u;
	UserM um = new UserM();
	PlM plm = new PlM();
	@FXML
	private Label lb_nametag;
	@FXML
	private Label lb_roletag;
	@FXML
	private Button btn_editmanager;
	@FXML
	private Button btn_editpl;
	@FXML
	private StackPane stk_menupane;
	@FXML
	private TextField fld_name2;
	@FXML
	private TextField fld_add2;
	@FXML
	private ComboBox<String> cb_district2;
	@FXML
	private TextField fld_amount2;
	@FXML
	private Button btn_add2;
	@FXML
	private Button btn_edit2;
	@FXML
	private Button btn_del2;
	@FXML
	private Button btn_reset2;
	@FXML
	private TableView<Parking_Lot> tv_pl2;
	@FXML
	private TableView<User> tv_manager2;
	@FXML
	private Label lb_username1;
	@FXML
	private TextField fld_user1;
	@FXML
	private Label lb_password1;
	@FXML
	private PasswordField fld_psswd1;
	@FXML
	private TextField fld_name1;
	@FXML
	private DatePicker dp_birth1;
	@FXML
	private ComboBox<String> cb_gender1;
	@FXML
	private TextField fld_mail1;
	@FXML
	private TextField fld_phone1;
	@FXML
	private TextField fld_add1;
	@FXML
	private Button btn_add1;
	@FXML
	private Button btn_edit1;
	@FXML
	private Button btn_del1;
	@FXML
	private Button btn_reset1;
	@FXML
	private TableView<User> tv_manager1;
	@FXML
	private TextField fld_search1;
	@FXML
	private Button btn_search1;

	public void initobj() throws SQLException {
		u = um.selectbyid(UserSession.getInstance().getId());
	}

	public void init(final AdminMenuController AdminMenuController) throws SQLException {
		initobj();
		lb_nametag.setText("Xin ch??o, admin");
		lb_roletag.setText("T??i kho???n: Admin");
		viewmanager();
		fld_phone1.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					fld_phone1.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		fld_amount2.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					fld_amount2.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	public void switch_pane(int n) {
		ObservableList<Node> childs = stk_menupane.getChildren();
		if (childs.size() > 1) {
			Node managerNode = childs.get(childs.size() - 1);
			Node plNode = childs.get(childs.size() - 2);
			if (n == 1) {
				managerNode.setVisible(true);
				plNode.setVisible(false);
			} else if (n == 2) {
				managerNode.setVisible(false);
				plNode.setVisible(true);
			}
		}
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

	private void alert(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.setContentText(text);
		alert.showAndWait();
	}

	public void viewmanager() throws SQLException {
		switch_pane(1);

		tv_manager1.getItems().clear();
		tv_manager1.getColumns().clear();

		cb_gender1.getItems().clear();
		cb_gender1.getItems().add("Nam");
		cb_gender1.getItems().add("N???");
		cb_gender1.getItems().add("Kh??c");

		fld_user1.setText("");
		fld_psswd1.setText("");
		fld_name1.setText("");
		dp_birth1.setValue(LocalDate.now());
		cb_gender1.setValue("");
		fld_mail1.setText("");
		fld_phone1.setText("");
		fld_add1.setText("");
		fld_search1.setText("");

		TableColumn<User, Integer> IDColumn = new TableColumn<User, Integer>("ID");
		IDColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id_user"));
		TableColumn<User, String> UserColumn = new TableColumn<User, String>("T??n ????ng nh???p");
		UserColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		TableColumn<User, String> PassColumn = new TableColumn<User, String>("M???t kh???u");
		PassColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
		TableColumn<User, String> NameColumn = new TableColumn<User, String>("T??n");
		NameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		TableColumn<User, LocalDate> BirthColumn = new TableColumn<User, LocalDate>("Ng??y sinh");
		BirthColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("birthday"));
		TableColumn<User, String> GenderColumn = new TableColumn<User, String>("Gi???i t??nh");
		GenderColumn.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
		TableColumn<User, String> EmailColumn = new TableColumn<User, String>("Email");
		EmailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		TableColumn<User, String> PhoneColumn = new TableColumn<User, String>("S??T");
		PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
		TableColumn<User, String> AddressColumn = new TableColumn<User, String>("?????a ch???");
		AddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		tv_manager1.getColumns().addAll(IDColumn, UserColumn, PassColumn, NameColumn, BirthColumn, GenderColumn,
				EmailColumn, PhoneColumn, AddressColumn);
		ObservableList<User> list = um.selectmanager();
		tv_manager1.setItems(list);
	}

	public void viewpl() throws SQLException {
		switch_pane(2);

		tv_pl2.getItems().clear();
		tv_pl2.getColumns().clear();
		tv_manager2.getItems().clear();
		tv_manager2.getColumns().clear();

		cb_district2.getItems().clear();
		cb_district2.getItems().add("H???i Ch??u");
		cb_district2.getItems().add("C???m L???");
		cb_district2.getItems().add("Thanh Kh??");
		cb_district2.getItems().add("Li??n Chi???u");
		cb_district2.getItems().add("Ng?? H??nh S??n");
		cb_district2.getItems().add("S??n Tr??");
		cb_district2.getItems().add("H??a Vang");
		cb_district2.getItems().add("Ho??ng Sa");

		fld_name2.setText("");
		fld_add2.setText("");
		cb_district2.setValue("");
		fld_amount2.setText("");

		TableColumn<Parking_Lot, String> LotNameColumn = new TableColumn<Parking_Lot, String>("T??n b??i ?????");
		LotNameColumn.setCellValueFactory(new PropertyValueFactory<Parking_Lot, String>("pl_name"));
		TableColumn<Parking_Lot, String> LotAddColumn = new TableColumn<Parking_Lot, String>("?????a ch???");
		LotAddColumn.setCellValueFactory(new PropertyValueFactory<Parking_Lot, String>("address"));
		TableColumn<Parking_Lot, String> LotDistrictColumn = new TableColumn<Parking_Lot, String>("Qu???n");
		LotDistrictColumn.setCellValueFactory(new PropertyValueFactory<Parking_Lot, String>("district"));
		TableColumn<Parking_Lot, Integer> LotAmountColumn = new TableColumn<Parking_Lot, Integer>("S??? ch???");
		LotAmountColumn.setCellValueFactory(new PropertyValueFactory<Parking_Lot, Integer>("slot_amount"));
		tv_pl2.getColumns().addAll(LotNameColumn, LotAddColumn, LotDistrictColumn, LotAmountColumn);

		TableColumn<User, Integer> IDColumn = new TableColumn<User, Integer>("ID");
		IDColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id_user"));
		TableColumn<User, String> UserColumn = new TableColumn<User, String>("T??n ????ng nh???p");
		UserColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		TableColumn<User, String> NameColumn = new TableColumn<User, String>("T??n");
		NameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		tv_manager2.getColumns().addAll(IDColumn, UserColumn, NameColumn);
		ObservableList<Parking_Lot> list1 = plm.selectall();
		tv_pl2.setItems(list1);
		ObservableList<User> list2 = um.selectmanager();
		tv_manager2.setItems(list2);
	}

	public void evnt_getitem1(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
			User sel = tv_manager1.getSelectionModel().getSelectedItem();
			fld_user1.setText(sel.getUsername());
			fld_psswd1.setText(sel.getPassword());
			fld_name1.setText(sel.getName());
			dp_birth1.setValue(sel.getBirthday());
			cb_gender1.setValue(sel.getGender());
			fld_mail1.setText(sel.getEmail());
			fld_phone1.setText(sel.getPhone_number());
			fld_add1.setText(sel.getAddress());
		}
	}

	public void evnt_getpl2(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
			Parking_Lot sel1 = tv_pl2.getSelectionModel().getSelectedItem();
			fld_name2.setText(sel1.getPl_name());
			fld_add2.setText(sel1.getAddress());
			cb_district2.setValue(sel1.getDistrict());
			fld_amount2.setText(String.valueOf(sel1.getSlot_amount()));

			for (int i = 0; i < tv_manager2.getItems().size(); i++) {
				tv_manager2.getSelectionModel().select(i);
				User sel2 = tv_manager2.getSelectionModel().getSelectedItem();
				if (sel1.getId_user() == sel2.getId_user())
					break;
				tv_manager2.getSelectionModel().clearSelection();
			}
		}
	}

	@FXML
	public void evnt_editmanager(MouseEvent event) throws SQLException {
		viewmanager();
	}

	@FXML
	public void evnt_editpl(MouseEvent event) throws SQLException {
		viewpl();
	}

	@FXML
	public void evnt_add2(MouseEvent event) throws SQLException {
		con = DBConnect.conDB();
		User selman = tv_manager2.getSelectionModel().getSelectedItem();
		if (fld_name2.getText().isEmpty() || fld_add2.getText().isEmpty() || cb_district2.getValue().isEmpty()
				|| fld_amount2.getText().isEmpty()) {
			alert("Vui l??ng ??i???n ?????y ????? th??ng tin");
		} else if (selman == null)
			alert("Vui l??ng ch???n ng?????i qu???n l??");
		else {

			Parking_Lot pl = new Parking_Lot(selman.getId_user(), fld_name2.getText(), fld_add2.getText(),
					cb_district2.getValue(), Integer.parseInt(fld_amount2.getText()));
			if (plm.checkassigned(selman))
				alert("Ng?????i qu???n l?? n??y ???? qu???n l?? b??i ????? xe kh??c");
			else {
				plm.insert(pl);
				alert("???? th??m");
				viewpl();
			}
		}
	}

	@FXML
	public void evnt_edit2(MouseEvent event) throws SQLException {
		con = DBConnect.conDB();
		User selman = tv_manager2.getSelectionModel().getSelectedItem();
		Parking_Lot selpl = tv_pl2.getSelectionModel().getSelectedItem();
		if (selpl == null) {
			alert("Vui l??ng ch???n b??i ????? xe ????? ch???nh s???a th??ng tin");
		} else if (selman == null)
			alert("Vui l??ng ch???n ng?????i qu???n l??");
		else {
			if (plm.checkassigned2(selman, selpl))
				alert("Ng?????i qu???n l?? n??y ???? qu???n l?? b??i ????? xe kh??c");
			else {
				selpl.setId_user(selman.getId_user());
				selpl.setPl_name(fld_name2.getText());
				selpl.setAddress(fld_add2.getText());
				selpl.setSlot_amount(Integer.parseInt(fld_amount2.getText()));
				plm.updatebyidpl(selpl);
				alert("Thay ?????i th??ng tin th??nh c??ng");

				viewpl();
			}
		}
	}

	@FXML
	public void evnt_del2(MouseEvent event) throws SQLException {
		con = DBConnect.conDB();
		Parking_Lot sel = tv_pl2.getSelectionModel().getSelectedItem();
		if (sel != null) {
			plm.delete(sel);
			alert("???? x??a");
			viewpl();
		} else
			alert("Ch???n b??i ????? xe c???n x??a");
	}

	@FXML
	public void evnt_reset2(MouseEvent event) throws SQLException {
		viewpl();
	}

	@FXML
	public void evnt_add1(MouseEvent event) throws SQLException {
		con = DBConnect.conDB();
		if (fld_user1.getText().isEmpty() || fld_psswd1.getText().isEmpty()) {
			alert("Vui l??ng nh???p t??n t??i kho???n v?? m???t kh???u");
		} else if (!fld_mail1.getText().equals("") && !validate_email(fld_mail1.getText()))
			alert("?????a ch??? email kh??ng h???p l???");
		else if (!fld_phone1.getText().equals("") && !validate_phone(fld_phone1.getText()))
			alert("S??? ??i???n tho???i kh??ng h???p l???");
		else {

			User obj = new User(2, fld_user1.getText(), fld_psswd1.getText(), fld_name1.getText(), dp_birth1.getValue(),
					cb_gender1.getValue(), fld_mail1.getText(), fld_phone1.getText(), fld_add1.getText());
			if (um.checkuser(obj)) {
				alert("T??i kho???n ???? t???n t???i");
			} else {
				um.insert(obj);
				alert("???? th??m");
				viewmanager();
			}
		}
	}

	@FXML
	public void evnt_edit1(MouseEvent event) throws SQLException {
		con = DBConnect.conDB();
		if (fld_user1.getText().isEmpty() || fld_psswd1.getText().isEmpty()) {
			alert("Vui l??ng nh???p t??n t??i kho???n v?? m???t kh???u");
		} else if (!fld_mail1.getText().equals("") && !validate_email(fld_mail1.getText()))
			alert("?????a ch??? email kh??ng h???p l???");
		else if (!fld_phone1.getText().equals("") && !validate_phone(fld_phone1.getText()))
			alert("S??? ??i???n tho???i kh??ng h???p l???");
		else {
			User sel = tv_manager1.getSelectionModel().getSelectedItem();
			if (sel == null) {
				alert("Vui l??ng ch???n ng?????i qu???n l?? ????? ch???nh s???a th??ng tin");
			} else if (fld_user1.getText().equals(sel.getUsername())) {
				sel.setPassword(fld_psswd1.getText());
				sel.setName(fld_name1.getText());
				sel.setBirthday(dp_birth1.getValue());
				sel.setGender(cb_gender1.getValue());
				sel.setEmail(fld_mail1.getText());
				sel.setPhone_number(fld_phone1.getText());
				sel.setAddress(fld_add1.getText());
				um.updateuserbyid(sel);
				alert("Thay ?????i th??ng tin th??nh c??ng");
				viewmanager();
			} else
				alert("Kh??ng ???????c ph??p thay ?????i t??n ????ng nh???p");
		}
	}

	@FXML
	public void evnt_del1(MouseEvent event) throws SQLException {
		con = DBConnect.conDB();
		User sel = tv_manager1.getSelectionModel().getSelectedItem();
		if (plm.checkassigned(sel))
			alert("Ng?????i n??y v???n c??n qu???n l?? b??i xe");
		else if (sel != null) {
			um.deleteuserbyid(sel);
			alert("???? x??a");
			viewmanager();
		} else
			alert("Ch???n ng?????i c???n x??a");
	}

	@FXML
	public void evnt_reset1(MouseEvent event) throws SQLException {
		viewmanager();
	}

	@FXML
	public void evnt_search1(MouseEvent event) throws SQLException {
		String search = fld_search1.getText();
		tv_manager1.getItems().clear();
		con = DBConnect.conDB();
		ObservableList<User> list = um.searchmanager(search);
		tv_manager1.setItems(list);
	}
}
