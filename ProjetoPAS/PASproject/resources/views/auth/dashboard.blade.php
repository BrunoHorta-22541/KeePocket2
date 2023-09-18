
@extends('layouts.auth');

@section('content')
<div class="main-panel">
          <div class="content-wrapper">
            <!--Conteudo Aqui -->
            <div class="row">
              <div class="col-sm-6 grid-margin">
                <div class="card">
                <div class="card-body">
                  <h5>Income</h5>
                  <div class="row">
                      <div class="col-8 col-sm-12 col-xl-8 my-auto">
                          <div class="d-flex d-sm-block d-md-flex align-items-center">
                              <h2 class="mb-0" id="income-total">{{ $sumpositive  ?? '0' }}</h2>
                          </div>
                      </div>
                      <div class="col-4 col-sm-12 col-xl-4 text-center text-xl-right">
                          <i class="icon-lg mdi mdi-codepen text-primary ml-auto"></i>
                      </div>
                  </div>
              </div>
                </div>
              </div>
              <div class="col-sm-6 grid-margin">
                <div class="card">
                  <div class="card-body">
                    <h5>Expenses</h5>
                    <div class="row">
                      <div class="col-8 col-sm-12 col-xl-8 my-auto">
                        <div class="d-flex d-sm-block d-md-flex align-items-center">
                          <h2 class="mb-0">{{ $sumnegative ?? '0' }}</h2>
                        </div>
                      </div>
                      <div class="col-4 col-sm-12 col-xl-4 text-center text-xl-right">
                        <i class="icon-lg mdi mdi-wallet-travel text-danger ml-auto"></i>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>


            <div class="row">
                <div class="col-md-3 grid-margin stretch-card">
                    <!--Empty card -->
                </div>

                <div class="col-md-6 grid-margin stretch-card">
                    <div class="card">
                      <div class="card-body">
                          <h4 class="card-title">Total expenses By categories</h4>
                          <div id="piechart" style="width: 900px; height: 500px;"></div>
                      </div>
                    </div>
                </div>
                <div class="col-md-3 grid-margin stretch-card">
                    <!--Empty card-->
                </div>
            </div>
            <!--Conteudo Aqui -->
          </div>
          <!-- content-wrapper ends -->
          <!-- partial:partials/_footer.html -->

          <!-- partial -->
        </div>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">
            google.charts.load('current', {'packages': ['corechart']});
            google.charts.setOnLoadCallback(drawChart);

            function drawChart() {
                var data = google.visualization.arrayToDataTable([
                ['Category Name', 'Total Value'],
                @foreach($pieChart as $d)
                    ['{{ $d->categoryName }}', {{ abs($d->totalvalue) }}], // Leave the value as is
                @endforeach
            ]);

            // Add '€' symbol to the labels
            for (var i = 0; i < data.getNumberOfRows(); i++) {
                data.setFormattedValue(i, 1, data.getFormattedValue(i, 1) + '€');
            }

            var options = {
                title: '',
                is3D: false,
                backgroundColor: 'none',
                titleTextStyle: {
                    color: 'white' // Set the title text color to white
                },
                legend: {
                    textStyle: {
                        color: 'white' // Set the legend text color to white
                    }
                },
                pieSliceText: 'label', // This is where you can specify the label format
                pieSliceTextStyle: {
                    color: 'white', // Set the label text color to white
                },
                pieSliceText: 'value-and-percentage', // Display both value and percentage
            };

            var chart = new google.visualization.PieChart(document.getElementById('piechart'));

            chart.draw(data, options);
            }
        </script>

        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

@endsection
