package monopoly.jeu ;
import monopoly.evenements.IEvent;
import monopoly.proprietes.IProperty;

/** Cette interface décrit une case du plateau de jeu du Monopoly */
//public interface Case
public interface ISlot
{

	/** Numéro de la case */
	//int numero() ;
	int getId() ;

	/** Donne la case associée au numéro spécifié */
	//Case get(int numero) ;
	ISlot getSlot(int id) ;

	/** Intitulé de la case */
	//String nom() ;
	String getName() ;

	/** Titre de propriété associé à la case (éventuellement
	 * <code>null</code>)*/
	//Propriete propriete() ;
	IProperty getProperty() ;

	/** Événément susceptible de se déclencher à l'arrivée sur cette
	 * case (éventuellement <code>null</code>) */
	//Evenement evenement() ;
	IEvent getEvent() ;
	
	/** Définit l'évènement associé à la case */
	void setEvent(IEvent event);

}