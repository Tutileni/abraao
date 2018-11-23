package com.uno.JEU;


/**
 * classe repr�sentant le talon du jeu
 * @author Stoufa
 *
 */
public class Talon extends Pile {

	/**
	 * constructeur
	 * @param pioche : la pioche du jeu dont on doit prendre une carte pour quelle soit la 1ere carte du talon
	 */
	public Talon(Pioche pioche) {
		ajouterPremiereCarte(pioche);
	}
	
	/**
	 * permet d'ajouter la premi�re carte ( au d�but du jeu )
	 * @param pioche : la pioche d'o� on veut tirer la carte
	 */
	private void ajouterPremiereCarte(Pioche pioche) {
		empiler(pioche.premiereCarteTalon());
		/*
		while (true) {
			// Retirer une carte
			Carte carte = pioche.depiler();
			if (carte instanceof CarteSpecial) {	// C'est une carte sp�ciale
				// Il faut dans ce cas la rajouter al�atoirement dans la pioche
				pioche.retournerCarte(carte);
				//System.out.println("Oops carte sp�cial , ...");
				//System.out.println(carte);
			} else {
				this.empiler(carte);
				return;
			}
		}
		*/
	}
	
	/**
	 * retourne une cha�ne d�crivant le talon
	 */
	public String toString() {
		String str = "";
		if (cartes.isEmpty()) {
			str = "[VIDE]";
		}
		for(int i = 0; i < cartes.size(); ++i) {
			Carte carte = cartes.get(i);
			str = str + i + ") " + carte.toString();
			if (i != cartes.size() - 1) {	// si ce n'est pas la derni�re it�ration
				str = str + "\n";	// ajouter un retour chariot
			}
		}
		return str;
	}
	
	/**
	 * permet d'afficher le talon
	 */
	public void afficher() {
		System.out.println(this);
	}
	
	/**
	 * permet d'afficher la carte au sommet du talon
	 */
	public void afficherSommet() {
		System.out.println("Sommet talon : " + sommet().toString());
	}
	
}
