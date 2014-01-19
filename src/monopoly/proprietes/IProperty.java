package monopoly.proprietes ;

import monopoly.jeu.IPlayer;
import monopoly.jeu.ISlot;

/** Cette interface decrit les fonctionnalites associees a tout titre
 * de propriete */
//public interface Propriete
public interface IProperty
{

	/** La case du plateau de jeu associee a ce titre de propriete */
	// Case position() ;
	ISlot getSlot() ;
	
	/** Definit la case du plateau de jeu associee a ce titre de propriete */
	void setSlot(ISlot slot); // added

	/** Nom de la propriete (le meme que la case en principe) */
	//String nom() ;
	String getName() ;

	/** Indique si la propriete est hypothequee */
	//boolean hypotheque() ;
	boolean isMortgaged() ;

	/** Hypotheque la propriete (en procurant ainsi des liquidites au
	 * proprietaire pour une valeur moitie du prix d'achat) */
	//void hypothequer() ;
	void setMortgaged() ;

	/** Leve l'hypotheque (si le joueur possede les liquidites
	 * suffisantes soit la valeur hypothecaire + 10%)
	 * @return true si l'hypotheque est levee, false sinon */
	//void deshypothequer() ;
	boolean setUnmortgaged() ;

	/** Prix d'achat */
	//int getPrice();
	int getPrice() ;

	/** Le groupe auquel est rattachee la propriete */
	//Groupe groupe() ;
	IGroup getGroup() ;

	/** Indique si la propriete est constructible */
	//boolean constructible() ;
	boolean isBuildable() ;

	/** Construit un batiment sur cette propriete si c'est possible
	 * (cf. regles de constructibilite et liquidites du joueur).
	 * @return true si la construction a pu etre realisee, false
	 * sinon */
	//boolean construire() ;
	boolean build() ;

	/** Detruit un batiment sur cette propriete si c'est possible (et
	 * reverse alors au joueur la moitie du prix d'achat des
	 * batiments) 
	 * @return true si un batiment a ete detruit, false sinon */
	//boolean detruire() ;
	boolean unbuild() ;

	/** Proprietaire du titre (eventuellement <code>null</code>) */
	//Joueur proprietaire() ;
	IPlayer getOwner() ;
	
	/** Definit le proprietaire du titre */
	void setOwner(IPlayer p); // added

	/** Montant du loyer a percevoir */
	//int loyer() ;
	int calcRent() ;

	/** Niveau des constructions (0 = terrain nu, 1 a 4 = nb de
	 * maisons, 5 = hôtel)  */
	//int niveauImmobilier() ;
	int getBuildings() ;

}