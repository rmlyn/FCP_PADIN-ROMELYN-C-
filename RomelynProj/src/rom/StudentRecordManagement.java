package rom;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentRecordManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable TblStudents;
	private DefaultTableModel model;
	private JLabel lblNewLabel;
	private JLabel lblName;
	private JLabel lblName_1;
	private JLabel lblProgram;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField TbxStudId;
	private JTextField TbxName;
	private JComboBox CbxYear;
	private JLabel lblNewLabel_3;
	private JButton BtnAdd;
	private JLabel lblSearch;
	private JTextField TbxSearch;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentRecordManagement frame = new StudentRecordManagement();
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
	public StudentRecordManagement() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 200, 791, 246);
		contentPane.add(scrollPane);
		
		String[] columnNames = {"ID", "NAME", "YEAR"};
		model = new DefaultTableModel(columnNames, 0);
		TblStudents = new JTable(model);
		TblStudents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int SelectedStud = TblStudents.getSelectedRow();
				
				if (SelectedStud >= 0) {
					String StudId = TblStudents.getValueAt(SelectedStud, 0).toString();
					String name = TblStudents.getValueAt(SelectedStud, 1).toString();
					String program = TblStudents.getValueAt(SelectedStud, 2).toString();
					
					int option = JOptionPane.showOptionDialog(
							contentPane,
							"What do you want to do with \"" + name + "\"?",
							"Options",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							new String[]{"Delete", "Update", "Cancel"},
							"Cancel"
						);

						if (option == 0) {
							int confirm = JOptionPane.showConfirmDialog(
							        contentPane,
							        "Are you sure you want to delete " + name.toUpperCase() + "?",
							        "Confirm Delete",
							        JOptionPane.YES_NO_OPTION
							    );

							    if (confirm == JOptionPane.YES_OPTION) {
							        DeleteStudent(StudId);
							    } else {
							    	return;
							    }
						} else if (option == 1) {
							new UpdateStudent(StudentRecordManagement.this, StudId, name, program).setVisible(true);
						}
				}
			}
		});
		scrollPane.setViewportView(TblStudents);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1)); 
		panel.setBounds(220, 11, 581, 140);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel("ADD NEW STUDENT");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 15, 407, 14);
		panel.add(lblNewLabel);
		
		lblName = new JLabel("STUDENT ID:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblName.setBounds(20, 44, 86, 14);
		panel.add(lblName);
		
		lblName_1 = new JLabel("NAME:");
		lblName_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblName_1.setBounds(300, 46, 86, 14);
		panel.add(lblName_1);
		
		lblProgram = new JLabel("YEAR:");
		lblProgram.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProgram.setBounds(20, 73, 86, 14);
		panel.add(lblProgram);
		
		TbxStudId = new JTextField();
		TbxStudId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		TbxStudId.setBounds(110, 41, 170, 20);
		panel.add(TbxStudId);
		TbxStudId.setColumns(10);
		
		TbxName = new JTextField();
		TbxName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		TbxName.setColumns(10);
		TbxName.setBounds(354, 43, 206, 20);
		panel.add(TbxName);
		
		String[] year = {"1st", "2nd", "3rd", "4th"};
		CbxYear = new JComboBox(year);
		CbxYear.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CbxYear.setSelectedIndex(-1);
		CbxYear.setBounds(110, 69, 170, 22);
		panel.add(CbxYear);
		
		BtnAdd = new JButton("ADD");
		BtnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String StudId = TbxStudId.getText();
				String name = TbxName.getText();
				String prog = (String)CbxYear.getSelectedItem();
				
				if (StudId.equals("") || name.equals("") || prog == null) {
					JOptionPane.showMessageDialog(panel, "Fill up all fields...");
				} else {
					Student student = new Student(StudId, name, prog);
					AddNew(student);
					LoadStudents();
				}
			}
		});
		BtnAdd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		BtnAdd.setBounds(20, 102, 540, 23);
		panel.add(BtnAdd);
		
		lblNewLabel_1 = new JLabel("BSIT STUDENT");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 22));
		lblNewLabel_1.setBounds(10, 31, 200, 50);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("by ROMELYN PADIN");
		lblNewLabel_2.setFont(new Font("Century", Font.ITALIC, 12));
		lblNewLabel_2.setBounds(10, 108, 200, 14);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("RECORD MANAGEMENT");
		lblNewLabel_3.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_3.setBounds(10, 72, 200, 37);
		contentPane.add(lblNewLabel_3);
		
		lblSearch = new JLabel("SEARCH:");
		lblSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSearch.setBounds(10, 175, 86, 14);
		contentPane.add(lblSearch);
		
		TbxSearch = new JTextField();
		TbxSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchValue = TbxSearch.getText();
				
				if (searchValue.equals("")) {
					LoadStudents();
				} else {
					SearchStudent(searchValue);
				}
			}
		});
		TbxSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		TbxSearch.setColumns(10);
		TbxSearch.setBounds(77, 172, 444, 20);
		contentPane.add(TbxSearch);
		
		JButton BtnAddCourse = new JButton("ADD COURSE");
		BtnAddCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddCourse().setVisible(true);
			}
		});
		BtnAddCourse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		BtnAddCourse.setBounds(533, 171, 268, 23);
		contentPane.add(BtnAddCourse);
		
		LoadStudents();
	}
	
	public void LoadStudents() {
		model.setRowCount(0);
		boolean found = false;
		String selectQuery = "SELECT * FROM students";
		
		try {
			Connection connection = DBConn.getConn();
			Statement selectStmt = connection.createStatement();
			ResultSet res = selectStmt.executeQuery(selectQuery);
			
			while (res.next()) {
				found = true;
				model.addRow(new Object[] {
						res.getString("studid"),
						res.getString("name").toUpperCase(),
						res.getString("year")
				});
			}
			
			if (!found) {
				model.addRow(new Object[] {"No student record found..."});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, "Failed: " + e.getMessage());
		}
	}
	
//	public String[] GetCourses() {
//		String selectQuery = "SELECT coursecode FROM courses";
//		
//		try {
//			Connection conn = DBConn.getConn();
//			Statement selectStmt = conn.createStatement();
//			ResultSet res = selectStmt.executeQuery(selectQuery);
//			
//			while (res.next()) {
//				String courses = res.getString("coursecode");
//			}
//		} catch (Exception e) {
//			
//		}
//	}
	
	public void AddNew(Student students) {
		boolean found = false;
		String isExisting = "SELECT studid FROM students WHERE studid=?";
		String addStud = "INSERT INTO students (studid, name, year) VALUES (?,?,?)";
		
		try {
			Connection connection = DBConn.getConn();
			PreparedStatement checkPstmt = connection.prepareStatement(isExisting);
			checkPstmt.setString(1, students.getStudid());
			ResultSet res = checkPstmt.executeQuery();
			
			while (res.next()) {
				found = true;
				JOptionPane.showMessageDialog(contentPane, "Student ID " + students.getStudid() + " already exist. Delete or update existing record.");
			}
			
			if (!found) {
				int response = JOptionPane.showConfirmDialog(contentPane, "Add " + students.getName().toUpperCase() + "?", "Confirm Add", JOptionPane.YES_NO_OPTION);
				
				if (response == JOptionPane.YES_OPTION) {
					PreparedStatement addPstmt = connection.prepareStatement(addStud);
					addPstmt.setString(1, students.getStudid());
					addPstmt.setString(2, students.getName().toLowerCase());
					addPstmt.setString(3, students.getYear());
					int affected = addPstmt.executeUpdate();
					
					if (affected > 0) {
						JOptionPane.showMessageDialog(contentPane, students.getName().toUpperCase() + " added!");
						TbxName.setText("");
						TbxStudId.setText("");
						CbxYear.setSelectedIndex(-1);
					} else {
						JOptionPane.showMessageDialog(contentPane, "Failed to add " + students.getName().toUpperCase() + "! Try again...");
					}
				} else {
					return;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, "Failed to add: " + e.getMessage());
		}
	}
	
	public void SearchStudent(String SearchValue) {
		model.setRowCount(0);
		boolean found = false;
		String value = "%" + SearchValue + "%";
		String searchQuery = "SELECT * FROM students WHERE name LIKE ? OR studid LIKE ?";
		
		try {
			Connection connection = DBConn.getConn();
			PreparedStatement searchPstmt = connection.prepareStatement(searchQuery);
			searchPstmt.setString(1, value);
			searchPstmt.setString(2, value);
			ResultSet res = searchPstmt.executeQuery();
			
			while (res.next()) {
				found = true;
				model.addRow(new Object[] {
						res.getString("studid"),
						res.getString("name").toUpperCase(),
						res.getString("year")
				});
			}
			
			if (!found) {
				model.addRow(new Object[] {"No student found..."});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, "Failed to search: " + e.getMessage());
		}
	}
	
	public void DeleteStudent(String StudId) {
		String deleteQuery = "DELETE FROM students WHERE studid=?";
		
		try {
			Connection connection = DBConn.getConn();
			PreparedStatement deletePstmt = connection.prepareStatement(deleteQuery);
			deletePstmt.setString(1, StudId);
			int affected = deletePstmt.executeUpdate();
			
			if (affected > 0) {
				JOptionPane.showMessageDialog(contentPane, "Successfully deleted student with Student ID " + StudId);
				LoadStudents();
			} else {
				JOptionPane.showMessageDialog(contentPane, "Failed to delete... Try again...");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, "Failed to search: " + e.getMessage());
		}
	}
}
