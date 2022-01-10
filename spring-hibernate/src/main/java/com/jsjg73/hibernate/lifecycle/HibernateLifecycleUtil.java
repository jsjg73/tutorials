package com.jsjg73.hibernate.lifecycle;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.SessionImplementor;

public class HibernateLifecycleUtil {
	
	public static List<Object> getManagedEntities(Session session) {
		Map.Entry<Object, EntityEntry>[] entries = ((SessionImplementor) session).getPersistenceContext().reentrantSafeEntityEntries();
		return Arrays.stream(entries).map(a-> a.getValue()).collect(Collectors.toList());
	}
}
