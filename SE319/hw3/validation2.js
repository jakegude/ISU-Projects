function validate2() {
	var resultEmailCheck = emailCheck(document.forms["contact information"]["email"].value);
	var resultPhoneNumberCheck = phoneNumberCheck(document.forms["contact information"]["phone number"].value);
	var resultAddressCheck = addressCheck(document.forms["contact information"]["address"].value);
	var image1 = getImage(Boolean(resultEmailCheck), "email");
	var image2 = getImage(Boolean(resultPhoneNumberCheck), "phone number");
	var image3 = getImage(Boolean(resultAddressCheck), "address");
	var labelEmail = getNotification(resultEmailCheck, "email");
	var labelPhoneNumber = getNotification(resultPhoneNumberCheck, "phone number");
	var labelAddress = getNotification(resultAddressCheck, "address");
	document.getElementById("email").appendChild(image1);
	document.getElementById("email").appendChild(labelEmail);
	document.getElementById("phone number").appendChild(image2);
	document.getElementById("phone number").appendChild(labelPhoneNumber);
	document.getElementById("address").appendChild(image3);
	document.getElementById("address").appendChild(labelAddress);

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

function phoneNumberCheck(phoneNumber){
	splits = phoneNumber.split('-');
	if(splits.length == 1 && numericCheck(phoneNumber) && phoneNumber.length == 10) return true;
	if(splits.length == 3 && numericCheck(splits[0]) && numericCheck(splits[1]) && numericCheck(splits[2])
		&& splits[0].length == 3 && splits[1].length == 3 && splits[2].length == 4) return true;
	return false;
}

function addressCheck(address){
	atSplit = address.split(',');
	if(atSplit.length == 2){
		if(atSplit[0].length > 0 && atSplit[1].length > 0) return true;
	}
	return false;
}

function numericCheck(entry) {
	regex = /^[0-9]+$/i;
	if(entry != null && entry.match(regex)) return true;
	return false;
}

function alphaNumCheck(entry) {
    let regex = /^[a-z0-9]+$/i;
    if (entry != null && entry.match(regex)) {
        return true;
    } else {
        return false;
    }
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

function getNotification(bool, ID) {
    var label = document.getElementById("labelNotify" + ID);
    if (label == null) {
        label = document.createElement("LABEL");
        label.id = "labelNotify" + ID;
        label.setAttribute( 'class', 'errorMessage' );
    }

    switch(ID){
    	case 'email':
    		label.innerHTML = bool ? "" : "Email should be in the form xxx@xxx.xxx, which x should be alphanumeric";
    		break;
    	case 'phone number':
    		label.innerHTML = bool ? "" : "Phone Number should be in the form xxx-xxx-xxxx or xxxxxxxxxx, which x should be numeric";
    		break;
    	case 'address':
    		label.innerHTML = bool ? "" : "Address should be in the form city, state";
    		break;
    	default:
    		label.innerHTML = "invalid label name";
    }
    return label;
}