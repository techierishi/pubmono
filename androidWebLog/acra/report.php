<?php

if(isset($_POST['STACK_TRACE'])){
	writeLog($_POST['PACKAGE_NAME']);
}

if(isset($_GET['del'])){
	if(!empty($_GET['del'])){
		deleteAllFiles($_GET['del']);
	}else{
		echo "Please enter package name.";
	}
	
}

function writeLog($pn){
	
	$err = "";
	$err .= "<html>
	<head>
		<title> Bug Report at ".date('Y-m-d_H-i-s')."</title>
		<style>
			.TFtable{
				width:100%; 
				border-collapse:collapse; 
			}
			.TFtable td{ 
				padding:7px; border:#98BF21 1px solid;
			}
			.TFtable tr{
				background: #FFFFFF;
				color:red;
			}
			.TFtable tr:nth-child(odd){ 
				background: #B8D1F3;

			}
			.TFtable tr:nth-child(even){
				background: #EAF2D3;
			}
		</style>
		<body>";


// Outputs all POST parameters to a text file. The file name is the date_time of the report reception

			$path = 'bugs/';
			$fileName = $path.$pn.date('Y-m-d_H-i-s').'.html';

			if (!file_exists($path.'/'.$pn)) {
				mkdir($path.'/'.$pn, 0777, true);
				$fileName = $path.'/'.$pn.'/'.date('Y-m-d_H-i-s').'.html';
			}else{
				$fileName = $path.'/'.$pn.'/'.date('Y-m-d_H-i-s').'.html';
			}


			$file = fopen($fileName,'w') or die('Could not create report file: ' . $fileName);

			$err .= "<table class='TFtable'>";
			foreach($_POST as $key => $value) {
				$reportLine = "<tr><td><div class='key'>".$key."</div></td><td><div class='value' >".$value."</div></td></tr>";

				$err .= $reportLine;
			}
			$err .="</table>
		</body>
	</head>
	</html>";

	fwrite($file, $err) or die ('Could not write to report file ' . $reportLine);

	fclose($file);
}

function deleteAllFiles($package){
	$fullpath = 'bugs/'.$package;
	if (file_exists($fullpath)) {
		$files = glob($fullpath.'/*'); 
		foreach($files as $file){ 
			if(is_file($file))
				unlink($file); 
		}
	}else{
		echo "Invalid package name.";
	}
}

?>
