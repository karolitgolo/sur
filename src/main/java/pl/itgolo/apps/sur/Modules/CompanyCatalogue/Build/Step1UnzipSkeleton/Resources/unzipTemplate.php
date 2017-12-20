<?php
$zip = new ZipArchive;
$res = $zip->open('{{fileNameZip}}');
if ($res === TRUE) {
    $zip->extractTo('./');
    $zip->close();
    echo "Success extract";
} else {
    echo "Failed extract";
}