package com.uno.JEU;

import com.uno.JEU.io.Clavier;

/**
 * classe repr�sentant le jeu, elle est responsable du d�roulement du jeu
 * suivie du sens du jeu, de l'activation des effets sp�ciales, ...
 * @author Stoufa
 *
 */
public class Jeu {
	/**
	 * la pioche
	 */
	private Pioche pioche;
	/**
	 * le talon
	 */
	private Talon talon;
	/**
	 * le nombre de joueurs ( doit �tre entre 2 et 4 )
	 */
	private int nbJoueurs;
	/**
	 * la liste des joueurs
	 */
	private Joueur[] joueurs;
	/**
	 * le sens du jeu, peut avoir deux valeurs possibles : -1 de droite � gauche, 1 de gauche � droite
	 * [0] + (1) -> [1] ...
	 * [1] + (-1) -> [0] ...
	 */
	private int sens = -1;	// par d�faut � gauche
	/**
	 * l'indice du joueur courant, initialement le premier
	 */
	private int indiceJoueurCourant = 0;
	/**
	 * l'objet Joueur � l'indice indiceJoueurCourant du tableau joueurs
	 */
	private Joueur joueurCourant;
	
	/**
	 * constructeur
	 */
	public Jeu() {
		pioche = new Pioche();
		pioche.melanger();
		talon = new Talon(pioche);
		System.out.println("nbJoueurs ?");
		nbJoueurs = Clavier.lireEntier(2, 4);
		joueurs = new Joueur[nbJoueurs];
		for (int i = 0; i < nbJoueurs; i++) {
			String pseudoJoueur;
			boolean pseudoUnique;
			do {
				System.out.println("Nom du joueur ?");
				pseudoJoueur = Clavier.lireChaine();
				pseudoUnique = pseudoValide(pseudoJoueur, i);
				if (!pseudoUnique) {
					System.out.println("Le pseudo <" + pseudoJoueur + "> est d�ja utilis� !");
				}
			} while (!pseudoUnique);	// le pseudo du joueur doit �tre unique !
			joueurs[i] = new Joueur(pseudoJoueur, pioche, talon);
			System.out.println("Le joueur <" + joueurs[i].getPseudo() + "> est conn�ct�");
		}
		for (int i = 0; i < joueurs.length; i++) {	// Distirbution des cartes
			for (int j = 0; j < 7; j++) {	// Chaque joueur va prendre 7 cartes
				joueurs[i].prendreCarte();
			}
		}
		//talon.ajouterPremiereCarte(pioche);	// [DONE] d�placer cette m�thode au constructeur du talon
	}
	
	/**
	 * permet de d�marrer le jeu
	 */
	public void lancer() {
		System.out.println("=== Le jeu commence ===");
		boolean effetSpecial = false;	// cette variable permet de boucler � l'infini dans le cas des cartes sp�ciales
		while (true) {	// boucle du jeu
			joueurCourant = joueurs[indiceJoueurCourant];
			System.out.println("Tour de " + joueurCourant.getPseudo());
			if (effetSpecial && talon.sommet() instanceof CarteSpecial) {	// le joueur pr�c�dant a jou� une carte sp�ciale
				effetSpecial = false;
				if (((CarteSpecial) talon.sommet()).getSymbole() == Symbole.PASSER) {	// le joueur courant doit passer son tour
					System.out.println(joueurCourant.getPseudo() + " doit passer son tour -> effet de la carte : " + talon.sommet().toString());
					joueurSuivant();
					continue;
				}
				if (((CarteSpecial) talon.sommet()).getSymbole() == Symbole.PLUS2) {	// le joueur pr�c�dant a jou� +2
					// le joueur courant doit piocher 2 cartes
					System.out.println(joueurCourant.getPseudo() + " doit piocher 2 cartes et passer son tour -> effet de la carte " + talon.sommet());
					for (int i = 0; i < 2; i++) {
						joueurCourant.prendreCarte();
					}
					// et passer son tour
					joueurSuivant();
					continue;
				}
				if (((CarteSpecial) talon.sommet()).getSymbole() == Symbole.PLUS4) {	// le joueur pr�c�dant a jou� +4
					// le joueur courant doit piocher 4 cartes
					System.out.println(joueurCourant.getPseudo() + " doit piocher 4 cartes et passer son tour -> effet de la carte " + talon.sommet());
					for (int i = 0; i < 4; i++) {
						joueurCourant.prendreCarte();
					}
					// et passer son tour
					joueurSuivant();
					continue;
				}
			}
			joueurCourant.jouerTour();
			if (joueurCourant.nbCartes() == 0) {	// on teste si le joueur courant a vid� sa main
				System.out.println(joueurCourant.getPseudo() + " a gagn� !");
				break;
			}
			if (joueurCourant.nbCartes() == 1) {	// on teste si le joueur courant lui reste une seule carte ( UNO ! ) dans sa main
				System.out.println(joueurCourant.getPseudo() + " <UNO!>");
			}
			// On doit tester ici si le joueur a des doublons de la carte jou�e TODO
			// On doit tester la carte inverser � ce niveau
			if (talon.sommet() instanceof CarteSpecial) {	// le joueur courant a jou� une carte sp�ciale
				effetSpecial = true;	// activer l'effet sp�cial
				if (((CarteSpecial) talon.sommet()).getSymbole() == Symbole.INVERSER) {	// le joueur courant � invers� le sens
					System.out.println(joueurCourant.getPseudo() + " a invers� le sens du jeu");
					sens *= -1;	// la valeur de sens est soit 1 soit -1, on multiplie par -1 pour changer
				}
			}
			joueurSuivant();
			System.out.println();
		}
		System.out.println("=== Fin du jeu ===");
	}
	
	/**
	 * permet de passer au joueur suivant selon le sens du jeu
	 */
	private void joueurSuivant() {
		// avancer vers le joueur suivant
		indiceJoueurCourant += sens;
		// On doit v�rifier si l'indice a d�passer les bornes du tableau
		if (indiceJoueurCourant < 0) {
			indiceJoueurCourant += nbJoueurs;
			// exemple : 3 joueurs -> [ 0 , 1 , 2 ]
			// 0 + (-1) = -1 < 0 -> -1 + 3 -> 2 ( dernier joueur )
		}
		if (indiceJoueurCourant > nbJoueurs - 1) {	// indice >= nbJoueurs
			indiceJoueurCourant -= nbJoueurs;
			// exemple : 3 joueurs -> [ 0 , 1 , 2 ]
			// 2 + (1) = 3 > 2 -> 3 - 3 -> 0 ( premier joueur )
		}
	}

	/**
	 * permet de v�rifier si le pseudo entr� est unique ou pas
	 * @param pseudoJoueur : le pseudo � v�rifier
	 * @return true : si le pseudo est valide ( n'est pas utils� d�ja ! )
	 * @return false : sinon
	 * @param indice : l'indice de la case du tableau � v�rifer
	 */
	private boolean pseudoValide(String pseudoJoueur, int indice) {
		if (indice == 0) {	// si c'est le premier pseudo � v�rifier
			return true;	// il est valide, aucun autre pseudo le pr�c�de !
		}
		// on doit v�rifier les cases 0 jusqu'� indice - 1
		for (int j = 0; j < indice; j++) {	// il faut v�rifier tous les cartes qui pr�c�dent la case d'indice (indice)
			//System.out.println("pseudo � v�rifier : " + joueurs[j]);
			if (joueurs[j].getPseudo().equalsIgnoreCase(pseudoJoueur)) {	// si on a trouv� un joueur ayant le m�me pseudo
				return false;	// le pseudo n'est pas valide
			}
		}
		return true;	// tous les pseudos pr�c�dants sont != que le pseudo � v�rifier, il est donc valide
	}
	
}
