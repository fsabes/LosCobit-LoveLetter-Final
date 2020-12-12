package objetosJuego;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import javax.swing.Timer;

import assets.BotonPersonalizado;
import assets.ImageAssets;
import cartas.Carta;
import metricas.CardDimension;
import panels.DrawPanel;
import panels.Mensaje;
import panels.MenuPrincipalPanel;
import panels.PresentacionPAnel;
import panels.SalasPanel;
import servidor.MensajeStream;

public class Juego extends JFrame implements Runnable {

	public List<Jugador> jugadores;
	public Jugador jugadorActual;

	private static final long serialVersionUID = 1L;
	public DrawPanel drawPanel;
	public MenuPrincipalPanel panelMenu;
	public SalasPanel panelSalas;
	public int nroJugador;
	public int idJuego;
	public String userName = "";
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	public boolean anfitrion = false;
	public boolean showPlayers = false;
	public boolean inGame = false;
	public boolean showCards = false;
	public boolean drawCards = false;
	public boolean canClick = false;
	public String salaName;
	public HashMap<String, Integer> salas;
	Socket socket;
	public int manoJugada;
	public CardDimension[] cardDimension;
	public int totalCards;
	public int cardSelected;
	public int indexDialog = 0;
	Timer timer;
	public Timer timerAnimation;
	public Timer timerDialog;

	public int getRonda() {
		return ronda;
	}

	public void setRonda(int ronda) {
		this.ronda = ronda;
	}

	public void setTurno(GestorTurnos turno) {
		this.turno = turno;
	}

	boolean rondaActiva;
	GestorTurnos turno;
	public int ronda = 1;
	private int simbolosAGanar = 2;
	private PresentacionPAnel panelInicial;

	public Juego() {
		// Inicio de recursos
		salas = new HashMap<String, Integer>();
		ImageAssets.init();
		Sound.initSounds();
		cardDimension = new CardDimension[2];
		cardDimension[1] = new CardDimension(0, 0, 170, 320);
		cardDimension[0] = new CardDimension(0, 0, 170, 320);

		new FontPersonalizado();
		totalCards = 0;
		Sound.menuSoundtrack.play();
		jugadores = new ArrayList<Jugador>();
		panelMenu = new MenuPrincipalPanel(this);
		panelInicial = new PresentacionPAnel(this);
		add(panelInicial);
		panelInicial.iniciarPresentacion();
		// creacion de la ventana
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setFocusable(true);
		setTitle("Love Letter Cobit");
		requestFocusInWindow();

	}

	public void cargarComponentes() {

	}

	public void iniciarJuego(int simbolosCondicion) {

		turno = new GestorTurnos(jugadores, drawPanel, this);
		simbolosAGanar = simbolosCondicion;

		turno.iniciarRonda();
			animacionCargarMazo();
		showCards = true;
		if (anfitrion)
		seguirJugando();

	}

	private void animacionCargarMazo() {
		drawPanel.mostrarDialogoTexto("Se elimino una carta al azar.");

		//// Animacion de cargar mazo
		timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				turno.getMazo().index++;
				drawPanel.repaint();
				if (turno.getMazo().index > turno.getMazo().getMazo().size())
					timer.stop();

			}
		});
		timer.start();

	}

	private void mostrarPuntosTotales() {
		Resultados res = new Resultados(this, true);
		res.init();
		res.agregarJugadores(jugadores);
		res.jugadorGanador(obtenerGanadorPartida());
		res.setVisible(true);

	}

	private Jugador obtenerGanadorPartida() {
		Jugador winner = null;
		int simbolosMax = 0;
		for (Jugador jugador : jugadores) {
			if (jugador.getSimbolosAfectos() > simbolosMax) {
				winner = jugador;
				simbolosMax = jugador.getSimbolosAfectos();
			}
		}
		return winner;
	}

	public int puntosDeGanador() {
		return turno.obtenerGanador().getSimbolosAfectos();
	}

	private void sumarPuntosJugador() {
		turno.obtenerGanador().setSimbolosAfectos(turno.obtenerGanador().getSimbolosAfectos() + 1);

	}

	public void seguirJugando() {

		// Verifico si hay ganador
		if (finPartida()) {
			mostrarPuntosTotales();
			mostrarMenu();

		} else if (estaRondaFinalizada()) {
			/// indica que ya no se pueden mostrar las cartas
			showCards = false;
			sumarPuntosJugador();
			drawPanel.mostrarDialogoTexto(
					"\n\n Termino la ronda " + ronda + " y el ganador fue : " + turno.obtenerGanador().getNombre());
			// Boton para continuar con la ronda
			if (anfitrion) {
				
				BotonPersonalizado nextRound = new BotonPersonalizado("Proxima ronda ", 50, 250, 450, 350);
				nextRound.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						enviarMensajeServidor(new MensajeStream(MensajeStream.REINICIARRONDA, null, null, null, 0, true, null, null));
						reiniciarRonda();
						turno.reiniciarJugadores(jugadores);
						turno.iniciarRonda();
						
						drawPanel.remove(nextRound);
						showCards = true;
						animacionCargarMazo();
						ronda++;
						seguirJugando();
						drawPanel.repaint();
						
					}
				});
				drawPanel.add(nextRound);
			}
			drawPanel.repaint();
		} else {
			jugadorActual = turno.obtenerJugadorActual();
			if (turno.getTurno() == nroJugador)
				drawPanel.mostrarMensaje("Tu turno");
			drawPanel.repaint();
			drawPanel.mostrarDialogoTexto("Turno del jugador : " + jugadorActual.getNombre());

			///// Animacion de tomar carta del mazo
			if (turno.getTurno() == nroJugador)
				animacionTomarCartaMazo();

			jugadorActual.jugar();

		}

	}

	private void animacionTomarCartaMazo() {
		cardDimension[1].offsetY = -(64 - (2 * getTurno().getMazo().getMazo().size()));
		cardDimension[1].offsetX = 500;
		cardDimension[1].width = 75;
		cardDimension[1].height = 130;

		Sound.playCard.play();
		timerAnimation = new Timer(3, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				cardDimension[1].width = -((cardDimension[1].offsetX * 95) / 500) + 172;
				cardDimension[1].height = -((cardDimension[1].offsetX * 193) / 500) + 323;

				if (cardDimension[1].offsetY < 0 && cardDimension[1].offsetX % 5 == 0)
					cardDimension[1].offsetY += 1;
				if (cardDimension[1].offsetX > 0)
					cardDimension[1].offsetX -= Math.max((cardDimension[1].offsetX * 5) / 500, 1);
				drawPanel.repaint();
				if (cardDimension[1].offsetY >= 0 && cardDimension[1].offsetX <= 0) {
					timerAnimation.stop();
					cardDimension[1].offsetY = 0;
					cardDimension[1].offsetX = 0;
					//// Se fija si tiene condesa de antemano , no le permite al jugador clickear la
					//// carta
					if (!jugadorActual.condicionCondesa())
						canClick = true;
				}

			}
		});
		timerAnimation.start();

	}

	public void enviarMensajeServidor(MensajeStream mensaje) {

		try {
			salida.writeObject(mensaje);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void crearSala() {
		try {
			salida.writeObject(
					new MensajeStream(MensajeStream.CREARSALA, this.userName, null, salaName, 0, false, null,null));

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void unirseSala() {
		try {
			salida.writeObject(
					new MensajeStream(MensajeStream.UNIRSESALA, this.userName, null, salaName, 0, false, null,null));

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void mostrarMenu() {
		showPlayers = true;
		inGame = false;
		showCards = false;
		drawCards = false;
		canClick = false;
		ronda = 1;
		jugadores.clear();
		if (anfitrion)
			jugadores.add(new Jugador(userName));
		drawPanel.mostrarCrearSala();
		Sound.buttonSound.play();
		drawPanel.background = ImageAssets.backgroundSala;
	}

	private boolean finPartida() {

		for (Jugador act : jugadores)
			if (act.getSimbolosAfectos() == simbolosAGanar) // CONDICION DE FIN
				return true;

		return false;
	}

	private boolean estaRondaFinalizada() {

		if (!turno.getMazo().tieneCartas())
			return true;
		if (turno.getCantidadJugadores() == 1)
			return true;
		return false;
	}

	public GestorTurnos getTurno() {
		return turno;
	}

	public void reiniciarRonda() {
		for (Jugador jugador : jugadores) {
			jugador.dejarMano();
		}
		if (anfitrion) {
			getTurno().getMazo().recargarCartas();			
		}
		getTurno().setTurno(0);

	}

	public void rendirse() {
		if (canClick) {

			new Mensaje(null, true, "Te has rendido");

			turno.eliminarDeRonda(getTurno().getTurno());
			getTurno().siguiente();
			seguirJugando();

		}

	}

	public void mostrarMesa() {
			(new InterfazMostrarMesa(this, true, jugadores)).mostrarMenu(this.getTurno().getTurno());

	}

	/// Animacion de jugar carta
	public void levantarCarta(int i) {

		Sound.playCard.play();

		// Velocidad de desplazamiento de las cartas

		timerAnimation = new Timer(5, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (i == 0) {
					if (cardDimension[0].offsetX >= -465)
						cardDimension[0].offsetX -= Math.max(((465 - Math.abs(cardDimension[0].offsetX)) * 7) / 465, 1);
					if (cardDimension[0].offsetY >= -185)
						cardDimension[0].offsetY -= 2;

					cardDimension[0].width = 75 + ((Math.abs(465 + cardDimension[0].offsetX) * 95) / 465);
					cardDimension[0].height = 120 + ((Math.abs(465 + cardDimension[0].offsetX) * 203) / 465);

				} else {
					if (cardDimension[1].offsetX >= -265)
						cardDimension[1].offsetX -= Math.max(((265 - Math.abs(cardDimension[1].offsetX)) * 7) / 265, 1);
					if (cardDimension[1].offsetY >= -185)
						cardDimension[1].offsetY -= 2;

					cardDimension[1].width = 75 + ((Math.abs(265 + cardDimension[1].offsetX) * 95) / 265);
					cardDimension[1].height = 120 + ((Math.abs(265 + cardDimension[1].offsetX) * 203) / 265);
				}

				repaint();
				if ((cardDimension[0].offsetY < -185 && cardDimension[0].offsetX < -465)
						|| (cardDimension[1].offsetY <= -185 && cardDimension[1].offsetX < -265)) {
					timerAnimation.stop();
					if (i == 0) {
						cardDimension[0].offsetY = 0;
						cardDimension[0].offsetX = 0;

						cardDimension[0].width = 172;
						cardDimension[0].height = 323;

					} else if (!timerAnimation.isRunning()) {
						cardDimension[1].offsetY = 0;
						cardDimension[1].offsetX = 0;

						cardDimension[1].width = 172;
						cardDimension[1].height = 323;
					}
					jugadorActual.jugarCarta(i);

				}

			}
		});
		timerAnimation.start();

	}

	public boolean conectar(String ip, int puerto) {

		try {
			socket = new Socket(ip, puerto);
			//

			setSalida(new ObjectOutputStream(socket.getOutputStream()));
			setEntrada(new ObjectInputStream(socket.getInputStream()));
		} catch (IOException e) {

			new Mensaje(null, true, "Error al conectar al servidor");
			return false;
		}
		return true;
	}

	public ObjectInputStream getEntrada() {
		return entrada;
	}

	public void setEntrada(ObjectInputStream entrada) {
		this.entrada = entrada;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}

	public void setSalida(ObjectOutputStream salida) {
		this.salida = salida;
	}

	public void desconectar() {
		try {
			entrada.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Se ha desconectado del servidor");

	}

	public void run() {
		
		recibirDatos();

	}

	public void recibirDatos() {

		boolean canRecibe = true;

		while (canRecibe) {
			try {
				MensajeStream mensaje = (MensajeStream) entrada.readObject();

				switch (mensaje.getType()) {
				case MensajeStream.NOTIFICARSALAS:
					int i = 0;
					for (String salas : mensaje.getSalas()) {
						this.salas.put(salas, mensaje.getNumeros().get(i));
						i++;
					}
					panelSalas.repaint();
					break;
				case MensajeStream.SALANODISPONIBLE:
					new Mensaje(null, true, "No existe la sala ingresada.");
					break;

				case MensajeStream.SALADISPONIBLE:
					Sound.buttonSound.play();
					showPlayers = true;
					nroJugador = mensaje.getIndex();
					idJuego = nroJugador;
					panelSalas.removeAll();
					drawPanel = new DrawPanel(this);
					panelSalas.setVisible(false);
					add(drawPanel);
					mostrarMenu();
					remove(panelSalas);
					break;
				case MensajeStream.AGREGARJUGADORSALA:
					this.jugadores.add(new Jugador(mensaje.getMensaje()));
					panelSalas.repaint();
					drawPanel.repaint();
					System.out.println(nroJugador);

					break;
				case MensajeStream.ACTUALIZARJUGADORES:
					this.jugadores.add(new Jugador(mensaje.getUser()));
					System.out.println(nroJugador);
					break;
				case MensajeStream.INICIARPARTIDA:
					Sound.menuSoundtrack.stop();
					Sound.gameSoundtrack.play();
					drawPanel.background = ImageAssets.tablero;
					showPlayers = false;
					inGame = true;
					drawPanel.removeAll();
					drawPanel.repaint();
					drawPanel.cargarInterfaz();
					iniciarJuego(mensaje.getIndex());
					break;
				case MensajeStream.JUGARCARTA:
						Jugador obj = 	jugadores.get(mensaje.getNumeros().get(1));
						obj.cartasDescartadas.add(obj.cartaActual.get(mensaje.getNumeros().get(0)));
						obj.cartaActual.remove(mensaje.getNumeros().get(0));
					getTurno().siguiente();
					seguirJugando();

					break;
				case MensajeStream.ELIMINARJUGADOR:
					getTurno().eliminarDeRondaOnline(mensaje.getIndex());
					break;
				case MensajeStream.ENVIARMAZO:
					
					getTurno().getMazo().getMazo().clear();
					getTurno().getMazo().cargarMazoDe(mensaje.getNumeros());
					
					for (Jugador actualJugador : 	getTurno().getJugadoresActuales()) // se reparte la primera carta
						actualJugador.pedirCarta();
					
					seguirJugando();
					
					drawPanel.repaint();
					break;
				case MensajeStream.REINICIARRONDA:
					
					reiniciarRonda();
					turno.reiniciarJugadores(jugadores);
					//turno.iniciarRonda();					
					showCards = true;
					animacionCargarMazo();
					ronda++;
					drawPanel.repaint();
					
					break;
				}
			} catch (ClassNotFoundException e) {
				 canRecibe = false;
			} catch (IOException e) {
				canRecibe = false;
			}

		}
	}

	public void iniciarPartidaOnline(int simbolos) {

		try {
			salida.writeObject(
					new MensajeStream(MensajeStream.INICIARPARTIDA, this.userName, null, salaName, simbolos, false, null,null));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Carta> cartasEnMano() {

		return jugadores.get(nroJugador).getCartaActual();
	}

}
