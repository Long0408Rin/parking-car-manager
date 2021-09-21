package controller;

import java.sql.*;

import data.DBConnect;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;

public class LoginController {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@FXML
	private Pane content_area;
	@FXML
	private TextField fld_usrname;
	@FXML
	private PasswordField fld_psswd;
	@FXML
	private Label lb_reg;
	@FXML
	private Label lb_status;
	@FXML
	private Button btn_login;
	@FXML
	private StackPane stkpane_login;
	@FXML
	private Button btn_back;
	@FXML
	private Button btn_exit;
	@FXML
	private Button btn_exit1;
	@FXML
	private TextField fld_usrname1;
	@FXML
	private TextField fld_email;
	@FXML
	private TextField fld_psswd1;
	@FXML
	private TextField fld_pwcheck;
	@FXML
	private Button btn_reg;
	@FXML
	private Label lb_status1;

	public String authorize() throws SQLException {
		con = DBConnect.conDB();
		String status = "Success";
		String usrname = fld_usrname.getText();
		String psswd = fld_psswd.getText();
		if (usrname.isEmpty() || psswd.isEmpty()) {
			alert("Vui lòng nhập đủ thông tin");
			status = "Error";
		} else {
			String sql = "SELECT * FROM user where BINARY username = ? and BINARY password = ?";
			try {
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, usrname);
				preparedStatement.setString(2, psswd);
				resultSet = preparedStatement.executeQuery();
				if (!resultSet.next()) {
					alert("Sai thông tin đăng nhập");
					status = "Error";
				} else {
					alert("Đăng nhập thành công");
					sql = "SELECT * FROM user where username ='" + usrname + "'";
					int privileges, id;
					preparedStatement = con.prepareStatement(sql);
					resultSet = preparedStatement.executeQuery(sql);
					while (resultSet.next()) {
						id = resultSet.getInt("id_user");
						privileges = resultSet.getInt("id_role");
						UserSession.setInstance(id, privileges);
					}
				}
			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
				status = "Exception";
			} finally {
				con.close();
			}
		}
		return status;
	}

	private void alert(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.setContentText(text);
		alert.showAndWait();
	}

	private boolean validate_email(String email) {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Boolean b = email.matches(EMAIL_REGEX);
		return b;
	}

	@FXML
	public void evnt_switchreg(MouseEvent event) {
		ObservableList<Node> childs = stkpane_login.getChildren();
		if (childs.size() > 1) {
			Node loginNode = childs.get(childs.size() - 1);
			Node regNode = childs.get(childs.size() - 2);
			loginNode.setVisible(false);
			regNode.setVisible(true);
		}
	}

	@FXML
	public void evnt_back(MouseEvent event) {
		ObservableList<Node> childs = stkpane_login.getChildren();
		if (childs.size() > 1) {
			//
			Node loginNode = childs.get(childs.size() - 1);
			Node regNode = childs.get(childs.size() - 2);
			regNode.setVisible(false);
			loginNode.setVisible(true);
		}
	}

	public void init(final AppManager AppManager) {
		btn_login.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String sessionID;
				try {
					sessionID = authorize();
					if (sessionID == "Success") {
						AppManager.authenticated();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void evnt_reg(MouseEvent event) throws SQLException {
		con = DBConnect.conDB();
		String usrname = fld_usrname1.getText();
		String email = fld_email.getText();
		String psswd = fld_psswd1.getText();
		String pwcheck = fld_pwcheck.getText();
		if (usrname.isEmpty() || email.isEmpty() || psswd.isEmpty() || pwcheck.isEmpty()) {
			alert("Vui lòng nhập đủ thông tin");
		} else if (!(psswd.equals(pwcheck))) {
			alert("Mật khẩu không trùng khớp");
		} else if (!validate_email(email)) {
			alert("Địa chỉ email không hợp lệ");
		} else {
			String sql = "SELECT * FROM user where username = ?";
			try {
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, usrname);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					alert("Tài khoản đã tồn tại");
				} else {
					sql = "INSERT INTO user (id_role, username, email, password) values (3,?,?,?)";
					try {
						preparedStatement = con.prepareStatement(sql);
						preparedStatement.setString(1, usrname);
						preparedStatement.setString(2, email);
						preparedStatement.setString(3, psswd);
						preparedStatement.executeUpdate();
						alert("Đăng ký thành công");
					} catch (SQLException ex) {
						System.err.println(ex.getMessage());
					}
				}
			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
			} finally {
				con.close();
			}
		}
	}

	@FXML
	public void evnt_exit(MouseEvent event) {
		System.exit(0);
	}
}