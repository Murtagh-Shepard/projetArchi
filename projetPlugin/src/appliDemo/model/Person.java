package appliDemo.model;

/**
 * @author alexandre
 *
 */
public class Person {

	@Override
	public String toString() {
		return "Person [nom=" + nom + ", age=" + age + "]";
	}

	public Person(String nom, int age) {
		super();
		this.setNom(nom);
		this.age = age;
	}

	private String nom;
	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

}
