package org.gank.triqui.juego;

public interface SuscriptorAlJuego {
    void tableroMarcado(Marca marca, int x, int y);
    void juegoGanado(Marca ganador, int[][] lineaGanadora);
}
