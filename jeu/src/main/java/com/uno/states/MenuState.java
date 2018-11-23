package com.uno.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * l'�tat du menu affich� au d�but du jeu
 * @author Stoufa
 *
 */
public class MenuState extends BasicGameState {
    /**
     * liste des options
     */
    private String[]  menuOptions = new String[] {
            "Jouer",
            "Quitter"
    };
    /**
     * permet de savoir quelle option on a s�l�ctionn� ( initialement la premi�re )
     */
    private int       index       = 0;
    /**
     * identificateur de l'�tat ( State )
     */
    public static int stateID;

    /**
     * constructeur
     * @param stateID identificateur de l'�tat ( state )
     */
    public MenuState( int stateID ) {
        MenuState.stateID = stateID;
    }

    /**
     * initialiser l'�tat du menu
     */
    @Override
    public void init( GameContainer arg0, StateBasedGame arg1 ) throws SlickException {
        // rien � initialiser !
    }

    @Override
    /**
     * mettre � jour la logique de l'�tat
     */
    public void update( GameContainer container, StateBasedGame sbg, int delta ) throws SlickException {
        Input input = container.getInput(); // utilis� pour tester l'entr�e de l'utilisateur ( clavier et souris )
        if ( input.isKeyPressed( Input.KEY_UP ) ) { // si la touche Fl�che-Haut est appuiy�
            index--;
            if ( index == -1 ) { // si on a d�bord� le tableau du haut ...
                index = menuOptions.length - 1; // ... on recommance du bas
            }
        } else if ( input.isKeyPressed( Input.KEY_DOWN ) ) { // si la touche Fl�che-Bas est appuiy�
            index++;
            if ( index == menuOptions.length ) { // si on a d�bord� le tableau du bas ...
                index = 0; // ... on recommance du haut
            }
        } else if ( input.isKeyPressed( Input.KEY_ENTER ) ) { // si la touche Entr�e est appuiy�
            if ( index == 0 ) { // premi�re option : Jouer
                // On entre l'�tat ( State ) du jeu avec des transitions
            	//Marcado
                //sbg.enterState( GameState.stateID, new FadeOutTransition(), new FadeInTransition() );
                sbg.enterState(1, new FadeOutTransition(), new FadeInTransition() );
            } else if ( index == 1 ) { // 2eme option : Quitter
                // Quitter l'application
                System.exit( 0 );
            }
        }
    }

    /**
     * dessiner la logique du menu
     */
    @Override
    public void render( GameContainer gc, StateBasedGame sbg, Graphics g ) throws SlickException {
        for ( int i = 0; i < menuOptions.length; ++i ) { // On parcourt toutes les options
            if ( i == index ) { // l'option s�l�ctionn�e ...
                g.setColor( Color.red ); // ... est color�e en rouge
            } else { // les autres
                g.setColor( Color.white ); // sont color�es en blanc
            }
            int step = gc.getHeight() / ( menuOptions.length + 1 ); // l'espace entre les diff�rents options
            /*
             * ----------
             * <step>
             * option1
             * <step>
             * option2
             * <step>
             * ----------	
             * On doit donc diviser la hauteur de la fen�tre par (le nombre d'options + 1)
             */
            // afficher l'option centr�
            g.drawString(
                    menuOptions[i],
                    gc.getWidth() / 2 - g.getFont().getWidth( menuOptions[i] ) / 2,
                    step * ( i + 1 ) );
        }
    }

    /**
     * retourne l'identificateur de l'�tat
     */
    @Override
    public int getID() {
        return stateID;
    }
}
