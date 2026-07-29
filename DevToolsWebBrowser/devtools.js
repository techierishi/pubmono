// Can use
// chrome.devtools.*
// chrome.extension.*

// Create a tab in the devtools area
chrome.devtools.panels.create("DBrowser", "res/dbrowser16.png", "panel.html", function(panel) {});