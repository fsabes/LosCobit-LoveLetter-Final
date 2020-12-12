package objetosJuego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import assets.ImageAssets;
import cartas.*;
import estados.Estado;
import estados.Normal;
import servidor.MensajeStream;


public class Jugador {

	// Constantes
	public static int PRIMERCARTA = 0;
	public static int SEGUNDACARTA = 1;
	private BufferedImage spritePlayersHud;
	public BufferedImage descartedCard;
	private String nombre;
	private int idJugador;
	private int simbolosAfectos;

	private GestorTurnos observado;
	private List<Integer> enemigosObjetivos;
	List<Carta> cartaActual;
	public boolean choosing = false;
	private Timer timerAnimation;
	private int indexLoop = 0;
	List<Carta> cartasDescartadas;
	Juego juego;
	/// Estado del jugador
	private Estado estado = new Normal();

	public Jugador(String nombre) {
		try {
			spritePlayersHud = ImageIO.read(new File("Backgrounds/hudPlayers.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setNombre(nombre);
		setSimbolosAfectos(0);
		cartaActual = new ArrayList<Carta>();
		cartasDescartadas = new ArrayList<Carta>();
		enemigosObjetivos = new ArrayList<Integer>();
	}

	public void jugar() {
		estado = new Normal();
		pedirCarta();

	}

	private void removerCondesa() {

		for (int i = 0; i < cartaActual.size(); i++) {
			if (cartaActual.get(i).getFuerza() == 7)
				cartasDescartadas.add(cartaActual.remove(i));

		}

	}

	public void paint(Graphics2D g) {

		g.drawImage(ImageAssets.playersRound, null, 10, 575);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 32));
		
		FontPersonalizado.dibujarTexto(g, getObservado().getVentana().jugadores.get( getObservado().getVentana().nroJugador).getNombre(), 75, 600);



	}



	public boolean condicionCondesa() {
		if (tieneCondesa() && tieneReyOPrincipe())
			return true;
		return false;
	}

	private boolean tieneCondesa() {
		for (Carta carta : cartaActual) {
			if (carta.getFuerza() == 7)
				return true;
		}
		return false;
	}

	public List<Carta> getCartaActual() {
		return cartaActual;
	}

	public void setCartaActual(List<Carta> cartaActual) {
		this.cartaActual = cartaActual;
	}

	public List<Carta> getCartasDescartadas() {
		return cartasDescartadas;
	}

	public void setCartasDescartadas(List<Carta> cartasDescartadas) {
		this.cartasDescartadas = cartasDescartadas;
	}



	public void pedirCarta() {
		observado.getMazo().index--;
		cartaActual.add(observado.getMazo().extraerCarta());
		if (condicionCondesa()&& getObservado().getTurno() == getObservado().getVentana().idJuego) {
			indexLoop = 0;

			getObservado().getInterfaz()
					.mostrarDialogoTexto("Tienes condesa y el Principe/Rey , se descartara la carta condesa.");

			/// Animacion para mostrar el mensaje de condesa
			timerAnimation = new Timer(100, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					indexLoop++;
					if (indexLoop > 50) {
						timerAnimation.stop();
						// Continua el siguiente turno
						removerCondesa();
						finTurno();
					}

				}
			});
			timerAnimation.start();

		}
	}


	public void otorgarleCarta(Carta nueva) {
		cartaActual.add(nueva);
	}

	public void jugarCarta(int numCarta){

		Carta cartaAdescartar = cartaActual.get(numCarta);
		cartasDescartadas.add(cartaActual.remove(numCarta));
		descartedCard = cartasDescartadas.get(cartasDescartadas.size() - 1).imagenCarta();
		cartaAdescartar.activarEfecto(this);

	}

	public Carta elejirCartaGuardia() {
		int index = (int) (Math.random() * 6);
		Carta[] cartas = new Carta[8];
		cartas[0] = new CartaSacerdote();
		cartas[1] = new CartaBaron();
		cartas[2] = new CartaMucama();
		cartas[3] = new CartaPrincipe();
		cartas[4] = new CartaRey();
		cartas[5] = new CartaCondesa();
		cartas[6] = new CartaPrincesa();

		return cartas[index];
	}

	public int elejirJugadorObjetivo(boolean canSelf) {
		return (new InterfazEleccionOponente(getObservado().getVentana(), true,
				getObservado().getJugadoresActuales())).elejirOponente(getIdJugador(),canSelf);
	}

	public int minimaFuerza() {
		int min = 20;
		for (Carta naipe : cartaActual) {
			if (naipe.getFuerza() < min)
				min = naipe.getFuerza();

		}
		return min;
	}

	public void descartarCarta() {

	}

	public void AgregarleCarta() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getSimbolosAfectos() {
		return simbolosAfectos;
	}

	public void setSimbolosAfectos(int simbolosAfectos) {
		this.simbolosAfectos = simbolosAfectos;
	}


	public void protegerse() {
		this.estado = estado.protegerse();
	}
	
	public void vaciarMano() {
		for (Carta carta : cartaActual) {
			cartasDescartadas.add(carta);
		}
		cartaActual.clear();

	}

	public boolean tienePrincesa() {
		for (Carta carta : cartaActual) {
			if (carta.getFuerza() == 8)
				return true;
		}
		return false;
	}

	public boolean tieneReyOPrincipe() {
		for (Carta carta : cartaActual) {
			if (carta.getFuerza() == 6 || carta.getFuerza() == 5)
				return true;
		}
		return false;
	}

	public void dejarMano() {
		this.getCartaActual().clear();
		this.getCartasDescartadas().clear();

	}

	public GestorTurnos getObservado() {
		return observado;
	}

	public void setObservado(GestorTurnos observado) {
		this.observado = observado;
	}

	public List<Integer> getEnemigosObjetivos() {
		return enemigosObjetivos;
	}

	public void setEnemigosObjetivos(List<Integer> enemigosObjetivos) {
		this.enemigosObjetivos = enemigosObjetivos;
	}

	public int getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}

	public void escribirTexto(String text) {
		getObservado().getInterfaz().mostrarDialogoTexto(text);
	}
	public void finTurno() {
		ArrayList<Integer> lista= new ArrayList<Integer>();
		lista.add(observado.getVentana().manoJugada);
		lista.add(observado.getVentana().nroJugador);
		getObservado().getVentana().enviarMensajeServidor(new MensajeStream(MensajeStream.JUGARCARTA, null, null, null, 0,false,lista,null));
		getObservado().siguiente();
		getObservado().getVentana().seguirJugando();
	}
	public void mostrarCartas() {
		try {
			(new InterfazMostrarCartas(getObservado().getVentana(), true)).mostrarCartas(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public boolean estaProtegido() {
		return estado.estaProtegido();
	}
}
