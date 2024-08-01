import { Component, Input, OnInit } from '@angular/core';
import {ChartConfiguration, ChartType, ChartData, ChartOptions} from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
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
export class PieChartComponent implements OnInit {
  @Input() data: Map<string, number> = new Map();

  //public pieChartLabels: string[] = ['Example 1', 'Example 2', 'Example 3'];
  //public pieChartLabels: string[] = ['POSITIVE', 'NEGATIVE', 'NEUTRAL'];
/*  public pieChartData: ChartData<'pie'> = {
    labels: this.pieChartLabels,
    datasets: [{
      data: [30, 50, 20],
      backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56']
    }]
  };*/
  public pieChartOptions: ChartOptions<'pie'> = {
    responsive: false,
  };
  public pieChartLabels = [ [ 'Download', 'Sales' ], [ 'In', 'Store', 'Sales' ], 'Mail Sales' ];
  public pieChartDatasets = [ {
    data: [ 300, 500, 100 ]
  } ];
  public pieChartLegend = true;
  public pieChartPlugins = [];
  public pieChartType: ChartType = 'pie';

  ngOnInit(): void {
    /*this.pieChartData.datasets[0].data = [
      this.data.get('POSITIVE') || 0,
      this.data.get('NEGATIVE') || 0,
      this.data.get('NEUTRAL') || 0
    ];*/
  }
}
