package ntou.cs.rat.model;

public class Pharmacy {
	private String id;
	private String name;
	private String address;
	private double longitude;
	private double latitude;	
	private String phone;
	private String brandOfRapidAntigenTests;
	private int numberOfRapidAntigenTests;
	private String updatedTime;
	private String note;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}	
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBrandOfRapidAntigenTests() {
		return brandOfRapidAntigenTests;
	}

	public void setBrandOfRapidAntigenTests(String brandOfRapidAntigenTests) {
		this.brandOfRapidAntigenTests = brandOfRapidAntigenTests;
	}

	public int getNumberOfRapidAntigenTests() {
		return numberOfRapidAntigenTests;
	}

	public void setNumberOfRapidAntigenTests(int numberOfRapidAntigenTests) {
		this.numberOfRapidAntigenTests = numberOfRapidAntigenTests;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Pharmacy [id=" + id + ", name=" + name + ", address=" + address + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", phone=" + phone + ", brandOfRapidAntigenTests="
				+ brandOfRapidAntigenTests + ", numberOfRapidAntigenTests=" + numberOfRapidAntigenTests
				+ ", updatedTime=" + updatedTime + ", note=" + note + "]";
	}

}
