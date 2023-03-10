/**
 * Java Script
 */
window.onload = function() {
    document.getElementById("userLogin").addEventListener("click", function() {
        let crypt = new Crypt({
            aesStandard: "AES-CBC",
            aesKeySize: 128,
            aesIvSize: 16,
            rsaStandard: "RSA-OAEP"
        });

        let pemkey = document.getElementById("base64PublicKey").value;
        let userpw = document.getElementById("userpw").value;
        console.log(pemkey);
        console.log(userpw);

        let encrypted = crypt.encrypt(pemkey, userpw);
        json_log(encrypted);
        let base64encrypted = btoa(encrypted);
        console.log(base64encrypted);
        document.getElementById("userpw").value = base64encrypted;
        document.loginForm.submit();
    })
}

function json_log(encrypted) {
    let json = JSON.parse(encrypted);
    console.log("v ... " + json.v);
    console.log("iv ... " + json.iv);
    console.log("json.keys ... " + json.keys);
    console.log("keys ... " + Object.keys(json.keys));
    console.log("value ... " + Object.values(json.keys));
    console.log("cipher ... " + json.cipher);
}
