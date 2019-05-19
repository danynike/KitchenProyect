package com.kitchen.backend.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@SuppressWarnings({"rawtypes","unchecked"})
public class GenericDAOImpl<T, ID extends Serializable>{

	private Class persistentClass;

	@PersistenceContext(unitName = "manager")
	private EntityManager manager;

	public GenericDAOImpl() {

		Type tipo = getClass().getGenericSuperclass();

		if (tipo instanceof ParameterizedType) {
			this.persistentClass = (Class) ((ParameterizedType) tipo)
					.getActualTypeArguments()[0];
		} else {
			this.persistentClass = null;
		}
	}

	public synchronized EntityManager getEntityManager() {
		return manager;
	}

	/**
	 * Convierte un elemento en un array que lo contiene
	 * @param element
	 * @return
	 */
	protected String[] convertToArray(String element) {
		String[] mappedFilters = null;
		if(element != null && !element.trim().equals("")) {
			mappedFilters = new String[]{element};
		}
		return mappedFilters;
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#find(ID)
	 */
	public T find(ID id) {

		return (T) getEntityManager().find(persistentClass, id);
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findAll()
	 */
	public List<T> findAll() {

		return findAll("");
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findAll()
	 */
	public List<T> findAll(String orderClausule) {

		if (orderClausule == null){
			orderClausule = "";
		}

		return getEntityManager().createQuery("from " + persistentClass.getSimpleName() + " " + orderClausule).getResultList();
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#create(java.lang.Object)
	 */
	public T create(T entity) {

		getEntityManager().persist(entity);
		return entity;
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#merge(T)
	 */
	
	public T merge(T entity) {

		return getEntityManager().merge(entity);
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#delete(ID)
	 */
	public void delete(ID id) {
		T toDel = find(id);
		if (toDel != null) {
			getEntityManager().remove(toDel);
		}
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#delete(ID)
	 */
	public void deleteEntity(T entity) {
		if (entity != null) {
			getEntityManager().remove(entity);
		}
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#deleteAll(java.util.List)
	 */
	public void deleteAll(List<ID> ids) {
		for (ID id : ids) {
			T toDel = find(id);
			if (toDel != null) {
				getEntityManager().remove(toDel);
			}
		}
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#deleteAllEntities(java.util.List)
	 */
	public void deleteAllEntities(List<T> entities) {

		for (T entity : entities) {
			getEntityManager().remove(entity);
		}
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findByProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	public List<T> findByProperty(String property, Object value) {

		String where = "";
		if (property != null && !property.equals("")) {
			where = " where model." + property + " = :value";
		}

		String queryString = "select model from " + persistentClass.getSimpleName() + " model " + where;
		Query query = getEntityManager().createQuery(queryString);
		if (property != null && !property.equals("")) {
			query.setParameter("value", value);
		}

		return query.getResultList();
	}

	public List<T> findByPropertySorted(String property, Object value, String sortClausule) {
		String where = "";
		if (property != null && !property.equals("")) {
			where = " where model." + property + " = :value";
		}

		if (sortClausule == null){
			sortClausule = "";
		}

		String queryString = "select model from " + persistentClass.getSimpleName() + " model " + where + " " + sortClausule;
		Query query = getEntityManager().createQuery(queryString);
		if (property != null && !property.equals("")) {
			query.setParameter("value", value);
		}

		return query.getResultList();
	}


	/**
	 * @see com.millenium.comun.dao.GenericDAO#findByPropertySortedPaged(int, int, java.lang.String, java.lang.Object, java.lang.String)
	 */
	public List<T> findByPropertySortedPaged(int start, int limit, String property, Object value, String sortClausule) {
		String where = "";
		if (property != null && !property.equals("")) {
			where = " where model." + property + " = :value";
		}

		if (sortClausule == null){
			sortClausule = "";
		}

		String queryString = "select model from " + persistentClass.getSimpleName() + " model " + where + " " + sortClausule;
		Query query = getEntityManager().createQuery(queryString);
		if (property != null && !property.equals("")) {
			query.setParameter("value", value);
		}

		query.setFirstResult(start);
		query.setMaxResults(limit);

		return query.getResultList();
	}


	/**
	 * @see com.millenium.comun.dao.GenericDAO#findUniqueByProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	public T findUniqueByProperty(String property, Object value) {

		String where = "";
		if (property != null && !property.equals("")) {
			where = " where model." + property + " = :value";
		}

		String queryString = "select model from " + persistentClass.getSimpleName() + " model " + where;
		Query query = getEntityManager().createQuery(queryString);
		//query.setHint("org.hibernate.cacheable", true);
		if (property != null && !property.equals("")) {
			query.setParameter("value", value);
		}

		List<T> result = query.getResultList();
		//List<T> result = query.list();

		return !result.isEmpty() ? result.get(0) : null;
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findUniqueLastByProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	public T findUniqueLastByProperty(String property, Object value) {
		String where = "";
		if (property != null && !property.equals("")) {
			where = " where model." + property + " = :value";
		}

		String queryString = "select model from " + persistentClass.getSimpleName() + " model " + where + " order by id desc";
		Query query = getEntityManager().createQuery(queryString);
		//query.setHint("org.hibernate.cacheable", true);
		if (property != null && !property.equals("")) {
			query.setParameter("value", value);
		}

		query.setMaxResults(1);
		List<T> result = query.getResultList();

		return !result.isEmpty() ? result.get(0) : null;
	}

	/**
	 * @see com.millenium.seguridad.dao.UsuarioDAO#findAllPaged(int, int)
	 */
	public List<T> findAllPaged(int start, int limit) {

		return findAllPaged(start, limit, "");
	}

	/**
	 * @see com.millenium.seguridad.dao.UsuarioDAO#findAllPaged(int, int)
	 */
	public List<T> findAllPaged(int start, int limit, String orderClausule) {

		if (orderClausule == null){
			orderClausule = "";
		}

		Query query = getEntityManager().createQuery("from " + persistentClass.getSimpleName() + " " + orderClausule);

		query.setFirstResult(start);
		query.setMaxResults(limit);

		return query.getResultList();
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findByPropertyPaged(int, int,
	 *      java.lang.String, java.lang.String)
	 */
	public List<T> findByPropertyPaged(int start, int limit, String property, String value) {

		String where = "";
		if (property != null && !property.equals("")) {
			where = " where model." + property + " = :value";
		}

		String queryString = "select model from " + persistentClass.getSimpleName() + " model where " + where;
		Query query = getEntityManager().createQuery(queryString);
		if (property != null && !property.equals("")) {
			query.setParameter("value", value);
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);

		return query.getResultList();
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findNumByProperty(java.lang.String,
	 *      java.lang.String)
	 */
	public Integer findNumByProperty(String property, Object value) {

		String where = "";
		if (property != null && !property.equals("")) {
			where = " where model." + property + " = :value";
		}

		String queryString = "select count(*) from " + persistentClass.getSimpleName() + " model " + where;
		Query query = getEntityManager().createQuery(queryString);
		if (property != null && !property.equals("")) {
			query.setParameter("value", value);
		}

		List result = query.getResultList();

		if (result != null && !result.isEmpty()) {
			return ((Long) result.get(0)).intValue();
		}

		return 0;
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findByFilter(java.lang.String, java.lang.String)
	 */
	public List<T> findByFilter(String propertyFilter, String valueFilter, boolean isExactMatch) {

		return findByFilter(convertToArray(propertyFilter), valueFilter, isExactMatch);
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findByFilter(java.lang.String[], java.lang.String)
	 */
	public List<T> findByFilter(String propertyFilter[], String valueFilter, boolean isExactMatch) {

		return findByFilterSorted(propertyFilter, valueFilter, "", isExactMatch);
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findByFilter(java.lang.String, java.lang.String)
	 */
	public List<T> findByFilterSorted(String propertyFilter, String valueFilter, String sortClausule, boolean isExactMatch) {

		return findByFilterSorted(convertToArray(propertyFilter), valueFilter, sortClausule, isExactMatch);
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findByFilter(java.lang.String[], java.lang.String)
	 */
	public List<T> findByFilterSorted(String propertyFilter[], String valueFilter, String sortClausule, boolean isExactMatch) {

		String where = "";
		if (propertyFilter != null && propertyFilter.length >0) {
			boolean primero = true;
			for(String property : propertyFilter) {
				if(!property.equals("")) {
					if(primero) {
						if(isExactMatch) {
							where += " where model." + property + " = '" + valueFilter + "' ";
						} else {
							where += " where upper(model." + property + ") like '%" + valueFilter.toUpperCase() + "%' ";
						}
						primero = false;
					} else {
						if(isExactMatch) {
							where += " or model." + property + " = '" + valueFilter + "' ";
						} else {
							where += " or upper(model." + property + ") like '%" + valueFilter.toUpperCase() + "%' ";
						}
					}
				}
			}
		}

		if (sortClausule == null){
			sortClausule = "";
		}

		String queryString = "select model from " + persistentClass.getSimpleName() + " model " + where + " " + sortClausule;
		Query query = getEntityManager().createQuery(queryString);

		return query.getResultList();
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findByFilterPaged(int, int, java.lang.String, java.lang.String)
	 */
	public List<T> findByFilterPaged(int start, int limit, String propertyFilter, String valueFilter, boolean isExactMatch) {

		return findByFilterPaged(start, limit, convertToArray(propertyFilter), valueFilter, isExactMatch);
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findByFilterPaged(int, int, java.lang.String[], java.lang.String)
	 */
	public List<T> findByFilterPaged(int start, int limit, String propertyFilter[], String valueFilter, boolean isExactMatch) {

		return findByFilterPagedSorted(start, limit, propertyFilter, valueFilter, "", isExactMatch);
	}

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findByFilterPaged(int, int, java.lang.String, java.lang.String)
	 */
	public List<T> findByFilterPagedSorted(int start, int limit, String propertyFilter, String valueFilter, String sortClausule, boolean isExactMatch) {

		return findByFilterPagedSorted(start, limit, convertToArray(propertyFilter), valueFilter, sortClausule, isExactMatch);
	}

	/**
     * @param isExactMatch
     * @see com.millenium.comun.dao.GenericDAO#findByFilterPagedSorted(int, int, java.lang.String[], java.lang.String, java.lang.String)
     */
    public List<T> findByFilterPagedSorted(int start, int limit, String[] propertyFilter, String valueFilter, String sortClausule, boolean isExactMatch) {
        String where = "";
        String nombreLista = "";
        if (propertyFilter != null && propertyFilter.length >0) {
            boolean primero = true;
            for(String property : propertyFilter) {
                if(!property.equals("")) {

                    String[] listaSplit  = property.split(":");
                    String prefijo = "model.";
                    if(listaSplit.length > 1){
                        property = property.replace(":", ".");
                        nombreLista = listaSplit[0];
                        prefijo = "";
                    }

                    if(primero) {

                        if(isExactMatch) {
                            where += " where "+prefijo + property + " = '" + valueFilter + "' ";
                        } else {
                            where += " where upper("+prefijo + property + ") like '%" + valueFilter.toUpperCase() + "%' ";
                        }
                        primero = false;
                    } else {
                        if(isExactMatch) {
                            where += " or "+prefijo + property + " = '" + valueFilter + "' ";
                        } else {
                            where += " or upper("+prefijo + property + ") like '%" + valueFilter.toUpperCase() + "%' ";
                        }
                    }
                }
            }
        }

        if (sortClausule == null){
            sortClausule = "";
        }

        String queryString = "";
        if(nombreLista.equals("")){
            queryString = "select model from " + persistentClass.getSimpleName() + " model " + where + " " + sortClausule;
        }else{
            queryString = "select model from " + persistentClass.getSimpleName() + " model JOIN model."+nombreLista+" "+nombreLista+" "+ where + " " + sortClausule;;
        }

        Query query = getEntityManager().createQuery(queryString);
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.getResultList();
    }

	/**
	 * @see com.millenium.comun.dao.GenericDAO#findNumByFilter(java.lang.String, java.lang.String)
	 */
	public Integer findNumByFilter(String propertyFilter, String valueFilter, boolean isExactMatch) {

		return findNumByFilter(convertToArray(propertyFilter), valueFilter, isExactMatch);
	}

	/**
	 * @param isExactMatch
	 * @see com.millenium.comun.dao.GenericDAO#findNumByFilter(java.lang.String[], java.lang.String)
	 */
	public Integer findNumByFilter(String[] propertyFilter, String valueFilter, boolean isExactMatch) {
		String where = "";
		if (propertyFilter != null && propertyFilter.length >0) {
			boolean primero = true;
			for(String property : propertyFilter) {
				if(primero) {
					if(isExactMatch) {
						where += " where model." + property + " = '" + valueFilter + "' ";
					} else {
						where += " where upper(model." + property + ") like '%" + valueFilter.toUpperCase() + "%' ";
					}
					primero = false;
				} else {
					if(isExactMatch) {
						where += " or model." + property + " = '" + valueFilter + "' ";
					} else {
						where += " or upper(model." + property + ") like '%" + valueFilter.toUpperCase() + "%' ";
					}
				}
			}

		}

		String queryString = "select count(*) from " + persistentClass.getSimpleName() + " model " + where;
		Query query = getEntityManager().createQuery(queryString);

		List result = query.getResultList();

		if (result != null && !result.isEmpty()) {
			return ((Long) result.get(0)).intValue();
		}

		return 0;
	}

	/**
	 * @return the persistentClass
	 */
	public Class getPersistentClass() {
		return persistentClass;
	}

	/**
	 * @param persistentClass
	 *            the persistentClass to set
	 */
	public void setPersistentClass(Class persistentClass) {
		this.persistentClass = persistentClass;
	}

	public List<T> createAll(List<T> entities) {
		for(T entity : entities){
			getEntityManager().persist(entity);
		}
		return entities;
	}

	public List<T> mergeAll(List<T> entities) {
		List<T> list = new ArrayList<T>();
		if(entities==null) {
			return list;
		}
		for(T entity : entities){
			list.add(getEntityManager().merge(entity));
		}
		return list;
	}

	public void cleanCache(Object id) {
		if (id == null) {
		    getEntityManager().getEntityManagerFactory().getCache().evict(persistentClass);
		} else {
		    getEntityManager().getEntityManagerFactory().getCache().evict(persistentClass, id);
		}
	}

}