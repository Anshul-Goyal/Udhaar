<?PHP 
    include_once("connection.php"); 
    mysql_select_db("a9929614_udhaar")or die("cannot select DB");
    $response = array();
     if( isset($_POST['txtid1']) && isset($_POST['txtmob']) && isset($_POST['txtmoney']) )
    {
        $money = $_POST['txtmoney'];
        $id1=$_POST['txtid1'];
        $id2=$_POST['txtmob'];
        $id = $id1;           // id1 is requesting the transaction

        $query = "Select id from user where mob_no='$id2'"; 
        
        $result = @mysql_query($query , $conn);

        $res=@mysql_fetch_array($result);

        $id2=$res['id']; 

        $query = "Select id1,id2,money from
        trans where (id1='$id1' and id2='$id2') or (id1='$id2' and id2='$id1') "; 
        
        $result = @mysql_query( $query , $conn);

        $res=@mysql_fetch_array($result);

        $m = $res['money'];
        $finalmoney=0;
        if($id==$res['id1'])               //  1-->2 ---->>>> Negative  ---->>> take
        {
            $finalmoney = $m - $money;
        }
        else                        //  2-->1 ---->>>> Positive  ---->>> take
        {
            $finalmoney = $m + $money;
        }

         $query = "update trans set money = $finalmoney 
                      where (id1='$id1' and id2='$id2') or (id1='$id2' and id2='$id1') "; 
        
        $result = @mysql_query($query , $conn);

        if($result)
        {
            $q = "update trans set tym=CURRENT_TIMESTAMP where (id1='$id1' and id2='$id2') or (id1='$id2' and id2='$id1');";
            $result = @mysql_query( $query , $conn);
            date_default_timezone_set('Asia/Kolkata');
            $response['success']=1;
            $response['money']=$finalmoney;
            $response['tym'] = date('Y-m-d G-i-s');
            echo json_encode($response);
        }
        else
        {
            $response['success']=0;
            $response['finalmoney']=$money;
            echo json_encode($response);
        }
        
   }
   else
   {
        $response['success']=-1;
        $response['finalmoney']=0;
        echo json_encode($response);
   }



?>

