<?php

 //getting the dboperation class
 require_once 'ProduitOperation.php';

 //function validating all the paramters are available
 //we will pass the required parameters to this function
 function isTheseParametersAvailable($params){
 //assuming all parameters are available
 $available = true;
 $missingparams = "";

 foreach($params as $param){
 if(!isset($_GET[$param]) || strlen($_GET[$param])<=0){
 $available = false;
 $missingparams = $missingparams . ", " . $param;
 }
 }

 //if parameters are missing
 if(!$available){
 $response = array();
 $response['error'] = true;
 $response['message'] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';

 //displaying error
 echo json_encode($response);

 //stopping further execution
 die();
 }
 }

 //an array to display response
 $response = array();

 //if it is an api call
 //that means a get parameter named api call is set in the URL
 //and with this parameter we are concluding that it is an api call
 if(isset($_GET['service'])){

 switch($_GET['service']){

 //the CREATE operation
 //if the api call value is 'createhero'
 //we will create a record in the database
 case 'create':
 //first check the parameters required for this request are available or not
//isTheseParametersAvailable(array('nom', 'prenom', 'age' , 'tel' , 'mail' , 'password' , 'role'  , 'img' ));

 //creating a new dboperation object
 $db = new ProduitOperation();

 $result = $db->create(

    $_GET['boutique'] ,
    $_GET['reference'] ,
    $_GET['libelle'],
    $_GET['description'],
    $_GET['prix'] ,
    $_GET['taille'] ,
    $_GET['couleur'] ,
    $_GET['texture'],
    $_GET['poids'] ,
    $_GET['date_creation'] ,
    $_GET['quantite'] ,
    $_GET['path']
 );


 //if the record is created adding success to response
 if($result){
 //record is created means there is no error
 $response['error'] = false;

 //in message we have a success message
 $response['message'] = 'user addedd successfully';
 }else{

 //if record is not added that means there is an error
 $response['error'] = true;

 //and we have the error message
 $response['message'] = 'Some error occurred please try again';
 }

 break;


 //the READ operation
 //if the call is getAll
 case 'getallbyboutique':
 $db = new ProduitOperation();
 $response['error'] = false;
 $response['message'] = 'Request successfully completed';
 $response['data'] = $db->getallbyboutique( $_GET['id'] );
 break;
 //the READ operation
 //if the call is getAll
 case 'getall':
 $db = new ProduitOperation();
 $response['error'] = false;
 $response['message'] = 'Request successfully completed';
 $response['data'] = $db->getAll();
 break;

 case 'getEntityById':
 $db = new ProduitOperation();
 $response['error'] = false;
 $response['message'] = 'Request successfully completed';
 $user = $db->getEntityById ( $_GET['id'] ) ;
 	$response['error'] = false;


    $response['id'] = $user['id'];
    $response['boutique'] = $user['boutique'];
    $response['reference'] = $user['reference'] ;
    $response['libelle'] = $user['libelle'];
    $response['description'] = $user['description'];
    $response['prix'] = $user['prix'];
    $response['taille'] = $user['taille'] ;
    $response['couleur'] = $user['couleur'];
    $response['texture'] = $user['texture'];
    $response['poids'] = $user['poids'];
    $response['date_creation'] = $user['date_creation'];
    $response['quantite'] = $user['quantite'];
    $response['path'] = $user['path'];
 break;


 //the UPDATE operation
 case 'update':
 //isTheseParametersAvailable(array('id', 'nom', 'prenom', 'age' , 'tel' , 'mail' , 'password' , 'role'  , 'img'));
 $db = new ProduitOperation();


 $result = $db->update(


        $_GET['id'] ,
        $_GET['boutique'] ,
        $_GET['reference'] ,
        $_GET['libelle'],
        $_GET['description'],
        $_GET['prix'] ,
        $_GET['taille'] ,
        $_GET['couleur'] ,
        $_GET['texture'],
        $_GET['poids'] ,
        $_GET['date_creation'] ,
        $_GET['quantite'] ,
        $_GET['path']

 );



 if($result){
 $response['error'] = false;
 $response['message'] = 'user updated successfully';
 }else{
 $response['error'] = true;
 $response['message'] = 'Some error occurred please try again';
 }
 break;

 //the delete operation
 case 'delete':

 //for the delete operation we are getting a GET parameter from the url having the id of the record to be deleted
 if(isset($_GET['id'])){
 $db = new ProduitOperation();
 if($db->delete($_GET['id'])){
 $response['error'] = false;
 $response['message'] = 'user deleted successfully';
 }else{
 $response['error'] = true;
 $response['message'] = 'Some error occurred please try again';
 }
 }else{
 $response['error'] = true;
 $response['message'] = 'Nothing to delete, provide an id please';
 }
 break;
 }

 }else{
 //if it is not api call
 //pushing appropriate values to response array
 $response['error'] = true;
 $response['message'] = 'Invalid API Call';
 }

 //displaying the response in json structure
 echo json_encode($response);


 ?>
