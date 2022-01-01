package org.walkmod.javalang.javadoclinks;

import java.util.List;

public class MethodLink implements Link {

	private String className;

	private String name;

	private List<String> arguments;

	public MethodLink(String className, String name, List<String> arguments) {
		this.className = className;
		this.name = name;
		this.arguments = arguments;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getArguments() {
		return arguments;
	}

	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}

}
