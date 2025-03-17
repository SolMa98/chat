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
        roomHtml += `<div class="room-list ${active}" data-key="${room.roomId}" onclick="chatRoomSelected('${room.roomId}')">
                        <div class="room-list-icon" data-icon="ChatCircleDots" data-size="24px" data-weight="regular">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24px" height="24px" fill="currentColor" viewBox="0 0 256 256">
                                <path d="M140,128a12,12,0,1,1-12-12A12,12,0,0,1,140,128ZM84,116a12,12,0,1,0,12,12A12,12,0,0,0,84,116Zm88,0a12,12,0,1,0,12,12A12,12,0,0,0,172,116Zm60,12A104,104,0,0,1,79.12,219.82L45.07,231.17a16,16,0,0,1-20.24-20.24l11.35-34.05A104,104,0,1,1,232,128Zm-16,0A88,88,0,1,0,51.81,172.06a8,8,0,0,1,.66,6.54L40,216,77.4,203.53a7.85,7.85,0,0,1,2.53-.42,8,8,0,0,1,4,1.08A88,88,0,0,0,216,128Z"></path>
                            </svg>
                        </div>
                        <div class="room-list-info">
                            <p class="room-key">${room.roomId}</p>
                            <p class="room-message">Room Message Preview</p>
                        </div>
                    </div>`;
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
