package rom;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class UpdateStudent extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField TbxStudId;
	private JTextField TbxName;
	private StudentRecordManagement parentDirectoryManagement;

	/**
	 * Create the frame.
	 */
	public UpdateStudent(StudentRecordManagement ermParent, String StudId, String name, String program) {
		this.parentDirectoryManagement = ermParent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("UPDATE STUDENT INFORMATION");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 414, 32);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("STUDENT ID:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 70, 414, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("NAME:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(10, 134, 414, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("PROGRAM:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_2.setBounds(10, 202, 414, 14);
		contentPane.add(lblNewLabel_1_2);
		
		TbxStudId = new JTextField(StudId);
		TbxStudId.setEditable(false);
		TbxStudId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		TbxStudId.setBounds(10, 89, 414, 20);
		contentPane.add(TbxStudId);
		TbxStudId.setColumns(10);
		
		TbxName = new JTextField(name);
		TbxName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		TbxName.setColumns(10);
		TbxName.setBounds(10, 153, 414, 20);
		contentPane.add(TbxName);
		
		String[] programs = {"BSAIS", "BEED", "BSE", "BSIT", "BSA", "BSHM", "BSC"};
		JComboBox CbxProg = new JComboBox(programs);
		CbxProg.setBounds(10, 221, 414, 22);
		CbxProg.setSelectedItem(program);
		contentPane.add(CbxProg);
		
		JButton BtnUpdate = new JButton("SAVE CHANGES");
		BtnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName = TbxName.getText();
				String newStudId = TbxStudId.getText();
				String newProg = (String)CbxProg.getSelectedItem();
				
				if (newName.equals("") || newStudId.equals("") || newProg == null) {
					JOptionPane.showMessageDialog(contentPane, "Fill up fields...");
				} else {
					int confirm = JOptionPane.showConfirmDialog(
					        contentPane,
					        "Are you sure you want to save " + name.toUpperCase() + "'s new information?",
					        "Confirm Update",
					        JOptionPane.YES_NO_OPTION
					    );

					    if (confirm == JOptionPane.YES_OPTION) {
					    	Student student = new Student(newStudId, newName, newProg);
							UpdateInfo(student);
							parentDirectoryManagement.LoadStudents();
							dispose();
					    } else {
					    	return;
					    }
				}
			}
		});
		BtnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		BtnUpdate.setBounds(28, 259, 175, 23);
		contentPane.add(BtnUpdate);
		
		JButton BtnCancel = new JButton("CANCEL");
		BtnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		BtnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		BtnCancel.setBounds(231, 259, 175, 23);
		contentPane.add(BtnCancel);
	}
	
	public void UpdateInfo(Student student) {
		String updateQuery = "UPDATE students SET name=?, year=? WHERE studid=?";
		
		try {
			Connection connection = DBConn.getConn();
			PreparedStatement updatePstmt = connection.prepareStatement(updateQuery);
			updatePstmt.setString(1, student.getName().toLowerCase());
			updatePstmt.setString(2, student.getYear());
			updatePstmt.setString(3, student.getStudid());
			int affected = updatePstmt.executeUpdate();
			
			if (affected > 0) {
				JOptionPane.showMessageDialog(contentPane, "Successfully updated information for " + student.getName().toUpperCase() + "!");
			} else {
				JOptionPane.showMessageDialog(contentPane, "Failed... Try again...");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, "Failed: " + e.getMessage());
		}
	}

}
