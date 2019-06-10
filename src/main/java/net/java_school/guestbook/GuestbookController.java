package net.java_school.guestbook;

import java.io.IOException;
import java.util.List;

import net.java_school.spring.security.GaeUserAuthentication;
import net.java_school.user.GaeUser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;

import static com.googlecode.objectify.ObjectifyService.ofy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class GuestbookController {

	private GuestbookService guestbookService;

	public void setGuestbookService(GuestbookService guestbookService) {
		this.guestbookService = guestbookService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "index";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminHome() {
		return "admin/index";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String error403() {
		return "403";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		String redirectUrl = UserServiceFactory.getUserService().createLogoutURL(url);
		response.sendRedirect(redirectUrl);
	}

	@RequestMapping(value = "/guestbook", method = RequestMethod.GET)
	public String guestbook(String guestbookName, Model model) {
		if (guestbookName == null || guestbookName.equals("")) {
			guestbookName = "default";
		}

		List<Greeting> greetings = guestbookService.list(guestbookName);

		model.addAttribute("guestbookName", guestbookName);
		model.addAttribute("greetings", greetings);

		return "guestbook/guestbook";
	}

	@RequestMapping(value = "/guestbook/sign", method = RequestMethod.POST)
	public String sign(String guestbookName, String content, GaeUserAuthentication gaeUserAuthentication) {
		Greeting greeting = null;

		if (gaeUserAuthentication != null) {
			GaeUser gaeUser = (GaeUser) gaeUserAuthentication.getPrincipal();
			greeting = new Greeting(guestbookName, content, gaeUser.getUserId(), gaeUser.getEmail());
		} else {
			greeting = new Greeting(guestbookName, content);
		}
		guestbookService.sign(greeting);
		return "redirect:/guestbook/?guestbookName=" + guestbookName;
	}

	@RequestMapping(value = "/guestbook/del", method = RequestMethod.POST)
	public String del(String guestbookName, String keyString) {
		Key<Greeting> key = Key.create(keyString);
		Greeting greeting = ofy().load().key(key).now();
		guestbookService.del(greeting);
		return "redirect:/guestbook/?guestbookName=" + guestbookName;
	}

}
