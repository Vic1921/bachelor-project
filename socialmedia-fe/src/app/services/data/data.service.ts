import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ModelFeedbackOverview} from "../../models/model-feedback-overview";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private baseUrl = 'http://localhost:8081/api/analysis';

  constructor(private http: HttpClient) { }

  getSentimentSummary(pinId: string | null): Observable<{ [p: string]: number }> {
    return this.http.get<{[key: string]: number}>(`${this.baseUrl}/sentiment-summary/${pinId}`);
  }

  getCategoryOverview(pinId: string | null): Observable<ModelFeedbackOverview> {
    return this.http.get<ModelFeedbackOverview>(`${this.baseUrl}/categorized/${pinId}`);
  }
}
