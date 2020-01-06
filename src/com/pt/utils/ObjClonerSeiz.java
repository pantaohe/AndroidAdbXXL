package com.pt.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjClonerSeiz {

	@SuppressWarnings("unchecked")
	public static <T>T  CloneObj(T obj){
		T retobj = null;
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ios = null;
		try {
			//写入流中
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			//从流中读取
			ios = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
			retobj=(T) ios.readObject();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (baos != null){
					baos.close();
				}
			} catch (IOException e) {
			}finally {
				baos = null;
			}
			try {
				if (oos != null){
					oos.close();
				}
			} catch (IOException e) {
			}finally {
				oos = null;
			}
			try {
				if (ios != null){
					ios.close();
				}
			} catch (IOException e) {
			}finally {
				ios = null;
			}
		}
		return retobj;
	}


}