package monopoly.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** JPanel contenant le plateau de jeu */
public class BoardPanel extends JPanel
{

	//
	// ATTRIBUTES
	//
	/** Reference sur le PropertyPanel */
	private PropertyPanel prop;
	
	/** Image de fond */
	private BufferedImage bg;
	
	/** Liste des labels correspondant aux cases */
	private LabelCustom[] labels;
	
	/** Coordonnées visuelles des cases */
	private final int[][] coord = new int[][]
			{
			{ 2,3,47,47 },
			{ 49,3,80,47 },
			{ 82,3,114,47 },
			{ 116,3,148,47 },
			{ 150,3,182,47 },
			{ 184,3,216,47 },
			{ 218,3,250,47 },
			{ 252,3,283,47 },
			{ 285,3,317,47 },
			{ 319,3,351,47 },

			{ 353,3,397,47 },
			{ 353,49,397,80 },
			{ 353,82,397,114 },
			{ 353,116,397,148 },
			{ 353,150,397,182 },
			{ 353,184,397,216 },
			{ 353,218,397,250 },
			{ 353,252,397,283 },
			{ 353,285,397,317 },
			{ 353,319,397,351 },

			{ 353,353,397,397 },
			{ 319,353,351,397 },
			{ 285,353,317,397 },
			{ 252,353,283,397 },
			{ 218,353,250,397 },
			{ 184,353,216,397 },
			{ 150,353,182,397 },
			{ 116,353,148,397 },
			{ 82,353,114,397 },
			{ 49,353,80,397 },

			{ 2,353,47,397 },
			{ 2,319,47,351 },
			{ 2,285,47,317 },
			{ 2,252,47,283 },
			{ 2,218,47,250 },
			{ 2,184,47,216 },
			{ 2,150,47,182 },
			{ 2,116,47,148 },
			{ 2,82,47,114 },
			{ 2,49,47,80 },
			};



	//
	// CONSTRUCTOR
	//
	/** Construit un nouveau BoardPanel */
	public BoardPanel(PropertyPanel prop)
	{
		this.prop = prop;

		this.addMouseListener(new MouseListenerCustom());
		this.labels = new LabelCustom[40];

		for (int i = 0; i < 40; i++)
		{
			Rectangle r = new Rectangle(this.coord[i][0], this.coord[i][1], this.coord[i][2] - this.coord[i][0], this.coord[i][3] - this.coord[i][1]);
			this.labels[i] = new LabelCustom(i, r);
			add(this.labels[i]);
		}

		try
		{
			this.bg = ImageIO.read(new File("files/plateau-400x400.PNG"));
		}
		catch (IOException ex)
		{
			System.out.println("ERREUR chargement background");
		}
	}



	//
	// METHODS
	//
	/** Permet d'afficher l'image de fond */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(this.bg, 0, 0, null);         
	}



	//
	// CLASSES
	//
	/** MouseAdapter pour detecter les clics sur chaque case du plateau */
	class MouseListenerCustom extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			Point point = e.getPoint();

			for (LabelCustom lc : labels)
			{
				//if (lc.contains(point)) // doesn't work, don't know why
				if (lc.containsPoint(point))
				{
					prop.setSlot(lc.getId());
					e.consume();
					return;
				}
			}

		}
	}

	/** JLabel pour calculer les collisions entre les clics souris et les cases */
	class LabelCustom extends JLabel
	{
		private int id;
		private Rectangle rect;

		public LabelCustom(int id, Rectangle rect)
		{
			super();
			this.id = id;
			this.rect = rect;
			this.setOpaque(true);
		}

		/** Retourne true si le Point p se trouve dans ce JLabel, false sinon */
		public boolean containsPoint(Point p)
		{
			int x = (int)p.getX();
			int y = (int)p.getY();
			int xA = (int)this.rect.getX();
			int yA = (int)this.rect.getY();
			int xB = (int)this.rect.getX() + (int)this.rect.getWidth();
			int yB = (int)this.rect.getY() + (int)this.rect.getHeight();

			return (x >= xA && x <= xB && y >= yA && y <= yB);
		}

		public int getId()
		{
			return this.id;
		}
	}

}