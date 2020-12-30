<?php
	session_start();
?>
<html>
	<head>

		<title>RefugeLand | Verifier</title>

		<meta charset="utf-8">

		<link rel="canonical" href="https://getbootstrap.com/docs/4.0/examples/floating-labels/">

		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
		
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>

		<link href="../floating-labels.css" rel="stylesheet">

		<!-- ReCaptcha -->
		<script src="https://www.google.com/recaptcha/api.js" async defer></script>

	</head>
	<body>
		<form class="form-signin" method="POST" action="../" id="captcha_form">
			<div class="text-center mb-4">
				<br>
				<h1 class="h3 mb-3 font-weight-normal">Verifier</h1>

				<?php if(empty($_POST['g-recaptcha-response'])) { ?>
				<p>Please complete the captcha.</p>
												
				<button class="btn btn-lg btn-primary btn-block" type="submit">Return</button>

				<?php } else {
					if (strlen($_POST['player_name']) < 4) { ?>

				<p>Invalid username.</p>
										
				<button class="btn btn-lg btn-primary btn-block" type="submit">Return</button>

				<?php } else {
					$server = "localhost";
					$user = "root";
					$password = "password";
					$dbname = "mysql";
					$conn = mysqli_connect($server, $user, $password, $dbname) or die("MySQL connection error.");

					$playername = filter_input(INPUT_POST, 'player_name', FILTER_SANITIZE_STRING);
					$ps = mysqli_prepare($conn, "SELECT * FROM verifiedaccounds WHERE player_name = ? LIMIT 1");
					mysqli_stmt_bind_param($ps, 's', $playername);
					mysqli_stmt_execute($ps);
					$result = mysqli_stmt_fetch($ps);
					if (!empty($_SERVER['HTTP_CLIENT_IP'])) {
						$ip = $_SERVER['HTTP_CLIENT_IP'];
					} elseif (!empty($_SERVER['HTTP_X_FORWARDED_FOR'])) {
						$ip = $_SERVER['HTTP_X_FORWARDED_FOR'];
					} else {
						$ip = $_SERVER['REMOTE_ADDR'];
					}

					if(!$result) {
						$stmt = mysqli_prepare($conn, "INSERT INTO verifiedaccounds (player_name, registered_date, ip) VALUES (?, NOW(), ?)");
						mysqli_stmt_bind_param($stmt, 's', $playername);
						mysqli_stmt_bind_param($stmt, 's', $ip);
						mysqli_stmt_execute($stmt);
						mysqli_stmt_close($stmt);
						mysqli_close($conn); 
				?> 

				<p>Successfully verified, <?php echo $_POST['player_name']; ?> welcome to RefugeLand!</p>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Return</button>

				<?php } else { ?> 

				<p>This username is already verified.</p>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Return</button>

				<?php }
					}
				} ?>
			</div>
		</form>
	</body>
</html>