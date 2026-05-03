<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Authors" />
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="container">
    <div class="page-header">
        <h1>Add New Author</h1>
        <a href="/authors" class="btn btn-secondary">&#8592; Back to Authors</a>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">${errorMessage}</div>
    </c:if>

    <div class="card" style="max-width: 600px;">
        <div class="form-container">
            <form action="/authors/add" method="post">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" id="name" name="name" placeholder="e.g. Jane Austen" required />
                </div>
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" placeholder="e.g. author@library.com" required />
                </div>
                <div class="form-group">
                    <label for="nationality">Nationality</label>
                    <input type="text" id="nationality" name="nationality" placeholder="e.g. British" required />
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Save Author</button>
                    <a href="/authors" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
