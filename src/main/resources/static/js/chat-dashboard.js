// 페이지 진입 시 이벤트
(function init() {
    getRoomList();
})();

// 채팅방 리스트 가져오기
function getRoomList(){
    fetch("http://localhost:8080/chat/room", {
        method: "GET"
    })
    .then((res) => {
        if (!res.ok) {
            throw new Error("Failed to get rooms list");
        }
        return res.json();
    })
    .then((data) => {
        if (data) {
            makeRoomList(data);
        } else {
            console.error('Data is undefined');
        }
    }).catch((error) => {
        console.error("Error:", error);
    });
}

// 채팅방 화면 출력
function makeRoomList(rooms){
    let roomListSection = document.getElementById("room-list-section");
    let roomHtml = "";

    rooms.forEach(function(room){
        let active = document.getElementById("selected-chat-room").value === room.roomId ? "active" : "";
        roomHtml += `<li class="room-list ${active}" data-key="${room.roomId}" onclick="chatRoomSelected('${room.roomId}')">
                        <span>${room.roomId}</span>
                    </li>`;
    });

    roomListSection.innerHTML = roomHtml;
}

// 채팅방 선택
function chatRoomSelected(roomId) {
    reConnect(roomId);
}

// 채팅방 변경 이벤트 트리거
function chatRoomChangeEvent(key) {
    let chatSelectedRoom = document.getElementById("selected-chat-room");
    chatSelectedRoom.value = key;
    chatSelectedRoom.dispatchEvent(new Event("change"));
}

// 채팅방 변경 시 이벤트
document.getElementById("selected-chat-room").addEventListener("change", function() {
    let chatRoomList = document.getElementsByClassName("room-list");
    let chatSelectedRoomKey = document.getElementById("selected-chat-room").value;

    for(let room of chatRoomList){
        if(room.dataset.key === chatSelectedRoomKey){
            room.classList.add("active");
        }else{
            room.classList.remove("active");
        }
    }
});
