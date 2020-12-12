package objetosJuego;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import assets.ImageAssets;
import cartas.Carta;
import panels.DrawPanel;
import servidor.MensajeStream;

public class GestorTurnos {
	private List<Jugador> jugadoresActuales;
	private int turno;
	public int indexAnimation = 0;
	public boolean canDraw = false;
	private Mazo mazo;
	public Timer timeResponse ;
	public String textDialog = "";
	private boolean continueGame = false;
	private DrawPanel interfaz;
	private Juego ventana;
	public boolean choosing = false;
	public InterfazEsperandoRespuesta respuesta;


	public Mazo getMazo() {
		return mazo;

	}

	public void paint(Graphics2D g) {

		for (int i = 0; i < jugadoresActuales.size(); i++) {
			g.drawImage(ImageAssets.playersRound, null, 850, 10 + 63 * i);
			interfaz.dibujarTexto(g, jugadoresActuales.get(i).getNombre(), 900, 30 + 63 * i);
			interfaz.dibujarTexto(g, String.valueOf(jugadoresActuales.get(i).getSimbolosAfectos()), 1015, 30 + i * 63); 

		}

	}

	public void setMazo(Mazo mazo) {
		this.mazo = mazo;
	}

	// Gestor de turno es el observado
	public GestorTurnos(List<Jugador> jugadores, DrawPanel drawPanel, Juego ventana) {

		this.setInterfaz(drawPanel);
		this.setVentana(ventana);

		int id = 0;
		jugadoresActuales = new ArrayList<Jugador>();
		for (Jugador jugador : jugadores) {

			jugadoresActuales.add(jugador);
			/// Lo agrego a la lista de observados
			jugadoresActuales.get(id).setObservado(this);
			/// seteo el id de cada jugador
			jugadoresActuales.get(id).setIdJugador(id);

			/// aumento el valor del id
			id++;
		}
		this.turno = 0;
		resetIdObjetivosPlayer();

	}

	public boolean finDeRonda() {
		return getJugadoresActuales().size() == 1;
	}

	private void resetIdObjetivosPlayer() {
		/// Agrego el id a los jugadores como objetivos
		int id = 0;
		for (Jugador jugador : jugadoresActuales) {

			for (id = 0; id < jugadoresActuales.size(); id++) {
				if (jugador.getIdJugador() != id)
					jugador.getEnemigosObjetivos().add(id);
			}
		}
	}

	public Jugador obtenerJugadorActual() {
		return getJugadoresActuales().get(turno);
	}

	public Jugador obtenerGanador() {
		return getJugadoresActuales().get(0);
	}

	public void siguiente() {

		turno++;
		if (turno >= getJugadoresActuales().size())
			turno = 0;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public void iniciarRonda() {
		mazo = new Mazo();
		if (getVentana().nroJugador == 0) {
			mazo.cargarMazo();
			mazo.barajar();
			ArrayList<Integer> ns = new ArrayList<Integer>();
			for (Carta fuerza : mazo.mazo) {
				ns.add(fuerza.getFuerza());
			}
			ventana.enviarMensajeServidor(new MensajeStream(MensajeStream.ENVIARMAZO, null, null, null, 0, false, ns,null));

			

			for (Jugador actualJugador : jugadoresActuales) // se reparte la primera carta
				actualJugador.pedirCarta();
		}

		


	}
	
	
	public MensajeStream recibirDatos() {
		MensajeStream mensaje = null;
		try {
			mensaje = (MensajeStream) getVentana().getEntrada().readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mensaje;
	}

	public void eliminarDeRonda(int index) {
		
		if (getVentana().idJuego == index)
			getVentana().drawPanel.mostrarMensaje("Te han eliminado");

		if (getVentana().idJuego < index)
			getVentana().idJuego--;
		if (this.getJugadoresActuales().get(index).getIdJugador() <= turno)
			turno--;
		if (turno < 0)
			turno = jugadoresActuales.size() - 2;

		jugadoresActuales.remove(index);
		// Actualiza los indices
		int id = 0;
		int internalId = 0;
		/// Agrego el id a los jugadores como objetivos
		for (Jugador jugador : jugadoresActuales) {
			jugador.setIdJugador(internalId);
			internalId++;
			jugador.getEnemigosObjetivos().clear();
			for (id = 0; id < jugadoresActuales.size(); id++) {
				if (jugador.getIdJugador() != id)
					jugador.getEnemigosObjetivos().add(id);
			}
		}
		getVentana().enviarMensajeServidor(new MensajeStream(MensajeStream.ELIMINARJUGADOR, null, null, null, index, true, null, null));
	}

	public void eliminarDeRondaOnline(int index) {
		if (getVentana().idJuego == index)
			getVentana().drawPanel.mostrarMensaje("Te han eliminado");

		if (getVentana().idJuego < index)
			getVentana().idJuego--;
		
		if (this.getJugadoresActuales().get(index).getIdJugador() <= turno)
			turno--;
		if (turno < 0)
			turno = jugadoresActuales.size() - 2;

		jugadoresActuales.remove(index);
		// Actualiza los indices
		int id = 0;
		int internalId = 0;
		/// Agrego el id a los jugadores como objetivos
		for (Jugador jugador : jugadoresActuales) {
			jugador.setIdJugador(internalId);
			internalId++;
			jugador.getEnemigosObjetivos().clear();
			for (id = 0; id < jugadoresActuales.size(); id++) {
				if (jugador.getIdJugador() != id)
					jugador.getEnemigosObjetivos().add(id);
			}
		}
	}
	public void reiniciarJugadores(List<Jugador> jugadoresTotales) {
		jugadoresActuales.clear();
		int id = 0;
		for (Jugador jugador : jugadoresTotales) {
			jugadoresActuales.add(jugador);
			jugadoresActuales.get(id).setIdJugador(id);
			id++;
		}

		resetIdObjetivosPlayer();

	}

	public void listarJugadoresEnRonda() {
		int i = 1;
		for (Jugador jugador : jugadoresActuales) {
			System.out.print(i + " )   " + jugador.getNombre() + ".\n");
			i++;
		}
	}

	public int getCantidadJugadores() {
		return getJugadoresActuales().size();
	}

	public List<Jugador> getJugadoresActuales() {
		return jugadoresActuales;
	}

	public void setJugadoresActuales(List<Jugador> jugadoresActuales) {
		this.jugadoresActuales = jugadoresActuales;

	}

	public boolean isContinueGame() {
		return continueGame;
	}

	public void setContinueGame(boolean continueGame) {
		this.continueGame = continueGame;
	}

	public DrawPanel getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(DrawPanel drawPanel) {
		this.interfaz = drawPanel;
	}

	public Juego getVentana() {
		return ventana;
	}

	public void setVentana(Juego ventana) {
		this.ventana = ventana;
	}

}
