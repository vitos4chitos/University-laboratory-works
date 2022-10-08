package Results;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultList implements Serializable {
    List<Results.Result> resultList;

    public List<Results.Result> getResultList() {
        return resultList;
    }

    public void addResult(Results.Result result) {
        resultList.add(result);
    }

    public ResultList() {
        resultList = new ArrayList<Results.Result>();
    }

    @Override
    public String toString() {
        String answer = "";
        for (Results.Result result : resultList) {
            answer += result.getX() + " " + result.getY() + " " + result.getR() + " " + result.isResult() + "\n";
        }
        return answer;
    }
}
