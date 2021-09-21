package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import classd.Car;
import classd.Order;
import controller.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderM {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	public ObservableList<Order> selectforadmin(LocalDate date) {
		con = DBConnect.conDB();
		ObservableList<Order> data = FXCollections.observableArrayList();
		String sql = "select * from order1 where date(time_in) = ?";

		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setObject(1, date);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Order obj = new Order(resultSet.getInt("id_order"), resultSet.getInt("id_pl"),
						resultSet.getString("plate_number"), resultSet.getInt("id_payment"),
						resultSet.getTimestamp("time_in").toLocalDateTime(),
						resultSet.getTimestamp("time_out").toLocalDateTime(), resultSet.getInt("fee"));
				data.add(obj);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return data;
	}

	public ObservableList<Order> selectformanager(LocalDate date, int id_pl) {
		con = DBConnect.conDB();
		ObservableList<Order> data = FXCollections.observableArrayList();
		String sql = "select * from order1 where date(time_in) = ? and id_pl = ?";

		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setObject(1, date);
			preparedStatement.setInt(2, id_pl);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Order obj = new Order(resultSet.getInt("id_order"), resultSet.getInt("id_pl"),
						resultSet.getString("plate_number"), resultSet.getInt("id_payment"),
						resultSet.getTimestamp("time_in").toLocalDateTime(),
						resultSet.getTimestamp("time_out").toLocalDateTime(), resultSet.getInt("fee"));
				data.add(obj);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return data;
	}

	public ObservableList<Order> selectforuser(int id_user) throws SQLException {
		con = DBConnect.conDB();
		ObservableList<Order> data = FXCollections.observableArrayList();
		String sql = "select * from order1 inner join car on order1.plate_number = car.plate_number where car.id_user= ?";

		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, id_user);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Order obj = new Order(resultSet.getInt("id_order"), resultSet.getInt("id_pl"),
						resultSet.getString("plate_number"), resultSet.getInt("id_payment"),
						resultSet.getTimestamp("time_in").toLocalDateTime(),
						resultSet.getTimestamp("time_out").toLocalDateTime(), resultSet.getInt("fee"));
				data.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return data;
	}

	public boolean checkcar(Car c) throws SQLException {
		con = DBConnect.conDB();
		String sql = "select time_in, time_out from order1 where plate_number = ? ";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, c.getPlate_number());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Timestamp in = resultSet.getTimestamp("order1.time_in");
				Timestamp out = resultSet.getTimestamp("order1.time_out");
				Timestamp now = Timestamp.valueOf(LocalDateTime.now());
				if (now.after(in) && now.before(out)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return false;
	}
	public void addorder(Order o) throws SQLException{
		con = DBConnect.conDB();
		String sql = "INSERT INTO order1 (id_pl,plate_number,id_payment,time_in,time_out,fee) values (?,?,?,?,?,?)";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, o.getId_pl());
			preparedStatement.setString(2, o.getPlate_number());
			preparedStatement.setInt(3, o.getId_payment());
			preparedStatement.setTimestamp(4, Timestamp.valueOf(o.getTime_in()));
			preparedStatement.setTimestamp(5,Timestamp.valueOf(o.getTime_out()));
			preparedStatement.setInt(6, o.getFee());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}
}
