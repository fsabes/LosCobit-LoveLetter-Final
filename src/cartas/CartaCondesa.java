package cartas;

import assets.ImageAssets;
import objetosJuego.Jugador;

public class CartaCondesa extends Carta {
	public CartaCondesa() {
		fuerza = 7;
		nombre = "Condesa";
		cantidad = 1;
		imageCard =ImageAssets.cartaCondesa;

	}

	@Override
	public void activarEfecto(Jugador player) {
		player.escribirTexto(player.getNombre() +" jugo la carta Condesa, no hace nada.");
		pausarAnimacion(player);
	}

}
