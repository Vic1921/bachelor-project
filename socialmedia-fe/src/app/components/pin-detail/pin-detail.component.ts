import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterOutlet } from '@angular/router';
import { PinService } from '../../services/pin/pin.service';
import { CommonModule } from '@angular/common';
import {PinDTO} from "../../models/pin-dto";

@Component({
  selector: 'app-pin-detail',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule],
  templateUrl: './pin-detail.component.html',
  styleUrls: ['./pin-detail.component.css']
})
export class PinDetailComponent implements OnInit {
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
