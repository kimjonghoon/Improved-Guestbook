package net.java_school.guestbook;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface GuestbookService {

	public List<Greeting> list(String guestbookName);

	public void sign(Greeting greeting);

	public void del(Greeting greeting);
}
