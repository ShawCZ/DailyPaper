package com.shaw.daily.dp.main;

import com.shaw.daily.dp.main.index.IndexDelegate;

/**
 * Created by ${XCZ} on 2018/4/14.
 */
public class Rotuer {

	private static volatile Rotuer rotuer;
	IndexDelegate indexDelegate ;

	public static Rotuer getInstance(){
		if (rotuer == null){
			synchronized (Rotuer.class){
				if (rotuer == null){
					rotuer = new Rotuer();
				}
			}
		}
		return rotuer;
	}

	public  void setIndexDelegate(IndexDelegate delegate){
		indexDelegate = delegate;
	}

	public IndexDelegate getIndexDelegate(){
		return indexDelegate;
	}
}
