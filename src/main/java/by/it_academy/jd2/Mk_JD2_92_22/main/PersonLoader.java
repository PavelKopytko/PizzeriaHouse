package by.it_academy.jd2.Mk_JD2_92_22.main;

import javax.persistence.EntityManager;

public class PersonLoader {

    public static void main(String[] args) {
        Person person = new Person(null, 35,"Pavel", "Kopytko");
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        HibernateUtil.close();
    }
}
