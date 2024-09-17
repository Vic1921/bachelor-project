import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PinService } from '../../services/pin/pin.service';
import { Observable, of } from 'rxjs';
import { PinDTO } from '../../models/pin-dto';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { FileUploadModule } from 'primeng/fileupload';

@Component({
  selector: 'app-pins',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    CardModule,
    ButtonModule,
    FileUploadModule,
  ],
  templateUrl: './pins.component.html',
  styleUrls: ['./pins.component.css'],
})
export class PinsComponent implements OnInit {
  pins$: Observable<PinDTO[]> = of([]);
  pinForm: FormGroup;
  selectedFile: File | null = null;

  constructor(
    private pinService: PinService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.pinForm = this.fb.group({
      boardId: [''],
      title: [''],
      description: [''],
      altText: [''],
    });
  }

  ngOnInit(): void {
    this.pins$ = this.pinService.getPins();
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.files[0] || null;
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

  viewPinDetails(pinId: string): void {
    this.router.navigate(['/pin', pinId]);
  }

  getPinImageUrl(pin: PinDTO): string {
    // If the pin has an image, return the image URL, otherwise return a placeholder image
    return pin?.media?.images?.['400x300']?.url || 'assets/images/placeholder.png';
  }
}
