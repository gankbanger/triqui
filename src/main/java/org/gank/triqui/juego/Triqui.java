package org.gank.triqui.juego;

import java.util.ArrayList;
import java.util.List;

public class Triqui {
    /**
     *
     */
    public static final int DIMENSION_TABLERO = 3;
    private Casilla[][] tablero;
    private int[][] lineaGanadora;
    private Casilla siguienteTurno;
    private Casilla ganador;
    private int totalMarcas;

    private List<SuscriptorAlJuego> suscriptoresCambioTurno;

    public Triqui() {
        this.tablero = new Casilla[DIMENSION_TABLERO][DIMENSION_TABLERO];
        this.nuevoJuego();
        this.suscriptoresCambioTurno = new ArrayList<>();
    }

    public void nuevoJuego() {
        this.limpiarTablero();
        this.siguienteTurno = Casilla.X;
        this.ganador = Casilla.VACIA;
        this.lineaGanadora = new int[DIMENSION_TABLERO][2];
        this.totalMarcas = 0;
    }

    public Casilla obtenerCasilla(int i, int j) {
        return this.tablero[i][j];
    }

    public void limpiarTablero() {
        for (int i = 0; i < DIMENSION_TABLERO; i++) {
            for (int j = 0; j < DIMENSION_TABLERO; j++) {
                this.tablero[i][j] = Casilla.VACIA;
            }
        }
    }

    public void marcar(int i, int j) {
        if (!this.finJuego() && this.tablero[i][j].equals(Casilla.VACIA)) {
            this.tablero[i][j] = this.siguienteTurno;
            this.totalMarcas++;
            calcularGanador();
            if (!this.ganador.equals(Casilla.VACIA)) {
                this.notificarVictoria();
            } else {
                this.siguienteTurno = this.siguienteTurno.equals(Casilla.X) ? Casilla.O : Casilla.X;
            }
        }
    }

    private void calcularGanador() {
        for (int i = 0; i < DIMENSION_TABLERO; i++) {
            if (!this.tablero[i][0].equals(Casilla.VACIA) && this.tablero[i][0].equals(this.tablero[i][1])
                    && this.tablero[i][0].equals(this.tablero[i][2])) {
                this.ganador = this.tablero[i][0];
                this.lineaGanadora[0][0] = i;
                this.lineaGanadora[0][1] = 0;
                this.lineaGanadora[1][0] = i;
                this.lineaGanadora[1][1] = 1;
                this.lineaGanadora[2][0] = i;
                this.lineaGanadora[2][1] = 2;
            }
            if (!this.tablero[0][i].equals(Casilla.VACIA) && this.tablero[0][i].equals(this.tablero[1][i])
                    && this.tablero[0][i].equals(this.tablero[2][i])) {
                this.ganador = this.tablero[0][i];
                this.lineaGanadora[0][0] = 0;
                this.lineaGanadora[0][1] = i;
                this.lineaGanadora[1][0] = 1;
                this.lineaGanadora[1][1] = i;
                this.lineaGanadora[2][0] = 2;
                this.lineaGanadora[2][1] = i;
            }
        }
        if (!this.tablero[0][0].equals(Casilla.VACIA) && this.tablero[0][0].equals(this.tablero[1][1])
                && this.tablero[0][0].equals(this.tablero[2][2])) {
            this.ganador = this.tablero[0][0];
            this.lineaGanadora[0][0] = 0;
            this.lineaGanadora[0][1] = 0;
            this.lineaGanadora[1][0] = 1;
            this.lineaGanadora[1][1] = 1;
            this.lineaGanadora[2][0] = 2;
            this.lineaGanadora[2][1] = 2;
        }
        if (!this.tablero[0][2].equals(Casilla.VACIA) && this.tablero[0][2].equals(this.tablero[1][1])
                && this.tablero[0][2].equals(this.tablero[2][0])) {
            this.ganador = this.tablero[0][0];
            this.lineaGanadora[0][0] = 0;
            this.lineaGanadora[0][1] = 2;
            this.lineaGanadora[1][0] = 1;
            this.lineaGanadora[1][1] = 1;
            this.lineaGanadora[2][0] = 2;
            this.lineaGanadora[2][1] = 0;
        }
    }

    public void inscribirseNotificacionesTurnos(SuscriptorAlJuego suscriptor) {
        this.suscriptoresCambioTurno.add(suscriptor);
    }

    private void notificarVictoria() {
        this.suscriptoresCambioTurno.forEach(suscriptor -> suscriptor.victoria(this.ganador, this.lineaGanadora));
    }

    public boolean finJuego() {
        return !this.ganador.equals(Casilla.VACIA) || totalMarcas == DIMENSION_TABLERO * DIMENSION_TABLERO;
    }

}
