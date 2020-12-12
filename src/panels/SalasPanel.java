package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JTextField;

import assets.BotonPersonalizado;
import assets.ImageAssets;
import objetosJuego.FontPersonalizado;
import objetosJuego.InterfazElejirNameUser;
import objetosJuego.InterfazNameSala;
import objetosJuego.Juego;
import objetosJuego.Sound;
import servidor.ClientWorker;
import servidor.MensajeStream;

public class SalasPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 1080;
	private final int HEIGHT = 720;
	private Juego window;
	public BufferedImage background;
	public boolean showSalas = false;

	public SalasPanel(Juego parent) {

		background = ImageAssets.backgroundSala;

		window = parent;
		setLayout(null);
		
		
		BotonPersonalizado boton = new BotonPersonalizado("Partida Local", 70, 275, 375, 210);
		add(boton);
		BotonPersonalizado botonAtras = new BotonPersonalizado("Atras", 70, 275, 800, 610);
		add(botonAtras);
		BotonPersonalizado boton1 = new BotonPersonalizado("Crear Sala", 70, 275, 375, 320);
		boton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Sound.buttonSound.play();
				window.nroJugador = 0;
				window.anfitrion = true;
				if (window.conectar("localhost", 10578)==true) {
					System.out.println("Se ha conectado al servidor");
					new Thread(window).start();
					
					window.salaName = (new InterfazNameSala(window, true)).mostrarVentana();
					Sound.buttonSound.play();
					window.showPlayers = true;
					removeAll();
					window.drawPanel = new DrawPanel(window);
					setVisible(false);
					window.add(window.drawPanel);
					window.crearSala();
					window.mostrarMenu();
					window.remove(window.panelSalas);
					repaint();
					
				}
			}
		});
		add(boton1);
		BotonPersonalizado boton2 = new BotonPersonalizado("Unirse a Sala", 70, 275, 375, 430);
		boton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Sound.buttonSound.play();
				
				if (window.conectar("localhost", 10578)==true) {
					System.out.println("Se ha conectado al servidor");
					new Thread(window).start();
					window.showPlayers = true;
					removeAll();
					window.unirseSala();
					mostrarSalas();
					repaint();
					
				}
			}
		});
		add(boton2);
	}

	public void mostrarSalas() {

		removeAll();
		JTextField textoSala = new JTextField();
		textoSala.setSize(650,70);
		textoSala.setLocation(20, 590);
		textoSala.setBackground(Color.BLACK);
		textoSala.setFont(new Font("Dialog", Font.BOLD, 22));
		textoSala.setForeground(Color.WHITE);
		add(textoSala);
		BotonPersonalizado botonBack = new BotonPersonalizado("Atras", 70, 275, 775, 20);
		add(botonBack);
		BotonPersonalizado refresh = new BotonPersonalizado("Actualizar", 50, 275, 400, 500);
		add(refresh);
		BotonPersonalizado boton1 = new BotonPersonalizado("Unirse", 70, 275, 775, 590);
		boton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Sound.buttonSound.play();
				if(!textoSala.getText().isEmpty()) {
					try {
						window.salaName = textoSala.getText();
						window.getSalida().writeObject(new MensajeStream(MensajeStream.CONSULTARSALA, null, null,textoSala.getText(), 0, false, null,null));

					} catch (IOException e1) {
						e1.printStackTrace();
						System.out.println("fallo al enviar mensaje");
					}
				}
				else
					new Mensaje(window, true, "El nombre no debe estar vacio.");
				repaint();
			}
		});
		add(boton1);
		System.out.println(window.salas.toString());
		showSalas = true;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Dialog", Font.BOLD, 24));
		// Dimension currentDimension = window.getContentPane().getSize();
		// g2.scale(currentDimension.getWidth() / WIDTH, currentDimension.getHeight() /
		// HEIGHT);

		g2.drawImage(background, null, 0, 0);
		
		if (showSalas) {
			
			g2.drawImage(ImageAssets.fondoInterfaz, 50,100,950,460,null);
			FontPersonalizado.dibujarTexto(g2, "Salas disponibles", 350, 25);
			int i = 0;
			FontPersonalizado.dibujarTexto(g2, "Nombre", 110, 135 );
			FontPersonalizado.dibujarTexto(g2, "Actual", 750, 135 );
			for (Entry<String, Integer> entry : window.salas.entrySet()) {
				FontPersonalizado.dibujarTexto(g2, entry.getKey(), 100, 180 + 35 * i);
				FontPersonalizado.dibujarTexto(g2, entry.getValue() + " / 4", 750, 180 + 35 * i);
				i++;
			}
			
			
		} else {
			
			g2.drawImage(ImageAssets.spritePlayersHud, null, 20, 25);
			FontPersonalizado.dibujarTexto(g2,window.userName, 85,54);
			g2.drawImage(ImageAssets.fondoInterfaz, null, 350, 150);
			
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

}
