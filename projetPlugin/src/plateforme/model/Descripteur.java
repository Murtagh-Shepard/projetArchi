package plateforme.model;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Descripteur.
 */
public class Descripteur {
	
	/** The name. */
	protected String name;
	
	/** The class name. */
	protected String className;
	
	/** The auto run. */
	protected boolean autoRun;
	
	/** The args. */
	protected Class[] args; 
	
	/** The requirements. */
	protected List<String> requirements;
	
	/** The dependency. */
	protected String dependency;
	
	/** The loaded. */
	protected boolean loaded;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the autoRun
	 */
	public boolean isAutoRun() {
		return autoRun;
	}

	/**
	 * @param autoRun the autoRun to set
	 */
	public void setAutoRun(boolean autoRun) {
		this.autoRun = autoRun;
	}

	/**
	 * @return the args
	 */
	public Class[] getArgs() {
		return args;
	}

	/**
	 * @param args the args to set
	 */
	public void setArgs(Class[] args) {
		this.args = args;
	}
	
	/**
	 * Adds the args.
	 *
	 * @param argClasses the arg classes
	 */
	public void addArgs(List<String> argClasses) {
		args=new Class[argClasses.size()];
		for (int i=0; i<argClasses.size(); i++) {
			try {
				args[i]=Class.forName(argClasses.get(i));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the requirements
	 */
	public List<String> getRequirements() {
		return requirements;
	}

	/**
	 * @param requirements the requirements to set
	 */
	public void setRequirements(List<String> requirements) {
		this.requirements = requirements;
	}

	/**
	 * @return the dependency
	 */
	public String getDependency() {
		return dependency;
	}

	/**
	 * @param dependency the dependency to set
	 */
	public void setDependency(String dependency) {
		this.dependency = dependency;
	}

	/**
	 * @return the loaded
	 */
	public boolean isLoaded() {
		return loaded;
	}

	/**
	 * @param loaded the loaded to set
	 */
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
}
