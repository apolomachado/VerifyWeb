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

    	<link href="floating-labels.css" rel="stylesheet">

    	<script src="https://www.google.com/recaptcha/api.js" async defer></script>

    	<?php $sitekey = "6LcCTBgaAAAAAPw8TyWq_-unVJLaqySQ4VE8WLwU";?>

  	</head>

  	<body>
    	<form class="form-signin" method="POST" action="verify/" id="captcha_form">

      		<div class="text-center mb-4">

        		<h1 class="h3 mb-3 font-weight-normal">Verifier</h1>
        		<p>Verify your account in this page.</a></p>

      		</div>

      		<div class="form-label-group">

        		<input type="text" id="txt" class="form-control" placeholder="Nickname" name="player_name" autocomplete="off" onpaste="return false" minlength="4" maxlength="16" required>
        		
        		<br>

        		<div class="recaptcha_div">
            		<div class="g-recaptcha" data-sitekey="<?php echo $sitekey ?>"></div>
          		</div>

        		<label for="txt">Nickname</label>

      		</div>
      		<button class="btn btn-lg btn-primary btn-block" type="submit">Verify</button>
    	</form>
  	</body>
</html>