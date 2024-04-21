const baseURI = "http://localhost:8080/dashboard/";

const
   saleBaseURI = baseURI + "sale/",
   orderBaseURI = baseURI + "order/";

const salesEvent = [
      {title: 'Smartphone in sale', start: '2024-04-21', color: 'yellow', url: saleBaseURI + '1'},
      {title: 'T-shirt in sale', start: '2024-04-22', color: 'yellow', url: saleBaseURI + '2'}
   ],
   orderEvent = [
      {title: 'Smartphone ordered', start: '2024-04-21', color: 'green', url: orderBaseURI + '1'},
      {title: 'T-shirt ordered', start: '2024-04-22', color: 'green', url: orderBaseURI + '2'}
   ];

document.addEventListener("DOMContentLoaded", () => {
   let calendarEl = document.getElementById("user-calendar");
   let calendar = new FullCalendar.Calendar(calendarEl, {
      initialView: "dayGridMonth",
      headerToolbar: {
         start: "prevYear,nextYear",
         center: "title",
         end: "today prev,next"
      },
      events: [],
   });

   salesEvent.forEach(saleEvent => calendar.addEvent(saleEvent));

   orderEvent.forEach(orderEvent => calendar.addEvent(orderEvent));
   calendar.render();
});

