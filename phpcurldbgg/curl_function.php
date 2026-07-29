<?php

function dbgg($message,$tag="",$mode="w",$filename="dbg_file.html"){
		   
			$ch = curl_init();
			
			$message = "<pre>".print_r($message,true)."</pre>";
			
			$dbg_jsn = array(
				'message'=> $message,
				'tag' => $tag,
				'mode' => $mode,
				'filename' => $filename,
			
			);
			
			
			curl_setopt($ch, CURLOPT_URL,"http://127.0.0.1/test/dbg.php");
			curl_setopt($ch, CURLOPT_POST, 1);
			curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($dbg_jsn));

			// receive server response
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

			$server_output = curl_exec ($ch);

			curl_close ($ch);
			
			// further processing
			if ($server_output == "OK") {
				
				} else {
				
					}

	}
