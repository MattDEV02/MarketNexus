document.addEventListener("DOMContentLoaded", () => {
   const chatbotContainer =
      document.getElementById("chatbot-container");
   const chatbotContainerStyle = chatbotContainer.style,
      chatbotButton = document.getElementById("chatbot-button"),
      closeChatbot = document.getElementById("close-chatbot"),
      chatbotSendMessageButton = document.getElementById(
         "chatbot-send-message-button",
      ),
      chatbotMessageTextInput = document.getElementById(
         "chatbot-message-text-input",
      ),
      chatBotContent = document.getElementById("chatbot-content");

   String.prototype.capitalizeFirstChar = function () {
      if (!validateObject(this) === null || this.length === 0) {
         return this;
      }
      return this.charAt(0).toUpperCase() + this.slice(1);
   };

   const getCurrentTime = () => {
      const now = new Date();
      const hours = now.getHours().toString().padStart(2, "0");
      const minutes = now.getMinutes().toString().padStart(2, "0");
      return hours + ":" + minutes;
   };

   const createChatbotResponse = chatbotResponse => {
      let messageDivResponse = document.createElement("div");
      messageDivResponse.className =
         "bg-secondary text-white p-2 rounded-3 my-4 mx-1";
      let messageSpanTextResponse = document.createElement("span");
      messageSpanTextResponse.textContent = chatbotResponse;
      messageDivResponse.appendChild(messageSpanTextResponse);
      chatBotContent.appendChild(messageDivResponse);
   };

   const messageTransaction = () => {
      const messageText =
         chatbotMessageTextInput.value.capitalizeFirstChar();
      if (messageText.length >= chatbotMessageTextInput.minLength) {
         // User request
         chatbotMessageTextInput.value = "";
         let messageDiv = document.createElement("div");
         messageDiv.className =
            "bg-white text-black border border-black p-2 rounded-3 message my-4";
         let messageSpanText = document.createElement("span");
         messageSpanText.className = "mx-1";
         messageSpanText.textContent = messageText;
         let messageSpanTimestamp = document.createElement("span");
         messageSpanTimestamp.className = "time";
         messageSpanTimestamp.textContent = getCurrentTime();
         messageDiv.appendChild(messageSpanTimestamp);
         messageDiv.appendChild(messageSpanText);
         chatBotContent.appendChild(messageDiv);
         chatbotMessageTextInput.focus();
         // Chatbot response

         const API_KEY = "3058a65c8ba7410a8c87e58fb82fd88c ";

         axios
            .post(
               "https://api.aimlapi.com/chat/completions",
               {
                  model: "mistralai/Mistral-7B-Instruct-v0.2",
                  prompt: messageText,
                  max_tokens: 50,
                  temperature: 1,
                  messages: [
                     {
                        role: "system",
                        content:
                           "You are an AI assistant who knows everything.",
                     },
                     {
                        role: "user",
                        content: messageText,
                     },
                  ],
               },
               {
                  headers: {
                     Authorization: `Bearer ${API_KEY}`,
                     "Content-Type": "application/json",
                  },
               },
            )
            .catch(error => {
               console.error("Error: ", error);
               createChatbotResponse(
                  "A MarketNexus error occurred: " + error.message,
               );
            })
            .then(response => {
               console.log(response);
               if (
                  validateObject(response) &&
                  validateObject(response.data) &&
                  validateArray(response.data.choices) &&
                  validateObject(response.data.choices[0].message) &&
                  validateObject(response.data.choices[0].message.content) &&
                  (response.status === 200 || response.status === 201)
               ) {
                  const answer =
                     response.data.choices[0].message.content.trim();
                  createChatbotResponse(answer);
               }
            })
            .then(() => {
               const chatbotScrollContainer = document.getElementById(
                  "chatbot-scroll-container",
               );
               chatbotScrollContainer.scrollTop =
                  chatbotScrollContainer.scrollHeight;
            });
      }
   };

   chatbotButton.addEventListener("click", () => {
      chatbotContainerStyle.display =
         document.getElementById("chatbot-container").style.display ===
         "flex"
            ? "none"
            : "flex";
   });

   closeChatbot.addEventListener(
      "click",
      () => (chatbotContainerStyle.display = "none"),
   );

   chatbotMessageTextInput.addEventListener("keydown", event => {
      if (event.key && event.key === "Enter") {
         event.preventDefault();
         messageTransaction();
      }
   });

   chatbotSendMessageButton.addEventListener("click", () =>
      messageTransaction(),
   );
});