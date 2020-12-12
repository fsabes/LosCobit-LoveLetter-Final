package objetosJuego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import panels.Mensaje;

public class InterfazNameSala extends JDialog {

	/**
	 * 
	 */
	private BotonPersonalizado botonEleccion;
	private static final long serialVersionUID = 1L;
	private BufferedImage spriteCard;
	DrawPanel panel;
	public String user;
	
	public InterfazNameSala(JFrame parent, boolean modal) {
		super(parent, modal);
		setUndecorated(true);
		pack();
		// Se obtienen las dimensiones en pixels de la pantalla.
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

		// Se obtienen las dimensiones en pixels de la ventana.
		Dimension ventana = getSize();

		// Una cuenta para situar la ventana en el centro de la pantalla.
		setSize(640, 480);
		setLayout(null);
		panel = new DrawPanel();
		panel.setLayout(null);
		panel.setSize(640, 480);
		getContentPane().add(panel);
		panel.setBackground(new Color(154, 24, 81));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(parent);
		JTextField userText = new JTextField("");
		userText.setBackground(Color.BLACK);
		userText.setFont(new Font("Dialog", Font.BOLD, 22));
		userText.setForeground(Color.WHITE);
		userText.setLocation(110,230);
		userText.setSize(400, 50);
		panel.add(userText);
		
		botonEleccion = new BotonPersonalizado("Aceptar",45,250,180,340);
		botonEleccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!userText.getText().isEmpty()) {
					setVisible(false);
					user = userText.getText();
				}
				else
					new Mensaje(parent, true, "El nombre no debe estar vacio.");
			}
		});
		panel.add(botonEleccion);

		panel.repaint();
		
		
	}

	public String mostrarVentana(){

		panel.repaint();
		setVisible(true);
		
		return user;
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
			FontPersonalizado.dibujarTexto(g2, "Elija nombre de Sala", 130, 160);


		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(WIDTH, HEIGHT);
		}
	}
}
