package appliDemo;

import java.util.HashMap;

import appliDemo.interfacePlugin.StrategyAffichage;
import appliDemo.model.Person;
import plateforme.PluginLoader;
import plateforme.model.Descripteur;

public class Demo implements Runnable {
	private StrategyAffichage afficheur;

	private HashMap<String, Descripteur> descripteursPlugins;

	private final static PluginLoader LOADER = PluginLoader.getInstance();

	private static final String APP_NAME = "RDV Smarts";

	public Demo() {
		descripteursPlugins = LOADER.getDescripteursFor(APP_NAME);
		afficheur = (StrategyAffichage) LOADER.recupererPlugin(descripteursPlugins.get("Affichage mieux"), null);
	}

	public void run() {
		// Chargement de données
		Person p = new Person("machin", 80);

		// Traitement
		p.setAge(p.getAge() + 1);

		// Affichage des données
		affichage(p);
	}

	public void affichage(Person p) {
		afficheur.affichage(p);
	}
}
