
function checkForm() {

    if (document.mail_form.id.value == "") {
        var myId = document.getElementById("error1");
        myId.innerHTML = "ログインIDは必須です。";
    } else if(!document.mail_form.id.value.match(/^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)){
        var myId = document.getElementById("error1");
        myId.innerHTML = "メールアドレスの形式で入力してください。";
    } else if(document.mail_form.pass.value == ""){
        var myId = document.getElementById("error2");
        myId.innerHTML = "パスワードは必須です。";
    } else if(document.mail_form.id.value == "smtakakubo@nissho-ele.co.jp"　&& document.mail_form.pass.value == "aaa"){
        window.location.href = "../html/home.html";
    }else{
        var myId = document.getElementById("error2");
        myId.innerHTML = "ユーザー名もしくはパスワードが間違っています。";
    }
}