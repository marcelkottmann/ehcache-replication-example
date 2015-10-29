package com.github.pepe79;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class App {
	public static void main(String[] args) throws InterruptedException {

		CacheManager.create();

		Cache cache = CacheManager.getInstance().getCache("members");
		String id = CacheManager.getInstance().getCachePeerListener("RMI").getUniqueResourceIdentifier();
		cache.put(new Element(id, null));

		while (true) {
			System.out.println("--- Members ---");
			for (Object key : cache.getKeysWithExpiryCheck()) {
				System.out.println(key);
			}
			System.out.println("--------------");
			Thread.sleep(1000);
		}
	}
}
