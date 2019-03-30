<?php

/**
 * @author Raslan
 * This code acts as SNMP Manager that returns SNMP objects throgh an http request.
 *
 */

 /**
  * Variable Declaration
  */
  $system="";
  $arp="";
  $routing="";
  $interface="";
  $start="<HTML><body style='background-color:  #071730'><center>";
  $communityName = "Raslan";  //Change this to your community name or define 'Raslan' as read/write community
  $system="<h1>System Group</h1><table border='2px' style='background-color: #10a02f'>";

  /**
   * Code to prepare all requried Objects
   */
  for($i=1;$i<8;$i++)
  {
      $b = snmp2_get("127.0.0.1", $communityName, ".1.3.6.1.2.1.1.$i.0");

      $system=$system."<tr><td>$b</td></tr>";
  }
  $system=$system."</table>";


  $routing="<h1>Routing Table</h1>
  <table border='2px' style='background-color: #10a02f'>
  ";
  $b = snmpwalk("127.0.0.1", $communityName, ".1.3.6.1.2.1.4.21");
  foreach($b as $val)
  {

      $routing=$routing."<tr><td>$val</td></tr>";
  }
  $routing=$routing."</table>";

  $arp="<h1>ARP Table</h1>
  <table border='2px' style='background-color: #10a02f'>
  ";
  $b = snmpwalk("127.0.0.1", $communityName, ".1.3.6.1.2.1.4.22");
  foreach($b as $val)
  {

      $arp=$arp."<tr><td>$val</td></tr>";
  }
  $arp=$arp."</table>";

  $interface="<h1>Interface Table</h1>
  <table border='2px' style='background-color: #10a02f'>
  ";
  $b = snmpwalk("127.0.0.1", $communityName, ".1.3.6.1.2.1.4.20");
  foreach($b as $val)
  {

      $interface=$interface."<tr><td>$val</td></tr>";
  }
  $interface=$interface."</table>";


  $end="</center></body></HTML>";

  /**
   * Display all objects.
   * Executed only if run using browser.
   */
  if(empty($_REQUEST['Operation'])) {
      echo $start.$system.$arp.$interface.$routing.$end;
  }

  /**
   * Return the object through http request
   * Executed when run from Java client
   */
  if(isset($_REQUEST['Operation'])) {

      if($_REQUEST['Operation']=="System Group") {

          echo $start.$system.$end;
      }

      if($_REQUEST['Operation']=="ARP Table") {
        echo $start.$arp.$end;
      }

      if($_REQUEST['Operation']=="Routing Table") {
        echo $start.$routing.$end;

      }

      if($_REQUEST['Operation']=="Interface Table"){
         echo $start.$interface.$end;
      }

  }

?>
