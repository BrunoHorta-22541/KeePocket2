<?php

namespace App\Charts;

use ConsoleTVs\Charts\Classes\Chartjs\Chart;
class MovementsChart extends Chart
{
    /**
     * Initializes the chart.
     */
    public function __construct($categoryNames, $colors)
    {
        $this->labels($categoryNames); // Set category names as labels
        $this->options([
            'responsive' => true,
            'maintainAspectRatio' => false,
        ]);
        //$this->backgroundColor($colors); // Set random colors
       // $this->hoverBackgroundColor($colors); // Set hover colors
    }

    /**
     * Add dataset to the chart.
     *
     * @param  mixed  $dataset
     * @param  string  $name
     * @return \ConsoleTVs\Charts\Classes\Chartjs\Dataset
     */
   /* public function dataset($dataset, $name)
    {
        return parent::dataset($dataset, $name)
            ->backgroundColor($this->colors)
            ->hoverBackgroundColor($this->colors);
    }*/
}