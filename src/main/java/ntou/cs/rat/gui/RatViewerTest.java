package ntou.cs.rat.gui;

import javax.swing.JFrame;

public class RatViewerTest {

	public static void main(String args[]) {
		RatMainGUI mainGUI = new RatMainGUI();

		mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainGUI.setSize(800, 600); // set the frame size
		mainGUI.setVisible(true); // display the frame
	} // end main
}
