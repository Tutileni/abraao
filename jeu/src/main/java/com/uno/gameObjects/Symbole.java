package com.uno.gameObjects;

/**
 * �num�ration repr�sentant les diff�rents symboles que les cartes du jeu peuvent avoir
 * @author Stoufa
 *
 */
public enum Symbole {

    PASSER( "Passer" ), INVERSER( "Inverser" ), PLUS2( "+2" ), PLUS4( "+4" ), JOKER( "Joker" );

    /**
     * la valeur du symbole, en fait c'est une cha�ne de caract�res contenant le nom du symbole
     * utile dans la m�thode toString()
     */
    private String valeur;

    /**
     * constructeur
     * @param valeur : la valeur du symbole
     */
    private Symbole( String valeur ) {
        this.valeur = valeur;
    }

    /**
     * @return la valeur du symbole
     */
    public String getValeur() {
        return valeur;
    }
}
