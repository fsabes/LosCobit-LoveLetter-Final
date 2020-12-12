package cartas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.Timer;

import assets.ImageAssets;
import objetosJuego.Jugador;

public class CartaMucama extends Carta {
	public int indexTimeLine = 0;

	public CartaMucama() {
		fuerza = 4;
		nombre = "Mucama";
		cantidad = 2;
		imageCard =ImageAssets.cartaMucama;
	}

	@Override
	public void activarEfecto(Jugador player) {
		indexTimeLine = 0;

		player.escribirTexto(player.getNombre() + " jugo la carta MUCAMA");
		player.protegerse();

		player.escribirTexto("Te encuentras protegido por un turno");

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
}
