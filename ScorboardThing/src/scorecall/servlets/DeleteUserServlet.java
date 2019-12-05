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

@WebServlet(name="DeleteUser",urlPatterns = "DeleteUser")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String userID = req.getParameter("userID");
        Gson gson = new Gson();
        if (source.deleteUser(userID)) {

            out.print(gson.toJson(HttpStatusCodes.STATUS_CODE_OK));

        } else {
           out.print(gson.toJson(HttpStatusCodes.STATUS_CODE_NOT_FOUND));
        }

       out.flush();
    }


}
