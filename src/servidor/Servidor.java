package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import objetosJuego.Mazo;

public class Servidor implements Runnable {

	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private Socket socket;

	static HashMap<String, ArrayList<ClientWorker>> salas;

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

	private int port = 0;

	public Servidor(Socket socket) throws IOException {
		entrada = new ObjectInputStream(socket.getInputStream());
		salida = new ObjectOutputStream(socket.getOutputStream());

	}

	public static void main(String[] args) {
		ServerSocket servidor;

		Servidor.salas = new HashMap<String, ArrayList<ClientWorker>>();

		System.out.print("Inicializando servidor... ");
		try {
			servidor = new ServerSocket(10578);

			System.out.println("\t[OK]");
			while (true) {
				Socket socket;
				socket = servidor.accept();
				Servidor server = new Servidor(socket);
				System.out.println("Nueva conexión entrante: " + socket.getInetAddress());
				new Thread(server).start();

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		String salaActual = "";
		String userName = "Vacio";
		boolean canSend = true;
		while (canSend) {
			try {
				MensajeStream mensaje = (MensajeStream) entrada.readObject();

				switch (mensaje.getType()) {
				case MensajeStream.CREARSALA:
					ArrayList<ClientWorker> clientes = new ArrayList<ClientWorker>();
					clientes.add(new ClientWorker(mensaje.getUser(), salida));
					salaActual = mensaje.getMensaje();
					userName = mensaje.getUser();
					salas.put(mensaje.getMensaje(), clientes);
					System.out.println("El usuario " + mensaje.getUser() + " Creo la sala" + mensaje.getMensaje());
					break;
				case MensajeStream.UNIRSESALA:
					ArrayList<Integer> nSalas = new ArrayList<Integer>();
					ArrayList<String> salasNames = new ArrayList<String>();
					userName = mensaje.getUser();
					for (Entry<String, ArrayList<ClientWorker>> entry : salas.entrySet()) {
						salasNames.add(entry.getKey());
						nSalas.add(salas.get(entry.getKey()).size());
					}
					salida.writeObject(
							new MensajeStream(MensajeStream.NOTIFICARSALAS, null, salasNames, null, 0, false, nSalas,null));
					break;
				case MensajeStream.CONSULTARSALA:

					if (salas.containsKey(mensaje.getMensaje())) {
						salaActual = mensaje.getMensaje();
						salida.writeObject(new MensajeStream(MensajeStream.SALADISPONIBLE, null, null, null,
								salas.get(salaActual).size(), false, null,null));
						salas.get(salaActual).add(new ClientWorker(userName, this.salida));
						for (ClientWorker cliente : salas.get(salaActual)) {
							salida.writeObject(new MensajeStream(MensajeStream.ACTUALIZARJUGADORES, cliente.getNombre(),
									null, null, 0, false, null,null));
						}
						for (ClientWorker cliente : salas.get(salaActual)) {
							if (cliente.getSalida() != this.salida)
								cliente.getSalida().writeObject(new MensajeStream(MensajeStream.AGREGARJUGADORSALA,
										null, null, userName, 0, true, null,null));
						}
					} else
						salida.writeObject(
								new MensajeStream(MensajeStream.SALANODISPONIBLE, null, null, null, 0, false, null,null));
					break;
				case MensajeStream.INICIARPARTIDA : 

					for (ClientWorker cliente : salas.get(salaActual)) {
						if (cliente.getSalida() != this.salida)
							cliente.getSalida().writeObject(new MensajeStream(MensajeStream.INICIARPARTIDA,
									null, null, userName, mensaje.getIndex(), true, null,null));

					}
					break;
				case MensajeStream.JUGARCARTA : 
					
					for (ClientWorker cliente : salas.get(salaActual)) {
						if (cliente.getSalida() != this.salida)
							cliente.getSalida().writeObject(new MensajeStream(MensajeStream.JUGARCARTA,
									null, null, userName, mensaje.getIndex(), true, mensaje.getNumeros(),null));
					}
					
					break;
				case MensajeStream.ENVIARMAZO :
					for (ClientWorker cliente : salas.get(salaActual)) {
						if (cliente.getSalida() != this.salida)
							cliente.getSalida().writeObject(new MensajeStream(MensajeStream.ENVIARMAZO,
									null, null, userName, mensaje.getIndex(), true, mensaje.getNumeros(),null));
					}
					break;
				case MensajeStream.ELIMINARJUGADOR:
					for (ClientWorker cliente : salas.get(salaActual)) {
						if (cliente.getSalida() != this.salida)
							cliente.getSalida().writeObject(new MensajeStream(MensajeStream.ELIMINARJUGADOR,
									null, null, null, mensaje.getIndex(), true, null,null));
					}
					
					
					break;
				case MensajeStream.REINICIARRONDA:
					for (ClientWorker cliente : salas.get(salaActual)) {
						if (cliente.getSalida() != this.salida)
							cliente.getSalida().writeObject(new MensajeStream(MensajeStream.REINICIARRONDA,
									null, null, null, mensaje.getIndex(), true, null,null));
					}
					
					
					break;
				}
			} catch (IOException e) {

				canSend = false;
			} catch (ClassNotFoundException e) {

				canSend = false;
			}

		}

	}

}
