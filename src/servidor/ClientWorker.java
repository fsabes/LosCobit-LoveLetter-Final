package servidor;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Date;

public class ClientWorker {

	private String nombre;
	private ObjectOutputStream salida;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}

	public void setSalida(ObjectOutputStream salida) {
		this.salida = salida;
	}



	public ClientWorker(String nombre,ObjectOutputStream salida) {

		this.nombre = nombre;
		this.salida = salida;
	}
	
	
}
