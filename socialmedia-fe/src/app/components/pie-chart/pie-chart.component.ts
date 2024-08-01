import { Component, Input, OnInit } from '@angular/core';
import { ChartConfiguration, ChartType, ChartData } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-pie-chart',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule, BaseChartDirective],
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit {
  @Input() data: Map<string, number> = new Map();

  public pieChartLabels: string[] = ['POSITIVE', 'NEGATIVE', 'NEUTRAL'];
  public pieChartData: ChartData<'pie'> = {
    labels: this.pieChartLabels,
    datasets: [{
      data: [],
      backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56']
    }]
  };
  public pieChartType: ChartType = 'pie';

  ngOnInit(): void {
    this.pieChartData.datasets[0].data = [
      this.data.get('POSITIVE') || 0,
      this.data.get('NEGATIVE') || 0,
      this.data.get('NEUTRAL') || 0
    ];
  }
}
