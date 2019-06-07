package engsoft.dellinhostore.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.dellinhostore.model.Dispute;
import engsoft.dellinhostore.util.HibernateUtil;

public class DisputeDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void save(Dispute dispute) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(dispute);
		tx.commit();
		session.close();
	}
	
	public Dispute getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Dispute dispute = (Dispute) session.get(Dispute.class, id);
		tx.commit();
		session.close();
		return dispute;
	}
	
	public void update(Dispute dispute) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(dispute);
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Dispute> getAll() {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Dispute> query = session.createQuery("FROM Dispute");
		List<Dispute> result = query.getResultList();
		session.close();
		return result;
	}

}
