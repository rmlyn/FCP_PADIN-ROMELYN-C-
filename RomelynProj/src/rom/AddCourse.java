package rom;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class AddCourse extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField TbxCode;
	private JLabel lblNewLabel_1;
	private JTextField TbxInstructor;
	private JButton btnNewButton;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCourse frame = new AddCourse();
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
	public AddCourse() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Course Code");
		lblNewLabel.setBounds(36, 79, 130, 27);
		contentPane.add(lblNewLabel);
		
		TbxCode = new JTextField();
		TbxCode.setBounds(176, 77, 224, 30);
		contentPane.add(TbxCode);
		TbxCode.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Instructor");
		lblNewLabel_1.setBounds(36, 121, 130, 14);
		contentPane.add(lblNewLabel_1);
		
		TbxInstructor = new JTextField();
		TbxInstructor.setColumns(10);
		TbxInstructor.setBounds(176, 118, 224, 30);
		contentPane.add(TbxInstructor);
		
		btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = TbxCode.getText();
				String instructor = TbxInstructor.getText();
				
				if (!code.equals("") && !instructor.equals("")) {
					Course course = new Course(code, instructor);
					AddCourse(course);
				} else {
					JOptionPane.showMessageDialog(contentPane, "Fill up all fields...");
				}
			}
		});
		btnNewButton.setBounds(176, 159, 224, 23);
		contentPane.add(btnNewButton);
		
		lblNewLabel_2 = new JLabel("Add Course");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(163, 27, 95, 40);
		contentPane.add(lblNewLabel_2);
	}
	
	public void AddCourse(Course course) {
		String addQuery = "INSERT INTO courses (coursecode, instructor) VALUES (?,?)";
		
		try {
			Connection conn = DBConn.getConn();
			PreparedStatement addPstmt = conn.prepareStatement(addQuery);
			addPstmt.setString(1, course.getCode().toLowerCase());
			addPstmt.setString(2, course.getInstructor().toLowerCase());
			int affected = addPstmt.executeUpdate();
			
			if (affected > 0) {
				JOptionPane.showMessageDialog(contentPane, "Added new course: " + course.getCode().toUpperCase());
				TbxCode.setText("");
				TbxInstructor.setText("");
			} else {
				JOptionPane.showMessageDialog(contentPane, "Failed to add: " + course.getCode().toUpperCase() + "... Try again...");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, "Failed: " + e.getMessage());
		}
	}
}

class Course {
	String code;
	String instructor;
	
	public Course(String code, String instructor) {
		this.code = code;
		this.instructor = instructor;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getInstructor() {
		return instructor;
	}
}
