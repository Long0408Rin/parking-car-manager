package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

public class AppManager {
	private Scene scene;

	public AppManager(Scene scene) {
		this.scene = scene;
	}

	public void authenticated() {
		showMainMenu();
	}

	public void logout() {
		UserSession.cleanUserSession();
		showLoginScreen();
	}

	public void showLoginScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../form/Login.fxml"));
			scene.setRoot((Parent) loader.load());
			LoginController controller = loader.<LoginController>getController();
			controller.init(this);
		} catch (IOException ex) {
			Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void showMainMenu() {
		try {
			FXMLLoader loader;
			switch (UserSession.getInstance().getPrivileges()) {
			case 1:
				loader = new FXMLLoader(getClass().getResource("../form/admin/AdminMenu.fxml"));
				scene.setRoot((Parent) loader.load());
				AdminMenuController controller1 = loader.<AdminMenuController>getController();
				controller1.init(this);
				break;
			case 2:
				loader = new FXMLLoader(getClass().getResource("../form/manager/ManagerMenu.fxml"));
				scene.setRoot((Parent) loader.load());
				ManagerMenuController controller2 = loader.<ManagerMenuController>getController();
				controller2.init(this);
				break;
			case 3:
				loader = new FXMLLoader(getClass().getResource("../form/user/UserMenu.fxml"));
				scene.setRoot((Parent) loader.load());
				UserMenuController controller3 = loader.<UserMenuController>getController();
				controller3.init(this);
				break;
			}
		} catch (IOException ex) {
			Logger.getLogger(AppManager.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
