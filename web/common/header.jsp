<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg bg-body-tertiary" style="z-index: 1">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Quiz Practice</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="">Courses</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="blogs/list">Blogs</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="RegistrationController">My Registration</a>
                </li>
            </ul>
            <div class="d-flex gap-2">
                <div id="notification" class="notification"></div>

                <c:if test="${not empty sessionScope.userEmail}">
                    <div class="btn-group">
                        <!--button onclick="displayPopUp('UserProfile')" type="button" class="btn btn-primary">
                            <i class="bi bi-person-circle"></i>
                        ${sessionScope.userEmail}
                    </button-->

                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#userProfileModal">
                            <i class="bi bi-person-circle"></i>
                            ${sessionScope.userEmail}
                        </button>
                        <!-- Modal -->

                        <button type="button" class="btn btn-primary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                            <span class="visually-hidden">Toggle Dropdown</span>
                        </button>

                        <ul class="dropdown-menu">
                            <li>
                                <form method="post" action="loginviewofAn">
                                    <button type="submit" class="btn">Logout</button>
                                    <input type="hidden" name="service" value="logout"/>
                                </form>
                            </li>
                            <li>
                                <form method="post" action="loginviewofAn">
                                    <button type="submit" class="btn">Logout</button>
                                    <input type="hidden" name="service" value="logout"/>
                                </form>
                            </li>
                        </ul>
                    </div>
                    <jsp:include page="/testModalChangePass.jsp" />
                </c:if>

                <c:if test="${empty sessionScope.userEmail}">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#loginModal">
                        Login
                    </button>
                    <!-- Modal -->
                    <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" >
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <jsp:include page="/LoginInterface.jsp" />
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</nav>

<div class="modal fade" id="userProfileModal" tabindex="-1" role="dialog" >
    <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                </button>
            </div>
            <div class="modal-body">
                <jsp:include page="/UserProfile.jsp" />
            </div>
        </div>
    </div>
</div>
