import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink, RouterOutlet} from "@angular/router";
import {CommonModule} from "@angular/common";
import {ModelFeedbackOverview} from "../../models/model-feedback-overview";
import {DataService} from "../../services/data/data.service";

@Component({
  selector: 'app-category-table',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule],
  templateUrl: './category-table.component.html',
  styleUrl: './category-table.component.css'
})
export class CategoryTableComponent implements OnInit {
  feedbackOverview: ModelFeedbackOverview = {
    topConcerns: '',
    favoriteAspects: '',
    mostRequestedFeatures: ''
  };

  constructor(
    private route: ActivatedRoute,
    private dataService: DataService) { }

  ngOnInit(): void {
    const pinId = this.route.snapshot.paramMap.get('pinId');
    this.dataService.getCategoryOverview(pinId).subscribe(overview => {
      this.feedbackOverview = overview;
    });
  }
}
