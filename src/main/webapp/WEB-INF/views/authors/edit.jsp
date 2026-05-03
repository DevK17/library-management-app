<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Authors" />
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="container">
    <div class="page-header">
        <h1>Edit Author</h1>
        <a href="/authors" class="btn btn-secondary">&#8592; Back to Authors</a>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">${errorMessage}</div>
    </c:if>

    <div class="card" style="max-width: 600px;">
        <div class="form-container">
            <form action="/authors/edit/${author.id}" method="post">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" id="name" name="name" value="${author.name}" required />
                </div>
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" value="${author.email}" required />
                </div>
                <div class="form-group">
                    <label for="nationality">Nationality</label>
                    <input type="text" id="nationality" name="nationality" value="${author.nationality}" required />
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Update Author</button>
                    <a href="/authors" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
