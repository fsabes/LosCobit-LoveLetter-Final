package estados;

import objetosJuego.Jugador;

public class Protegido extends Estado {
	@Override
	public Estado eliminarDeRonda(Jugador player) {
		return this;
	}

	@Override
	public Estado mostrarCartas(Jugador player) {
		return this;
	}

	@Override
	public Estado protegerse() {
		return new Protegido();
	}
	@Override
	public  boolean estaProtegido() {
		return true;
	}

}
