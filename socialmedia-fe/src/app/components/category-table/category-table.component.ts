import {Component, Input, OnInit} from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import {CommonModule} from "@angular/common";
import {ModelFeedbackOverview} from "../../models/model-feedback-overview";

@Component({
  selector: 'app-category-table',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule],
  templateUrl: './category-table.component.html',
  styleUrl: './category-table.component.css'
})
export class CategoryTableComponent implements OnInit {
  @Input() data: ModelFeedbackOverview = { topConcerns: '', favoriteAspects: '', mostRequestedFeatures: '' };

  ngOnInit(): void {}
}
