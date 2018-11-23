package com.uno.JEU;

/**
 * cette classe repr�sente les cartes chiffres du jeu : 0..9 x BLEU,JAUNE,VERT,ROUGE
 * @author Stoufa
 *
 */
public class CarteChiffre extends Carte {

	/**
	 * la valeur de la carte, doit �tre un entier entre 0 et 9
	 */
	private int valeur;
	
	/**
	 * constructeur
	 * @param couleur : la couleur de la carte
	 * @param valeur : la valeur de la carte
	 */
	public CarteChiffre(Couleur couleur, int valeur) {
		super(couleur);
		this.valeur = valeur;
	}
	
	/**
	 * permet de retourner une cha�ne contenant la repr�sentation de l'objet courant
	 */
	@Override
	public String toString() {
		return super.toString() + " " + valeur;
	}

	/**
	 * @return true : si la carte courante et celle pass�e en param�tre sont compatibles ( ��d jouable )
	 * @return false : sinon
	 */
	@Override
	public boolean compatible(Carte carte) {
		if (carte instanceof CarteChiffre) {	//	CarteChiffre
			// m�me couleur ou m�me valeur ?
			return ( carte.couleur == couleur ) || ( ((CarteChiffre) carte).valeur == valeur );	// m�me couleur ou m�me valeur
		} else {	// CarteSpecial
			// m�me couleur ?
			return carte.couleur == couleur;	// m�me couleur
		}
	}

}
