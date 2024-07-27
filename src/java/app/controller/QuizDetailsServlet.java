
package app.controller;

import app.dal.DAOQuiz;
import app.dal.DAOSubject;
import app.dal.QuestionDAO;
import app.entity.QuizInformation;
import app.entity.QuizLesson;
import app.entity.QuizType;
import app.entity.Subject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoapmhe173343
 */
@WebServlet(name = "QuizDetailsServlet", urlPatterns = {"/admin/quizzeslist/details"})
public class QuizDetailsServlet extends HttpServlet {

    DAOQuiz quizDao = new DAOQuiz();
    DAOSubject subDao = new DAOSubject();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String quizIdRaw = request.getParameter("quizid");
        if (quizIdRaw == null) {
            response.sendRedirect("../quizzeslist");
            return;
        }

        int quizId;
        try {
            quizId = Integer.parseInt(quizIdRaw);
        } catch (NumberFormatException e) {
            response.sendRedirect("../quizzeslist");
            return;
        }

        QuizInformation quiz = quizDao.getQuizById(quizId);
        request.setAttribute("quiz", quiz);

        List<Subject> listSubjects = subDao.getAllSubject();
        Map<Integer, String> subjectMap = new HashMap<>();
        for (Subject subject : listSubjects) {
            subjectMap.put(subject.getSubjectId(), subject.getSubjectName());
        }

        List<Integer> lessonList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            lessonList.add(i);
        }

        List<QuizLesson> listGroupQuestion = quizDao.getGroupQuestionByLesson(quizId);

        request.setAttribute("listGroupQuestion", listGroupQuestion);
        request.setAttribute("lessonList", lessonList);
        request.setAttribute("subjectMap", subjectMap);
        request.getRequestDispatcher("../quizdetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String levelStr = request.getParameter("examLevel");
        String durationStr = request.getParameter("duration");
        String passrateStr = request.getParameter("passRate");
        String typeStr = request.getParameter("quizType");
        String description = request.getParameter("description");

        int quizId = Integer.parseInt(request.getParameter("quizid"));
        
        boolean hasErrors = false;

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("errorName", "Name cannot be empty. ");
            hasErrors = true;
        }

        int level = 0;
        try {
            level = Integer.parseInt(levelStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorLevel", "Invalid exam level. ");
            hasErrors = true;
        }

        int duration = 0;
        try {
            duration = Integer.parseInt(durationStr);
            if (duration <= 0) {
                request.setAttribute("errorDuration", "Duration must be greater than 0. ");
                hasErrors = true;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorDuration", "Invalid duration. ");
            hasErrors = true;
        }

        int passrate = 0;
        try {
            passrate = Integer.parseInt(passrateStr);
            if (passrate <= 0) {
                request.setAttribute("errorPassRate", "Pass rate must be greater than 0. ");
                hasErrors = true;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorPassRate", "Invalid pass rate. ");
            hasErrors = true;
        }
        int type = 0;
        try {
            type = Integer.parseInt(typeStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorType", "Invalid quiz type. ");
            hasErrors = true;
        }

        if (hasErrors) {
            request.getRequestDispatcher(request.getContextPath() + "/admin/quizzeslist/details?quizid=" + quizId).forward(request, response);
            return;
        }

        
        try {
            if (quizDao.isQuizAttempted(quizId)) {
                request.getSession().setAttribute("message", "The quiz has already been taken, the quiz cannot be edited.");
                request.getSession().setAttribute("status", "error");
            } else if (quizDao.editQuiz(quizId, name, level, duration, passrate, type, description)) {
                request.getSession().setAttribute("message", "Edit quiz successfully.");
                request.getSession().setAttribute("status", "success");
            } else {
                request.getSession().setAttribute("message", "Failed to edit quiz.");
                request.getSession().setAttribute("status", "error");
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("message", "An error occurred while editing the quiz.");
            request.getSession().setAttribute("status", "error");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/quizzeslist/details?quizid=" + quizId);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
