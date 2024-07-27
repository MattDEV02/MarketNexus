document.addEventListener("DOMContentLoaded", () => {
   const cartContainer = document.getElementById("cart-container"),
      confirmMakeOrderButton = document.getElementById("confirm-order-button"),
      baseUrl = "/marketplace/cart/updateCartLineItemQuantity/";

   cartContainer.addEventListener("change", event => {
      if (validateObject(event) && validateObject(event.target) && event.target.classList.contains("cartlineitem-quantity-input")) {
         const cartLineItemQuantityInput = event.target;
         // id e quantity del CartLineItem i-esimo.
         const
            cartLineItemId = cartLineItemQuantityInput.id.replace("quantity#", ""),
            quantity = parseInt(cartLineItemQuantityInput.value, 10);
         const url = baseUrl + cartLineItemId;
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

   confirmMakeOrderButton.addEventListener("click", () => {
      const orderProcessedAudio = new Audio("/audio/order-processed.mp3");
      orderProcessedAudio.play()
         .then(() => console.log("orderProcessedAudio playback started successfully."))
         .catch(error => console.error("Error during orderProcessedAudio playback:", error));
   });
});

