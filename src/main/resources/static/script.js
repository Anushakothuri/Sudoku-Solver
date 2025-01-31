document.addEventListener("DOMContentLoaded", function() {
    createSudokuGrid();
});

function createSudokuGrid() {
    let table = document.getElementById("sudoku-grid");
    for (let row = 0; row < 9; row++) {
        let tr = document.createElement("tr");
        for (let col = 0; col < 9; col++) {
            let td = document.createElement("td");
            let input = document.createElement("input");
            input.type = "text";
            input.maxLength = 1;
            input.id = `cell-${row}-${col}`;
            td.appendChild(input);
            tr.appendChild(td);
        }
        table.appendChild(tr);
    }
}

function solveSudoku() {
    let board = [];
    
    for (let row = 0; row < 9; row++) {
        let newRow = [];
        for (let col = 0; col < 9; col++) {
            let cellValue = document.getElementById(`cell-${row}-${col}`).value;
            newRow.push(cellValue ? parseInt(cellValue) : 0);
        }
        board.push(newRow);
    }

    fetch("/solve", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ board })
    })
    .then(response => response.json())
    .then(data => {
        if (data.solved) {
            updateGrid(data.board);
        } else {
            alert("No solution exists!");
        }
    })
    .catch(error => console.error("Error:", error));
}

function updateGrid(board) {
    for (let row = 0; row < 9; row++) {
        for (let col = 0; col < 9; col++) {
            document.getElementById(`cell-${row}-${col}`).value = board[row][col];
        }
    }
}
