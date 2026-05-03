<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Books" />
<%@ include file="/WEB-INF/views/layout/header.jsp" %>

<div class="container">
    <div class="page-header">
        <h1>Edit Book</h1>
        <a href="/books" class="btn btn-secondary">&#8592; Back to Books</a>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">${errorMessage}</div>
    </c:if>

    <div class="card" style="max-width: 600px;">
        <div class="form-container">
            <form action="/books/edit/${book.id}" method="post">
                <div class="form-group">
                    <label for="title">Book Title</label>
                    <input type="text" id="title" name="title" value="${book.title}" required />
                </div>
                <div class="form-group">
                    <label for="genre">Genre</label>
                    <input type="text" id="genre" name="genre" value="${book.genre}" required />
                </div>
                <div class="form-group">
                    <label for="publishedYear">Published Year</label>
                    <input type="number" id="publishedYear" name="publishedYear" value="${book.publishedYear}" min="1000" max="2100" required />
                </div>
                <div class="form-group">
                    <label for="isbn">ISBN</label>
                    <input type="text" id="isbn" name="isbn" value="${book.isbn}" required />
                </div>
                <div class="form-group">
                    <label for="authorId">Author</label>
                    <select id="authorId" name="authorId" required>
                        <option value="">-- Select an Author --</option>
                        <c:forEach var="author" items="${authors}">
                            <option value="${author.id}" ${book.author.id == author.id ? 'selected' : ''}>${author.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Update Book</button>
                    <a href="/books" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
