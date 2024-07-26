import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BoardService {
  private baseUrl = 'http://localhost:8081/api/pinterest';

  constructor(private http: HttpClient) { }

  getBoards(): Observable<any> {
    return this.http.get(`${this.baseUrl}/boards`);
  }

  createBoard(board: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/board`, board);
  }

  getBoard(boardId: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/board/${boardId}`);
  }

  deleteBoard(boardId: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/board/${boardId}`);
  }
}
