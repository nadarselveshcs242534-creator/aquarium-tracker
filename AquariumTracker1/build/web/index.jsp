<%@ page import="com.aquarium.dao.WaterRecordDAO, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>AquaGuard Fleet Manager</title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="container fade-in">
    <div class="header-section">
        <div>
            <h1 class="gradient-text">AquaGuard Fleet</h1>
            <p>Select a station to track ecosystem telemetry or manage units (Max 15 tanks).</p>
        </div>
        <a href="tracker?action=export" class="btn btn-success export-btn">Download CSV Report</a>
    </div>

    <%
        String action = request.getParameter("action");
        if("delete".equals(action)) {
            String targetTank = request.getParameter("tank");
            WaterRecordDAO.removeTank(targetTank);
            response.sendRedirect("index.jsp");
        }
        
        String newTank = request.getParameter("newTank");
        if(newTank != null && !newTank.trim().isEmpty()) {
            if(WaterRecordDAO.getTanks().size() < 15) {
                WaterRecordDAO.addTank(newTank.trim());
                response.sendRedirect("index.jsp");
            } else {
    %>
                <div class="alert danger-alert">
                    Maximum limit of 15 tanks reached!
                </div>
    <%      }
        }
    %>

    <div class="tank-grid">
    <% 
        List<String> tanks = WaterRecordDAO.getTanks();
        for(String tank : tanks) {
    %>
        <div class="tank-card glass">
            <div class="tank-title"><%= tank %></div>
            <div class="action-row">
                <a href="tracker?action=dashboard&tankName=<%= java.net.URLEncoder.encode(tank, "UTF-8") %>" class="btn btn-glow" style="flex: 2;">Dashboard</a>
                <a href="index.jsp?action=delete&tank=<%= java.net.URLEncoder.encode(tank, "UTF-8") %>" class="btn btn-danger" style="flex: 1;">Delete</a>
            </div>
        </div>
    <% } %>
    </div>

    <div class="divider"></div>

    <h3 class="section-title">Register New Tank Station</h3>
    <form action="index.jsp" method="POST" class="add-tank-form">
        <div class="form-group flex-1">
            <input type="text" name="newTank" placeholder="e.g. Tank 4 - Amazon Biotop" required class="glass-input">
        </div>
        <button type="submit" class="btn btn-success">+ Add Station</button>
    </form>
</div>
</body>
</html>