package estados;

import objetosJuego.Jugador;

public class Normal extends Estado{

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




	
}
