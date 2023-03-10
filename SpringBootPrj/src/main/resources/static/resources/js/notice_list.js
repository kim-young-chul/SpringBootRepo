/**
 * Java Script
 */
window.onload = function() {

    document.getElementById("userLogout").addEventListener("click", function() {
        location.href = "/servlet/user_logout";
    });

    document.getElementById("file").addEventListener("change", function() {
        let fileName = document.getElementById("file").value;
        document.querySelector(".upload-name").value = fileName;
    })

    document.getElementById("noticeWrite").addEventListener("click", function() {
        location.href = "/servlet/notice_write";
    });
}
