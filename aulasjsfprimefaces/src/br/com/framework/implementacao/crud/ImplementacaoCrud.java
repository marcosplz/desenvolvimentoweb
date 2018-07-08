package br.com.framework.implementacao.crud;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.framework.Interface.crud.InterfaceCrud;
import br.com.hibernate.session.HibernateUtil;

@Component
@Transactional
public class ImplementacaoCrud<T> implements InterfaceCrud<T> {

	private static final long serialVersionUID = 1L;

	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Autowired
	private JdbcTemplateImpl JdbcTemplate;

	@Autowired
	private SimpleJdbcTemplateImpl SimpleJdbcTemplate;

	@Autowired
	private SimpleJdbcInsertImplements SimpleJdbcInsert;

	@Autowired
	private SimpleJdbcClassImpl SimpleJdbcTemplateImpl;

	@Override
	public void save(T obj) throws Exception {

		SessionFactoryValidate();
		sessionFactory.getCurrentSession().save(obj);
		executeFlushSession();
	}

	@Override
	public void persist(T obj) throws Exception {

		SessionFactoryValidate();
		sessionFactory.getCurrentSession().persist(obj);
		executeFlushSession();
	}

	@Override
	public void saveOrUpdate(T obj) throws Exception {

		SessionFactoryValidate();
		sessionFactory.getCurrentSession().saveOrUpdate(obj);
		executeFlushSession();
	}

	@Override
	public void update(T obj) throws Exception {

		SessionFactoryValidate();
		sessionFactory.getCurrentSession().update(obj);
		executeFlushSession();
	}
	
	public void delete(T obj) throws Exception{
		
		SessionFactoryValidate();
		sessionFactory.getCurrentSession().delete(obj);
		executeFlushSession();
	}


	@Override
	public T merge(T obj) throws Exception {

		SessionFactoryValidate();
		obj = (T) sessionFactory.getCurrentSession().merge(obj);
		executeFlushSession();
		return null;
	}

	@Override
	public List<T> findList(Class<T> entidade) throws Exception {

		StringBuilder query = new StringBuilder();
		query.append("select distinct(entity) from ").append(entidade.getSimpleName()).append(" entity ");

		List<T> lista = sessionFactory.getCurrentSession().createQuery(query.toString()).list();
		return lista;
	}

	@Override
	public Object findById(Class<T> entidade, Long id) throws Exception {

		SessionFactoryValidate();
		Object obj = sessionFactory.getCurrentSession().load(getClass(), id);
		return obj;
	}

	@Override
	public T findPorId(Class<T> entidade, Long id) throws Exception {

		SessionFactoryValidate();
		T obj = (T) sessionFactory.getCurrentSession().load(getClass(), id);
		return obj;
	}

	@Override
	public List<T> findListByQueryDinamica(String s) throws Exception {

		List<T> lista = new ArrayList<T>();
		lista = sessionFactory.getCurrentSession().createQuery(s).list();
		return lista;
	}

	@Override
	public void executeUpdateQueryDinamica(String s) throws Exception {

		SessionFactoryValidate();
		sessionFactory.getCurrentSession().createQuery(s).executeUpdate();
		executeFlushSession();
	}

	@Override
	public void executeUpdateSQLDinamica(String s) throws Exception {

		SessionFactoryValidate();
		sessionFactory.getCurrentSession().createSQLQuery(s).executeUpdate();
		executeFlushSession();
	}

	@Override
	public void clearSession() throws Exception {

		sessionFactory.getCurrentSession().clear();
	}

	@Override
	public void evict(Object obj) throws Exception {

		SessionFactoryValidate();
		sessionFactory.getCurrentSession().evict(obj);
	}

	@Override
	public Session getSession() throws Exception {

		SessionFactoryValidate();
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<?> getListSQLDinamica(String sql) throws Exception {

		List<?> lista = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return lista;
	}

	@Override
	public JdbcTemplate geJdbcTemplate() {
		return JdbcTemplate;
	}

	@Override
	public SimpleJdbcTemplate geSimpleJdbcTemplate() {
		return SimpleJdbcTemplate;
	}

	@Override
	public SimpleJdbcInsert getSimpleJdbcInsert() {
		return SimpleJdbcInsert;
	}

	@Override
	public Long totalRegistro(String table) throws Exception {

		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) from ").append(table);
		return JdbcTemplate.queryForLong(sql.toString());
	}

	@Override
	public Query obterQuery(String query) throws Exception {

		SessionFactoryValidate();
		Query returnQuery = sessionFactory.getCurrentSession().createQuery(query.toString());
		return returnQuery;
	}

	@Override
	public List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception {

		SessionFactoryValidate();
		List<T> lista = new ArrayList<T>();
		lista = sessionFactory.getCurrentSession().createQuery(query).setFirstResult(iniciaNoRegistro)
				.setMaxResults(maximoResultado).list();
		return lista;
	}

	public SimpleJdbcClassImpl getSimpleJdbcTemplateImpl() {
		return SimpleJdbcTemplateImpl;
	}

	private void transactionValidate() {

		if (!sessionFactory.getCurrentSession().getTransaction().isActive()) {
			sessionFactory.getCurrentSession().beginTransaction();
		}
	}

	private void SessionFactoryValidate() {
		if (sessionFactory == null) {
			sessionFactory = HibernateUtil.getSessionFactory();
		}

		transactionValidate();
	}

	private void AjaxProcessCommit() {
		sessionFactory.getCurrentSession().beginTransaction().commit();
	}

	private void AjaxProcessRollback() {
		sessionFactory.getCurrentSession().beginTransaction().rollback();
	}

	private void executeFlushSession() {
		sessionFactory.getCurrentSession().flush();
	}

	public List<Object[]> getListSQLDinamicaArray(String sql) throws Exception {

		SessionFactoryValidate();
		List<Object[]> lista = (List<Object[]>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return lista;
	}
}
