package org.gank.triqui.ui.cli;

import java.util.Scanner;

import org.gank.triqui.juego.Marca;
import org.gank.triqui.juego.SuscriptorAlJuego;
import org.gank.triqui.juego.Triqui;

public class TriquiCLI implements SuscriptorAlJuego {
    private Triqui juego;

    public Triqui getJuego() {
        return juego;
    }

    public TriquiCLI(Triqui juego) {
        this.juego = juego;
    }
    public static void main(String[] args) {
        TriquiCLI cli = new TriquiCLI(new Triqui());
        cli.getJuego().inscribirseNotificacionesTurnos(cli);
        boolean continuar = true;

        try (Scanner in = new Scanner(System.in)) {
            imprimirTablero(cli.getJuego());
            while (!cli.getJuego().finJuego() && continuar) {
                jugarTurno(cli.getJuego(), in);
                
                if (cli.getJuego().finJuego()) {
                    continuar = continuarJugando(in);
                    if (continuar) {
                        cli.getJuego().nuevoJuego();
                        imprimirTablero(cli.getJuego());
                    }
                }
            }
        }
    }

    private static void jugarTurno(Triqui juego, Scanner in) {
        String resp = "";

        in.useDelimiter(System.lineSeparator());
        do {
            System.out.println("Escoger posición dónde marcar: [x] [y]");
            resp = in.next();
        } while (!resp.matches("[1-3] [1-3]"));

        String[] coord = resp.split(" ");
        juego.marcar(Integer.valueOf(coord[0]) - 1, Integer.valueOf(coord[1]) - 1);
    }

    private static void imprimirTablero(Triqui juego) {
        for (int i = 0; i < Triqui.DIMENSION_TABLERO; i++) {
            for (int j = 0; j < Triqui.DIMENSION_TABLERO; j++) {
                String casilla = juego.obtenerCasilla(i, j).toString();
                casilla = "VACIA".equals(casilla) ? "_" : casilla;
                System.out.print(casilla + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private static boolean continuarJugando(Scanner in) {
        String resp = null;
        while (!"S".equals(resp) && !"N".equals(resp)) {
            System.out.println("¿Jugar de nuevo? [S]í / [N]o");
            resp = in.next();
        }
        return "S".equals(resp.trim());

    }

    @Override
    public void juegoGanado(Marca ganador, int[][] lineaGanadora) {
        System.out.println(String.format("%s ganó el juego con la linea (%d, %d), (%d,%d), (%d,%d)", ganador.toString(),
                lineaGanadora[0][0], lineaGanadora[0][1], lineaGanadora[1][0], lineaGanadora[1][1], lineaGanadora[2][0],
                lineaGanadora[2][1]));
        System.out.println("");
    }

    @Override
    public void tableroMarcado(Marca marca, int x, int y) {
        imprimirTablero(juego);
    }
}
