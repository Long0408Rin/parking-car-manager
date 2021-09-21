package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import classd.Parking_Lot;
import classd.User;
import data.DBConnect;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.scene.control.DatePicker;

public class ManagerInfoController {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	User u;
	Parking_Lot pl = null;
	UserM um = new UserM();
	PlM plm = new PlM();
	@FXML
	private Label lb_nametag;
	@FXML
	private Label lb_roletag;
	@FXML
	private Button btn_viewinfo;
	@FXML
	private Button btn_editinfo;
	@FXML
	private Button btn_editpl;
	@FXML
	private StackPane stk_menupane;
	@FXML
	private TextField fld_name3;
	@FXML
	private TextField fld_add3;
	@FXML
	private ComboBox<String> cb_district3;
	@FXML
	private TextField fld_amount3;
	@FXML
	private PasswordField fld_pass3;
	@FXML
	private Button btn_update3;
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
	private Label lb_plname1;
	@FXML
	private Label lb_pladd1;
	@FXML
	private Label lb_pldistrict1;
	@FXML
	private Label lb_plamount1;

	public void initobj() throws SQLException {
		u = um.selectbyid(UserSession.getInstance().getId());
		pl = plm.selectbyiduser(UserSession.getInstance().getId());
	}

	public void init(final ManagerMenuController ManagerMenuController) throws SQLException {
		initobj();
		lb_nametag.setText("Xin chào, " + u.getName());
		lb_roletag.setText("Tài khoản: Manager");
		viewinfo();
		fld_phone2.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					fld_phone2.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		fld_amount3.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					fld_amount3.setText(newValue.replaceAll("[^\\d]", ""));
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

	public void viewinfo() {
		switch_pane(1);
		lb_email1.setText(u.getEmail());
		if (u.getBirthday() != null)
			lb_birth1.setText(u.getBirthday().toString());
		else
			lb_birth1.setText("");
		lb_gender1.setText(u.getGender());
		lb_phone1.setText(u.getPhone_number());
		lb_add1.setText(u.getAddress());
		if (pl == null) {
			lb_plname1.setText("-");
			lb_pladd1.setText("-");
			lb_pldistrict1.setText("-");
			lb_plamount1.setText("-");
		} else {
			lb_plname1.setText(pl.getPl_name());
			lb_pladd1.setText(pl.getAddress());
			lb_pldistrict1.setText(pl.getDistrict());
			lb_plamount1.setText(String.valueOf(pl.getSlot_amount()));
		}
	}

	public void vieweditinfo() {
		switch_pane(2);

		cb_gender2.getItems().clear();
		cb_gender2.getItems().add("Nam");
		cb_gender2.getItems().add("Nữ");
		cb_gender2.getItems().add("Khác");

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

	public void vieweditpl() {
		if (pl == null)
			alert("Chưa được chỉ định bãi đỗ xe");
		else {
			switch_pane(3);

			cb_district3.getItems().clear();
			cb_district3.getItems().add("Hải Châu");
			cb_district3.getItems().add("Cẩm Lệ");
			cb_district3.getItems().add("Thanh Khê");
			cb_district3.getItems().add("Liên Chiểu");
			cb_district3.getItems().add("Ngũ Hành Sơn");
			cb_district3.getItems().add("Sơn Trà");
			cb_district3.getItems().add("Hòa Vang");
			cb_district3.getItems().add("Hoàng Sa");

			fld_name3.setText(pl.getPl_name());
			fld_add3.setText(pl.getAddress());
			cb_district3.setValue(pl.getDistrict());
			fld_amount3.setText(String.valueOf(pl.getSlot_amount()));
			fld_pass3.setText("");
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

	@FXML
	public void evnt_viewinfo(MouseEvent event) {
		viewinfo();
	}

	@FXML
	public void evnt_editinfo(MouseEvent event) {
		vieweditinfo();
	}

	@FXML
	public void evnt_editpl(MouseEvent event) {
		vieweditpl();
	}

	@FXML
	public void evnt_update3(MouseEvent event) throws SQLException {
		if (fld_name3.getText().isEmpty() || fld_add3.getText().isEmpty() || cb_district3.getValue().isEmpty()
				|| fld_amount3.getText().isEmpty() || fld_pass3.getText().isEmpty()) {
			alert("Vui lòng nhập đủ thông tin");
		} else if (!fld_pass3.getText().equals(u.getPassword()))
			alert("Sai mật khẩu");
		else {
			pl.setPl_name(fld_name3.getText());
			pl.setAddress(fld_add3.getText());
			pl.setDistrict(cb_district3.getValue());
			pl.setSlot_amount(Integer.parseInt(fld_amount3.getText()));
			plm.updatebyiduser(pl);
			alert("Thay đổi thông tin thành công");
		}
		vieweditpl();
	}

	@FXML
	public void evnt_reset3(MouseEvent event) {
		vieweditpl();
	}

	@FXML
	public void evnt_update2(MouseEvent event) throws SQLException {
		con = DBConnect.conDB();
		if (u.getPassword().equals(fld_pass2.getText())) {
			if (!fld_newpass2.getText().isEmpty() && fld_newpass2.getText().equals(fld_repass2.getText()))
				u.setPassword(fld_newpass2.getText());
			else if (!fld_newpass2.getText().isEmpty() && !fld_newpass2.getText().equals(fld_repass2.getText())) {
				alert("Vui lòng xác nhận lại mật khẩu mới");
				return;
			}
			u.setName(fld_name2.getText());
			u.setBirthday(dp_birth2.getValue());
			u.setGender(cb_gender2.getValue());
			if (validate_email(fld_email2.getText()) || fld_email2.getText().equals(""))
				u.setEmail(fld_email2.getText());
			else {
				alert("Địa chỉ email không hợp lệ");
				return;
			}
			if (validate_phone(fld_phone2.getText()) || fld_phone2.getText().equals(""))
				u.setPhone_number(fld_phone2.getText());
			else {
				alert("Số điện thoại không hợp lệ");
				return;
			}
			u.setAddress(fld_add2.getText());
			um.updateuserbyid(u);
			lb_nametag.setText("Xin chào, " + u.getName());
			alert("Cập nhật thành công");

		} else {
			alert("Sai mật khẩu");
			return;
		}
	}

	@FXML
	public void evnt_reset2(MouseEvent event) {
		vieweditinfo();
	}
}
