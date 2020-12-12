package servidor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cartas.Carta;

public class MensajeStream implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	private ArrayList<String> salas;
	private String mensaje;
	private int index;
	private String user;
	private boolean state;
	private ArrayList<Integer> numeros;
	public final static int CREARSALA = 0;
	public final static int UNIRSESALA = 1;
	public final static int NOTIFICARSALAS = 2;
	public final static int CONSULTARSALA = 3;
	public final static int SALADISPONIBLE = 4;
	public final static int SALANODISPONIBLE = 5;
	public final static int SALALLENA = 6;
	public final static int AGREGARJUGADORSALA = 7;
	public final static int ACTUALIZARJUGADORES = 8;
	public final static int INICIARPARTIDA = 9;
	public final static int JUGARCARTA = 10;
	public final static int ENVIARMAZO = 11;
	public final static int ELIMINARJUGADOR = 12;
	public final static int REINICIARRONDA = 13;
	public List<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}

	private List<Carta> cartas;

	public MensajeStream(int type, String user, ArrayList<String> salas, String mensaje, int index, boolean state,
			ArrayList<Integer> listaNumeros,List<Carta> cartas) {

		this.index = index;
		this.mensaje = mensaje;
		this.user = user;
		this.salas = new ArrayList<String>();
		this.cartas = new ArrayList<Carta>();
		if (salas != null && salas.size() > 0)
			for (String string : salas) {
				this.salas.add(string);
			}
		if (cartas != null && cartas.size() > 0)
			for (Carta cartica : cartas) {
				this.cartas.add(cartica);
			}
		this.numeros = new ArrayList<Integer>();
		if (listaNumeros != null && listaNumeros.size() > 0)
			for (Integer n : listaNumeros) {
				this.numeros.add(n);
			}
		this.state = state;
		this.type = type;

	}

	public ArrayList<Integer> getNumeros() {
		return numeros;
	}

	public void setNumeros(ArrayList<Integer> numeros) {
		this.numeros = numeros;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ArrayList<String> getSalas() {
		return salas;
	}

	public void setSalas(ArrayList<String> salas) {
		this.salas = salas;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
