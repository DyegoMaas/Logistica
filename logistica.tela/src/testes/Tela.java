package testes;

import javax.swing.JFrame;

public class Tela {

	private JFrame frame;
	
	public Tela(){
		frame = new JFrame("Log�stica");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}

}