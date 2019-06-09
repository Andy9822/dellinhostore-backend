package engsoft.dellinhostore.dao;

import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	public Game getByName(String name) {
		Game game;
		Session session = this.sessionFactory.openSession();
		TypedQuery<Game> query = session.createQuery("FROM Game WHERE name = :name");
		query.setParameter("name", name);
		try {
			game  = query.getSingleResult();
		} catch (Exception e) {
			game = null;
		}
		return game;
	}
	
	@SuppressWarnings("unchecked")
	public List<Game> getGameList() {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Game> query = session.createQuery("FROM Game");
		List<Game> gameList = query.getResultList();
		session.close();
		return gameList;
	}

}
