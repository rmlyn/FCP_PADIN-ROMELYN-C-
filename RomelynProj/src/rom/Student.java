package rom;

public class Student {
	String studid;
	String name;
	String year;
	
	public Student(String studid, String name, String year) {
		this.studid = studid;
		this.name = name;
		this.year = year;
	}
	
	public String getStudid() {
		return studid;
	}
	
	public String getName() {
		return name;
	}
	
	public String getYear() {
		return year;
	}
	
}
