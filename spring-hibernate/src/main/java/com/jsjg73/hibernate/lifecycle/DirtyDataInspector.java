package com.jsjg73.hibernate.lifecycle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class DirtyDataInspector extends EmptyInterceptor{
	private final List<Object> dirtyEntities = new ArrayList<Object>();

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		dirtyEntities.add(entity);
		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
	}
	
	public int size() {
		return dirtyEntities.size();
	}
	
	public void clearDirtyEntities() {
		dirtyEntities.clear();
	}
	
	public List<Object> getDirtyEntities(){
		return dirtyEntities;
	}
}
