<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.aquarium.model.WaterRecord" %>
<% String tankName = (String) request.getAttribute("tankName"); %>
<html>
<head>
    <title>Dashboard - <%= tankName %></title>
    <link rel="stylesheet" type="text/css" href="style.css">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container fade-in">
        <div class="top-bar">
            <h2><span class="gradient-text"><%= tankName %></span> - Chemistry Logs</h2>
            <div class="action-buttons">
                <a href="tracker?action=export&tankName=<%= java.net.URLEncoder.encode(tankName, "UTF-8") %>" class="btn btn-success">⬇ Download CSV</a>
                <a href="index.jsp" class="btn btn-secondary">← Back to Tanks</a>
            </div>
        </div>

        <div class="glass-panel form-panel">
            <h3>Log New Reading</h3>
            <form action="tracker" method="POST" class="log-form">
                <input type="hidden" name="action" value="addRecord">
                <input type="hidden" name="tankName" value="<%= tankName %>">
                <div class="form-group"><label>Date</label><input type="date" name="dateLogged" required class="glass-input"></div>
                <div class="form-group"><label>pH</label><input type="number" step="0.1" name="ph" required class="glass-input"></div>
                <div class="form-group"><label>Ammonia</label><input type="number" step="0.01" name="ammonia" required class="glass-input"></div>
                <div class="form-group"><label>Nitrite</label><input type="number" step="0.01" name="nitrite" required class="glass-input"></div>
                <div class="form-group"><label>Nitrate</label><input type="number" step="0.01" name="nitrate" required class="glass-input"></div>
                <div class="form-group"><label>Temp (°C)</label><input type="number" step="0.1" name="temperature" required class="glass-input"></div>
                <div class="form-group button-group"><button type="submit" class="btn btn-glow">Save Data</button></div>
            </form>
        </div>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Date</th><th>pH</th><th>Ammonia</th><th>Nitrite</th><th>Nitrate</th><th>Temp</th><th>Status</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    List<WaterRecord> records = (List<WaterRecord>) request.getAttribute("records");
                    if(records != null && !records.isEmpty()) {
                        for(WaterRecord r : records) {
                            boolean safe = r.getAlertStatus().equals("Safe");
                %>
                <tr class="table-row">
                    <td><%= r.getDateLogged() %></td>
                    <td><%= r.getPh() %></td>
                    <td><%= r.getAmmonia() %></td>
                    <td><%= r.getNitrite() %></td>
                    <td><%= r.getNitrate() %></td>
                    <td><%= r.getTemperature() %></td>
                    <td>
                        <span class="status-badge <%= safe ? "status-safe" : "status-danger" %>">
                            <%= r.getAlertStatus() %>
                        </span>
                    </td>
                </tr>
                <%      }
                    } else { %>
                <tr><td colspan="7" class="empty-state">No telemetry data recorded yet for this station.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>