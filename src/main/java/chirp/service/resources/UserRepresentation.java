package chirp.service.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import chirp.model.Chirp;
import chirp.model.User;

@XmlRootElement(name="user")
public class UserRepresentation {

	private String userName;
	private String realName;
	private List<ChirpRepresentation> chirps;
	
	public UserRepresentation() {
	}
	
	public UserRepresentation(User user, boolean summary) {
		this.userName = user.getUsername();
		this.realName = user.getRealname();
		if (summary)
			return;
		this.chirps = new ArrayList<>();
		for (Chirp chirp : user.getChirps()) {
			ChirpRepresentation rep = new ChirpRepresentation(chirp);
			this.chirps.add(rep);
		}
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

	@XmlElement(name="chirp")
	@XmlElementWrapper(name="chirps")
	public List<ChirpRepresentation> getChirps() {
		return chirps;
	}

	public void setChirps(List<ChirpRepresentation> chirps) {
		this.chirps = chirps;
	}

}
