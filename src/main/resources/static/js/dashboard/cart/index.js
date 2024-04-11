const regExp = new RegExp("^(https?|ftp):\\/\\/[^\\s/$.?#].[^\\s]*$");

const validateURL = (URL) =>
   URL !== undefined && URL !== null && URL !== "" && URL !== " " &&
   regExp.test(
      URL
   );


const sendDeleteCartRequest = (URI) => {
   if (validateURL(URI)) {
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
         sendDeleteCartRequest(deleteCartButton.href);
      } else {
         window.alert("Cart line not deleted.");
      }
   });
});

