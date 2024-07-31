import {Component, Input, OnInit} from '@angular/core';
import { ChartConfiguration, ChartType } from 'chart.js';
import {BaseChartDirective} from 'ng2-charts';
import {RouterLink, RouterOutlet} from "@angular/router";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-pie-chart',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule, BaseChartDirective],
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit {
  @Input() data: { [key: string]: number } = { positive: 0, negative: 0, neutral: 0 };

  public pieChartLabels: string[] = ['Positive', 'Negative', 'Neutral'];
  public pieChartData: ChartConfiguration<'pie'>['data']['datasets'] = [{
    data: [],
    backgroundColor: ['#dc0d0d', '#43c92f', '#3bbfcb']
  }];
  public pieChartType: ChartType = 'pie';

  ngOnInit(): void {
    this.pieChartData[0].data = [this.data['positive'], this.data['negative'], this.data['neutral']];
  }
}
