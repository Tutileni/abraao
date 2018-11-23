package com.uno.JEU;

import java.util.ArrayList;
import java.util.HashMap;

import com.uno.JEU.io.Clavier;


/**
 * cette classe repr�sente le joueur : ce qu'il a et ce qu'il peut faire, ��d ses propri�t�s et ses actions
 * @author Stoufa
 *
 */
public class Joueur {

	/**
	 * la main du joueur contenant toutes ses cartes
	 */
	private Main main;
	/**
	 * le pseudo du joueur
	 */
	private String pseudo;
	/**
	 * la pioche : la pile des cartes o� le joueur peut prendre des cartes dans le cas o� il n'a pas de cartes jouables
	 */
	private Pioche pioche;
	/**
	 * le talon : la pile des cartes o� les joueurs d�pose leurs cartes
	 */
	private Talon talon;

	/**
	 * contructeur
	 * @param pseudo : pseudo du joueur
	 * @param pioche : la pioche
	 * @param talon : le talon
	 */
	public Joueur(String pseudo, Pioche pioche, Talon talon) {
		this.pseudo = pseudo;
		this.pioche = pioche;
		this.talon = talon;
		main = new Main();
	}
	
	/**
	 * permet de prender une carte de la pioche et l'ajouter � la main du joueur
	 * @return la carte tir�e
	 */
	public Carte prendreCarte() {
		if (pioche.nbCartes() == 0) {	// la pioche est vide !
			System.out.println("La pioche est vide !");
			// dans ce cas, on doit utiliser le talon ( sauf le sommet ) pour la populer de nouveau
			Carte sommetTalon = talon.depiler();
			while (talon.nbCartes() != 0) {	// cette boucle va vider le talon dans la pioche
				pioche.empiler(talon.depiler());
			}
			pioche.melanger();
			talon.empiler(sommetTalon);	// remettre le sommet du talon
		}
		Carte carte = pioche.depiler();
		main.ajouter(carte);
		return carte;
	}
	
	/**
	 * cette m�thode permet d'afficher les cartes dans la main du joueur courant
	 */
	public void afficherMain() {
		String str = "";
		ArrayList<Carte> cartes = main.getCartes();
		if (cartes.isEmpty()) {
			str = "[VIDE]";
		}
		for(int i = 0; i < cartes.size(); ++i) {
			Carte carte = cartes.get(i);
			str = str + i + ") " + carte.toString();
			if (carte.compatible(talon.sommet())) {
				str = str + " [Jouable]";
			}
			if (i != cartes.size() - 1) {	// if this isn't the last iteration
				str = str + "\n";	// add a new line
			}
		}
		System.out.println(str);
	}
	
	/**
	 * permet au joueur de jouer son tour
	 */
	public void jouerTour() {	// TODO : ajouter le cas o� on a des doublons ! on doit se d�barasser de toutes les occurences de la carte jou�e !
		talon.afficherSommet();
		afficherMain();
		if (nbCartesJouables() == 0) {
			System.out.println("Vous n'avez pas de cartes jouables ! vous devez piocher !");
			pause();
			Carte c = prendreCarte();
			System.out.println("La carte pioch�e est : " + c);
			if (!c.compatible(talon.sommet())) {	// la carte r�cemment pioch�e n'est pas compatible avec le sommet du talon
				System.out.println("Pas de chance ! vous n'avez encore pas de cartes jouables, vous devez passer le tour");
				System.out.println("----------------------------------");
				return;	// passer le tour <=> quitter la m�thode
			} else {
				afficherMain();
			}
		}
		jouerCarte();
		System.out.println("----------------------------------");
	}
	
	/**
	 * permet d'attendre un peu pour que l'utilisateur arrive � lire le message affich�
	 */
	private void pause() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * permet au joueur de jouer une carte
	 */
	private void jouerCarte() {
		boolean carteJouable = false;
		int num = -1;
		ArrayList<Carte> cartes = main.getCartes();
		
		System.out.println("Vous avez " + nbCartesJouables() + " cartes jouables");
		while (!carteJouable) {	// tant que la carte n'est pas jouable
			num = Clavier.lireEntier(cartes);
			Carte carteAjouer = cartes.get(num);
			if (carteAjouer.compatible(talon.sommet())) {
				carteJouable = true;
			} else {
				System.out.println(carteAjouer + " ne peut pas �tre jou�e sur " + talon.sommet());
			}
		}
		
		Carte carte = main.retirer(num);
		if (carte.getCouleur() == Couleur.NOIR) {	// TODO : ce test doit �tre d�l�gu�e � la classe Jeu
			// On doit demander une couleur au joueur
			System.out.println("Vous devez choisir une couleur");
			Couleur couleur = donnerCouleur();
			// Si elle est de couleur noir, on est s�r qu'elle est sp�ciale !
			((CarteSpecial) carte).setCouleur(couleur);
		}
		talon.empiler(carte);
		System.out.println(pseudo + " a jou� " + carte);
	}

	/*
	public void jouerTour() {
		Scanner scanner = new Scanner(System.in);
		boolean carteJouable = false;
		boolean horsBornes;
		int num = -1;
		ArrayList<Carte> cartes = main.getCartes();
		
		System.out.println("Vous avez " + this.nbCartesJouables() + " cartes jouables");
		
		while (!carteJouable) {	// tant que la carte n'est pas jouable
			do {
				System.out.print("? >> ");
				num = scanner.nextInt();
				horsBornes = num < 0 || num >= cartes.size();
				if (horsBornes) {
					System.out.println("indice hors bornes ! [0.." + (cartes.size() - 1) + "]");
				}
			} while (horsBornes);
			Carte carteAjouer = cartes.get(num);
			if (carteAjouer.compatible(talon.sommet())) {
				carteJouable = true;
			} else {
				System.out.println(carteAjouer + " ne peut pas �tre jou�e sur " + talon.sommet());
			}
		}
		
		scanner.close();
		Carte carte = main.retirer(num);
		talon.empiler(carte);
		System.out.println(nom + " a jou� " + carte);
	}
	*/
	
	/**
	 * permet au joueur de donner une couleur
	 * @return la couleur choisie
	 */
	private Couleur donnerCouleur() {
		HashMap<Integer, Couleur> menu = new HashMap<>();
		int i = -1;
		for(Couleur couleur : Couleur.values()) {
			if (couleur != Couleur.NOIR) {	// la couleur doit �tre diff�rente de NOIR
				i++;
				menu.put(i, couleur);
				System.out.println(i + ") " + couleur.getValeur());
			}
		}
		int choix = Clavier.lireEntier(0, i);	// le choix doit �tre entre 0 et i
		return menu.get(choix);
	}

	/**
	 * cette m�thode doit �tre priv� ! seul le joueur doit conna�tre combien il a de cartes jouables !
	 * @return le nombre de cartes jouables ��d : compatibles avec le sommet du talon 
	 */
	private int nbCartesJouables() {
		int n = 0;
		ArrayList<Carte> cartes = main.getCartes();
		if (cartes.isEmpty()) {
			return 0;
		}
		Carte sommetTalon = talon.sommet();
		for(int i = 0; i < cartes.size(); ++i) {
			Carte carte = cartes.get(i);
			if (carte.compatible(sommetTalon)) {
				n++;
			}
		}
		return n;
	}
	
	/**
	 * contrairement � nbCartesJouables(), cette fonction doit �tre publique
	 * les autres joueurs peuvent voir combien vous avez de cartes dans la main
	 * @return le nombre de cartes que poss�de le joueur dans sa main
	 */
	public int nbCartes() {
		return main.nbCartes();
	}

	/**
	 * @return le pseudo du joueur courant
	 */
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * @return cha�ne d�crivant le joueur en cours
	 * le joueur est identifi� par son pseudo
	 */
	public String toString() {
		return getPseudo();
	}
	
	
}
