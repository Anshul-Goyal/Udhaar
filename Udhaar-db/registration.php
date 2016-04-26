<?PHP 
    include_once("connection.php"); 
    mysql_select_db("a9929614_udhaar")or die("cannot select DB");
    $response = array();
     if( isset($_POST['txtmobile'])) { 
        $mob_no = $_POST['txtmobile'];
 
        $query = "insert into user (mob_no) 
        values ($mob_no)"; 
        
        $result = @mysql_query($query , $conn);
        
                $response = array();
                $q = "select id from user where mob_no='$mob_no'";
                $result = @mysql_query($q , $conn);
                if(@mysql_num_rows($result)==1)
                {
                    $res = @mysql_fetch_array($result);
                    $response['id']=$res['id'];
                    $response['success']=1;
                    echo json_encode($response);
                    // echo($result['id']);
                }
                else
                {
                    $response['id'] = -1;
                    $response['success'] = 0;
                    echo json_encode($response);
                    // echo('-1');
                }
                
   }


?>


 <html>
<head><title>Login</title></head>
    <body>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
            Username <input type="text" name="txtmobile" value="" /><br/>
            <input type="submit" name="btnSubmit" value="Login"/>
        </form>
    </body>
</html>


<!-- <html>
<head><title>Login</title></head>
    <body>
        <form action="<?PHP $_PHP_SELF ?>" method="post">
            ID <input type="text" name="txtid" value="" /><br/>
            <input type="submit" name="btnSubmit" value="Login"/>
        </form>
    </body>
</html>
 -->