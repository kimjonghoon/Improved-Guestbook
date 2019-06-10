package net.java_school.spring.security;

import java.util.Collection;
import java.util.HashSet;

import net.java_school.user.GaeUser;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class GaeUserAuthentication implements Authentication {

	private final GaeUser principal;
	private final Object details;
	private boolean authenticated;

	public GaeUserAuthentication(GaeUser principal, Object details) {
		this.principal = principal;
		this.details = details;
		this.authenticated = true;
	}

	@Override
	public String getName() {
		return principal.getEmail();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new HashSet<>(principal.getAuthorities());
	}

	@Override
	public Object getCredentials() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
		this.authenticated = authenticated;
	}

}
