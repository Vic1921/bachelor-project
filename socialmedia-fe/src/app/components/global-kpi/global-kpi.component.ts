import { Component, OnInit } from '@angular/core';
import { DataService } from '../../services/data/data.service';
import { PieChartComponent } from '../pie-chart/pie-chart.component';
import { CommonModule } from "@angular/common";
import { ModelFeedbackOverview } from '../../models/model-feedback-overview';
import {CategoryTableComponent} from "../category-table/category-table.component";
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-global-kpi',
  templateUrl: './global-kpi.component.html',
  styleUrls: ['./global-kpi.component.css'],
  standalone: true,
  imports: [CommonModule, PieChartComponent, CategoryTableComponent, CardModule, ButtonModule]
})
export class GlobalKPIComponent implements OnInit {
  globalKPI: Map<string, number> = new Map();
  categoryOverview: ModelFeedbackOverview | null = null;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.getGlobalKPI().subscribe(data => {
      this.globalKPI = data;
      this.loadCategoryOverview();
    });
  }

  loadCategoryOverview(): void {
    this.dataService.getGlobalKPIByCategory().subscribe(data => {
      this.categoryOverview = data;
    });
  }
}
