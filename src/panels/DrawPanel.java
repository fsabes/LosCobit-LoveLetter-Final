package panels;

import objetosJuego.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;



import javax.swing.JButton;


import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import assets.BotonPersonalizado;
import assets.ImageAssets;
import metricas.CardDimension;
import objetosJuego.FontPersonalizado;
import objetosJuego.Jugador;
import objetosJuego.Sound;
import servidor.MensajeStream;

////////////////// Panel de dibujo
////////////////// ///////////////////////////////////////////////////////////
public class DrawPanel extends JPanel {
	private static final long serialVersionUID = 91574813372177663L;
	private final int WIDTH = 1080;
	private final int HEIGHT = 720;
	private BotonPersonalizado rendirse;
	private Juego window;
	public String textDialog;
	public String textoTotal = "";
	public BufferedImage background;


	public DrawPanel(Juego parent) {
		textDialog = "";
		background = ImageAssets.backgroundMenu;
		window = parent;
		setLayout(null);

		/// Evento click para elejir las cartas

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {
				super.mouseClicked(me);
				Point point = me.getPoint();
				System.out.println("x : " + point.x + " y :" + point.y);
				if (window.showCards && window.canClick) {
					if (point.x > 385 && point.x < (385 + 172) && point.y > 315 && point.y < (315 + 322)) {

							mostrarDialogoTexto("");
							if (window.getTurno().getTurno() == window.nroJugador) {
								window.levantarCarta(0);
								window.manoJugada = 0;

							}

							window.canClick = false;
							window.cardSelected = 0;
							window.cardDimension[0].width = CardDimension.anchoOriginal;
							window.cardDimension[0].height = CardDimension.altoOriginal;
							repaint();

					} else if (point.x > 585 && point.x < (585 + 172) && point.y > 315 && point.y < (315 + 322)) {

							mostrarDialogoTexto("");
							if (window.getTurno().getTurno() == window.nroJugador){
								window.levantarCarta(1);
								window.manoJugada = 1;
							}

							window.canClick = false;
							window.cardSelected = 1;
							repaint();


					}

				}

			}

		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Dialog", Font.BOLD, 24));
		Dimension currentDimension = window.getContentPane().getSize();
		g2.scale(currentDimension.getWidth() / WIDTH, currentDimension.getHeight() / HEIGHT);

		g2.drawImage(background, null, 0, 0);

		if (window.showPlayers == true) {

			g2.drawImage(ImageAssets.hudMenu, null, 0, 0);
			dibujarTexto(g2,"Sala :  " +  window.salaName, 425, 40);
			dibujarTexto(g2, "Lista de jugadores", 243, 274);
			g2.setFont(new Font("Arial", Font.BOLD, 24));
			g2.setColor(Color.WHITE);
			dibujarTexto(g2, "Simbolos Objetivo", 107, 120);
			if (window.jugadores.size() != 0) {
				for (int i = 0; i < window.jugadores.size(); i++) {
					g2.drawImage(ImageAssets.spritePlayersHud, 123, 332 + i * 75, 489, 40, null);
					dibujarTexto(g2, window.jugadores.get(i).getNombre(), 280, 343 + i * 75); 
					
				}
			}
		} else if (window.inGame && window.getTurno() != null) {
			// Interfaz de ronda
			g2.drawImage(ImageAssets.hudText, null, 0, 645);
			g2.drawImage(ImageAssets.hudRound, null, 250, 5);
			dibujarTexto(g2, "Ronda " + window.ronda, 450, 23);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Dialog", Font.BOLD, 24));
			dibujarTexto(g2, textDialog, 35, 670);
			if ( window.getTurno().getMazo() != null)
				window.getTurno().getMazo().paint(g2);
			if (window.jugadorActual != null)
				window.jugadorActual.paint(g2);

			if (window.showCards) {
				
				// Dibuja el descarte del jugador
				if (window.jugadores.get(window.nroJugador).getCartasDescartadas().size() > 0)
					g2.drawImage(window.jugadores.get(window.nroJugador).descartedCard, 850, 500, 75, 120, null);
				// Dibuja la segunda carta
				if (window.cartasEnMano().size() > 1) {
					g2.drawImage(window.cartasEnMano().get(1).imagenCarta(),
							585 - window.cardDimension[1].offsetX, 315 - window.cardDimension[1].offsetY,
							window.cardDimension[1].width, window.cardDimension[1].height, null);
				}
				// Dibuja la primera carta
				if (window.cartasEnMano().size()>0)
				g2.drawImage(window.cartasEnMano().get(0).imagenCarta(),
						385 - window.cardDimension[0].offsetX, 315 - window.cardDimension[0].offsetY,
						window.cardDimension[0].width, window.cardDimension[0].height, null);

				// interfaz del tiempo
				dibujarTexto(g2, "Tiempo ", 460, 85);
			
				
			}
			if (window.getTurno() != null)
				window.getTurno().paint(g2);

		}
	}

	public void mostrarMensaje(String text) {
		new Mensaje(window, true, text);

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	public void mostrarDialogoTexto(String text) {
		Sound.playCard.play();

		if (window.timerDialog != null && window.timerDialog.isRunning()) {
			window.timerDialog.stop();
		}
		textDialog = "";
		textoTotal = text;
		window.indexDialog = 0;
		window.timerDialog = new Timer(55, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				textDialog = textoTotal.substring(0, window.indexDialog);
				window.indexDialog++;
				repaint();
				if (window.indexDialog > textoTotal.length()) {
					window.timerDialog.stop();
					window.indexDialog = 0;
					textoTotal = "";
				}

			}
		});
		window.timerDialog.start();

	}

	public void mostrarCrearSala() {

		removeAll();

		BotonPersonalizado botonack = new BotonPersonalizado("Atras", 75, 200, 753, 150);
		add(botonack);
		
		if (window.anfitrion==true) {
			JTextField simbolos = new JTextField("2");
			simbolos.setBounds(208, 170, 50, 50);
			simbolos.setHorizontalAlignment(SwingConstants.CENTER);
			simbolos.setEditable(false);
			add(simbolos);

			JButton botonAgregate = new JButton("+");
			botonAgregate.setBounds(258, 170, 50, 50);
			botonAgregate.setForeground(Color.WHITE);
			botonAgregate.setBackground(Color.BLACK);
			botonAgregate.setFont(new Font("Dialog", Font.BOLD, 22));
			botonAgregate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Sound.buttonSound.play();
					switch (simbolos.getText()) {
					case "2":
						simbolos.setText("3");
						break;
					case "3":
						simbolos.setText("4");
						break;
					case "4":
						simbolos.setText("5");
						break;
					}
				}
			});
			add(botonAgregate);

			JButton botonDecrementate = new JButton("-");
			botonDecrementate.setBounds(158, 170, 50, 50);
			botonDecrementate.setForeground(Color.WHITE);
			botonDecrementate.setBackground(Color.BLACK);
			botonDecrementate.setFont(new Font("Dialog", Font.BOLD, 22));
			botonDecrementate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Sound.buttonSound.play();
					switch (simbolos.getText()) {
					case "3":
						simbolos.setText("2");
						break;
					case "4":
						simbolos.setText("3");
						break;
					case "5":
						simbolos.setText("4");
						break;
					}
				}
			});
			add(botonDecrementate);
			
			BotonPersonalizado boton2 = new BotonPersonalizado("Empezar Partida", 75, 200, 753, 575);
			boton2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Sound.buttonSound.play();
					try {
						if (window.jugadores.size() < 2) {
							mostrarMensaje("Debe haber mas jugadores");
						} else {
							Sound.menuSoundtrack.stop();
							Sound.gameSoundtrack.play();
							background = ImageAssets.tablero;
							window.showPlayers = false;
							window.inGame = true;
							removeAll();
							repaint();
							cargarInterfaz();
							window.iniciarPartidaOnline(Integer.parseInt(simbolos.getText()));
							window.iniciarJuego(Integer.parseInt(simbolos.getText()));
						}
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			});
			add(boton2);
			
			
		}
		repaint();
	}

	public void cargarInterfaz() {
		this.removeAll();
		rendirse = new BotonPersonalizado("Rendirse", 55, 250, 5, 150);
		rendirse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Sound.buttonSound.play();
				window.rendirse();
			}
		});
		add(rendirse);

		rendirse = new BotonPersonalizado("Mostrar Mesa", 55, 250, 5, 230);
		rendirse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Sound.buttonSound.play();
				window.mostrarMesa();
			}
		});
		add(rendirse);

		repaint();
	}

	public void dibujarTexto(Graphics2D g, String string, int x, int y) {
		String espacio = " ";
		for (int i = 0; i < string.length(); i++) {
			g.drawImage(FontPersonalizado.font.get(Math.max(string.codePointAt(i) - espacio.codePointAt(0), 0)), null,
					x + 15 * i, y);
		}

	}

}