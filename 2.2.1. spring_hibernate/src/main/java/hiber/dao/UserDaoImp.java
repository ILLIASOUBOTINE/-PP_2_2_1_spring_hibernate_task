package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCarModelAndSeries(String model, int series) {
      Session session = sessionFactory.getCurrentSession();
      String hql = "Select u From User u Join u.car c Where c.model = :model and c.series = :series";
      TypedQuery<User> query =  session.createQuery(hql, User.class);
      query.setParameter("model",model);
      query.setParameter("series",series);

      try {
         return query.getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }


}
