'use strict';
let searchURL = document.querySelector(".searchURL");
let searchButton = document.querySelector(".searchButton");
let iframePage = document.querySelector(".iframePage");
searchButton.onclick = function () {
  iframePage.src = searchURL.value;
  iframePage.onload = function() {
    console.log(this.contentWindow.location)
  }
};
