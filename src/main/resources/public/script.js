$(document).ready(function() {
    for(const tag of tagList) {
        $("#tag-slider-" + tag).on("input", function(event) {
            event.preventDefault();
            let value = $("#tag-slider-" + tag).val() / 10;
            $.ajax({
                type: "PUT",
                dataType: "text",
                url: "/setTagPop?tag=" + tag + "&popularity=" + value,
                success: function(response) {
                    console.log("Set popularity of " + tag + " to " + value);
                }
            });
        });
    }

    for(const prof of profList) {
        $("#prof-slider-" + prof).on("input", function(event) {
            event.preventDefault();
            let value = $("#prof-slider-" + prof).val() / 10;
            $.ajax({
                type: "PUT",
                dataType: "text",
                url: "/setProfPop?prof=" + prof + "&popularity=" + value,
                success: function(response) {
                    console.log("Set popularity of " + prof + " to " + value);
                }
            });
        });
    }

    $("#greedyPick").on("click", function(event) {
        event.preventDefault();
        $.ajax({
            type: "PUT",
            dataType: "json",
            url: "/greedyPick",
            success: function(response) {
                $("#result_announce").html("Basierend auf Ihren Angaben sollten Sie folgende Module belegen:");
                let resultList = "";
                for(let key in response)
                    resultList = resultList.concat("<li>" + response[key]["name"] + " (" + key + ", " + response[key]["cp"] + " CP)</li>");
                $("#result_list").html(resultList);
            }
        });
    });

    $("#linearSum").on("click", function(event) {
        event.preventDefault();
        $.ajax({
            type: "PUT",
            dataType: "json",
            url: "/linearSum",
            success: function(response) {
                $("#result_announce").html("Basierend auf Ihren Angaben sollten Sie folgende Module belegen:");
                let resultList = "";
                for(let key in response)
                    resultList = resultList.concat("<li>" + response[key]["name"] + " (" + key + ", " + response[key]["cp"] + " CP)</li>");
                $("#result_list").html(resultList);
            }
        });
    });
});