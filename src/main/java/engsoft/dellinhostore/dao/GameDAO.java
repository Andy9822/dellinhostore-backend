package engsoft.dellinhostore.dao;


import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.dellinhostore.model.Game;
import engsoft.dellinhostore.util.HibernateUtil;

public class GameDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public void save(Game game) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(game);
		tx.commit();
		session.close();
	}

	public void update(Game game) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(game);
		tx.commit();
		session.close();
	}

	public void delete(Game game) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(game);
		tx.commit();
		session.close();
	}

	public Game getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Game game = (Game) session.get(Game.class, id);
		tx.commit();
		session.close();
		return game;
	}
	
	public Game getById(String name) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Game game = (Game) session.get(Game.class, name);
		tx.commit();
		session.close();
		return game;
	}

}
