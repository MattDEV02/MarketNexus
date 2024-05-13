//Chart.defaults.elements.bar.borderWidth = 2;

const CHART_TYPES = {
   bar: "bar",
   line: "line",
   horizontalBar: "horizontalBar",
   pie: "pie",
   //radar: "radar",
   //polarArea: "polarArea",
   bubble: "bubble",
   doughnut: "doughnut",
};

const isMultiColorChartType = chartType => chartType === CHART_TYPES.pie || chartType === CHART_TYPES.doughnut;

const getChartColor = chartType =>
   isMultiColorChartType(chartType) ? [
      "#0D6EFD", // PRIMARY
      "#6C757D", // SECONDARY
      "#198754", // SUCCESS
      "#DC3545", // DANGER
      "#FFC107", // WARNING
      "#0DCAF0", // INFO
      "#212529", // DARK
   ] : "#1D86BA";


const chartTypeSelect = document.getElementById("chart-type-select");

const weekDaysXToNumberOfSalesY = [];

let weekDaysX = null, numberOfSoldSalesY = null;

const ctx = document.getElementById("chart").getContext("2d");

let type = null, data = null, options = null, config = null;

document.addEventListener("DOMContentLoaded", () => {
   axios.get(`${baseAPIURI}chartData`)
      .then(response => {
         const chartData = response.data;
         console.log(chartData);
         if (validateObject(chartData) && response.status === 200) {
            chartData.forEach(chartDataRow => {
               weekDaysXToNumberOfSalesY.push({
                  weekDay: chartDataRow[0],
                  numberOfSoldSales: chartDataRow[1],
               });
            });
            weekDaysX = weekDaysXToNumberOfSalesY.map(productCategoryToNumberOfSales => productCategoryToNumberOfSales.weekDay);
            numberOfSoldSalesY = weekDaysXToNumberOfSalesY.map(productCategoryToNumberOfSales => productCategoryToNumberOfSales.numberOfSoldSales);
            type = CHART_TYPES.bar;
            data = {
               labels: weekDaysX,
               datasets: [{
                  label: " Number of Sales in this day",
                  data: numberOfSoldSalesY,
                  borderWidth: 2,
                  backgroundColor: "#1D86BA",
                  borderColor: "#000000",
                  pointRadius: 5,
               }]
            };
            options = {
               indexAxis: "x",
               responsive: true,
               maintainAspectRatio: false,
               plugins: {
                  title: {
                     display: true,
                     text: "Number of sales in last week",
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
                        },
                     }
                  },
                  y: {
                     beginAtZero: true,
                     ticks: {
                        font: {
                           size: 13
                        },
                        callback: (value) => parseInt(value) === value ? value : null
                     }
                  }
               }
            };
            config = {
               type,
               data,
               options,
            }
            chart = new Chart(ctx, config);
            chartTypeSelect.addEventListener('change', () => {
               const selectedChartType = chartTypeSelect.value;
               chart.config.type = selectedChartType;
               chart.config.data.datasets[0].backgroundColor = getChartColor(selectedChartType);
               chart.update();
            });
         }
      })
      .catch(error => console.error("Error:", error));
});



