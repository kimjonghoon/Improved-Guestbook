package net.java_school.spring.security;

import org.springframework.security.core.GrantedAuthority;

public enum AppRole implements GrantedAuthority {
	ROLE_ADMIN(0),
	ROLE_USER(1);

	private int bit;

	AppRole(int bit) {
		this.bit = bit;
	}

	@Override
	public String getAuthority() {
		return toString();
	}

	public int getBit() {
		return bit;
	}

	public void setBit(int bit) {
		this.bit = bit;
	}

}
