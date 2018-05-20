<?php
  $filename= sha1(uniqid(mt_rand(), true)).'.'.$_GET['filetype'];//uniqid('f_')
  $fileData=file_get_contents('php://input');
  if (!file_exists('uploads')) {
    mkdir('uploads', 0777, true);
  }
  $fhandle=fopen("uploads/".$filename, 'wb');
  fwrite($fhandle, $fileData);
  fclose($fhandle);
  echo($filename);
?>