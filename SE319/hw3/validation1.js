function validate1() {
	var resultFNameCheck = fnameCheck(document.forms["Validation Form"]["fname"].value);
	var resultLNameCheck = lnameCheck(document.forms["Validation Form"]["lname"].value);
	var resultGenderCheck = genderCheck(document.getElementById("gender").value);
	var resultStateCheck = stateCheck(document.getElementById("state").value);
	var image1 = getImage(Boolean(resultFNameCheck), "fname");
	var image2 = getImage(Boolean(resultLNameCheck), "lname");
	var image3 = getImage(Boolean(resultGenderCheck), "gender");
	var image4 = getImage(Boolean(resultStateCheck), "state");
	var labelFName = getNotification(Boolean(resultFNameCheck), "fname");
	var labelLName = getNotification(Boolean(resultLNameCheck), "lname");
	var labelGender = getNotification(Boolean(resultGenderCheck), "gender");
	var labelState = getNotification(Boolean(resultStateCheck), "state");
	document.getElementById("fname").appendChild(image1);
	document.getElementById("fname").appendChild(labelFName);
	document.getElementById("lname").appendChild(image2);
	document.getElementById("lname").appendChild(labelLName);
	document.getElementById("genderWrapper").appendChild(image3);
	document.getElementById("genderWrapper").appendChild(labelGender);
	document.getElementById("stateWrapper").appendChild(image4);
	document.getElementById("stateWrapper").appendChild(labelState);
	if(resultFNameCheck && resultLNameCheck && resultGenderCheck && resultStateCheck) location.assign("./validation2.html");
}

function fnameCheck(fname){
	return alphaNumCheck(fname);
}

function lnameCheck(lname){
	return alphaNumCheck(lname);
}

function genderCheck(gender){
	return (gender == "male" || gender == "female");
}

function stateCheck(state){
	switch(state){
		case 'California':
		case 'Florida':
		case 'New York':
		case 'Texas':
		case 'Hawaii':
		case 'Washington':
		case 'Colorado':
		case 'Virginia':
		case 'Iowa':
		case 'Arizona':
			return true;
			break;
		default:
			return false;
			break;
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
    	case 'fname':
    	case 'lname':
    		label.innerHTML = bool ? "" : "Only alphanumeric characters accepted";
    		break;
    	case 'gender':
    	case 'state':
    		label.innerHTML = bool ? "" : "Please select from drop down menu";
    		break;
    	default:
    		label.innerHTML = "invalid label name";
    }
    return label;
}