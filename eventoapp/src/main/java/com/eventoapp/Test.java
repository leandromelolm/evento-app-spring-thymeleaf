package com.eventoapp;

import java.util.ArrayList;
import java.util.List;

public class Test {		

	public static void main(String[] args) {
		
		List<String> acessos = new ArrayList<>();
		
		acessos.add("2007-02-10 18:06:35.756902Z");
		acessos.add("2008-02-10 18:06:35.756902Z");
		acessos.add("1500-02-10 18:06:35.756902Z");
		acessos.add("2019-02-10 18:06:35.756902Z");
		acessos.add("2007-02-10 18:06:35.756902Z");
		acessos.add("2008-02-10 18:06:35.756902Z");
		acessos.add("3009-02-10 18:06:35.756902Z");
		acessos.add("5010-02-10 18:06:35.756902Z");
		acessos.add("1011-02-10 18:06:35.756902Z");
		acessos.add("0012-02-10 18:06:35.756902Z");
		acessos.add("2013-02-10 18:06:35.756902Z");
		acessos.add("7014-02-10 18:06:35.756902Z");
		acessos.add("2023-02-10 18:06:35.756902Z");
		acessos.add("2014-02-10 18:06:35.756902Z");	
		
		System.out.println("List: "+acessos);		
		
		while(acessos.size() >=4) {
			System.out.println("Remover: " + acessos.remove(0));
			System.out.println("tamanho fila: "+ acessos.size());
		}
		
		System.out.println("tamanho fila final: "+ acessos.size());		

		for (String s : acessos) {
		    System.out.println(s);		   
		}		
	}
}
