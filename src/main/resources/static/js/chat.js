let stompClient = null;
let senderKey = Math.random().toString(36).substr(2,11);
let roomKey = "";

// 페이지 진입 시 이벤트
(function init() {
    connect();
})();

// 소켓 연결
function connect(event){
    let socket = new WebSocket('/ws');
    stompClient  = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function reConnect(roomId){
    // 기존 연결 종료
    if(stompClient && stompClient.connected){
        stompClient.disconnect(function (){
           console.log("소켓 연결이 끊어졌습니다.");

            let socket = new WebSocket('/ws');
            stompClient = Stomp.over(socket);

            stompClient.connect({}, function() {
                console.log("새로운 STOMP 연결이 완료되었습니다.");
                stompClient.subscribe('/sub/' + roomId, onMessageReceived);
                let chatSelectedRoom = document.getElementById("selected-chat-room");
                chatSelectedRoom.value = roomId;
                roomKey = roomId;

            }, onError);

        });
    }
}

// 채팅방 연결 : default => 새로운 채팅방을 생성
function onConnected() {
    fetch("http://localhost:8080/chat/room", {
        method: "POST"
    })
    .then((res) => {
        if (!res.ok) {
            throw new Error("Failed to create room");
        }
        return res.json();
    })
    .then((data) => {
        if (data) {
            roomKey = data.roomId;
            stompClient.subscribe('/sub/' + roomKey, onMessageReceived);
            let chatSelectedRoom = document.getElementById("selected-chat-room");
            chatSelectedRoom.value = roomKey;

            // 새로운 채팅방 생성 공지
            stompClient.send("/pub/chat/new");
        } else {
            console.error('Data is undefined');
        }
    })
    .catch((error) => {
        console.error("Error:", error);
    });
}

function onError(error) {
    console.log('STOMP 연결 오류:', error);
}

// 메시지 리시버
function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);

    if(message.sender === "system"){
        if(message?.content === "NEW_ROOM"){
            getRoomList();
        }
    }else{
        // 내가 보낸 메시지와 상대가 보낸 메시지 구분
        if(message.senderKey === senderKey){
            makeMyMessage(message.content);
        }else{
            makeThemMessage(message.content);
        }
    }
}

// 메시지 전송
function sendMessage(event) {
    let messageInputElement = document.getElementById("chatMessage");

    let message = {
        roomId: roomKey,
        sender: "agent",
        senderKey: senderKey,
        content: messageInputElement.value
    }

    messageInputElement.value = "";
    stompClient.send("/pub/chat/message", {}, JSON.stringify(message));
}

// 메시지 입력창 키다운 이벤트
function sendKeyDownEvent(event){
    if (event.key === 'Enter') {
        sendMessage();
    }
}

// 내가 보낸 채팅방 메시지
function makeMyMessage(message){
    let myMessageHtml = `<div class="me-message">
                                    <div class="message">${message}</div>
                                    <div class="emoji">🤖</div>
                                </div>`;

    document.getElementById("chatMessageView").innerHTML += (myMessageHtml);
}

// 상대방이 보낸 채팅방 메시지
function makeThemMessage(message){
    let themMessageHtml = `<div class="them-message">
                                        <div class="emoji">👨‍💻</div>
                                        <div class="message">${message}</div>
                                    </div>`;

    document.getElementById("chatMessageView").innerHTML += themMessageHtml;
}