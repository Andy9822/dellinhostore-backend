package engsoft.dellinhostore.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.dellinhostore.model.Platform;
import engsoft.dellinhostore.util.HibernateUtil;

public class PlatformDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Platform platform) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(platform);
		tx.commit();
		session.close();
	}
	
	public Platform getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Platform platform = (Platform) session.get(Platform.class, id);
		tx.commit();
		session.close();
		return platform;
	}
	
	public void update(Platform platform) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(platform);
		tx.commit();
		session.close();
	}
	
	public void delete(Platform platform) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(platform);
		tx.commit();
		session.close();
	}

	
	@SuppressWarnings("unchecked")
	public List<Platform> getAll() {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Platform> query = session.createQuery("FROM Platform");
		List<Platform> result = query.getResultList();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Platform getByName(String name) {
		Platform platform;
		Session session = this.sessionFactory.openSession();
		TypedQuery<Platform> query = session.createQuery("FROM Platform WHERE name = :name");
		query.setParameter("name", name);
		try {
			platform  = query.getSingleResult();
		} catch (Exception e) {
			platform = null;
		}
		return platform;
	}

}
