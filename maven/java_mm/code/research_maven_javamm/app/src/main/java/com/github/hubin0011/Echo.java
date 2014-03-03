package com.github.hubin0011.app;

import com.github.hubin0011.modulea.EchoA;
import com.github.hubin0011.moduleb.EchoB;

public class Echo
{
    public static void main(String[] args){
	
		System.out.println("in app");
		String str = "maven mm";
		
		EchoA echoa = new EchoA();
		
		echoa.echo(str);
		
		EchoB echob = new EchoB();
		
		echob.echo(str);
	}
}
