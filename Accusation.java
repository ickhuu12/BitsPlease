package clueless;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class Accusation extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtPerson;
	private JTextField txtWeapon;
	private JTextField txtRoom;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Accusation dialog = new Accusation();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Accusation() {
		setBounds(100, 100, 369, 462);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtPerson = new JTextField();
			txtPerson.setEditable(false);
			txtPerson.setHorizontalAlignment(SwingConstants.CENTER);
			txtPerson.setText("Person");
			txtPerson.setColumns(10);
			txtPerson.setBounds(10, 53, 86, 20);
			contentPanel.add(txtPerson);
		}
		{
			JRadioButton radioButton = new JRadioButton("Peacock");
			buttonGroup_2.add(radioButton);
			radioButton.setBounds(101, 52, 109, 23);
			contentPanel.add(radioButton);
		}
		{
			JRadioButton radioButton = new JRadioButton("White");
			buttonGroup_2.add(radioButton);
			radioButton.setBounds(216, 52, 109, 23);
			contentPanel.add(radioButton);
		}
		{
			JRadioButton radioButton = new JRadioButton("Plum");
			buttonGroup_2.add(radioButton);
			radioButton.setBounds(101, 82, 109, 23);
			contentPanel.add(radioButton);
		}
		{
			JRadioButton radioButton = new JRadioButton("Mustard");
			buttonGroup_2.add(radioButton);
			radioButton.setBounds(216, 82, 109, 23);
			contentPanel.add(radioButton);
		}
		{
			JRadioButton radioButton = new JRadioButton("Green");
			buttonGroup_2.add(radioButton);
			radioButton.setBounds(101, 108, 109, 23);
			contentPanel.add(radioButton);
		}
		{
			JRadioButton radioButton = new JRadioButton("Scarlet");
			buttonGroup_2.add(radioButton);
			radioButton.setBounds(216, 108, 109, 23);
			contentPanel.add(radioButton);
		}
		{
			txtWeapon = new JTextField();
			txtWeapon.setEditable(false);
			txtWeapon.setHorizontalAlignment(SwingConstants.CENTER);
			txtWeapon.setText("Weapon");
			txtWeapon.setColumns(10);
			txtWeapon.setBounds(12, 160, 86, 20);
			contentPanel.add(txtWeapon);
		}
		{
			JRadioButton radioButton = new JRadioButton("Rope");
			buttonGroup_1.add(radioButton);
			radioButton.setBounds(111, 159, 109, 23);
			contentPanel.add(radioButton);
		}
		{
			JRadioButton radioButton = new JRadioButton("Candlestick");
			buttonGroup_1.add(radioButton);
			radioButton.setBounds(216, 159, 109, 23);
			contentPanel.add(radioButton);
		}
		{
			JRadioButton radioButton = new JRadioButton("Revolver");
			buttonGroup_1.add(radioButton);
			radioButton.setBounds(111, 187, 109, 23);
			contentPanel.add(radioButton);
		}
		{
			JRadioButton radioButton = new JRadioButton("Lead Pipe");
			buttonGroup_1.add(radioButton);
			radioButton.setBounds(216, 187, 109, 23);
			contentPanel.add(radioButton);
		}
		{
			JRadioButton radioButton = new JRadioButton("Wrench");
			buttonGroup_1.add(radioButton);
			radioButton.setBounds(111, 213, 109, 23);
			contentPanel.add(radioButton);
		}
		{
			txtRoom = new JTextField();
			txtRoom.setEditable(false);
			txtRoom.setHorizontalAlignment(SwingConstants.CENTER);
			txtRoom.setText("Room");
			txtRoom.setBounds(10, 262, 86, 20);
			contentPanel.add(txtRoom);
			txtRoom.setColumns(10);
		}
		{
			JRadioButton rdbtnHall = new JRadioButton("Hall");
			buttonGroup.add(rdbtnHall);
			rdbtnHall.setBounds(111, 261, 109, 23);
			contentPanel.add(rdbtnHall);
		}
		{
			JRadioButton rdbtnLounge = new JRadioButton("Lounge");
			buttonGroup.add(rdbtnLounge);
			rdbtnLounge.setBounds(111, 287, 109, 23);
			contentPanel.add(rdbtnLounge);
		}
		{
			JRadioButton rdbtnLibrary = new JRadioButton("Library");
			buttonGroup.add(rdbtnLibrary);
			rdbtnLibrary.setBounds(111, 313, 109, 23);
			contentPanel.add(rdbtnLibrary);
		}
		{
			JRadioButton rdbtnBillardRoom = new JRadioButton("Billard Room");
			buttonGroup.add(rdbtnBillardRoom);
			rdbtnBillardRoom.setBounds(111, 339, 109, 23);
			contentPanel.add(rdbtnBillardRoom);
		}
		{
			JRadioButton rdbtnDiningRoom = new JRadioButton("Dining Room");
			buttonGroup.add(rdbtnDiningRoom);
			rdbtnDiningRoom.setBounds(216, 261, 109, 23);
			contentPanel.add(rdbtnDiningRoom);
		}
		{
			JRadioButton rdbtnConservatory = new JRadioButton("Conservatory");
			buttonGroup.add(rdbtnConservatory);
			rdbtnConservatory.setBounds(216, 287, 109, 23);
			contentPanel.add(rdbtnConservatory);
		}
		{
			JRadioButton rdbtnBallroom = new JRadioButton("Ballroom");
			buttonGroup.add(rdbtnBallroom);
			rdbtnBallroom.setBounds(216, 313, 109, 23);
			contentPanel.add(rdbtnBallroom);
		}
		{
			JRadioButton rdbtnKitchen = new JRadioButton("Kitchen");
			buttonGroup.add(rdbtnKitchen);
			rdbtnKitchen.setBounds(216, 339, 109, 23);
			contentPanel.add(rdbtnKitchen);
		}
		{
			JTextPane txtpnMakingAnAccusation = new JTextPane();
			txtpnMakingAnAccusation.setEditable(false);
			txtpnMakingAnAccusation.setText("Making An Accusation");
			txtpnMakingAnAccusation.setBounds(111, 0, 110, 20);
			contentPanel.add(txtpnMakingAnAccusation);
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
