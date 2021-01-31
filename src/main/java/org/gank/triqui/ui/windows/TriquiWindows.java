package org.gank.triqui.ui.windows;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.plaf.ColorUIResource;

import org.gank.triqui.juego.Casilla;
import org.gank.triqui.juego.SuscriptorAlJuego;
import org.gank.triqui.juego.Triqui;

import lombok.Getter;

public class TriquiWindows implements SuscriptorAlJuego {

	private JFrame frame;
	private Triqui juego;

	public TriquiWindows(JFrame frame) {
		juego = new Triqui();
		this.frame = frame;

		juego.inscribirseNotificacionesTurnos(this);

		this.inicializar();
	}

	private void inicializar() {
		frame.setTitle("Triqui");
		frame.setSize(300, 300);

		Container pane = frame.getContentPane();
		pane.setLayout(new GridLayout(3, 3));
		pane.add(new BotonCasilla(this.juego, 0, 0));
		pane.add(new BotonCasilla(this.juego, 0, 1));
		pane.add(new BotonCasilla(this.juego, 0, 2));
		pane.add(new BotonCasilla(this.juego, 1, 0));
		pane.add(new BotonCasilla(this.juego, 1, 1));
		pane.add(new BotonCasilla(this.juego, 1, 2));
		pane.add(new BotonCasilla(this.juego, 2, 0));
		pane.add(new BotonCasilla(this.juego, 2, 1));
		pane.add(new BotonCasilla(this.juego, 2, 2));

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		new TriquiWindows(frame);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	@Override
	public void victoria(Casilla ganador, int[][] lineaGanadora) {
		for (Component component : frame.getContentPane().getComponents()) {
			if (component instanceof BotonCasilla) {
				BotonCasilla boton = (BotonCasilla) component;
				for (int l = 0; l < Triqui.DIMENSION_TABLERO; l++) {
					if (boton.getFila() == lineaGanadora[l][1] && boton.getColumna() == lineaGanadora[l][0]) {
						boton.setBackground(Color.GREEN);
					}
				}
			}
		}
	}

	@Getter
	private class BotonCasilla extends JButton {
		private static final long serialVersionUID = -2711573858699872271L;
		private final int columna;
		private final int fila;
		private transient Triqui juego;

		public BotonCasilla(Triqui juego, int x, int y) {
			super();
			this.columna = x;
			this.fila = y;
			this.setText("");
			this.juego = juego;
			this.setFont(new Font("Arial", Font.PLAIN, 40));

			this.addActionListener(event -> {
				if (!juego.finJuego()) {

					juego.marcar(this.columna, this.fila);
					Casilla c = juego.obtenerCasilla(this.columna, this.fila);
					if (c.equals(Casilla.X)) {
						this.setText("X");
					} else if (c.equals(Casilla.O)) {
						this.setText("O");
					}
				} else {
					juego.nuevoJuego();
					for (Component component : this.getParent().getComponents()) {
						if (component instanceof BotonCasilla) {
							BotonCasilla boton = ((BotonCasilla)component);
							boton.setText("");
							boton.setBackground(new ColorUIResource(238, 138, 210));
						}
					}
				}
			});
		}
	}
}
