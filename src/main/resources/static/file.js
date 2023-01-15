const form = document.querySelector("form"),
  fileInput = document.querySelector(".file-input"),
  uploadedArea = document.querySelector(".uploaded-area"),
  formSubmit = document.querySelector("#form"),
  submitBtn = document.querySelector(".submitBtn");

form.addEventListener("click", () => {
  fileInput.click();
});

fileInput.onchange = ({ target }) => {
  let file = target.files[0];
  if (file) {
    let fileSize = file.size;

    if (fileSize > 20 * 1024 * 1024) {
      alert("单次上传文件大小不得超过20MB！");
      return;
    }
    let fileName = file.name;
    if (fileName.length >= 12) {
      let splitName = fileName.split('.');
      fileName = splitName[0].substring(0, 13) + "... ." + splitName[1];
    }

    if (fileSize > 1048576) {
      fileSize = (fileSize / (1024 * 1024)).toFixed(2) + " MB";
    }

    if (fileSize > 1024) {
      fileSize = (fileSize / 1024).toFixed(2) + " KB";
    }

    let uploadedHTML = `<li class="row">
                            <div class="content upload">
                              <i class="fas fa-file-alt"></i>
                              <div class="details">
                                <span class="name">${fileName}</span>
                                <span class="size">${fileSize}</span>
                              </div>
                            </div>
                            <i class="fas fa-check"></i>
                          </li>`;
    uploadedArea.innerHTML = uploadedHTML;
  }
}


submitBtn.addEventListener('click', function () {
  if (fileInput.value === "") {
    alert("请选择文件");
    return;
  }
  formSubmit.submit();

});