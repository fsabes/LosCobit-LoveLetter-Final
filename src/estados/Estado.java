package estados;

import objetosJuego.Jugador;

public abstract class Estado {

	public Estado eliminarDeRonda(Jugador player) {
		return this;
	}

	public Estado mostrarCartas(Jugador player) {
		return this;
	}

	public Estado protegerse() {
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		return obj.getClass().getName().equals(this.getClass().getName());
	}

	public  boolean estaProtegido() {
		return false;
	}

}