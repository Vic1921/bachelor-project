import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PinDTO } from "../../models/pin-dto";
import { PinRequestBase64 } from "../../models/pin-request-base64";
import { PinRequest } from "../../models/pin-request";

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

  deletePin(pinId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/pin/${pinId}`);
  }

  postPinWithUrl(pinRequest: PinRequest): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/pins/url`, pinRequest);
  }

  postPinWithBase64(pinRequestBase64: PinRequestBase64): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/pins/base64`, pinRequestBase64);
  }

}
