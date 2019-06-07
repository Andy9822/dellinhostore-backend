package engsoft.dellinhostore.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.dellinhostore.model.Manager;
import engsoft.dellinhostore.util.HibernateUtil;

public class ManagerDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Manager admin) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(admin);
		tx.commit();
		session.close();
	}
	
	public void update(Manager admin) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(admin);
		tx.commit();
		session.close();
	}
	
	public Manager getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Manager client = (Manager) session.get(Manager.class, id);
		tx.commit();
		session.close();
		return client;
	}
	public void delete(Manager admin) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(admin);
		tx.commit();
		session.close();
	}
}
