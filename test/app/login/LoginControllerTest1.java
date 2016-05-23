package app.login;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginControllerTest1 {


	LoginController loginController;
	MailVersenderMock mailVersenderMock;
	
	@Before
	public void setupLoginController() {
		loginController = new LoginController();
		mailVersenderMock = new MailVersenderMock();
		loginController.mailVersender = mailVersenderMock;
	}
		
	@Test
	public void Wenn_der_Benutzer_sich_anmeldet_dann_soll_der_Login_Timestamp_gesetzt_werden_und_jeweils_eine_Mail_an_die_NSA_und_den_BND_versendet_werden() throws Exception {
		BenutzerTestDouble benutzer = BenutzerTestDoubleBuilder.erstelleBenutzer().build();
		loginController.login(benutzer, benutzer.passwort);
		Assert.assertTrue(mailVersenderMock.mailAnNSAVersendet);
		Assert.assertTrue(mailVersenderMock.mailAnBNDVersendet);
		Assert.assertTrue(benutzer.loginTimestamp > 0);
	}
		
	public static class BenutzerTestDoubleBuilder {

		public static BenutzerTestDoubleBuilder erstelleBenutzer() {
			return new BenutzerTestDoubleBuilder().mitName("admin").mitPasswort("admin");
		}
		
		BenutzerTestDouble benutzer = new BenutzerTestDouble();
		
		public BenutzerTestDoubleBuilder mitName(String name) {
			benutzer.name  = name;
			return this;
		}

		public BenutzerTestDoubleBuilder mitPasswort(String passwort) {
			benutzer.passwort = passwort;
			return this;
		}

		public BenutzerTestDouble build() {
			return benutzer;
		}
		
	}
	
	public static class BenutzerTestDouble extends Benutzer {
		public String name = "admin";
		public String passwort = "admin";	
	}
	
	public class MailVersenderMock extends MailVersender {
		public boolean mailAnBNDVersendet = false;
		public boolean mailAnNSAVersendet = false;
		
		@Override
		public void sendeMailAnNSA(Benutzer benutzer) {
			mailAnNSAVersendet = true;
		}
		
		@Override
		public void sendeMailAnBND(Benutzer benutzer) {
			mailAnBNDVersendet = true;
		}
	}
}
