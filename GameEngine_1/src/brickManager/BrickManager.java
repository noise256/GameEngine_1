package brickManager;

public class BrickManager {
	public static double getArmourDamageMultiplier(double armour) {
		return 1.0 / (armour * armour);
	}
}
