<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="pageTitle" value="Home" />
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="container">
    <div class="page-header">
        <h1>Dashboard</h1>
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

    <div class="card">
        <div style="padding: 0.9rem 1rem; border-bottom: 1px solid #ddd; display: flex; align-items: center; justify-content: space-between;">
            <h2 style="font-size:1rem; color:#003366;">All Books in Catalogue</h2>
            <a href="/books/add" class="btn btn-primary btn-sm">+ Add Book</a>
        </div>
        <c:choose>
            <c:when test="${empty recentBooks}">
                <div class="empty-state">
                    <p>No books found. Add some books to get started.</p>
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
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${recentBooks}" varStatus="status">
                            <tr>
                                <td>${status.count}</td>
                                <td><strong>${book.title}</strong></td>
                                <td>${book.author.name}</td>
                                <td>
                                    <span class="badge badge-default">${book.genre}</span>
                                </td>
                                <td>${book.publishedYear}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <div style="display:flex; gap:1rem; margin-top:1.2rem;">
        <a href="/authors" class="btn btn-secondary">Manage Authors</a>
        <a href="/books" class="btn btn-primary">Manage Books</a>
    </div>
</div>
</body>
</html>
