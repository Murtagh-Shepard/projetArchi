/**
 * 
 */
package tier;

import appliDemo.interfacePlugin.StrategyAffichage;
import appliDemo.model.Person;

/**
 * @author alexandre
 *
 */
public class AffichageMieux implements StrategyAffichage {
	@Override
	public void affichage(Person p) {
		System.out.println("personne : " + p);
	}
}
