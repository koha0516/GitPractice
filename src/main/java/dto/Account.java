package dto;

public class Account {
	private int id;
	private String name;
	private String mail;
	private int age;
	private String gender; 
	private int tel;
	private String salt;
	private String password;
	private String hashedPassword;
	
	
	
	public Account(int id, String name, String mail, int age, String gender, int tel, String salt, String password,
			String hashedPassword) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.age = age;
		this.gender = gender;
		this.tel = tel;
		this.salt = salt;
		this.password = password;
		this.hashedPassword = hashedPassword;
	}

	public Account(int id, String name, String mail, String salt, String password, String hashedPassword) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.salt = salt;
		this.password = password;
		this.hashedPassword = hashedPassword;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getTel() {
		return tel;
	}

	public void setTel(int tel) {
		this.tel = tel;
	}
	
	
}
