package clueless;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;

public class Movement extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Movement dialog = new Movement();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Movement() {
		setBounds(100, 100, 875, 542);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel panel = new JPanel();
			panel.setBounds(10, 11, 828, 448);
			contentPanel.add(panel);
			panel.setLayout(null);
			{
				JTextPane txtpnMoveTo = new JTextPane();
				txtpnMoveTo.setBounds(390, 5, 47, 20);
				txtpnMoveTo.setEditable(false);
				txtpnMoveTo.setText("Move To");
				panel.add(txtpnMoveTo);
			}
			
			JRadioButton rdbtnStudy = new JRadioButton("Study");
			rdbtnStudy.setBounds(6, 39, 109, 23);
			panel.add(rdbtnStudy);
			
			JRadioButton rdbtnLibrary = new JRadioButton("Library");
			rdbtnLibrary.setBounds(6, 219, 109, 23);
			panel.add(rdbtnLibrary);
			
			JRadioButton rdbtnConservatory = new JRadioButton("Conservatory");
			rdbtnConservatory.setBounds(0, 406, 109, 23);
			panel.add(rdbtnConservatory);
			
			JRadioButton rdbtnBallroom = new JRadioButton("Ballroom");
			rdbtnBallroom.setBounds(346, 406, 109, 23);
			panel.add(rdbtnBallroom);
			
			JRadioButton rdbtnKitchen = new JRadioButton("Kitchen");
			rdbtnKitchen.setBounds(689, 406, 109, 23);
			panel.add(rdbtnKitchen);
			
			JRadioButton rdbtnBilliardRoom = new JRadioButton("Billiard Room");
			rdbtnBilliardRoom.setBounds(346, 219, 109, 23);
			panel.add(rdbtnBilliardRoom);
			
			JRadioButton rdbtnDiningRom = new JRadioButton("Dining Rom");
			rdbtnDiningRom.setBounds(689, 219, 109, 23);
			panel.add(rdbtnDiningRom);
			
			JRadioButton rdbtnHall = new JRadioButton("Hall");
			rdbtnHall.setBounds(346, 39, 109, 23);
			panel.add(rdbtnHall);
			
			JRadioButton rdbtnLounge = new JRadioButton("Lounge");
			rdbtnLounge.setBounds(689, 39, 109, 23);
			panel.add(rdbtnLounge);
			
			JRadioButton rdbtnStudyhallHallway = new JRadioButton("Study-Hall Hallway");
			rdbtnStudyhallHallway.setBounds(134, 39, 130, 23);
			panel.add(rdbtnStudyhallHallway);
			
			JRadioButton rdbtnLibrarybilliardRoomHallway = new JRadioButton("Library-Billiard Room Hallway");
			rdbtnLibrarybilliardRoomHallway.setBounds(134, 219, 163, 23);
			panel.add(rdbtnLibrarybilliardRoomHallway);
			
			JRadioButton rdbtnNewRadioButton = new JRadioButton("Conservatory-Ballroom Hallway");
			rdbtnNewRadioButton.setBounds(134, 406, 187, 23);
			panel.add(rdbtnNewRadioButton);
			
			JRadioButton rdbtnStudylibraryHallway = new JRadioButton("Study-Library Hallway");
			rdbtnStudylibraryHallway.setBounds(6, 126, 130, 23);
			panel.add(rdbtnStudylibraryHallway);
			
			JRadioButton rdbtnLibraryconservatoryHallway = new JRadioButton("Library-Conservatory Hallway");
			rdbtnLibraryconservatoryHallway.setBounds(6, 319, 169, 23);
			panel.add(rdbtnLibraryconservatoryHallway);
			
			JRadioButton rdbtnHallbilliardRoomHallway = new JRadioButton("Hall-Billiard Room Hallway");
			rdbtnHallbilliardRoomHallway.setBounds(346, 126, 154, 23);
			panel.add(rdbtnHallbilliardRoomHallway);
			
			JRadioButton rdbtnLoungediningRoomHallway = new JRadioButton("Lounge-Dining Room Hallway");
			rdbtnLoungediningRoomHallway.setBounds(623, 126, 175, 23);
			panel.add(rdbtnLoungediningRoomHallway);
			
			JRadioButton rdbtnBilliardRoomballroomHallway = new JRadioButton("Billiard Room-Ballroom Hallway");
			rdbtnBilliardRoomballroomHallway.setBounds(346, 319, 187, 23);
			panel.add(rdbtnBilliardRoomballroomHallway);
			
			JRadioButton rdbtnDiningRoomkitchenHallway = new JRadioButton("Dining Room-Kitchen Hallway");
			rdbtnDiningRoomkitchenHallway.setBounds(623, 319, 175, 23);
			panel.add(rdbtnDiningRoomkitchenHallway);
			
			JRadioButton rdbtnBilliardRoomdiningRoom = new JRadioButton("Billiard Room-Dining Room Hallway");
			rdbtnBilliardRoomdiningRoom.setBounds(501, 219, 198, 23);
			panel.add(rdbtnBilliardRoomdiningRoom);
			
			JRadioButton rdbtnHallloungeHallway = new JRadioButton("Hall-Lounge Hallway");
			rdbtnHallloungeHallway.setBounds(501, 39, 130, 23);
			panel.add(rdbtnHallloungeHallway);
			
			JRadioButton rdbtnBallroomkitchenHallway = new JRadioButton("Ballroom-Kitchen Hallway");
			rdbtnBallroomkitchenHallway.setBounds(501, 406, 145, 23);
			panel.add(rdbtnBallroomkitchenHallway);
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
