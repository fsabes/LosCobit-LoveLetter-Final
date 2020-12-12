package cartas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


import javax.swing.Timer;

import assets.ImageAssets;
import objetosJuego.Jugador;

public class CartaRey extends Carta {
	public int offset = 0;

	public CartaRey() {

		fuerza = 6;
		nombre = "Rey";
		cantidad = 1;
		imageCard =ImageAssets.cartaRey;
	}

	@Override
	public void activarEfecto(Jugador player) {
		int choose;
		offset = 150;
		player.escribirTexto(player.getNombre() + " jugo la carta REY");


		choose = player.elejirJugadorObjetivo(false);

		Jugador objetivo = player.getObservado().getJugadoresActuales().get(choose);

		player.escribirTexto("Se intercambiara la mano con el jugador " + objetivo.getNombre());
		if (objetivo.estaProtegido()) {
			player.escribirTexto("El jugador se encontraba protegido , se cancela el efecto");
			player.finTurno();
			return;
		}

		timeAnimation = new Timer(8, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.getObservado().getInterfaz().repaint();
				player.getObservado().getVentana().cardDimension[0].offsetY = (150 - Math.abs(offset));
				offset--;
				if (offset == 0) {
					List<Carta> cartasAux = player.getCartaActual();
					player.setCartaActual(objetivo.getCartaActual());
					objetivo.setCartaActual(cartasAux);
				}
				if (offset <= -150) {
					player.getObservado().getVentana().cardDimension[0].offsetY = 0;
					player.finTurno();
					timeAnimation.stop();
				}

			}
		});
		timeAnimation.start();

	}
	
}
