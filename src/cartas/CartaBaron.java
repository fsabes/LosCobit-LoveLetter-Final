package cartas;

import assets.ImageAssets;
import objetosJuego.Jugador;

public class CartaBaron extends Carta {

	public CartaBaron() {
		fuerza = 3;
		nombre = "Baron";
		cantidad = 2;
		imageCard =ImageAssets.cartaBaron;
	}

	@Override
	public void activarEfecto(Jugador player) {

		int choose;
		/// Mostrando mensaje
		player.escribirTexto(player.getNombre() + " jugo la carta Baron");

		// Activa ventana para elejir oponente
		choose = player.elejirJugadorObjetivo(false);
		// obtiene el jugador objetivo
		Jugador objetivo = player.getObservado().getJugadoresActuales().get(choose);
		
		/// Mostrando mensaje
		player.escribirTexto(player.getNombre() +" Se elijio al jugador " + objetivo.getNombre() + " , el de menor fuerza sera eliminado");

		// Si esta protegido se sale
		if (objetivo.estaProtegido()) {
			player.escribirTexto("El jugador se encontraba protegido , se cancela el efecto");
			player.finTurno();
			return;
		}

		int fuerzaPlayer = player.minimaFuerza();
		int fuerzaEnemigo = objetivo.minimaFuerza();
		
		player.escribirTexto("Fuerza de " + player.getNombre() + " : " + fuerzaPlayer + " y fuerza de "
				+ objetivo.getNombre() + " : " + fuerzaEnemigo);
		if (fuerzaPlayer < fuerzaEnemigo) {
			player.escribirTexto(player.getNombre() + " tiene menos fuerza, sera eliminado.");
			player.getObservado().eliminarDeRonda(player.getIdJugador());
		} else {
			player.escribirTexto(objetivo.getNombre() + " tiene menos fuerza, sera eliminado.");
			player.getObservado().eliminarDeRonda(objetivo.getIdJugador());
		}
		
		pausarAnimacion(player);

	}
}
