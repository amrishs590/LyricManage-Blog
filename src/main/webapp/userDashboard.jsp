<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="com.app.model.Lyric, com.app.model.Comment" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Dashboard</title>
<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: #f0f2f5;
        margin: 0;
        padding: 0;
    }
    header {
        background: #2c3e50;
        color: #fff;
        padding: 15px 30px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    header h2 {
        margin: 0;
        font-size: 1.5rem;
    }
    header form button {
        background: #e74c3c;
        color: white;
        border: none;
        padding: 8px 16px;
        cursor: pointer;
        border-radius: 5px;
        font-weight: bold;
        transition: 0.3s;
    }
    header form button:hover {
        background: #c0392b;
    }
    .container {
        width: 85%;
        max-width: 1000px;
        margin: 25px auto;
    }
    form textarea, form input[type="text"], form select {
        width: 100%;
        padding: 10px;
        margin-bottom: 12px;
        border-radius: 6px;
        border: 1px solid #ccc;
        font-size: 0.95rem;
    }
    form button {
        background: #27ae60;
        color: white;
        border: none;
        padding: 8px 16px;
        cursor: pointer;
        border-radius: 5px;
        font-weight: bold;
        transition: 0.3s;
    }
    form button:hover {
        background: #219150;
    }
    .lyric-card {
        background: #fff;
        border-radius: 8px;
        box-shadow: 0px 3px 7px rgba(0,0,0,0.1);
        padding: 20px;
        margin-bottom: 20px;
        transition: transform 0.2s;
    }
    .lyric-card:hover {
        transform: translateY(-2px);
        box-shadow: 0px 5px 10px rgba(0,0,0,0.15);
    }
    .lyric-card p {
        margin: 6px 0;
    }
    .lyric-card .badge {
        display: inline-block;
        padding: 2px 8px;
        border-radius: 12px;
        font-size: 0.8rem;
        color: white;
        margin-right: 5px;
    }
    .genre-badge { background-color: #3498db; }
    .mood-badge { background-color: #9b59b6; }
    .reaction-btn {
        background: #2980b9;
        margin-right: 5px;
        padding: 5px 12px;
        border-radius: 5px;
        font-weight: bold;
        transition: 0.3s;
    }
    .reaction-btn:hover {
        background: #1f6391;
    }
    .comment-box {
        border-top: 1px solid #ddd;
        margin-top: 15px;
        padding-top: 10px;
    }
    .comment {
        margin: 6px 0;
        padding: 8px;
        background: #f7f7f7;
        border-radius: 5px;
        font-size: 0.9rem;
    }
    .filter-form {
        margin: 15px 0;
        display: flex;
        align-items: center;
        gap: 10px;
    }
    .filter-form label {
        font-weight: bold;
    }
    .filter-form select {
        width: auto;
    }
</style>
</head>
<body>

<header>
    <h2>Welcome, <%= session.getAttribute("username") %>!</h2>
    <form action="LogoutServlet" method="get">
        <button type="submit">Logout</button>
    </form>
</header>

<div class="container">
    <form action="PostLyricServlet" method="post">
        <textarea name="lyric_text" rows="3" placeholder="Write your lyric here..." required></textarea>

        Genre:
        <select name="genre">
            <option value="Rap">Rap</option>
            <option value="Indie">Indie</option>
            <option value="Classical">Classical</option>
            <option value="Pop">Pop</option>
        </select>

        Mood:
        <select name="mood">
            <option value="Sad">Sad</option>
            <option value="Energetic">Energetic</option>
            <option value="Romantic">Romantic</option>
            <option value="Happy">Happy</option>
        </select>

        <button type="submit">Post Lyric</button>
    </form>

    <form method="get" action="UserDashboard" class="filter-form">
        <label>Filter:</label>
        <select name="filter" onchange="this.form.submit()">
            <option value="all" <%= "all".equals(request.getAttribute("filter")) ? "selected" : "" %>>All Posts</option>
            <option value="mine" <%= "mine".equals(request.getAttribute("filter")) ? "selected" : "" %>>My Posts</option>
            <option value="others" <%= "others".equals(request.getAttribute("filter")) ? "selected" : "" %>>Others’ Posts</option>
        </select>
    </form>

    <h3>All Lyrics</h3>
    <%
        List<Lyric> lyrics = (List<Lyric>) request.getAttribute("lyrics");
        if (lyrics != null && !lyrics.isEmpty()) {
            for (Lyric lyric : lyrics) {
    %>
        <div class="lyric-card">
            <p><b><%= lyric.getLyricText() %></b></p>
            <p>
                <span class="badge genre-badge">Genre: <%= lyric.getGenre() %></span>
                <span class="badge mood-badge">Mood: <%= lyric.getMood() %></span>
            </p>
            <p>Posted by: <b><%= lyric.getUsername() %></b> | On: <%= lyric.getCreatedAt() %></p>
            <p>👍 <%= lyric.getUpvotes() %> | 👎 <%= lyric.getDownvotes() %> | 💬 <%= lyric.getCommentCount() %></p>

            <form action="ReactionServlet" method="post" style="display:inline;">
                <input type="hidden" name="lyric_id" value="<%= lyric.getLyricId() %>" />
                <input type="hidden" name="reaction_type" value="upvote" />
                <button type="submit" class="reaction-btn">👍 Like</button>
            </form>

            <form action="ReactionServlet" method="post" style="display:inline;">
                <input type="hidden" name="lyric_id" value="<%= lyric.getLyricId() %>" />
                <input type="hidden" name="reaction_type" value="downvote" />
                <button type="submit" class="reaction-btn">👎 Dislike</button>
            </form>

            <form action="CommentServlet" method="post" style="margin-top:8px;">
                <input type="hidden" name="lyric_id" value="<%= lyric.getLyricId() %>" />
                <input type="text" name="comment_text" placeholder="Write a comment..." required />
                <button type="submit">💬 Comment</button>
            </form>

            <div class="comment-box">
                <%
                    List<Comment> comments = lyric.getComments();
                    if (comments != null && !comments.isEmpty()) {
                        for (Comment c : comments) {
                %>
                    <div class="comment">
                        <b><%= c.getUsername() %>:</b> <%= c.getCommentText() %> 
                        <small>(<%= c.getCreatedAt() %>)</small>
                    </div>
                <%
                        }
                    } else {
                %>
                    <div class="comment">No comments yet.</div>
                <%
                    }
                %>
            </div>
        </div>
    <%
            }
        } else {
    %>
        <p>No lyrics posted yet.</p>
    <%
        }
    %>
</div>
</body>
</html>
