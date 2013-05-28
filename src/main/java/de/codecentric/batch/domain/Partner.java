package de.codecentric.batch.domain;

/**
 * @author Tobias Flohre
 */
public class Partner {
	
	private String name;
	private String email;
	private String gender;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Partner [name=" + name + ", email=" + email + ", gender="+ gender + "]";
	}
	
}
