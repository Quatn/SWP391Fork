package app.controller;

import app.dal.DAOQuiz;
import app.dal.DAOUser;
import app.dal.QueryResult;
import app.entity.QuizType;
import app.entity.Subject;
import app.entity.User;
import app.utils.Config;
import app.utils.Parsers;
import app.utils.URLUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name="QuizzesListController", urlPatterns={"/admin/quizzeslist"})
public class QuizzesListController extends HttpServlet {
    private final static String PAGE_NAME = "/admin/quizzeslist/QuizzesList.jsp";

    private User getCurrentUser(HttpServletRequest request) {
        HttpSession sess = request.getSession(false);
        if (sess == null) return null;

        Object userEmail = sess.getAttribute("userEmail");
        if (userEmail == null) return null;

        DAOUser daoUser = new DAOUser();
        return daoUser.getUserByEmail(userEmail.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        User user = getCurrentUser(request);
        if (user == null || (user.getRoleId() != 2 && user.getRoleId() != 4)) {
            request.getRequestDispatcher("/Unauthorized.jsp").forward(request, response);
            return;
        }

        Config cfg = new Config(getServletContext());

        int page = Parsers.parseIntOrDefault(request.getParameter("page"), 1);
        int pageSize = cfg.getIntOrDefault("pagination.size", 5);
        DAOQuiz daoQuiz = new DAOQuiz();

        String subject = request.getParameter("subjectIds");
        String quizName = request.getParameter("quizName");
        String quizTypes = request.getParameter("quizTypes");
        
        int type = Parsers.parseIntOrDefault(quizTypes, -1);
        int subjectId = Parsers.parseIntOrDefault(subject, -1);
        QuizType quizType = QuizType.fromInt(type);

        // is this the admin, if so set to -1 to ignore this parameter.
        // otherwise if this is the expert, set this parameter to userid
        int assignedExpertId = user.getRoleId() == 2 ? -1 : user.getUserId();

        QueryResult result = daoQuiz.search(
                quizName,
                subjectId,
                quizType,
                assignedExpertId,
                page, pageSize
        );

        boolean isSearching = (quizName != null && !quizName.isBlank()) || type != -1;
        request.setAttribute("isSearching", isSearching);

        if (result.getTotalPages() > 0 && page > result.getTotalPages()) {
            String params = request.getQueryString();
            params = params.replace("page=" + page, "page=" + result.getTotalPages());
            response.sendRedirect("quizzeslist?" + params);
        }
        
        List<Subject> subjects = daoQuiz.getSubjectsForOwner(assignedExpertId);

        request.setAttribute("subjects", subjects);
        request.setAttribute("result", result);

        request.getRequestDispatcher(PAGE_NAME).forward(request, response);
    } 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }
    

    @Override
    public String getServletInfo() {
        return "QuizzesListController Servlet";
    }
}
