package base;

import base.interfacePlugin.StrategyAffichage;
import base.model.Person;
import plateforme.PluginLoader;

public class Main {
	private StrategyAffichage afficheur;

	public Main() {
		PluginLoader pluginLoader = PluginLoader.getInstance();
		afficheur = (StrategyAffichage) pluginLoader.getPlugin(StrategyAffichage.class);
		// CHargement de données
		Person p = new Person("machin", 80);
		// Traitement
		p.setAge(p.getAge() + 1);

		// Affichage des données
		affichage(p);
	}

	public static void main(String[] args) {
		new Main();
	}

	public void affichage(Person p) {
		afficheur.affichage(p);
	}
}
