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
        <link rel="stylesheet" href="admin/subjectdetail/overview/Overview.css">
    </head>
    <body>
        <div class="admin-layout">
            <%@include file="/admin/common/admin-header.jsp" %>
            <%@include file="/admin/common/subject-sidebar.jsp" %>
            <main class="admin-main">
                <c:if test="${marked}">
                    <div class="my-4 card container bg-info">
                        <div class="card-body">
                            <h4 class="text-light"><i class="bi bi-flag"></i> This subject is currently marked for publication</h4>
                        </div>
                    </div>
                </c:if>
                <div class="container">
                    <h2 class="my-4">
                        <i class="bi bi-clipboard-check-fill"></i>
                        Subject Details
                    </h2>
                    <ul class="nav nav-tabs">
                        <li class="nav-item" style="position: relative">
                            <a
                                class="nav-link active"
                                href="admin/subjectdetail/overview?subjectId=${param.subjectId}"
                                >Overview</a>
                            <i id="change-indicator" class="bi bi-diamond-fill"></i>
                        </li>
                        <li class="nav-item">
                            <a
                                class="nav-link"
                                href="DimensionServlet?subjectId=${param.subjectId}"
                                >Dimension</a>
                        </li>
                        <li class="nav-item">
                            <a
                                class="nav-link"
                                href="admin/subjectdetail/pricepackage?subjectId=${param.subjectId}"
                                >Price Package</a>
                        </li>
                    </ul>
                </div>

                <form class="container mt-3" method="POST" id='new-subject-form' action="admin/subjectdetail/overview" onkeydown="return event.key != 'Enter';" enctype="multipart/form-data" onsubmit="submitting = true;" onreset="formReset()">
                    <div class="row">
                        <div class="row">
                            <div class="col-8">
                                <div class="form-group pb-3">
                                    <label for="subject-title">Title</label><br>
                                    <small id='title-warning' style="color: red; display: none;">The maximum amount of characters for subject title is 50!</small>
                                    <input type="text" class="form-control" id="subject-title" name="subjectTitle" value="${subjectTitle}" oninput="validateTitle(this.value)"
                                           <c:if test="${role != 'admin' && userId != ownerId}">readonly</c:if>
                                           >
                                </div>

                                <div class="form-group">
                                    <label for="subject-category">Category</label>
                                    <select class="form-control" id="subject-category" name="subjectCategory" onchange="handleCateChange()"
                                            <c:if test="${role != 'admin' && userId != ownerId}">disabled</c:if>
                                            >
                                        <c:forEach var="category" items="${subjectCategoryList}">
                                            <option value="${category.getCateId()}" <c:if test="${subjectCategory eq category.getCateId()}">selected</c:if>>${category.getCateName()}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="row">
                                    <div class="form-check col-6 pt-5 ps-5">
                                        <input class="form-check-input" type="checkbox" value="true" id="featured-flag" name="featured" onclick="handleFeaturedChange()" 
                                        <c:if test="${featured}">checked</c:if>
                                           <c:if test="${role != 'admin' && userId != ownerId}">disabled</c:if>
                                           >
                                            <label class="form-check-label" for="featured-flag">
                                                Featured
                                            </label>
                                        </div>
                                        <div class="form-group col-6 row p-0 pt-5">
                                            <div class="col-md-2">
                                                <label for="subject-status">Status</label>
                                            </div>
                                            <div class="col-md-1 col-sm-0">
                                            </div>
                                            <div class="col-md-9 p-0">
                                            <c:if test="${role eq 'owner'}"><input type="hidden" name="subjsubjectStatus" value="${subjectStatus}"></c:if>
                                            <select class="form-control" id="subject-status" name="subjectStatus" onchange="handleStatusChange()" <c:if test="${role eq 'owner'}">disabled</c:if>>
                                                    <!--Temporary-->
                                                    <option value="0" <c:if test="${subjectStatus eq 0}">selected</c:if>>Unpublished</option>
                                                <option value="1" <c:if test="${subjectStatus eq 1}">selected</c:if>>Published</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-4">
                                    <div id="img-div">
                                        <img id="subject-thumbnail" src="${thumbnailUrl}" alt="Subject Thumbnail" onerror="handleThumbnailErr()"/>
                                </div>
                            </div>

                        </div>

                        <div class="row mb-5">

                            <div class="col-8 d-flex" style="align-items: center;">
                                <c:if test="${role eq 'admin'}">
                                    <div><i class="bi bi-person-circle float-lg-start h-100"></i>
                                        <p class="result-display mb-1">${ownerName}</p>
                                        <small>${ownerEmail}</small>
                                    </div>
                                    <div style="width: 100px"></div>
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target=".changeOnwerModal">Change owner</button>
                                </c:if>
                            </div>



                            <c:if test="${role eq 'admin' || userId eq ownerId}">
                            <div class="col-4">
                                <label for="image-upload" class="form-label" id="upload-label"></label>
                                <input class="form-control" type="file" id="image-upload" name="uploadData">
                                <input type="hidden" id="image-name" name="uploadName">
                                <!--p  id="imgsetter-text" class="mb-0 mt-2">Link to thumbnail image:</p>
                                <div class="input-group mb-3">
                                    <input type="text" id="thumbnail-url" name="thumbnailUrl" class="form-control" placeholder="" aria-label="Subject thumbnail" aria-describedby="thumbnail-preview-btn" oninput="changeSetter()">
                                    
                                </div-->
                            </div>
                            </c:if>

                            <div id='chosen-expert' class="col-4"></div>
                        </div>



                        <div class="form-group pb-3">
                            <label for="subject-tagline">Tagline</label><br>
                            <small id='tagline-warning'>A short sentence (<50 characters) that describes this subject.</small>
                            <input type="text" class="form-control" id="subject-tagline" name="subjectTagline" value="${subjectTagline}" oninput="validateTagline(this.value)"
                                   <c:if test="${role != 'admin' && userId != ownerId}">readonly</c:if>
                                   >
                        </div>

                        <div class="form-group">
                            <label for="subject-brief">Brief Info</label><br>
                            <small id='brief-warning'>Write a short paragraph (<300 characters) that describes this subject.</small>
                            <textarea class="form-control" id="subject-brief" rows="3" name="subjectBrief" oninput="validateBrief(this.value)"
                                      <c:if test="${role != 'admin' && userId != ownerId}">readonly</c:if>
                                      >${subjectBrief}</textarea>
                        </div>

                        <div class="form-group mt-5">
                            <label for="subject-description">Description</label>
                            <c:if test="${userId eq ownerId}">
                                <button type="button" class="btn btn-sm btn-outline-info float-end mb-1" data-bs-toggle="modal" data-bs-target="#useTemplate"><small>Use template</small></button>
                            </c:if>
                            <textarea class="form-control" id="subject-description" rows="3" name="subjectDescription" oninput="handleDescChange()"
                                      <c:if test="${role != 'admin' && userId != ownerId}">readonly</c:if>
                                      >${subjectDescription}</textarea>
                        </div>
                    </div>

                    <div class="row mt-3">
                        <c:if test="${role eq 'admin' || userId eq ownerId}">
                        <div class="col-lg-6 col-12-md">
                            
                                <input id="submitButton" class="btn btn-primary disabled" type="submit" value="Save">
                                <input id="clearButton" class="btn btn-secondary" type="reset" value="Reset">
                            
                        </div>
                        <div class="col-lg-4 col-12-md">
                            <div class="form-check form-switch float-end">
                                <c:if test="${marked}"><a href='admin/subjectdetail/overview?service=changeMark&subjectId=${subjectId}'><input id="markbtn" class="btn btn-danger" type="button" value="Unmark this subject"></a></c:if>
                                <c:if test="${!marked}"><a href='admin/subjectdetail/overview?service=changeMark&subjectId=${subjectId}'><input id="markbtn" class="btn btn-primary" type="button" value="Mark this subject"></a></c:if>
                                </div>
                            </div>

                        </div>
                        </c:if>

                        <input id='hiddenEmail' type="hidden" name="expertEmail">
                        <input type="hidden" name="subjectId" value="${subjectId}">
                    <input type="hidden" name="service" value="edit">
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
                    <c:if test="${not empty notification}">
                        <script>
                                                    $(document).ready(function () {
                                                        $(".notify").modal('show');
                                                    });
                        </script>
                    </c:if>
                </form>
            </main>
        </div>
    </body>
</html>

<div class="modal fade changeOnwerModal"
     tabindex="-1">
    <div class="modal-dialog modal-dialog-centered modal-lg"
         role="document">
        <div class="modal-content">
            <div class="modal-header text-bg-primary">
                <h4>Subject Register</h4>
                <button type="button" 
                        class="btn-close" 
                        data-bs-dismiss="modal" 
                        aria-label="Close">
                </button>
            </div>
            <div class="modal-body">
                <div id='expert-search-div' class="px-1">
                    <h3>Owner</h3>

                    <div id="subject-search" class="input-group">
                        <input id='query' type="email" class="form-control" placeholder="Enter an expert's email or name" oninput="filterExpert()">
                        <span class="input-group-text" >
                            <i class="bi bi-search"></i>
                        </span>
                    </div>

                    <div style="position: relative">
                        <div id='search-result' class="bg-light border border-1 rounded-bottom-2">

                        </div>

                    </div>
                </div>
                <script src="admin/subjectdetail/overview/Overview.js" expertList='${expertList}' currentImg='${thumbnailUrl}' currentOwner="${ownerEmail}" initState='${marked}'></script>
            </div>
        </div>
    </div>
</div>


<div class="modal fade notify" role="dialog" >
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                </button>
            </div>
            <div class="modal-body">
                ${notification}
                <c:set var="notification" scope="session" value="" />
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="useTemplate" role="dialog" >
    <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4>Use a template for subject description</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                </button>
            </div>
            <div class="modal-body">
                <h4>Choose a template:</h4>
                <select class="form-control" id="template-options">
                    <option value="1" selected>4 sections with a navbar</option>
                </select>
                <strong style="color: red;"><small>Choosing a template will wipe what you've already written in the subject description text area</small></strong>
                <button type="button" class="btn btn-primary float-end mt-5" onclick="setTemplate(document.getElementById('template-options').value)" data-bs-dismiss="modal" aria-label="Close">Confirm</button>
            </div>
        </div>
    </div>
</div>
