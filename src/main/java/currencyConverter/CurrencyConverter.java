package currencyConverter;

import javax.swing.UIManager;

public class CurrencyConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//System.out.println("hello now");
	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		MainWindow mainWindow = new MainWindow();
		System.out.println(mainWindow.getTitle());
		mainWindow.setVisible(true);
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	}

}
