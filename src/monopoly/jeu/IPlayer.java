package monopoly.jeu ;

import java.util.List;
import java.util.Stack;

import monopoly.evenements.IEvent;
import monopoly.proprietes.IProperty;

/** Cette interface decrit les fonctionnalites que doit presenter une
 * classe representant un joueur de Monopoly */
//public interface Joueur
public interface IPlayer
{

	/** Le numero du joueur */
	//int numero() ;
	int getId() ;

	/** Le nom du joueur */
	//String nom() ;
	String getName() ;

	/** Indique si le joueur est emprisonne */
	//boolean enPrison() ;
	boolean isJailed() ;

	/** Emprisonne le joueur */
	//void emprisonner() ;
	void setJailed() ;

	/** Indique si le joueur est elimine */
	//boolean elimine() ;
	boolean isEliminated() ;

	/** elimine le joueur */
	//void eliminer() ;
	void setEliminated() ;

	/** Liquidites possedees par le joueur */
	//int especes() ;
	int getMoney() ;

	/** Impose au joueur le paiement de la somme specifiee
	 * @return true si le joueur a pu payer, false sinon
	 */
	//boolean payer(int somme) ;
	boolean take(int amount) ;

	/** Verse au joueur la somme specifiee */
	//void verser(int somme) ;
	void give(int amount) ;

	/** Donne la case sur laquelle le joueur est place */
	//Case position() ;
	ISlot getSlot() ;

	/** Place le joueur sur la case specifiee */
	//void placerSur(Case c) ;
	void setSlot(ISlot c) ;

	/** Donne la liste des autres joueurs encore en lice (tous sauf
	 * <code>this</code> et les elimines)*/
	//List<Joueur> adversaires() ;
	List<IPlayer> getOpponents() ;

	/** Titres de proprietes possedes par le joueur */
	//List<Propriete> titres() ;
	List<IProperty> getProperties() ;

	/** Cartes conservees par le joueur */
	//List<Evenementt> cartes() ;
	List<IEvent> getCards() ;

	/** La pile d'evenements que le joueur doit subir pendant son tour
	 * de jeu : si la pile est vide, le joueur a termine son tour ; sinon il doit
	 * depiler un evenement pour l'executer. Il peut arriver qu'un evenement manipule
	 * cette pile (par exemple "Aller en prison" termine le tour du joueur meme s'il lui
	 * restait theoriquement des choses a faire) */
	//Stack<Evenement> chosesAFaire() ;
	Stack<IEvent> getTodo() ;
	
	/** Libere un joueur de prison */
	void setFree();

}