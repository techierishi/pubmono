(function (angular) {
    'use strict';
var app = angular.module("myApp", []);

app.controller("myPhotoCtrl2", function($scope,$http) {
	$scope.records = [{name:"Abhijeet Das",image:"images/friends/abhijeet_das.jpg",message:" Hi Abhijeet Das please attend our wedding"}
,{name:"Abhijit Das",image:"images/friends/abhijit_das.jpg",message:" Hi Abhijit Das please attend our wedding"}
,{name:"Ajay Jha",image:"images/friends/ajay_jha.jpg",message:" Hi Ajay Jha please attend our wedding"}
,{name:"Akshay",image:"images/friends/akshay.jpg",message:" Hi Akshay please attend our wedding"}
,{name:"Amit",image:"images/friends/amit.jpg",message:" Hi Amit please attend our wedding"}
,{name:"Amit ",image:"images/friends/amit_.jpg",message:" Hi Amit  please attend our wedding"}
,{name:"Anup Kumar",image:"images/friends/anup_kumar.jpg",message:" Hi Anup Kumar please attend our wedding"}
,{name:"Anup Sharma",image:"images/friends/anup_sharma.jpg",message:" Hi Anup Sharma please attend our wedding"}
,{name:"Arun",image:"images/friends/arun.jpg",message:" Hi Arun please attend our wedding"}
,{name:"Arun Prasad",image:"images/friends/arun_prasad.jpg",message:" Hi Arun Prasad please attend our wedding"}
,{name:"Ashish",image:"images/friends/ashish.jpg",message:" Hi Ashish please attend our wedding"}
,{name:"Avijeet",image:"images/friends/avijeet.jpg",message:" Hi Avijeet please attend our wedding"}
,{name:"Ayan",image:"images/friends/ayan.jpg",message:" Hi Ayan please attend our wedding"}
,{name:"Bajrangi",image:"images/friends/bajrangi.jpg",message:" Hi Bajrangi please attend our wedding"}
,{name:"Bijay",image:"images/friends/bijay.jpg",message:" Hi Bijay please attend our wedding"}
,{name:"Chandan",image:"images/friends/chandan.jpg",message:" Hi Chandan please attend our wedding"}
,{name:"Debasmita",image:"images/friends/debasmita.jpg",message:" Hi Debasmita please attend our wedding"}
,{name:"Dinabanshu",image:"images/friends/dinabanshu.jpg",message:" Hi Dinabanshu please attend our wedding"}
,{name:"Indrajit",image:"images/friends/indrajit.jpg",message:" Hi Indrajit please attend our wedding"}
,{name:"Jay",image:"images/friends/jay.jpg",message:" Hi Jay please attend our wedding"}
,{name:"Joydeep",image:"images/friends/joydeep.jpg",message:" Hi Joydeep please attend our wedding"}
,{name:"Kaushal",image:"images/friends/kaushal.jpg",message:" Hi Kaushal please attend our wedding"}
,{name:"Mayuri",image:"images/friends/mayuri.jpg",message:" Hi Mayuri please attend our wedding"}
,{name:"Niharika",image:"images/friends/niharika.jpg",message:" Hi Niharika please attend our wedding"}
,{name:"Pallavi",image:"images/friends/pallavi.jpg",message:" Hi Pallavi please attend our wedding"}
,{name:"Pankaj",image:"images/friends/pankaj.jpg",message:" Hi Pankaj please attend our wedding"}
,{name:"Pushpak",image:"images/friends/pushpak.jpg",message:" Hi Pushpak please attend our wedding"}
,{name:"Rajeev",image:"images/friends/rajeev.jpg",message:" Hi Rajeev please attend our wedding"}
,{name:"Rakesh",image:"images/friends/rakesh.jpg",message:" Hi Rakesh please attend our wedding"}
,{name:"Ranjit",image:"images/friends/ranjit.jpg",message:" Hi Ranjit please attend our wedding"}
,{name:"Richa",image:"images/friends/richa.jpg",message:" Hi Richa please attend our wedding"}
,{name:"Riya",image:"images/friends/riya.jpg",message:" Hi Riya please attend our wedding"}
,{name:"Robin",image:"images/friends/robin.jpg",message:" Hi Robin please attend our wedding"}
,{name:"Rupak",image:"images/friends/rupak.jpg",message:" Hi Rupak please attend our wedding"}
,{name:"Sandip",image:"images/friends/sandip.jpg",message:" Hi Sandip please attend our wedding"}
,{name:"Sanjay",image:"images/friends/sanjay.jpg",message:" Hi Sanjay please attend our wedding"}
,{name:"Satyendra",image:"images/friends/satyendra.jpg",message:" Hi Satyendra please attend our wedding"}
,{name:"Souvik",image:"images/friends/souvik.jpg",message:" Hi Souvik please attend our wedding"}
,{name:"Subhadeep",image:"images/friends/subhadeep.jpg",message:" Hi Subhadeep please attend our wedding"}
,{name:"Subhajit",image:"images/friends/subhajit.jpg",message:" Hi Subhajit please attend our wedding"}
,{name:"Suvra",image:"images/friends/suvra.jpg",message:" Hi Suvra please attend our wedding"}
,{name:"Swagnika",image:"images/friends/swagnika.jpg",message:" Hi Swagnika please attend our wedding"}
,{name:"Vikash",image:"images/friends/vikash.jpg",message:" Hi Vikash please attend our wedding"}
,{name:"Vikash Keshri",image:"images/friends/vikash_keshri.jpg",message:" Hi Vikash Keshri please attend our wedding"}
,{name:"Vyom",image:"images/friends/vyom.jpg",message:" Hi Vyom please attend our wedding"}];
/*$http.get('js/friends.json').
	success(function(data, status, headers, config) {
		$scope.records = data;
	}).
	error(function(data, status, headers, config) {
      // log error
  });*/
});
app.controller("myPhotoCtrl", function($scope,$http) {

$scope.ourphotos = [{category:"selfie",image:"images/us/us11_selfie.jpg"}
,{category:"selfie",image:"images/us/us12_selfie.jpg"}
,{category:"selfie",image:"images/us/us13_selfie.jpg"}
,{category:"selfie",image:"images/us/us14_selfie.jpg"}
,{category:"romantic",image:"images/us/us15_romantic.jpg"}
,{category:"romantic",image:"images/us/us16_romantic.jpg"}
,{category:"selfie",image:"images/us/us17_selfie.jpg"}
,{category:"selfie",image:"images/us/us18_selfie.jpg"}
,{category:"romantic",image:"images/us/us1_romantic.jpg"}
,{category:"romantic",image:"images/us/us3_romantic.jpg"}
,{category:"beach",image:"images/us/us4_beach.jpg"}
,{category:"beach",image:"images/us/us5_beach.jpg"}
,{category:"beach",image:"images/us/us6_beach.jpg"}
,{category:"beach",image:"images/us/us7_beach.jpg"}
,{category:"selfie",image:"images/us/us9_selfie.jpg"}
	];

	/*$http.get('js/us.json').
	success(function(data, status, headers, config) {
		$scope.ourphotos = data;
	}).
	error(function(data, status, headers, config) {
      // log error
  });
  */
  
});

})(angular);