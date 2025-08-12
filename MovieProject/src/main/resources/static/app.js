const chatContainer = document.getElementById("chatContainer");
const chatHeader = document.getElementById("chatHeader");
const toggleChat = document.getElementById("toggleChat");
const messageBtn = document.querySelector("#messageForm");
const messageArea = document.querySelector("#messageArea");
const messageForm = document.querySelector("#msgForm");
const messageInput = document.querySelector("#msgInput");
const nameInput = document.querySelector("#name");
let socket = null;
let username = null;

//TODO: Close WS Connection on minimize...

chatHeader.addEventListener("click", function () {
    chatContainer.classList.toggle("minimized");
    toggleChat.textContent = chatContainer.classList.contains("minimized") ? "+" : "−";
});

messageForm.addEventListener("submit", sendMessage);
messageBtn.addEventListener("submit", connect);

function connect(e) {
    e.preventDefault();
    socket = new WebSocket(`ws://${window.location.hostname}:${window.location.port}/ws`);
    socket.onmessage = onMessageReceived;
    username = nameInput.value.trim();
    const welcomeMessage = {
        sender: "System",
        message: `${username} has joined the chat!`
    }
    socket.onopen = function () {
        socket.send(JSON.stringify(welcomeMessage));
    }
    addMessage(welcomeMessage.sender, welcomeMessage.message, false);
}

// let stompClient = null;
// let username = "";
//
// function connect(event) {
//   event.preventDefault();
//   username = nameInput.value.trim();
//
//   let socket = new SockJS("/ws");
//   stompClient = Stomp.over(socket);
//   stompClient.connect({}, function() {
//     // FOR MANUAL SUBSCRIPTION!!   stompClient.subscribe(`/queue/ai/${username}`, onMessageReceived);
//     stompClient.subscribe("/user/queue/ai", onMessageReceived);
//     addMessage("System", `Welcome, ${username}!`, false);
//     stompClient.send("/app/welcome", {}, JSON.stringify({
//       message: `${username} joined the chat`,
//       sender: "System"
//     }));
//   });
// }

function sendMessage(event) {
    event.preventDefault();
    const msg = messageInput.value.trim();

    if (!msg) return;
    // if(!stompClient || !username) {
    //   alert("Please connect first");
    //   return;
    // }

    addMessage(username, msg, true);
    const chatMessage = {sender: username, message: msg};
    //stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
    socket.send(JSON.stringify(chatMessage));
    messageInput.value = "";
}

function onMessageReceived(payload) {
    const message = JSON.parse(payload.data);
    //const message = JSON.parse(payload.body);
    addMessage(message.sender, message.message, message.sender === username);
}

function addMessage(sender, message, isUser) {
    if (chatContainer.classList.contains("minimized")) {
        chatContainer.classList.remove("minimized");
        toggleChat.textContent = "−";
    }

    const messageElement = document.createElement("div");
    messageElement.classList.add("message");
    messageElement.classList.add(isUser ? "user-message" : "other-message");

    const senderElement = document.createElement("div");
    senderElement.classList.add("fw-bold");
    senderElement.textContent = sender;

    const textElement = document.createElement("div");
    textElement.textContent = message;

    const timeElement = document.createElement("div");
    timeElement.classList.add("timestamp");
    timeElement.textContent = new Date().toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});

    messageElement.appendChild(senderElement);
    messageElement.appendChild(textElement);
    messageElement.appendChild(timeElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}