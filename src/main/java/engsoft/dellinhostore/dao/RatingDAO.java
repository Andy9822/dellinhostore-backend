package engsoft.dellinhostore.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.dellinhostore.model.Rating;
import engsoft.dellinhostore.util.HibernateUtil;

public class RatingDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Rating rating) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(rating);
		tx.commit();
		session.close();
	}
	
	public Rating getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Rating rating = (Rating) session.get(Rating.class, id);
		tx.commit();
		session.close();
		return rating;
	}
	
	public void update(Rating rating) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(rating);
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Rating> getAll() {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Rating> query = session.createQuery("FROM Rating");
		List<Rating> result = query.getResultList();
		session.close();
		return result;
	}

}
