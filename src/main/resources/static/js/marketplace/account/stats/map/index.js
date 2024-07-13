document.addEventListener("DOMContentLoaded", () => {
   const map = L.map("map").setView([0, 0], 1.5);
   const usersMapFilterContainer = document.getElementById("users-map-filter-container");

   L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
      minZoom: 1,
      maxZoom: 17,
      attribution: "&copy; <a href='https://www.openstreetmap.org/copyright'>OpenStreetMap</a>"
   }).addTo(map);

   const info = L.control();

   info.onAdd = function (map) {
      this._div = L.DomUtil.create("div", "info");
      this.update();
      return this._div;
   };

   info.update = function (props) {
      const contents = "Click a State with a marker";
      this._div.innerHTML = `<h4>Nations that use MarketNexus</h4>${contents}`;
   };

   info.addTo(map);

   // Array per tenere traccia dei marker
   let markers = [];

   const clearMarkers = () => {
      markers.forEach(marker => map.removeLayer(marker));
      markers = [];
   };

   const highlightNation = nationToNumberOfUsers => {
      axios.get(`https://nominatim.openstreetmap.org/search?format=json&q=${nationToNumberOfUsers.nationName}`)
         .then(response => {
            console.log(response);
            if (validateObject(response) && validateObject(response.data) && response.status === 200) {
               const coordinatesData = response.data;
               const marker = L.marker([coordinatesData[0].lat, coordinatesData[0].lon])
                  .addTo(map)
                  .bindPopup(`${nationToNumberOfUsers.nationName} has ${nationToNumberOfUsers.numbersOfUsers} MarketNexus ${nationToNumberOfUsers.numbersOfUsers > 1 ? "Users" : "User"}.`);
               markers.push(marker); // Aggiungi il marker all'array dei marker
            } else {
               console.warn(`Nation "${nationToNumberOfUsers.nationName}" not found.`);
            }
         })
         .catch(error => console.error("Error:", error));
   };

   const highlightNations = (response) => {
      clearMarkers(); // Pulisci i marker esistenti
      let nationsToNumberOfUsers = [];
      const mapData = response.data;
      mapData.forEach(mapDataRow => {
         nationsToNumberOfUsers.push({
            nationName: mapDataRow[0],
            numbersOfUsers: mapDataRow[1],
         });
      });
      nationsToNumberOfUsers.forEach(nationToNumberOfUsers =>
         highlightNation(nationToNumberOfUsers)
      );
   };

   axios.get(`${baseAPIURI}mapData`)
      .then(response => {
         console.log(response);
         if (validateObject(response) && validateObject(response.data) && response.status === 200) {
            highlightNations(response);
         }
      })
      .catch(error => console.error("Error:", error));

   const getUserMapFiltered = event => {
      const URI = "/marketplace/account/api/stats/mapData";
      console.log(URI);
      const usersMapFilterIsOnlineSelect = document.getElementById("users-map-filter-isonline-select"),
         usersMapFilterRoleSelect = document.getElementById("users-map-filter-role-select"),
         usersMapFilterRegisteredFrom = document.getElementById("users-map-filter-registered-from"),
         usersMapFilterRegisteredTo = document.getElementById("users-map-filter-registered-to");
      console.log({
         isOnline: usersMapFilterIsOnlineSelect.value,
         role: usersMapFilterRoleSelect.value,
         registeredFrom: usersMapFilterRegisteredFrom.value,
         registeredTo: usersMapFilterRegisteredTo.value
      });
      if (validateURI(URI)) {
         axios.get(URI, {
            params: {
               isOnline: usersMapFilterIsOnlineSelect.value,
               role: usersMapFilterRoleSelect.value,
               registeredFrom: usersMapFilterRegisteredFrom.value,
               registeredTo: usersMapFilterRegisteredTo.value
            }
         })
            .then(response => {
               console.log(response);
               if (validateObject(response) && validateObject(response.data) && response.status === 200) {
                  highlightNations(response);
               }
            })
            .catch(error => console.error("Error:", error));
      }
   };

   usersMapFilterContainer.addEventListener("change", event => getUserMapFiltered(event));
});
