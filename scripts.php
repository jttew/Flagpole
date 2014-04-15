<?

// POST variable method can be:

// validateUser (email, password) -> return Integer user_id if true, return 0 if failure
// createUser (email, password, first_name, last_name) -> return Integer user_id if success, return 0 if failure (email already exists/other error)
// getUserFirstName (user_id) -> return String first_name
// getUserLastName (user_id) -> return String last_name
// getUserEmail (user_id) -> return String email
// getUserByEmail (email) -> return Integer user_id if true, return 0 if failure

// getFlagsInRadius ( Double latitude, Double longitude, radius ) -> radius in meters, return List<Integer> of Flag ids -> only Active flags
// getFlagTitle (flag_id) -> return String title
// getFlagContent (flag_id) -> return String content
// getFlagsByAuthor (user_id) -> return List<Integer> of flag ids
// getFlagAuthor (flag_id) -> return Integer user_id
// getFlagLatitude (flag_id) -> return Float latitude
// getFlagLongitude (flag_id) -> return Float longitude
// getFlagStatus (flag_id) -> return String status -> ("Active","Expired","Private","Deleted")
// getFlagRecipients (flag_id) -> return Integer recipients
// getFlagScore (flag_id) -> return Integer score
// incrementFlagScore (flag_id) -> return Integer score
// decrementFlagScore (flag_id) -> return Integer score
// incrementFlagRecipients (flag_id) -> return Integer recipients
// decrementFlagRecipients (flag_id) -> return Integer recipients
// getFlagPostTime (flag_id) -> return Date time_posted
// getFlagExpireTime (flag_id) -> return Date time_expire
// getFlagImage (flag_id) -> return String img_url

// createFlag (user_id, title, content, recipients, img_url, Double latitude, Double longitude) -> return Integer flag_id if true, return 0 if failure
// setFlagStatusActive (flag_id) -> return 1 if success, 0 otherwise
// setFlagStatusExpired (flag_id) -> sets status to Expired, sets time_expire to Now, return 1 if success, 0 otherwise
// setFlagStatusPrivate (flag_id) -> return 1 if success, 0 otherwise
// setFlagStatusDeleted (flag_id) -> return 1 if success, 0 otherwise

// getDistanceToFlag (flag_id, Double latitude, Double longitude) -> return Double distance -> distance in meters between given Flag and given coords

$return_id = 0; 


function getDistance( $latitudeFrom, $longitudeFrom, $latitudeTo, $longitudeTo )
{
  $latFrom = deg2rad($latitudeFrom);
  $lonFrom = deg2rad($longitudeFrom);
  $latTo = deg2rad($latitudeTo);
  $lonTo = deg2rad($longitudeTo);

  $lonDelta = $lonTo - $lonFrom;
  $a = pow(cos($latTo) * sin($lonDelta), 2) +
    pow(cos($latFrom) * sin($latTo) - sin($latFrom) * cos($latTo) * cos($lonDelta), 2);
  $b = sin($latFrom) * sin($latTo) + cos($latFrom) * cos($latTo) * cos($lonDelta);

  $angle = atan2(sqrt($a), $b);
  return $angle * 6371000;
}


function getResult( $query1 ) {
	$con=mysqli_connect("localhost","unexactl_flag","flagpole33","unexactl_flagpole");
	if (mysqli_connect_errno($con)) { echo "Failed to connect to MySQL: " . mysqli_connect_error(); }
	$returned = mysqli_query($con, $query1 );
	$row = mysqli_fetch_array($returned);
	global $return_id; 
	$return_id = mysqli_insert_id($con);
	return $row[0];  
}



function validateUser( $email, $password ) {
	$query = "SELECT id FROM users WHERE email='$email' AND password='$password'";
	$answer = getResult( $query ); 
	if ( !$answer ) {
		return '0';
	}
	else {
		return $answer; 
	}
}

function createUser( $email, $password, $first_name, $last_name ) {
	$query = "SELECT id FROM users WHERE email = '$email'"; 
	$user_id = -1;
	if ( getResult( $query ) ) {
		$user_id = '0'; 
	}
	else {
		$query = "INSERT INTO users (first_name, last_name, email, password) VALUES ('$first_name', '$last_name', '$email', '$password')"; 
		$discard = getResult( $query ); 
		$query = "SELECT id FROM users WHERE email = '$email'"; 
		$answer = getResult( $query ); 
		if ( !$answer ) {
			$user_id = '0';
		}
		else {
			$user_id = $answer; 
		}
	}
	return $user_id;
}

function getUserFirstName( $user_id ) {
	$query = "SELECT first_name FROM users WHERE id = $user_id"; 
	return getResult( $query ); 
}

function getUserLastName( $user_id ) {
	$query = "SELECT last_name FROM users WHERE id = $user_id"; 
	return getResult( $query ); 
}
function getUserEmail( $user_id ) {
	$query = "SELECT email FROM users WHERE id = $user_id"; 
	return getResult( $query ); 
}
function getUserByEmail( $email ) {
	$query = "SELECT id FROM users WHERE email = '$email'"; 
	return getResult( $query ); 
}
	
function getFlagsInRadius ( $latitude, $longitude, $radius ) {
	$query = "SELECT id, latitude, longitude FROM flags WHERE status = 'Active'";
	$con=mysqli_connect("localhost","unexactl_flag","flagpole33","unexactl_flagpole");
	if (mysqli_connect_errno($con)) { echo "Failed to connect to MySQL: " . mysqli_connect_error(); }
	$returned = mysqli_query($con, $query );
	$flag_ids = "";
	while($row = mysqli_fetch_array($returned)) {
		$distance = getDistance ( $latitude, $longitude, $row['latitude'], $row['longitude'] ); 
		if ( $distance <= $radius ) {
	  		$flag_ids .= ($row['id'].","); 
	 	}
	}
	return $flag_ids; 	
}

function getFlagTitle( $flag_id ) {
	$query = "SELECT title FROM flags WHERE id = $flag_id"; 
	return getResult( $query ); 
}

function getFlagContent( $flag_id ) {
	$query = "SELECT content FROM flags WHERE id = $flag_id"; 
	return getResult( $query ); 
}
	
function getFlagsByAuthor( $user_id ) {
	$query = "SELECT id FROM flags WHERE user_id = $user_id"; 
	$con=mysqli_connect("localhost","unexactl_flag","flagpole33","unexactl_flagpole");
	if (mysqli_connect_errno($con)) { echo "Failed to connect to MySQL: " . mysqli_connect_error(); }
	$returned = mysqli_query($con, $query );
	$flag_ids = "";
	while($row = mysqli_fetch_array($returned)) {
		$flag_ids .= ($row['id'].","); 
	}
	return $flag_ids; 
}

function getFlagAuthor( $flag_id ) {
	$query = "SELECT user_id FROM flags WHERE id = $flag_id"; 
	return getResult( $query ); 
}
	
function getFlagLatitude( $flag_id ) {
	$query = "SELECT latitude FROM flags WHERE id = $flag_id"; 
	return getResult( $query ); 
}

function getFlagLongitude( $flag_id ) {
	$query = "SELECT longitude FROM flags WHERE id = $flag_id"; 
	return getResult( $query ); 
}
function getFlagStatus( $flag_id ) {
	$query = "SELECT status FROM flags WHERE id = $flag_id"; 
	return getResult( $query ); 
}
function getFlagRecipients( $flag_id ) {
	$query = "SELECT recipients FROM flags WHERE id = $flag_id"; 
	return getResult( $query ); 
}
function getFlagScore( $flag_id ) {
	$query = "SELECT score FROM flags WHERE id = $flag_id"; 
	return getResult( $query ); 
}
function incrementFlagScore( $flag_id ) {
	$query = "UPDATE flags SET score=score+1 WHERE id = $flag_id";
	getResult( $query ); 
	$query = "SELECT score FROM flags WHERE id = $flag_id"; 
	return getResult( $query );
}
function decrementFlagScore( $flag_id ) {
	$query = "UPDATE flags SET score=score-1 WHERE id = $flag_id";
	getResult( $query ); 
	$query = "SELECT score FROM flags WHERE id = $flag_id"; 
	return getResult( $query );
}
function incrementFlagRecipients( $flag_id ) {
	$query = "UPDATE flags SET recipients= recipients +1 WHERE id = $flag_id";
	getResult( $query ); 
	$query = "SELECT recipients FROM flags WHERE id = $flag_id"; 
	return getResult( $query );
}
function decrementFlagRecipients( $flag_id ) {
	$query = "UPDATE flags SET recipients = recipients-1 WHERE id = $flag_id";
	getResult( $query ); 
	$query = "SELECT recipients FROM flags WHERE id = $flag_id"; 
	return getResult( $query ); 
}
function getFlagPostTime( $flag_id ) {
	$query = "SELECT time_posted FROM flags WHERE id = $flag_id"; 
	return getResult( $query ); 
}
function getFlagExpireTime( $flag_id ) {
	$query = "SELECT time_expire FROM flags WHERE id = $flag_id"; 
	return getResult( $query ); 
}
function getFlagImage( $flag_id ) {
	$query = "SELECT img_url FROM flags WHERE id = $flag_id"; 
	return getResult( $query ); 
}
function createFlag( $user_id, $title, $content, $recipients, $img_url, $latitude, $longitude) {
	$query = "INSERT INTO flags (user_id, title, content, recipients, score, time_posted, time_expire, img_url, latitude, longitude, status) VALUES ($user_id, '$title', '$content', $recipients, 0, NOW(), NOW() + INTERVAL 7 DAY, '$img_url', $latitude, $longitude, 'Active')"; 
	getResult( $query ); 
	global $return_id; 
	return $return_id;
}
function setFlagStatusActive( $flag_id ) {
	$query = "UPDATE flags SET status = 'Active' WHERE id = $flag_id"; 
	getResult( $query ); 
	$query = "SELECT id FROM flags WHERE id = $flag_id"; 
	if ( getResult($query) ) { return 1; }
	else { return 0; }
}
function setFlagStatusPrivate( $flag_id ) {
	$query = "UPDATE flags SET status = 'Private' WHERE id = $flag_id"; 
	getResult( $query ); 
	$query = "SELECT id FROM flags WHERE id = $flag_id"; 
	if ( getResult($query) ) { return 1; }
	else { return 0; }
}
function setFlagStatusExpired( $flag_id ) {
	$query = "UPDATE flags SET status = 'Expired' WHERE id = $flag_id"; 
	getResult( $query ); 
	$query = "UPDATE flags SET time_expire = NOW() WHERE id = $flag_id"; 
	getResult( $query ); 
	$query = "SELECT id FROM flags WHERE id = $flag_id"; 
	if ( getResult($query) ) { return 1; }
	else { return 0; }
}
function setFlagStatusDeleted( $flag_id ) {
	$query = "UPDATE flags SET status = 'Deleted' WHERE id = $flag_id"; 
	getResult( $query ); 
	$query = "SELECT id FROM flags WHERE id = $flag_id"; 
	if ( getResult($query) ) { return 1; }
	else { return 0; }
}
function getDistanceToFlag( $flag_id, $latitude, $longitude ) {
	$query = "SELECT id FROM flags WHERE id = $flag_id"; 
	$answer = getResult( $query ); 
	if ( !$answer ) { $distance = 0; }
	else {
		$flag_lat = getFlagLatitude($flag_id); 
		$flag_lon = getFlagLongitude($flag_id); 
		$distance = getDistance($flag_lat, $flag_lon, $latitude, $longitude);
	}
	return $distance; 
}


function output( $result ) {
	echo $result; 
}


if (isset($_POST['method'])) {
	$method = $_POST['method'];
	$con=mysqli_connect("localhost","unexactl_flag","flagpole33","unexactl_flagpole");
	if (mysqli_connect_errno($con)) { echo "Failed to connect to MySQL: " . mysqli_connect_error(); }
	switch ($method) {
		case 'validateUser' : 
			$result = validateUser( mysqli_real_escape_string($con, $_POST['email']) , mysqli_real_escape_string($con, $_POST['password']) ); 
			break;
		case 'createUser' : 
			$result = createUser( mysqli_real_escape_string($con, $_POST['email']), mysqli_real_escape_string($con, $_POST['password']), mysqli_real_escape_string($con, $_POST['first_name']), mysqli_real_escape_string($con, $_POST['last_name']) );
			break; 
		case 'getUserFirstName' : 
			$result = getUserFirstName( mysqli_real_escape_string($con, $_POST['user_id']) ); 
			break;
		case 'getUserLastName' : 
			$result = getUserLastName( mysqli_real_escape_string($con, $_POST['user_id']) ); 
			break;
		case 'getUserEmail' : 
			$result = getUserEmail( mysqli_real_escape_string($con, $_POST['user_id']) ); 
			break;
		case 'getUserByEmail' : 
			$result = getUserByEmail( mysqli_real_escape_string($con, $_POST['email']) ); 
			break;
		case 'getFlagsInRadius' : 
			$result = getFlagsInRadius( mysqli_real_escape_string($con, $_POST['latitude']), mysqli_real_escape_string($con, $_POST['longitude']), mysqli_real_escape_string($con, $_POST['radius']) ); 
			break; 
		case 'getFlagTitle' : 
			$result = getFlagTitle( mysqli_real_escape_string($con, $_POST['flag_id']) ); 
			break; 
		case 'getFlagContent' : 
			$result = getFlagContent( mysqli_real_escape_string($con, $_POST['flag_id']) ); 
			break; 
		case 'getFlagsByAuthor' : 
			$result = getFlagsByAuthor( mysqli_real_escape_string($con, $_POST['user_id']) ); 
			break; 
		case 'getFlagAuthor' : 
			$result = getFlagAuthor(  mysqli_real_escape_string($con, $_POST['flag_id']) ); 
			break; 
		case 'getFlagLatitude' : 
			$result = getFlagLatitude( mysqli_real_escape_string($con, $_POST['flag_id']) ); 
			break;
		case 'getFlagLongitude' : 
			$result = getFlagLongitude( mysqli_real_escape_string($con, $_POST['flag_id']) ); 
			break;
		case 'getFlagStatus' : 
			$result = getFlagStatus( mysqli_real_escape_string($con, $_POST['flag_id'])); 
			break;
		case 'getFlagRecipients' : 
			$result = getFlagRecipients( mysqli_real_escape_string($con, $_POST['flag_id'])); 
			break;
		case 'getFlagScore' : 
			$result = getFlagScore( mysqli_real_escape_string($con, $_POST['flag_id'])); 
			break;
		case 'incrementFlagScore' : 
			$result = incrementFlagScore( mysqli_real_escape_string($con, $_POST['flag_id'])); 
			break;
		case 'decrementFlagScore' : 
			$result = decrementFlagScore( mysqli_real_escape_string($con, $_POST['flag_id']) ); 
			break;
		case 'incrementFlagRecipients' : 
			$result = incrementFlagRecipients( mysqli_real_escape_string($con, $_POST['flag_id']) ); 
			break;
		case 'decrementFlagRecipients' : 
			$result = decrementFlagRecipients( mysqli_real_escape_string($con, $_POST['flag_id'])); 
			break;
		case 'getFlagPostTime' : 
			$result = getFlagPostTime(  mysqli_real_escape_string($con, $_POST['flag_id'])); 
			break;
		case 'getFlagExpireTime' : 
			$result = getFlagExpireTime(  mysqli_real_escape_string($con, $_POST['flag_id'])); 
			break;
		case 'getFlagImage' : 
			$result = getFlagImage(  mysqli_real_escape_string($con, $_POST['flag_id']) ); 
			break;
		case 'createFlag' : 
			$result = createFlag( mysqli_real_escape_string($con, $_POST['user_id']), mysqli_real_escape_string($con, $_POST['title']), mysqli_real_escape_string($con, $_POST['content']), mysqli_real_escape_string($con, $_POST['recipients']), mysqli_real_escape_string($con, $_POST['img_url']),mysqli_real_escape_string($con, $_POST['latitude']), mysqli_real_escape_string($con, $_POST['longitude']) ); 
			break;
		case 'setFlagStatusActive' : 
			$result = setFlagStatusActive (  mysqli_real_escape_string($con, $_POST['flag_id'])); 
			break; 
		case 'setFlagStatusExpired' : 
			$result = setFlagStatusExpired (  mysqli_real_escape_string($con, $_POST['flag_id'])); 
			break; 
		case 'setFlagStatusPrivate' : 
			$result = setFlagStatusPrivate (  mysqli_real_escape_string($con, $_POST['flag_id'])); 
			break; 
		case 'setFlagStatusDeleted' : 
			$result = setFlagStatusDeleted (  mysqli_real_escape_string($con, $_POST['flag_id'])); 
			break; 
		case 'getDistanceToFlag' : 
			$result = getDistanceToFlag ( mysqli_real_escape_string($con, $_POST['flag_id']), mysqli_real_escape_string($con, $_POST['latitude']), mysqli_real_escape_string($con, $_POST['longitude']) ); 
			break;
		default : 
			$result = "Invalid method."; 
	}
	output( $result ); 
}
else {
	$result = "Method not provided."; 
	output( $result ); 
}
 
mysqli_close($con);
?>