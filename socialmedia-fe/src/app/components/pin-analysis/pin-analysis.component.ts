import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PieChartComponent } from '../pie-chart/pie-chart.component';
import { CategoryTableComponent } from '../category-table/category-table.component';
import { DataService } from '../../services/data/data.service';
import { ModelFeedbackOverview } from '../../models/model-feedback-overview';
import { SentimentResult } from '../../models/sentiment-result';
import {ButtonModule} from "primeng/button";

@Component({
  selector: 'app-pin-analysis',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule, PieChartComponent, CategoryTableComponent, ButtonModule],
  templateUrl: './pin-analysis.component.html',
  styleUrls: ['./pin-analysis.component.css']
})
export class PinAnalysisComponent implements OnInit {
  sentimentResults: SentimentResult[] = [];
  sentimentSummary: Map<string, number> = new Map();
  feedbackOverview: ModelFeedbackOverview = { topConcerns: '', favoriteAspects: '', mostRequestedFeatures: '' };
  isPosting: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private dataService: DataService
  ) { }

  ngOnInit(): void {
    const pinId = this.route.snapshot.paramMap.get('pinId');
    if (pinId) {
      this.dataService.getSentimentAnalysis(pinId).subscribe(sentimentResults => {
        this.sentimentResults = sentimentResults;
        this.dataService.getSentimentSummary(pinId, sentimentResults).subscribe(summary => {
          this.sentimentSummary = summary;
          console.log(this.sentimentSummary);
        });
        this.dataService.getCategoryOverview(pinId, sentimentResults).subscribe(overview => {
          this.feedbackOverview = overview;
          console.log(this.feedbackOverview);
        });
      });
    }
  }

  postFeedbackOnPinterest(): void {
    const pinId = this.route.snapshot.paramMap.get('pinId');
    if (pinId) {
      this.isPosting = true;
      this.dataService.postFeedbackOnPinterest(pinId, this.feedbackOverview).subscribe(() => {
        console.log('Feedback posted on Pinterest');
        this.isPosting = false;
      });
    }
  }
}
