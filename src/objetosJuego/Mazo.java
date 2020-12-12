package objetosJuego;

import cartas.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;



public class Mazo {

	List<Carta> mazo;
	int index = 0;
	private BufferedImage backCard;
	private Sound soundCharge ;
	public Mazo() {
		try {
			soundCharge = new Sound("Sounds/cardFan1.wav");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mazo = new ArrayList<Carta>();
		try {
			backCard = ImageIO.read(new File("Cartas/backCard.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarMazoDe(ArrayList<Integer> fuerzas) {
		
		for (Integer integer : fuerzas) {
			
			
			switch(integer) {
			case 1:
				mazo.add(new CartaGuardia());
				break;
			case 2:
				mazo.add(new CartaSacerdote());
				break;
			case 3:
				mazo.add(new CartaBaron());
				break;
			case 4:
				mazo.add(new CartaMucama());
				break;
			case 5:
				mazo.add(new CartaPrincipe());
				break;
			case 6:
				mazo.add(new CartaRey());
				break;
			case 7:
				mazo.add(new CartaCondesa());
				break;
			case 8:
				mazo.add(new CartaPrincesa());
				break;
			}
		}
	}
	public void cargarMazo() {
		soundCharge.play();
		mazo.add(new CartaGuardia());
		mazo.add(new CartaGuardia());
		mazo.add(new CartaGuardia());
		mazo.add(new CartaGuardia());
		mazo.add(new CartaGuardia());

		mazo.add(new CartaSacerdote());
		mazo.add(new CartaSacerdote());
		

		mazo.add(new CartaBaron());
		mazo.add(new CartaBaron());

		mazo.add(new CartaMucama());
		mazo.add(new CartaMucama());

		mazo.add(new CartaPrincipe());
		mazo.add(new CartaPrincipe());


		mazo.add(new CartaRey());

		mazo.add(new CartaCondesa());


		mazo.add(new CartaPrincesa());

		mazo.remove((int) Math.random()*(mazo.size() - 1));
	}

	public boolean mazoVacio() {
		return mazo.size() == 0;
	}
	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		
		
		for (int i = 0; i < index; i++) {
			g2.drawImage(backCard,  85 , 405 - 2 * i,75,130,null);
			
		}
		
	
		
	}

	public void mostrar() { /// en realidad esto es para ejemplo
		for (Carta carta : mazo)
			System.out.println(carta);
	}

	public void barajar() {
		Collections.shuffle(mazo);

	}

	public Carta extraerCarta() {
		// el primer elemento es cero, esto se quita el primero
		/// dado que se quita el primer , el siguiente elemento es el nuevo primero

		return mazo.remove(0);
	}

	public boolean tieneCartas() {
		return !mazo.isEmpty();
	}

	public List<Carta> getMazo() {
		return mazo;
	}

	public void recargarCartas() {
		this.mazo.clear();
		this.cargarMazo();

	}

	public void setMazo(List<Carta> mazo) {
		this.mazo = mazo;
	}

	// public void RepatirTodos(jugador jugadores);
	// public Carta Retirar();
	// public Reiniciar();

}
