<?php

$handle = fopen("php://input", "rb");
$raw_post_data = '';
while (!feof($handle)) {
 $raw_post_data .= fread($handle, 8192);
}
fclose($handle); 

 
dbgg($raw_post_data,'a');

$rdata = json_decode($raw_post_data);

header("Content-type: application/json");
echo json_encode($rdata);

dbgg($rdata,'a');

function dbgg($msg,$md='w',$fn='dbgg.html',$tg='No tag'){
	$fo = fopen($fn,$md);
	fwrite($fo,'<br>[================[ '. date('d/m/Y H:m:s').' { '.$tg. ' } ]================]<br>');
	$msg = "<pre>".print_r($msg,true)."</pre>";
	fwrite($fo,$msg);
	fclose($fo);
}
?>