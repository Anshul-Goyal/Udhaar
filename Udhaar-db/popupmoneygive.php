<?PHP 
    include_once("connection.php"); 
    $response = array();
     if( isset($_POST['txtid1']) && isset($_POST['txtmob']) && isset($_POST['txtmoney']) )
    {
        $type = 0;
        $money = $_POST['txtmoney'];
        $id1=$_POST['txtid1'];
        $id2=$_POST['txtmob'];
        $id = $id1;           // id1 is requesting the transaction

        $query = "Select id from user where mob_no='$id2'"; 
        
        $result = mysqli_query($conn, $query);

        $res=mysqli_fetch_array($result);

        $id2=$res['id']; 
        // echo $id2;
        $query = "Select id1,id2,money from
        trans where (id1='$id1' and id2='$id2') or (id1='$id2' and id2='$id1') "; 
        
        $result = mysqli_query($conn, $query);

        $res=mysqli_fetch_array($result);

        $m = $res['money'];

        // echo $res['money'];

        $finalmoney=0;
        if($id==$res['id1'])               //  1-->2 ---->>>> Positive  ---->>> give
        {
            $type=1;
            $finalmoney = $m + $money;
        }
        else                        //  2-->1 ---->>>> Negative  ---->>> give
        {
            $type=-1;
            $finalmoney = $m - $money;
        }
        // echo $finalmoney;

         $query = "update trans set money = $finalmoney 
                      where (id1='$id1' and id2='$id2') or (id1='$id2' and id2='$id1'); ";
        
        $result = mysqli_query($conn, $query);

        if($result)
        {
            $q = "update trans set tym=CURRENT_TIMESTAMP where (id1='$id1' and id2='$id2') or (id1='$id2' and id2='$id1');";
            $result = mysqli_query($conn, $query);
            date_default_timezone_set('Asia/Kolkata');
            $response['success']=1;
            if($type==1)
                $response['money']=$finalmoney;
            else
                $response['money']=(-1)*$finalmoney;
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


<html>
<head><title>Login</title></head>
    <body>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
            ID <input type="text" name="txtid1" value="" /><br/>
            mob <input type="text" name="txtmob" value="" /><br/>
            money <input type="text" name="txtmoney" value="" /><br/>
            <input type="submit" name="btnSubmit" value="Login"/>
        </form>
    </body>
</html>

