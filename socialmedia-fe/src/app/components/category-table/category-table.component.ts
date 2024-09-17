import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { TooltipModule } from 'primeng/tooltip';
import { ButtonModule } from 'primeng/button';
import {ModelFeedbackOverview} from "../../models/model-feedback-overview";

@Component({
  selector: 'app-category-table',
  standalone: true,
  imports: [CommonModule, TableModule, TooltipModule, ButtonModule],
  templateUrl: './category-table.component.html',
  styleUrls: ['./category-table.component.css'],
})
export class CategoryTableComponent implements OnInit {
  @Input() data: ModelFeedbackOverview = {
    topConcerns: '',
    favoriteAspects: '',
    mostRequestedFeatures: '',
  };

  tableData: any[] = [];

  ngOnInit(): void {
    this.prepareTableData();
  }

  truncateText(text: string, limit: number): string {
    return text.length > limit ? text.substring(0, limit) + '...' : text;
  }

  prepareTableData(): void {
    this.tableData = [
      { category: 'Top Concerns', content: this.data.topConcerns, expanded: false },
      { category: 'Favorite Aspects', content: this.data.favoriteAspects, expanded: false },
      { category: 'Most Requested Features', content: this.data.mostRequestedFeatures, expanded: false },
    ];
  }

}
