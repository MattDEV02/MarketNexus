const ctx = document.getElementById("chart");

//Chart.defaults.elements.bar.borderWidth = 2;

const productCategoriesX = ["Beauty", "Books", "Clothing", "Electronics", "Food", "Footwear"],
   numberOfSales = [12, 19, 3, 0, 2, 3];

const CHART_TYPES = {
   bar: "bar",
};

chart = new Chart(ctx, {
   type: CHART_TYPES.bar,
   data: {
      labels: productCategoriesX,
      datasets: [{
         label: " Number of Sales",
         data: numberOfSales,
         borderWidth: 1,
         backgroundColor: "#1D86BA",
         borderColor: "#000000",
         //pointRadius: 3,
         //pointBackgroundColor: "red",
      }]
   },
   options: {
      plugins: {
         title: {
            display: true,
            text: "Sales per Product category",
            fullSize: true,
            font: {
               weight: "bold",
               size: 15.5
            }
         },
         legend: {
            labels: {
               font: {
                  size: 15
               }
            }
         },
      },
      scales: {
         x: {
            beginAtZero: true,
            ticks: {
               font: {
                  size: 14
               }
            }
         },
         y: {
            beginAtZero: true,
            ticks: {
               font: {
                  size: 13
               }
            }
         }
      }
   }
});
