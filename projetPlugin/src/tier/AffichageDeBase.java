/**
 * 
 */
package tier;

import base.interfacePlugin.StrategyAffichage;
import base.model.Person;

/**
 * @author alexandre
 *
 */
public class AffichageDeBase implements StrategyAffichage {
	@Override
	public void affichage(Person p) {
		System.out.println(p);
	}
}
