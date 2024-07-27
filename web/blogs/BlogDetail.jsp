<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${not empty information ? information.getBlogTitle() : "Not found" }</title>
                <!-- Common import -->
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link href="common/common.css" rel="stylesheet">
        <link href="blogs/BlogDetail.css" rel="stylesheet">
        <link href="blogs/BlogItem.css" rel="stylesheet">
        <link href="blogs/BlogSider.css" rel="stylesheet">
        <script src="common/common.js"></script>
    </head>
    <body class="body-layout">
        <%@include file="/common/header.jsp" %>
        <c:set var="currentUrl" value="blogs/detail?id=${param.id}" />
        <jsp:useBean id="formatter" class="app.utils.FormatData" />

        <main class="container mt-3">
            <nav style="--bs-breadcrumb-divider: url(&#34;data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8'%3E%3Cpath d='M2.5 0L1 1.5 3.5 4 1 6.5 2.5 8l4-4-4-4z' fill='%236c757d'/%3E%3C/svg%3E&#34;);" aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="blogs/list">Blogs</a></li>
                    <li class="breadcrumb-item" aria-current="page">
                        <a href="blogs/list?categoryId=${information.getCategoryId()}">
                            ${information.getCategoryName()}
                        </a>
                    </li>
                    <li class="breadcrumb-item active" aria-current="page">${information.getBlogTitle()}</li>
                </ol>
            </nav>
            <div class="row">
                <div style="width: 80px;">
                    <div class="toc-container">
                        <div class="toc-icon">
                            <i class="bi bi-book-fill"></i>
                        </div>
                        <div class="toc">
                            <h5>Table of Contents</h5>
                            ${document.getHtmlTableOfContents(currentUrl)}
                        </div>
                    </div>
                </div>
                <div class="col-md-7">
                    <h1>${information.getBlogTitle()}</h1>
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <p class="m-0 text-muted">
                                Written by: ${information.getAuthorFullName()}
                            </p>
                            <p class="text-muted">
                                Updated Date: ${formatter.dateFormat(information.getUpdatedTime())}
                            </p>
                        </div>
                        <p class="badge bg-primary">${information.getCategoryName()}</p>
                    </div>

                    <div class="mb-3">
                        <img src="public/thumbnails/${information.getBlogThumbnail()}" class="cover-image rounded-3" height="400" width="100%" class="rounded" alt="Main Image">
                    </div>

                    <div class="blog-content">
                        ${document.toHtml()}
                    </div>

                </div>

                <div class="col-md-4">
                    <%@include file="/blogs/BlogSider.jsp" %>
                </div>
            </div>
        </main>
        <%@include file="/common/footer.jsp" %>
        <script src="blogs/BlogDetail.js"></script>
    </body>
</html>
