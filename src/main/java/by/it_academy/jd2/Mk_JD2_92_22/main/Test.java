package by.it_academy.jd2.Mk_JD2_92_22.main;

import javax.persistence.EntityManager;

public class Test {

    public static void main(String[] args) {
        Person person = new Person(null, 35,"Pavel", "Kopytko");
        EntityManager em = HibernateUtil.getEntityManager();

        Person personF = em.find(Person.class, person.getId());
    }
}
