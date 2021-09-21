package classd;

import java.time.LocalDateTime;

public class Order {
	private int id_order;
	private int id_pl;
	private String plate_number;
	private int id_payment;
	private LocalDateTime time_in;
	private LocalDateTime time_out;
	private int fee;

	public int getId_order() {
		return id_order;
	}

	public void setId_order(int id_order) {
		this.id_order = id_order;
	}

	public int getId_pl() {
		return id_pl;
	}

	public void setId_pl(int id_pl) {
		this.id_pl = id_pl;
	}

	public String getPlate_number() {
		return plate_number;
	}

	public void setPlate_number(String plate_number) {
		this.plate_number = plate_number;
	}

	public int getId_payment() {
		return id_payment;
	}

	public void setId_payment(int id_payment) {
		this.id_payment = id_payment;
	}

	public LocalDateTime getTime_in() {
		return time_in;
	}

	public void setTime_in(LocalDateTime time_in) {
		this.time_in = time_in;
	}

	public LocalDateTime getTime_out() {
		return time_out;
	}

	public void setTime_out(LocalDateTime time_out) {
		this.time_out = time_out;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public Order(int id_order, int id_pl, String plate_number, int id_payment, LocalDateTime time_in,
			LocalDateTime time_out, int fee) {
		super();
		this.id_order = id_order;
		this.id_pl = id_pl;
		this.plate_number = plate_number;
		this.id_payment = id_payment;
		this.time_in = time_in;
		this.time_out = time_out;
		this.fee = fee;
	}
	public Order(int id_pl, String plate_number, int id_payment, LocalDateTime time_in,
			LocalDateTime time_out, int fee) {
		super();
		this.id_pl = id_pl;
		this.plate_number = plate_number;
		this.id_payment = id_payment;
		this.time_in = time_in;
		this.time_out = time_out;
		this.fee = fee;
	}
}
