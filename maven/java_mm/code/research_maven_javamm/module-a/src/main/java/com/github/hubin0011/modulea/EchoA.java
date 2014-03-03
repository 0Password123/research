package com.github.hubin0011.modulea;

import com.github.hubin0011.modulecom.EchoCom;

public class EchoA
{
    public String echo(String str){
	
		System.out.println("in module-a");
		EchoCom echoc = new EchoCom();
		
		return echoc.echo(str);
	}
}
