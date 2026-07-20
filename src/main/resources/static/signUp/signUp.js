$("#signUpForm").on("submit", function (event) {
    event.preventDefault();

    if(!$("#signUpForm").reportValidity()) {
        return;
    }

    $.ajax({
        type: "POST", 
        url: "/signUpValidation",
        data: $("#signUpForm").serialize(), 
        contentType: "application/x-www-form-urlencoded", 
        success: function() {
            window.location.replace("/");
        }
    });
})