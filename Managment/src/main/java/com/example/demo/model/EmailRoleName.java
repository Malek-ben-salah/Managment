package com.example.demo.model;

public class EmailRoleName {

	private String email;
	private String roleName;

	public EmailRoleName() {
		super();
	}

	public EmailRoleName(String email, String roleName) {
		super();
		this.email = email;
		this.roleName = roleName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "EmailRoleName [email=" + email + ", roleName=" + roleName + "]";
	}

}
