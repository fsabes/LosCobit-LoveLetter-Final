package panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import assets.BotonPersonalizado;
import assets.ImageAssets;
import objetosJuego.*;

public class MenuPrincipalPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BufferedImage background;
	private Juego window;
	private final int WIDTH = 1080;
	private final int HEIGHT = 720;
	public String textDialog;
	public String textoTotal = "";
	public int offsetCloud[];
	public Timer timerAnimation;
	public MenuPrincipalPanel(Juego parent) {

		textDialog = "";
		background = ImageAssets.backgroundMenu;
		window = parent;
		setLayout(null);
		mostrarMenu();
		offsetCloud = new int[5];
		
		offsetCloud[0] = 0;
		offsetCloud[1] = 500;
		offsetCloud[2] = 105;
		offsetCloud[3] = 400;
		
	
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		Dimension currentDimension = window.getContentPane().getSize();
		g2.scale(currentDimension.getWidth() / WIDTH, currentDimension.getHeight() / HEIGHT);
		g2.drawImage(ImageAssets.sky,0,0,1080,720 ,null);
		
		
		g2.drawImage(ImageAssets.cloud1,null,offsetCloud[0],150);
		g2.drawImage(ImageAssets.cloud2,null,offsetCloud[1],20);
		g2.drawImage(ImageAssets.cloud3,null,offsetCloud[2],80);
		g2.drawImage(ImageAssets.cloud4,null,offsetCloud[3],170);
		
		g2.drawImage(ImageAssets.castle, 0,0,1080,720,null);
		
		g2.drawImage(ImageAssets.princess, null,-50,0);
		
		g2.drawImage(ImageAssets.logo, null,450,70);

	}

	public void mostrarMensaje(String text) {
		new Mensaje(window, true, text);

	}
	
	public void mostrarMenu() {
		BotonPersonalizado boton1 = new BotonPersonalizado("Nueva Partida", 50, 275, 470, 400);
		boton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Sound.buttonSound.play();
				window.userName = (new InterfazElejirNameUser(window, true)).mostrarVentana();
				window.showPlayers = true;
				removeAll();
				window.panelSalas = new SalasPanel(window);
				setVisible(false);
				window.add(window.panelSalas);


				repaint();
			}
		});

		add(boton1);
		BotonPersonalizado boton2 = new BotonPersonalizado("Salir", 50, 275, 470, 500);
		boton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Sound.playCard.play();
				System.exit(0);
			}
		});
		add(boton2);
		repaint();
		
		timerAnimation = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				offsetCloud[0]++;
				offsetCloud[1]++;
				offsetCloud[2]++;
				offsetCloud[3]+=2;
				if (offsetCloud[0]>1080)
					offsetCloud[0] =0 ;

				if (offsetCloud[1]>1080)
					offsetCloud[1] =0 ;

				if (offsetCloud[2]>1080)
					offsetCloud[2] =0 ;

				if (offsetCloud[3]>1080)
					offsetCloud[3] =0 ;
				
				repaint();
			}
		});
		timerAnimation.start();
		
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

}
