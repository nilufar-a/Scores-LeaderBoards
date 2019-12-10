package servlets;

import com.google.gson.Gson;
import main.User;
import main.source;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "RegisterGame", urlPatterns = "/RegisterGame")
public class RegisterGameServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        try {


            String userID = req.getParameter("userID");
            double score = Double.parseDouble(req.getParameter("score"));
            double wins = Double.parseDouble(req.getParameter("wins"));
            double kills = Double.parseDouble(req.getParameter("kills"));
            double timePlayed = Double.parseDouble(req.getParameter("timePlayed"));
            Gson gson = new Gson();
            User user = new User(userID, score, wins, kills, timePlayed);
            if (source.registerGame(user)) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal SQL error");
            }

        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Incorrect input parameters");

        }
        out.flush();
    }
}




