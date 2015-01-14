package chirp.service.resources;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.User;

@XmlRootElement(name="user")
public class UserRepresentation {

	private String userName;
	private String realName;
	private List<Address> addresses=Arrays.asList(new Address(),new Address());
	
	@XmlElement(name="address")
	@XmlElementWrapper(name="addresses")
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public UserRepresentation() {
	}
	
	public UserRepresentation(User user) {
		this.userName = user.getUsername();
		this.realName = user.getRealname();
	}


	@XmlAttribute(name="user-name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@XmlElement(name="real-name")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
