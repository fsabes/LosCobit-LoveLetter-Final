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

public class InterfazEsperandoRespuesta extends JDialog {

	/**
	 * 
	 */


	private static final long serialVersionUID = 1L;

	private PanelChooser panel;

	public InterfazEsperandoRespuesta(JFrame parent, boolean modal) {
		super(parent, modal);
		setUndecorated(true);
		pack();

		// Se obtienen las dimensiones en pixels de la pantalla.
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		// Se obtienen las dimensiones en pixels de la ventana.
		Dimension ventana = getSize();

		// Una cuenta para situar la ventana en el centro de la pantalla.
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);

		setSize(640, 480);
		setTitle("Elija oponente");
		setLocationRelativeTo(null);
		setLayout(null);
		panel = new PanelChooser();
		panel.setLayout(null);
		panel.setSize(640, 480);
		panel.setBackground(new Color(154, 24, 81));
		getContentPane().add(panel);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		panel.repaint();
	}

	public void agregarBotones() {

		panel.repaint();

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

			FontPersonalizado.dibujarTexto(g2, "Esperando Respuesta", 197, 35);

		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(WIDTH, HEIGHT);
		}
	}

}
