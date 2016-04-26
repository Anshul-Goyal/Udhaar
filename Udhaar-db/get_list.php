<?PHP 
    include_once("connection.php"); 
    mysql_select_db("a9929614_udhaar")or die("cannot select DB");
     if( isset($_POST['txtid']))
      { 
        $id = $_POST['txtid'];

     		$response = array();
        $response['person'] = array();

    		// $q2 = "select id,mob_no from user where (id in (select id1 from trans where id2='$id')) or (id in (select id2 from trans where id1='$id'))";

       //  $q2 = "select id,mob_no,money,tym from user,trans where (user.id = trans.id1 and trans.id2 = '$id') or (user.id = trans.id2 and trans.id1 = '$id') ;";

     		// $mo = @mysql_query($conn, $q2);

        $q2 = "select id,mob_no,money,tym from user,trans where (user.id = trans.id2 and trans.id1 = '$id') ;";

        $mo = @mysql_query($q2 , $conn);

        $i=0;
     		if(@mysql_num_rows($mo)>0)
     		{
     			while($row = @mysql_fetch_array($mo))
     			{
            $person = array();
            $i++;
     		    $person['id'] = $row['id'];
            $person['mob_no'] = $row['mob_no'];
            $person['money'] = $row['money'];
            $person['tym'] = $row['tym'];
            array_push($response['person'],$person);
     			}

       }

      $q2 = "select id,mob_no,money,tym from user,trans where (user.id = trans.id1 and trans.id2 = '$id') ;";

      $mo = @mysql_query( $q2 , $conn);

       if(@mysql_num_rows($mo)>0)
       {

         while($row = @mysql_fetch_array($mo))
          {
            $person = array();
            $i++;
            $person['id'] = $row['id'];
            $person['mob_no'] = $row['mob_no'];
            $person['money'] = $row['money']*-1;
            $person['tym'] = $row['tym'];
            array_push($response['person'],$person);
          }

      }

        $response['success'] = 1;
        $response['count'] = $i;
        echo json_encode($response);
     		
 		   
       if($i==0)
       {
          $response['success'] = 1;
          $response['count'] = 0;
          echo json_encode($response);
       }
    }
 		else
 		{
 			    $response['success'] = 0;
          $response['count'] = 0;
          echo json_encode($response);
 		}


?>

                                                                                                                                                                                                 