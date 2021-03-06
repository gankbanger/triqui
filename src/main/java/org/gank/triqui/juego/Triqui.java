package org.gank.triqui.juego;

import java.util.ArrayList;
import java.util.List;

public class Triqui {
    /**
     *
     */
    public static final int DIMENSION_TABLERO = 3;
    private Marca[][] tablero;
    private int[][] lineaGanadora;
    private Marca siguienteTurno;
    private Marca ganador;
    private int totalMarcas;

    private List<SuscriptorAlJuego> suscriptores;

    public Triqui() {
        this.tablero = new Marca[DIMENSION_TABLERO][DIMENSION_TABLERO];
        this.nuevoJuego();
        this.suscriptores = new ArrayList<>();
    }

    public void nuevoJuego() {
        this.limpiarTablero();
        this.siguienteTurno = Marca.X;
        this.ganador = Marca.VACIA;
        this.lineaGanadora = new int[DIMENSION_TABLERO][2];
        this.totalMarcas = 0;
    }

    public Marca obtenerCasilla(int i, int j) {
        return this.tablero[i][j];
    }

    public void limpiarTablero() {
        for (int i = 0; i < DIMENSION_TABLERO; i++) {
            for (int j = 0; j < DIMENSION_TABLERO; j++) {
                this.tablero[i][j] = Marca.VACIA;
            }
        }
    }

    public void marcar(int i, int j) {
        if (!this.finJuego() && this.tablero[i][j].equals(Marca.VACIA)) {
            this.tablero[i][j] = this.siguienteTurno;
            this.totalMarcas++;

            this.suscriptores.forEach(suscriptor -> suscriptor.tableroMarcado(this.siguienteTurno, i, j));
            calcularGanador();

            if (!this.ganador.equals(Marca.VACIA)) {
                this.suscriptores.forEach(suscriptor -> suscriptor.juegoGanado(this.ganador, this.lineaGanadora));
            } else {
                this.siguienteTurno = this.siguienteTurno.equals(Marca.X) ? Marca.O : Marca.X;
            }
        }
    }

    private void calcularGanador() {
        for (int i = 0; i < DIMENSION_TABLERO; i++) {
            victoriaHorizontal(i);
            victoriaVertical(i);
        }
        victoriaDiagonal();
    }

    private void victoriaDiagonal() {
        if (!this.tablero[0][0].equals(Marca.VACIA) && this.tablero[0][0].equals(this.tablero[1][1])
                && this.tablero[0][0].equals(this.tablero[2][2])) {
            this.ganador = this.tablero[0][0];
            this.lineaGanadora[0][0] = 0;
            this.lineaGanadora[0][1] = 0;
            this.lineaGanadora[1][0] = 1;
            this.lineaGanadora[1][1] = 1;
            this.lineaGanadora[2][0] = 2;
            this.lineaGanadora[2][1] = 2;
        }
        if (!this.tablero[0][2].equals(Marca.VACIA) && this.tablero[0][2].equals(this.tablero[1][1])
                && this.tablero[0][2].equals(this.tablero[2][0])) {
            this.ganador = this.tablero[0][2];
            this.lineaGanadora[0][0] = 0;
            this.lineaGanadora[0][1] = 2;
            this.lineaGanadora[1][0] = 1;
            this.lineaGanadora[1][1] = 1;
            this.lineaGanadora[2][0] = 2;
            this.lineaGanadora[2][1] = 0;
        }
    }

    private void victoriaVertical(int col) {
        if (!this.tablero[0][col].equals(Marca.VACIA) && this.tablero[0][col].equals(this.tablero[1][col])
                && this.tablero[0][col].equals(this.tablero[2][col])) {
            this.ganador = this.tablero[0][col];
            this.lineaGanadora[0][0] = 0;
            this.lineaGanadora[0][1] = col;
            this.lineaGanadora[1][0] = 1;
            this.lineaGanadora[1][1] = col;
            this.lineaGanadora[2][0] = 2;
            this.lineaGanadora[2][1] = col;
        }
    }

    private void victoriaHorizontal(int fila) {
        if (!this.tablero[fila][0].equals(Marca.VACIA) && this.tablero[fila][0].equals(this.tablero[fila][1])
                && this.tablero[fila][0].equals(this.tablero[fila][2])) {
            this.ganador = this.tablero[fila][0];
            this.lineaGanadora[0][0] = fila;
            this.lineaGanadora[0][1] = 0;
            this.lineaGanadora[1][0] = fila;
            this.lineaGanadora[1][1] = 1;
            this.lineaGanadora[2][0] = fila;
            this.lineaGanadora[2][1] = 2;
        }
    }

    public void inscribirseNotificacionesTurnos(SuscriptorAlJuego suscriptor) {
        this.suscriptores.add(suscriptor);
    }

    public boolean finJuego() {
        return !this.ganador.equals(Marca.VACIA) || totalMarcas == DIMENSION_TABLERO * DIMENSION_TABLERO;
    }

}
