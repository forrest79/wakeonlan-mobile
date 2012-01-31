WakeOnMobile, verze 1.0.0
http://forrest79.net, jakub.trmota@forrest79.net
10.5.2007

Tento zdrojovy kod je sirem pod licenci GNU/GPL! Viz soubor gpl.txt

SEZNAM SOUBORU
--------------
adresar                       soubor                   verze   zmenen       popis
-------                       ------                   -----   ------       -----
doc                           readme.txt               1.0.0   10.5.2007    tento soubor, instalace, informace
doc                           gpl.txt                                       text licence GNU/GPL
doc                           version.txt              1.0.0   10.5.2007    popis zmen u jednotlivych verzi
j2me/Slovnik_WEB2CZ/res       *                                10.5.2007    adresar obsahuje zdroje pro midlet
j2me/Slovnik_WEB2CZ/src       TextLangCZ.java          1.0.0   10.5.2007    ceske texty
j2me/Slovnik_WEB2CZ/src       TextLangEN.java          1.0.0   10.5.2007    anglicke texty
j2me/Slovnik_WEB2CZ/src       CheckVersion.java        1.0.0   10.5.2007    trida, slouzici k pripojeni k internetu a
                                                                             zjisteni aktualni verze
j2me/Slovnik_WEB2CZ/src       Loading.java             1.0.0   10.5.2007    trida, implementujici uvodni obrazovku
j2me/Slovnik_WEB2CZ/src       Result.java              1.0.0   10.5.2007    trida, zprostredkovavajici zobrazeni vysledku
                                                                             odeslani magic paketu
j2me/Slovnik_WEB2CZ/src       WakeOn.java              1.0.0   10.5.2007    trida, realizujici samotne odeslani
                                                                             magic paketu
j2me/Slovnik_WEB2CZ/src       Settings.java            1.0.0   10.5.2007    trida k ulozeni a nacteni nastaveni
j2me/Slovnik_WEB2CZ/src       WakeOnMobile.java        1.0.0   10.5.2007    hlavni trida, realizujici beh cele aplikace
php                           version.php              1.0.0   10.5.2007    rozhrani pro odeslani aktualni verze

INSTALACE
---------
1) Stahnete a nainstalujte si Sun Java Wireless Toolkit 2.2 (http://java.sun.com/javame/downloads) a spustte program
   KToolbar. Vyberte New project... a zadejte Project name: WakeOnMobile a MIDlet Class Name: WakeOnMobile.
2) Zkopirujte adresare j2me/WakeOnMobile/res a j2me/WakeOnMobile/src do C:\Programovani\WTK22\apps\WakeOnMobile,
   popripade jineho, kam jste nainstalovali Sun Java Wireless Toolkit 2.2.
3) Tot vse, nyni muzete program prelozit tlacitkem Build a spustit pomoci Run.

INFORMACE
---------
Program WakeOnMobile slouzi k odesilani Magic Packetu, slouziciho pro probouzeni ci zapinani pocitacu po siti sluzbou
WakeOnLan. Zde si muzete nadefinovat vice pocitacu a celou aplikaci chrenit heslem.

Bohuzel na mych pocitacich neni sluzba WakeOnLan dostupna, proto jsem mohl otestovat funkcnost pouze off-line pomoci zachytavani
paketu. Vse se zda funkcni, ale chtel bych poprosit, zda by nekdo, kdo program otestuje, mi mohl napsat, zda vse funguje jak, ma :)

Tesim se na vase reakce na emailu web2cz@forrest79.net.

Jakub Trmota