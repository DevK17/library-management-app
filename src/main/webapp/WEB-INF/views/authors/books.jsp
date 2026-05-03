<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Authors" />
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="container">
    <div class="page-header">
        <h1>Books by ${author.name}</h1>
        <a href="/authors" class="btn btn-secondary">&#8592; Back to Authors</a>
    </div>

    <div class="card" style="margin-bottom:1.2rem; padding:1rem 1.2rem;">
        <p style="margin:0; color:#555;"><strong>Email:</strong> ${author.email} &nbsp;&nbsp; <strong>Nationality:</strong> ${author.nationality}</p>
    </div>

    <div class="card">
        <c:choose>
            <c:when test="${empty books}">
                <div class="empty-state">
                    <p>This author has no books yet.</p>
                    <a href="/books/add" class="btn btn-primary" style="margin-top:1rem;">Add a Book</a>
                </div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Title</th>
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
                                <td><span class="badge badge-default">${book.genre}</span></td>
                                <td>${book.publishedYear}</td>
                                <td>${book.isbn}</td>
                                <td>
                                    <div class="actions-cell">
                                        <a href="/books/edit/${book.id}" class="btn btn-warning btn-sm">Edit</a>
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
