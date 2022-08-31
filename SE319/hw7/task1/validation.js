function validate() {
  var resultFirstName = alphaNumCheck(document.forms["formUserDetails"]["txtFirstName"].value);
	var resultLastName = alphaNumCheck(document.forms["formUserDetails"]["txtLastName"].value);
	var resultGender = dropCheck(document.forms["formUserDetails"]["selectGender"].value);
	var resultState = dropCheck(document.forms["formUserDetails"]["selectState"].value);
  var resultEmailCheck = emailCheck(document.forms["formUserDetails"]["txtEmail"].value);
  var resultPhoneCheck = phoneCheck(document.forms["formUserDetails"]["txtPhone"].value);
  var resultAddressCheck = addressCheck(document.forms["formUserDetails"]["txtAddress"].value);

	var labelNotifyFirstName=getNotificationName(Boolean(resultFirstName), "txtFirstName") ;
	var labelNotifyLastName=getNotificationName(Boolean(resultLastName), "txtLastName") ;
	var labelNotifyGender=getNotificationDrop(Boolean(resultGender), "selectGender") ;
	var labelNotifyState=getNotificationDrop(Boolean(resultState), "selectState") ;
  var labelNotifyEmail1= getNotification(Boolean(resultEmailCheck), "txtEmail") ;
  var labelNotifyPhone= getNotificationPhone(Boolean(resultPhoneCheck), "txtPhone") ;
  var labelNotifyAddress=getNotificationAddress(Boolean(resultAddressCheck), "txtAddress") ;




  var image1 = getImage(Boolean(resultFirstName), "txtFirstName");
	var image2 = getImage(Boolean(resultLastName), "txtLastName");
	var image3 = getImage(Boolean(resultGender), "selectGender");
	var image4 = getImage(Boolean(resultState), "selectState");
  var image5 = getImage(Boolean(resultEmailCheck), "txtEmail");
  var image6 = getImage(Boolean(resultPhoneCheck), "txtPhone");
  var image7 = getImage(Boolean(resultAddressCheck), "txtAddress");

  document.getElementById("FirstName").appendChild(image1);
	document.getElementById("LastName").appendChild(image2);
	document.getElementById("Gender").appendChild(image3);
	document.getElementById("State").appendChild(image4);

	document.getElementById("FirstName").appendChild(labelNotifyFirstName);
	document.getElementById("LastName").appendChild(labelNotifyLastName);
	document.getElementById("Gender").appendChild(labelNotifyGender);
	document.getElementById("State").appendChild(labelNotifyState);

  document.getElementById("Email").appendChild(image5);
  document.getElementById("Email").appendChild(labelNotifyEmail1);

  document.getElementById("Phone").appendChild(image6);
  document.getElementById("Phone").appendChild(labelNotifyPhone);

  document.getElementById("Address").appendChild(image7);
  document.getElementById("Address").appendChild(labelNotifyAddress);

	if(resultFirstName&&resultLastName && resultGender&&resultState&&resultEmailCheck&&resultPhoneCheck&&resultAddressCheck)
	{
		// alert('OK')
    var labelResult=getNotificationFinalResult(true, "txtFinalResult","OK","Error") ;
	} else{
    // alert('Failed')
    var labelResult=getNotificationFinalResult(false, "txtFinalResult","OK","Error") ;
  }
  document.getElementById("FinalResult").appendChild(labelResult);
}

function getNotificationFinalResult(bool, ID,messageOK,messageFailed) {
    var label = document.getElementById("labelNotify" + ID);
    if (label == null) {
        label = document.createElement("LABEL");
        label.id = "labelNotify" + ID;
        // label.className = "errorMessage";
        label.setAttribute( 'class', 'errorMessage' );
      }

    label.innerHTML = bool ? messageOK : messageFailed;
    return label;
}

function getNotification(bool, ID) {
    var label = document.getElementById("labelNotify" + ID);
    if (label == null) {
        label = document.createElement("LABEL");
        label.id = "labelNotify" + ID;
        // label.className = "errorMessage";
        label.setAttribute( 'class', 'errorMessage' );
      }

    label.innerHTML = bool ? "" : "Email should be in form xxx@xxx.xxx, which x should be alphanumeric!";
    return label;
}

function getNotificationPhone(bool, ID) {
    var label = document.getElementById("labelNotify" + ID);
    if (label == null) {
        label = document.createElement("LABEL");
        label.id = "labelNotify" + ID;
        // label.className = "errorMessage";
        label.setAttribute( 'class', 'errorMessage' );
      }

    label.innerHTML = bool ? "" : "Phone Must be in the form xxx-xxx-xxxx or xxxxxxxxxx. x should be numeric!";
    return label;
}

function getNotificationAddress(bool, ID) {
    var label = document.getElementById("labelNotify" + ID);
    if (label == null) {
        label = document.createElement("LABEL");
        label.id = "labelNotify" + ID;
        // label.className = "errorMessage";
        label.setAttribute( 'class', 'errorMessage' );
      }

    label.innerHTML = bool ? "" : "Must be in the form of city & state. example: Ames,IA";
    return label;
}

function getNotificationName(bool, ID) {
    var label = document.getElementById("labelNotify" + ID);
    if (label == null) {
        label = document.createElement("LABEL");
        label.id = "labelNotify" + ID;
        // label.className = "errorMessage";
        label.setAttribute( 'class', 'errorMessage' );
      }

    label.innerHTML = bool ? "" : "Must contain only alphabetic or numeric characters.";
    return label;
}

function getNotificationDrop(bool, ID) {
    var label = document.getElementById("labelNotify" + ID);
    if (label == null) {
        label = document.createElement("LABEL");
        label.id = "labelNotify" + ID;
        // label.className = "errorMessage";
        label.setAttribute( 'class', 'errorMessage' );
      }

    label.innerHTML = bool ? "" : "Select from given list!";
    return label;
}


function getImage(bool, ID) {
    var image = document.getElementById("image" + ID);
    if (image == null) {
        image = new Image(15, 15);
        image.id = "image" + ID;
    }
    image.src = bool ? './correct.png' : './wrong.png';
    return image;
}

function dropCheck(entry)
{
	if(entry != "")
	{
		return true;
	}
	else
	{
		false
	}
}
function emailCheck(email) {
    atSplit = email.split('@');
    if (atSplit.length == 2 && alphaNumCheck(atSplit[0])) {
        periodSplit = atSplit[1].split('.')
        if (periodSplit.length == 2 && alphaNumCheck(periodSplit[0] + periodSplit[1])) {
            return true;
        }
    }
    valCheck = false;
    return false;
}


function phoneCheck(phone) {
	if(!isNaN(phone) && phone.length == 10){
		return true;
	}
	else
	{
		dashSplit = phone.split('-');
		if (dashSplit.length == 3 && !isNaN(dashSplit[0] + dashSplit[1] + dashSplit[2]) && (dashSplit[0].length + dashSplit[1].length + dashSplit[2].length == 10))
		{
			return true;
		}
		return false;
	}
	return false;
}


function addressCheck(address) {
    commaSplit = address.split(',');
    if (commaSplit.length == 2) {
		if(alphaNumCheck(commaSplit[0] + commaSplit[1]) && !(isDigit(commaSplit[0] + commaSplit[1])))
		{
         return true;
        }
		else
		{return false;}
    }
    valCheck = false;
    return false;
}

function isDigit(entry)
{
	var regex = /^[0-9]+$/;
      if(entry != null && entry.match(regex)){
      return true;
      }
      else{
      return false;
      }
}

function alphaNumCheck(entry) {
    let regex = /^[a-z0-9]+$/i;
    if (entry != null && entry.match(regex)) {
        return true;
    } else {
        return false;
    }
}

function deleteCookie( name ) {
  document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}
