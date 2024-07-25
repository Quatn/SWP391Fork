<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Blogs</title>

        <!-- Common import -->
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link href="common/common.css" rel="stylesheet">
        <script src="common/common.js"></script>

        <link href="blogs/BlogList.css" rel="stylesheet">
        <link href="blogs/BlogItem.css" rel="stylesheet">
        <link href="blogs/BlogSider.css" rel="stylesheet">
    </head>
    <body class="body-layout">
        <%@include file="/common/header.jsp" %>
        
        <main class="container my-2">
            <div class="my-3">
                <c:if test="${empty blogCategory}">
                        <h1 class="">Blogs</h1>
                        <p class="text-secondary">Discover tips, tricks for exams and news related to our products.</p>
                </c:if>
                <c:if test="${not empty blogCategory}">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h1>
                                    <span class="text-secondary">Discover</span> ${blogCategory.getCategoryName()}
                                </h1>
                                <p class="text-secondary">Here are all posts from this category</p>
                            </div>
                            <a class="btn btn-outline-primary" href="blogs/list">Go back to View All</a>
                        </div>
                </c:if>
                <c:if test="${not empty param.q}">
                    <h5 class="text-mute">
                        Searching sub-string <b>"${param.q}"</b> in blog titles.
                    </h5>
                </c:if>
            </div>
            <div class="row">
                <section class="col-8">
                    <div class="blog-grid">
                        <c:forEach items="${blogs}" var="blog">
                            <myTags:BlogItem highlight="${param.q}" blog="${blog}" />
                        </c:forEach>
                    </div>
                    
                    <c:if test="${empty blogs or blogs.size() eq 0}">
                        <div class="text-center my-5">
                            <i class="bi bi-emoji-neutral-fill fs-1 text-danger"></i>
                            <h1 class="text-body-secondary fw-bold">No posts found</h1>
                            <a href="blogs/list" class="btn btn-primary">Reset</a>
                        </div>
                    </c:if>

                    <c:if test="${blogs.size() > 0}">
                        <myTags:Paginator
                            className="mt-3 d-flex justify-content-center"
                            total="${pagesCount}"
                            size="1"
                            current="${param.page}"
                            url="blogs/list"
                        />
                    </c:if>
                </section>
                <aside class="col-4">
                   <%@include file="/blogs/BlogSider.jsp" %> 
                </aside>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
        <script src="blogs/BlogList.js"></script>
    </body>
</html>
