import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

public class DataBaseController implements Serializable {

    private SessionFactory factory;
    private DAO<Dot, Integer> resultDao;

    public void init() {
        factory = new Configuration().configure().buildSessionFactory();
        resultDao = new DotDAO(factory);
        System.out.println("Init of DB");
    }

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public DAO<Dot, Integer> getResultDao() {
        return resultDao;
    }

    public void setResultDao(DAO<Dot, Integer> resultDao) {
        this.resultDao = resultDao;
    }
}
