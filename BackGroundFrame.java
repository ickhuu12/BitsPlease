package clueless;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Button;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BackGroundFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BackGroundFrame frame = new BackGroundFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BackGroundFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 873, 569);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel GameBoard = new JPanel();
		GameBoard.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Board Game", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GameBoard.setBounds(10, 11, 612, 309);
		contentPane.add(GameBoard);
		GameBoard.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 24, 592, 274);
		GameBoard.add(panel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Player List", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(624, 11, 216, 180);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 16, 204, 157);
		panel_2.add(panel_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Command Output", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(4, 326, 624, 168);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JTextPane txtpnCommandOutput = new JTextPane();
		txtpnCommandOutput.setEditable(false);
		txtpnCommandOutput.setBounds(6, 16, 612, 145);
		panel_3.add(txtpnCommandOutput);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Chat Box", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(628, 198, 208, 296);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		JTextPane txtpnChatBox = new JTextPane();
		txtpnChatBox.setBounds(6, 16, 196, 285);
		panel_4.add(txtpnChatBox);
		
		JButton btnMakeSuggestion = new JButton("Make Suggestion");
		btnMakeSuggestion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		btnMakeSuggestion.setBounds(62, 505, 154, 23);
		contentPane.add(btnMakeSuggestion);
		
		JButton btnMakeAccusation = new JButton("Make Accusation");
		btnMakeAccusation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnMakeAccusation.setBounds(226, 505, 136, 23);
		contentPane.add(btnMakeAccusation);
		
		JButton btnSendChat = new JButton("Send Chat");
		btnSendChat.setBounds(624, 505, 103, 23);
		contentPane.add(btnSendChat);
		
		JButton btnBlockChatUser = new JButton("Block User");
		btnBlockChatUser.setBounds(730, 505, 110, 23);
		contentPane.add(btnBlockChatUser);
		
		JButton btnMakeMove = new JButton("Make Move");
		btnMakeMove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnMakeMove.setBounds(372, 505, 118, 23);
		contentPane.add(btnMakeMove);
	}
}
