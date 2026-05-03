<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Books" />
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="container">
    <div class="page-header">
        <h1>Books</h1>
        <a href="/books/add" class="btn btn-primary">+ Add Book</a>
    </div>

    <div class="stat-grid">
        <div class="stat-card">
            <div class="stat-icon stat-icon-blue">BOOK</div>
            <div>
                <div class="stat-number">${totalBooks}</div>
                <div class="stat-label">Total Books</div>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon stat-icon-red">AUTH</div>
            <div>
                <div class="stat-number">${totalAuthors}</div>
                <div class="stat-label">Total Authors</div>
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
            <c:when test="${empty books}">
                <div class="empty-state">
                    <p>No books found. Click "Add Book" to get started.</p>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Genre</th>
                            <th>Year</th>
                            <th>ISBN</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${books}" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td><strong>${book.title}</strong></td>
                                <td>
                                    <a href="/authors/${book.author.id}/books" style="color:#003366; text-decoration:none; font-weight:600;">
                                        ${book.author.name}
                                    </a>
                                </td>
                                <td><span class="badge badge-default">${book.genre}</span></td>
                                <td>${book.publishedYear}</td>
                                <td style="font-size:0.83rem; color:#666;">${book.isbn}</td>
                                <td>
                                    <div class="actions-cell">
                                        <a href="/books/edit/${book.id}" class="btn btn-warning btn-sm">Edit</a>
                                        <a href="/books/delete/${book.id}"
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirm('Delete book: ${book.title}?');">Delete</a>
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
