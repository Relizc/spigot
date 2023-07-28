package net.itsrelizc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.itsrelizc.lifesteal.Recipe;
import net.itsrelizc.lifesteal.Shop;

public class RecipeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		
		Recipe recipe = new Recipe(player);
		recipe.openRecipe(0);
		
		return true;
	}

}
