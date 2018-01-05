// ========================================================================
var reqData = $('#user_login_form').serializeJSON();
jQuery.ajax({
    url: 'http://local.com/login.json',
    type: 'POST',
    data: JSON.stringify(reqData),
    contentType: 'application/json',
    dataType: 'json',
    processData: false,
    success: function (resData, textStatus, jqXHR) {
        // console.log(resData);
        if (resData.code == 200) {
            localStorage.setItem('username', resData.username);
            localStorage.setItem('viewname', resData.username);
            window.location.href = "./index.html";
        } else if (resData.code == 400) {
            $.messager.show({msg: JSON.stringify(resData.data)});
        } else {
            $.messager.show({msg: resData.message});
        }
    }
}).always(function (data_OR_jqXHR, textStatus, jqXHR_OR_errorThrown) {
    // console.log(data_OR_jqXHR, textStatus, jqXHR_OR_errorThrown);
    thisButton.linkbutton('enable');
});

// ========================================================================
var jsonString = JSON.stringify({});
console.log(jsonString);

// ========================================================================
var jsonObject = JSON.parse("{}");
console.log(jsonObject);