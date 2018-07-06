package br.com.framework.Interface.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public interface InterfaceCrud<T> extends Serializable {
	
	void save(T obj) throws Exception;

	void persist(T obj) throws Exception;

	void saveOrUpdate(T obj) throws Exception;

	void update(T obj) throws Exception;
	
	T merge(T obj) throws Exception;
	
	// Carrega a lista de dados
	List<T> findList(Class<T> obj) throws Exception;
	
	Object findById(Class<T> entidade, Long id) throws Exception;
	
	T findPorId(Class<T> entidade, Long id) throws Exception;
	
	List<T> findListByQueryDinamica(String s) throws Exception;
	
	//Executar update com HQL
	void  executeUpdateQueryDinamica(String s) throws Exception;
	
	//Executar update com SQL
	void  executeUpdateSQLDinamica(String s) throws Exception;
	
	//Limpar a sessão do Hibernate
	void clearSession() throws Exception;
	
	//Retirar um objeto da sessão do Hibernate
	void evict(Object obj) throws Exception;
	
	Session getSession() throws Exception;
	
	List<?> getListSQLDinamica(String sql) throws Exception;
	
	// Para trabalhar com o JDBC do Spring
	JdbcTemplate geJdbcTemplate();
	
	SimpleJdbcTemplate geSimpleJdbcTemplate();
	
	SimpleJdbcInsert getSimpleJdbcInsert();
	
	Long totalRegistro(String table) throws Exception;
	
	Query obterQuery(String query) throws Exception;
	
	//Carregamento dinâmico com JSF e PrimeFaces
	List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception;
	
	
}
