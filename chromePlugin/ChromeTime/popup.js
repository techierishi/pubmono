document.addEventListener('DOMContentLoaded', function() {
 $.ajax({
   url: 'http://api.timezonedb.com/v2/list-time-zone?key=5G70CPYHX6GK&format=json&country=IN',
   error: function() {
      console.log('Error occured');
   },
   success: function(data) {
		console.log(data);
		var timestamp = data.zones[0].timestamp;
		console.log(timestamp);

		var timezoneAdd = 330*60; 
		date = new Date((timestamp+timezoneAdd) * 1000);
		datevalues = [
		   date.getFullYear(),
		   date.getMonth()+1,
		   date.getDate(),
		   date.getHours()+1,
		   date.getMinutes(),
		   date.getSeconds(),
		];
		
		var formatted = zeroPadding(datevalues[3],2)+"&nbsp;:&nbsp;"+zeroPadding(datevalues[4],2)+"&nbsp;:&nbsp;"+zeroPadding(datevalues[5],2);
		$('#h4id').html(formatted);
		
		function zeroPadding(num, digit) {
			var zero = '';
			for(var i = 0; i < digit; i++) {
				zero += '0';
			}
			return (zero + num).slice(-digit);
		}
   },
   type: 'GET'
});

}, false);

/* The Web Request API */
const WEB_REQUEST = chrome.webRequest;

WEB_REQUEST.onBeforeRequest.addListener(
    function(details) {
        if(details.method == "POST")
            console.log(JSON.stringify(details));
    },
    {urls: ["<all_urls>"]},
    ["blocking", "requestBody"]
);

