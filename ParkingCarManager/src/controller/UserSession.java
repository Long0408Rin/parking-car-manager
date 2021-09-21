package controller;

public final class UserSession {

	private static UserSession instance;

	private int id;
	private int privileges;

	private UserSession(int id, int privileges) {
		this.id = id;
		this.privileges = privileges;
	}

	public static void setInstance(int id, int privileges) {
		if (instance == null) {
			instance = new UserSession(id, privileges);
		}
	}

	public static UserSession getInstance() {
		return instance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrivileges() {
		return privileges;
	}

	public void setPrivileges(int privileges) {
		this.privileges = privileges;
	}

	public static void cleanUserSession() {
		instance = null;
	}
}
