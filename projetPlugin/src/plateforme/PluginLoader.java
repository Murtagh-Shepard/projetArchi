/**
 * 
 */
package plateforme;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;

/**
 * The Class PluginLoader.
 *
 * @author alexandre
 */
public class PluginLoader {

	private static Map<Class<?>, Object> pluginMap;
	private static HashMap configMap;

	private static final PluginLoader instance = new PluginLoader();

	public static final PluginLoader getInstance() {
		return instance;
	}

	public PluginLoader() {
		pluginMap = new HashMap<>();

		Yaml yaml = new Yaml();
		try {
			InputStream inputStream = new FileInputStream("config.yml");

			configMap = yaml.load(inputStream);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Gets the plugin.
	 *
	 * @return the plugin
	 */
	public Object getPlugin(Class<?> classInterface) {
		Class<?> cl = null;
		Object result = pluginMap.get(classInterface);
		if (result != null) {
			return result;
		}

		try {
			HashMap interfaceMap = (HashMap) configMap.get(classInterface.getName());
			Set<Integer> keys = interfaceMap.keySet();
			for (Integer key : keys) {
				HashMap pluginMap = (HashMap) interfaceMap.get(key);
				cl = Class.forName((String) pluginMap.get("nom"));
				if (classInterface.isAssignableFrom(cl)) {
					result = cl.getDeclaredConstructor().newInstance();
				}
			}

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
