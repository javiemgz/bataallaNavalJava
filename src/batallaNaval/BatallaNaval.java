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

	private static int numeroAleatorio() {
		Random r = new Random(System.currentTimeMillis());
		return r.nextInt(TAMANIO);
	}

}
