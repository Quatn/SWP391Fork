package app.controller;

import app.dal.QuestionDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import app.entity.Question;
import app.entity.Answer;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SaveChangeQuestion", urlPatterns = {"/admin/savechange"})
public class SaveChangeQuestion extends HttpServlet {

    QuestionDAO quesDAO = new QuestionDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int questionID = Integer.parseInt(request.getParameter("questionID"));

            if (quesDAO.isQuestionInQuiz(questionID)) {
                sendError(response, "This question is already in the quiz, so it cannot be edited.");
                return;
            }

            String questionName = request.getParameter("questionName");
            if (questionName == null || questionName.trim().isEmpty()) {
                sendError(response, "Question content cannot be empty.");
                return;
            }

            int subjectID = Integer.parseInt(request.getParameter("subjectId"));
            int lessonID = Integer.parseInt(request.getParameter("lessonID"));
            int level = Integer.parseInt(request.getParameter("level"));
            String explanation = request.getParameter("explanation");
            int status = Integer.parseInt(request.getParameter("status"));

            Question question = new Question(questionID, questionName, explanation, level, subjectID, lessonID, status, null);
            quesDAO.updateQuestion(question);

            List<Answer> answers = getAnswersFromRequest(request, questionID);
            if (answers == null) {
                sendError(response, "Cannot save question, because there is a blank answer option!");
                return;
            }

            for (Answer answer : answers) {
                quesDAO.updateAnswer(answer);
            }
            question.setAnswers(answers);

            sendSuccess(response);
        } catch (NumberFormatException e) {
            sendError(response, "Invalid number format in request parameters.");
        } catch (Exception e) {
            sendError(response, "An error occurred while saving the question: " + e.getMessage());
        }
    }

    private List<Answer> getAnswersFromRequest(HttpServletRequest request, int questionID) {
        String[] answerIDs = request.getParameterValues("answerID");
        String[] answerNames = request.getParameterValues("answerName");
        String[] isCorrects = request.getParameterValues("isCorrect");

        if (answerIDs == null || answerNames == null || isCorrects == null) {
            return null;
        }

        List<Answer> answers = new ArrayList<>();
        boolean hasCorrectAnswer = false;

        for (int i = 0; i < answerIDs.length; i++) {
            String answerName = answerNames[i];
            if (answerName == null || answerName.trim().isEmpty()) {
                return null;
            }

            int isCorrect = Integer.parseInt(isCorrects[i]);
            if (isCorrect == 1) {
                hasCorrectAnswer = true;
            }

            int answerID = Integer.parseInt(answerIDs[i]);
            Answer answer = new Answer(answerID, questionID, answerName, isCorrect);
            answers.add(answer);
        }

        if (!hasCorrectAnswer) {
            return null;
        }

        return answers;
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.getWriter().print("{\"status\":\"error\", \"message\":\"" + message + "\"}");
        response.getWriter().flush();
    }

    private void sendSuccess(HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"status\":\"success\"}");
        response.getWriter().flush();
    }

    @Override
    public String getServletInfo() {
        return "Servlet for saving changes to a question and its answers.";
    }
}
