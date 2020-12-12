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

import javax.swing.*;

import assets.BotonPersonalizado;
import assets.ImageAssets;
import cartas.Carta;
import cartas.CartaBaron;
import cartas.CartaCondesa;
import cartas.CartaMucama;
import cartas.CartaPrincipe;
import cartas.CartaRey;
import cartas.CartaSacerdote;

public class InterfazElejirCarta extends JDialog {

	/**
	 * 
	 */

	private BotonPersonalizado botonEleccion;
	private static final long serialVersionUID = 1L;

	private Carta cartaElejida;
	PanelCardChooser panel;

	public InterfazElejirCarta(JFrame parent, boolean modal) {
		super(parent, modal);
		setUndecorated(true);
		pack();
		// Se obtienen las dimensiones en pixels de la pantalla.
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		// Se obtienen las dimensiones en pixels de la ventana.
		Dimension ventana = getSize();

		// Una cuenta para situar la ventana en el centro de la pantalla.

		setSize(640, 480);
		setTitle("Elija tipo de carte que tiene el rival.");
		setLocationRelativeTo(parent);
		setLayout(null);
		panel = new PanelCardChooser();
		panel.setLayout(null);
		panel.setSize(640, 480);
		getContentPane().add(panel);
		panel.setBackground(new Color(154, 24, 81));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		int altoY = 44;
		int separator = 12;
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				JOptionPane.showMessageDialog(null, "Elija una carta, no tengas miedo");
			}

		});

		botonEleccion = new BotonPersonalizado("Sacerdote", 44, 250, 190, altoY + separator);

		botonEleccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cartaElejida = new CartaSacerdote();
				setVisible(false);
			}
		});
		separator += 5;
		panel.add(botonEleccion);

		botonEleccion = new BotonPersonalizado("Barón", 44, 250, 190, altoY*2 + separator);
		botonEleccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cartaElejida = new CartaBaron();
				setVisible(false);
			}
		});
		panel.add(botonEleccion);
		separator += 5;

		botonEleccion = new BotonPersonalizado("Mucama", 44, 250, 190,altoY*3 + separator);
		botonEleccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cartaElejida = new CartaMucama();
				setVisible(false);
			}
		});
		panel.add(botonEleccion);
		separator += 5;

		botonEleccion = new BotonPersonalizado("Príncipe", 44, 250, 190, altoY*4 + separator);
		botonEleccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cartaElejida = new CartaPrincipe();
				setVisible(false);
			}
		});
		panel.add(botonEleccion);
		separator += 5;
		botonEleccion = new BotonPersonalizado("Rey", 44, 250, 190,altoY*5 + separator);
		botonEleccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cartaElejida = new CartaRey();
				setVisible(false);
			}
		});
		panel.add(botonEleccion);
		separator += 5;
		botonEleccion = new BotonPersonalizado("Condesa", 44, 250, 190, altoY*6 + separator);
		botonEleccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cartaElejida = new CartaCondesa();
				setVisible(false);
			}
		});
		panel.add(botonEleccion);
		separator += 5;
		botonEleccion = new BotonPersonalizado("Princesa", 44, 250, 190,altoY*7 + separator);
		botonEleccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cartaElejida = new CartaPrincipe();
				setVisible(false);
			}
		});
		panel.add(botonEleccion);

		panel.repaint();

	}

	public Carta elejirCarta() {

		setVisible(true);
		panel.repaint();

		return cartaElejida;
	}

	public class PanelCardChooser extends JPanel {

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

			g2.drawImage(ImageAssets.fondoInterfaz, -11, 0, 660, 484, null);
			
			FontPersonalizado.dibujarTexto(g2, "Elija Carta", 218, 30);

		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(WIDTH, HEIGHT);
		}
	}
}
