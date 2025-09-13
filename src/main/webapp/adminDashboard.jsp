<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.app.model.Participant" %>
<%@ page import="java.util.*, com.app.model.Participant, com.app.model.LyricAdmin, com.app.model.Admin" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<style>
	.admin-header {
	  display: flex;
	  justify-content: space-between;
	  align-items: center;
	  padding: 10px 20px;
	}
	
	.admin-header h1 {
	  margin: 0;
	  font-size: 24px;
	  color: white;
	}
	
	.logout-btn {
	  background-color: red;
	  color: white;
	  text-decoration: none;
	  padding: 8px 15px;
	  border-radius: 5px;
	  font-weight: bold;
	  transition: background-color 0.3s ease;
	}
	
	.logout-btn:hover {
	  background-color: darkred;
	}
		
    body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
    header { background: #333; color: #fff; padding: 15px; text-align: center; }
    nav { background: #444; padding: 10px; text-align: center; }
    nav a { color: white; margin: 0 15px; text-decoration: none; font-weight: bold; }
    nav a:hover { text-decoration: underline; }
    section { padding: 20px; }
    h2 { color: #333; margin-bottom: 10px; }
    table { width: 100%; border-collapse: collapse; margin-bottom: 30px; }
    table, th, td { border: 1px solid #ccc; }
    th, td { padding: 10px; text-align: left; }
    th { background: #eee; }
    button { padding: 6px 12px; margin: 2px; border: none; border-radius: 4px; cursor: pointer; }
    .add-btn { background: #28a745; color: white;margin-bottom:10px; }
    .edit-btn { background: #007bff; color: white; }
    .delete-btn { background: #dc3545; color: white; }
    .toggle-btn { background: #ffc107; color: black; }
</style>
</head>
<body>
    <header>
	  <div class="admin-header">
	    <h1>Admin Dashboard</h1>
	    <a href="LogoutServlet" class="logout-btn">Logout</a>
	  </div>
	</header>

    <!-- Participants Section -->
    <section id="participants">
        <h2>Participants</h2>
        <a href="NewUser.jsp" class="add-btn" style="text-decoration:none; display:inline-block; padding:6px 12px;">+ Add Participant</a>      
        <table>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Actions</th>
            </tr>
            <%
                List<Participant> plist = (List<Participant>) request.getAttribute("participantsList");
                if (plist != null) {
                    for (Participant p : plist) {
            %>
            <tr>
                <td><%= p.getId() %></td>
                <td><%= p.getUsername() %></td>
                <td>
	    			<form action="EditParticipantServlet" method="get" style="display:inline;">
				        <input type="hidden" name="user_id" value="<%= p.getId() %>"/>
				        <button type="submit" class="edit-btn">Edit</button>
				    </form>
				    <form action="DeleteParticipantServlet" method="post" style="display:inline;">
				        <input type="hidden" name="user_id" value="<%= p.getId() %>"/>
				        <button type="submit" class="delete-btn">Delete</button>
				    </form>
				</td>

            </tr>
            <%
                    }
                }
            %>
        </table>
    </section>
    
    
    <!-- Admins Section -->
	<section id="admins">
	    <h2>Admins</h2>
	    <a href="NewAdmin.jsp" class="add-btn" style="text-decoration:none; display:inline-block; padding:6px 12px;">+ Add Admin</a>
	    
	    <table>
	        <tr>
	            <th>ID</th>
	            <th>Username</th>
	        </tr>
	        <%
	            List<Admin> adminList = (List<Admin>) request.getAttribute("adminList");
	            if (adminList != null) {
	                for (Admin a : adminList) {
	        %>
	        <tr>
	            <td><%= a.getId() %></td>
	            <td><%= a.getUsername() %></td>
	        </tr>
	        <%
	                }
	            } else {
	        %>
	        <tr>
	            <td colspan="3">No admins found</td>
	        </tr>
	        <%
	            }
	        %>
	    </table>
	</section>
    

    <!-- Lyrics Section -->
    <section>
		<h2>Lyrics Management</h2>
		<table border="1" cellpadding="8">
		    <tr>
		        <th>ID</th>
		        <th>Lyric</th>
		        <th>Posted By</th>
		        <th>Created At</th>
		        <th>Auto - Status</th>
		        <th>Admin Status</th>
		        <th> Change </th>
		    </tr>
		    <%
		        List<LyricAdmin> lyrics = (List<LyricAdmin>) request.getAttribute("lyrics");
		        if (lyrics != null && !lyrics.isEmpty()) {
		            for (LyricAdmin lyric : lyrics) {
		    %>
		    <tr>
		        <td><%= lyric.getLyricId() %></td>
		        <td><%= lyric.getLyricText() %></td>
		        <td><%= lyric.getUsername() %></td>
		        <td><%= lyric.getCreatedAt() %></td>
		        <td><%= lyric.isHidden() %></td>
		        <td><%= lyric.isOveride() %></td>
		        <td>
				    <form action="ToggleLyricVisibilityServlet" method="post">
				        <input type="hidden" name="lyricId" value="<%= lyric.getLyricId() %>" />
				        <input type="hidden" name="currentStatusAuto" value="<%= lyric.isHidden() %>" />
				        <input type="hidden" name="currentStatusAdmin" value="<%= lyric.isOveride() %>" />
				
				        <%
				            String buttonLabel = "";
				            boolean isAutoHidden = lyric.isHidden();
				            boolean isAdminOverride = lyric.isOveride();
				
				            if (!isAutoHidden) {
				                // Post is not auto-hidden → show No Action button
				                buttonLabel = "No Action";
				        %>
				                <button type="button" class="toggle-btn" disabled><%= buttonLabel %></button>
				        <%
				            } else {
				                // Post is auto-hidden → Admin can Show/Hide
				                if (isAdminOverride) {
				                    buttonLabel = "Hide";
				                } else {
				                    buttonLabel = "Show";
				                }
				        %>
				                <button type="submit" class="toggle-btn"><%= buttonLabel %></button>
				        <%
				            }
				        %>
				        
				    </form>
				</td>

		         
		    </tr>
		    <%
		            }
		        } else {
		    %>
		    <tr>
		        <td colspan="5">No lyrics found</td>
		    </tr>
		    <%
		        }
		    %>
		</table>
	</section>
		   	
</body>
</html>
