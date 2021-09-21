package classd;

public class Payment {
	private int id_payment;
	private String payment_type;

	public int getId_payment() {
		return id_payment;
	}

	public void setId_payment(int id_payment) {
		this.id_payment = id_payment;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public Payment(int id_payment, String payment_type) {
		super();
		this.id_payment = id_payment;
		this.payment_type = payment_type;
	}

}
