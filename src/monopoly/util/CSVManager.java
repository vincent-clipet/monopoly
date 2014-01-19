package monopoly.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/** Classe utilitaire permettant de gerer les fichiers CSV */
public class CSVManager
{

	//
	// METHODS
	//
	/** Extrait les donnees d'un fichier CSV, avec un separateur defini */
	public static ArrayList<String[]> read(String filename, String separator)
	{
		return read(filename, separator, 0);
	}
	
	/** Extrait les donnees d'un fichier CSV, avec un separateur defini,
	 * et un nombre de lignes a ignorer au debut du fichier */
	public static ArrayList<String[]> read(String filename, String separator, int linesToIgnore)
	{
		ArrayList<String> unsplit = CSVManager.read(filename, linesToIgnore);
		ArrayList<String[]> ret = new ArrayList<String[]>();

		for (String line : unsplit)
		{
			String[] fields = line.split(separator, -1);
			ret.add(fields);
		}

		return ret;
	}

	/** Extrait les lignes d'un fichier CSV,
	 * avec un nombre de lignes a ignorer au debut du fichier */
	public static ArrayList<String> read(String filename, int linesToIgnore)
	{
		ArrayList<String> ret = new ArrayList<String>();
		BufferedReader br = null;
		String line = "";

		try
		{
			br = new BufferedReader(new FileReader(filename));
			int countIgnored = 0;

			while ((line = br.readLine()) != null)
			{
				if (countIgnored >= linesToIgnore)
				{
					if (line.length() != 0)
						ret.add(line);
				}
				else
					countIgnored++;
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return ret;
	}

	/** Enregistre des donnees dans un fichier CSV */
	public static boolean write(String filename, ArrayList<String> data)
	{
		ArrayList<String[]> newData = new ArrayList<String[]>();

		for (String s : data)
			newData.add(new String[] {s});

		return CSVManager.write(filename, newData, "");
	}


	/** Enregistre des donnees dans un fichier CSV,
	 * en specifiant le separateur */
	public static boolean write(String filename, ArrayList<String[]> data, String separator)
	{
		try
		{
			FileWriter writer = new FileWriter(filename);

			for (String[] fields : data)
			{
				for (int i = 0; i < fields.length - 1; i++)
					writer.append(fields[i]).append(separator);

				writer.append(fields[fields.length - 1]).append("\n");
			}

			writer.flush();
			writer.close();

			return true;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		} 
	}

}