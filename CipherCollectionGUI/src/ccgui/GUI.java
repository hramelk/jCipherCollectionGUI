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
	private JLabel				inputLabel			= new JLabel(
															"Enter your plain text here:");
	private JTextArea			inputText			= new JTextArea(7,
															columnSize);
	private JScrollPane			inputScrollPane		= new JScrollPane();
	private JLabel				keyLabel			= new JLabel("Key: ");
	private JTextField			keyText				= new JTextField(columnSize
															- keySizeIndex);
	private JLabel				keyHintLabel		= new JLabel(
															"Choose a cipher");
	
	private JPanel				resultPanel			= new JPanel(leftFlow);
	private JLabel				resultLabel			= new JLabel(
															"Your result will be shown here:");
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
			"Atbash",
			"Dvorak",
			"-- Select --"							};
	private JComboBox<String>	cipherSelectorBox	= new JComboBox<String>(
															cipherList);
	
	private static boolean		isValidKey			= true;
	
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
		keyText.setToolTipText("Enter your key here.");
		keyText.setBackground(Color.white);
		keyText.setEnabled(false);
		
		inputText.setLineWrap(true);
		inputText.setWrapStyleWord(true);
		inputText.setToolTipText("Enter your plain text here.");
		TransferFocus.patch(inputText);
		inputPanel.setBackground(mainColor);
		inputPanel.setPreferredSize(new Dimension(360, 190));
		inputScrollPane.setViewportView(inputText);
		inputPanel.add(inputLabel);
		inputPanel.add(inputScrollPane);
		inputPanel.add(keyLabel);
		inputPanel.add(keyText);
		inputPanel.add(keyHintLabel);
		
		resultText.setLineWrap(true);
		resultText.setWrapStyleWord(true);
		resultText.setToolTipText("Your result will be shown here.");
		TransferFocus.patch(resultText);
		resultPanel.setBackground(mainColor);
		resultPanel.setPreferredSize(new Dimension(360, 150));
		resultScrollPane.setViewportView(resultText);
		resultPanel.add(resultLabel);
		resultPanel.add(resultScrollPane);
		
		ActionListener cipherSelectorListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkKeyCipherCompatibility();
				setKeyFieldAvailability();
			}
		};
		
		cipherSelectorBox
				.setSelectedIndex(cipherSelectorBox.getItemCount() - 1);
		cipherSelectorBox.addActionListener(cipherSelectorListener);
		cipherSelectorBox.setToolTipText("Choose a cipher.");
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
		mainPanel.setPreferredSize(new Dimension(370, 400));
		mainPanel.setBackground(Color.red);
		mainPanel.setBackground(mainColor);
		getContentPane().setBackground(mainColor);
		
		add(mainPanel);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setSize(400, 440);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String input = inputText.getText();
		String key = keyText.getText();
		if (input.isEmpty() || !isValidKey) {
			return;
		}
		String output = "";
		Object source = arg0.getSource();
		int cipherIndex = cipherSelectorBox.getSelectedIndex();
		if (source == encodeButton) {
			output = getEncoded(cipherIndex, input, key);
		} else if (source == decodeButton) {
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
		case 2: // Keyword Cipher
			encoded = KeywordCipher.encode(textOriginal, key);
			break;
		case 3: // Atbash
			encoded = Atbash.encode(textOriginal);
			break;
		case 4: // Dvorak
			encoded = Dvorak.encode(textOriginal);
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
		case 2: // Keyword Cipher
			decoded = KeywordCipher.decode(textEncoded, key);
			break;
		case 3: // Atbash
			decoded = Atbash.decode(textEncoded);
			break;
		case 4: // Dvorak
			decoded = Dvorak.decode(textEncoded);
			break;
		default:
			break;
		}
		
		return decoded;
	}
	
	public void setKeyFieldAvailability() {
		int cipherIndex = cipherSelectorBox.getSelectedIndex();
		boolean keyIsNeeded = false;
		boolean isCipher = true;
		
		String hint = "";
		
		switch (cipherIndex) {
		case 0: // Caesar
			keyIsNeeded = true;
			hint = "Enter a number";
			break;
		case 1: // Viginere
			keyIsNeeded = true;
			hint = "Enter a letter or a word";
			break;
		case 2: // Keyword Cipher
			keyIsNeeded = true;
			hint = "Enter a letter or a word";
			break;
		case 3: // Atbash
			keyIsNeeded = false;
			break;
		case 4: // Dvorak
			keyIsNeeded = false;
			break;
		default:
			keyIsNeeded = false;
			isCipher = false;
		}
		
		if (keyIsNeeded) {
			keyHintLabel.setText(hint);
		} else {
			keyHintLabel.setText("A key is not needed for this cipher");
			keyText.setText("A key is not needed for this cipher");
			keyText.setBackground(Color.white);
		}
		if (!isCipher) {
			keyHintLabel.setText("Choose a cipher.");
			keyText.setText("Choose a cipher.");
			keyText.setBackground(Color.white);
		}
		
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
			// keyIsValid = true;
			if (key.isEmpty() || key == null) {
				keyIsValid = false;
			} else {
				keyIsValid = true;
			}
			break;
		case 2: // Keyword Cipher
			keyIsValid = KeywordCipher.isValidKey(key);
			break;
		case 3: // Atbash
			if (key.isEmpty() || key == null) {
				keyIsValid = true;
			} else {
				keyIsValid = false;
			}
			break;
		case 4: // Dvorak
			if (key.isEmpty() || key == null) {
				keyIsValid = true;
			} else {
				keyIsValid = false;
			}
			break;
		default:
			break;
		}
		
		isValidKey = keyIsValid;
		
		if (keyIsValid) {
			keyText.setBackground(Color.WHITE);
		} else {
			keyText.setBackground(Color.PINK);
		}
		
	}
}
