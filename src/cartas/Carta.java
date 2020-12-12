package cartas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.Timer;


import objetosJuego.Jugador;

public abstract class Carta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int fuerza;
	protected String nombre;
	protected int cantidad;
	public BufferedImage imageCard;
	protected Timer timeAnimation;
	protected int indexTimeLine;

	public BufferedImage getImageCard() {
		return imageCard;
	}
	public BufferedImage imagenCarta()  {
		return imageCard;
	}
	public void setPathImage(BufferedImage imageCard) {
		this.imageCard = imageCard;
	}

	public abstract void activarEfecto(Jugador player);

	public int getFuerza() {
		return fuerza;
	}
	public void pausarAnimacion(Jugador player) {
		indexTimeLine = 0;
		timeAnimation = new Timer(15, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.getObservado().getInterfaz().repaint();

				indexTimeLine++;

				if (indexTimeLine > 150) {
					indexTimeLine = 0;
					player.finTurno();
					timeAnimation.stop();
				}

			}
		});
		timeAnimation.start();
		
	}
	public void setFuerza(int fuerza) {
		this.fuerza = fuerza;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return nombre + " " + fuerza + " |";
	}

}
