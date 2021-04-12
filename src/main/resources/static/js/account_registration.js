
/* function textClear(){
     const element = document.querySelector('errorAll')
     element.remove()
 }*/

 function textClear(){
    const deleteError = document.getElementsByClassName("errorAll")
    for(let i = 0; i < 4; i++){
    deleteError[i].innerHTML = ""
    }
}

 //各項目をエラーチェックして、入力項目を正しく満たしていればログイン
 function checkForm(){
     if(document.acForm.acName.value == ""){
         var myId = document.getElementById("error1");
         myId.innerHTML = "アカウント名は必須です。";
     }if(document.acForm.id.value == ""){
         var myId = document.getElementById("error2");
         myId.innerHTML = "ログインIDは必須です。";
     }if(document.acForm.id.value == ""){
         var myId = document.getElementById("error2");
        myId.innerHTML = "メールアドレスは必須です。";
     }if(!document.acForm.id.value.match(/^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)){
         var myId = document.getElementById("error2");
         myId.innerHTML = "メールアドレスの形式で入力してください。";
     }if(document.acForm.pass.value == ""){
         var myId = document.getElementById("error3");
         myId.innerHTML = " パスワードは必須です。";
     }if(document.acForm.confPass.value == ""){
         var myId = document.getElementById("error4");
         myId.innerHTML = " 確認パスワードは必須です。";
     }else if(document.acForm.pass.value != document.acForm.confPass.value){
         var myId = document.getElementById("error4");
         myId.innerHTML = "パスワードと確認パスワードが一致していません。";
     }

 }
