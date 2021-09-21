package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import classd.Order;
import classd.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserM {
	Connection con = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	public User selectbyid(int id) throws SQLException {
		User u = null;
		con = DBConnect.conDB();
		String sql = "select * from User where id_user = ? ";

		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id_user = resultSet.getInt("id_user");
				int id_role = resultSet.getInt("id_role");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String name = resultSet.getString("name");
				LocalDate birthday = resultSet.getObject("birthday", LocalDate.class);
				String gender = resultSet.getString("gender");
				String email = resultSet.getString("email");
				String phone_number = resultSet.getString("phone_number");
				String address = resultSet.getString("address");
				u = new User(id_user, id_role, username, password, name, birthday, gender, email, phone_number,
						address);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return u;
	}

	public void updateuserbyid(User u) throws SQLException {
		con = DBConnect.conDB();
		try {
			String sql = "update user set password = ?,name = ?, birthday = ?, gender = ?, email = ?, phone_number = ?,address = ? where id_user = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, u.getPassword());
			preparedStatement.setString(2, u.getName());
			if (u.getBirthday() == null)
				preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			else
				preparedStatement.setTimestamp(3, Timestamp.valueOf(u.getBirthday().atStartOfDay()));
			preparedStatement.setString(4, u.getGender());
			preparedStatement.setString(5, u.getEmail());
			preparedStatement.setString(6, u.getPhone_number());
			preparedStatement.setString(7, u.getAddress());
			preparedStatement.setInt(8, u.getId_user());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			con.close();
		}
	}

	public void deleteuserbyid(User u) throws SQLException {
		con = DBConnect.conDB();
		try {
			String sql = "delete from user where id_user = ?";
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, u.getId_user());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	public void insert(User u) throws SQLException {
		con = DBConnect.conDB();
		String sql = "INSERT INTO user (id_role, username, password,name,birthday,gender,email,phone_number,address) values (?,?,?,?,?,?,?,?,?)";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, u.getId_role());
			preparedStatement.setString(2, u.getUsername());
			preparedStatement.setString(3, u.getPassword());
			preparedStatement.setString(4, u.getName());
			preparedStatement.setTimestamp(5, Timestamp.valueOf(u.getBirthday().atStartOfDay()));
			preparedStatement.setString(6, u.getGender());
			preparedStatement.setString(7, u.getEmail());
			preparedStatement.setString(8, u.getPhone_number());
			preparedStatement.setString(9, u.getAddress());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			con.close();
		}
	}

	public ObservableList<User> selectmanager() throws SQLException {
		con = DBConnect.conDB();
		ObservableList<User> data = FXCollections.observableArrayList();
		String sql = "select * from user where id_role = 2";
		try {
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id_user");
				int id_role = resultSet.getInt("id_role");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String name = resultSet.getString("name");
				LocalDate birthday = resultSet.getObject("birthday", LocalDate.class);
				String gender = resultSet.getString("gender");
				String email = resultSet.getString("email");
				String phone_number = resultSet.getString("phone_number");
				String address = resultSet.getString("address");
				User obj = new User(id, id_role, username, password, name, birthday, gender, email, phone_number,
						address);
				data.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return data;
	}

	public ObservableList<User> searchmanager(String search) throws SQLException {
		con = DBConnect.conDB();
		ObservableList<User> data = FXCollections.observableArrayList();
		String sql = "";
		try {
			if (search.equals("")) {
				sql = "select * from user where id_role = 2";
				preparedStatement = con.prepareStatement(sql);
			} else {
				sql = "select * from user where id_role = 2 and username = ?";
				preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, search);
			}
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id_user");
				int id_role = resultSet.getInt("id_role");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String name = resultSet.getString("name");
				LocalDate birthday = resultSet.getObject("birthday", LocalDate.class);
				String gender = resultSet.getString("gender");
				String email = resultSet.getString("email");
				String phone_number = resultSet.getString("phone_number");
				String address = resultSet.getString("address");
				User obj = new User(id, id_role, username, password, name, birthday, gender, email, phone_number,
						address);
				data.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return data;
	}

	public boolean checkuser(User u) throws SQLException {
		con = DBConnect.conDB();
		String sql = "SELECT * FROM user where username = ?";
		try {
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, u.getUsername());
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

}
