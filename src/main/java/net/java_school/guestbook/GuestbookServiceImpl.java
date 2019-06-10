package net.java_school.guestbook;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Service
public class GuestbookServiceImpl implements GuestbookService {

	public List<Greeting> list(String guestbookName) {
		Key<Guestbook> theBook = Key.create(Guestbook.class, guestbookName);

		List<Greeting> greetings = ofy()
				.load()
				.type(Greeting.class) // We want only Greetings
				.ancestor(theBook)    // Anyone in this book
				.order("-date")       // Most recent first - date is indexed.
				.limit(5)             // Only show 5 of them.
				.list();

		return greetings;
	}

	public void sign(Greeting greeting) {
		ofy().save().entity(greeting).now();
	}

	@PreAuthorize("isAuthenticated() and (#greeting.author_id == principal.userId or hasRole('ROLE_ADMIN'))")
	public void del(Greeting greeting) {
		Key<Greeting> key = greeting.getKey();
		ofy().delete().key(key).now();
	}

}