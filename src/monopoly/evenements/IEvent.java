package monopoly.evenements ;

import monopoly.jeu.IPlayer;

/** Cette interface decrit les fonctionnalites associees aux evenements du jeu */
//public interface Evenement
public interface IEvent
{

	/** Intitule de l'evenement (i.e. en principe de la case associee ou de la carte) */
	//String nom() ;
	String getName() ;

	/** Retourne le joueur cible de l'evenement */
	//Joueur cible() ;
	IPlayer getTarget() ;

	/** Definit le joueur cible de l'evenement */
	void setTarget(IPlayer p) ;

	/** Execute l'evenement et renvoie true s'il a reussi, false sinon */
	boolean run() ;
	
	/** 'Nettoie' l'evenement, en reinitialisant les valeurs de ses attributs */
	void clear();

}