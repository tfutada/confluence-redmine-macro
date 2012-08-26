AJS.$(function($) {
    var populateTable = function() {
        AJS.$(".dataTable").each(
            function() {
                var restapi = AJS.$(".restapi").val();
                var redmine = AJS.$(".redmine").val();

                var json = eval("(" + redmine + ")");

                var html = "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"display\" id=\"redmine\" width=\"100%\" >"
                        + "<thead><tr>"
                        + "<th>Project</th>"
                        + "<th>ID</th>"
                        + "<th>Subject</th>"
                        + "<th>Reporter</th>"
                        + "<th>Status</th>"
                        + "<th>Tracker</th>"
                        + "<th>Priority</th>" + "<th>Created Date</th>" + "</tr></thead><tbody>";

                AJS.$.each(json.issues, function() {
                   html = html
                            + "<tr class=\"gradeA\">"
                            + "<td>" + this.project.name + "</td>"
                            + "<td><a class='details' href='" + restapi + "/demo/issues/" + this.id + "'>" + this.id + "</a></td>"
                            + "<td>" + this.subject + "</td>"
                            + "<td>" + this.author.name + "</td>"
                            + "<td>" + this.status.name + "</td>"
                            + "<td>" + this.tracker.name + "</td>"
                            + "<td>" + this.priority.name + "</td>"
                            + "<td>" + this.created_on + "</td>"
                            + "</tr>";
                });

                html = html + "</tbody></table>";

                AJS.$(this).html(html);
            });
    };

    AJS.$(document).ready(function() {

        populateTable();
    });

    jQuery(function($) {
        $("#redmine").dataTable({"bFilter":false});
    });

});