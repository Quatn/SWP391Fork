<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Price Package</title>
        <%@include file="/common/ImportBootstrap.jsp" %>
        <link rel="stylesheet" href="admin/common/admin-common.css">
        <script src="admin/common/admin-common.js"></script>
        <link rel="stylesheet" href="public/css/sortable/sortable-theme-bootstrap.css">
    </head>
    <body x-data="{ selected: { id: 0, name: '' } }">
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/subject-sidebar.jsp" %>
            <main class="admin-main">
                <div class="container">
                    <h2 class="my-4">
                        <i class="bi bi-clipboard-check-fill"></i>
                        Subject Details
                    </h2>
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                            <a
                                class="nav-link"
                                href="admin/subjectdetail/overview?subjectId=${param.subjectId}"
                            >Overview</a>
                        </li>
                        <li class="nav-item">
                            <a
                                class="nav-link"
                                href="admin/subjectdetail/dimension?subjectId=${param.subjectId}"
                            >Dimension</a>
                        </li>
                        <li class="nav-item">
                            <a
                                class="nav-link active"
                                href="admin/subjectdetail/pricepackage?subjectId=${param.subjectId}"
                            >Price Package</a>
                        </li>
                    </ul>

                    <%@include file="/admin/subjectdetail/pricepackage/PricePackageTable.jsp" %>
                </div>
            </main>
        </div>

        <%@include file="/admin/subjectdetail/pricepackage/PriceModals.jsp" %>

        <script src="public/js/sortable/sortable.min.js"></script>
        <script src="public/js/alpine/core.min.js"></script>
    </body>
</html>


