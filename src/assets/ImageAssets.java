package assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageAssets implements Serializable{

	static public BufferedImage backgroundMenu;
	static public BufferedImage backgroundSala;
	static public BufferedImage spritePlayersHud;
	static public BufferedImage backgroundJugadores;
	static public BufferedImage fondoInterfaz;
	static public BufferedImage hudRound;
	static public BufferedImage fondoPrincesa;
	static public BufferedImage tablaPuntaje;
	static public BufferedImage tablaGanador;
	static public BufferedImage tablero;
	static public BufferedImage playersRound;
	static public ImageIcon iconButton;
	static public BufferedImage castle;
	static public BufferedImage sky;
	static public BufferedImage cloud1;
	static public BufferedImage cloud2;
	static public BufferedImage cloud3;
	static public BufferedImage cloud4;
	static public BufferedImage princess;
	
	static public BufferedImage logo;
	
	
	static public BufferedImage cartaGuardia;
	static public BufferedImage cartaSacerdote;
	static public BufferedImage cartaBaron;
	static public BufferedImage cartaMucama;
	static public BufferedImage cartaPrincipe;
	static public BufferedImage cartaRey;
	static public BufferedImage cartaCondesa;
	static public BufferedImage cartaPrincesa;
	static public BufferedImage cartaPrin;
	static public BufferedImage backCard;
	
	
	static public BufferedImage hudMenu;
	static public BufferedImage hudText;

	public static void init() {	
		try {
			
			
			logo = ImageIO.read(new File("Backgrounds/logo.png"));
			princess = ImageIO.read(new File("Backgrounds/princess.png"));
			castle =  ImageIO.read(new File("Backgrounds/castle.png"));
			sky =  ImageIO.read(new File("Backgrounds/sky.png"));
			cloud1 = ImageIO.read(new File("Backgrounds/cloud1.png"));
			cloud2 =  ImageIO.read(new File("Backgrounds/cloud2.png"));
			cloud3 =  ImageIO.read(new File("Backgrounds/cloud3.png"));
			cloud4 = ImageIO.read(new File("Backgrounds/cloud4.png"));
			playersRound = ImageIO.read(new File("Backgrounds/hudPlayersRound.png"));
			tablero =  ImageIO.read(new File("Backgrounds/tablero.png"));
			tablaGanador = ImageIO.read(new File("Fotos/GanadorTabla.png"));
			backgroundMenu = ImageIO.read(new File("Backgrounds/background.jpg"));
			backgroundSala = ImageIO.read(new File("Backgrounds/selectPlayers.png"));
			spritePlayersHud = ImageIO.read(new File("Backgrounds/hudPlayers.png"));
			iconButton = new ImageIcon(ImageIO.read(new File("Backgrounds/hudPlayers.png")) );
			fondoInterfaz = ImageIO.read(new File("Backgrounds/fondoInterfaz.png"));
			hudRound = ImageIO.read(new File("Backgrounds/hudRound.png"));
			hudMenu = ImageIO.read(new File("Backgrounds/hudMenu.png"));
			tablaPuntaje = ImageIO.read(new File("Fotos/RecuadroPuntos.png"));
			hudText = ImageIO.read(new File("Backgrounds/hudText.png"));
			fondoPrincesa = ImageIO.read(new File("Fotos/PrincesaFoto.png"));
			
			
			cartaGuardia = ImageIO.read(new File("Cartas/CartaGuardia.png"));
			cartaSacerdote = ImageIO.read(new File("Cartas/CartaSacerdote.png"));
			cartaBaron = ImageIO.read(new File("Cartas/CartaBaron.png"));
			cartaMucama = ImageIO.read(new File("Cartas/CartaMucama.png"));
			cartaPrincipe = ImageIO.read(new File("Cartas/CartaPrincipe.png"));
			cartaRey = ImageIO.read(new File("Cartas/CartaRey.png"));
			cartaCondesa = ImageIO.read(new File("Cartas/CartaCondesa.png"));
			cartaPrincesa = ImageIO.read(new File("Cartas/CartaPrincesa.png"));
			backCard  = ImageIO.read(new File("Cartas/backCard.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}