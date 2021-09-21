package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import classd.Car;
import classd.Parking_Lot;
import classd.User;
import controller.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlM {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	public ObservableList<Parking_Lot> selectall() throws SQLException {
		con = DBConnect.conDB();
		ObservableList<Parking_Lot> data = FXCollections.observableArrayList();
		String sql = "select * from parking_lot";

		try {
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Parking_Lot obj = new Parking_Lot(resultSet.getInt("id_pl"), resultSet.getInt("id_user"),
						resultSet.getString("pl_name"), resultSet.getString("address"), resultSet.getString("district"),
						resultSet.getInt("slot_amount"), resultSet.getInt("slot_available"));
				data.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return data;
	}
	public ObservableList<Parking_Lot> selectbydistrict(String district) throws SQLException {
		con = DBConnect.conDB();
		ObservableList<Parking_Lot> data = FXCollections.observableArrayList();
		String sql = "select * from parking_lot where district = ?";

		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, district);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Parking_Lot obj = new Parking_Lot(resultSet.getInt("id_pl"), resultSet.getInt("id_user"),
						resultSet.getString("pl_name"), resultSet.getString("address"),
						resultSet.getString("district"), resultSet.getInt("slot_amount"),
						resultSet.getInt("slot_available"));
				data.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return data;
	}
	public Parking_Lot selectbyiduser(int id_user) throws SQLException {
		con = DBConnect.conDB();
		Parking_Lot pl = null;
		String sql = "select * from parking_lot where id_user = ?";
		try {

			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, id_user);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				pl = new Parking_Lot(resultSet.getInt("id_pl"), resultSet.getInt("id_user"),
						resultSet.getString("pl_name"), resultSet.getString("address"), resultSet.getString("district"),
						resultSet.getInt("slot_amount"), resultSet.getInt("slot_available"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return pl;
	}

	public Parking_Lot selectbyidpl(int id_pl) throws SQLException {
		con = DBConnect.conDB();
		Parking_Lot pl = null;
		String sql = "select * from parking_lot where id_pl = ?";
		try {

			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, id_pl);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				pl = new Parking_Lot(resultSet.getInt("id_pl"), resultSet.getInt("id_user"),
						resultSet.getString("pl_name"), resultSet.getString("address"), resultSet.getString("district"),
						resultSet.getInt("slot_amount"), resultSet.getInt("slot_available"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return pl;
	}

	public void updatebyiduser(Parking_Lot pl) throws SQLException {
		con = DBConnect.conDB();
		String sql = "update parking_lot set pl_name = ?,address = ?,district = ?,slot_amount = ? where id_user = ?";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, pl.getPl_name());
			preparedStatement.setString(2, pl.getAddress());
			preparedStatement.setString(3, pl.getDistrict());
			preparedStatement.setInt(4, pl.getSlot_amount());
			preparedStatement.setInt(5, pl.getId_pl());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			con.close();
		}
	}

	public void updatebyidpl(Parking_Lot pl) throws SQLException {
		con = DBConnect.conDB();
		String sql = "update parking_lot set id_user =?,pl_name = ?,address = ?,district = ?,slot_amount = ? where id_pl = ?";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, pl.getId_user());
			preparedStatement.setString(2, pl.getPl_name());
			preparedStatement.setString(3, pl.getAddress());
			preparedStatement.setString(4, pl.getDistrict());
			preparedStatement.setInt(5, pl.getSlot_amount());
			preparedStatement.setInt(6, pl.getId_pl());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			con.close();
		}
	}

	public void insert(Parking_Lot pl) throws SQLException {
		con = DBConnect.conDB();
		String sql = "INSERT INTO parking_lot(id_user, pl_name, address,district,slot_amount) values (?,?,?,?,?)";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, pl.getId_user());
			preparedStatement.setString(2, pl.getPl_name());
			preparedStatement.setString(3, pl.getAddress());
			preparedStatement.setString(4, pl.getDistrict());
			preparedStatement.setInt(5, pl.getSlot_amount());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			con.close();
		}
	}

	public void delete(Parking_Lot pl) throws SQLException {
		con = DBConnect.conDB();
		String sql = "delete from parking_lot where id_pl = ?";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, pl.getId_pl());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	public boolean checkassigned(User u) throws SQLException {
		con = DBConnect.conDB();
		String sql = "SELECT * FROM parking_lot where id_user = ?";
		try {

			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, u.getId_user());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return true;
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			con.close();
		}
		return false;
	}

	public boolean checkassigned2(User u, Parking_Lot pl) throws SQLException {
		con = DBConnect.conDB();
		String sql = "SELECT * FROM parking_lot where id_user = ? and id_pl != ?";
		try {

			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, u.getId_user());
			preparedStatement.setInt(2, pl.getId_pl());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				return true;
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			con.close();
		}
		return false;
	}
	public void check_available() throws SQLException {
		con = DBConnect.conDB();
		String sql1 = "select id_pl from parking_lot";
		String sql2 = "select slot_amount from parking_lot where id_pl=?";
		String sql3 = "select time_in, time_out from order1 where id_pl=?";
		String sql4 = "update parking_lot set slot_available = ? where id_pl=?";
		try {
			preparedStatement = con.prepareStatement(sql1);
			resultSet = preparedStatement.executeQuery();
			ArrayList<Integer> id = new ArrayList<Integer>();
			while (resultSet.next()) {
				id.add(resultSet.getInt("id_pl"));
			}
			id.forEach((id_pl) -> {
				int used = 0, amount = 0;
				try {
					preparedStatement = con.prepareStatement(sql2);
					preparedStatement.setInt(1, id_pl);
					resultSet = preparedStatement.executeQuery();
					while (resultSet.next()) {
						amount = resultSet.getInt("slot_amount");
					}
					preparedStatement = con.prepareStatement(sql3);
					preparedStatement.setInt(1, id_pl);
					resultSet = preparedStatement.executeQuery();

					while (resultSet.next()) {
						Timestamp in = resultSet.getTimestamp("order1.time_in");
						Timestamp out = resultSet.getTimestamp("order1.time_out");
						Timestamp now = Timestamp.valueOf(LocalDateTime.now());
						if (now.after(in) && now.before(out)) {
							used++;
						}
					}
					int avail = amount - used;
					if (avail < 0)
						avail = 0;
					preparedStatement = con.prepareStatement(sql4);
					preparedStatement.setInt(1, avail);
					preparedStatement.setInt(2, id_pl);
					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}
}
