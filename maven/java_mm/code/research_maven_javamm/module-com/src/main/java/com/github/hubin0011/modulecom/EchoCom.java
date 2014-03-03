package com.github.hubin0011.modulecom;

public class EchoCom
{
    public String echo(String str){
		System.out.println("in module-com");
		return "com-" + str;
	}
}
