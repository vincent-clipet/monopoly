package monopoly.jeu;

import java.util.Random;

/** Paire de dés */
public class DicePair
{

	//
	// ATTRIBUTES
	//
	/** Tableau contenant la valeur des 2 dés */
	private int[] dices;
	
	/** GPA */
	private Random rand;



	//
	// CONSTRUCTOR
	//
	/** Construit une paire de dés */
	public DicePair()
	{
		this.rand = new Random(System.nanoTime());
		this.dices = new int[2];
	}



	//
	// METHODS
	//
	/** Genere des nouvelles valeurs pour les dés */
	public void roll()
	{
		this.dices[0] = this.rand.nextInt(6) + 1;
		this.dices[1] = this.rand.nextInt(6) + 1;
	}



	//
	// GET & SET
	//
	/** Retourne les valeurs actuelles des dés */
	public int[] getValues()
	{
		return this.dices;
	}

	/** Retourne la valeur d'un seul dé */
	public int getValue(int i)
	{
		return this.dices[i];
	}



	//
	// UTIL
	//
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("DicePair: ").append(this.dices[0]).append(", ").append(this.dices[1]);

		return sb.toString();
	}

	/** Retourne true si les 2 dés ont la meme valeur, false sinon */
	public boolean isDouble()
	{
		return this.dices[0] == this.dices[1];
	}

	/** Retourne la somme des valeurs des 2 dés */
	public int getTotal()
	{
		return this.dices[0] + this.dices[1];
	}

}