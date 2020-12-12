package cartas;



import assets.ImageAssets;

import objetosJuego.Jugador;

public class CartaSacerdote extends Carta {
	public CartaSacerdote() {
		fuerza = 2;
		nombre = "Sacerdote";
		cantidad = 2;
		imageCard =ImageAssets.cartaSacerdote;
	}

	@Override
	public void activarEfecto(Jugador player) {
		int choose;
		player.escribirTexto(player.getNombre() + " jugo la carta Sacerdote");

		choose = player.elejirJugadorObjetivo(false);

		Jugador objetivo = player.getObservado().getJugadoresActuales().get(choose);
		if (!objetivo.estaProtegido())
			objetivo.mostrarCartas();
		else
			player.escribirTexto(player.getNombre() + " estaba protegido");
		

		player.finTurno();
	}

}
