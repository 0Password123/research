package com.github.hubin0011.moduleb;

import com.github.hubin0011.modulecom.EchoCom;

public class EchoB
{
    public String echo(String str){
	
		System.out.println("in module-b");
		EchoCom echoc = new EchoCom();
		
		return echoc.echo(str);
	}
}
