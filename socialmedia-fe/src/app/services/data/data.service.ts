import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ModelFeedbackOverview} from "../../models/model-feedback-overview";
import {SentimentResult} from "../../models/sentiment-result";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private baseUrl = 'http://localhost:8081/api/analysis';

  constructor(private http: HttpClient) { }

  getSentimentAnalysis(pinId: string): Observable<SentimentResult[]> {
    return this.http.get<SentimentResult[]>(`${this.baseUrl}/${pinId}`);
  }

  getCategoryOverview(pinId: string, sentimentResults: SentimentResult[]): Observable<ModelFeedbackOverview> {
    return this.http.post<ModelFeedbackOverview>(`${this.baseUrl}/categorized/${pinId}`, sentimentResults);
  }

  getSentimentSummary(pinId: string, sentimentResults: SentimentResult[]): Observable<Map<string, number>> {
    return this.http.post<Map<string, number>>(`${this.baseUrl}/sentiment-summary/${pinId}`, sentimentResults);
  }


}
