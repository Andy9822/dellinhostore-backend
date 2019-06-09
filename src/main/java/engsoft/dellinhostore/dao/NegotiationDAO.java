package engsoft.dellinhostore.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.dellinhostore.enums.TransactionStatus;
import engsoft.dellinhostore.model.Negotiation;
import engsoft.dellinhostore.util.HibernateUtil;

public class NegotiationDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Negotiation negotiation) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(negotiation);
		tx.commit();
		session.close();
	}

	public void update(Negotiation negotiation) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(negotiation);
		tx.commit();
		session.close();
	}

	public void delete(Negotiation negotiation) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(negotiation);
		tx.commit();
		session.close();
	}

	public Negotiation getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Negotiation negotiation = (Negotiation) session.get(Negotiation.class, id);
		tx.commit();
		session.close();
		return negotiation;
	}

	public Negotiation getById(String name) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Negotiation negotiation = (Negotiation) session.get(Negotiation.class, name);
		tx.commit();
		session.close();
		return negotiation;
	}

	@SuppressWarnings("unchecked")
	public void deleteNegotiationsByAdvertId(long advert_id) {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Negotiation> query = session.createQuery("FROM Negotiation WHERE advert_id = :advert_id");
		query.setParameter("advert_id", advert_id);
		List<Negotiation> negotiationList = query.getResultList();
		for (Negotiation negotiation : negotiationList) {
			delete(negotiation);
		}
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Negotiation> getNegotiationList() {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Negotiation> query = session.createQuery("FROM Negotiation");
		List<Negotiation> negotiationList = query.getResultList();
		session.close();
		return negotiationList;
	}

	@SuppressWarnings("unchecked")
	public void closeNegotiationsByAdvertId(long advert_id) {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Negotiation> query = session.createQuery("FROM Negotiation WHERE advert_id = :advert_id AND status =:status");
		query.setParameter("advert_id", advert_id);
		query.setParameter("status", TransactionStatus.WAITING_ANSWER.ordinal());
		List<Negotiation> negotiationList = query.getResultList();
		for (Negotiation negotiation : negotiationList) {
			negotiation.close();
			update(negotiation);

		}
		session.close();
	}
}
