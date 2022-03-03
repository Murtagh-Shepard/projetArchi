/**
 * 
 */
package plateforme;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;

import plateforme.model.Descripteur;

/**
 * The Class PluginLoader.
 */
public class PluginLoader {

	/** The Constant CONF_PATH. */
	private final String CONF_PATH = "config.yml";

	/** The descripteurs. */
	private HashMap<String, Descripteur> descripteurs;

	/** The Constant INSTANCE. */
	private static final PluginLoader INSTANCE = new PluginLoader();

	/**
	 * Instantiates a new plugin loader.
	 */
	public PluginLoader() {

	}

	/**
	 * Gets the single instance of PluginLoader.
	 *
	 * @return single instance of PluginLoader
	 */
	public static final PluginLoader getInstance() {
		return INSTANCE;
	}

	/**
	 * Gets the descripteurs.
	 *
	 * @return the descripteurs
	 */
	public HashMap<String, Descripteur> getDescripteurs() {
		return descripteurs;
	}

	/**
	 * Sets the descripteurs.
	 *
	 * @param descripteurs the descripteurs
	 */
	public void setDescripteurs(HashMap<String, Descripteur> descripteurs) {
		this.descripteurs = descripteurs;
	}

	/**
	 * Parcours les descripteurs afin de lancer les plugins flaggés en autoRun dans
	 * des threads.
	 */
	private void autoRun() {
		// Parcours des descripteurs chargés
		for (Descripteur d : descripteurs.values()) {
			// On regarde les plugins flaggés autorun
			if (d.isAutoRun()) {
				// On lance un thread par plugin flaggé
				try {
					Thread t = new Thread((Runnable) this.recupererPlugin(d, null));
					System.out.println("Plugin autorun : " + d.getClassName() + " a été chargé !");
					t.start();
				} catch (SecurityException | IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		PluginLoader loader = PluginLoader.getInstance();
		// Chargement des descripteurs
		loader.chargerDescripteurs();
		// Lancement des plugins autorun
		loader.autoRun();
		// loader.notifyInit();

	}

	/**
	 * Méthode de chargement des descripteurs.
	 *
	 */
	public void chargerDescripteurs() {
		// Initialisation des descripteurs
		this.descripteurs = new HashMap<String, Descripteur>();

		try {
			// Récupération du fichier de conf
			Yaml yaml = new Yaml();

			HashMap configMap;

			InputStream inputStream = new FileInputStream(CONF_PATH);

			configMap = yaml.load(inputStream);

			// Parcours du fichier de conf
			Set<Integer> keys = configMap.keySet();
			System.out.println(keys.size() + " plugin(s) trouvé !");
			for (Integer key : keys) {
				HashMap pluginMap = (HashMap) configMap.get(key);
				// Valorisation du descripteur
				Descripteur descripteur = new Descripteur();
				descripteur.setName((String) pluginMap.get("nom"));
				descripteur.setClassName((String) pluginMap.get("nomClasse"));
				descripteur.setAutoRun((Boolean) pluginMap.get("autorun"));
				List<String> reqs = (List<String>) pluginMap.get("requirements");
				if (reqs == null || !reqs.isEmpty()) {
					descripteur.setRequirements(reqs);
				}
				// Gestion arguments constructeur par défaut
				List<String> args = (List<String>) pluginMap.get("params");
				if (args == null || !args.isEmpty()) {
					descripteur.addArgs(args);
				}
				// Gestion des dépendences : valorisation du parent
				if (configMap.get("dependances") != null || pluginMap.get("dependances") != "") {
					if (pluginMap.get("dependances") != null) {
						System.out.println("Le plugin " + descripteur.getName() + " a une dépendance : " + pluginMap.get("dependances"));
					}
					descripteur.setDependency((String) pluginMap.get("dependances"));
				}
				// Ajout à la liste des descripteurs
				this.descripteurs.put(descripteur.getName(), descripteur);
				System.out.println("Descripteur : " + descripteur.getName() + " ajouté");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Récupération des plugins enfant du plugin entré en paramètre
	 *
	 * @param dependency the dependency
	 * @return the descripteurs for
	 */
	public static HashMap<String, Descripteur> getDescripteursFor(String dependency) {
		HashMap<String, Descripteur> descripteurs = new HashMap<String, Descripteur>();
		for (Descripteur d : INSTANCE.getDescripteurs().values()) {
			// On récupère tous les plugins enfant du plugin entré en paramètre
			if (d.getDependency() != null && d.getDependency().equals(dependency)) {
				descripteurs.put(d.getName(), d);
			}
		}
		return descripteurs;

	}

	/**
	 * Méthode de récupération d'instance du plugin passé en paramètre
	 *
	 * @param descripteur the descripteur du plugin
	 * @param args        the args
	 * @return the object
	 */
	public static Object recupererPlugin(Descripteur descripteur, Object[] args) {
		PluginLoader instance = PluginLoader.getInstance();
		Class classe;
		Constructor constructor;
		Object plugin = null;
		try {
			// Instanciation de la classe avec le contructeur, avec ou sans paramètres
			classe = Class.forName(descripteur.getClassName());
			if (descripteur.getArgs() != null) {
				constructor = classe.getConstructor(descripteur.getArgs());
			} else {
				constructor = classe.getConstructor(null);
			}
			plugin = constructor.newInstance(args);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException
				| SecurityException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return plugin;
	}
}
