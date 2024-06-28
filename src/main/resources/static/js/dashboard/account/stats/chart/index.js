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

const canvas = document.getElementById("chart");

const ctx = canvas.getContext("2d");

let type = null, data = null, options = null, config = null;

document.addEventListener("DOMContentLoaded", () => {
   axios.get(`${baseAPIURI}chartData`)
      .then(response => {
         console.log(response);
         if (validateObject(response) && validateObject(response.data) && response.status === 200) {
            const chartData = response.data;
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
                  label: " Number sold of Sales in this day",
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
                     text: "Number of sold Sales in this week",
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
            chartTypeSelect.addEventListener("change", () => {
               const selectedChartType = chartTypeSelect.value;
               chart.config.type = selectedChartType;
               chart.config.data.datasets[0].backgroundColor = getChartColor(selectedChartType);
               chart.update();
            });
         }
      })
      .catch(error => console.error("Error:", error));
});

const downloadPDFChartButton = document.getElementById("pdf-download-chart-button"),
   downloadPNGChartButton = document.getElementById("png-download-chart-button")
printChartButton = document.getElementById("print-chart-button");

downloadPDFChartButton.addEventListener("click", event => {
   const {jsPDF} = window.jspdf;
   const pdf = new jsPDF();
   const imgData = canvas.toDataURL("image/png");

   // Dimensioni della pagina del PDF
   const pageWidth = pdf.internal.pageSize.getWidth();
   const pageHeight = pdf.internal.pageSize.getHeight();

   const canvasWidth = canvas.width;
   const canvasHeight = canvas.height;
   const ratio = Math.min(pageWidth / canvasWidth, pageHeight / canvasHeight);

   // Calcola le nuove dimensioni per mantenere le proporzioni
   const imgWidth = canvasWidth * ratio;
   const imgHeight = canvasHeight * ratio;

   // Centra l'immagine nella pagina PDF
   const x = (pageWidth - imgWidth) / 2;
   const y = (pageHeight - imgHeight) / 2;

   // Aggiungi l'immagine del canvas al PDF
   pdf.addImage(imgData, "PNG", x, y, imgWidth, imgHeight);
   pdf.save("your_sold_sales_chart.pdf");
});

downloadPNGChartButton.addEventListener("click", () => {
   const link = document.createElement("a");
   link.download = "your_sold_sales_chart.png";
   link.href = canvas.toDataURL("image/png");
   link.click();
});

printChartButton.addEventListener("click", event => {
   const imgData = canvas.toDataURL("image/png");
   const printWindow = window.open("", "_blank");
   printWindow.document.write("<html><head><title>Your sold sales Chart</title><style>body{margin:0;display:flex;align-items:center;justify-content:center;} img{max-width:100%;max-height:100%;}</style></head><body>");
   printWindow.document.write(`<img src="${imgData}" alt="Number of sold Sales in this week" />`);
   printWindow.document.write("</body></html>");
   printWindow.document.close();
   printWindow.focus();
   // Usa un timeout per garantire che la pagina sia caricata prima della stampa
   setTimeout(() => {
      printWindow.print();
      printWindow.close();
   }, 750);
});