package classd;

import java.time.LocalDate;

public class User {
	private int id_user;
	private int id_role;
	private String username;
	private String password;
	private String name;
	private LocalDate birthday;
	private String gender;
	private String email;
	private String phone_number;
	private String address;

	public User(int id_user, int id_role, String username, String password, String name, LocalDate birthday,
			String gender, String email, String phone_number, String address) {
		super();
		this.id_user = id_user;
		this.id_role = id_role;
		this.username = username;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.email = email;
		this.phone_number = phone_number;
		this.address = address;
	}

	public User(int id_role, String username, String password, String name, LocalDate birthday, String gender,
			String email, String phone_number, String address) {
		super();
		this.id_role = id_role;
		this.username = username;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.email = email;
		this.phone_number = phone_number;
		this.address = address;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public int getId_role() {
		return id_role;
	}

	public void setId_role(int id_role) {
		this.id_role = id_role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
