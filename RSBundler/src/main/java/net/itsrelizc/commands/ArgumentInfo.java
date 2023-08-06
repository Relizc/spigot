package net.itsrelizc.commands;

public abstract class ArgumentInfo {
		
	public Class<?> type = null;
	public String customClassName = null;
	public String argname = "argument";
	public boolean required = false;
	public String description = null;
	
}