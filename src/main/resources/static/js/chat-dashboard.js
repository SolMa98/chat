// 페이지 진입 시 이벤트
(function init() {
    getRoomList()
})();

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

function makeRoomList(rooms){
    let roomListSection = document.getElementById("room-list-section");
    let roomHtml = "";

    rooms.forEach(function(room){
        roomHtml += `<li onclick="chatRoomSelected('${room.roomId}')">
                        <span>${room.roomId}</span>
                    </li>`;
    });

    roomListSection.innerHTML = roomHtml;
}

function chatRoomSelected(roomId) {
    console.log("채팅방 선택");

    reConnect(roomId);
}