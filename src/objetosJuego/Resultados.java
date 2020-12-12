package objetosJuego;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import assets.BotonPersonalizado;
import assets.ImageAssets;


public class Resultados extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanelConFondo drawPanel;
	public ImageIcon imagenFondo;
	public String nombreFondoimg;
	private BufferedImage background;
	private BufferedImage tablaPuntaje;
	private BufferedImage tablaGanador;
	private List<Jugador> jugadores;
	private String ganador;

	public Resultados(JFrame parent, boolean modal){
		super(parent, modal);
		pack();
		jugadores = new ArrayList<Jugador>();
		setBounds(100, 100, 1080, 720);
		setLocationRelativeTo(null);
	}

	private class JPanelConFondo extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public JPanelConFondo() {
			setBorder(new EmptyBorder(5, 5, 5, 5));
			setLayout(null);

		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			//Dimension currentDimension = getContentPane().getSize();
			//g2.scale(currentDimension.getWidth() / WIDTH, currentDimension.getHeight() / HEIGHT);

			g2.drawImage(background, null, 0, 0);
			setOpaque(false);
			tablaPuntaje = ImageAssets.tablaPuntaje;
			g2.drawImage(tablaPuntaje, null, 30, 30);
			int posPuntajeJug = 120;
			g2.setFont(new Font("Arial", Font.BOLD, 20));
			g2.setColor(Color.white);
			g2.drawString("PUNTOS DE AFECTO", 75, 65);
			for (int i = 0; i < jugadores.size(); i++) {

				FontPersonalizado.dibujarTexto(g2,
						jugadores.get(i).getNombre() + " -> Simbolos: " + jugadores.get(i).getSimbolosAfectos(), 50,
						posPuntajeJug);
				posPuntajeJug += 60;
			}

			tablaGanador = ImageAssets.tablaGanador;
			g2.drawImage(tablaGanador, null, 265, 500);
			g2.setFont(new Font("Arial", Font.BOLD, 20));
			g2.setColor(Color.white);
			FontPersonalizado.dibujarTexto(g2, ganador + " ha ganado la partida.", 305, 555);

		}

	}

	public void init() {
		background = ImageAssets.fondoPrincesa;
		drawPanel = new JPanelConFondo();
		getContentPane().add(drawPanel);
		setLocationRelativeTo(null);
		setResizable(false);


	}

	public void agregarJugadores(List<Jugador> jugadoresTotales) {
		BotonPersonalizado boton = new BotonPersonalizado("Nuevo juego", 65, 250, 800, 600);
		boton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		drawPanel.add(boton);
		drawPanel.repaint();

		for (Jugador play : jugadoresTotales) {
			jugadores.add(play);
		}
		//repaint();

	}

	public void jugadorGanador(Jugador obtenerGanador) {
		ganador = obtenerGanador.getNombre();

	}

}
