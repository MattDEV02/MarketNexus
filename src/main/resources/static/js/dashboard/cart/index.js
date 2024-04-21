const sendDeleteCartLineItemRequest = (URI) => {
   if (validateURI(URI)) {
      window.location.href = URI;
   } else {
      window.alert("Server error.");
   }
}

const deleteCartButtons = document.querySelectorAll('.delete-cart-button');

deleteCartButtons.forEach(deleteCartButton => {
   deleteCartButton.addEventListener('click', (event) => {
      event.preventDefault();
      if (window.confirm("Are you sure you want to delete this cart line?")) {
         sendDeleteCartLineItemRequest(deleteCartButton.href);
      } else {
         window.alert("Cart line not deleted.");
      }
   });
});

