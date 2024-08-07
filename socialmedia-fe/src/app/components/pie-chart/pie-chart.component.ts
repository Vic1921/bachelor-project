import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ChartType, ChartOptions} from 'chart.js';
import { NgChartsModule } from 'ng2-charts';

import { RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-pie-chart',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule, NgChartsModule],
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnChanges {
  @Input() chartData: Map<string, number> = new Map();

  public pieChartOptions: ChartOptions<'pie'> = {
    responsive: false,
  };
  public pieChartLabels: string[] = ['POSITIVE', 'NEGATIVE', 'NEUTRAL'];
  public pieChartDatasets: { data: number[], backgroundColor: string[] }[] = [{
    data: [],
    backgroundColor: ['#4caf50', '#f44336', '#9e9e9e']
  }];
  public pieChartLegend = true;
  public pieChartPlugins = [];
  public pieChartType: ChartType = 'pie';

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['chartData']) {
      this.updateChartData();
    }
  }

  updateChartData(): void {
    let values: number[] = [];
    if (this.chartData instanceof Map) {
      values = Array.from(this.chartData.values()).map(value => value as number);
    } else if (typeof this.chartData === 'object') {
      values = Object.values(this.chartData).map(value => value as number);
      console.log(values);
    } else {
      console.error('chartData is not a Map or an object');
    }
    this.pieChartDatasets[0].data = values;
    console.log(this.pieChartDatasets[0].data);
  }

}

