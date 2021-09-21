package classd;

public class Car {
	private String plate_number;
	private int id_user;
	private String car_brand;
	private String car_name;
	private String car_color;
	private String car_type;

	public String getPlate_number() {
		return plate_number;
	}

	public void setPlate_number(String plate_number) {
		this.plate_number = plate_number;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public String getCar_brand() {
		return car_brand;
	}

	public void setCar_brand(String car_brand) {
		this.car_brand = car_brand;
	}

	public String getCar_name() {
		return car_name;
	}

	public void setCar_name(String car_name) {
		this.car_name = car_name;
	}

	public String getCar_color() {
		return car_color;
	}

	public void setCar_color(String car_color) {
		this.car_color = car_color;
	}

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	public Car(String plate_number, int id_user, String car_brand, String car_name, String car_color, String car_type) {
		super();
		this.plate_number = plate_number;
		this.id_user = id_user;
		this.car_brand = car_brand;
		this.car_name = car_name;
		this.car_color = car_color;
		this.car_type = car_type;
	}
}
