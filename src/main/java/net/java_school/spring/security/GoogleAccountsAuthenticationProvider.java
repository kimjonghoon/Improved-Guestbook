package net.java_school.spring.security;

import net.java_school.user.GaeUser;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class GoogleAccountsAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User googleUser = (User) authentication.getPrincipal();

		GaeUser gaeUser = new GaeUser(googleUser.getUserId(), googleUser.getEmail(), googleUser.getNickname());

		if (UserServiceFactory.getUserService().isUserAdmin()) {
			gaeUser.getAuthorities().add(AppRole.ROLE_ADMIN);
		}

		return new GaeUserAuthentication(gaeUser, authentication.getDetails());
	}

	@Override
	public final boolean supports(Class<?> authentication) {
		return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
