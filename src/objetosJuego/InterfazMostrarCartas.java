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
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;

import assets.BotonPersonalizado;
import assets.ImageAssets;

public class InterfazMostrarCartas extends JDialog {

	/**
	 * 
	 */
	private BotonPersonalizado botonEleccion;
	private static final long serialVersionUID = 1L;
	private BufferedImage spriteCard;
	DrawPanel panel;

	public InterfazMostrarCartas(JFrame parent, boolean modal) {
		super(parent, modal);
		setUndecorated(true);
		pack();
		// Se obtienen las dimensiones en pixels de la pantalla.
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		// Se obtienen las dimensiones en pixels de la ventana.
		Dimension ventana = getSize();

		// Una cuenta para situar la ventana en el centro de la pantalla.

		setSize(640, 480);
		setLocationRelativeTo(parent);
		setLayout(null);
		panel = new DrawPanel();
		panel.setLayout(null);
		panel.setSize(640, 480);
		getContentPane().add(panel);
		panel.setBackground(new Color(154, 24, 81));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				JOptionPane.showMessageDialog(null, "Elija un jugador , no sea doble moral.");
			}

		});

		botonEleccion = new BotonPersonalizado("Ya lo vi",45,250,180,365);
		botonEleccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
			}
		});
		panel.add(botonEleccion);

		panel.repaint();

	}

	public void mostrarCartas(Jugador objetivo) throws IOException {

		spriteCard = objetivo.getCartaActual().get(0).getImageCard();
		panel.repaint();
		setVisible(true);

	}

	private class DrawPanel extends JPanel {
		private static final long serialVersionUID = 91574813372177663L;

		private final int SQUARE = 50;
		private final int WIDTH = SQUARE * 16;
		private final int HEIGHT = SQUARE * 9;

		public DrawPanel() {

		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			g2.drawImage(ImageAssets.fondoInterfaz, -12, 0, 660, 484, null);
			
			FontPersonalizado.dibujarTexto(g2, " Cartas del enemigo :", 130, 6);
			g2.drawImage(spriteCard, null, 220, 44);

		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(WIDTH, HEIGHT);
		}
	}
}
