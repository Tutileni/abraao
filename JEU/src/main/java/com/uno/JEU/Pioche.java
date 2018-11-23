package com.uno.JEU;


/**
 * classe repr�sentant la pioche du jeu
 * @author Stoufa
 *
 */
public class Pioche extends Pile {
	// TODO : on doit traiter le cas o� la pioche devient vide et on doit prendre une carte
	// dans ce cas, on a besoin de m�langer le talon ( sauf le sommet ) et le d�poser dans la pioche

	/**
	 * constructeur : permet de construire la pioche et d'y ins�rer toutes les cartes n�c�ssaires
	 */
	public Pioche() {
		for(Couleur couleur : Couleur.values()) {
			if (couleur == Couleur.NOIR) {
				for(int i = 0; i < 4; i++) {	// la pioche 4 cartes joker et 4 cartes +4
					ajouter(new CarteSpecial(Couleur.NOIR, Symbole.JOKER));
					ajouter(new CarteSpecial(Couleur.NOIR, Symbole.PLUS4));
				}
				continue;	// toutes les cartes noirs sont ajout�es, on passe � la couleur suivante
			}
			// 1 Carte 0 pour chaque couleur
			ajouter(new CarteChiffre(couleur, 0));
			// 2 Cartes 1..9 pour chaque couleur
			for(int i = 1; i <= 9; i++) {
				ajouter(new CarteChiffre(couleur, i));
				ajouter(new CarteChiffre(couleur, i));
			}
			// 2 Cartes passer, inverser, +2 pour chaque couleur
			for (int i = 0; i < 2; i++) {
				ajouter(new CarteSpecial(couleur, Symbole.PASSER));
				ajouter(new CarteSpecial(couleur, Symbole.INVERSER));
				ajouter(new CarteSpecial(couleur, Symbole.PLUS2));
			}
		}
	}

	/**
	 * Cette m�thode est utilis�e pour retourner une carte al�atoirement dans la pioche
	 * dans le cas o� la premi�re carte est une carte sp�ciale ( au d�but du jeu )
	 * @param carte
	 */
	private void retournerCarte(Carte carte) {
		int i = rand.nextInt(cartes.size());	// Entier al�atoire entre 0 et cartes.size() - 1
		cartes.add(i, carte);
	}
	
	/**
	 * cette m�thode est appel�e par le talon pour qu'elle lui retourne sa premi�re carte
	 * la carte ne doit pas �tre sp�ciale
	 * @return
	 */
	public Carte premiereCarteTalon() {
		Carte carte;
		while (true) {
			carte = depiler();	// Retirer une carte
			if (carte instanceof CarteSpecial) {	// C'est une carte sp�ciale
				// Il faut dans ce cas la rajouter al�atoirement dans la pioche
				retournerCarte(carte);
				//System.out.println("Oops carte sp�cial , ...");
				//System.out.println(carte);
			} else {
				return carte;
			}
		}
	}


}
