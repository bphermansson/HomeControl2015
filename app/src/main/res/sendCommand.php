/*
Runs on a Linux server and receives calls from the Android app.
Uses a Tellstick RF-sender to send on/off to Nexa receivers for controlling lights and stuff.
Also uses Linux software rfcmd, http://download.telldus.se/TellStick/Software/rfcmd/.

*/

<?php
header('Content-Type: text/xml');

//Get values
$port = $_GET['port'];
$proto = $_GET['proto'];
$house = $_GET['house'];
$channel = $_GET['channel'];
$onoff = $_GET['onoff'];

//Check values
if ($port == "null") {
	$output = "No device selected!";
	echo "<?xml version=\"1.0\" ?>";
	echo "<command><result>$output</result>";
	echo "</command>";
	exit;
}
if ($proto == "none") {
	$output = "No protocol selected!";
	echo "<?xml version=\"1.0\" ?>";
	echo "<command><result>$output</result>";
	echo "</command>";
	exit;
}
if ($house == "none") {
	$output = "No housecode selected!";
	echo "<?xml version=\"1.0\" ?>";
	echo "<command><result>$output</result>";
	echo "</command>";
	exit;
}
if ($channel == "none") {
	$output = "No channel selected!";
	echo "<?xml version=\"1.0\" ?>";
	echo "<command><result>$output</result>";
	echo "</command>";
	exit;
}
//Convert on/off
if ($onoff == "on")
	$onoffnum=1;
if ($onoff == "off")
	$onoffnum=0;

//rfcmd DEVICE PROTOCOL [PROTOCOL_ARGUMENTS]
$command = "rfcmd /dev/$port $proto $house $channel $onoffnum";

//Run command, result in $output
$output=shell_exec($command." 2>&1");
$output="Command sent";

//Return result
echo "<?xml version=\"1.0\" ?>";
echo "<command><result>$output. ($command)</result>";
echo "</command>";
?>