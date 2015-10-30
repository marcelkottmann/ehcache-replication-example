package com.github.pepe79;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class App {
	public static void main(String[] args) throws InterruptedException {
		final String id = CacheManager.getInstance().getCachePeerListener("RMI").getUniqueResourceIdentifier();

		final CacheManager cacheManager = CacheManager.create();
		final Cache cache = cacheManager.getCache("members");

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Removing member: " + id);
				cache.remove(id);
				cacheManager.shutdown();
			}
		}));

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
