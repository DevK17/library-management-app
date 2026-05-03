<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Authors" />
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="container">
    <div class="page-header">
        <h1>Authors</h1>
        <a href="/authors/add" class="btn btn-primary">+ Add Author</a>
    </div>

    <div class="stat-grid">
        <div class="stat-card">
            <div class="stat-icon stat-icon-red">AUTH</div>
            <div>
                <div class="stat-number">${totalAuthors}</div>
                <div class="stat-label">Total Authors</div>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon stat-icon-blue">BOOK</div>
            <div>
                <div class="stat-number">${totalBooks}</div>
                <div class="stat-label">Total Books</div>
            </div>
        </div>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">${errorMessage}</div>
    </c:if>

    <div class="card">
        <c:choose>
            <c:when test="${empty authors}">
                <div class="empty-state">
                    <p>No authors found. Click "Add Author" to get started.</p>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Nationality</th>
                            <th>Books</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="author" items="${authors}" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td><strong>${author.name}</strong></td>
                                <td>${author.email}</td>
                                <td>${author.nationality}</td>
                                <td>
                                    <a href="/authors/${author.id}/books" class="badge badge-default" style="text-decoration:none; cursor:pointer;">
                                        ${author.books.size()} book(s)
                                    </a>
                                </td>
                                <td>
                                    <div class="actions-cell">
                                        <a href="/authors/edit/${author.id}" class="btn btn-warning btn-sm">Edit</a>
                                        <a href="/authors/delete/${author.id}"
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirm('Delete author ${author.name}? This will also remove their books.');">Delete</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
