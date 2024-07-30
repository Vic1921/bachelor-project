import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PinService } from '../../services/pin/pin.service';
import { PinDTO } from '../../models/pin-dto';

@Component({
  selector: 'app-pin-analysis',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule],
  templateUrl: './pin-analysis.component.html',
  styleUrls: ['./pin-analysis.component.css']
})
export class PinAnalysisComponent implements OnInit {
  pin: PinDTO | undefined;

  constructor(
    private route: ActivatedRoute,
    private pinService: PinService
  ) { }

  ngOnInit(): void {
    const pinId = this.route.snapshot.paramMap.get('pinId');
    this.pinService.getPin(<string>pinId).subscribe(data => this.pin = data);
  }
}
