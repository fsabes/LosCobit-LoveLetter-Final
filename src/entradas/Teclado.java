package entradas;

import java.util.Scanner;

public class Teclado {

	private static int valorIngresado;
	private static Scanner leer;

	public static int pedirValorTeclado() {
		valorIngresado = 0;

		do {
			System.out.print("Seleccione Carta:");
			leer = new Scanner(System.in);
			valorIngresado = leer.nextInt();
		} while (!esValorCorrecto(1, 2));
		return valorIngresado - 1;
	}

	public static int pedirValorTecladoEntre(int valorMin, int valorMax) {
		valorIngresado = 0;

		do {
			leer = new Scanner(System.in);
			System.out.print("opcion:");
			valorIngresado = leer.nextInt();
		} while (!esValorCorrecto(valorMin, valorMax));
		return valorIngresado;
	}

	private static boolean esValorCorrecto(int valorMin, int valorMax) {
		if (valorIngresado >= valorMin && valorIngresado <= valorMax)
			return true;
		System.out.println("valorErroneo");
		return false;
	}

	public static String ingresarNombre() { // cuando se quiera ingresar los nombre por teclado

		System.out.print("ingreseNombre:");
		leer = new Scanner(System.in);
		String nombre = leer.nextLine();
		return nombre;
	}

}
