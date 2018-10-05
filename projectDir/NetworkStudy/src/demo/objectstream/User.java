package demo.objectstream;

import java.io.*;

public class User implements Serializable { //마킹인터페이스(추상..메소드없는거)->에코해줄라고..?ㅠㅠㅠ뭐라는거지
	private String name;
	private String id;
	private int age;

	// "name|*|id|*|age"
	// 프로토콜 설계 필요 없음...
	
	public User(){}

	public User(String name, String id, int age) {
		this.name = name;
		this.id = id;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", id=" + id + ", age=" + age + "]";
	}
}
