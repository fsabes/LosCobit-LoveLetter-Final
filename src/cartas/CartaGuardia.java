package cartas;

import assets.ImageAssets;
import objetosJuego.InterfazElejirCarta;
import objetosJuego.Jugador;

public class CartaGuardia extends Carta {
	public CartaGuardia() {
		fuerza = 1;
		nombre = "Guardia";
		cantidad = 5;
		imageCard =ImageAssets.cartaGuardia;
	}

	@Override
	public void activarEfecto(Jugador player) {
		
		
		int choose;
		player.escribirTexto(player.getNombre() + " jugo la carta REY");

		choose = player.elejirJugadorObjetivo(false);

		Jugador objetivo = player.getObservado().getJugadoresActuales().get(choose);
		
		
		Carta cartaObjetivo = (new InterfazElejirCarta(player.getObservado().getVentana(), true)).elejirCarta() ;
		player.escribirTexto(player.getNombre() +" jugo la carta GUARDIA y se elijio a " + objetivo.getNombre());
		
		
		if (objetivo.estaProtegido()) {
			player.escribirTexto("El jugador se encontraba protegido , se cancela el efecto");
			player.finTurno();
			return;
		}

		player.escribirTexto("El jugador nombra la carta " + cartaObjetivo.getNombre());
		boolean findCard = false;

		for (Carta cartaEnemigo : objetivo.getCartaActual()) {
			if (cartaEnemigo.getFuerza() == cartaObjetivo.getFuerza())
				findCard = true;
		}
		if (findCard) {
			player.escribirTexto("El jugador tenia la carta , sera eliminado de la ronda.");
			player.getObservado().eliminarDeRonda(objetivo.getIdJugador());
		} else
			player.escribirTexto("No tenia la carta.");
		
		pausarAnimacion(player);
	}
};
