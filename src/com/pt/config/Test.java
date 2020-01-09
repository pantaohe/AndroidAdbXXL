package com.pt.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

public class Test {
	public static void main(String[] args) {
		String abc = "456";
		Map<String, Object> map = new HashMap<String, Object>();
		String a = "A";
		String jexlExp = "a = ConstantImg.screen1;";
		JexlEngine jexl=new JexlEngine();
		Expression e = jexl.createExpression(jexlExp);
		JexlContext jc = new MapContext();
		
		for (String key : map.keySet()) {
            jc.set(key, map.get(key));
        }
		e.evaluate(jc);
//		String xx = (String)evaluate;
		System.out.println(a);
		
	
	}

}
