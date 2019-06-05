package engsoft.dellinhostore.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.dellinhostore.model.Advert;
import engsoft.dellinhostore.util.HibernateUtil;

public class AdvertDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public void save(Advert advert) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(advert);
		tx.commit();
		session.close();
	}

	public void update(Advert advert) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(advert);
		tx.commit();
		session.close();
	}

	public void delete(Advert advert) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(advert);
		tx.commit();
		session.close();
	}

	public Advert getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Advert advert = session.get(Advert.class, id);
		tx.commit();
		session.close();
		return advert;
	}
	
	public Advert getById(String name) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Advert advert = session.get(Advert.class, name);
		tx.commit();
		session.close();
		return advert;
	}

	@SuppressWarnings("unchecked")
	public List<Advert> getAdvertList() {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Advert> query = session.createQuery("FROM Advert");
		List<Advert> advertList = query.getResultList();
		session.close();
		return advertList;
	}

}
