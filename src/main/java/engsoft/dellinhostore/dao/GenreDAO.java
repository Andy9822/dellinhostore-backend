package engsoft.dellinhostore.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.dellinhostore.model.Genre;
import engsoft.dellinhostore.util.HibernateUtil;

public class GenreDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Genre genre) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(genre);
		tx.commit();
		session.close();
	}
	
	public void delete(Genre genre) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(genre);
		tx.commit();
		session.close();
	}
	
	public Genre getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Genre genre = (Genre) session.get(Genre.class, id);
		tx.commit();
		session.close();
		return genre;
	}
	
	public void update(Genre genre) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(genre);
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Genre> getAll() {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Genre> query = session.createQuery("FROM Genre");
		List<Genre> result = query.getResultList();
		session.close();
		return result;
	}

	@SuppressWarnings("unchecked")
	public Genre getByName(String name) {
		Genre genre;
		Session session = this.sessionFactory.openSession();
		TypedQuery<Genre> query = session.createQuery("FROM Genre WHERE name = :name");
		query.setParameter("name", name);
		try {
			genre  = query.getSingleResult();
		} catch (Exception e) {
			genre = null;
		}
		return genre;
	}

}
