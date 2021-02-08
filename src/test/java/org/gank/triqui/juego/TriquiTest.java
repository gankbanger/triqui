package org.gank.triqui.juego;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

public class TriquiTest {
    private Stack<Marca> marcas = new Stack<>();
    private Marca ultimoGanador;
    private Triqui triqui;

    @Before
    public void setUp() {
        triqui = new Triqui();
        ultimoGanador = Marca.VACIA;
        marcas.clear();
        triqui.nuevoJuego();
        triqui.inscribirseNotificacionesTurnos(new SuscriptorAlJuego() {

            @Override
            public void tableroMarcado(Marca marca, int x, int y) {
                marcas.push(marca);
            }

            @Override
            public void juegoGanado(Marca ganador, int[][] lineaGanadora) {
                ultimoGanador = ganador;
            }

            @Override
            public void juegoTerminado() {
                // Sin usar
            }
        });
    }

    @Test
    public void testTriqui() {
        for (int i = 0; i < Triqui.DIMENSION_TABLERO; i++) {
            for (int j = 0; j < Triqui.DIMENSION_TABLERO; j++) {
                assertEquals(Marca.VACIA, triqui.obtenerCasilla(i, j));
            }
        }
    }

    @Test
    public void testMarcar() {
        assertEquals(Marca.VACIA, triqui.obtenerCasilla(0, 0));
        triqui.marcar(0, 0);
        assertEquals(Marca.X, marcas.pop());
        assertEquals(Marca.X, triqui.obtenerCasilla(0, 0));
        triqui.marcar(0, 1);
        assertEquals(Marca.O, marcas.pop());
        assertEquals(Marca.O, triqui.obtenerCasilla(0, 1));
        triqui.marcar(0, 1);
        assertTrue(marcas.isEmpty());
        assertEquals(Marca.O, triqui.obtenerCasilla(0, 1));
        triqui.marcar(0, 2);
        assertEquals(Marca.X, marcas.pop());
        assertEquals(Marca.X, triqui.obtenerCasilla(0, 2));
    }

    @Test
    public void testGanadorXHorizontal() {
        assertEquals(Marca.VACIA, triqui.obtenerCasilla(0, 0));
        triqui.marcar(0, 0);
        triqui.marcar(1, 0);
        triqui.marcar(0, 1);
        triqui.marcar(2, 0);
        triqui.marcar(0, 2);

        assertTrue(triqui.finJuego());
        assertEquals(Marca.X, this.ultimoGanador);
    }

    @Test
    public void testGanadorOVertical() {
        assertEquals(Marca.VACIA, triqui.obtenerCasilla(0, 0));
        triqui.marcar(0, 0);
        triqui.marcar(0, 1);
        triqui.marcar(2, 2);
        triqui.marcar(1, 1);
        triqui.marcar(0, 2);
        triqui.marcar(2, 1);

        assertTrue(triqui.finJuego());
        assertEquals(Marca.O, this.ultimoGanador);
    }

    @Test
    public void testGanadorXDiagonal() {
        assertEquals(Marca.VACIA, triqui.obtenerCasilla(0, 0));
        triqui.marcar(0, 0);
        triqui.marcar(0, 1);
        triqui.marcar(1, 1);
        triqui.marcar(2, 1);
        triqui.marcar(2, 2);

        assertTrue(triqui.finJuego());
        assertEquals(Marca.X, this.ultimoGanador);
    }

    @Test
    public void testGanadorXDiagonal2() {
        assertEquals(Marca.VACIA, triqui.obtenerCasilla(0, 0));
        triqui.marcar(0, 2);
        triqui.marcar(0, 1);
        triqui.marcar(1, 1);
        triqui.marcar(2, 1);
        triqui.marcar(2, 0);

        assertTrue(triqui.finJuego());
        assertEquals(Marca.X, this.ultimoGanador);
    }

    @Test
    public void testFinDeJuegoSinGanador() {
        triqui.marcar(0, 0);
        triqui.marcar(0, 1);
        triqui.marcar(0, 2);
        triqui.marcar(1, 1);
        triqui.marcar(1, 0);
        triqui.marcar(1, 2);
        triqui.marcar(2, 2);
        triqui.marcar(2, 0);
        triqui.marcar(2, 1);

        assertTrue(triqui.finJuego());
        assertEquals(Marca.VACIA, ultimoGanador);
    }
}
