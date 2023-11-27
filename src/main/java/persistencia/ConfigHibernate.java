
package persistencia;

import modelo.*;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;


public class ConfigHibernate {
   private static SessionFactory sessionFactory;

    public static void load(){

        try {
            AnnotationConfiguration config = new AnnotationConfiguration();
            config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/argentina_programa?useTimezone=true&serverTimezone=UTC");
            config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            config.setProperty("hibernate.connection.username", "root");
            config.setProperty("hibernate.connection.password", "admin");
            config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            config.setProperty("hibernate.show_sql", "false");
            config.setProperty("hibernate.c3p0.min_size","0");
			config.setProperty("hibernate.hbm2ddl.auto", "update" );
			//config.setProperty("hibernate.hbm2ddl.auto", "create-drop" );
			config.setProperty("hibernate.c3p0.max_size","7");
			config.setProperty("hibernate.c3p0.timeout","100");
            config.setProperty("hibernate.c3p0.max_elements","100");
            config.setProperty("hibernate.c3p0.idle_test_period","100");
            config.setProperty("hibernate.c3p0.autoCommitOnClose","true");
            config.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");

			config.addPackage("modelo");
			config.addAnnotatedClass(Empleado.class);
			config.addAnnotatedClass(Cliente.class);
			config.addAnnotatedClass(DatosContacto.class);
			config.addAnnotatedClass(Especialidad.class);
			config.addAnnotatedClass(OperadorMesaAyuda.class);
			config.addAnnotatedClass(ReporteIncidencia.class);
			config.addAnnotatedClass(Servicio.class);
			config.addAnnotatedClass(Tecnico.class);
			config.addAnnotatedClass(TecnicoEspecialidad.class);
			config.addAnnotatedClass(ClienteServicio.class);

			sessionFactory = config.buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Error: HibernateUtil.HibernateException: " + e.getMessage());
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	public void initSession() {
		Session session = openSession();
	}
	public void cargarEspecialidadesBulk(List<String> denominaciones) {
		Session session = openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			for (String denominacion : denominaciones) {
				if (!existeEspecialidad(session, denominacion)) {
					Especialidad especialidad = new Especialidad(denominacion);
					saveEntity(session, especialidad);
				}
			}

			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
	}

	private boolean existeEspecialidad(Session session, String denominacion) {
		Query query = session.createQuery("SELECT COUNT(*) FROM Especialidad WHERE denominacion = :denominacion");
		query.setParameter("denominacion", denominacion);

		Long count = (Long) query.uniqueResult();
		return count != null && count > 0;
	}

	private void saveEntity(Session session, Object entity) {
		session.saveOrUpdate(entity);
	}


	public synchronized static Session openSession() {
		return getSessionFactory().openSession();
	}

	public static void closeSession(Session session) {
		session.close();
	}

	public synchronized static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			load();
		}

		return sessionFactory;
	}

	public synchronized static void closeSessionFactory() {
		try {
			if (sessionFactory != null) {
				sessionFactory.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sessionFactory = null;
		}
	}

	public boolean saveEntity(Session sezion, EntidadId entity) {
		Transaction tx = null;

		if (!sezion.getTransaction().isActive()) {
			tx = sezion.beginTransaction();
		}

		sezion.saveOrUpdate(entity);

		if (tx != null) {
			tx.commit();
		}

		return true;
	}

	public boolean deleteEntity(Session sezion, EntidadId entity) {
		Transaction tx = sezion.beginTransaction();

		sezion.delete(entity);

		tx.commit();

		return true;
	}

	public boolean updateEntity(Session sezion, EntidadId entity) {
		Transaction tx = null;

		if (!sezion.getTransaction().isActive()) {
			tx = sezion.beginTransaction();
		}

		sezion.update(entity);

		if (tx != null) {
			tx.commit();
		}

		return true;
	}

	public boolean deleteEntitys(Session sezion, List entities) {

		for (Iterator i = entities.iterator(); i.hasNext();) {
			EntidadId entity = (EntidadId) i.next();

			sezion.delete(entity);
		}

		return true;
	}

	public void destroy() {
		closeSessionFactory();
	}

}
