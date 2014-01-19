package monopoly.proprietes ;

import java.util.List;

/** Cette interface decrit les fonctionnalites associees a un groupe
 * de proprietes */
//public interface Groupe
public interface IGroup
{

	/** L'intitule du groupe */
	//String nom() ;
	String getName() ;

	/** Le prix des constructions pour ce groupe */
	//int coutImmo() ;
	int calcTotalCost() ;

	/** La liste des proprietes qui le composent */
	//List<Propriete> composition() ;
	List<IProperty> getProperties() ;

	/** Retourne le groupe dont le nom est specifie */
	//Groupe get(String nom) ;
	IGroup getGroup(String name) ;

	/** Indique si le groupe appartient entierement a un seul joueur */
	//boolean proprietaireUnique() ;
	boolean hasUniqueOwner() ;

}