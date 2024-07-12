<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quizzes List</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="admin/common/admin-common.css">
        <script src="admin/common/admin-common.js"></script>
        <style>
            .subject-card {
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 16px;
                margin-bottom: 16px;
                display: grid;
                grid-template-rows: 200px 1fr auto;
            }
            .subject-card img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                border-radius: 8px;
            }

            .subject-grid {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
                gap: 10px;
            }

            .featured-subject {
                & .card {
                    width: 300px;
                    min-width: 300px !important;
                    margin: 10px 10px;
                    transition-duration: 0.5s;

                    & img {
                        height: 200px;
                        object-fit: cover;
                    }
                }

                & .card:hover {
                    transform: scale(1.05);
                }

                margin: 30px 0;
                padding: 30px;
                background-color: #F4F2EE;
                border-radius: 10px;
            }
            .featured-subject-btn-filler {
                height: 38px;
            }
        </style>
    </head>
    <body>
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/admin-sidebar.jsp" %>
            <main class="admin-main">
                <div class="container">
                    <c:if test="${not empty markedSubjects}">
                        <div class="featured-subject">
                            <h2>Marked for publication</h2>

                            <div class="d-flex flex-row flex-nowrap overflow-auto">
                                <c:forEach var="subject" items="${markedSubjects}">
                                    <div class="card">
                                        <img class="card-img-top" src="public/thumbnails/${subject.getThumbnail()}" alt="Card image cap">
                                        <div class="card-body">
                                            <h5 class="card-title">${subject.getSubjectName()}</h5>
                                            <div class="featured-subject-btn-filler"></div>
                                            <a href="/QuizPractice/admin/subjectdetail/overview?subjectId=${subject.getSubjectId()}" class="btn btn-primary position-absolute" style="bottom: 16px">View</a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                    <div class="row mt-4">
                        <h2 class="col">
                            <i class="bi bi-list-ul"></i>
                            Subject List
                        </h2>
                        <form class="d-flex col gap-3 align-items-center">
                            <div class="input-group me-2">
                                <input type="text" class="form-control" placeholder="Subject Title" name="title" value="${param.title}">
                            </div>
                            <div class="input-group me-2">
                                <select class="form-select" name="asc">
                                    <option value="1" ${param.asc eq '1' ? 'selected' : ''}>Oldest First</option>
                                    <option value="0" ${param.asc eq '0' ? 'selected' : ''}>Latest First</option>
                                </select>
                            </div>
                            <c:if test="${not empty param.title or (not empty param.asc and param.asc eq '0')}">
                                <a class="btn btn-outline-danger"href="admin/subjectlist">Reset</a>
                            </c:if>
                            <button class="btn btn-primary">Search</button>
                        </form>
                    </div>

                    <div class="mt-4">
                        <c:if test="${result.getTotalPages() > 0}">
                            <myTags:Paginator
                                className="mt-3 d-flex justify-content-end"
                                current="${param.page}"
                                total="${result.getTotalPages()}"
                                size="1"
                                url="admin/subjectlist"
                                />
                        </c:if>

                        <div class="subject-grid">
                            <c:forEach items="${result.getResults()}" var="subject">
                                <div class="subject-card">
                                    <img src="public/thumbnails/${subject.getThumbnail()}" alt="Subject Image">
                                    <h2 class="mt-2">${subject.getSubjectName()}</h2>
                                    <div class="text-end">
                                        <a href="admin/subjectdetail/overview?subjectId=${subject.getSubjectId()}" class="btn">
                                            Go to course <i class="bi bi-arrow-right-circle ms-1"></i>
                                        </a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <c:if test="${result.getTotalPages() > 0}">
                            <myTags:Paginator
                                className="mt-3 d-flex justify-content-end"
                                current="${param.page}"
                                total="${result.getTotalPages()}"
                                size="1"
                                url="admin/subjectlist"
                                />
                        </c:if>
                    </div>
                </div>
            </main>
        </div>    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.min.js"></script>
    </body>
</html>
