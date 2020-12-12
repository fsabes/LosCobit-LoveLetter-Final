
package assets;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class BotonPersonalizado extends JButton{
	
	private static final long serialVersionUID = 956363962488397247L;
	
	public BotonPersonalizado(String nombre, int altura,int ancho,int posX,int posY) {
		super(nombre, ImageAssets.iconButton);
		setFont(new Font("Dialog", Font.BOLD, 22));
		
		setSize(ancho, altura);
		setHorizontalTextPosition(SwingConstants.CENTER);
		Border emptyBorder = BorderFactory.createEmptyBorder();
		setBorder(emptyBorder);
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
		setLocation(posX, posY);
		
		this.addMouseListener(new MouseAdapter() {
	
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				setForeground(Color.orange);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				setForeground(Color.WHITE);
			}	
		});
		
	}
}