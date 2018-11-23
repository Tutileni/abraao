package com.uno.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * Classe responsable de l'extraction des configurations du fichier .ini
 * 
 * @author Stoufa
 *
 */
public class Config {

	/**
	 * nom du fichier de configuration ( fichier .ini )
	 */
	private static final String configFilePath = "config.ini";
	/**
	 * contient la configuration par d�faut du jeu
	 */
	private static final Properties defaults = new Properties();
	/**
	 * contient la configuration du jeu la configuration est lue une seule fois
	 * du fichier et tout au long du jeu � partir de cette table de hachage
	 */
	private static final HashMap<String, String> values = new HashMap<>();
	/**
	 * drapeau ( flag ) utilis� pour s'assurer que la m�thode init() � �t�
	 * invoqu� une seule fois
	 */
	private static boolean isInitialized = false;
	/**
	 * Objet utilis� pour lire ( parser ) le fichier de configuration
	 */
	static Properties p = new Properties();

	/**
	 * m�thode d'initialisation, initialise les valeurs par d�faut
	 */
	private static void init() {
		if (isInitialized) { // si la m�thode � �t� d�ja invoqu�e ...
			return; // ... quitter
		}
		isInitialized = true; // sinon, on marque que c'est invoqu�e pour ne pas
								// l'invoquer une 2�me fois
		// les configurations par d�fauts ( utiles quand on ne trouve pas les
		// configurations dans le fichier )
		defaults.put("nbJoueurs", "2");
		// defaults.put("width", "800");
		defaults.put("title", "UNO");
		defaults.put("imgPath", "res/gfx/");
		defaults.put("soundPath", "res/sons/");
		defaults.put("offsetPiocheTalon", "50");

		defaults.put("jouerMusique", "true");
		defaults.put("jouerSons", "true");

		// sons
		defaults.put("clickSound", "cardClicked.wav");
		defaults.put("invalidClickSound", "invalidCardClicked.wav");
		defaults.put("unoSound", "uno.wav");
		defaults.put("skipSound", "passe.wav");
		defaults.put("reverseSound", "reverse.wav");
		defaults.put("plus2Sound", "+2.wav");
		defaults.put("wildSound", "wild.wav");
		defaults.put("hardLuckSound", "hardLuck.wav");
		defaults.put("noPlayableCardsSound", "noPlayableCards.wav");
		defaults.put("plus4Sound", "+4.wav");
		defaults.put("winSound", "win.wav");
		// music
		defaults.put("bgMusic", "bgMusic.wav");
	}

	/**
	 * lit ( parse ) le fichier de configuration
	 */
	private static void readConfigFile() {
		try {
			init(); // initialisation
			InputStream is = new FileInputStream(configFilePath);
			p.load(is);
			is.close();
		} catch (IOException e) {
			Debug.err("Fichier introuvable : " + configFilePath);
		}
	}

	/**
	 * utilis�e pour avoir une valeur du fichier de configuration
	 * 
	 * @param key
	 *            nom du param�tre demand�
	 * @return la valeur du param�tre demand�
	 */
	public static String get(String key) {
		return values.get(key);
	}

	/**
	 * permet de charger les configurations pour qu'elles soient utilis�es dans
	 * le jeu
	 */
	public static void load() {
		try {
			readConfigFile(); // lecture du fichier
			// charger les configurations du fichier
			Enumeration<?> e = p.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				values.put(key, p.getProperty(key));
			}
			// s'il y on a des configurations manquantes, on met les
			// configurations par d�faut
			e = defaults.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				if (!values.containsKey(key)) { // si le nom du param�tre n'est
												// pas trouv� dans la liste des
												// configurations ...
					// ... on l'ajoute
					values.put(key, defaults.getProperty(key));
				}
			}
			// sauvegarde des configurations dans le fichier
			OutputStream os = new FileOutputStream(configFilePath);
			p.store(os, null);
		} catch (IOException e) {
			Debug.err("Fichier introuvable: " + configFilePath);
		}
	}
}
