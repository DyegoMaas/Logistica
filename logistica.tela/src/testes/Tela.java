package testes;

import javax.swing.JFrame;

public class Tela {

	private JFrame frame;
	
	public Tela(){
		frame = new JFrame("Logística");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}

}