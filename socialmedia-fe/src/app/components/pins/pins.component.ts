import {Component, OnInit} from '@angular/core';
import { RouterLink } from "@angular/router";
import { CommonModule } from "@angular/common";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import { PinService } from "../../services/pin/pin.service";
import { Observable, of } from "rxjs";
import { PinDTO } from "../../models/pin-dto";
import { PinRequest } from "../../models/pin-request";
import { PinRequestBase64 } from "../../models/pin-request-base64";

@Component({
  selector: 'app-pins',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './pins.component.html',
  styleUrl: './pins.component.css'
})
export class PinsComponent implements OnInit {
  pins$: Observable<PinDTO[]> = of([]);
  pinForm: FormGroup;
  pinFormBase64: FormGroup;

  constructor(
    private pinService: PinService,
    private fb: FormBuilder
  ) {
    this.pinForm = this.fb.group({
      boardId: [''],
      title: [''],
      description: [''],
      mediaUrl: [''],
      altText: ['']
    });

    this.pinFormBase64 = this.fb.group({
      boardId: [''],
      title: [''],
      description: [''],
      base64Image: [''],
      altText: ['']
    });
  }

  ngOnInit(): void {
    this.pins$ = this.pinService.getPins();
  }

  createPinWithUrl(): void {
    const pinRequest: PinRequest = this.pinForm.value;
    this.pinService.postPinWithUrl(pinRequest).subscribe(() => {
      this.pins$ = this.pinService.getPins();
      this.pinForm.reset();
    });
  }

  createPinWithBase64(): void {
    const pinRequestBase64: PinRequestBase64 = this.pinFormBase64.value;
    this.pinService.postPinWithBase64(pinRequestBase64).subscribe(() => {
      this.pins$ = this.pinService.getPins();
      this.pinFormBase64.reset();
    });
  }

}
