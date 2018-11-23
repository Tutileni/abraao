package com.uno.gameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import com.uno.gfx.Sprite;


/**
 * cette classe repr�sente les cartes chiffres du jeu : [0..9] x [BLEU,JAUNE,VERT,ROUGE]
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
     * @throws SlickException 
     */
    public CarteChiffre( Couleur couleur, int valeur ) throws SlickException {
        super( couleur );
        this.valeur = valeur;
        image = Sprite.get( valeur, couleur );
    }

    /**
     * permet de retourner une cha�ne contenant la repr�sentation de l'objet courant
     */
    @Override
    public String toString() {
        return "(" + couleur.getValeur() + "," + valeur + ")";
    }

    /**
     * @return true : si la carte courante et celle pass�e en param�tre sont compatibles ( ��d jouable )
     * @return false : sinon
     */
    @Override
    public boolean compatible( Carte carte ) {
        if ( carte instanceof CarteChiffre ) { //	CarteChiffre
            // m�me couleur ou m�me valeur ?
            return ( carte.couleur == couleur ) || ( ( (CarteChiffre) carte ).valeur == valeur ); // m�me couleur ou m�me valeur
        } else { // CarteSpecial
            // m�me couleur ?
            return carte.couleur == couleur; // m�me couleur
        }
    }

    @Override
    public void update( GameContainer container ) throws SlickException {
        // TODO Auto-generated method stub

    }

}
