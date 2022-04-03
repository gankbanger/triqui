package org.gank.triqui;

import org.gank.triqui.juego.Triqui;
import org.gank.triqui.ui.Juego;
import org.gank.triqui.ui.cli.TriquiCLI;
import org.gank.triqui.ui.windows.TriquiWindows;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalStateException("No se ha especificado un modo de juego.");
        }

        Juego juego = crearJuego(args);
        juego.run();
    }

    private static Juego crearJuego(String[] args) {
        Triqui triqui = new Triqui();
        
        switch (args[0]) {
            case "--win":
                return new TriquiWindows(triqui);
            case "--cli":
                return new TriquiCLI(triqui);
            default:
                throw new IllegalArgumentException("Modo de juego desconocido: " + args[0]);
        }
    }
}
