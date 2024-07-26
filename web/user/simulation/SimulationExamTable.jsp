<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="table-responsive">
    <table class="table table-striped table-bordered table-hover sortable-theme-bootstrap" data-sortable>
        <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Subject</th>
                <th>Simulation Exam</th>
                <th>Level</th>
                <th># Question</th>
                <th>Duration</th>
                <th>Pass Rate</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="quiz" items="${result.getResults()}">
                <tr>
                    <td>${quiz.getQuizId()}</td>
                    <td>${quiz.getSubjectName()}</td>
                    <td>${quiz.getQuizName()}</td>
                    <td>
                        <c:choose>
                            <c:when test="${quiz.getLevel() eq 'HARD'}">
                                <c:set var="levelColor" value="danger" />
                            </c:when>
                            <c:when test="${quiz.getLevel() eq 'MEDIUM'}">
                                <c:set var="levelColor" value="warning" />
                            </c:when>
                            <c:when test="${quiz.getLevel() eq 'EASY'}">
                                <c:set var="levelColor" value="primary" />
                            </c:when>
                        </c:choose>
                        <span class="badge w-100 text-bg-${levelColor}">
                            ${quiz.getLevel().toString()}
                        </span>
                    </td>
                    <td>
                        ${quiz.getTotalQuestion()}
                    </td>
                    <td>${quiz.getDurationInMinutes()}</td>
                    <td>${quiz.getPassRate()}%</td>
                    <td style="width: 1px; white-space: nowrap;">
                        <button @click="selected = { name: '${quiz.getQuizName()}', passRate: ${quiz.getPassRate()}, duration: ${quiz.getDurationInMinutes()}, quizId: ${quiz.getQuizId()} }" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#confirmStartQuizModal">Take Exam</button>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${result.getResults().isEmpty()}">
                <tr>
                    <td colspan="12" class="text-center">No results</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>