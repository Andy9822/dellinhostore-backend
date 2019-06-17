package engsoft.dellinhostore.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.dellinhostore.model.Trade;
import engsoft.dellinhostore.util.HibernateUtil;

public class TradeDAO {

private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public void save(Trade trade) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(trade);
		tx.commit();
		session.close();
	}

	public void update(Trade trade) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(trade);
		tx.commit();
		session.close();
	}

	public void delete(Trade trade) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(trade);
		tx.commit();
		session.close();
	}

	public Trade getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Trade trade = (Trade) session.get(Trade.class, id);
		tx.commit();
		session.close();
		return trade;
	}
	
	public Trade getById(String name) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Trade trade = (Trade) session.get(Trade.class, name);
		tx.commit();
		session.close();
		return trade;
	}
	
	@SuppressWarnings("unchecked")
	public List<Trade> getTradeList() {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Trade> query = session.createQuery("FROM Trade");
		List<Trade> tradeList = query.getResultList();
		session.close();
		return tradeList;
	}
	
	@SuppressWarnings("unchecked")
	public Trade getByDescription(String offer) {
		Trade trade;
		Session session = this.sessionFactory.openSession();
		TypedQuery<Trade> query = session.createQuery("FROM Trade WHERE offer = :offer");
		query.setParameter("offer", offer);
		try {
			trade  = query.getSingleResult();
		} catch (Exception e) {
			trade = null;
		}
		return trade;
	}
}
