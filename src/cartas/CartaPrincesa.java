package cartas;

import assets.ImageAssets;
import objetosJuego.Jugador;

public class CartaPrincesa extends Carta {

	public CartaPrincesa() {
		this.fuerza = 8;
		this.nombre = "Princesa";
		this.cantidad = 1;
		imageCard =ImageAssets.cartaPrincesa;

	}

	@Override
	public void activarEfecto(Jugador player) {
		player.escribirTexto(player.getNombre() +" jugo la carta Princesa.");
		player.getObservado().eliminarDeRonda(player.getIdJugador());

		
		player.finTurno();
	}
}
