package net.mcreator.gts.procedures;

import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

public class SupporterNameProcedure {
	public static String execute() {
		double RNG = 0;
		RNG = Mth.nextInt(RandomSource.create(), 1, 14);
		if (RNG == 1) {
			return "Hane";
		}
		if (RNG == 2) {
			return "Ocean Men";
		}
		if (RNG == 3) {
			return "M3Donboi";
		}
		if (RNG == 4) {
			return "yeebeeseadog";
		}
		if (RNG == 5) {
			return "lemon";
		}
		if (RNG == 6) {
			return "Qerkn";
		}
		if (RNG == 7) {
			return "CustomStoryGatherers";
		}
		if (RNG == 8) {
			return "\u94C3\u00B7\u5915";
		}
		if (RNG == 9) {
			return "\u53EF\u7231\u7684\u540C\u5FD7";
		}
		if (RNG == 10) {
			return ":)";
		}
		if (RNG == 11) {
			return "Tina";
		}
		if (RNG == 12) {
			return "\u7231\u53D1\u7535\u7528\u6237_318a9";
		}
		if (RNG == 13) {
			return "\u6653\u73A5";
		}
		if (RNG == 14) {
			return "MemezRDreamz";
		}
		if (RNG == 15) {
			return "\u7231\u53D1\u7535\u7528\u6237_eb646";
		}
		if (RNG == 14) {
			return "\u7EA2\u9B54\u9986\u54B2\u591C";
		}
		return "";
	}
}








