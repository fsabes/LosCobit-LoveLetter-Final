package panels;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import assets.BotonPersonalizado;
import assets.ImageAssets;
import objetosJuego.FontPersonalizado;
import objetosJuego.Sound;

public class Mensaje extends JDialog {
	public String mensaje = "";
	private String text = "";
	public PanelMensaje panel;

	/**
	 * @param string
	 * 
	 */


	public Mensaje(JFrame parent, boolean modal, String string) {
		super(parent, modal);
		setUndecorated(true);
		pack();
		this.text = string;
		// Se obtienen las dimensiones en pixels de la pantalla.
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		// Se obtienen las dimensiones en pixels de la ventana.
		Dimension ventana = getSize();

		// Una cuenta para situar la ventana en el centro de la pantalla.
		setSize(320 + text.length()*5, 240);
		setTitle("Mensaje");
		setLocationRelativeTo(parent);
		setLayout(null);

		panel = new PanelMensaje();
		panel.setLayout(null);
		panel.setSize(320+ text.length()*5, 240);
		getContentPane().add(panel);

		BotonPersonalizado aceptar = new BotonPersonalizado("OK", 35, 105, 120 + text.length()*2, 180);
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Sound.buttonSound.play();
			}
		});
		panel.add(aceptar);
		panel.repaint();

		setVisible(true);
	}

	public void mostrarMensaje() {
		panel.repaint();
	}

	private static final long serialVersionUID = 1L;

	private class PanelMensaje extends JPanel {
		private static final long serialVersionUID = 91574813372177663L;

		private final int SQUARE = 50;
		private final int WIDTH = SQUARE * 16;
		private final int HEIGHT = SQUARE * 9;

		public PanelMensaje() {

		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			g2.drawImage(ImageAssets.fondoInterfaz, -9, -2, 334 + text.length()*5, 245, null);

			if (text != null)
				FontPersonalizado.dibujarTexto(g2, text, 100 - (text.length()*3), 75);


		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(WIDTH, HEIGHT);
		}
	}

}
