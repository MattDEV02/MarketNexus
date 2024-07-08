document.addEventListener("DOMContentLoaded", () => {
   const cartContainer = document.getElementById("cart-container"),
      baseUrl = "/marketplace/cart/updateCartLineItemQuantity/";

   cartContainer.addEventListener("change", event => {
      if (validateObject(event) && validateObject(event.target) && event.target.classList.contains("cartlineitem-quantity-input")) {
         const cartLineItemQuantityInput = event.target;
         // id e quantity del CartLineItem i-esimo.
         const
            id = cartLineItemQuantityInput.id.replace("quantity#", ""),
            quantity = parseInt(cartLineItemQuantityInput.value, 10);
         const url = baseUrl + id;
         const data = {
            quantity
         };
         if (validateURI(url)) {
            axios.put(url, data)
               .then(response => {
                  console.log(response);
                  if (validateObject(response) && validateObject(response.data) && response.status === 200) {
                     cartContainer.innerHTML = response.data;
                  }
               })
               .catch(error => console.error("Error:", error))
               .finally(() => {
                  const activeElement = document.getElementById(cartLineItemQuantityInput.id);
                  if (validateObject(activeElement)) {
                     activeElement.focus();
                  }
               });
         }
      }
   });

   const deleteCartLineItemButtons = document.querySelectorAll(".confirm-confirm-delete-cartlineitem-button");

   deleteCartLineItemButtons.forEach(deleteCartLineItemButton => {
      deleteCartLineItemButton.addEventListener("click", event => {
         event.preventDefault();
         console.log(deleteCartLineItemButton);
         axios.delete(deleteCartLineItemButton.href)
            .then(response => {
               console.log(response);
               if (validateObject(response) && validateObject(response.data) && validateString(response.data.redirect) && response.status === 200) {
                  window.location.href = response.data.redirect;
               }
            })
            .catch(error => console.error(error));
      });
   });

});

