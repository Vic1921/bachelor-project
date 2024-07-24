import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PinService {
  private apiUrl = 'http://localhost:8081/api/pinterest';

  constructor(private http: HttpClient) { }

  getPins(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/pins`);
  }

  getPinDetails(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/pins/${id}`);
  }
}
