<jsp:useBean id="blogSiderFormatter" class="app.utils.FormatData" />

<form method="GET" action="blogs/list">
    <div class="mb-3">
        <label for="searchBox" class="form-label fs-5 fw-bold">Search</label>
        <input type="text" placeholder="Search posts..." value="${param.q}" class="form-control" name="q" id="searchBox">
    </div>
    <input type="hidden" name="categoryId" value="${param.categoryId}" />
</form>
    
<div class="mb-3">
    <h5 class="fw-bold">Categories</h5>
    <ul class="list-group">
        <c:forEach var="cat" items="${categories}">
        <c:set var="activeBlog" value="${information.getCategoryId() eq cat.getCategoryId()}" />
        <c:set var="activeCategoryQuery" value="${param.categoryId eq cat.getCategoryId()}" />
        <a
            href="blogs/list?categoryId=${cat.getCategoryId()}&q=${param.q}"
            class="list-group-item ${activeBlog or activeCategoryQuery ? "active" : ""}">
            ${cat.getCategoryName()}
        </a>
        </c:forEach>
    </ul>
</div>

<div class="mb-3">
    <h5 class="fw-bold">Advertisement</h5>
    <div class="card text-white">
        <img src="public/images/ad.jpg" class="card-img" alt="Banner Ad">
        <div class="card-img-overlay ad-overlay">
            <h4 class="card-title fw-bold">Boost Your Quiz Skills Today!</h4>
            <p>
                Ace Your Exams with Fun and Interactive Practice Sessions
            </p>
            <ul>
                <li>Engaging quizzes</li>
                <li>Instant feedback</li>
                <li>Cheap</li>
            </ul>
            <a href="public/SubjectsList" class="btn btn-primary">Checkout our Courses!</a>
        </div>
    </div>
</div>
    
<div class="mb-3">
    <h5 class="fw-bold">Latest Posts</h5>
    <c:forEach items="${recents}" var="post">
        <a href="blogs/detail?id=${post.getBlogId()}">
            <div class="card d-flex flex-row align-items-center mb-3">
                <img height="128" src="public/thumbnails/${post.getBlogThumbnail()}" class="p-2 rounded-4" alt="${post.getPostBrief()}">
                <div class="card-body">
                    <h5 class="card-title">${post.getBlogTitle()}</h5>
                    <p class="card-text"><small class="text-muted">${blogSiderFormatter.dateFormat(post.getUpdatedTime())}</small></p>
                </div>
            </div>
        </a>
    </c:forEach>
    <div class="text-center">
        <a href="blogs/list" class="btn btn-outline-primary">View all</a>
    </div>
</div>
    