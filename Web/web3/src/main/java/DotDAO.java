import com.sun.istack.internal.NotNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class DotDAO implements DAO<Dot, Integer> {
    private final SessionFactory factory;

    public DotDAO(@NotNull final SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(@NotNull final Dot responseObject) {
        try (final Session session = factory.openSession()) {

            session.beginTransaction();

            session.save(responseObject);

            session.getTransaction().commit();
        }
    }

    @Override
    public Dot read(@NotNull final Integer key) {
        try (final Session session = factory.openSession()) {

            final Dot responseObject = session.get(Dot.class, key);

            return responseObject != null ? responseObject : new Dot();
        }
    }

    @Override
    public void update(@NotNull final Dot responseObject) {
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.update(responseObject);

            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(@NotNull final Dot responseObject) {
        try (Session session = factory.openSession()) {

            session.beginTransaction();

            session.delete(responseObject);

            session.flush();

            session.refresh(responseObject);

            session.getTransaction().commit();

        } catch (Exception e) {
            factory.getCurrentSession().getTransaction().rollback();
        }
    }
}
