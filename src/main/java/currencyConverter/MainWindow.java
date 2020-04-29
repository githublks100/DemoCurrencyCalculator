package currencyConverter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final ResourceBundle BUNDlE =  ResourceBundle.getBundle("localization.translation");
	
	private JPanel contentPane = new JPanel();
	private JTextField fieldAmount;
	private ArrayList<Currency> currencies = Currency.init(); 
	public MainWindow()
	{
		//System.out.println(BUNDlE.getString("MainWindow.this.title"));
		setTitle(BUNDlE.getString("MainWindow.this.title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu(BUNDlE.getString("MainWindow.mnFile.text"));
		mnFile.setMnemonic(KeyEvent.VK_F);
		menuBar.add(mnFile);
		
		JMenuItem  mntmQuit = new JMenuItem(BUNDlE.getString("MainWindow.mntmQuit.text"));
		mntmQuit.setMnemonic(KeyEvent.VK_Q);
		mntmQuit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		mnFile.add(mntmQuit);
		
		JMenu mnHelp = new JMenu(BUNDlE.getString("MainWindow.mnHelp.text"));
		mnHelp.setMnemonic(KeyEvent.VK_H);
		menuBar.add(mnHelp);
		
		JMenu mnAbout = new JMenu(BUNDlE.getString("MainWindow.mntmAbout.text"));
		mnAbout.setMnemonic(KeyEvent.VK_A);
		menuBar.add(mnAbout);
		
		JMenu mnPref = new JMenu(BUNDlE.getString("MainWindow.mntmPreferences.text"));
		mnPref.setMnemonic(KeyEvent.VK_P);
		menuBar.add(mnPref);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblConvert = new JLabel(BUNDlE.getString("MainWindow.lblConvert.text"));
		lblConvert.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConvert.setBounds(0, 14, 92, 15);
		contentPane.add(lblConvert);
		
		final JComboBox<String> comboBoxCountry1  = new JComboBox<String>();
		comboBoxCountry1.setBounds(147, 7, 240, 28);
		//lblConvert.setBounds(0, 14, 92, 15);
		populateCombo(comboBoxCountry1);
		contentPane.add(comboBoxCountry1);
		
		JLabel lblTo = new JLabel(BUNDlE.getString("MainWindow.lblTo.text"));
		lblTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTo.setBounds(0, 56, 92, 15);
		contentPane.add(lblTo); 
		
		final JComboBox<String> comboBoxCountry2  = new JComboBox<String>();
		comboBoxCountry2.setBounds(147, 49, 240, 28);
		//lblConvert.setBounds(0, 14, 92, 15);
		populateCombo(comboBoxCountry2);
		contentPane.add(comboBoxCountry2);
		
		final JLabel lblAmount = new JLabel(BUNDlE.getString("MainWindow.lblAmount.text")); //$NON-NLS-1$
		lblAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAmount.setBounds(23, 108, 69, 15);
		contentPane.add(lblAmount);
		
		fieldAmount = new JTextField();
		fieldAmount.setText("0.00");
		fieldAmount.setBounds(147, 101, 103, 29);
		contentPane.add(fieldAmount);
		fieldAmount.setColumns(10);
		
		// Label displaying result of conversion
		final JLabel lblResult = new JLabel("");
		lblResult.setHorizontalAlignment(SwingConstants.LEFT);
		lblResult.setBounds(147, 188, 428, 38);
		contentPane.add(lblResult);
		
		//fieldAmount.setDocument(new JTextFieldLimit(8));
		
		JButton btnConvert = new JButton(BUNDlE.getString("MainWindow.btnConvert.text"));
		btnConvert.setBounds(147, 142, 129, 38);
		
		btnConvert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nameCurrency1 = comboBoxCountry1.getSelectedItem().toString();
	        	String nameCurrency2 = comboBoxCountry2.getSelectedItem().toString();
	        	String result;
	        	String formattedPrice;
	        	String formattedAmount; 
	        	Double price;
	        	Double amount = 0.0;
	        	DecimalFormat format = new DecimalFormat("#0.00");
	        	
	        	try {
	        		amount = Double.parseDouble( fieldAmount.getText() ) ;
	        	} catch (NumberFormatException ex) {
	        		ex.printStackTrace();
	        	    amount = 0.0;
	        	}
	        	
	        	price = convert(nameCurrency1, nameCurrency2, currencies, amount);
	        	
	        	// Format numbers to avoid "E7" problem
	        	formattedPrice = format.format(price);
	        	formattedAmount = format.format(amount);
	        	
	        	result = formattedAmount + " " + nameCurrency1 + " = " + formattedPrice + " " + nameCurrency2;
	        	lblResult.setText(result);
			}
		});
		
		contentPane.add(btnConvert);
		
	}
	// Find the short name and the exchange value of the second currency
		public static Double convert(String currency1, String currency2, ArrayList<Currency> currencies, Double amount) {
			String shortNameCurrency2 = null;
			Double exchangeValue;
			Double price = 0.0;
			
			// 
			for (Integer i = 0; i < currencies.size(); i++) {
				if (currencies.get(i).getName() == currency2) {
					shortNameCurrency2 = currencies.get(i).getShortName();
					break;
				}
			}
			
			// Find exchange value and call convert() to calcul the new price
			if (shortNameCurrency2 != null) {
				for (Integer i = 0; i < currencies.size(); i++) {
					if (currencies.get(i).getName() == currency1) {
						exchangeValue = currencies.get(i).getExchangeValues().get(shortNameCurrency2);
						price = Currency.convert(amount, exchangeValue);
						break;
					}
				}
			}
			
			return price;
		}
		
	private void populateCombo(JComboBox<String> comboBoxCountry) {
		
		for (int i = 0; i < currencies.size(); i++) {
			comboBoxCountry.addItem(currencies.get(i).getName());
		}
		
	}
}
