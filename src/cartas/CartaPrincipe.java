package cartas;





import assets.ImageAssets;

import objetosJuego.Jugador;

public class CartaPrincipe extends Carta {


	public CartaPrincipe() {

		fuerza = 5;
		nombre = "Principe";
		cantidad = 2;
		imageCard = ImageAssets.cartaPrincipe;
	}

	@Override
	public void activarEfecto(Jugador player) {
		player.escribirTexto(player.getNombre() + " jugo la carta PRINCIPE");
		if (!player.getObservado().getMazo().tieneCartas()) {
			player.escribirTexto("El mazo ya no tiene cartas , se cancela el efecto.");
			pausarAnimacion(player);
			return;
		}
		int choose;
		choose = player.elejirJugadorObjetivo(true);

		Jugador objetivo = player.getObservado().getJugadoresActuales().get(choose);

		if (objetivo.estaProtegido()) {
			player.escribirTexto(
					"El jugador " + objetivo.getNombre() + " se encontraba protegido , se cancela el efecto");
			pausarAnimacion(player);
			return;
		}
		/// Si tiene princesa es eliminado
		if (objetivo.tienePrincesa()) {
			player.escribirTexto("El jugador " + objetivo.getNombre() + " tenia princesa.");
			objetivo.getObservado().eliminarDeRonda(objetivo.getIdJugador());
			pausarAnimacion(player);
			return;
		} else {
			objetivo.vaciarMano();
			objetivo.pedirCarta();
		}

		player.escribirTexto("El jugador " + objetivo.getNombre() + " vacia su mano y saco una carta del mazo");
		pausarAnimacion(player);
	}

}
