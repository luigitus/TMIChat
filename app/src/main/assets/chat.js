function addMessage(b, f, m) {
	div = document.createElement("div");
	li = document.createElement("li");
	timestamp = document.createElement("span");
	badges = document.createElement("span");
	from = document.createElement("span");
	colon = document.createElement("span");
	message = document.createElement("span");

	timestamp.setAttribute("class", "timestamp");
	badges.setAttribute("class", "badges");
	from.setAttribute("class", "from");
	colon.setAttribute("class", "colon");
	message.setAttribute("class", "message");

	timestamp.innerHTML = "";
	badges.innerHTML = b;
	from.innerHTML = f;
	colon.innerHTML = ":";
	message.innerHTML = " " + m;

	li.appendChild(timestamp);
	li.appendChild(badges);
	li.appendChild(from);
	li.appendChild(colon);
	li.appendChild(message);
	div.appendChild(li);

	document.getElementById("chat").insertAdjacentHTML("afterend", div.innerHTML)
}