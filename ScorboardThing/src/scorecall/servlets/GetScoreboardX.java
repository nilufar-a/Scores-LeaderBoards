package servlets;


import com.google.api.client.http.HttpStatusCodes;
import com.google.gson.Gson;
import main.source;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

enum BoardName {
    score, wins, kills, timePlayed
}

@WebServlet(name = "GetLeaderBoardX", urlPatterns = "GetLeaderBoardX")
public class GetScoreboardX extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        String boardName = req.getParameter("boardName");
        if (boardName.equals(BoardName.score.toString()) || boardName.equals(BoardName.wins.toString()) || boardName.equals(BoardName.kills.toString()) || boardName.equals(BoardName.timePlayed.toString())) {
            String json = source.getSomeBoard(boardName);
            out.print(json);
        } else {
            Gson gson = new Gson();
            out.print(gson.toJson(HttpStatusCodes.STATUS_CODE_NOT_FOUND + " No such table"));
        }
        out.flush();

    }


}
