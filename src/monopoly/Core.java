package monopoly;

import monopoly.jeu.Game;

public class Core
{
	public static boolean debug = false;

	public static void main(String[] args)
	{
		if (args.length >= 1)
			if (args[0].equals("true"))
				Core.debug = true;

		Game.getInstance().init();
		Game.getInstance().start();
	}

}