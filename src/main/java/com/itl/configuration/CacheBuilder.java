package com.itl.configuration;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheBuilder {

	@Autowired
	private CacheManager cacheManager;

	private Logger logger = LoggerFactory.getLogger(CacheBuilder.class);

	/**
	 * This method allows populating cache at runtime potentially . The idea is that
	 * when you have multiple keys with which we have to search then consider one of
	 * them as master key and rest as alternative keys. Pass the field name of the
	 * master key in first index and remaining in the subsequent places in fields.
	 * 
	 * @param cacheContainerName - name of the cache the data should be initialized
	 *                           to
	 * @param fields             - field names to be mapped as key-to-key mapping;
	 *                           first index should hold the master key
	 * @param cacheObjList       - list of actual objects to be put into cache
	 * @param initCacheSetup     - should master key to actual object cache be
	 *                           pre-populated ?
	 */
	@SuppressWarnings("rawtypes")
	public void initializeCache(String cacheContainerName, String fields[], List cacheObjList, Boolean initCacheSetup) {
		if(null == cacheObjList)	{
			logger.warn("$$$ Cache not initialized for {} ... Datasource empty $$$", cacheContainerName);
			return;
		}

		for (Object cacheObj : cacheObjList) {
			try {
				if (fields != null && fields.length > 0) {
					Field masterKey = null;
					try {
						masterKey = cacheObj.getClass().getDeclaredField(fields[0]);
						masterKey.setAccessible(true);
					} catch (NoSuchFieldException e) {
						logger.warn("Cache initialization - master key field not found in object.");
						return;
					}
					if (Objects.nonNull(initCacheSetup) && initCacheSetup) {
						cacheManager.getCache(cacheContainerName).put(masterKey.get(cacheObj), cacheObj);
					}
					if (fields.length > 1) {
						for (int i = 1; i < fields.length; i++) {
							Field altKey = null;
							if (Objects.isNull(fields[i])) {
								continue;
							}
							try {
								altKey = cacheObj.getClass().getDeclaredField(fields[i]);
							} catch (NoSuchFieldException e) {
								logger.info("Cache initialization - {} : no such field found in object.", fields[i]);
								continue;
							}
							altKey.setAccessible(true);
							cacheManager.getCache(cacheContainerName).put(altKey.get(cacheObj),
									masterKey.get(cacheObj));
						}
					}
				} else {
					logger.debug("No keys provided for cache initialization.");
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				logger.warn("Failed to build cache for : {}, due to inaccessible field in object.", cacheContainerName);
			}
		}
	}
}
