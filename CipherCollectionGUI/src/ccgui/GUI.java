package ccgui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GUI extends JFrame implements ActionListener {
	private static final long	serialVersionUID	= 1L;
	
	private int					columnSize			= 32;
	private int					keySizeIndex		= 4;
	private FlowLayout			leftFlow			= new FlowLayout(
															FlowLayout.LEFT);
	private JPanel				mainPanel			= new JPanel(leftFlow);
	private Color				mainColor			= Color.lightGray;
	
	private JPanel				inputPanel			= new JPanel(leftFlow);
	private JTextArea			inputText			= new JTextArea(7,
															columnSize);
	private JScrollPane			inputScrollPane		= new JScrollPane();
	private JLabel				keyLabel			= new JLabel("Key: ");
	private JTextField			keyText				= new JTextField(columnSize
															- keySizeIndex);
	
	private JPanel				resultPanel			= new JPanel(leftFlow);
	private JTextArea			resultText			= new JTextArea(7,
															columnSize);
	private JScrollPane			resultScrollPane	= new JScrollPane();
	
	private JPanel				buttonPanel			= new JPanel(leftFlow);
	private JButton				encodeButton		= new JButton("Encode");
	private JButton				decodeButton		= new JButton("Decode");
	
	private JPanel				comboBoxPanel		= new JPanel(leftFlow);
	private String[]			cipherList			= {
			"Caesar",
			"Viginere",
			"Keyword",
			"Other",
			"etc."									};
	private JComboBox<String>	cipherSelectorBox	= new JComboBox<String>(
															cipherList);
	
	public GUI() throws HeadlessException {
		super("Cipher collection");
		
		setLayout(leftFlow);
		
		// Listen for changes in the text
		keyText.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				checkKeyCipherCompatibility();
			}
			
			public void removeUpdate(DocumentEvent e) {
				checkKeyCipherCompatibility();
			}
			
			public void insertUpdate(DocumentEvent e) {
				checkKeyCipherCompatibility();
			}
		});
		
		inputText.setLineWrap(true);
		inputText.setWrapStyleWord(true);
		TransferFocus.patch(inputText);
		inputPanel.setBackground(mainColor);
		inputPanel.setPreferredSize(new Dimension(360, 150));
		inputScrollPane.setViewportView(inputText);
		inputPanel.add(inputScrollPane);
		inputPanel.add(keyLabel);
		inputPanel.add(keyText);
		
		resultText.setLineWrap(true);
		resultText.setWrapStyleWord(true);
		TransferFocus.patch(resultText);
		resultPanel.setBackground(mainColor);
		resultPanel.setPreferredSize(new Dimension(360, 130));
		resultScrollPane.setViewportView(resultText);
		resultPanel.add(resultScrollPane);
		
		ActionListener cipherSelectorListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkKeyCipherCompatibility();
			}
		};
		
		cipherSelectorBox.setSelectedIndex(0);
		cipherSelectorBox.addActionListener(cipherSelectorListener);
		comboBoxPanel.setBackground(mainColor);
		comboBoxPanel.add(cipherSelectorBox);
		
		encodeButton.addActionListener(this);
		decodeButton.addActionListener(this);
		buttonPanel.setBackground(mainColor);
		buttonPanel.add(encodeButton);
		buttonPanel.add(decodeButton);
		
		mainPanel.add(inputPanel);
		mainPanel.add(comboBoxPanel);
		mainPanel.add(buttonPanel);
		mainPanel.add(resultPanel);
		mainPanel.setPreferredSize(new Dimension(370, 340));
		mainPanel.setBackground(Color.red);
		mainPanel.setBackground(mainColor);
		// setBackground(Color.darkGray);
		getContentPane().setBackground(mainColor);
		
		add(mainPanel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setSize(400, 400);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String input = inputText.getText();
		String key = keyText.getText();
		if (input.isEmpty() || key.isEmpty()) {
			return;
		}
		String output = "";
		Object source = arg0.getSource();
		int cipherIndex = cipherSelectorBox.getSelectedIndex();
		if (source == encodeButton) {
			// System.out.println("encode");
			output = getEncoded(cipherIndex, input, key);
		} else if (source == decodeButton) {
			// System.out.println("decode");
			output = getDecoded(cipherIndex, input, key);
		}
		resultText.setText(output);
	}
	
	private String getEncoded(int cipher, String textOriginal, String key) {
		
		String encoded = "";
		
		switch (cipher) {
		case 0: // Caesar
			encoded = Caesar.encode(textOriginal, key);
			break;
		case 1: // Viginere
			encoded = Vigenere.encode(textOriginal, key);
			break;
		case 2:
			encoded = KeywordCipher.encode(textOriginal, key);
			break;
		case 3:
			encoded = Cipher3.encode(textOriginal, key);
			break;
		case 4:
			encoded = Cipher4.encode(textOriginal, key);
			break;
		default:
			break;
		}
		
		return encoded;
	}
	
	private String getDecoded(int cipher, String textEncoded, String key) {
		
		String decoded = "";
		
		switch (cipher) {
		case 0: // Caesar
			decoded = Caesar.decode(textEncoded, key);
			break;
		case 1: // Viginere
			decoded = Vigenere.decode(textEncoded, key);
			break;
		case 2:
			decoded = KeywordCipher.decode(textEncoded, key);
			break;
		case 3:
			decoded = Cipher3.decode(textEncoded, key);
			break;
		case 4:
			decoded = Cipher4.decode(textEncoded, key);
			break;
		default:
			break;
		}
		
		return decoded;
	}
	
	public void checkKeyCipherCompatibility() {
		int cipherIndex = cipherSelectorBox.getSelectedIndex();
		String key = keyText.getText();
		
		boolean keyIsValid = false;
		
		switch (cipherIndex) {
		case 0: // Caesar
			keyIsValid = Caesar.isValidKey(key);
			break;
		case 1: // Viginere
			keyIsValid = true;
			break;
		case 2: // Keyword Cipher
			keyIsValid = KeywordCipher.isValidKey(key);
			break;
		case 3:
			keyIsValid = true;
			break;
		case 4:
			keyIsValid = true;
			break;
		default:
			break;
		}
		
		if (keyIsValid) {
			keyText.setBackground(Color.WHITE);
		} else {
			keyText.setBackground(Color.PINK);
		}
		
	}
}
