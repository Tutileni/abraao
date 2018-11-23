package com.uno.JEU;

/**
 * �num�ration pr�sentant les couleurs possibles des cartes du jeu uno
 * @author Stoufa
 *
 */
public enum Couleur {
	
	ROUGE("Rouge"),
	JAUNE("Jaune"),
	VERT("Vert"),
	BLEU("Bleu"),
	NOIR("Noir");
	
	/**
	 * la valeur de la couleur, en fait c'est une cha�ne de caract�res contenant le nom de la couleur
	 * utile dans la m�thode toString()
	 */
	private String valeur;
	
	/**
	 * contructeur
	 * @param valeur : la valeur de la couleur
	 */
	private Couleur(String valeur) {
		this.valeur = valeur;
	}
	
	/**
	 * @return la valeur de la couleur
	 */
	public String getValeur() {
		return valeur;
	}
}
