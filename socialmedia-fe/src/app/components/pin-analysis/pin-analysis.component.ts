import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import {PieChartComponent} from "../pie-chart/pie-chart.component";
import {CategoryTableComponent} from "../category-table/category-table.component";
import {DataService} from "../../services/data/data.service";
import {ModelFeedbackOverview} from "../../models/model-feedback-overview";
import {SentimentResult} from "../../models/sentiment-result";

@Component({
  selector: 'app-pin-analysis',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule, PieChartComponent, CategoryTableComponent],
  templateUrl: './pin-analysis.component.html',
  styleUrls: ['./pin-analysis.component.css']
})
export class PinAnalysisComponent implements OnInit {
  sentimentResults: SentimentResult[] = [];
  sentimentSummary: Map<string, number> = new Map();
  /*sentimentSummary: { [key: string]: number } = { POSITIVE: 0, NEGATIVE: 0, NEUTRAL: 0 };*/
  feedbackOverview: ModelFeedbackOverview = { topConcerns: '', favoriteAspects: '', mostRequestedFeatures: '' };

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
        });
        this.dataService.getCategoryOverview(pinId, sentimentResults).subscribe(overview => {
          this.feedbackOverview = overview;
        });
      });
    }
  }
}
