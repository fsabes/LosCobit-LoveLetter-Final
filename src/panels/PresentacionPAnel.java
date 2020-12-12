package panels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import assets.ImageAssets;

import objetosJuego.Juego;

public class PresentacionPAnel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 1080;
	private final int HEIGHT = 720;
	private Juego window;
	public BufferedImage background;
	public boolean showSalas = false;
	public BufferedImage[] imagenes;
	private float alpha = 0;
	private float delta = 0.01f;
	private int indexImage;

	public Timer timerAnimation;

	public PresentacionPAnel(Juego parent) {
		indexImage = 0;
		setBackground(Color.black);

		try {
			imagenes = new BufferedImage[4];
			imagenes[0] = ImageIO.read(new File("Backgrounds/inicioJava.png"));
			imagenes[1] = ImageIO.read(new File("Backgrounds/fondocobit.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		background = ImageAssets.backgroundSala;

		window = parent;
		setLayout(null);
		
	
		

	}

	public void iniciarPresentacion() {
		
		window.addKeyListener(new KeyAdapter() 
	    {
	        public void keyPressed(KeyEvent evt)
	        {
	            if(evt.getKeyCode() == KeyEvent.VK_ENTER)
	            {
					removeAll();
					window.panelMenu = new MenuPrincipalPanel(window);
					setVisible(false);
					window.add(window.panelMenu);
					
	            }
	        }
	    });
		
		timerAnimation = new Timer(40, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alpha += delta;
				if (alpha > 1.0) {
					alpha = 1.0f;
					delta *= -1.0f;
				} else if (alpha < 0.0) {
					alpha = 0.0f;
					delta *= -1.0f;
					indexImage++;
					if (indexImage>1) {						
						timerAnimation.stop();
						removeAll();
						window.panelMenu = new MenuPrincipalPanel(window);
						setVisible(false);
						window.add(window.panelMenu);
					}
				}
				repaint();
			}
		});
		timerAnimation.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Dialog", Font.BOLD, 24));
		g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
		int x = (getWidth() - imagenes[0].getWidth()) / 2;
		int y = (getHeight() - imagenes[0].getHeight()) / 2;
		//g2.drawImage(imagenes[0], x, y, this);
		g2.drawImage(imagenes[indexImage], 320, 150, 410	,410, this);
		g2.dispose();
		//g2.drawImage(background, null, 0, 0);

		if (showSalas) {

		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

}
