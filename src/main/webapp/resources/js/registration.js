$("#submit").on("click", function(e) {
    e.preventDefault();

    var userFormData = $("#userForm").serializeArray();
    var whFormData = $("#whForm").serializeArray();

    var data = {userData: {}, whData: {}, _csrf: userFormData.pop().value};
    for(var i=0; i<userFormData.length; i++) {
        data.userData[userFormData[i].name] = userFormData[i].value;
    }

    for(var i=0; i<whFormData.length; i++) {
        data.whData[whFormData[i].name] = whFormData[i].value;
    }

    $.ajax({
        type: "POST",
        url: "/registration",
        data: data,
        success: function(success) {
            console.log(success)
        },
        headers: {
            "X-CSRFToken": getCookie("csrftoken")
        }
    });
});

function getCookie(c_name) {
    if(document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if(c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if(c_end == -1) c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start,c_end));
        }
    }
    return "";
}