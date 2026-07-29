<?php

if(isset($_POST['message'])){
	
	dbgg($_POST['message'],$_POST['mode'],$_POST['filename'],$_POST['tag']);
}



function dbgg($msg,$md='w',$fn='dbgg.html',$tg='No tag'){
	
	$fo = fopen($fn,$md);
	
	fwrite($fo,'<br>[================[ '. date('d/m/Y H:m:s').' { '.$tg. ' } ]================]<br>');
	$msg = "<pre>".print_r($msg,true)."</pre>";
	fwrite($fo,$msg);

	fclose($fo);
	
}
