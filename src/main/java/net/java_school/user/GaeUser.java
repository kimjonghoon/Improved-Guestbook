package net.java_school.user;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import net.java_school.spring.security.AppRole;

@SuppressWarnings("serial")
public class GaeUser implements Serializable {

	private String userId;
	private String email;
	private String nickname;
	private Set<AppRole> authorities;

	public GaeUser() {
	}

	public GaeUser(String userId, String email, String nickname) {
		this.userId = userId;
		this.email = email;
		this.nickname = nickname;
		this.authorities = EnumSet.of(AppRole.ROLE_USER);
	}

	public GaeUser(String userId, String email, String nickname, Set<AppRole> authorities) {
		this.userId = userId;
		this.email = email;
		this.nickname = nickname;
		this.authorities = authorities;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<AppRole> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<AppRole> authorities) {
		this.authorities = authorities;
	}

}
