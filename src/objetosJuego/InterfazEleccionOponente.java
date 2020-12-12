package objetosJuego;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.*;


import javax.swing.*;

import assets.BotonPersonalizado;
import assets.ImageAssets;
import panels.Mensaje;

public class InterfazEleccionOponente extends JDialog {

	/**
	 * 
	 */

	private List<Jugador> jugador;
	private BotonPersonalizado botonEleccion;
	private static final long serialVersionUID = 1L;
	private int indexButton = 0;
	private boolean canChooseSelf = false;
	private int actualIndex = 0;
	private PanelChooser panel;

	public InterfazEleccionOponente(JFrame parent, boolean modal, List<Jugador> jugadores) {
		super(parent, modal);
		setUndecorated(true);
		indexButton = 0;
		pack();

		// Se obtienen las dimensiones en pixels de la pantalla.
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		// Se obtienen las dimensiones en pixels de la ventana.
		Dimension ventana = getSize();

		// Una cuenta para situar la ventana en el centro de la pantalla.

		jugador = jugadores;
		setSize(640, 480);
		setTitle("Elija oponente");
		setLocationRelativeTo(parent);
		setLayout(null);
		panel = new PanelChooser();
		panel.setLayout(null);
		panel.setSize(640, 480);
		panel.setBackground(new Color(154, 24, 81));
		getContentPane().add(panel);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				JOptionPane.showMessageDialog(null, "Elija un jugador , no sea doble moral.");
			}

		});
		panel.repaint();
	}

	public void agregarBotones() {
		if (jugador.size() > 0) {
			botonEleccion = new BotonPersonalizado(jugador.get(0).getNombre(), 55, 250, 180, 10 + 80);
			botonEleccion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (actualIndex == 0 && !canChooseSelf)
						new Mensaje(null, true, "No te puedes elejir a ti mismo");
					else {

						indexButton = 0;
						setVisible(false);
					}
				}
			});
			panel.add(botonEleccion);

		}
		if (jugador.size() > 1) {

			botonEleccion = new BotonPersonalizado(jugador.get(1).getNombre(), 55, 250, 180, 10 + 80 * 2);
			botonEleccion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Sound.buttonSound.play();
					if (actualIndex == 1 && !canChooseSelf)
						new Mensaje(null, true, "No te puedes elejir a ti mismo");
					else {

						indexButton = 1;
						setVisible(false);
					}
				}
			});
			panel.add(botonEleccion);
		}
		if (jugador.size() > 2) {

			botonEleccion = new BotonPersonalizado(jugador.get(2).getNombre(), 55, 250, 180, 10 + 80 * 3);
			botonEleccion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Sound.buttonSound.play();
					if (actualIndex == 2 && !canChooseSelf)
						new Mensaje(null, true, "No te puedes elejir a ti mismo");
					else {

						indexButton = 2;
						setVisible(false);
					}
				}
			});
			panel.add(botonEleccion);
		}
		if (jugador.size() > 3) {

			botonEleccion = new BotonPersonalizado(jugador.get(3).getNombre(), 55, 250, 180, 10 + 80 * 4);
			botonEleccion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Sound.buttonSound.play();
					if (actualIndex == 3 && !canChooseSelf)
						new Mensaje(null, true, "No te puedes elejir a ti mismo");
					else {

						indexButton = 3;
						setVisible(false);
					}
				}
			});
			panel.add(botonEleccion);
		}

		panel.repaint();

	}

	public int elejirOponente(int index, boolean canSelf) {
		actualIndex = index;
		canChooseSelf = canSelf;
		agregarBotones();
		setVisible(true);
		panel.repaint();
		return indexButton;

	}

	public class PanelChooser extends JPanel {

		private final int WIDTH = 640;
		private final int HEIGHT = 480;

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			g2.drawImage(ImageAssets.fondoInterfaz, -12, 0, 660, 484, null);

			FontPersonalizado.dibujarTexto(g2, "Elija oponente", 197, 35);

		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(WIDTH, HEIGHT);
		}
	}

}
