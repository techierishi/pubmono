<?php
class MyDB extends SQLite3
{
   function __construct()
   {
      $this->open('dbgg.db');
   }
}

$db = new MyDB();
   if(!$db){
      //echo $db->lastErrorMsg();
   } else {
      //echo "Opened database successfully\n";
   }

// Create Table Part

   $sql =<<<EOF
     CREATE TABLE IF NOT EXISTS DEBUG
     (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
      TAG           TEXT    NOT NULL,
      CONTENT            TEXT     NOT NULL,
      TIMESTAMP        TEXT     NOT NULL);
EOF;

   $ret = $db->exec($sql);
   if(!$ret){
      //echo $db->lastErrorMsg();
   } else {
      //echo "Table created successfully\n";
   }

// Create Table Part ends

//Insert Table Part

   $tag = $_POST['tag'];
   $content = $_POST['content'];
   $time = time();

   $sql ="INSERT INTO DEBUG (TAG,CONTENT,TIMESTAMP) VALUES ('$tag', '$content','$time')";

   $ret = $db->exec($sql);
//Insert Table Part ends

// Close db at last
$db->close();
