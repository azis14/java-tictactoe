document.addEventListener("DOMContentLoaded", function () {
  const gameBoard = document.getElementById("game-board");

  if (!gameBoard) {
    console.error("Game board element not found");
    return;
  }

  const gameId = gameBoard.getAttribute("data-game-id");
  const resetButton = document.getElementById("reset-game");
  const gameStatus = document.querySelector("[data-game-status]")?.textContent;

  setupCellClickHandlers();
  checkGameStatus(gameStatus);

  if (resetButton) {
    resetButton.addEventListener("click", function () {
      resetGame(gameId);
    });
  }

  function setupCellClickHandlers() {
    const cells = document.querySelectorAll(".cell");

    cells.forEach((cell) => {
      cell.addEventListener("click", function () {
        const row = this.getAttribute("data-row");
        const col = this.getAttribute("data-col");

        if (row !== null && col !== null) {
          makeMove(gameId, row, col);
        }
      });
    });
  }

  function makeMove(gameId, row, col) {
    console.log(`Making move: row=${row}, col=${col}`);

    fetch(`/api/game/${gameId}/move?row=${row}&col=${col}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          window.location.reload();
        } else {
          // Show error message
          showToast(data.message || "Invalid move", "error");
        }
      })
      .catch((error) => {
        console.error("Error making move:", error);
        showToast("Error making move. Please try again.", "error");
      });
  }

  function resetGame(gameId) {
    fetch(`/api/game/${gameId}/reset`, {
      method: "POST",
    })
      .then((response) => {
        if (response.ok) {
          // Reload page to reflect the reset
          window.location.reload();
        } else {
          showToast("Failed to reset the game", "error");
        }
      })
      .catch((error) => {
        console.error("Error resetting game:", error);
        showToast("Error resetting game. Please try again.", "error");
      });
  }

  function checkGameStatus(status) {
    if (!status) return;

    const winnerName =
      document.querySelector("[data-winner-name]")?.textContent;

    if (status === "PLAYER_X_WON" || status === "PLAYER_O_WON") {
      showToast(`🎉 ${winnerName} has won the game!`, "success");
    } else if (status === "DRAW") {
      showToast("🤝 Game ended in a draw!", "info");
    }
  }

  function showToast(message, type) {
    const isDarkMode =
      document.documentElement.getAttribute("data-theme") === "dark";
    let bgColor;

    switch (type) {
      case "success":
        bgColor = isDarkMode
          ? "linear-gradient(to right, #009688, #4CAF50)"
          : "linear-gradient(to right, #00b09b, #96c93d)";
        break;
      case "error":
        bgColor = isDarkMode
          ? "linear-gradient(to right, #C62828, #E53935)"
          : "linear-gradient(to right, #ff5f6d, #ffc371)";
        break;
      case "info":
        bgColor = isDarkMode
          ? "linear-gradient(to right, #303F9F, #1976D2)"
          : "linear-gradient(to right, #3f51b5, #2196f3)";
        break;
      default:
        bgColor = isDarkMode ? "#424242" : "#757575";
    }

    Toastify({
      text: message,
      duration: 3000,
      close: true,
      gravity: "top",
      position: "center",
      backgroundColor: bgColor,
      stopOnFocus: true,
    }).showToast();
  }
});
