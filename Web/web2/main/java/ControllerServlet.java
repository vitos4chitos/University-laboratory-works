import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class ControllerServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(ControllerServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if(check_Request(req)){
            logger.info("Forward to CleatUpdatet");
            req.getRequestDispatcher("/ClearUpdate").forward(req, resp);
        }
        else if(check_Data(req)){
            logger.info("Forward to AreaCheckServlet");
            req.getRequestDispatcher("/AreaCheckServlet").forward(req, resp);
        }
        else{
            logger.info("Error with variable.");
            resp.getWriter().println("ErrorType: Error with variable");
        }
    }

    private boolean check_Data(HttpServletRequest req){
        Map<String, String[]> data = req.getParameterMap();
        logger.info(data.get("x")[0] + " " + data.get("y")[0] + " " + data.get("r")[0]);
        return data.containsKey("x") && data.containsKey("y") && data.containsKey("r");
    }

    private boolean check_Request(HttpServletRequest req){
        return req.getParameterMap().containsKey("reset");
    }
}
