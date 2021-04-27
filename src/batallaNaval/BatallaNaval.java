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

        inicializacion(mapaUsuario, mapaOrdenador);
        inicializaMapaAux(mapaAuxUsuario);

        //Mientras queden barcos a flote
        while (!juegoTerminado) {

            System.out.println("MAPA DEL USUARIO");
            imprimirMapa(mapaUsuario);

            System.out.printf("PUNTOS RESTANTES DEL JUGADOR: %d\n", puntosUsuario);
            System.out.println("TURNO DEL JUGADOR");

            tiroCorrecto = false;
            while (!tiroCorrecto) {
                tiro = pedirCasilla();
                //Verificamos si el tiro es correcto o no
                if (tiro[0] != -1 && tiro[1] != -1) {
                    //Puede ser INCORRECTO en ese caso devolvemos -1
                    tiroCorrecto = evaluarTiro(mapaOrdenador, tiro);
                    if (!tiroCorrecto)
                        System.out.println("TIRO INCORRECTO");
                } else {
                    System.out.println("TIRO INCORRECTO");
                }
                //De no serlo, el jugador debe volver a tirar

            }

            int puntosOrdenadorAnterior = puntosOrdenador;
            puntosOrdenador = actualizarMapa(mapaOrdenador, tiro, puntosOrdenador);

            char tipoTiro = (puntosOrdenadorAnterior - puntosOrdenador) > 0 ? TOCADO : AGUA;
            actualizarMapaRegistro(mapaAuxUsuario, tiro, tipoTiro);
            System.out.println("\nREGISTRO DEL MAPA DEL ORDENADOR");
            imprimirMapa(mapaAuxUsuario);

            juegoTerminado = (puntosOrdenador == 0);


            //Si no ha ganado el jugador, le toca a la máquina
            if (!juegoTerminado) {

                System.out.printf("PUNTOS RESTANTES DEL ORDENADOR: %d\n\n", puntosOrdenador);
                System.out.println("TURNO DEL ORDENADOR");
                tiroCorrecto = false;
                while (!tiroCorrecto) {
                    tiro = generaDisparoAleatorio();
                    tiroCorrecto = evaluarTiro(mapaUsuario, tiro);
                }
            }
            puntosUsuario = actualizarMapa(mapaUsuario, tiro, puntosUsuario);

            //El juego termina si el número de puntos llega a 0
            juegoTerminado = (puntosUsuario == 0);

        }
        if (puntosOrdenador == 0) {
            System.out.println("EL VENCEDOR HA SIDO EL JUGADOR");
        } else
            System.out.println("EL VENCEDOR HA SIDO EL ORDENADOR");

        sc.close();

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
        int[] barcos = {5, 5, 3, 3, 3, 1, 1, 1, 1, 1};

        char[] direccion = {'V', 'H'};

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

    private static void inicializaMapaAux(char[][] mapa) {
        // Inicializamos el mapa entero a AGUA_NO_TOCADO
        for (int i = 0; i < TAMANIO; i++)
            for (int j = 0; j < TAMANIO; j++)
                mapa[i][j] = AGUA_NO_TOCADO;

    }

    public static void imprimirMapa(char[][] mapa) {

        char[] letras = new char[TAMANIO];
        for (int i = 0; i < TAMANIO; i++)
            letras[i] = (char) ('A' + i);

        System.out.print("    ");
        for (int i = 0; i < TAMANIO; i++) {
            System.out.print("[" + i + "] ");
        }

        System.out.println("");
        for (int i = 0; i < TAMANIO; i++) {
            System.out.print("[" + letras[i] + "]  ");
            for (int j = 0; j < TAMANIO; j++) {
                System.out.print(mapa[i][j] + "   ");
            }
            System.out.println("");
        }
    }


    private static int[] pedirCasilla() {

        System.out.println("Introduzca la casilla (por ejemplo B4): ");
        String linea = sc.nextLine();
        //Pasamos la cadena a mayúsculas
        linea = linea.toUpperCase();
        int[] t;


        // Comprobamos que lo introducido por el usaurio es correcto mediante una expresión regular
        if (linea.matches("^[A-Z][0-9]*$")) {

            char letra = linea.charAt(0);
            int fila = Character.getNumericValue(letra) - Character.getNumericValue('A');
            int columna = Integer.parseInt(linea.substring(1, linea.length()));
            if (fila >= 0 && fila < TAMANIO && columna >= 0 && columna <= TAMANIO) {
                t = new int[]{fila, columna};
            } else //En otro caso, devolvemos -1, para que vuelva a solicitar el tiro
                t = new int[]{-1, -1};
        } else
            t = new int[]{-1, -1};


        return t;

    }

    public static boolean evaluarTiro(char[][] mapa, int[] t) {
        int fila = t[0];
        int columna = t[1];
        return mapa[fila][columna] == AGUA_NO_TOCADO || (mapa[fila][columna] >= '1' && mapa[fila][columna] <= '5');

    }

    private static int actualizarMapa(char[][] mapa, int[] t, int puntos) {
        int fila = t[0];
        int columna = t[1];

        if (mapa[fila][columna] == AGUA_NO_TOCADO) {
            mapa[fila][columna] = AGUA;
            System.out.println("AGUA");
        } else {
            mapa[fila][columna] = TOCADO;
            System.out.println("HAS ALCANZADO A ALGÚN BARCO");
            --puntos;
        }

        return puntos;

    }

    private static void actualizarMapaRegistro(char[][] mapa, int[] t, char valor) {
        int fila = t[0];
        int columna = t[1];

        mapa[fila][columna] = valor;
    }

    private static int numeroAleatorio() {
        Random r = new Random(System.currentTimeMillis());
        return r.nextInt(TAMANIO);
    }

    private static int[] generaDisparoAleatorio() {
        return new int[] {aleatorio(), aleatorio()};
    }


}
