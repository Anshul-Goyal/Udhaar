<?PHP 
    include_once("connection.php"); 
     $response = array();
     if( isset($_POST['txtid'])&&isset($_POST['user_mob_no']))
      { 
        $id1 = $_POST['txtid'];
        $mob_no = $_POST['user_mob_no'];

        $query = "select id from  user where mob_no = '$mob_no'";
        $result = @mysql_query($query , $conn );
        $rc = @mysql_num_rows($result);
        // $query = "select id from user where mob_no = '$mob_no'";
        // $result = @mysql_query($conn, $query);
        if($rc==1)
        {
		    $res = @mysql_fetch_array($result);
		    $id2 = $res['id'];
		    $query = "insert into trans (id1,id2,money) values ($id1 ,$id2,0 )";
		    $result = @mysql_query($query , $conn);
		     
		            // $q = "select id from user where mob_no='$mob_no'";
		            // $result = @mysql_query($conn, $q);
		   	$response['success'] = 2;
		   	$response['status']='true';
		   	$response['rc'] = $rc; 
		    echo json_encode($response);
		}
		else
		{          
		    $response['success'] = 0;    
		    $response['status'] = 'false2222';   
		    $response['rc'] = $rc;     
		    echo json_encode($response);          
		}           


	}
	    else
		{          
		    $response['success'] = 0;    
		    $response['status'] = 'false';         
		    echo json_encode($response);          
		}           

			
?>
