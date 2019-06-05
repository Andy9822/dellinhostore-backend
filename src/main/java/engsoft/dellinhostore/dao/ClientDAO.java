package engsoft.dellinhostore.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import engsoft.dellinhostore.model.Client;
import engsoft.dellinhostore.util.HibernateUtil;

public class ClientDAO {

private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public void save(Client client) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(client);
		tx.commit();
		session.close();
	}

	public void update(Client client) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(client);
		tx.commit();
		session.close();
	}

	public void delete(Client client) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(client);
		tx.commit();
		session.close();
	}

	public Client getById(long id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Client client = (Client) session.get(Client.class, id);
		tx.commit();
		session.close();
		return client;
	}
	
	@SuppressWarnings("unchecked")
	public List<Client> getClientList() {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Client> query = session.createQuery("FROM Client");
		List<Client> clientList = query.getResultList();
		session.close();
		return clientList;
	}

	@SuppressWarnings("unchecked")
	public Client getByEmail(String email) {
		Session session = this.sessionFactory.openSession();
		TypedQuery<Client> query = session.createQuery("FROM Client WHERE email = :email");
		query.setParameter("email", email);
		Client client  = query.getSingleResult();
		return client;
	}
}
