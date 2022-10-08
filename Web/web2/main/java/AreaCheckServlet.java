import Results.Result;
import Results.ResultList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

public class AreaCheckServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(AreaCheckServlet.class.getName());


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> data = req.getParameterMap();
        if(check_Data(data) && check_Values(data)){
            logger.info("Coordinates: x = " + data.get("x")[0] + ", y = " + data.get("y")[0]);
            logger.info("Radius R = " + data.get("r")[0]);
            if(checkAcceptableValues(data)){
                logger.info("The point fell into the selected area");
                saveData(req, true);
            }
            else{
                logger.info("The point don't fell into the selected area");
                saveData(req, false);
            }
            sendAnswer(resp, (Results.ResultList) req.getSession().getAttribute("resultList"));
        }
        else{
            logger.info("I can't determine the values");
            resp.getWriter().println("ErrorType: can't determine the values");
        }
    }

    private boolean check_Data(Map<String, String[]> data){
        return data.containsKey("x") && data.containsKey("y") && data.containsKey("r");
    }

    private boolean check_Values(Map<String, String[]> data){

        try{
            double x = Double.parseDouble(data.get("x")[0]);
            double y = Double.parseDouble(data.get("y")[0]);
            double r = Double.parseDouble(data.get("r")[0]);
            if(data.get("x")[0].length() > 12 || data.get("y")[0].length() > 12 || data.get("r")[0].length() > 12){
                logger.info("Error with variable. It is impossible to reproduce accuracy. Forward to ErrorServlet");
                return false;
            }
            else{
                if(x >= -5 && x <= 3 && y > -5.0 && y < 3.0 && r >= 1 && r <= 3){
                    logger.info("All is ok");
                    return true;
                }
                else{
                    logger.info("Error with variable. Bad request");
                    return false;
                }
            }
        }
        catch (Exception e){
            logger.info("Error with variable. Forward to ErrorServlet");
            logger.info(e.toString());
            return false;
        }
    }

    private boolean checkAcceptableValues(Map<String, String[]> data){
        double x = Double.parseDouble(data.get("x")[0]);
        double y = Double.parseDouble(data.get("y")[0]);
        double r = Double.parseDouble(data.get("r")[0]);
        if (x >= 0 && y >= 0)
            return firstHalf(x, y, r);
        else if (x <= 0 && y > 0)
            return secondHalf();
        else if (x > 0 && y < 0)
            return fourthHalf(x, y, r);
        else
            return thirdHalf(x, y, r);
    }

    private boolean firstHalf(double x, double y, double r){
        return (x * x + y * y) <= r * r;
    }

    private  boolean secondHalf(){
        return false;
    }

    private boolean fourthHalf(double x, double y, double r){
        logger.info(x - y + " " + r);
        return x - y <= r;
    }

    private boolean thirdHalf(double x, double y, double r){
        return Math.abs(x) <= r && Math.abs(y) <= r;
    }

    private void sendAnswer(HttpServletResponse resp, Results.ResultList resultList) throws IOException {
        PrintWriter writer  = resp.getWriter();
//        writer.println("<table id=\"result_table\" align=\"center\" border=\"1\" width=\"80%\" >\n" +
//                "                <tr>\n" +
//                "                    <th width=\"15%\">\n" +
//                "                        X\n" +
//                "                    </th>\n" +
//                "                    <th width=\"15%\">\n" +
//                "                        Y\n" +
//                "                    </th>\n" +
//                "                    <th width=\"15%\">\n" +
//                "                        R\n" +
//                "                    <th width=\"20%\">\n" +
//                "                        Hit Results.Result\n" +
//                "                    </th>\n" +
//                "                </tr>");
//        for(Results.Result res: resultList.getResultList()){
//            writer.println(" <tr align=\"center\">\n" +
//                    "            <td  class = \"values\">" + res.getX() +" </td>\n" +
//                    "            <td  class = \"values\">" + res.getY() +" </td>\n" +
//                    "            <td  class = \"values\">" + res.getR() +" </td>\n" +
//                    "            <td  class = \"values\">" + res.isResult() +" </td>\n" +
//                    "    <tr>\n");
//
//        }
//        writer.println("        <td colspan=\"4\" align=\"center\">\n" +
//                "            <input type=\"submit\" value=\"Reset\" onclick=\"reset()\">\n" +
//                "        </td>\n" +
//                "    </tr>\n" +
//                "</table>\n");
        if(resultList.getResultList().get(resultList.getResultList().size() - 1).isResult()) {
            writer.println("green");
            logger.info("green");
        }
        else {
            writer.println("red");
            logger.info("red");
        }
        writer.close();
    }

    private void saveData(HttpServletRequest req, boolean res){
        Map<String, String[]> data = req.getParameterMap();
        HttpSession session = req.getSession();
        double x = Double.parseDouble(data.get("x")[0]);
        double y = Double.parseDouble(data.get("y")[0]);
        double r = Double.parseDouble(data.get("r")[0]);
        Results.ResultList resultList = (Results.ResultList) session.getAttribute("resultList");
        if (resultList == null) {
            resultList = new Results.ResultList();
        }
        Results.Result result = new Results.Result();
        result.setR(r);
        result.setX(x);
        result.setY(y);
        result.setResult(res);
        resultList.addResult(result);
        session.setAttribute("resultList", resultList);
        logger.info(resultList.toString());
    }

}
