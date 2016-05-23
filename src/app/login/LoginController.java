package app.login;

import java.util.Date;

public class LoginController {

	MailVersender mailVersender;
	
	
	public void login(Benutzer benutzer, String passwort) throws Exception {
		
		if (passwort != "admin") {
			throw new Exception("Passwort falsch!");
		}
		
		long timestamp = new Date().getTime();
		benutzer.loginTimestamp = timestamp;
		mailVersender.sendeMailAnNSA(benutzer);
		mailVersender.sendeMailAnBND(benutzer);
	}

}
