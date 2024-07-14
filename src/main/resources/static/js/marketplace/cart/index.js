document.addEventListener("DOMContentLoaded", () => {
   const cartContainer = document.getElementById("cart-container"),
      baseUrl = "/marketplace/cart/updateCartLineItemQuantity/";

   cartContainer.addEventListener("change", event => {
      if (validateObject(event) && validateObject(event.target) && event.target.classList.contains("cartlineitem-quantity-input")) {
         const cartLineItemQuantityInput = event.target;
         // id e quantity del CartLineItem i-esimo.
         const
            cartLineItemId = cartLineItemQuantityInput.id.replace("quantity#", ""),
            cartLineItemQuantity = parseInt(cartLineItemQuantityInput.value, 10);
         const url = baseUrl + cartLineItemId;
         const data = {
            cartLineItemQuantity
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

   cartContainer.addEventListener("click", event => {
      if (event.target.classList.contains("confirm-delete-cartlineitem-button")) {
         event.preventDefault();
         const deleteCartLineItemButton = event.target;
         const URI = deleteCartLineItemButton.href;
         if (validateURI(URI)) {
            axios.delete(URI)
               .then(response => {
                  console.log(response);
                  if (validateObject(response) && validateObject(response.data) && response.status === 200) {
                     if (!validateObject(response.data.redirect)) {
                        const cartLineItemId = deleteCartLineItemButton.id.replace("confirm-delete-cartlineitem-button-", "");
                        document.getElementById("close-confirm-delete-cartlineitem-modal-" + cartLineItemId).click();
                        cartContainer.innerHTML = response.data;
                     } else {
                        window.location.href = response.data.redirect;
                     }
                  }
               })
               .catch(error => console.error("Error:", error));
         }
      }
   });
});

