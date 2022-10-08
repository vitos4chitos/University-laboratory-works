import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

@ManagedBean
@SessionScoped

public class Results {
    private Dot dot;
    private String validator;
    private List<Dot> dots;
    private DataBaseController dbController;

    @PostConstruct
    public void init() {
        dot = new Dot();
        dots = new ArrayList<>();
        dbController = new DataBaseController();
        dbController.init();
    }





    public void addDot() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(checker()) {
            validator = "Всё хорошо, продолжайте работу";
            dot.setResult(validate(dot.getX(), dot.getY(), dot.getR()));
            dot.setJsessionid(facesContext.getExternalContext().getSessionId(false));
            try {
                dbController.getResultDao().create(dot);
            } catch (Exception e) {
                e.printStackTrace();
            }
            dots.add(dot);
            System.out.println(dot.getX() + " " + dot.getY() + " " + dot.getR());
            dot = new Dot();
        }
        else{
            validator = "Вы не всё заполнили";
        }
//        String url = "jdbc:postgresql://se.ifmo.ru:5432/pg/studs";
//        Properties props = new Properties();
//        props.setProperty("user", "s284691");
//        props.setProperty("password", "eyv368");
//        props.setProperty("ssl", "true");
//        try {
//            Connection conn = DriverManager.getConnection(url, props);
//            System.out.println("dbTestConnection!");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void getJs(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String x = facesContext.getExternalContext().getRequestParameterMap().get("x");
        String y = facesContext.getExternalContext().getRequestParameterMap().get("y");
        String r = facesContext.getExternalContext().getRequestParameterMap().get("r");
        dot.setX(Double.parseDouble(x));
        dot.setY(Double.parseDouble(y));
        dot.setR(Double.parseDouble(r));
        System.out.println(dot.getX() + " " + dot.getY() + " " + dot.getR());
        dot.setResult(validate(dot.getX(), dot.getY(), dot.getR()));
        dot.setJsessionid(facesContext.getExternalContext().getSessionId(false));
        try {
            dbController.getResultDao().create(dot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dots.add(dot);
        dot = new Dot();
    }


    public void reset(){
        Iterator<Dot> respIterator = dots.iterator();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        while (respIterator.hasNext()) {
            Dot cur = respIterator.next();
                try {
                    dbController.getResultDao().delete(cur);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                respIterator.remove();
            }
        System.out.println("Все удалил");
        }

    public Dot getDot(){
        return dot;
    }

    public void setDot(Dot dot){
        this.dot = dot;
    }

    public List<Dot> getDots(){
        return dots;
    }

    public void clearDots(){
        dots = new ArrayList<Dot>();
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public DataBaseController getDbController() {
        return dbController;
    }

    public void setDbController(DataBaseController dbController) {
        this.dbController = dbController;
    }

    private boolean checker(){
        if(dot.getX() != null && dot.getY() != null && dot.getR() != null){
            return dot.getX() >= -2 && dot.getX() <= 2 && dot.getY() >= -3 && dot.getY() <= 3 && dot.getR() >= 1
                    && dot.getR() <= 5;
        }
        else
            return false;
    }

    private boolean validate(double x, double y, double r){
        if (x >= 0 && y >= 0)
            return firstHalf(x, y, r);
        else if (x <= 0 && y <= 0)
            return thirdHalf(x, y, r);
        else if (x >= 0 && y <= 0)
            return fourthHalf(x, y, r);
        else
            return secondHalf(x, y, r);
    }

    private boolean firstHalf(double x, double y, double r){
        return x <= r && y <= r;
    }

    private boolean secondHalf(double x, double y, double r){
        return false;
    }

    private boolean thirdHalf(double x, double y, double r){
        return (x * x + y * y <= r * r);
    }

    private boolean fourthHalf(double x, double y, double r){
        return (2 * x + y >= -r);
    }
}
