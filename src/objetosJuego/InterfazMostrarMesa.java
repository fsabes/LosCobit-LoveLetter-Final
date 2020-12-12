package objetosJuego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;

import java.util.List;

import javax.swing.*;

import assets.BotonPersonalizado;
import assets.ImageAssets;

public class InterfazMostrarMesa extends JDialog{


		/**
		 * 
		 */
		private BotonPersonalizado botonEleccion;
		private static final long serialVersionUID = 1L;
		private BufferedImage spriteCard1;
		private BufferedImage spriteCard2;
		private BufferedImage spriteCard3;
		private BufferedImage spriteCard4 ;
		private List<Jugador> jugadores;
		DrawPanel panel;

		public InterfazMostrarMesa(JFrame parent, boolean modal, List<Jugador> jugadores2) {
			super(parent, modal);
			this.jugadores = jugadores2;
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

			botonEleccion = new BotonPersonalizado("Ok",45,250,180,420);
			botonEleccion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					setVisible(false);
				}
			});
			panel.add(botonEleccion);

			panel.repaint();

		}

		public void mostrarMenu(int indexActual){
			if (jugadores.get(0).getCartasDescartadas().size()>0)
			spriteCard1 = jugadores.get(0).getCartasDescartadas().get(  jugadores.get(0).getCartasDescartadas().size()  - 1).imagenCarta();
			if (jugadores.get(1).getCartasDescartadas().size()>0)
			spriteCard2 = jugadores.get(1).getCartasDescartadas().get(  jugadores.get(1).getCartasDescartadas().size()  - 1).imagenCarta();
			else
				spriteCard2 = null;
			if (jugadores.size()>2 && jugadores.get(2).getCartasDescartadas().size()>0)
			spriteCard3 = jugadores.get(2).getCartasDescartadas().get(  jugadores.get(2).getCartasDescartadas().size()  - 1).imagenCarta();
			else
				spriteCard3 = null;
			if (jugadores.size()>3 && jugadores.get(3).getCartasDescartadas().size()>0)
			spriteCard4 = jugadores.get(3).getCartasDescartadas().get(  jugadores.get(3).getCartasDescartadas().size()  - 1).imagenCarta();
			else
				spriteCard4 = null;
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

				g2.drawImage(ImageAssets.backgroundSala, -12, 0, 660, 484, null);
				
				FontPersonalizado.dibujarTexto(g2, "Mesa", 270, 6);

				for (int i = 0; i < jugadores.size(); i++) {
					FontPersonalizado.dibujarTexto(g2, jugadores.get(i).getNombre(), 40 + 140*i, 120);
				}
				g2.drawImage(spriteCard1,  45, 150,65,140,null);
				if (spriteCard2!=null)
				g2.drawImage(spriteCard2,  180, 150,65,140,null);
				if (spriteCard3!=null)
				g2.drawImage(spriteCard3,  310, 150,65,140,null);
				if (spriteCard4!=null)
				g2.drawImage(spriteCard4,  440, 150,65,140,null);
				


				

			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(WIDTH, HEIGHT);
			}
		}
	}

