import { Component, OnInit } from '@angular/core';
import { DataService } from '../../services/data/data.service';
import { PieChartComponent } from '../pie-chart/pie-chart.component';
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-global-kpi',
  templateUrl: './global-kpi.component.html',
  styleUrls: ['./global-kpi.component.css'],
  standalone: true,
  imports: [CommonModule, PieChartComponent]
})
export class GlobalKPIComponent implements OnInit {
  globalKPI: Map<string, number> = new Map();

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.getGlobalKPI().subscribe(data => {
      this.globalKPI = data;
    });
  }
}
