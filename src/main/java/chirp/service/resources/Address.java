package chirp.service.resources;

import javax.xml.bind.annotation.XmlElement;

public class Address {
	private String street = "1501 Brookfield Cove";
	private String city = "San Francisco";
	private String state = "CA";

	@XmlElement(name="streeeeeeeeeeet")
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
