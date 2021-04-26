package batallaNaval;

import java.util.Random;
import java.util.Scanner;

public class BatallaNaval {

	final static char AGUA_NO_TOCADO = '.';
	final static char AGUA = 'A';
	final static char TOCADO = 'X';

	final static int TAMANIO = 10;

	static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);

		char[][] mapaUsuario = new char[TAMANIO][TAMANIO];
		char[][] mapaOrdenador = new char[TAMANIO][TAMANIO];

		char[][] mapaAuxUsuario = new char[TAMANIO][TAMANIO];

		int puntosUsuario = 24;
		int puntosOrdenador = 24;

		boolean juegoTerminado = false;
		boolean tiroCorrecto = false;

		int[] tiro = new int[2];

	}

	public static void inicializacion(char[][] m1, char[][] m2) {
		inicializaMapa(m1);
		inicializaMapa(m2);
	}

	private static void inicializaMapa(char[][] mapa) {
		for (int i = 0; i < TAMANIO; i++)
			for (int j = 0; j < TAMANIO; j++)
				mapa[i][j] = AGUA_NO_TOCADO;

		// 2 portaaviones (5 casillas)
		// 3 buques (3 casillas)
		// 5 lanchas (1 casilla)
		int[] barcos = { 5, 5, 3, 3, 3, 1, 1, 1, 1, 1 };

		// Posible dirección de colocación del barco
		char[] direccion = { 'V', 'H' };

		for (int b : barcos) {
			boolean colocado = false;
			while (!colocado) {
				int fila = numeroAleatorio();
				int columna = numeroAleatorio();

				char direcc = direccion[numeroAleatorio() % 2];

				if (direcc == 'V') {
					if (fila + b <= (TAMANIO - 1)) {
						boolean otroBarco = false;
						for (int i = fila; (i <= fila + b) && !otroBarco; i++) {
							if (mapa[i][columna] != AGUA_NO_TOCADO)
								otroBarco = true;
						}
						// Si no hay otro barco, lo colocamos
						if (!otroBarco) {
							for (int i = fila; i < fila + b; i++) {
								mapa[i][columna] = Integer.toString(b).charAt(0);
							}
							colocado = true;
						} else { // direcc == 'H'
							if (columna + b <= (TAMANIO - 1)) {
								// comprobamos que no hay otro barco que se solape
								boolean otro = false;
								for (int j = columna; (j <= columna + b) && !otro; j++) {
									if (mapa[fila][j] != AGUA_NO_TOCADO)
										otro = true;
								}
								// Si no hay otro barco, lo colocamos
								if (!otro) {
									for (int j = columna; j < columna + b; j++) {
										mapa[fila][j] = Integer.toString(b).charAt(0);
									}
									colocado = true;
								}
							}
						}
					}
				}
			}
		}

	}

	private static int numeroAleatorio() {
		Random r = new Random(System.currentTimeMillis());
		return r.nextInt(TAMANIO);
	}

}
