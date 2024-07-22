import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SentimentService {
  private apiUrl = 'http://localhost:8081/api/analysis'; 

  constructor(private http: HttpClient) { }

    // TODO: Implement endpoints

  getAnalysis(pinId: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/analysis/${pinId}`);
  }
}