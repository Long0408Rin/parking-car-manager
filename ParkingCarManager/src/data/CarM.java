package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import classd.Car;
import controller.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CarM {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	public ObservableList<Car> selectbyiduser(int id_user) throws SQLException {
		con = DBConnect.conDB();
		ObservableList<Car> data = FXCollections.observableArrayList();
		String sql = "select * from car where id_user = ?";

		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, id_user);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Car obj = new Car(resultSet.getString("plate_number"), UserSession.getInstance().getId(),
						resultSet.getString("car_brand"), resultSet.getString("car_name"),
						resultSet.getString("car_color"), resultSet.getString("car_type"));
				data.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return data;
	}

	public boolean checkduplicate(String plate_number) throws SQLException {
		con = DBConnect.conDB();
		String sql = "SELECT * FROM car where plate_number = ?";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, plate_number);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			con.close();
		}
		return false;
	}

	public void insertcar(Car c) throws SQLException {
		con = DBConnect.conDB();
		String sql = "INSERT INTO car (plate_number,id_user,car_brand,car_name,car_color,car_type) values (?,?,?,?,?,?)";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, c.getPlate_number());
			preparedStatement.setInt(2, UserSession.getInstance().getId());
			preparedStatement.setString(3, c.getCar_brand());
			preparedStatement.setString(4, c.getCar_name());
			preparedStatement.setString(5, c.getCar_color());
			preparedStatement.setString(6, c.getCar_type());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			con.close();
		}
	}
	public void updatecar(Car c) throws SQLException{
		con = DBConnect.conDB();
		String sql = "update car set car_brand = ?,car_name = ?,car_color = ?,car_type = ? where plate_number = ?";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, c.getCar_brand());
			preparedStatement.setString(2, c.getCar_name());
			preparedStatement.setString(3, c.getCar_color());
			preparedStatement.setString(4, c.getCar_type());
			preparedStatement.setString(5, c.getPlate_number());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			con.close();
		}
	}
	public void delcar(Car c) throws SQLException{
		con = DBConnect.conDB();
		try {
			String sql = "delete from car where plate_number = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, c.getPlate_number());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}
}
