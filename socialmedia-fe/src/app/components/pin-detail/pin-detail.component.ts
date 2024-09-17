import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router, RouterLink, RouterOutlet} from '@angular/router';
import { PinService } from '../../services/pin/pin.service';
import { CommonModule } from '@angular/common';
import {PinDTO} from "../../models/pin-dto";
import {ButtonModule} from "primeng/button";

@Component({
  selector: 'app-pin-detail',
  standalone: true,
  imports: [RouterOutlet, RouterLink, CommonModule, ButtonModule],
  templateUrl: './pin-detail.component.html',
  styleUrls: ['./pin-detail.component.css']
})
export class PinDetailComponent implements OnInit {
  pin: PinDTO | undefined;

  constructor(
    private route: ActivatedRoute,
    private pinService: PinService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const pinId = this.route.snapshot.paramMap.get('pinId');
    this.pinService.getPin(<string>pinId).subscribe(data => this.pin = data);
  }

  performAnalysis(): void {
    const pinId = this.route.snapshot.paramMap.get('pinId');
    this.router.navigate(['/pin-analysis', pinId]);
  }

  deletePin(): void {
    const pinId = this.route.snapshot.paramMap.get('pinId');
    if (pinId) {
      this.pinService.deletePin(pinId).subscribe(() => {
        this.router.navigate(['/pins']);
      });
    }
  }

}
