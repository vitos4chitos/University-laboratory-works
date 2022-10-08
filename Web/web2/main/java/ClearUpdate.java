import Results.ResultList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class ClearUpdate extends HttpServlet {

    private Logger logger = Logger.getLogger(ClearUpdate.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameterMap().containsKey("reset")){
            reset(req, resp);
            logger.info("Table was cleared");
        }
        else{
            logger.info("Error with variable.");
            resp.getWriter().println("ErrorType: Error with variable");
        }
    }


    private void reset(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("resultList", new Results.ResultList());
        PrintWriter writer = resp.getWriter();
        writer.println("<table id=\"result_table\" align=\"center\" border=\"1\" width=\"80%\">\n" +
                "    <tr>\n" +
                "        <th width=\"15%\">\n" +
                "            X\n" +
                "        </th>\n" +
                "        <th width=\"15%\">\n" +
                "            Y\n" +
                "        </th>\n" +
                "        <th width=\"15%\">\n" +
                "            R\n" +
                "        </th>\n" +
                "        <th width=\"20%\">\n" +
                "            Hit Results.Result\n" +
                "        </th>\n" +
                "    </tr>");
    }
}
