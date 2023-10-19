package net.itsrelizc.mcserver.lxscripttreeexecutorrealversion;

import net.itsrelizc.mcserver.Commands.CommandOpenFile;
import net.itsrelizc.mcserver.Commands.CommandOpenIDE;
import net.itsrelizc.mcserver.Commands.CommandSetOnLoad;
import net.itsrelizc.mcserver.IDEManager.CommandDeleteLine;
import net.itsrelizc.mcserver.IDEManager.CommandEditLine;
import net.itsrelizc.mcserver.IDEManager.CommandQuit;
import net.itsrelizc.mcserver.IDEManager.ControlIDEs;
import net.itsrelizc.mcserver.LanguageManager.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class LXScriptTreeExecutorRealVersion extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginCommand("openide").setExecutor(new CommandOpenIDE());
        Bukkit.getPluginCommand("quitide").setExecutor(new CommandQuit());
        Bukkit.getPluginCommand("ed").setExecutor(new CommandEditLine());
        Bukkit.getPluginCommand("del").setExecutor(new CommandDeleteLine());
        Bukkit.getPluginCommand("openfromfile").setExecutor(new CommandOpenFile());
        Bukkit.getPluginCommand("setstarter").setExecutor(new CommandSetOnLoad());

        Bukkit.getPluginManager().registerEvents(new ControlIDEs(),this);




        if(FileIOManager.getFile("properties.lxp").exists()){
            String contents = FileIOManager.getStringFromFile("//scripts//"+FileIOManager.getStringFromFile("//properties.lxp").trim()+".lx");




            Lexer lexer = new Lexer(contents);
            lexer.lex();
            System.out.println("E");
            System.out.println("AND NOW I AM ABOUT TO PRN");
            for(LexerObject l:lexer.getLexxed()){
                System.out.println(l.getInner_Val() + " | "+l.getValue() + " | "+l.getType());
            }


            AstGen gen = new AstGen(lexer.getLexxed());
            gen.parseFunctions();
            Map<String, ParsedFunction> funcs = gen.parseInFuncs();
            Map<String,ParsedClass > classes = gen.parseClasses();

            System.out.println(classes.toString());




            ScriptExecutor executor = new ScriptExecutor(funcs,classes);
            executor.executeMain();

        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
