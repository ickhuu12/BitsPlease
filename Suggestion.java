package clueless;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class Suggestion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtWhoDidIt;
	private JTextField txtWeapon;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Suggestion dialog = new Suggestion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Suggestion() {
		setBounds(100, 100, 460, 302);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(null);
			{
				txtWhoDidIt = new JTextField();
				txtWhoDidIt.setEditable(false);
				txtWhoDidIt.setHorizontalAlignment(SwingConstants.CENTER);
				txtWhoDidIt.setBounds(10, 30, 86, 20);
				txtWhoDidIt.setText("Person");
				panel.add(txtWhoDidIt);
				txtWhoDidIt.setColumns(10);
			}
			{
				txtWeapon = new JTextField();
				txtWeapon.setEditable(false);
				txtWeapon.setHorizontalAlignment(SwingConstants.CENTER);
				txtWeapon.setText("Weapon");
				txtWeapon.setBounds(12, 137, 86, 20);
				panel.add(txtWeapon);
				txtWeapon.setColumns(10);
			}
			
			JRadioButton rdbtnRope = new JRadioButton("Rope");
			buttonGroup.add(rdbtnRope);
			rdbtnRope.setBounds(111, 136, 109, 23);
			panel.add(rdbtnRope);
			{
				JRadioButton rdbtnRevolver = new JRadioButton("Revolver");
				buttonGroup.add(rdbtnRevolver);
				rdbtnRevolver.setBounds(111, 164, 109, 23);
				panel.add(rdbtnRevolver);
			}
			{
				JRadioButton rdbtnCandlestick = new JRadioButton("Candlestick");
				buttonGroup.add(rdbtnCandlestick);
				rdbtnCandlestick.setBounds(216, 136, 109, 23);
				panel.add(rdbtnCandlestick);
			}
			{
				JRadioButton rdbtnLeadPipe = new JRadioButton("Lead Pipe");
				buttonGroup.add(rdbtnLeadPipe);
				rdbtnLeadPipe.setBounds(216, 164, 109, 23);
				panel.add(rdbtnLeadPipe);
			}
			{
				JRadioButton rdbtnWrench = new JRadioButton("Wrench");
				buttonGroup.add(rdbtnWrench);
				rdbtnWrench.setBounds(111, 190, 109, 23);
				panel.add(rdbtnWrench);
			}
			
			JRadioButton rdbtnPlum = new JRadioButton("Plum");
			buttonGroup_1.add(rdbtnPlum);
			rdbtnPlum.setBounds(101, 59, 109, 23);
			panel.add(rdbtnPlum);
			
			JRadioButton rdbtnPeacock = new JRadioButton("Peacock");
			buttonGroup_1.add(rdbtnPeacock);
			rdbtnPeacock.setBounds(101, 29, 109, 23);
			panel.add(rdbtnPeacock);
			
			JRadioButton rdbtnGreen = new JRadioButton("Green");
			buttonGroup_1.add(rdbtnGreen);
			rdbtnGreen.setBounds(101, 85, 109, 23);
			panel.add(rdbtnGreen);
			
			JRadioButton rdbtnScarlet = new JRadioButton("Scarlet");
			buttonGroup_1.add(rdbtnScarlet);
			rdbtnScarlet.setBounds(216, 85, 109, 23);
			panel.add(rdbtnScarlet);
			
			JRadioButton rdbtnWhite = new JRadioButton("White");
			buttonGroup_1.add(rdbtnWhite);
			rdbtnWhite.setBounds(216, 29, 109, 23);
			panel.add(rdbtnWhite);
			
			JRadioButton rdbtnMustard = new JRadioButton("Mustard");
			buttonGroup_1.add(rdbtnMustard);
			rdbtnMustard.setBounds(216, 59, 109, 23);
			panel.add(rdbtnMustard);
			{
				JTextPane txtpnMakingASuggestion = new JTextPane();
				txtpnMakingASuggestion.setEditable(false);
				txtpnMakingASuggestion.setText("Making a Suggestion");
				txtpnMakingASuggestion.setBounds(154, 0, 109, 20);
				panel.add(txtpnMakingASuggestion);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
