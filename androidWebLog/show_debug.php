<?php
   class MyDB extends SQLite3
   {
      function __construct()
      {
         $this->open('dbgg.db');
      }
   }
   $db = new MyDB();   
   $db->busyTimeout(5000);
   
   if(!$db){
      //echo $db->lastErrorMsg();
   } else {
      //echo "Opened database successfully\n";
   }

   $sql = "SELECT * from DEBUG";


   if(isset($_GET['tagName'])){
      $tagName = $_GET['tagName'];
      $sql = "SELECT * from DEBUG WHERE TAG LIKE '%%".$tagName."%%'";
   }


   

   $ret = $db->query($sql);
   $rows = array();
   while($row = $ret->fetchArray(SQLITE3_ASSOC) ){
      $rows[] = $row;
   }

   echo json_encode($rows);

   // Delete All on Request
   if(isset($_GET['clear'])){
      $dsql = "DELETE from DEBUG";
      $db->exec($dsql);
   }
   
   $db->close();
   unset($db);
?>