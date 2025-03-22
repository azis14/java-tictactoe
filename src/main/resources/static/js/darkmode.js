document.addEventListener("DOMContentLoaded", function () {
  const themeToggle = document.getElementById("theme-toggle");

  const savedTheme =
    localStorage.getItem("theme") ||
    (window.matchMedia("(prefers-color-scheme: dark)").matches
      ? "dark"
      : "light");

  document.documentElement.setAttribute("data-theme", savedTheme);
  updateToggleButton(savedTheme);

  themeToggle.addEventListener("click", function () {
    const currentTheme = document.documentElement.getAttribute("data-theme");

    const newTheme = currentTheme === "dark" ? "light" : "dark";

    localStorage.setItem("theme", newTheme);

    document.documentElement.setAttribute("data-theme", newTheme);

    updateToggleButton(newTheme);
  });

  function updateToggleButton(theme) {
    if (theme === "dark") {
      themeToggle.innerHTML = '<i class="fas fa-moon"></i>';
      themeToggle.setAttribute("title", "Switch to Light Mode");
    } else {
      themeToggle.innerHTML = '<i class="fas fa-sun"></i>';
      themeToggle.setAttribute("title", "Switch to Dark Mode");
    }
  }
});
