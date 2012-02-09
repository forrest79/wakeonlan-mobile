package wakeonlan.locale;

/**
 * English locale.
 *
 * @author Jakub Trmota | Forrest79
 */
public final class LocaleEn extends BaseLocale {
	/**
	 * Initialization english locale.
	 */
	public LocaleEn() {
		super();

		locales.put("Ano", "Yes");
		locales.put("Ne", "No");
		locales.put("Ok", "Ok");
		locales.put("Zpět", "Back");
		locales.put("Konec", "Exit");
		locales.put("Chyba", "Error");
		locales.put("Zkontrolovat", "Check");
		locales.put("Vymazat počítač", "Remove computer");
		locales.put("Opravdu chcete vymazat tento počítač", "Do you really want to remove this computer");
		locales.put("Heslo", "Password");
		locales.put("Zkontrolovat heslo", "Check password");
		locales.put("Název počítače", "Computer name");
		locales.put("IP adresa", "IP address");
		locales.put("MAC adresa", "MAC address");
		locales.put("MAC: text", "enter MAC address with delimiters \":\" or \"-\".");
		locales.put("Port", "Port");
		locales.put("Probudit počítač", "Wake up computer");
		locales.put("Probuď", "Wake up");
		locales.put("Rychle probudit", "Quick wake up");
		locales.put("Přidat počítač", "Add computer");
		locales.put("Upravit počítač", "Modify computer");
		locales.put("Uložit počítač", "Save computer");
		locales.put("Vymazat počítač", "Remove computer");
		locales.put("Uložit", "Save");
		locales.put("Nastavit jazyk", "Set lang");
		locales.put("Jazyk", "Lang");
		locales.put("česky", "česky");
		locales.put("anglicky", "english");
		locales.put("Nastavit heslo", "Set password");
		locales.put("Heslo znovu", "Password again");
		locales.put("O WakeOnLan", "About WakeOnLan");
		locales.put("Probouzím počítač", "Waking up computer");
		locales.put("Požadavek odeslán", "Request was sent");

		locales.put("Nechce prázdné pro deaktivování ochrany heslem.", "Leave blank for deactivate password protection.");
		locales.put("Verze WakeOnLan", "WakeOnLan version");
		locales.put("O WakeOnLan: text", "Choose computer or Quick wake up. Computers can be save to list for wake up. If you want to have password protection, go to page Set password. If you leave password blank, password protection will be deactivated. Enter MAC address with \":\" or \"-\" delimiter.");
		locales.put("Špatně zadané heslo.", "Bad password.");
		locales.put("Musíte zadat název počítače.", "You must enter computer name.");
		locales.put("Musíte zadat IP adresu počítače.", "You must enter IP address.");
		locales.put("Musíte zadat MAC adresu počítače.", "You must enter MAC address.");
		locales.put("MAC adresu musí mít jako oddělovač \":\" nebo \"-\".", "MAC address must have \":\" or \"-\" as delimiter.");
		locales.put("Port počítače musí být větší než 0.", "Computer port must be greater than 0.");
		locales.put("Nastala chyba při přidávání počítače.", "An error occured while adding computer.");
		locales.put("Nastala chyba při upravování počítače.", "An error occured while modyfing computer.");
		locales.put("Nastala chyba při vymazávání počítače.", "An error occured while removing computer.");
		locales.put("Není vybrán počítač pro úpravu.", "No computer is choosed to modify.");
		locales.put("Není vybrán počítač pro vymazání.", "No computer is choosed fo remove.");
		locales.put("Hesla se musí shodovat.", "Passwords must be same.");
		locales.put("Špatná akce.", "Bad action.");
		locales.put("špatná MAC adresa", "Bad MAC address");
		locales.put("příkaz neodeslán", "requset not sent");
	}
}
