<script type="text/javascript">
// dashboard.js
google.charts.load('current', { 'packages': ['corechart'] });
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    var data = google.visualization.arrayToDataTable([
        ['categoryName', 'totalvalue'],
        @foreach($pieChart as $d)
            ['{{ $d->categoryName }}', {{ $d->totalvalue }}],
        @endforeach
    ]);

    var options = {
        title: 'Category Detail',
        is3D: false,
    };

    var chart = new google.visualization.PieChart(document.getElementById('piechart'));

    chart.draw(data, options);
}
</script>

