document.addEventListener("DOMContentLoaded", function() {
    console.log("In javascript method")
    // Get the modal element
    var modal = document.getElementById("appointment-modal");

    // Get the button that opens the modal
    var openModalBtn = document.getElementById("create-appointment-btn");

    // Get the <span> element that closes the modal
    var closeModalBtn = document.getElementsByClassName("close")[0];

    // When the user clicks on the button, open the modal
    openModalBtn.onclick = function() {
        modal.style.display = "block";
    }

    // When the user clicks on <span> (x), close the modal
    closeModalBtn.onclick = function() {
        modal.style.display = "none";
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
});

//JavaScript for index page

