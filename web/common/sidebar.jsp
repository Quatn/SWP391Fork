<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .latest-posts {
        text-decoration: none;
    }

    .latest-posts p {
        color: #007bff;
        text-decoration-line: underline;
        text-decoration-color: #007bff;
    }

    .latest-posts small {
        color: black;
    }
</style>
<div class="offcanvas offcanvas-start w-25" id="offcanvas" data-bs-backdrop="false" data-bs-scroll="true">
    <div class="offcanvas-header">
        <h6 class="offcanvas-title d-none d-sm-block" id="offcanvas">Menu</h6>
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body px-0">
        <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-start h-100" id="menu">
            <div class="accordion w-100" id="accordionExample">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingOne">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            Latest posts
                        </button>
                    </h2>
                    <c:if test="${not empty sidebarLatestPosts}">
                        <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                            <div class="accordion-body">
                                <c:forEach var="post" items="${sidebarLatestPosts}">
                                    <a class="latest-posts" href="/QuizPractice/blogs/detail?id=${post.getBlogId()}">
                                        <div class="my-2 ps-1 border border-1 rounded-1">
                                            <p class="mb-1">${post.getBlogTitle()}</p>
                                            <small>${post.getUpdatedTime()}</small>
                                        </div>
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                </div>
                <!--div class="accordion-item">
                    <h2 class="accordion-header" id="headingTwo">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            Contacts
                        </button>
                    </h2>
                    <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
                        <div class="accordion-body">
                            <div>Contact #1</div>
                            <div>Contact #2</div>
                            <div>Contact #3</div>
                            <div>Contact #4</div>   
                        </div>
                </div-->
            </div>
            <div style="flex-grow: 1; width: 100%;"></div>
            <c:if test="${not empty userEmail}">
                <div>
                    <c:if test="${userRoleId eq 2 || userRoleId eq 4}">
                        <a href="admin/subjectlist">
                            <button class="btn w-100 text-start">To management console</button>
                        </a>
                    </c:if>
                    <a href="javascript:void(0)"><button type="button" class="btn w-100 text-start" data-bs-toggle="modal" data-bs-target=".changePassModal">
                            Change Password
                        </button>
                    </a>
                    <a href="SettingsServlet"><button type="button" class="btn w-100 text-start">
                            Settings
                        </button>
                    </a>

                    <form method="post" action="loginviewofAn" class="d-flex">
                        <a href="javascript:void(0)" class="me-auto">
                            <button type="submit" class="btn text-start">Logout</button>
                        </a>
                        <a href="javascript:void(0)" class="my-auto mx-2">
                            Contact Us
                        </a>
                        <input type="hidden" name="service" value="logout"/>
                    </form>

                </div>
            </c:if>
        </ul>
    </div>
</div>
<div class="h-100" style="position: absolute; background-color: #dedde6; height: 100%; z-index: 0;" data-bs-toggle="offcanvas" data-bs-target="#offcanvas" role="button">
    <div style="height: 50vh"></div>   
    <button class="btn" style="position: sticky; display: block; top: 0; transform: rotate(90deg);" >
        <i class="bi bi-border-width" data-bs-toggle="offcanvas" data-bs-target="#offcanvas"></i>
    </button>
</div>

<jsp:include page="/ChangePassAn.jsp" />
