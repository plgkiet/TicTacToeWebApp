"use strict";
let currentBoard = "- - - - - - - - -";
const buttons = document.getElementsByClassName("button");
const resetButton = document.getElementById("reset");
const message = document.querySelector(".message");
let gameEnded = false;

function updateBoard(currentBoard) {
    const boardArray = currentBoard.split(" ");
    for (let i = 0; i < buttons.length; i++) {
        if (boardArray[i] === '-') {
            buttons[i].innerHTML = '';
            if (!gameEnded) {
                buttons[i].disabled = false;
            }
        } else {
            buttons[i].innerHTML = boardArray[i];
            buttons[i].disabled = true;
        }
    }
}

resetButton.addEventListener("click", () => {
    message.textContent = "";
    currentBoard = "- - - - - - - - -";
    for (let i = 0; i < buttons.length; i++) {
        buttons[i].innerHTML = '';
        buttons[i].disabled = false;
        buttons[i].style.backgroundColor = ''; // Đặt lại về màu nền ban đầu được xác định trong CSS
    }
    gameEnded = false;
});

for (let i = 0; i < buttons.length; i++) {
    buttons[i].addEventListener("click", () => {
        const play = {
            board: currentBoard,
            move: parseInt(buttons[i].id)
        };
        
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = () => {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    const { status, board } = JSON.parse(xhr.responseText);
                    if (status === "Continues!") {
                        message.textContent = "Your turn!";
                        message.style.color = "blue";
                    } else {
                        message.textContent = status;
                        message.style.fontWeight = "bold";

                        if (status === "Invalid Move!" || status === "You lost!") {
                            message.style.color = "red";
                        } else if (status === "Draw!") {
                            message.style.color = "orange";
                        } else {
                            message.style.color = "green"; // You won!
                        }
                        for (let j = 0; j < buttons.length; j++) {
                            buttons[j].disabled = true;
                            buttons[j].style.backgroundColor = '#33CCCC';
                        } 
                        gameEnded = true;
                    }
                    currentBoard = board;
                    updateBoard(board); 
                }
            }
        };
        xhr.open("PUT", "move", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        const body = JSON.stringify(play);
        xhr.send(body);
    });
}
