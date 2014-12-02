package listeners;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCounterListener implements HttpSessionListener {
	 
	  private static int totalActiveSessions = 0;
	 
	  public static int getTotalActiveSessions() {
		return totalActiveSessions;
	}

	@Override
	  public void sessionCreated(HttpSessionEvent arg0) {
		totalActiveSessions++;
		System.out.println("Session Creada.");
		
	  }
	 
	  @Override
	  public void sessionDestroyed(HttpSessionEvent arg0) {
		totalActiveSessions--;
		System.out.println("Session Destruida");
	  }	
}

