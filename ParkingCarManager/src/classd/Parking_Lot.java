package classd;

public class Parking_Lot {
	private int id_pl;
	private int id_user;
	private String pl_name;
	private String address;
	private String district;
	private int slot_amount;
	private int slot_available;

	public int getId_pl() {
		return id_pl;
	}

	public void setId_pl(int id_pl) {
		this.id_pl = id_pl;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public String getPl_name() {
		return pl_name;
	}

	public void setPl_name(String pl_name) {
		this.pl_name = pl_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public int getSlot_amount() {
		return slot_amount;
	}

	public void setSlot_amount(int slot_amount) {
		this.slot_amount = slot_amount;
	}

	public int getSlot_available() {
		return slot_available;
	}

	public void setSlot_available(int slot_available) {
		this.slot_available = slot_available;
	}

	public Parking_Lot(int id_pl, int id_user, String pl_name, String address, String district, int slot_amount,
			int slot_available) {
		super();
		this.id_pl = id_pl;
		this.id_user = id_user;
		this.pl_name = pl_name;
		this.address = address;
		this.district = district;
		this.slot_amount = slot_amount;
		this.slot_available = slot_available;
	}

	public Parking_Lot(int id_user, String pl_name, String address, String district, int slot_amount) {
		super();
		this.id_user = id_user;
		this.pl_name = pl_name;
		this.address = address;
		this.district = district;
		this.slot_amount = slot_amount;
	}
}
