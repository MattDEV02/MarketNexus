const sendMakeOrderRequest = (URI) => {
   if (validateURI(URI)) {
      window.location.href = URI;
   } else {
      window.alert("Server error.");
   }
}

const makeOrderButton = document.getElementById("make-order-button"),
   cartTotalText = document.getElementById("cart-total-text").innerText.replace("Total: ", '');

makeOrderButton.addEventListener("click", (event) => {
   event.preventDefault();
   if (window.confirm(`Are you sure you want to make this order of ${cartTotalText} ?`)) {
      sendMakeOrderRequest(makeOrderButton.href);
   } else {
      window.alert("Order not sent.");
   }
});


