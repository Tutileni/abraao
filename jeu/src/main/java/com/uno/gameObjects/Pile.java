package com.uno.gameObjects;

import java.util.Random;

/**
 * cette classe pr�sente les fonctionnalit�s communes entre la pioche et le talon
 * @author Stoufa
 *
 */
public class Pile extends Main {

    /**
     * g�n�rateur de valeurs al�atoires
     */
    protected Random rand = new Random();

    /**
     * permet de d�piler le sommet de la pile
     * @return la carte supprim�e
     */
    public Carte depiler() {
        return cartes.remove( 0 );
    }

    /**
     * permet d'empiler une carte
     * @param carte : la carte � empiler
     */
    public void empiler( Carte carte ) {
        cartes.add( 0, carte );
    }

    /**
     * permet de m�langer la pile (shuffle) en utilisant l'algorithme : M�lange de Fisher-Yates
     * @see https://fr.wikipedia.org/wiki/M%C3%A9lange_de_Fisher-Yates
     * acad�miquement parlant, on ne peut pas m�langer une pile ! mais dans ce contexte, la pile n'est
     * pas une structure FIFO ( First In First Out ) ordinaire, on peut la m�langer
     * c'est une m�thode commune entre les classes Pioche et Talon
     */
    public void melanger() {
        for ( int i = cartes.size() - 1; i > 0; i-- ) {
            // Permuter une carte al�atoire entre la premi�re
            // et la derni�re carte de la boucle
            int pick = rand.nextInt( i ); // entier al�atoire entre 0 et i - 1
            Carte randCard = cartes.get( pick );
            Carte lastCard = cartes.get( i );
            cartes.set( i, randCard );
            cartes.set( pick, lastCard );
        }
    }

    /**
     * @return une cha�ne de carat�res repr�sentant la pile courante
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * @return le sommet de la pile
     */
    public Carte sommet() {
        if ( cartes.isEmpty() ) {
            return null;
        }
        return cartes.get( 0 ); // Retourne le sommet de la pile ( sans le supprimer )
    }

}
