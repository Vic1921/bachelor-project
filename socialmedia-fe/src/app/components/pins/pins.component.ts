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
  selectedFile: File | null = null;

  constructor(
    private pinService: PinService,
    private fb: FormBuilder
  ) {
    this.pinForm = this.fb.group({
      boardId: [''],
      title: [''],
      description: [''],
      altText: ['']
    });
  }

  ngOnInit(): void {
    this.pins$ = this.pinService.getPins();
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0] || null;
  }

  createPin(): void {
    if (!this.selectedFile) {
      console.error('No file selected');
      return;
    }

    const formData: FormData = new FormData();
    formData.append('boardId', this.pinForm.get('boardId')?.value);
    formData.append('title', this.pinForm.get('title')?.value);
    formData.append('description', this.pinForm.get('description')?.value);
    formData.append('image', this.selectedFile);
    formData.append('altText', this.pinForm.get('altText')?.value);

    this.pinService.postPinWithImageFile(formData).subscribe(() => {
      this.pins$ = this.pinService.getPins();
      this.pinForm.reset();
      this.selectedFile = null;
    });
  }


}
