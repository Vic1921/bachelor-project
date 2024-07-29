import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {BoardDto} from "../../models/board-dto";

@Injectable({
  providedIn: 'root'
})
export class BoardService {
  private baseUrl = 'http://localhost:8081/api/pinterest';

  constructor(private http: HttpClient) { }

  getBoards(): Observable<BoardDto[]> {
    return this.http.get<BoardDto[]>(`${this.baseUrl}/boards`);
  }

  createBoard(board: { name: string, description: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/board`, board);
  }

  getBoard(boardId: string): Observable<BoardDto> {
    return this.http.get<BoardDto>(`${this.baseUrl}/board/${boardId}`);
  }

  deleteBoard(boardId: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/board/${boardId}`);
  }
}
