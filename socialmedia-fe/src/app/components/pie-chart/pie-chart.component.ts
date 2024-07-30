import { Component, OnInit } from '@angular/core';
import { DataService } from '../../services/data/data.service';
import { ChartConfiguration, ChartType } from 'chart.js';
import {BaseChartDirective} from 'ng2-charts';
import {ActivatedRoute, RouterLink, RouterOutlet} from "@angular/router";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-pie-chart',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule, BaseChartDirective],
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit {
  public pieChartLabels: string[] = ['Positive', 'Negative', 'Neutral'];
  public pieChartData: ChartConfiguration<'pie'>['data']['datasets'] = [{
    data: [],
    backgroundColor: ['#dc0d0d', '#43c92f', '#3bbfcb']
  }];
  public pieChartType: ChartType = 'pie';

  constructor(
    private route: ActivatedRoute,
    private dataService: DataService) { }

  ngOnInit(): void {
    const pinId = this.route.snapshot.paramMap.get('pinId');
    this.dataService.getSentimentSummary(pinId).subscribe(summary => {
      this.pieChartData[0].data = [summary['positive'], summary['negative'], summary['neutral']];
    });
  }
}
