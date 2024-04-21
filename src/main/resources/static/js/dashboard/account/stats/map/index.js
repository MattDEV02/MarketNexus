const map = L.map("map").setView([0, 0], 1.5);

L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
   minZoom: 1,
   maxZoom: 17,
   attribution: "&copy; <a href='http://www.openstreetmap.org/copyright'>OpenStreetMap</a>"
}).addTo(map);


// control that shows state info on hover
const info = L.control();

info.onAdd = function (map) {
   this._div = L.DomUtil.create("div", "info");
   this.update();
   return this._div;
};

info.update = function (props) {
   const contents = "Click a state with a marker";
   this._div.innerHTML = `<h4>Nations that use MarketNexus</h4>${contents}`;
};

info.addTo(map);


const highlightCountry = countryToNumberOfUsers => {
   fetch(`https://nominatim.openstreetmap.org/search?format=json&q=${countryToNumberOfUsers.countryName}`)
      .then(response => response.json())
      .then(data => {
         console.log(data);
         // Se ci sono risultati, prendi le coordinate del primo risultato
         if (validateObject(data)) {
            L.marker([data[0].lat, data[0].lon])
               .addTo(map)
               .bindPopup(`${countryToNumberOfUsers.countryName} has ${countryToNumberOfUsers.numbersOfUsers} MarketNexus ${countryToNumberOfUsers.numbersOfUsers > 1 ? 'users' : 'user'}.`);
         } else {
            console.warn("Nation not found.");
         }
      })
      .catch(error => console.error("Error:", error));
}


const countriesToNumberOfUsers = [
   {
      countryName: "Italy",
      numbersOfUsers: 1
   },
   {
      countryName: "France",
      numbersOfUsers: 2
   },
   {
      countryName: "Germany",
      numbersOfUsers: 3
   },
   {
      countryName: "United Kingdom",
      numbersOfUsers: 4
   }
];

// Evidenzia ciascuna nazione sulla mappa
countriesToNumberOfUsers.forEach(countryToNumberOfUsers =>
   highlightCountry(countryToNumberOfUsers)
)