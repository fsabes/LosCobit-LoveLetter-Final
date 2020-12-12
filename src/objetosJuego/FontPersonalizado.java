package objetosJuego;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class FontPersonalizado {
	public static ArrayList<BufferedImage> font = new ArrayList<>();
	
	FontPersonalizado() {
	font = new ArrayList<BufferedImage>();
		try {
			for (int i = 0; i < 99; i++) {
				font.add(ImageIO.read(new File( "font/" + (i+1) + ".png")));
				System.out.println(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	static public void dibujarTexto(Graphics2D g, String string, int x, int y) {
		String espacio = " ";
		for (int i = 0; i < string.length(); i++) {
			g.drawImage(FontPersonalizado.font.get(Math.max(string.codePointAt(i) - espacio.codePointAt(0), 0)), null,
					x + 15 * i, y);
		}

	}
	
}
