package com.uno.JEU;

/**
 * cette classe repr�sente les cartes sp�ciales du jeu
 * @author Stoufa
 *
 */
public class CarteSpecial extends Carte {

	/**
	 * symbole de la carte
	 * @see Symbole
	 */
	private Symbole symbole;
	
	/**
	 * constructeur
	 * @param couleur : la couleur de la carte
	 * @param symbole : le symbole de la carte
	 */
	public CarteSpecial(Couleur couleur, Symbole symbole) {
		super(couleur);
		this.symbole = symbole;
	}
	
	/**
	 * retourne une cha�ne contenant la repr�sentation de la carte 
	 */
	@Override
	public String toString() {
		return super.toString() + " " + symbole.getValeur();
	}
	
	/**
	 * @return true : si la carte courante est compatible avec celle pass�e en param�tre (��d : jouable)
	 * @return false : sinon
	 * @param carte : la carte � comparer avec l'objet courant
	 */
	@Override
	public boolean compatible(Carte carte) {
		if (couleur == Couleur.NOIR) {
			return true;	// Les cartes noirs ( nottament le Joker et la carte +4 )
			// peuvent �tre d�pos�e sur n'importe qu'elle autre carte
		}
		if (carte instanceof CarteSpecial) {	//	CarteSpecial
			// m�me couleur ou m�me symbole ?
			return ( carte.couleur == couleur ) || ( ((CarteSpecial) carte).symbole == symbole );	// m�me couleur ou m�me symbole
		} else {	// CarteChiffre
			// m�me couleur ?
			return carte.couleur == couleur;	// m�me couleur
		}
	}
	
	/**
	 * permet de changer la couleur de la carte sp�ciale et ceci n'est possible
	 * que si la couleur initiale de la carte est NOIR
	 * @param couleur : peut �tre ROUGE, JAUNE, VERT ou BLEU
	 */
	public void setCouleur(Couleur couleur) {
		if (this.couleur == Couleur.NOIR) {
			// On ne peut changer la couleur que quand la couleur initiale de
			// la carte est noire : Joker ou +4
			this.couleur = couleur;
		}
	}

	/**
	 * permet de retourner le symbole de la carte sp�ciale
	 * @return le symbole de la carte sp�ciale
	 */
	public Symbole getSymbole() {
		return symbole;
	}
	
	

}
