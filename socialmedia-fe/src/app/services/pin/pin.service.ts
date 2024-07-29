import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {PinDTO} from "../../models/pin-dto";

@Injectable({
  providedIn: 'root'
})
export class PinService {
  private baseUrl = 'http://localhost:8081/api/pinterest';

  constructor(private http: HttpClient) { }

  getPins(): Observable<PinDTO[]> {
    return this.http.get<PinDTO[]>(`${this.baseUrl}/pins`);
  }

  getPin(pinId: string): Observable<PinDTO> {
    return this.http.get<PinDTO>(`${this.baseUrl}/pin/${pinId}`);
  }

  // TODO: Use PinRequest for both versions (base64 encoding and URL)
  createPin(pin: { title: string, description: string, imageUrl: string, boardId: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/pin`, pin);
  }

  deletePin(pinId: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/pin/${pinId}`);
  }

}
